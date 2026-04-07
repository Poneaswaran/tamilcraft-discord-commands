/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.Incubating;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPostContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ForumPostAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface IPostContainer
extends IThreadContainer {
    public static final int MAX_POST_CONTAINER_TOPIC_LENGTH = 4096;
    public static final int MAX_POST_TAGS = 5;

    @Override
    @Nonnull
    @CheckReturnValue
    public IPostContainerManager<?, ?> getManager();

    @Nonnull
    public SortedSnowflakeCacheView<ForumTag> getAvailableTagCache();

    @Nonnull
    default public @Unmodifiable List<ForumTag> getAvailableTags() {
        return this.getAvailableTagCache().asList();
    }

    @Nonnull
    default public @Unmodifiable List<ForumTag> getAvailableTagsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getAvailableTagCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public ForumTag getAvailableTagById(long id) {
        return (ForumTag)this.getAvailableTagCache().getElementById(id);
    }

    @Nullable
    default public ForumTag getAvailableTagById(@Nonnull String id) {
        return (ForumTag)this.getAvailableTagCache().getElementById(id);
    }

    @Nullable
    public String getTopic();

    default public boolean isTagRequired() {
        return this.getFlags().contains((Object)ChannelFlag.REQUIRE_TAG);
    }

    @Nullable
    public EmojiUnion getDefaultReaction();

    @Nonnull
    public SortOrder getDefaultSortOrder();

    @Nonnull
    @Incubating
    @CheckReturnValue
    public ForumPostAction createForumPost(@Nonnull String var1, @Nonnull MessageCreateData var2);

    public static enum SortOrder {
        RECENT_ACTIVITY(0),
        CREATION_TIME(1),
        UNKNOWN(-1);

        private final int order;

        private SortOrder(int order) {
            this.order = order;
        }

        public int getKey() {
            return this.order;
        }

        @Nonnull
        public static SortOrder fromKey(int key) {
            for (SortOrder order : SortOrder.values()) {
                if (order.order != key) continue;
                return order;
            }
            return UNKNOWN;
        }
    }
}

