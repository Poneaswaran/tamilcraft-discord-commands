# TamilCraft Discord Commands

A lightweight Fabric server-side mod designed to seamlessly link your Minecraft server to your Discord community using Simple Discord Link (SDLink). This mod allows you to easily create and manage custom Discord slash commands and text triggers without writing any additional code.

## Features

- **Dynamic Command Configuration**: Create and manage Discord commands entirely through a simple JSON configuration file.
- **Dual Triggers**: Commands added will automatically work as natively integrated **Slash Commands** (`/ip`), as well as **Text Triggers** (e.g., if a user types the word "ip" in a sentence, the bot will instantly reply).
- **Player HTTP API (v2)**: Rich player and server data exposed via HTTP.
- **SQLite Persistence**: Automatically caches player data (playtime, health, pokedex progress, pokemon lists) in a SQLite database to support **offline player profiles** and fast leaderboards.
- **TPS-Based Throttling**: The API automatically switches to database-only mode if the server TPS falls below 18, protecting server performance during lag.
- **Automatic Sync**: Periodically synchronizes all online player data and performs a full sync on server startup.
- **Rich Metadata**: API responses include high-quality player skin, avatar, and 3D body render URLs from `mc-heads.net`.
- **Maintenance Mode**: Admins can lock and unlock the Minecraft server instantly from Discord.
- **Hot-Reloading**: Automatically update your commands without restarting your Minecraft server.

## Installation

1. Copy the compiled `tamilcraft-discord-commands-v2.jar` into your Minecraft server's `mods` folder.
2. Ensure you have the required dependencies:
   - **Fabric Loader** `0.18.5` (or compatible)
   - **Simple Discord Link (SDLink)**
3. Start the server once to generate the configuration file.

## Configuration

All custom commands and API settings are stored in `config/tamilcraft-discord-commands.json`.

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
    }
  ],
  "maintenanceAdminRoleId": "YOUR_DISCORD_ROLE_ID_HERE",
  "maintenanceMessage": "The server is currently under maintenance.",
  "apiEnabled": true,
  "apiHost": "0.0.0.0",
  "apiPort": 8088,
  "apiAuthToken": "your-secret-token",
  "apiPokemonCacheEnabled": true,
  "apiPokemonCachePath": "pokemon_cache.db",
  "apiPokemonCacheSyncIntervalMinutes": 30
}
```

*   **`apiEnabled`**: Enables or disables the HTTP API.
*   **`apiPokemonCacheEnabled`**: Enables SQLite caching for offline data and leaderboards.
*   **`apiPokemonCachePath`**: Filename for the SQLite database (saved in server root).
*   **`apiPokemonCacheSyncIntervalMinutes`**: How often to automatically sync all player data.

## HTTP API

The mod exposes a robust HTTP API for live and cached server data.

### Player Endpoints
- `GET /api/players/{uuid-or-name}`: Player profile summary. Works for offline players if cached.
- `GET /api/players/{uuid}/party`: Party data (live or cached if low TPS).
- `GET /api/players/{uuid}/pc?page=1&pageSize=5`: Paginated PC box data.
- `GET /api/players/{uuid}/pc/search?q=...`: Search for specific Pokémon.
- `GET /api/players/{uuid}/pokedex`: Pokedex summary and entries.
- `GET /api/players/{uuid}/progress`: Advancement counters (wins, catches, etc.).

### Server & Stats Endpoints
- `GET /api/server/stats`: Live server dashboard (TPS, MOTD, online players, world time).
- `GET /api/leaderboard?stat=<type>&limit=10`: Ranked list of players.
  - `stat` options: `caughtCount`, `shinyCaughtCount`, `pvpWins`, `battleWins`, `eggsHatched`, `tradedCount`, `evolvedCount`, `pokedexCaught`, `pokedexSeen`, `playtimeTicks`, `totalPokemon`, `cobbledollars`, `deathCount`.
- `GET /api/pokemon/players?type=<type>`: Lists players that match a Pokemon filter.
- `GET /api/health`: Basic API health check.

## In-Game Commands

**`/tc-discord reload`**
- **Permission Level**: 2 (OP)
- **Description**: Reloads the JSON configuration from disk.

**`/tc-discord sync`**
- **Permission Level**: 2 (OP)
- **Description**: Manually triggers a full synchronization of all player data into the SQLite database.

## Building from Source

```bash
./gradlew build
```
Find the compiled jar in `build/libs/tamilcraft-discord-commands-v2.jar`.
