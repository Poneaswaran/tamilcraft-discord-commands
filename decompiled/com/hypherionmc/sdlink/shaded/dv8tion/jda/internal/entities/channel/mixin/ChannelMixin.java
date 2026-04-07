/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.ChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ChannelMixin<T extends ChannelMixin<T>>
extends Channel,
ChannelUnion {
    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> delete() {
        Route.CompiledRoute route = Route.Channels.DELETE_CHANNEL.compile(this.getId());
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    public T setName(String var1);

    public void checkCanAccess();
}

