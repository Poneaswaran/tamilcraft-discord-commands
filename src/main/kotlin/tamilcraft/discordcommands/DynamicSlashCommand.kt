package tamilcraft.discordcommands

import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData

class DynamicSlashCommand(private val commandConfig: CustomCommandConfig) : SDLinkSlashCommand(true) {

    init {
        this.name = commandConfig.name
        this.help = commandConfig.description
        
        if (commandConfig.hasTargetOption) {
            this.options = listOf(
                OptionData(OptionType.USER, "target", "The user you want to target with this command", false)
            )
        }
    }

    override fun execute(event: SlashCommandEvent) {
        // Fetch latest config for this command to allow hot-reloading responses
        val latestConfig = DiscordCommandsMod.db.commands.find { it.name == this.name } 
        val configToUse = latestConfig ?: commandConfig
        
        if (configToUse.allowedChannelIds.isNotEmpty() && !configToUse.allowedChannelIds.contains(event.channel.id)) {
            event.reply("❌ You cannot use this command in this channel!").setEphemeral(true).queue()
            return
        }
        
        if (configToUse.restrictedChannelIds.contains(event.channel.id)) {
            event.reply("❌ You cannot use this command in this channel!").setEphemeral(true).queue()
            return
        }
        
        val replyRaw = configToUse.replyMessage
        
        val targetOption = event.getOption("target")
        val pingString = if (targetOption != null && targetOption.asUser != null) "${targetOption.asUser.asMention} " else ""
        
        event.reply("${pingString}${replyRaw}").queue()
    }
}
