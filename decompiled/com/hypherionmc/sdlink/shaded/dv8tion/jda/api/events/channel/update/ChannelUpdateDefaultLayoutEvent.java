/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ChannelUpdateDefaultLayoutEvent
extends GenericChannelUpdateEvent<ForumChannel.Layout> {
    public static final ChannelField FIELD = ChannelField.DEFAULT_FORUM_LAYOUT;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateDefaultLayoutEvent(@Nonnull JDA api, long responseNumber, @Nonnull Channel channel, @Nonnull ForumChannel.Layout oldValue, @Nonnull ForumChannel.Layout newValue) {
        super(api, responseNumber, channel, ChannelField.DEFAULT_FORUM_LAYOUT, oldValue, newValue);
    }

    @Override
    @Nonnull
    public ForumChannel.Layout getOldValue() {
        return (ForumChannel.Layout)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public ForumChannel.Layout getNewValue() {
        return (ForumChannel.Layout)((Object)super.getNewValue());
    }
}

