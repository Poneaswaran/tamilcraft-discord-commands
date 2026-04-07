/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.core.event.CraterEvent
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.api.events;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import lombok.Generated;

public final class VerificationEvent {

    public static class PlayerUnverified
    extends CraterEvent {
        private final MinecraftAccount account;

        @Generated
        public MinecraftAccount getAccount() {
            return this.account;
        }

        @Generated
        public PlayerUnverified(MinecraftAccount account) {
            this.account = account;
        }
    }

    public static class PlayerVerified
    extends CraterEvent {
        private final MinecraftAccount account;

        @Generated
        public MinecraftAccount getAccount() {
            return this.account;
        }

        @Generated
        public PlayerVerified(MinecraftAccount account) {
            this.account = account;
        }
    }
}

