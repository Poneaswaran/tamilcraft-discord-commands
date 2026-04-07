/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildUpdateSplashEvent
extends GenericGuildUpdateEvent<String> {
    public static final String IDENTIFIER = "splash";

    public GuildUpdateSplashEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable String oldSplashId) {
        super(api, responseNumber, guild, oldSplashId, guild.getSplashId(), IDENTIFIER);
    }

    @Nullable
    public String getOldSplashId() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getOldSplashUrl() {
        return this.previous == null ? null : String.format("https://cdn.discordapp.com/splashes/%s/%s.png", this.guild.getId(), this.previous);
    }

    @Nullable
    public ImageProxy getOldSplash() {
        String oldSplashUrl = this.getOldSplashUrl();
        return oldSplashUrl == null ? null : new ImageProxy(oldSplashUrl);
    }

    @Nullable
    public String getNewSplashId() {
        return (String)this.getNewValue();
    }

    @Nullable
    public String getNewSplashUrl() {
        return this.next == null ? null : String.format("https://cdn.discordapp.com/splashes/%s/%s.png", this.guild.getId(), this.next);
    }

    @Nullable
    public ImageProxy getNewSplash() {
        String newSplashUrl = this.getNewSplashUrl();
        return newSplashUrl == null ? null : new ImageProxy(newSplashUrl);
    }
}

