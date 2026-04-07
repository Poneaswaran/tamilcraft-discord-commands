/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public class ChannelUpdateFlagsEvent
extends GenericChannelUpdateEvent<EnumSet<ChannelFlag>> {
    public static final ChannelField FIELD = ChannelField.FLAGS;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateFlagsEvent(@Nonnull JDA api, long responseNumber, @Nonnull Channel channel, @Nonnull EnumSet<ChannelFlag> oldValue, @Nonnull EnumSet<ChannelFlag> newValue) {
        super(api, responseNumber, channel, FIELD, oldValue, newValue);
    }

    @Override
    @Nonnull
    public EnumSet<ChannelFlag> getOldValue() {
        return (EnumSet)super.getOldValue();
    }

    @Override
    @Nonnull
    public EnumSet<ChannelFlag> getNewValue() {
        return (EnumSet)super.getNewValue();
    }
}

