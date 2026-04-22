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

class PokemonCacheManager(private val gson: Gson) {
    private val logger = LoggerFactory.getLogger("tamilcraft-discord-commands-cache")
    private var connection: Connection? = null

    @Synchronized
    fun initialize(dbPath: String) {
        try {
            val dbFile = File(dbPath)
            if (!dbFile.parentFile.exists()) {
                dbFile.parentFile.mkdirs()
            }

            val url = "jdbc:sqlite:$dbPath"
            connection = DriverManager.getConnection(url)
            
            val statement = connection?.createStatement()
            statement?.executeUpdate("""
                CREATE TABLE IF NOT EXISTS players (
                    uuid TEXT PRIMARY KEY,
                    name TEXT,
                    last_updated INTEGER
                )
            """)
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
            conn.autoCommit = false
            
            // Update player info
            val playerStmt = conn.prepareStatement("INSERT OR REPLACE INTO players (uuid, name, last_updated) VALUES (?, ?, ?)")
            playerStmt.setString(1, playerUuid.toString())
            playerStmt.setString(2, playerName ?: playerUuid.toString())
            playerStmt.setLong(3, System.currentTimeMillis())
            playerStmt.executeUpdate()
            playerStmt.close()

            // Clear old pokemon for this player
            val deleteStmt = conn.prepareStatement("DELETE FROM player_pokemon WHERE player_uuid = ?")
            deleteStmt.setString(1, playerUuid.toString())
            deleteStmt.executeUpdate()
            deleteStmt.close()

            // Fetch current pokemon from Cobblemon
            val pokemonList = CobblemonBridge.collectAllPokemonSummaries(server, playerUuid)
            
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
}
