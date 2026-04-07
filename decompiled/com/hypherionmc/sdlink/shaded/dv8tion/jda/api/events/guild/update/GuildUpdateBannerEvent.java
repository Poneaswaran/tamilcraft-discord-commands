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

public class GuildUpdateBannerEvent
extends GenericGuildUpdateEvent<String> {
    public static final String IDENTIFIER = "banner";

    public GuildUpdateBannerEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable String previous) {
        super(api, responseNumber, guild, previous, guild.getBannerId(), IDENTIFIER);
    }

    @Nullable
    public String getNewBannerId() {
        return (String)this.getNewValue();
    }

    @Nullable
    public String getNewBannerUrl() {
        return this.next == null ? null : String.format("https://cdn.discordapp.com/banners/%s/%s.%s", this.guild.getId(), this.next, ((String)this.next).startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    public ImageProxy getNewBanner() {
        String newBannerUrl = this.getNewBannerUrl();
        return newBannerUrl == null ? null : new ImageProxy(newBannerUrl);
    }

    @Nullable
    public String getOldBannerId() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getOldBannerUrl() {
        return this.previous == null ? null : String.format("https://cdn.discordapp.com/banners/%s/%s.%s", this.guild.getId(), this.previous, ((String)this.previous).startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    public ImageProxy getOldBanner() {
        String oldBannerUrl = this.getOldBannerUrl();
        return oldBannerUrl == null ? null : new ImageProxy(oldBannerUrl);
    }
}

