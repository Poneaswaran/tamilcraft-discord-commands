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

public class GuildUpdateIconEvent
extends GenericGuildUpdateEvent<String> {
    public static final String IDENTIFIER = "icon";

    public GuildUpdateIconEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable String oldIconId) {
        super(api, responseNumber, guild, oldIconId, guild.getIconId(), IDENTIFIER);
    }

    @Nullable
    public String getOldIconId() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getOldIconUrl() {
        return this.previous == null ? null : String.format("https://cdn.discordapp.com/icons/%s/%s.%s", this.guild.getId(), this.previous, ((String)this.previous).startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    public ImageProxy getOldIcon() {
        String oldIconUrl = this.getOldIconUrl();
        return oldIconUrl == null ? null : new ImageProxy(oldIconUrl);
    }

    @Nullable
    public String getNewIconId() {
        return (String)this.getNewValue();
    }

    @Nullable
    public String getNewIconUrl() {
        return this.next == null ? null : String.format("https://cdn.discordapp.com/icons/%s/%s.%s", this.guild.getId(), this.next, ((String)this.next).startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    public ImageProxy getNewIcon() {
        String newIconUrl = this.getNewIconUrl();
        return newIconUrl == null ? null : new ImageProxy(newIconUrl);
    }
}

