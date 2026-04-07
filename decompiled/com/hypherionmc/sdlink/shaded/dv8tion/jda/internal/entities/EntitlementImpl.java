/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class EntitlementImpl
implements Entitlement {
    private final JDA api;
    private long id;
    private long skuId;
    private long applicationId;
    private long userId;
    private long guildId;
    private Entitlement.EntitlementType type;
    private boolean deleted;
    private OffsetDateTime startsAt;
    private OffsetDateTime endsAt;
    private boolean consumed;

    public EntitlementImpl(JDA api, long id, long skuId, long applicationId, long userId, long guildId, Entitlement.EntitlementType type, boolean deleted, @Nullable OffsetDateTime startsAt, @Nullable OffsetDateTime endsAt, boolean consumed) {
        this.api = api;
        this.id = id;
        this.skuId = skuId;
        this.applicationId = applicationId;
        this.userId = userId;
        this.guildId = guildId;
        this.type = type;
        this.deleted = deleted;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.consumed = consumed;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    public long getSkuIdLong() {
        return this.skuId;
    }

    @Override
    public long getApplicationIdLong() {
        return this.applicationId;
    }

    @Override
    public long getUserIdLong() {
        return this.userId;
    }

    @Override
    public long getGuildIdLong() {
        return this.guildId;
    }

    @Override
    @Nonnull
    public Entitlement.EntitlementType getType() {
        return this.type;
    }

    @Override
    public boolean isDeleted() {
        return this.deleted;
    }

    @Override
    @Nullable
    public OffsetDateTime getTimeStarting() {
        return this.startsAt;
    }

    @Override
    @Nullable
    public OffsetDateTime getTimeEnding() {
        return this.endsAt;
    }

    @Override
    public boolean isConsumed() {
        return this.consumed;
    }

    @Override
    @Nonnull
    public RestAction<Void> consume() {
        if (this.consumed) {
            return new CompletedRestAction<Void>(this.api, null);
        }
        Route.CompiledRoute route = Route.Applications.CONSUME_ENTITLEMENT.compile(this.getApplicationId(), this.getId());
        return new RestActionImpl<Void>(this.api, route);
    }
}

