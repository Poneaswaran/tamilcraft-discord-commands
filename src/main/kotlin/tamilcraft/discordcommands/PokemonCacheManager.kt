package tamilcraft.discordcommands

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.server.MinecraftServer
import org.slf4j.LoggerFactory
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.util.*

data class CachedPlayerInfo(
    val name: String,
    val playtimeTicks: Int,
    val health: Double,
    val maxHealth: Double,
    val pokedexSeen: Int,
    val pokedexCaught: Int
)

class PokemonCacheManager(private val gson: Gson) {
    private val logger = LoggerFactory.getLogger("tamilcraft-discord-commands-cache")
    private var connection: Connection? = null

    @Synchronized
    fun initialize(dbPath: String) {
        try {
            val dbFile = File(dbPath)
            dbFile.parentFile?.let {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }

            val url = "jdbc:sqlite:$dbPath"
            connection = DriverManager.getConnection(url)
            
            val statement = connection?.createStatement()
            statement?.executeUpdate("""
                CREATE TABLE IF NOT EXISTS players (
                    uuid TEXT PRIMARY KEY,
                    name TEXT,
                    last_updated INTEGER,
                    playtime_ticks INTEGER DEFAULT 0,
                    health REAL DEFAULT 20.0,
                    max_health REAL DEFAULT 20.0,
                    pokedex_seen INTEGER DEFAULT 0,
                    pokedex_caught INTEGER DEFAULT 0,
                    caught_count INTEGER DEFAULT 0,
                    shiny_caught_count INTEGER DEFAULT 0,
                    pvp_wins INTEGER DEFAULT 0,
                    battle_wins INTEGER DEFAULT 0,
                    eggs_hatched INTEGER DEFAULT 0,
                    traded_count INTEGER DEFAULT 0,
                    evolved_count INTEGER DEFAULT 0,
                    total_pokemon INTEGER DEFAULT 0,
                    cobbledollars INTEGER DEFAULT 0,
                    deaths INTEGER DEFAULT 0
                )
            """)
            // Ensure columns exist for existing databases
            val columns = listOf(
                "playtime_ticks INTEGER DEFAULT 0",
                "health REAL DEFAULT 20.0",
                "max_health REAL DEFAULT 20.0",
                "pokedex_seen INTEGER DEFAULT 0",
                "pokedex_caught INTEGER DEFAULT 0",
                "caught_count INTEGER DEFAULT 0",
                "shiny_caught_count INTEGER DEFAULT 0",
                "pvp_wins INTEGER DEFAULT 0",
                "battle_wins INTEGER DEFAULT 0",
                "eggs_hatched INTEGER DEFAULT 0",
                "traded_count INTEGER DEFAULT 0",
                "evolved_count INTEGER DEFAULT 0",
                "total_pokemon INTEGER DEFAULT 0",
                "cobbledollars INTEGER DEFAULT 0",
                "deaths INTEGER DEFAULT 0"
            )
            columns.forEach { col ->
                try {
                    statement?.executeUpdate("ALTER TABLE players ADD COLUMN $col")
                } catch (_: Exception) {}
            }
            statement?.executeUpdate("""
                CREATE TABLE IF NOT EXISTS player_pokemon (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_uuid TEXT,
                    species TEXT,
                    types TEXT,
                    legendary BOOLEAN,
                    level INTEGER,
                    shiny BOOLEAN,
                    nickname TEXT,
                    FOREIGN KEY(player_uuid) REFERENCES players(uuid) ON DELETE CASCADE
                )
            """)
            statement?.close()
            logger.info("Pokemon cache database initialized at {}", dbPath)
        } catch (e: Exception) {
            logger.error("Failed to initialize pokemon cache database", e)
        }
    }

    @Synchronized
    fun close() {
        try {
            connection?.close()
            connection = null
        } catch (e: Exception) {
            logger.error("Failed to close database connection", e)
        }
    }

    @Synchronized
    fun updatePlayerCache(server: MinecraftServer, playerUuid: UUID, playerName: String?) {
        val conn = connection ?: return
        try {
            val onlinePlayer = server.playerList.getPlayer(playerUuid)
            
            // Get playtime
            val playtimeTicks = onlinePlayer?.let { 
                try {
                    it.server.playerList.getPlayerStats(it).getValue(net.minecraft.stats.Stats.CUSTOM.get(net.minecraft.stats.Stats.PLAY_TIME))
                } catch (_: Exception) { 0 }
            } ?: 0
            
            val health = onlinePlayer?.health?.toDouble() ?: 20.0
            val maxHealth = onlinePlayer?.maxHealth?.toDouble() ?: 20.0
            
            val deaths = onlinePlayer?.let { 
                try {
                    it.server.playerList.getPlayerStats(it).getValue(net.minecraft.stats.Stats.CUSTOM.get(net.minecraft.stats.Stats.DEATHS))
                } catch (_: Exception) { 0 }
            } ?: 0

            // Get pokedex summary
            val pokedex = CobblemonBridge.readPokedex(server, playerUuid)
            val summary = pokedex["summary"] as? Map<*, *>
            val seenCount = (summary?.get("seenCount") as? Number)?.toInt() ?: 0
            val caughtCountInPokedex = (summary?.get("caughtCount") as? Number)?.toInt() ?: 0

            // Get progress stats
            val progress = CobblemonBridge.readProgress(playerUuid)
            val advancement = progress["advancement"] as? Map<*, *>
            val caughtCount = (advancement?.get("totalCaptureCount") as? Number)?.toInt() ?: 0
            val shinyCaughtCount = (advancement?.get("totalShinyCaptureCount") as? Number)?.toInt() ?: 0
            val pvpWins = (advancement?.get("totalPvPBattleVictoryCount") as? Number)?.toInt() ?: 0
            val battleWins = (advancement?.get("totalBattleVictoryCount") as? Number)?.toInt() ?: 0
            val eggsHatched = (advancement?.get("totalEggsHatched") as? Number)?.toInt() ?: 0
            val tradedCount = (advancement?.get("totalTradedCount") as? Number)?.toInt() ?: 0
            val evolvedCount = (advancement?.get("totalEvolvedCount") as? Number)?.toInt() ?: 0

            // Get total pokemon
            val pokemonList = CobblemonBridge.collectAllPokemonSummaries(server, playerUuid)
            val totalPokemon = pokemonList.size
            
            // Get balance
            val cobbledollars = CobblemonBridge.readEconomy(playerUuid)

            val resolvedName = playerName 
                ?: onlinePlayer?.gameProfile?.name 
                ?: resolveNameFromCache(server, playerUuid) 
                ?: getCachedPlayerInfo(playerUuid)?.name
                ?: resolveNameFromUserCacheFile(playerUuid)
                ?: playerUuid.toString()

            conn.autoCommit = false
            
            // Update player info
            val playerStmt = conn.prepareStatement("""
                INSERT INTO players (uuid, name, last_updated, playtime_ticks, health, max_health, 
                    pokedex_seen, pokedex_caught, caught_count, shiny_caught_count, 
                    pvp_wins, battle_wins, eggs_hatched, traded_count, evolved_count, 
                    total_pokemon, cobbledollars, deaths) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT(uuid) DO UPDATE SET 
                    name = CASE WHEN excluded.name LIKE '%-%-%-%-%' AND players.name IS NOT NULL AND players.name NOT LIKE '%-%-%-%-%' THEN players.name ELSE excluded.name END,
                    last_updated = excluded.last_updated,
                    playtime_ticks = CASE WHEN excluded.playtime_ticks >= 0 THEN excluded.playtime_ticks ELSE players.playtime_ticks END,
                    health = CASE WHEN excluded.playtime_ticks >= 0 THEN excluded.health ELSE players.health END,
                    max_health = CASE WHEN excluded.playtime_ticks >= 0 THEN excluded.max_health ELSE players.max_health END,
                    pokedex_seen = excluded.pokedex_seen,
                    pokedex_caught = excluded.pokedex_caught,
                    caught_count = excluded.caught_count,
                    shiny_caught_count = excluded.shiny_caught_count,
                    pvp_wins = excluded.pvp_wins,
                    battle_wins = excluded.battle_wins,
                    eggs_hatched = excluded.eggs_hatched,
                    traded_count = excluded.traded_count,
                    evolved_count = excluded.evolved_count,
                    total_pokemon = excluded.total_pokemon,
                    cobbledollars = excluded.cobbledollars,
                    deaths = excluded.deaths
            """)
            playerStmt.setString(1, playerUuid.toString())
            playerStmt.setString(2, resolvedName)
            playerStmt.setLong(3, System.currentTimeMillis())
            playerStmt.setInt(4, if (onlinePlayer != null) playtimeTicks else -1)
            playerStmt.setDouble(5, if (onlinePlayer != null) health else -1.0)
            playerStmt.setDouble(6, if (onlinePlayer != null) maxHealth else -1.0)
            playerStmt.setInt(7, seenCount)
            playerStmt.setInt(8, caughtCountInPokedex)
            playerStmt.setInt(9, caughtCount)
            playerStmt.setInt(10, shinyCaughtCount)
            playerStmt.setInt(11, pvpWins)
            playerStmt.setInt(12, battleWins)
            playerStmt.setInt(13, eggsHatched)
            playerStmt.setInt(14, tradedCount)
            playerStmt.setInt(15, evolvedCount)
            playerStmt.setInt(16, totalPokemon)
            playerStmt.setLong(17, cobbledollars)
            playerStmt.setInt(18, deaths)
            playerStmt.executeUpdate()
            playerStmt.close()

            // Clear old pokemon for this player
            val deleteStmt = conn.prepareStatement("DELETE FROM player_pokemon WHERE player_uuid = ?")
            deleteStmt.setString(1, playerUuid.toString())
            deleteStmt.executeUpdate()
            deleteStmt.close()
            
            // Insert new pokemon
            val insertStmt = conn.prepareStatement("""
                INSERT INTO player_pokemon (player_uuid, species, types, legendary, level, shiny, nickname)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """)
            
            for (pokemon in pokemonList) {
                insertStmt.setString(1, playerUuid.toString())
                insertStmt.setString(2, pokemon["species"]?.toString() ?: "Unknown")
                
                @Suppress("UNCHECKED_CAST")
                val types = (pokemon["types"] as? List<String>)?.joinToString(",") ?: ""
                insertStmt.setString(3, types)
                
                insertStmt.setBoolean(4, pokemon["legendary"] == true)
                insertStmt.setInt(5, (pokemon["level"] as? Number)?.toInt() ?: 1)
                insertStmt.setBoolean(6, pokemon["shiny"] == true)
                insertStmt.setString(7, pokemon["nickname"]?.toString())
                insertStmt.addBatch()
            }
            insertStmt.executeBatch()
            insertStmt.close()
            
            conn.commit()
            conn.autoCommit = true
        } catch (e: Exception) {
            logger.error("Failed to update cache for player {}", playerUuid, e)
            try { conn.rollback() } catch (_: Exception) {}
        }
    }

    @Synchronized
    fun searchPlayers(mode: String, normalizedType: String): List<Map<String, Any?>> {
        val conn = connection ?: return emptyList()
        val results = mutableMapOf<String, MutableList<Map<String, Any?>>>()
        val playerNames = mutableMapOf<String, String>()

        try {
            val query = when (mode) {
                "legendary" -> "SELECT p.uuid, p.name, pk.* FROM players p JOIN player_pokemon pk ON p.uuid = pk.player_uuid WHERE pk.legendary = 1"
                "all" -> "SELECT p.uuid, p.name, pk.* FROM players p JOIN player_pokemon pk ON p.uuid = pk.player_uuid"
                else -> "SELECT p.uuid, p.name, pk.* FROM players p JOIN player_pokemon pk ON p.uuid = pk.player_uuid WHERE pk.types LIKE ?"
            }

            val stmt = conn.prepareStatement(query)
            if (mode == "type") {
                stmt.setString(1, "%$normalizedType%")
            }

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val uuid = rs.getString("uuid")
                val name = rs.getString("name")
                playerNames[uuid] = name

                val pokemon = mapOf(
                    "species" to rs.getString("species"),
                    "types" to rs.getString("types").split(",").filter { it.isNotBlank() },
                    "legendary" to rs.getBoolean("legendary"),
                    "level" to rs.getInt("level"),
                    "shiny" to rs.getBoolean("shiny"),
                    "nickname" to rs.getString("nickname")
                )
                
                results.getOrPut(uuid) { mutableListOf() }.add(pokemon)
            }
            rs.close()
            stmt.close()
        } catch (e: Exception) {
            logger.error("Failed to search players in cache", e)
        }

        return results.map { (uuid, pokemonList) ->
            mapOf(
                "player" to mapOf(
                    "uuid" to uuid,
                    "name" to playerNames[uuid],
                    "online" to false, // We don't know online status from DB easily, but we could check server if needed
                    "skinUrl" to "https://mc-heads.net/skin/$uuid",
                    "avatarUrl" to "https://mc-heads.net/avatar/$uuid/64",
                    "renderUrl" to "https://mc-heads.net/body/$uuid/150"
                ),
                "matchCount" to pokemonList.size,
                "pokemon" to pokemonList
            )
        }
    }

    @Synchronized
    fun syncAll(server: MinecraftServer, uuids: Set<UUID>) {
        logger.info("Starting full pokemon cache sync for {} players...", uuids.size)
        uuids.forEach { uuid ->
            // Try to get player name from cache or online
            val onlinePlayer = server.playerList.getPlayer(uuid)
            val name = onlinePlayer?.gameProfile?.name
            updatePlayerCache(server, uuid, name)
        }
        logger.info("Full pokemon cache sync complete.")
    }

    @Synchronized
    fun getCachedPokemon(playerUuid: UUID): List<Map<String, Any?>> {
        val conn = connection ?: return emptyList()
        val pokemonList = mutableListOf<Map<String, Any?>>()
        try {
            val stmt = conn.prepareStatement("SELECT * FROM player_pokemon WHERE player_uuid = ?")
            stmt.setString(1, playerUuid.toString())
            val rs = stmt.executeQuery()
            while (rs.next()) {
                pokemonList.add(mapOf(
                    "species" to rs.getString("species"),
                    "types" to rs.getString("types").split(",").filter { it.isNotBlank() },
                    "legendary" to rs.getBoolean("legendary"),
                    "level" to rs.getInt("level"),
                    "shiny" to rs.getBoolean("shiny"),
                    "nickname" to rs.getString("nickname")
                ))
            }
            rs.close()
            stmt.close()
        } catch (e: Exception) {
            logger.error("Failed to get cached pokemon for {}", playerUuid, e)
        }
        return pokemonList
    }

    @Synchronized
    fun getCachedPlayerInfo(playerUuid: UUID): CachedPlayerInfo? {
        val conn = connection ?: return null
        return try {
            val stmt = conn.prepareStatement("SELECT * FROM players WHERE uuid = ?")
            stmt.setString(1, playerUuid.toString())
            val rs = stmt.executeQuery()
            if (rs.next()) {
                val info = CachedPlayerInfo(
                    name = rs.getString("name"),
                    playtimeTicks = rs.getInt("playtime_ticks"),
                    health = rs.getDouble("health"),
                    maxHealth = rs.getDouble("max_health"),
                    pokedexSeen = rs.getInt("pokedex_seen"),
                    pokedexCaught = rs.getInt("pokedex_caught")
                )
                rs.close()
                stmt.close()
                info
            } else {
                rs.close()
                stmt.close()
                null
            }
        } catch (e: Exception) {
            logger.error("Failed to get cached player info for {}", playerUuid, e)
            null
        }
    }
    @Synchronized
    fun getLeaderboard(stat: String, limit: Int): List<Map<String, Any?>> {
        val conn = connection ?: return emptyList()
        val column = when (stat.lowercase(Locale.ROOT)) {
            "caughtcount", "caught_count" -> "caught_count"
            "shinycaughtcount", "shiny_caught_count" -> "shiny_caught_count"
            "pvpwins", "pvp_wins" -> "pvp_wins"
            "battlewins", "battle_wins" -> "battle_wins"
            "eggshatched", "eggs_hatched" -> "eggs_hatched"
            "tradedcount", "traded_count" -> "traded_count"
            "evolvedcount", "evolved_count" -> "evolved_count"
            "pokedexcaught", "pokedex_caught" -> "pokedex_caught"
            "pokedexseen", "pokedex_seen" -> "pokedex_seen"
            "playtimeticks", "playtime_ticks" -> "playtime_ticks"
            "totalpokemon", "total_pokemon" -> "total_pokemon"
            "cobbledollars" -> "cobbledollars"
            "deathcount", "deaths" -> "deaths"
            else -> return emptyList()
        }

        val list = mutableListOf<Map<String, Any?>>()
        try {
            val stmt = conn.prepareStatement("SELECT uuid, name, $column FROM players ORDER BY $column DESC LIMIT ?")
            stmt.setInt(1, limit)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val uuid = rs.getString("uuid")
                list.add(mapOf(
                    "player" to mapOf(
                        "uuid" to uuid,
                        "name" to rs.getString("name"),
                        "skinUrl" to "https://mc-heads.net/skin/$uuid",
                        "avatarUrl" to "https://mc-heads.net/avatar/$uuid/64",
                        "renderUrl" to "https://mc-heads.net/body/$uuid/150"
                    ),
                    "score" to rs.getLong(column)
                ))
            }
            rs.close()
            stmt.close()
        } catch (e: Exception) {
            logger.error("Failed to fetch leaderboard for {}", stat, e)
        }
        return list
    }

    private fun resolveNameFromCache(server: MinecraftServer, playerUuid: UUID): String? {
        val profileCache = ReflectionHelpers.readField(server, "profileCache")
            ?: ReflectionHelpers.callNoArg(server, listOf("getProfileCache", "profileCache"))
            ?: return null

        val result = ReflectionHelpers.callSingleArg(profileCache, listOf("get"), playerUuid) ?: return null
        val profile = ReflectionHelpers.extractOptionalValue(result) ?: result
        val rawName = ReflectionHelpers.readField(profile, "name")
            ?: ReflectionHelpers.callNoArg(profile, listOf("getName", "name"))
            ?: return null
        return rawName.toString().trim().ifBlank { null }
    }

    private fun resolveNameFromUserCacheFile(playerUuid: UUID): String? {
        try {
            val userCacheFile = File("usercache.json")
            if (!userCacheFile.exists()) return null
            val json = userCacheFile.readText()
            val entries = gson.fromJson<List<Map<String, Any>>>(json, object : TypeToken<List<Map<String, Any>>>() {}.type)
            val uuidStr = playerUuid.toString()
            return entries.find { it["uuid"] == uuidStr }?.get("name")?.toString()
        } catch (_: Exception) {
            return null
        }
    }
}
