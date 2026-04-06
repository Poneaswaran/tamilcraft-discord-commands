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
            logger.info("Registering Discord Command: /${configCommand.name}")
            event.addCommand(DynamicSlashCommand(configCommand))
        }
    }
}