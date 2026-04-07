/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;

public interface EntitlementPaginationAction
extends PaginationAction<Entitlement, EntitlementPaginationAction> {
    @Nonnull
    @CheckReturnValue
    public EntitlementPaginationAction user(@Nullable UserSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public EntitlementPaginationAction skuIds(long ... var1);

    @Nonnull
    @CheckReturnValue
    public EntitlementPaginationAction skuIds(String ... var1);

    @Nonnull
    @CheckReturnValue
    public EntitlementPaginationAction skuIds(@Nonnull Collection<String> var1);

    @Nonnull
    @CheckReturnValue
    public EntitlementPaginationAction guild(long var1);

    @Nonnull
    @CheckReturnValue
    default public EntitlementPaginationAction guild(@Nonnull String guildId) {
        Checks.notNull(guildId, "guildId");
        Checks.isSnowflake(guildId, "guildId");
        return this.guild(Long.parseUnsignedLong(guildId));
    }

    @Nonnull
    @CheckReturnValue
    default public EntitlementPaginationAction guild(@Nonnull Guild guild) {
        Checks.notNull(guild, "guild");
        return this.guild(guild.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public EntitlementPaginationAction excludeEnded(boolean var1);
}

