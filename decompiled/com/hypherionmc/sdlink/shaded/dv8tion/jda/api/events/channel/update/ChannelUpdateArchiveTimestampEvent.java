/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;

public class ChannelUpdateArchiveTimestampEvent
extends GenericChannelUpdateEvent<OffsetDateTime> {
    public static final ChannelField FIELD = ChannelField.ARCHIVED_TIMESTAMP;
    public static final String IDENTIFIER = FIELD.getFieldName();
    private final long oldTimestamp;
    private final long newTimestamp;

    public ChannelUpdateArchiveTimestampEvent(@Nonnull JDA api, long responseNumber, Channel channel, long oldValue, long newValue) {
        super(api, responseNumber, channel, FIELD, null, null);
        this.oldTimestamp = oldValue;
        this.newTimestamp = newValue;
    }

    @Override
    @Nonnull
    public OffsetDateTime getOldValue() {
        return Helpers.toOffset(this.oldTimestamp);
    }

    @Override
    @Nonnull
    public OffsetDateTime getNewValue() {
        return Helpers.toOffset(this.newTimestamp);
    }
}

