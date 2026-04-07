/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ActionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogEntry;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface AuditLogPaginationAction
extends PaginationAction<AuditLogEntry, AuditLogPaginationAction> {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public AuditLogPaginationAction type(@Nullable ActionType var1);

    @Nonnull
    @CheckReturnValue
    public AuditLogPaginationAction user(@Nullable UserSnowflake var1);
}

