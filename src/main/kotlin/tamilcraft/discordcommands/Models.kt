package tamilcraft.discordcommands

data class CustomCommandConfig(
    val name: String,
    val description: String,
    val replyMessage: String,
    val hasTargetOption: Boolean = true,
    val allowedChannelIds: List<String> = emptyList(),
    val restrictedChannelIds: List<String> = emptyList()
)

data class DiscordCommandsDatabase(
    val commands: List<CustomCommandConfig> = listOf(
        CustomCommandConfig(
            name = "ip",
            description = "Displays the Tamilcraft server IP",
            replyMessage = "The default IP is: **play.tamilcraft.in**",
            hasTargetOption = true
        )
    ),
    val maintenanceAdminRoleId: String = "YOUR_DISCORD_ROLE_ID_HERE",
    val maintenanceMessage: String = "The server is currently under maintenance. Please check Discord for updates!",
    val maintenanceOnReply: String = "✅ **Maintenance mode ENABLED!** Only Admins can join the server now.",
    val maintenanceOffReply: String = "✅ **Maintenance mode DISABLED!** The server is now open to verified players.",
    val maintenanceAllowedChannelIds: List<String> = emptyList(),
    val maintenanceRestrictedChannelIds: List<String> = emptyList(),
    val apiEnabled: Boolean = true,
    val apiHost: String = "0.0.0.0",
    val apiPort: Int = 8088,
    val apiAuthToken: String = ""
)
