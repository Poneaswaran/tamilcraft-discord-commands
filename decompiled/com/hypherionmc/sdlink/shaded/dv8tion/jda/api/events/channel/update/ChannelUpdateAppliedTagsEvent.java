/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChannelUpdateAppliedTagsEvent
extends GenericChannelUpdateEvent<List<Long>> {
    public static final ChannelField FIELD = ChannelField.APPLIED_TAGS;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateAppliedTagsEvent(@Nonnull JDA api, long responseNumber, @Nonnull ThreadChannel channel, @Nonnull List<Long> oldValue, @Nonnull List<Long> newValue) {
        super(api, responseNumber, channel, FIELD, oldValue, newValue);
    }

    @Nonnull
    public List<ForumTag> getAddedTags() {
        ArrayList<ForumTag> newTags = new ArrayList<ForumTag>(this.getNewTags());
        newTags.removeAll(this.getOldTags());
        return newTags;
    }

    @Nonnull
    public List<ForumTag> getRemovedTags() {
        ArrayList<ForumTag> oldTags = new ArrayList<ForumTag>(this.getOldTags());
        oldTags.removeAll(this.getNewTags());
        return oldTags;
    }

    @Nonnull
    public List<ForumTag> getNewTags() {
        SortedSnowflakeCacheView<ForumTag> cache = this.getChannel().asThreadChannel().getParentChannel().asForumChannel().getAvailableTagCache();
        return this.getNewValue().stream().map(cache::getElementById).filter(Objects::nonNull).sorted().collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    public List<ForumTag> getOldTags() {
        SortedSnowflakeCacheView<ForumTag> cache = this.getChannel().asThreadChannel().getParentChannel().asForumChannel().getAvailableTagCache();
        return this.getOldValue().stream().map(cache::getElementById).filter(Objects::nonNull).sorted().collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public List<Long> getOldValue() {
        return (List)super.getOldValue();
    }

    @Override
    @Nonnull
    public List<Long> getNewValue() {
        return (List)super.getNewValue();
    }
}

