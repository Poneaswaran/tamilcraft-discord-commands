/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public interface Entitlement
extends ISnowflake {
    public long getSkuIdLong();

    @Nonnull
    default public String getSkuId() {
        return Long.toUnsignedString(this.getSkuIdLong());
    }

    public long getApplicationIdLong();

    @Nonnull
    default public String getApplicationId() {
        return Long.toUnsignedString(this.getApplicationIdLong());
    }

    public long getUserIdLong();

    @Nonnull
    default public String getUserId() {
        return Long.toUnsignedString(this.getUserIdLong());
    }

    public long getGuildIdLong();

    @Nullable
    default public String getGuildId() {
        if (this.getGuildIdLong() == 0L) {
            return null;
        }
        return Long.toUnsignedString(this.getGuildIdLong());
    }

    @Nonnull
    public EntitlementType getType();

    public boolean isDeleted();

    @Nullable
    public OffsetDateTime getTimeStarting();

    @Nullable
    public OffsetDateTime getTimeEnding();

    public boolean isConsumed();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> consume();

    public static enum EntitlementType {
        PURCHASE(1),
        PREMIUM_SUBSCRIPTION(2),
        DEVELOPER_GIFT(3),
        TEST_MODE_PURCHASE(4),
        FREE_PURCHASE(5),
        USER_GIFT(6),
        PREMIUM_PURCHASE(7),
        APPLICATION_SUBSCRIPTION(8),
        UNKNOWN(-1);

        private final int key;

        private EntitlementType(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static EntitlementType fromKey(int key) {
            for (EntitlementType type : EntitlementType.values()) {
                if (type.getKey() != key) continue;
                return type;
            }
            return UNKNOWN;
        }
    }
}

