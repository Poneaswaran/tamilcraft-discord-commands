/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.ChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPermissionContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPositionableChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface ICategorizableChannelManager<T extends ICategorizableChannel, M extends ICategorizableChannelManager<T, M>>
extends ChannelManager<T, M>,
IPermissionContainerManager<T, M>,
IPositionableChannelManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M setParent(@Nullable Category var1);

    @Nonnull
    @CheckReturnValue
    default public M sync() {
        if (!(this.getChannel() instanceof ICategorizableChannel)) {
            throw new IllegalStateException("sync() requires that the channel be categorizable as it syncs the channel to the parent category.");
        }
        ICategorizableChannel categorizableChannel = (ICategorizableChannel)this.getChannel();
        if (categorizableChannel.getParentCategory() == null) {
            throw new IllegalStateException("sync() requires a parent category");
        }
        return this.sync(categorizableChannel.getParentCategory());
    }

    @Nonnull
    @CheckReturnValue
    public M sync(@Nonnull IPermissionContainer var1);
}

