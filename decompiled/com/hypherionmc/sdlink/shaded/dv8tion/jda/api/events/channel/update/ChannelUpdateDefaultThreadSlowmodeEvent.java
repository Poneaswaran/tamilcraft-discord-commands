/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ChannelUpdateDefaultThreadSlowmodeEvent
extends GenericChannelUpdateEvent<Integer> {
    public static final ChannelField FIELD = ChannelField.DEFAULT_THREAD_SLOWMODE;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateDefaultThreadSlowmodeEvent(@Nonnull JDA api, long responseNumber, @Nonnull Channel channel, int oldValue, int newValue) {
        super(api, responseNumber, channel, FIELD, oldValue, newValue);
    }

    @Override
    @Nonnull
    public Integer getOldValue() {
        return (Integer)super.getOldValue();
    }

    @Override
    @Nonnull
    public Integer getNewValue() {
        return (Integer)super.getNewValue();
    }
}

