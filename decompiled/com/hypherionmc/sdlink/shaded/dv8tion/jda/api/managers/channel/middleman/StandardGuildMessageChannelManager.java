/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IAgeRestrictedChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IThreadContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface StandardGuildMessageChannelManager<T extends StandardGuildMessageChannel, M extends StandardGuildMessageChannelManager<T, M>>
extends StandardGuildChannelManager<T, M>,
IAgeRestrictedChannelManager<T, M>,
IThreadContainerManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M setTopic(@Nullable String var1);
}

