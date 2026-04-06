package tamilcraft.discordcommands

import com.hypherionmc.craterlib.core.event.annot.CraterEventListener
import com.hypherionmc.sdlink.api.events.SlashCommandRegistrationEvent

class DiscordEvents {
    
    @CraterEventListener
    fun onCommandRegister(event: SlashCommandRegistrationEvent) {
        // Register your dynamic commands here from config
        DiscordCommandsMod.db.commands.forEach { configCommand ->
            event.addCommand(DynamicSlashCommand(configCommand))
        }
    }
}