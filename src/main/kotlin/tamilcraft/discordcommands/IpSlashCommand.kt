package tamilcraft.discordcommands

import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class IpSlashCommand : SDLinkSlashCommand(true) {

    init {
        this.name = "ip"
        this.help = "Displays the Tamilcraft server IP and optionally pings a user"
        
        // This creates the optional user parameter in Discord
        this.options = listOf(
            OptionData(OptionType.USER, "target", "The user you want to ping with the IP", false)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        // 1. Check if the command sender selected a user to ping
        val targetOption = event.getOption("target")
        
        // 2. Format the ping (if a user was selected, create the @mention string)
        val pingString = if (targetOption != null) "${targetOption.asMention} " else ""
        
        // 3. Grab the live IP from your main mod class
        val currentIp = DiscordCommandsMod.serverIp
        
        // 4. Send the reply back to the Discord channel
        event.reply("${pingString}The default IP is: **$currentIp**").queue()
    }
}