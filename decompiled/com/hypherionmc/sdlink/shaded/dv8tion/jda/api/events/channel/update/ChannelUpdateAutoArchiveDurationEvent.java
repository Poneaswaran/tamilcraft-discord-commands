/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ChannelUpdateAutoArchiveDurationEvent
extends GenericChannelUpdateEvent<ThreadChannel.AutoArchiveDuration> {
    public static final ChannelField FIELD = ChannelField.AUTO_ARCHIVE_DURATION;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateAutoArchiveDurationEvent(@Nonnull JDA api, long responseNumber, Channel channel, ThreadChannel.AutoArchiveDuration oldValue, ThreadChannel.AutoArchiveDuration newValue) {
        super(api, responseNumber, channel, FIELD, oldValue, newValue);
    }
}

