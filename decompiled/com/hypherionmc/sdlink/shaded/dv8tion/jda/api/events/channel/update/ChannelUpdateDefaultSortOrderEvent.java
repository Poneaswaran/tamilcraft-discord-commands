/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ChannelUpdateDefaultSortOrderEvent
extends GenericChannelUpdateEvent<IPostContainer.SortOrder> {
    public static final ChannelField FIELD = ChannelField.DEFAULT_SORT_ORDER;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateDefaultSortOrderEvent(@Nonnull JDA api, long responseNumber, IPostContainer channel, IPostContainer.SortOrder oldValue) {
        super(api, responseNumber, channel, ChannelField.DEFAULT_SORT_ORDER, oldValue, channel.getDefaultSortOrder());
    }

    @Override
    @Nonnull
    public IPostContainer.SortOrder getOldValue() {
        return (IPostContainer.SortOrder)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public IPostContainer.SortOrder getNewValue() {
        return (IPostContainer.SortOrder)((Object)super.getNewValue());
    }
}

