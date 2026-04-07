package tamilcraft.discordcommands

import com.hypherionmc.craterlib.core.event.annot.CraterEventListener
import com.hypherionmc.sdlink.api.events.SlashCommandRegistrationEvent

class DiscordEvents {
    
    @CraterEventListener
    fun onCommandRegister(event: SlashCommandRegistrationEvent) {
        val logger = org.slf4j.LoggerFactory.getLogger("tamilcraft-discord-commands")
        logger.info("SDLink fired SlashCommandRegistrationEvent! Registering ${DiscordCommandsMod.db.commands.size} custom commands...")

        // Register your dynamic commands here from config
        DiscordCommandsMod.db.commands.forEach { configCommand ->
            logger.info("Registering Discord Slash Command: /${configCommand.name}")
            event.addCommand(DynamicSlashCommand(configCommand))
        }
    }

    @CraterEventListener
    fun onBotReady(event: com.hypherionmc.sdlink.api.events.SDLinkReadyEvent) {
        val jda = com.hypherionmc.sdlink.core.discord.BotController.INSTANCE?.jda ?: return
        val logger = org.slf4j.LoggerFactory.getLogger("tamilcraft-discord-commands")
        
        jda.addEventListener(object : com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.ListenerAdapter() {
            override fun onMessageReceived(e: com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent) {
                if (e.author.isBot) return
                
                val msg = e.message.contentRaw.trim().lowercase()
                
                // Fetch the commands directly from database and match plain text
                val commands = DiscordCommandsMod.db.commands
                for (cmd in commands) {
                    // Use a regex with word boundaries "\b" so "skip" doesn't trigger "ip"
                    val regex = "\\b${cmd.name.lowercase()}\\b".toRegex()
                    
                    if (regex.containsMatchIn(msg)) {
                        logger.info("Triggered text command for '${cmd.name}' by ${e.author.name}")
                        e.channel.sendMessage(cmd.replyMessage).queue()
                        break
                    }
                }
            }
        })
    }
}