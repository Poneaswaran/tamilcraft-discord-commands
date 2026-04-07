/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPositionableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ICategorizableChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface ICategorizableChannel
extends GuildChannel,
IPermissionContainer,
IPositionableChannel {
    @Override
    @Nonnull
    @CheckReturnValue
    public ICategorizableChannelManager<?, ?> getManager();

    default public int getPositionInCategory() {
        Category parent = this.getParentCategory();
        return parent == null ? -1 : parent.getChannels().indexOf(this);
    }

    public long getParentCategoryIdLong();

    @Nullable
    default public String getParentCategoryId() {
        long parentID = this.getParentCategoryIdLong();
        if (parentID == 0L) {
            return null;
        }
        return Long.toUnsignedString(parentID);
    }

    @Nullable
    default public Category getParentCategory() {
        return this.getGuild().getCategoryById(this.getParentCategoryIdLong());
    }

    public boolean isSynced();
}

