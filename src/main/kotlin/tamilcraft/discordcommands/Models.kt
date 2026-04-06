package tamilcraft.discordcommands

data class CustomCommandConfig(
    val name: String,
    val description: String,
    val replyMessage: String,
    val hasTargetOption: Boolean = true
)

data class DiscordCommandsDatabase(
    val commands: List<CustomCommandConfig> = listOf(
        CustomCommandConfig(
            name = "ip",
            description = "Displays the Tamilcraft server IP",
            replyMessage = "The default IP is: **play.tamilcraft.in**",
            hasTargetOption = true
        )
    )
)
