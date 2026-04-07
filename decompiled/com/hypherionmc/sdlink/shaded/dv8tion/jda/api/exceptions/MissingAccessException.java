/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class MissingAccessException
extends InsufficientPermissionException {
    public MissingAccessException(@Nonnull GuildChannel channel, @Nonnull Permission permission) {
        super(channel, permission);
    }

    public MissingAccessException(@Nonnull GuildChannel channel, @Nonnull Permission permission, @Nonnull String reason) {
        super(channel, permission, reason);
    }
}

