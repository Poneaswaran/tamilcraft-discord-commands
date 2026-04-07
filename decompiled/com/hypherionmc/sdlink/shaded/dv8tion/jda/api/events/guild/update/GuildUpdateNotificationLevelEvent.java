/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateNotificationLevelEvent
extends GenericGuildUpdateEvent<Guild.NotificationLevel> {
    public static final String IDENTIFIER = "notification_level";

    public GuildUpdateNotificationLevelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Guild.NotificationLevel oldNotificationLevel) {
        super(api, responseNumber, guild, oldNotificationLevel, guild.getDefaultNotificationLevel(), IDENTIFIER);
    }

    @Nonnull
    public Guild.NotificationLevel getOldNotificationLevel() {
        return this.getOldValue();
    }

    @Nonnull
    public Guild.NotificationLevel getNewNotificationLevel() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Guild.NotificationLevel getOldValue() {
        return (Guild.NotificationLevel)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public Guild.NotificationLevel getNewValue() {
        return (Guild.NotificationLevel)((Object)super.getNewValue());
    }
}

