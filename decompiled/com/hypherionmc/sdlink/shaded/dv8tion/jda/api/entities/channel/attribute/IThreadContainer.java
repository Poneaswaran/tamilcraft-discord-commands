/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ThreadChannelPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface IThreadContainer
extends GuildChannel,
IPermissionContainer {
    public int getDefaultThreadSlowmode();

    @Nonnull
    default public @Unmodifiable List<ThreadChannel> getThreadChannels() {
        return this.getGuild().getThreadChannelCache().applyStream(stream -> stream.filter(thread -> thread.getParentChannel() == this).collect(Helpers.toUnmodifiableList()));
    }

    @Nonnull
    @CheckReturnValue
    default public ThreadChannelAction createThreadChannel(@Nonnull String name) {
        return this.createThreadChannel(name, false);
    }

    @Nonnull
    @CheckReturnValue
    public ThreadChannelAction createThreadChannel(@Nonnull String var1, boolean var2);

    @Nonnull
    @CheckReturnValue
    public ThreadChannelAction createThreadChannel(@Nonnull String var1, long var2);

    @Nonnull
    @CheckReturnValue
    default public ThreadChannelAction createThreadChannel(@Nonnull String name, @Nonnull String messageId) {
        return this.createThreadChannel(name, MiscUtil.parseSnowflake(messageId));
    }

    @Nonnull
    @CheckReturnValue
    public ThreadChannelPaginationAction retrieveArchivedPublicThreadChannels();

    @Nonnull
    @CheckReturnValue
    public ThreadChannelPaginationAction retrieveArchivedPrivateThreadChannels();

    @Nonnull
    @CheckReturnValue
    public ThreadChannelPaginationAction retrieveArchivedPrivateJoinedThreadChannels();
}

