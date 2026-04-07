/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildUpdateSafetyAlertsChannelEvent
extends GenericGuildUpdateEvent<TextChannel> {
    public static final String IDENTIFIER = "safety_alerts_channel";

    public GuildUpdateSafetyAlertsChannelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable TextChannel oldSafetyAlertsChannel) {
        super(api, responseNumber, guild, oldSafetyAlertsChannel, guild.getSafetyAlertsChannel(), IDENTIFIER);
    }

    @Nullable
    public TextChannel getOldSafetyAlertsChannel() {
        return (TextChannel)this.getOldValue();
    }

    @Nullable
    public TextChannel getNewSafetyAlertsChannel() {
        return (TextChannel)this.getNewValue();
    }
}

