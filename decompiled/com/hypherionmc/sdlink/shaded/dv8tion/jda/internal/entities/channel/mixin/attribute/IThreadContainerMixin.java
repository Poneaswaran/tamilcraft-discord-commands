/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IThreadContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ThreadChannelPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.ThreadChannelActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.ThreadChannelPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IThreadContainerMixin<T extends IThreadContainerMixin<T>>
extends IThreadContainer,
IThreadContainerUnion,
GuildChannelMixin<T> {
    @Override
    @Nonnull
    default public ThreadChannelAction createThreadChannel(@Nonnull String name, boolean isPrivate) {
        Checks.notNull(name, "Name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        Checks.checkAccess(this.getGuild().getSelfMember(), this);
        if (isPrivate) {
            this.checkPermission(Permission.CREATE_PRIVATE_THREADS);
        } else {
            this.checkPermission(Permission.CREATE_PUBLIC_THREADS);
        }
        ChannelType threadType = isPrivate ? ChannelType.GUILD_PRIVATE_THREAD : (this.getType() == ChannelType.TEXT ? ChannelType.GUILD_PUBLIC_THREAD : ChannelType.GUILD_NEWS_THREAD);
        return new ThreadChannelActionImpl((GuildChannel)this, name, threadType);
    }

    @Override
    @Nonnull
    default public ThreadChannelAction createThreadChannel(@Nonnull String name, long messageId) {
        Checks.notNull(name, "Name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        Checks.checkAccess(this.getGuild().getSelfMember(), this);
        this.checkPermission(Permission.CREATE_PUBLIC_THREADS);
        return new ThreadChannelActionImpl((GuildChannel)this, name, Long.toUnsignedString(messageId));
    }

    @Override
    @Nonnull
    default public ThreadChannelPaginationAction retrieveArchivedPublicThreadChannels() {
        Checks.checkAccess(this.getGuild().getSelfMember(), this);
        this.checkPermission(Permission.MESSAGE_HISTORY);
        Route.CompiledRoute route = Route.Channels.LIST_PUBLIC_ARCHIVED_THREADS.compile(this.getId());
        return new ThreadChannelPaginationActionImpl(this.getJDA(), route, this, false);
    }

    @Override
    @Nonnull
    default public ThreadChannelPaginationAction retrieveArchivedPrivateThreadChannels() {
        Checks.checkAccess(this.getGuild().getSelfMember(), this);
        this.checkPermission(Permission.MESSAGE_HISTORY);
        this.checkPermission(Permission.MANAGE_THREADS);
        Route.CompiledRoute route = Route.Channels.LIST_PRIVATE_ARCHIVED_THREADS.compile(this.getId());
        return new ThreadChannelPaginationActionImpl(this.getJDA(), route, this, false);
    }

    @Override
    @Nonnull
    default public ThreadChannelPaginationAction retrieveArchivedPrivateJoinedThreadChannels() {
        Checks.checkAccess(this.getGuild().getSelfMember(), this);
        this.checkPermission(Permission.MESSAGE_HISTORY);
        Route.CompiledRoute route = Route.Channels.LIST_JOINED_PRIVATE_ARCHIVED_THREADS.compile(this.getId());
        return new ThreadChannelPaginationActionImpl(this.getJDA(), route, this, true);
    }

    public T setDefaultThreadSlowmode(int var1);
}

