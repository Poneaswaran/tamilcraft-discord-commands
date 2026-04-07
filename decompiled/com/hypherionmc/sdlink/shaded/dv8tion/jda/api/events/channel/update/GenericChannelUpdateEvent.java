/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.GenericChannelEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GenericChannelUpdateEvent<T>
extends GenericChannelEvent
implements UpdateEvent<Channel, T> {
    protected final ChannelField channelField;
    protected final T oldValue;
    protected final T newValue;

    public GenericChannelUpdateEvent(@Nonnull JDA api, long responseNumber, Channel channel, ChannelField channelField, T oldValue, T newValue) {
        super(api, responseNumber, channel);
        this.channelField = channelField;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.channelField.getFieldName();
    }

    @Override
    @Nonnull
    public Channel getEntity() {
        return this.getChannel();
    }

    @Override
    @Nullable
    public T getOldValue() {
        return this.oldValue;
    }

    @Override
    @Nullable
    public T getNewValue() {
        return this.newValue;
    }
}

