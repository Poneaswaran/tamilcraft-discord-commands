/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ChannelUpdateRegionEvent
extends GenericChannelUpdateEvent<Region> {
    public static final ChannelField FIELD = ChannelField.REGION;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateRegionEvent(@Nonnull JDA api, long responseNumber, Channel channel, Region oldValue, Region newValue) {
        super(api, responseNumber, channel, FIELD, oldValue, newValue);
    }
}

