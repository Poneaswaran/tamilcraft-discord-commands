/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.managers;

import com.hypherionmc.sdlink.SDLinkConstants;
import com.hypherionmc.sdlink.api.messaging.Result;
import com.hypherionmc.sdlink.core.database.HiddenPlayers;
import com.hypherionmc.sdlink.core.managers.DatabaseManager;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.util.HashMap;
import java.util.LinkedHashMap;
import lombok.Generated;

public final class HiddenPlayersManager {
    public static final HiddenPlayersManager INSTANCE = new HiddenPlayersManager();
    private final HashMap<String, HiddenPlayers> hiddenPlayers = new LinkedHashMap<String, HiddenPlayers>();

    private HiddenPlayersManager() {
    }

    public void loadHiddenPlayers() {
        this.hiddenPlayers.clear();
        DatabaseManager.INSTANCE.getCollection(HiddenPlayers.class).forEach(p -> this.hiddenPlayers.put(p.getIdentifier(), (HiddenPlayers)p));
    }

    public Result hidePlayer(String identifier, String displayName, String type) {
        try {
            HiddenPlayers player = HiddenPlayers.of(identifier, displayName, type);
            DatabaseManager.INSTANCE.updateEntry(player);
            this.hiddenPlayers.put(identifier, player);
            return Result.success(SDText.translate("hiding.now_hidden", displayName));
        }
        catch (Exception e) {
            SDLinkConstants.LOGGER.error("Failed to hide player {}", (Object)displayName, (Object)e);
            return Result.error(SDText.translate("hiding.failed", e.getMessage()));
        }
    }

    public Result unhidePlayer(String identifier) {
        try {
            HiddenPlayers player = DatabaseManager.INSTANCE.findById(identifier, HiddenPlayers.class);
            if (player == null) {
                return Result.error(SDText.translate("hiding.not_hidden"));
            }
            this.hiddenPlayers.remove(identifier);
            DatabaseManager.INSTANCE.deleteEntry(player);
            return Result.success(SDText.translate("hiding.unhidden", player.getDisplayName()));
        }
        catch (Exception e) {
            SDLinkConstants.LOGGER.error("Failed to unhide player {}", (Object)identifier, (Object)e);
            return Result.error(SDText.translate("hiding.unhide_failed", e.getMessage()));
        }
    }

    public boolean isPlayerHidden(String identifier) {
        return this.hiddenPlayers.containsKey(identifier);
    }

    @Generated
    public HashMap<String, HiddenPlayers> getHiddenPlayers() {
        return this.hiddenPlayers;
    }
}

