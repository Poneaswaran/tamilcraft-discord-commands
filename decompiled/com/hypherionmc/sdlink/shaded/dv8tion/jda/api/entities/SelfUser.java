/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AccountManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface SelfUser
extends User {
    public long getApplicationIdLong();

    @Nonnull
    default public String getApplicationId() {
        return Long.toUnsignedString(this.getApplicationIdLong());
    }

    public boolean isVerified();

    public boolean isMfaEnabled();

    public long getAllowedFileSize();

    @Nonnull
    @CheckReturnValue
    public AccountManager getManager();
}

