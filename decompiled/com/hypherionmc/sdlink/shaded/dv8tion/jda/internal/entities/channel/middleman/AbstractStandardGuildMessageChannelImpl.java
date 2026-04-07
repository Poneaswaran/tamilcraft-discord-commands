/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractStandardGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.StandardGuildMessageChannelMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class AbstractStandardGuildMessageChannelImpl<T extends AbstractStandardGuildMessageChannelImpl<T>>
extends AbstractStandardGuildChannelImpl<T>
implements StandardGuildMessageChannelMixin<T> {
    protected String topic;
    protected boolean nsfw;
    protected long latestMessageId;
    protected int defaultThreadSlowmode;

    public AbstractStandardGuildMessageChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nullable
    public String getTopic() {
        return this.topic;
    }

    @Override
    public boolean isNSFW() {
        return this.nsfw;
    }

    @Override
    public long getLatestMessageIdLong() {
        return this.latestMessageId;
    }

    @Override
    public int getDefaultThreadSlowmode() {
        return this.defaultThreadSlowmode;
    }

    @Override
    public T setTopic(String topic) {
        this.topic = topic;
        return (T)this;
    }

    @Override
    public T setNSFW(boolean nsfw) {
        this.nsfw = nsfw;
        return (T)this;
    }

    @Override
    public T setLatestMessageIdLong(long latestMessageId) {
        this.latestMessageId = latestMessageId;
        return (T)this;
    }

    @Override
    public T setDefaultThreadSlowmode(int defaultThreadSlowmode) {
        this.defaultThreadSlowmode = defaultThreadSlowmode;
        return (T)this;
    }
}

