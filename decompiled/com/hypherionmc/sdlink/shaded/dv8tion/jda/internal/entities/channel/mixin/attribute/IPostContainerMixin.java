/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ForumPostAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IThreadContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.ForumPostActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedSnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IPostContainerMixin<T extends IPostContainerMixin<T>>
extends IPostContainer,
IThreadContainerMixin<T> {
    @Nonnull
    public SortedSnowflakeCacheViewImpl<ForumTag> getAvailableTagCache();

    @Override
    @Nonnull
    default public ForumPostAction createForumPost(@Nonnull String name, @Nonnull MessageCreateData message) {
        this.checkPermission(Permission.MESSAGE_SEND);
        return new ForumPostActionImpl(this, name, (MessageCreateBuilder)new MessageCreateBuilder().applyData(message));
    }

    @Override
    @Nonnull
    default public ThreadChannelAction createThreadChannel(@Nonnull String name) {
        throw new UnsupportedOperationException("You cannot create threads without a message payload in forum/media channels! Use createForumPost(...) instead.");
    }

    @Override
    @Nonnull
    default public ThreadChannelAction createThreadChannel(@Nonnull String name, @Nonnull String messageId) {
        throw new UnsupportedOperationException("You cannot create threads without a message payload in forum/media channels! Use createForumPost(...) instead.");
    }

    public T setDefaultReaction(DataObject var1);

    public T setDefaultSortOrder(int var1);

    public T setFlags(int var1);

    public int getRawSortOrder();

    public int getRawFlags();
}

