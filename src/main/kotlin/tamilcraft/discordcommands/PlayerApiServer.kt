package tamilcraft.discordcommands

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.Locale
import java.util.UUID
import java.util.concurrent.Executors
import kotlin.math.roundToInt

class PlayerApiServer(private val gson: Gson) {
    private val logger = LoggerFactory.getLogger("tamilcraft-discord-commands")

    @Volatile
    private var activeServer: MinecraftServer? = null

    @Volatile
    private var requiredToken: String = ""

    private var httpServer: HttpServer? = null
    private var runningHost: String = ""
    private var runningPort: Int = -1

    fun registerLifecycleHooks() {
        ServerLifecycleEvents.SERVER_STARTED.register(ServerLifecycleEvents.ServerStarted { server ->
            activeServer = server
        })
        ServerLifecycleEvents.SERVER_STOPPING.register(ServerLifecycleEvents.ServerStopping {
            activeServer = null
        })
    }

    @Synchronized
    fun start(host: String, port: Int, authToken: String) {
        val normalizedToken = authToken.trim()

        if (httpServer != null) {
            if (runningHost == host && runningPort == port && requiredToken == normalizedToken) {
                return
            }
            stop()
        }

        val server = HttpServer.create(InetSocketAddress(host, port), 0)
        server.createContext("/api/health", HealthHandler())
        server.createContext("/api/player", LegacyPlayerDetailsHandler())
        server.createContext("/api/players", PlayersApiHandler())
        server.executor = Executors.newFixedThreadPool(2)
        server.start()

        runningHost = host
        runningPort = port
        requiredToken = normalizedToken
        httpServer = server

        logger.info("Player API started on http://{}:{}", host, port)
    }

    @Synchronized
    fun stop() {
        val current = httpServer ?: return
        current.stop(0)
        httpServer = null
        runningHost = ""
        runningPort = -1
        logger.info("Player API stopped")
    }

    private data class PlayerIdentity(
        val uuid: UUID,
        val input: String,
        val player: ServerPlayer?
    )

    private inner class HealthHandler : HttpHandler {
        override fun handle(exchange: HttpExchange) {
            if (!isAuthorized(exchange)) {
                writeJson(exchange, 401, mapOf("error" to "Unauthorized"))
                return
            }

            if (exchange.requestMethod != "GET") {
                writeJson(exchange, 405, mapOf("error" to "Only GET is supported"))
                return
            }

            writeJson(
                exchange,
                200,
                mapOf(
                    "status" to "ok",
                    "serverAttached" to (activeServer != null)
                )
            )
        }
    }

    private inner class LegacyPlayerDetailsHandler : HttpHandler {
        override fun handle(exchange: HttpExchange) {
            if (!isAuthorized(exchange)) {
                writeJson(exchange, 401, mapOf("error" to "Unauthorized"))
                return
            }

            if (exchange.requestMethod != "GET") {
                writeJson(exchange, 405, mapOf("error" to "Only GET is supported"))
                return
            }

            val server = activeServer
            if (server == null) {
                writeJson(exchange, 503, mapOf("error" to "Minecraft server not available yet"))
                return
            }

            val path = exchange.requestURI.path.removePrefix("/api/player").trim('/')
            if (path.isBlank()) {
                writeJson(
                    exchange,
                    400,
                    mapOf("error" to "Use /api/player/{uuid-or-name}")
                )
                return
            }

            val identity = resolveIdentity(server, decodePathSegment(path))
            if (identity == null) {
                writeJson(exchange, 404, mapOf("error" to "Player not found", "lookup" to path))
                return
            }

            writeJson(exchange, 200, buildPlayerProfile(server, identity))
        }
    }

    private inner class PlayersApiHandler : HttpHandler {
        override fun handle(exchange: HttpExchange) {
            if (!isAuthorized(exchange)) {
                writeJson(exchange, 401, mapOf("error" to "Unauthorized"))
                return
            }

            if (exchange.requestMethod != "GET") {
                writeJson(exchange, 405, mapOf("error" to "Only GET is supported"))
                return
            }

            val server = activeServer
            if (server == null) {
                writeJson(exchange, 503, mapOf("error" to "Minecraft server not available yet"))
                return
            }

            val subPath = exchange.requestURI.path.removePrefix("/api/players").trim('/')
            if (subPath.isBlank()) {
                writeJson(
                    exchange,
                    400,
                    mapOf(
                        "error" to "Missing player identifier",
                        "usage" to listOf(
                            "/api/players/{uuid-or-name}",
                            "/api/players/{uuid}/party",
                            "/api/players/{uuid}/pc",
                            "/api/players/{uuid}/pc/search?q=...",
                            "/api/players/{uuid}/pokedex",
                            "/api/players/{uuid}/progress"
                        )
                    )
                )
                return
            }

            val rawSegments = subPath.split('/').filter { it.isNotBlank() }
            val segments = rawSegments.map(::decodePathSegment)
            val queryParams = parseQueryParams(exchange.requestURI.rawQuery)
            val identity = resolveIdentity(server, segments.first())

            if (identity == null) {
                writeJson(
                    exchange,
                    404,
                    mapOf("error" to "Player not found", "lookup" to segments.first())
                )
                return
            }

            when {
                segments.size == 1 -> {
                    writeJson(exchange, 200, buildPlayerProfile(server, identity))
                }

                segments.size == 2 && segments[1].equals("party", ignoreCase = true) -> {
                    val party = CobblemonBridge.readParty(server, identity.uuid)
                    writeJson(
                        exchange,
                        200,
                        mapOf(
                            "player" to identityPayload(identity),
                            "partyCount" to party.size,
                            "party" to party
                        )
                    )
                }

                segments.size == 2 && segments[1].equals("pc", ignoreCase = true) -> {
                    val page = parsePositiveIntQueryParam(queryParams["page"], 1)
                    if (page == null) {
                        writeJson(exchange, 400, mapOf("error" to "Invalid query parameter: page (must be a positive integer)"))
                        return
                    }

                    val pageSize = parsePositiveIntQueryParam(queryParams["pageSize"], 5)
                    if (pageSize == null || pageSize > 50) {
                        writeJson(exchange, 400, mapOf("error" to "Invalid query parameter: pageSize (must be 1..50)"))
                        return
                    }

                    val pc = CobblemonBridge.readPc(server, identity.uuid)
                    val paginatedPc = paginatePcBoxes(pc, page, pageSize)
                    writeJson(exchange, 200, mapOf("player" to identityPayload(identity)) + paginatedPc)
                }

                segments.size == 3 && segments[1].equals("pc", ignoreCase = true) && segments[2].equals("search", ignoreCase = true) -> {
                    val query = parseQueryParams(exchange.requestURI.rawQuery)["q"]?.trim().orEmpty()
                    if (query.isBlank()) {
                        writeJson(exchange, 400, mapOf("error" to "Missing query parameter: q"))
                        return
                    }

                    val matches = CobblemonBridge.searchPc(server, identity.uuid, query)
                    writeJson(
                        exchange,
                        200,
                        mapOf(
                            "player" to identityPayload(identity),
                            "query" to query,
                            "matchCount" to matches.size,
                            "matches" to matches
                        )
                    )
                }

                segments.size == 2 && segments[1].equals("pokedex", ignoreCase = true) -> {
                    val pokedex = CobblemonBridge.readPokedex(identity.uuid)
                    writeJson(exchange, 200, mapOf("player" to identityPayload(identity)) + pokedex)
                }

                segments.size == 2 && segments[1].equals("progress", ignoreCase = true) -> {
                    val progress = CobblemonBridge.readProgress(identity.uuid)
                    val playtimeTicks = identity.player?.let { readPlaytimeTicks(it) } ?: 0
                    writeJson(
                        exchange,
                        200,
                        mapOf(
                            "player" to identityPayload(identity),
                            "playtime" to mapOf(
                                "ticks" to playtimeTicks,
                                "seconds" to (playtimeTicks / 20),
                                "formatted" to formatTicks(playtimeTicks)
                            )
                        ) + progress
                    )
                }

                else -> {
                    writeJson(exchange, 404, mapOf("error" to "Unknown endpoint", "path" to exchange.requestURI.path))
                }
            }
        }
    }

    private fun buildPlayerProfile(server: MinecraftServer, identity: PlayerIdentity): Map<String, Any?> {
        val onlinePlayer = identity.player
        val playtimeTicks = onlinePlayer?.let { readPlaytimeTicks(it) } ?: 0
        val party = CobblemonBridge.readParty(server, identity.uuid)
        val pc = CobblemonBridge.readPc(server, identity.uuid)
        val pokedex = CobblemonBridge.readPokedex(identity.uuid)
        val progress = CobblemonBridge.readProgress(identity.uuid)

        val playerMap = identityPayload(identity) + mapOf(
            "health" to onlinePlayer?.let { (it.health * 10.0).roundToInt() / 10.0 },
            "maxHealth" to onlinePlayer?.let { (it.maxHealth * 10.0).roundToInt() / 10.0 }
        )

        val pcPokemonCount = ((pc["boxes"] as? List<*>) ?: emptyList<Any>())
            .sumOf { box -> ((box as? Map<*, *>)?.get("pokemonCount") as? Number)?.toInt() ?: 0 }

        val pokedexSummary = (pokedex["summary"] as? Map<*, *>) ?: emptyMap<Any, Any>()

        return mapOf(
            "player" to playerMap,
            "playtime" to mapOf(
                "ticks" to playtimeTicks,
                "seconds" to (playtimeTicks / 20),
                "formatted" to formatTicks(playtimeTicks)
            ),
            "cobblemon" to mapOf(
                "loaded" to CobblemonBridge.isLoaded(),
                "partyCount" to party.size,
                "pcPokemonCount" to pcPokemonCount,
                "pokedexSeenCount" to ((pokedexSummary["seenCount"] as? Number)?.toInt() ?: 0),
                "pokedexCaughtCount" to ((pokedexSummary["caughtCount"] as? Number)?.toInt() ?: 0)
            ),
            "general" to (progress["general"] ?: emptyMap<String, Any?>()),
            "links" to mapOf(
                "party" to "/api/players/${identity.uuid}/party",
                "pc" to "/api/players/${identity.uuid}/pc",
                "pcSearch" to "/api/players/${identity.uuid}/pc/search?q=pikachu",
                "pokedex" to "/api/players/${identity.uuid}/pokedex",
                "progress" to "/api/players/${identity.uuid}/progress"
            )
        )
    }

    private fun identityPayload(identity: PlayerIdentity): Map<String, Any?> {
        return mapOf(
            "lookup" to identity.input,
            "uuid" to identity.uuid.toString(),
            "name" to identity.player?.gameProfile?.name,
            "online" to (identity.player != null)
        )
    }

    private fun resolveIdentity(server: MinecraftServer, uuidOrName: String): PlayerIdentity? {
        val normalized = uuidOrName.trim()
        if (normalized.isBlank()) {
            return null
        }

        parseUuid(normalized)?.let { parsed ->
            val player = server.playerList.getPlayer(parsed)
            return PlayerIdentity(parsed, normalized, player)
        }

        val player = server.playerList.players.firstOrNull {
            it.gameProfile.name.equals(normalized, ignoreCase = true)
        } ?: return null

        return PlayerIdentity(player.uuid, normalized, player)
    }

    private fun parseUuid(value: String): UUID? {
        return try {
            UUID.fromString(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private fun decodePathSegment(segment: String): String {
        return URLDecoder.decode(segment, StandardCharsets.UTF_8)
    }

    private fun parseQueryParams(rawQuery: String?): Map<String, String> {
        if (rawQuery.isNullOrBlank()) {
            return emptyMap()
        }

        val params = mutableMapOf<String, String>()
        rawQuery.split('&').forEach { pair ->
            if (pair.isBlank()) {
                return@forEach
            }

            val idx = pair.indexOf('=')
            if (idx < 0) {
                params[decodePathSegment(pair)] = ""
            } else {
                val key = decodePathSegment(pair.substring(0, idx))
                val value = decodePathSegment(pair.substring(idx + 1))
                params[key] = value
            }
        }
        return params
    }

    private fun parsePositiveIntQueryParam(rawValue: String?, defaultValue: Int): Int? {
        if (rawValue.isNullOrBlank()) {
            return defaultValue
        }

        val parsed = rawValue.toIntOrNull() ?: return null
        return if (parsed > 0) parsed else null
    }

    private fun paginatePcBoxes(pc: Map<String, Any?>, page: Int, pageSize: Int): Map<String, Any?> {
        val allBoxes = (pc["boxes"] as? List<*>) ?: emptyList<Any>()
        val totalBoxes = allBoxes.size
        val totalPages = if (totalBoxes == 0) 1 else ((totalBoxes + pageSize - 1) / pageSize)

        val startIndex = (page - 1) * pageSize
        val endExclusive = (startIndex + pageSize).coerceAtMost(totalBoxes)
        val pageBoxes = if (startIndex >= totalBoxes) {
            emptyList<Any?>()
        } else {
            allBoxes.subList(startIndex, endExclusive)
        }

        val pagePokemonCount = pageBoxes.sumOf { box ->
            ((box as? Map<*, *>)?.get("pokemonCount") as? Number)?.toInt() ?: 0
        }

        return mapOf(
            "boxCount" to totalBoxes,
            "pageBoxCount" to pageBoxes.size,
            "pagePokemonCount" to pagePokemonCount,
            "boxes" to pageBoxes,
            "pagination" to mapOf(
                "page" to page,
                "pageSize" to pageSize,
                "totalPages" to totalPages,
                "hasPrevious" to (page > 1),
                "hasNext" to (page < totalPages)
            )
        )
    }

    private fun isAuthorized(exchange: HttpExchange): Boolean {
        if (requiredToken.isBlank()) {
            return true
        }

        val token = exchange.requestHeaders.getFirst("X-Api-Token")
            ?: exchange.requestHeaders.getFirst("Authorization")
            ?: return false

        val normalized = if (token.startsWith("Bearer ", ignoreCase = true)) token.substring(7).trim() else token.trim()
        return normalized == requiredToken
    }

    private fun readPlaytimeTicks(player: ServerPlayer): Int {
        return try {
            player.server.playerList.getPlayerStats(player).getValue(Stats.CUSTOM.get(Stats.PLAY_TIME))
        } catch (e: Exception) {
            logger.warn("Unable to fetch playtime stats for {}", player.gameProfile.name, e)
            0
        }
    }

    private fun formatTicks(ticks: Int): String {
        val seconds = (ticks / 20).toLong()
        val duration = Duration.ofSeconds(seconds)
        val hours = duration.toHours()
        val minutes = duration.toMinutesPart()
        val remainingSeconds = duration.toSecondsPart()
        return String.format("%02dh %02dm %02ds", hours, minutes, remainingSeconds)
    }

    private fun writeJson(exchange: HttpExchange, statusCode: Int, payload: Any) {
        val bytes = gson.toJson(payload).toByteArray(StandardCharsets.UTF_8)
        exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
        exchange.sendResponseHeaders(statusCode, bytes.size.toLong())
        exchange.responseBody.use { output ->
            output.write(bytes)
        }
    }
}

private object CobblemonBridge {
    private val logger = LoggerFactory.getLogger("tamilcraft-discord-commands")

    private data class PcSlotRecord(
        val box: Int,
        val slot: Int,
        val rawPokemon: Any,
        val summary: Map<String, Any?>
    )

    fun isLoaded(): Boolean {
        return FabricLoader.getInstance().isModLoaded("cobblemon")
    }

    fun readParty(server: MinecraftServer, playerUuid: UUID): List<Map<String, Any?>> {
        if (!isLoaded()) {
            return emptyList()
        }

        return try {
            val storage = resolveStorage() ?: return emptyList()
            val partyStore = ReflectionHelpers.callTwoArgs(
                storage,
                listOf("getParty"),
                playerUuid,
                server.registryAccess()
            ) ?: return emptyList()

            ReflectionHelpers.extractEntries(partyStore)
                .mapNotNull { pokemon -> summarizePokemon(pokemon) }
        } catch (e: Exception) {
            logger.debug("Failed to read Cobblemon party for {}", playerUuid, e)
            emptyList()
        }
    }

    fun readPc(server: MinecraftServer, playerUuid: UUID): Map<String, Any?> {
        if (!isLoaded()) {
            return mapOf("boxes" to emptyList<Map<String, Any?>>(), "boxCount" to 0)
        }

        return try {
            val storage = resolveStorage() ?: return mapOf("boxes" to emptyList<Map<String, Any?>>(), "boxCount" to 0)
            val pcStore = ReflectionHelpers.callTwoArgs(
                storage,
                listOf("getPC"),
                playerUuid,
                server.registryAccess()
            ) ?: return mapOf("boxes" to emptyList<Map<String, Any?>>(), "boxCount" to 0)

            val boxesObj = ReflectionHelpers.readField(pcStore, "boxes")
                ?: ReflectionHelpers.callNoArg(pcStore, listOf("getBoxes", "boxes"))

            val boxes = ReflectionHelpers.extractEntries(boxesObj)
                .mapIndexed { index, box -> summarizePcBox(index, box) }

            mapOf(
                "boxCount" to boxes.size,
                "boxes" to boxes
            )
        } catch (e: Exception) {
            logger.debug("Failed to read Cobblemon PC for {}", playerUuid, e)
            mapOf("boxes" to emptyList<Map<String, Any?>>(), "boxCount" to 0)
        }
    }

    fun searchPc(server: MinecraftServer, playerUuid: UUID, query: String): List<Map<String, Any?>> {
        if (!isLoaded()) {
            return emptyList()
        }

        return try {
            val storage = resolveStorage() ?: return emptyList()
            val pcStore = ReflectionHelpers.callTwoArgs(
                storage,
                listOf("getPC"),
                playerUuid,
                server.registryAccess()
            ) ?: return emptyList()

            val allSlots = collectPcSlots(pcStore)
            val searchObject = createSearchObject(query)
            val normalizedQuery = query.lowercase(Locale.ROOT)

            allSlots.filter { slot ->
                val byCobbleSearch = searchObject?.let { search ->
                    ReflectionHelpers.extractBoolean(
                        ReflectionHelpers.callSingleArg(search, listOf("passes"), slot.rawPokemon)
                    )
                }

                if (byCobbleSearch != null) {
                    byCobbleSearch
                } else {
                    val species = (slot.summary["species"] as? String).orEmpty().lowercase(Locale.ROOT)
                    val nickname = (slot.summary["nickname"] as? String).orEmpty().lowercase(Locale.ROOT)
                    val shiny = slot.summary["shiny"] == true
                    species.contains(normalizedQuery)
                        || nickname.contains(normalizedQuery)
                        || (normalizedQuery == "shiny" && shiny)
                }
            }.map { slot ->
                mapOf(
                    "box" to slot.box,
                    "slot" to slot.slot,
                    "pokemon" to slot.summary
                )
            }
        } catch (e: Exception) {
            logger.debug("Failed to search Cobblemon PC for {}", playerUuid, e)
            emptyList()
        }
    }

    fun readPokedex(playerUuid: UUID): Map<String, Any?> {
        if (!isLoaded()) {
            return mapOf("summary" to emptyMap<String, Any>(), "entries" to emptyList<Map<String, Any?>>())
        }

        return try {
            val manager = resolvePlayerDataManager() ?: return mapOf("summary" to emptyMap<String, Any>(), "entries" to emptyList<Map<String, Any?>>())
            val pokedexData = ReflectionHelpers.callSingleArg(manager, listOf("getPokedexData"), playerUuid)
                ?: return mapOf("summary" to emptyMap<String, Any>(), "entries" to emptyList<Map<String, Any?>>())

            val recordsObj = ReflectionHelpers.readField(pokedexData, "speciesRecords")
                ?: ReflectionHelpers.callNoArg(pokedexData, listOf("getSpeciesRecords", "speciesRecords"))

            val recordsMap = ReflectionHelpers.asMap(recordsObj)
            var seenCount = 0
            var caughtCount = 0

            val entries = recordsMap.entries.map { (speciesId, recordObj) ->
                val knowledge = ReflectionHelpers.extractDisplayName(
                    ReflectionHelpers.callNoArg(recordObj, listOf("getKnowledge", "knowledge"))
                ) ?: "NONE"

                if (!knowledge.equals("NONE", ignoreCase = true)) {
                    seenCount += 1
                }
                if (knowledge.equals("CAUGHT", ignoreCase = true)) {
                    caughtCount += 1
                }

                val aspects = ReflectionHelpers.extractEntries(
                    ReflectionHelpers.callNoArg(recordObj, listOf("getAspects", "aspects"))
                        ?: ReflectionHelpers.readField(recordObj, "aspects")
                        ?: emptyList<String>()
                ).map { it.toString() }

                val formsRaw = ReflectionHelpers.readField(recordObj, "formRecords")
                    ?: ReflectionHelpers.callNoArg(recordObj, listOf("getFormRecords", "formRecords"))
                val formsMap = ReflectionHelpers.asMap(formsRaw)

                val forms = formsMap.entries.map { (formName, formRecord) ->
                    mapOf(
                        "form" to formName.toString(),
                        "knowledge" to (
                            ReflectionHelpers.extractDisplayName(
                                ReflectionHelpers.callNoArg(formRecord, listOf("getKnowledge", "knowledge"))
                            ) ?: "NONE"
                        ),
                        "genders" to ReflectionHelpers.extractEntries(
                            ReflectionHelpers.callNoArg(formRecord, listOf("getGenders", "genders"))
                                ?: emptyList<String>()
                        ).map { it.toString() },
                        "seenShinyStates" to ReflectionHelpers.extractEntries(
                            ReflectionHelpers.callNoArg(formRecord, listOf("getSeenShinyStates", "seenShinyStates"))
                                ?: emptyList<String>()
                        ).map { it.toString() }
                    )
                }

                mapOf(
                    "speciesId" to speciesId.toString(),
                    "knowledge" to knowledge,
                    "aspects" to aspects,
                    "forms" to forms
                )
            }

            mapOf(
                "summary" to mapOf(
                    "seenCount" to seenCount,
                    "caughtCount" to caughtCount,
                    "speciesRecordCount" to recordsMap.size
                ),
                "entries" to entries
            )
        } catch (e: Exception) {
            logger.debug("Failed to read Cobblemon pokedex for {}", playerUuid, e)
            mapOf("summary" to emptyMap<String, Any>(), "entries" to emptyList<Map<String, Any?>>())
        }
    }

    fun readProgress(playerUuid: UUID): Map<String, Any?> {
        if (!isLoaded()) {
            return mapOf("general" to emptyMap<String, Any>(), "advancement" to emptyMap<String, Any>())
        }

        return try {
            val manager = resolvePlayerDataManager() ?: return mapOf("general" to emptyMap<String, Any>(), "advancement" to emptyMap<String, Any>())
            val generalData = ReflectionHelpers.callSingleArg(manager, listOf("getGenericData"), playerUuid)
                ?: return mapOf("general" to emptyMap<String, Any>(), "advancement" to emptyMap<String, Any>())

            val keyItemsRaw = ReflectionHelpers.readField(generalData, "keyItems")
                ?: ReflectionHelpers.callNoArg(generalData, listOf("getKeyItems", "keyItems"))
            val keyItems = ReflectionHelpers.extractEntries(keyItemsRaw).map { it.toString() }

            val advancementData = ReflectionHelpers.readField(generalData, "advancementData")
                ?: ReflectionHelpers.callNoArg(generalData, listOf("getAdvancementData", "advancementData"))

            val advancement = mapOf(
                "totalCaptureCount" to readCounter(advancementData, "TotalCaptureCount"),
                "totalEggsCollected" to readCounter(advancementData, "TotalEggsCollected"),
                "totalEggsHatched" to readCounter(advancementData, "TotalEggsHatched"),
                "totalEvolvedCount" to readCounter(advancementData, "TotalEvolvedCount"),
                "totalBattleVictoryCount" to readCounter(advancementData, "TotalBattleVictoryCount"),
                "totalPvPBattleVictoryCount" to readCounter(advancementData, "TotalPvPBattleVictoryCount"),
                "totalPvWBattleVictoryCount" to readCounter(advancementData, "TotalPvWBattleVictoryCount"),
                "totalPvNBattleVictoryCount" to readCounter(advancementData, "TotalPvNBattleVictoryCount"),
                "totalShinyCaptureCount" to readCounter(advancementData, "TotalShinyCaptureCount"),
                "totalTradedCount" to readCounter(advancementData, "TotalTradedCount")
            )

            mapOf(
                "general" to mapOf(
                    "starterPrompted" to ReflectionHelpers.extractBoolean(
                        ReflectionHelpers.readField(generalData, "starterPrompted")
                            ?: ReflectionHelpers.callNoArg(generalData, listOf("getStarterPrompted", "isStarterPrompted"))
                    ),
                    "starterLocked" to ReflectionHelpers.extractBoolean(
                        ReflectionHelpers.readField(generalData, "starterLocked")
                            ?: ReflectionHelpers.callNoArg(generalData, listOf("getStarterLocked", "isStarterLocked"))
                    ),
                    "starterSelected" to ReflectionHelpers.extractBoolean(
                        ReflectionHelpers.readField(generalData, "starterSelected")
                            ?: ReflectionHelpers.callNoArg(generalData, listOf("getStarterSelected", "isStarterSelected"))
                    ),
                    "starterUUID" to (
                        ReflectionHelpers.readField(generalData, "starterUUID")
                            ?: ReflectionHelpers.callNoArg(generalData, listOf("getStarterUUID", "starterUUID"))
                    )?.toString(),
                    "partySelectTutorialDone" to ReflectionHelpers.extractBoolean(
                        ReflectionHelpers.readField(generalData, "partySelectTutorialDone")
                            ?: ReflectionHelpers.callNoArg(generalData, listOf("getPartySelectTutorialDone", "isPartySelectTutorialDone"))
                    ),
                    "battleTheme" to (
                        ReflectionHelpers.readField(generalData, "battleTheme")
                            ?: ReflectionHelpers.callNoArg(generalData, listOf("getBattleTheme", "battleTheme"))
                    )?.toString(),
                    "keyItemsCount" to keyItems.size,
                    "keyItems" to keyItems
                ),
                "advancement" to advancement
            )
        } catch (e: Exception) {
            logger.debug("Failed to read Cobblemon progression for {}", playerUuid, e)
            mapOf("general" to emptyMap<String, Any>(), "advancement" to emptyMap<String, Any>())
        }
    }

    private fun resolveStorage(): Any? {
        val cobblemonClass = Class.forName("com.cobblemon.mod.common.Cobblemon")
        val instance = ReflectionHelpers.readStaticField(cobblemonClass, "INSTANCE") ?: return null

        return ReflectionHelpers.callNoArg(
            instance,
            listOf("getStorage", "storage")
        )
    }

    private fun resolvePlayerDataManager(): Any? {
        val cobblemonClass = Class.forName("com.cobblemon.mod.common.Cobblemon")
        val instance = ReflectionHelpers.readStaticField(cobblemonClass, "INSTANCE") ?: return null

        return ReflectionHelpers.callNoArg(
            instance,
            listOf("getPlayerDataManager", "playerDataManager")
        )
    }

    private fun summarizePcBox(index: Int, box: Any): Map<String, Any?> {
        val slots = collectPcSlotsFromBox(index, box)
            .sortedWith(compareBy<PcSlotRecord>({ it.box }, { it.slot }))
            .map { slot ->
                mapOf(
                    "slot" to slot.slot,
                    "pokemon" to slot.summary
                )
            }

        val boxName = ReflectionHelpers.extractDisplayName(
            ReflectionHelpers.readField(box, "name")
                ?: ReflectionHelpers.callNoArg(box, listOf("getName", "name"))
        )
        val wallpaper = (
            ReflectionHelpers.readField(box, "wallpaper")
                ?: ReflectionHelpers.callNoArg(box, listOf("getWallpaper", "wallpaper"))
            )?.toString()

        return mapOf(
            "box" to (index + 1),
            "name" to boxName,
            "wallpaper" to wallpaper,
            "pokemonCount" to slots.size,
            "slots" to slots
        )
    }

    private fun collectPcSlots(pcStore: Any): List<PcSlotRecord> {
        val boxesObj = ReflectionHelpers.readField(pcStore, "boxes")
            ?: ReflectionHelpers.callNoArg(pcStore, listOf("getBoxes", "boxes"))
        val boxes = ReflectionHelpers.extractEntries(boxesObj)
        return boxes.flatMapIndexed { index, box -> collectPcSlotsFromBox(index, box) }
    }

    private fun collectPcSlotsFromBox(index: Int, box: Any): List<PcSlotRecord> {
        val slots = mutableListOf<PcSlotRecord>()

        val pokemonArray = ReflectionHelpers.readField(box, "pokemon")
        if (pokemonArray is Array<*>) {
            pokemonArray.forEachIndexed { slotIndex, pokemon ->
                if (pokemon != null) {
                    val summary = summarizePokemon(pokemon)
                    if (summary != null) {
                        slots.add(PcSlotRecord(index + 1, slotIndex + 1, pokemon, summary))
                    }
                }
            }
            return slots
        }

        val nonEmpty = ReflectionHelpers.callNoArg(box, listOf("getNonEmptySlots"))
        val slotMap = ReflectionHelpers.asMap(nonEmpty)
        if (slotMap.isNotEmpty()) {
            slotMap.entries.forEach { (slot, pokemon) ->
                val summary = summarizePokemon(pokemon) ?: return@forEach
                val slotNumber = (ReflectionHelpers.extractNumber(slot) ?: 0) + 1
                slots.add(PcSlotRecord(index + 1, slotNumber, pokemon, summary))
            }
            return slots
        }

        ReflectionHelpers.extractEntries(box).forEachIndexed { slotIndex, pokemon ->
            val summary = summarizePokemon(pokemon)
            if (summary != null) {
                slots.add(PcSlotRecord(index + 1, slotIndex + 1, pokemon, summary))
            }
        }

        return slots
    }

    private fun createSearchObject(query: String): Any? {
        return try {
            val searchClass = Class.forName("com.cobblemon.mod.common.api.storage.pc.search.Search")
            val companion = ReflectionHelpers.readStaticField(searchClass, "Companion") ?: return null
            ReflectionHelpers.callSingleArg(companion, listOf("of"), query)
        } catch (_: Exception) {
            null
        }
    }

    private fun readCounter(advancementData: Any?, suffix: String): Int {
        if (advancementData == null) {
            return 0
        }
        val getter = "get$suffix"
        val value = ReflectionHelpers.callNoArg(advancementData, listOf(getter))
        return ReflectionHelpers.extractNumber(value) ?: 0
    }

    private fun summarizePokemon(pokemon: Any): Map<String, Any?>? {
        val speciesObj = ReflectionHelpers.callNoArg(pokemon, listOf("getSpecies", "species"))
        val speciesName = ReflectionHelpers.extractDisplayName(speciesObj)
            ?: ReflectionHelpers.extractDisplayName(pokemon)
            ?: return null

        val level = ReflectionHelpers.extractNumber(
            ReflectionHelpers.callNoArg(pokemon, listOf("getLevel", "level"))
        )
        val shiny = ReflectionHelpers.extractBoolean(
            ReflectionHelpers.callNoArg(pokemon, listOf("isShiny", "getShiny", "shiny"))
        )
        val nickname = ReflectionHelpers.extractDisplayName(
            ReflectionHelpers.callNoArg(pokemon, listOf("getNickname", "nickname", "getDisplayName", "displayName"))
        )

        return mapOf(
            "species" to speciesName,
            "level" to level,
            "shiny" to shiny,
            "nickname" to nickname
        )
    }
}

private object ReflectionHelpers {
    fun readStaticField(clazz: Class<*>, fieldName: String): Any? {
        return try {
            val field = clazz.getField(fieldName)
            field.get(null)
        } catch (_: Exception) {
            try {
                val field = clazz.getDeclaredField(fieldName)
                field.isAccessible = true
                field.get(null)
            } catch (_: Exception) {
                null
            }
        }
    }

    fun readField(target: Any, fieldName: String): Any? {
        return try {
            val field = target.javaClass.getField(fieldName)
            field.get(target)
        } catch (_: Exception) {
            try {
                val field = target.javaClass.getDeclaredField(fieldName)
                field.isAccessible = true
                field.get(target)
            } catch (_: Exception) {
                null
            }
        }
    }

    fun callNoArg(target: Any, methodNames: List<String>): Any? {
        for (name in methodNames) {
            val value = call(target, name, emptyArray())
            if (value != null) {
                return value
            }
        }
        return null
    }

    fun callSingleArg(target: Any, methodNames: List<String>, arg: Any): Any? {
        for (name in methodNames) {
            val value = call(target, name, arrayOf(arg))
            if (value != null) {
                return value
            }
        }
        return null
    }

    fun callTwoArgs(target: Any, methodNames: List<String>, arg1: Any, arg2: Any): Any? {
        for (name in methodNames) {
            val value = call(target, name, arrayOf(arg1, arg2))
            if (value != null) {
                return value
            }
        }
        return null
    }

    private fun call(target: Any, methodName: String, args: Array<Any>): Any? {
        val isClassTarget = target is Class<*>
        val targetClass = if (isClassTarget) target else target.javaClass
        val methods = targetClass.methods.filter { method ->
            method.name == methodName && method.parameterCount == args.size
        }

        for (method in methods) {
            try {
                val invokeTarget = if (isClassTarget) null else target
                if (args.isEmpty()) {
                    return method.invoke(invokeTarget)
                }

                val compatible = method.parameterTypes.indices.all { index ->
                    isCompatible(method.parameterTypes[index], args[index].javaClass)
                }
                if (compatible) {
                    return method.invoke(invokeTarget, *args)
                }
            } catch (_: Exception) {
                continue
            }
        }

        return null
    }

    fun extractEntries(value: Any?): List<Any> {
        if (value == null) {
            return emptyList()
        }

        if (value is Iterable<*>) {
            return value.filterNotNull()
        }
        if (value is Array<*>) {
            return value.filterNotNull()
        }
        if (value is Iterator<*>) {
            return value.asSequence().filterNotNull().toList()
        }
        if (value is Map<*, *>) {
            return value.values.filterNotNull()
        }

        val fromMethod = callNoArg(value, listOf("toList", "toGappyList", "getSlots", "values", "all", "stream"))
        if (fromMethod != null && fromMethod !== value) {
            return extractEntries(fromMethod)
        }

        return emptyList()
    }

    fun extractNumber(value: Any?): Int? {
        return when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull()
            else -> null
        }
    }

    fun extractBoolean(value: Any?): Boolean? {
        return when (value) {
            is Boolean -> value
            is String -> value.toBooleanStrictOrNull()
            else -> null
        }
    }

    fun extractDisplayName(value: Any?): String? {
        if (value == null) {
            return null
        }

        if (value is CharSequence) {
            return value.toString().trim().takeIf { it.isNotBlank() }
        }

        val byMethod = callNoArg(value, listOf("getString", "getName", "name", "toString"))
        if (byMethod is CharSequence) {
            val text = byMethod.toString().trim()
            if (text.isNotBlank()) {
                return text
            }
        }

        return null
    }

    fun asMap(value: Any?): Map<Any, Any> {
        if (value !is Map<*, *>) {
            return emptyMap()
        }
        return value.entries
            .filter { it.key != null && it.value != null }
            .associate { entry -> entry.key!! to entry.value!! }
    }

    private fun isCompatible(parameterType: Class<*>, argumentType: Class<*>): Boolean {
        if (parameterType.isAssignableFrom(argumentType)) {
            return true
        }

        if (!parameterType.isPrimitive) {
            return false
        }

        return when (parameterType) {
            java.lang.Boolean.TYPE -> argumentType == java.lang.Boolean::class.java
            java.lang.Integer.TYPE -> argumentType == java.lang.Integer::class.java
            java.lang.Long.TYPE -> argumentType == java.lang.Long::class.java
            java.lang.Double.TYPE -> argumentType == java.lang.Double::class.java
            java.lang.Float.TYPE -> argumentType == java.lang.Float::class.java
            java.lang.Short.TYPE -> argumentType == java.lang.Short::class.java
            java.lang.Byte.TYPE -> argumentType == java.lang.Byte::class.java
            java.lang.Character.TYPE -> argumentType == java.lang.Character::class.java
            else -> false
        }
    }
}