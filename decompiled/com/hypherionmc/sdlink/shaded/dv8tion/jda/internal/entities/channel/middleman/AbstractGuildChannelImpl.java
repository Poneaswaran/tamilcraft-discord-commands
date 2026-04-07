/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.AbstractChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ChannelUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class AbstractGuildChannelImpl<T extends AbstractGuildChannelImpl<T>>
extends AbstractChannelImpl<T>
implements GuildChannelMixin<T> {
    protected GuildImpl guild;

    public AbstractGuildChannelImpl(long id, GuildImpl guild) {
        super(id, guild.getJDA());
        this.guild = guild;
    }

    @Override
    @Nonnull
    public GuildImpl getGuild() {
        return this.guild;
    }

    @Override
    public int compareTo(@Nonnull GuildChannel o) {
        return ChannelUtil.compare(this, o);
    }
}

