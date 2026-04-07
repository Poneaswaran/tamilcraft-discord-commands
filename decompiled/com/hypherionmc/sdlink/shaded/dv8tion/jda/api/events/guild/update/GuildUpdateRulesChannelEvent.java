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

public class GuildUpdateRulesChannelEvent
extends GenericGuildUpdateEvent<TextChannel> {
    public static final String IDENTIFIER = "rules_channel";

    public GuildUpdateRulesChannelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable TextChannel oldRulesChannel) {
        super(api, responseNumber, guild, oldRulesChannel, guild.getRulesChannel(), IDENTIFIER);
    }

    @Nullable
    public TextChannel getOldRulesChannel() {
        return (TextChannel)this.getOldValue();
    }

    @Nullable
    public TextChannel getNewRulesChannel() {
        return (TextChannel)this.getNewValue();
    }
}

