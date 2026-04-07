/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class BulkBanResponse {
    private final List<UserSnowflake> bannedUsers;
    private final List<UserSnowflake> failedUsers;

    public BulkBanResponse(@Nonnull List<UserSnowflake> bannedUsers, @Nonnull List<UserSnowflake> failedUsers) {
        this.bannedUsers = Collections.unmodifiableList(bannedUsers);
        this.failedUsers = Collections.unmodifiableList(failedUsers);
    }

    @Nonnull
    public List<UserSnowflake> getBannedUsers() {
        return this.bannedUsers;
    }

    @Nonnull
    public List<UserSnowflake> getFailedUsers() {
        return this.failedUsers;
    }
}

