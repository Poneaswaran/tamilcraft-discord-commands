/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile
 *  lombok.Generated
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.api.accounts;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageIgnoreConfig;
import com.hypherionmc.sdlink.core.services.SDLinkPlatform;
import com.hypherionmc.sdlink.util.SDLinkChatUtils;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;

public final class DiscordAuthor {
    private String displayName;
    private String avatar;
    private final boolean isServer;
    private String username;
    private String uuid;
    private CraterGameProfile profile;
    String realPlayerAvatar = "";
    String realPlayerName = "";
    private int color = 0x1FFFFFFF;

    private DiscordAuthor(String displayName, String avatar, String username, boolean isServer, String uuid) {
        this.avatar = avatar;
        this.username = username;
        this.isServer = isServer;
        this.uuid = uuid;
        this.displayName = displayName;
        this.profile = null;
        this.fixDisplayName(displayName);
    }

    private void fixDisplayName(String displayName) {
        this.displayName = this.displayName.replace("_", "\\_");
        this.displayName = SDLinkChatUtils.applyFiltering(this.displayName, i -> i.appliesTo.appliesToUsername((MessageIgnoreConfig.Ignore)i));
        if (this.displayName == null || this.displayName.isEmpty()) {
            this.displayName = displayName;
            this.displayName = this.displayName.replace("_", "\\_");
        }
    }

    public static DiscordAuthor of(String displayName, String uuid, String username) {
        return new DiscordAuthor(displayName, SDLinkConfig.INSTANCE.chatConfig.playerAvatarType.resolve(SDLinkPlatform.minecraftHelper.isOnlineMode() ? uuid : username), username, false, uuid);
    }

    public static DiscordAuthor getServer() {
        return new DiscordAuthor(SDLinkConfig.INSTANCE.channelsAndWebhooks.serverName, SDLinkConfig.INSTANCE.channelsAndWebhooks.serverAvatar, "server", true, "").setPlayerName(SDLinkConfig.INSTANCE.channelsAndWebhooks.serverName);
    }

    public static DiscordAuthor of(String displayName, String avatar, String username, boolean server) {
        return new DiscordAuthor(displayName, avatar, username, server, username);
    }

    public DiscordAuthor setPlayerAvatar(String usr, String userid) {
        this.realPlayerAvatar = SDLinkConfig.INSTANCE.chatConfig.playerAvatarType.resolve(SDLinkPlatform.minecraftHelper.isOnlineMode() ? userid : usr);
        return this;
    }

    public DiscordAuthor setPlayerName(String name) {
        this.realPlayerName = name;
        return this;
    }

    public void overrideData(String name, String avatar) {
        this.displayName = name;
        this.avatar = avatar;
        this.realPlayerAvatar = avatar;
        this.fixDisplayName(name);
    }

    public void overrideData(String name) {
        this.displayName = name;
        this.fixDisplayName(name);
    }

    public DiscordAuthor setGameProfile(@Nullable CraterGameProfile profile) {
        this.profile = profile;
        if (profile != null) {
            this.username = profile.getName();
            this.uuid = profile.getId().toString();
        }
        return this;
    }

    @Generated
    public String getDisplayName() {
        return this.displayName;
    }

    @Generated
    public String getAvatar() {
        return this.avatar;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getUuid() {
        return this.uuid;
    }

    @Generated
    public CraterGameProfile getProfile() {
        return this.profile;
    }

    @Generated
    public String getRealPlayerAvatar() {
        return this.realPlayerAvatar;
    }

    @Generated
    public String getRealPlayerName() {
        return this.realPlayerName;
    }

    @Generated
    public int getColor() {
        return this.color;
    }

    @Generated
    public void setColor(int color) {
        this.color = color;
    }
}

