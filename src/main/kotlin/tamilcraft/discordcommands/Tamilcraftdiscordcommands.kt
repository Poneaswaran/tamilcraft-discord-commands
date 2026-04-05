package tamilcraft.discordcommands

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hypherionmc.craterlib.core.event.CraterEventBus
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory
import java.io.File

class DiscordCommandsMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("tamilcraft-discord-commands")
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile = File(FabricLoader.getInstance().configDir.toFile(), "tamilcraft-ip-config.json")

    // A companion object is Kotlin's way of making variables "static" or globally accessible
    companion object {
        var serverIp: String = "play.tamilcraft.in"
    }

    override fun onInitialize() {
        logger.info("Initializing Tamilcraft Discord Commands...")
        
        loadDatabase()
        
        // Register your custom Discord events to the CraterLib Event Bus!
        CraterEventBus.INSTANCE.registerEventListener(DiscordEvents())
    }

    private fun loadDatabase() {
        if (!configFile.exists()) {
            logger.info("No config database found, creating default...")
            val defaultConfig = IpDatabase("play.tamilcraft.in")
            configFile.writeText(gson.toJson(defaultConfig))
            serverIp = defaultConfig.ip
        } else {
            logger.info("Loading IP from database...")
            val data = gson.fromJson(configFile.readText(), IpDatabase::class.java)
            serverIp = data.ip
        }
    }
}

data class IpDatabase(val ip: String)