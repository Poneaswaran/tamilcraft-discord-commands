# TamilCraft Discord Commands

A lightweight Fabric server-side mod designed to seamlessly link your Minecraft server to your Discord community using Simple Discord Link (SDLink). This mod allows you to easily create and manage custom Discord slash commands and text triggers without writing any additional code.

## Features

- **Dynamic Command Configuration**: Create and manage Discord commands entirely through a simple JSON configuration file.
- **Dual Triggers**: Commands added will automatically work as natively integrated **Slash Commands** (`/ip`), as well as **Text Triggers** (e.g., if a user types the word "ip" in a sentence, the bot will instantly reply).
- **Smart Text Matching**: Text triggers use word boundary detection, meaning the word "skip" won't accidentally trigger a command mapped to "ip".
- **Channel Restrictions**: Explicitly whitelist or blacklist specific Discord channels for each individual custom command.
- **Maintenance Mode**: Admins can lock and unlock the Minecraft server instantly from Discord with customizable text commands.
- **Hot-Reloading**: Automatically update your commands without restarting your Minecraft server using the built-in reload command.
- **Player HTTP API**: Query player profile, party, PC, PC search, pokedex, and progression data over a local HTTP endpoint.

## Installation

1. Copy the compiled `tamilcraft-discord-commands-1.0.0.jar` into your Minecraft server's `mods` folder.
2. Ensure you have the required dependencies:
   - **Fabric Loader** `0.18.5` (or compatible)
   - **Simple Discord Link (SDLink)**
3. Start the server once to generate the configuration file.

### ⚠️ Important Discord Bot Setup

Because this mod listens to regular chat messages in Discord (so users can trigger a command just by typing a word like "ip"), your Discord bot **must** have permission to read messages.

1. Go to the [Discord Developer Portal](https://discord.com/developers/applications/).
2. Select your Bot Application.
3. On the left sidebar, click on **Bot**.
4. Scroll down to the **Privileged Gateway Intents** section.
5. Turn **ON** the switch for **MESSAGE CONTENT INTENT**.
6. Save your changes.

*Note: Discord natively caches Slash "Global" commands for 1-2 hours. If you add a new command and want the slash menu to appear immediately without waiting, you can kick your bot from your server and re-invite it.*

## Configuration

All custom commands are stored in `config/tamilcraft-discord-commands.json`.

**Example `tamilcraft-discord-commands.json`:**
```json
{
  "commands": [
    {
      "name": "ip",
      "description": "Displays the Tamilcraft server IP",
      "replyMessage": "The default IP is: **play.tamilcraft.in**",
      "hasTargetOption": true,
      "allowedChannelIds": [],
      "restrictedChannelIds": []
    },
    {
      "name": "support",
      "description": "Get a link to the support channel",
      "replyMessage": "Need help? Open a ticket in <#123456789012345678>!",
      "hasTargetOption": false,
      "allowedChannelIds": [],
      "restrictedChannelIds": []
    }
  ],
  "maintenanceAdminRoleId": "YOUR_DISCORD_ROLE_ID_HERE",
  "maintenanceMessage": "The server is currently under maintenance. Please check Discord for updates!",
  "maintenanceOnReply": "✅ **Maintenance mode ENABLED!** Only Admins can join the server now.",
  "maintenanceOffReply": "✅ **Maintenance mode DISABLED!** The server is now open to verified players.",
  "maintenanceAllowedChannelIds": [],
  "maintenanceRestrictedChannelIds": [],
  "apiEnabled": true,
  "apiHost": "0.0.0.0",
  "apiPort": 8088,
  "apiAuthToken": ""
}
```

*   **`name`**: The root word that triggers the command. (e.g., typing "ip" triggers the first command).
*   **`description`**: The hover description displayed in Discord's slash command menu.
*   **`replyMessage`**: The actual text the bot will respond with.
*   **`hasTargetOption`**: Set to `true` if you want the slash command to optionally target another user (e.g., `/ip @user`).
*   **`allowedChannelIds`**: (Whitelist) If you add Discord Channel IDs cleanly wrapped in quotes (e.g., `["123", "456"]`), the command will ONLY work in those channels. Leave empty `[]` to allow anywhere.
*   **`restrictedChannelIds`**: (Blacklist) Add Discord Channel IDs here to ban the command from being used in those specific channels.
*   **`maintenanceAdminRoleId`**: The Discord ID of the Role allowed to run the maintenance toggle commands in Discord.
*   **`maintenanceMessage`**: The kick screen message displayed to non-Admins trying to join a locked server.
*   **`apiEnabled`**: Enables or disables the built-in HTTP API.
*   **`apiHost`**: Host to bind the API server to. Default is `0.0.0.0` (all interfaces).
*   **`apiPort`**: Port for the API server.
*   **`apiAuthToken`**: Optional token. If set, requests must include `X-Api-Token: <token>` or `Authorization: Bearer <token>`.

## HTTP API

The mod exposes a small HTTP API for live server data.

- `GET /api/health`: API health and whether a Minecraft server instance is attached.
- `GET /api/players/{uuid-or-name}`: Player profile summary (identity, playtime, Cobblemon counts, general flags).
- `GET /api/players/{uuid}/party`: Party data from Cobblemon `PokemonStoreManager` / `PlayerPartyStore`.
- `GET /api/players/{uuid}/pc?page=1&pageSize=5`: Paginated PC box data from Cobblemon `PCStore`.
- `GET /api/players/{uuid}/pc/search?q=...`: PC search results (uses Cobblemon `Search` when available).
- `GET /api/players/{uuid}/pokedex`: Pokedex summary and entries from Cobblemon `PokedexManager`.
- `GET /api/players/{uuid}/progress`: General flags/key items and advancement counters from Cobblemon player data.
- `GET /api/pokemon/players`: Lists players that match a Pokemon filter. Default filter is legendary Pokemon.
  - Optional query `type`:
    - Omit `type` (or use `type=legendary`) to list players with legendary Pokemon.
    - Use `type=<pokemon-type>` (e.g. `fire`, `water`, `dragon`) to filter by Pokemon elemental type.
    - Use `type=all` to include players with any Pokemon.

Legacy compatibility endpoint:

- `GET /api/player/{uuid-or-name}`: Alias to profile summary.

### Example requests

```bash
curl http://127.0.0.1:8088/api/health
curl http://127.0.0.1:8088/api/players/Steve
curl http://127.0.0.1:8088/api/players/00000000-0000-0000-0000-000000000000/party
curl http://127.0.0.1:8088/api/players/00000000-0000-0000-0000-000000000000/pc?page=2&pageSize=4
curl http://127.0.0.1:8088/api/players/00000000-0000-0000-0000-000000000000/pc/search?q=shiny
curl http://127.0.0.1:8088/api/pokemon/players
curl http://127.0.0.1:8088/api/pokemon/players?type=legendary
curl http://127.0.0.1:8088/api/pokemon/players?type=water
curl http://127.0.0.1:8088/api/pokemon/players?type=all
```

If `apiAuthToken` is configured:

```bash
curl -H "X-Api-Token: YOUR_TOKEN" http://127.0.0.1:8088/api/players/Steve
```

### Notes

- Name-based lookup supports online and offline players (if their profile is present in server cache).
- UUID-based lookup works for profile and Cobblemon-backed data where stored data exists.
- If Cobblemon is not loaded, Cobblemon-specific endpoints return empty data structures.
- PC pagination parameters: `page` is 1-based, `pageSize` default is 5, and `pageSize` max is 50.

## Maintenance Mode
With the mod running, anyone holding the configured `maintenanceAdminRoleId` role in Discord can type the following exact phrases into any readable Discord channel:
- `maintenance on`: Instantly locks the Minecraft server. Anyone trying to join who does not have OP permissions will be immediately kicked.
- `maintenance off`: Unlocks the Minecraft server to the public.

## Recommended SDLink Configurations
This mod is specifically designed to work seamlessly with **Simple Discord Link's (SDLink)** native integrations. Configure the following features in your `config/sdlink/simple-discord-link.toml` file to get the most out of your server:

### 1. Verification Self-Whitelist (Access Control)
You do not need an external whitelist plugin. SDLink natively connects one Discord Account to exactly one Minecraft account to stop alt-account trolling.
- Under `[accessControl]`, set `enabled = true`.
- Ensure `allowMultipleAccounts = false`.
Unverified players trying to join Minecraft will receive a 4-digit code. They can **Direct Message (DM)** your bot or send a message in your `#bot-commands` channel with `/verify 1234` to permanently link their accounts and skip the whitelist.

### 2. Disabling the Console view in Discord
Need to view the server console while away from the terminal?
- Under `[channelsAndWebhooks.channels]`, add your staff-only discord channel ID to `consoleChannelID`.
- Under `[chat]`, set `sendConsoleMessages = true`.
Your Minecraft console will now forward line-by-line exclusively to that Discord channel.

### 3. Cross-Platform Banning
If you ban a troll in Discord, they can automatically be banned on Minecraft, and vice-versa!
- Under `[accessControl]`, set `banPlayerOnDiscordBan = true`.
- Set `banMemberOnMinecraftBan = true` to enable dual syncing.

## In-Game Commands

**`/tc-discord reload`**
- **Permission Level**: 2 (OP)
- **Description**: Reloads the `tamilcraft-discord-commands.json` file from disk. Changes to `replyMessage` will take effect instantly for text triggers!

## Building from Source

To compile the mod yourself:
1. Clone the repository.
2. Ensure you have JDK 21 installed.
3. Run the Gradle build command:
```bash
./gradlew build
```
4. Find the compiled jar in `build/libs/`.
