/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface TeamMember {
    @Nonnull
    public User getUser();

    @Nonnull
    public MembershipState getMembershipState();

    @Nonnull
    public RoleType getRoleType();

    @Nonnull
    default public String getTeamId() {
        return Long.toUnsignedString(this.getTeamIdLong());
    }

    public long getTeamIdLong();

    public static enum RoleType {
        OWNER(""),
        ADMIN("admin"),
        DEVELOPER("developer"),
        READ_ONLY("read_only"),
        UNKNOWN("");

        private final String key;

        private RoleType(String key) {
            this.key = key;
        }

        @Nonnull
        public String getKey() {
            return this.key;
        }

        @Nonnull
        public static RoleType fromKey(@Nonnull String key) {
            Checks.notNull(key, "Key");
            if (key.isEmpty()) {
                return UNKNOWN;
            }
            for (RoleType state : RoleType.values()) {
                if (!state.key.equals(key)) continue;
                return state;
            }
            return UNKNOWN;
        }
    }

    public static enum MembershipState {
        INVITED(1),
        ACCEPTED(2),
        UNKNOWN(-1);

        private final int key;

        private MembershipState(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static MembershipState fromKey(int key) {
            for (MembershipState state : MembershipState.values()) {
                if (state.key != key) continue;
                return state;
            }
            return UNKNOWN;
        }
    }
}

