package tamilcraft.discordcommands

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hypherionmc.craterlib.core.event.CraterEventBus
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.slf4j.LoggerFactory
import java.io.File

class DiscordCommandsMod : ModInitializer, PreLaunchEntrypoint {
    private val logger = LoggerFactory.getLogger("tamilcraft-discord-commands")
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile = File(FabricLoader.getInstance().configDir.toFile(), "tamilcraft-discord-commands.json")
    private val playerApiServer = PlayerApiServer(gson)

    companion object {
        var db: DiscordCommandsDatabase = DiscordCommandsDatabase()
        var isMaintenanceMode: Boolean = false
    }

    override fun onPreLaunch() {
        logger.info("Running Tamilcraft Discord Commands PreLaunch...")
        loadDatabase()
        
        // Register your custom Discord events to the CraterLib Event Bus!
        CraterEventBus.INSTANCE.registerEventListener(DiscordEvents())
    }

    override fun onInitialize() {
        logger.info("Initializing Tamilcraft Discord Commands main...")

        playerApiServer.registerLifecycleHooks()
        applyApiConfig()

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, registryAccess, environment ->
            dispatcher.register(Commands.literal("tc-discord")
                .requires { source -> source.hasPermission(2) }
                .then(Commands.literal("reload")
                    .executes { context ->
                        loadDatabase()
                        applyApiConfig()
                        context.source.sendSuccess({ Component.literal("Tamilcraft Discord Commands config reloaded! Run /discord reload to apply completely new commands to Discord.") }, false)
                        1
                    }
                )
            )
        })

        // Maintenance Mode Join Interceptor
        net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.JOIN.register(
            net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.Join { handler, sender, server ->
                if (isMaintenanceMode) {
                    val isOp = server.playerList.isOp(handler.player.gameProfile)
                    if (!isOp) {
                        server.execute {
                            handler.disconnect(Component.literal(db.maintenanceMessage))
                        }
                    }
                }
            }
        )
    }

    private fun applyApiConfig() {
        if (!db.apiEnabled) {
            playerApiServer.stop()
            logger.info("Player API disabled in config")
            return
        }

        val configuredHost = db.apiHost.trim()
        val effectiveHost = if (configuredHost.isBlank() || configuredHost == "127.0.0.1" || configuredHost.equals("localhost", ignoreCase = true)) {
            "0.0.0.0"
        } else {
            configuredHost
        }

        playerApiServer.start(effectiveHost, db.apiPort, db.apiAuthToken)
    }

    private fun loadDatabase() {
        if (!configFile.exists()) {
            logger.info("No config database found, creating default...")
            val defaultConfig = DiscordCommandsDatabase()
            configFile.writeText(gson.toJson(defaultConfig))
            db = defaultConfig
        } else {
            logger.info("Loading commands from database...")
            try {
                db = gson.fromJson(configFile.readText(), DiscordCommandsDatabase::class.java)
            } catch (e: Exception) {
                logger.error("Failed to load discord commands config!", e)
            }
        }
    }
}