/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface UserSnowflake
extends IMentionable {
    @Nonnull
    public static UserSnowflake fromId(long id) {
        return new UserSnowflakeImpl(id);
    }

    @Nonnull
    public static UserSnowflake fromId(@Nonnull String id) {
        return UserSnowflake.fromId(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    public String getDefaultAvatarId();

    @Nonnull
    default public String getDefaultAvatarUrl() {
        return String.format("https://cdn.discordapp.com/embed/avatars/%s.png", this.getDefaultAvatarId());
    }

    @Nonnull
    default public ImageProxy getDefaultAvatar() {
        return new ImageProxy(this.getDefaultAvatarUrl());
    }
}

