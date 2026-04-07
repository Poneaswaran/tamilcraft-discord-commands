/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.ChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface GuildChannel
extends Channel,
Comparable<GuildChannel> {
    public static final String JUMP_URL = "https://discord.com/channels/%s/%s";

    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public ChannelManager<?, ?> getManager();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    public IPermissionContainer getPermissionContainer();

    @Nonnull
    default public String getJumpUrl() {
        return Helpers.format(JUMP_URL, this.getGuild().getId(), this.getId());
    }
}

