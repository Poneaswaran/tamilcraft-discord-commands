/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum AuditLogOption {
    COUNT("count"),
    MESSAGE("message_id"),
    CHANNEL("channel_id"),
    USER("user_id"),
    ROLE("role_id"),
    ROLE_NAME("role_name"),
    TYPE("type"),
    ID("id"),
    DELETE_MEMBER_DAYS("delete_member_days"),
    MEMBERS_REMOVED("members_removed");

    private final String key;

    private AuditLogOption(String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return this.key;
    }

    public String toString() {
        return new EntityString((Object)this).setType(this).addMetadata("key", this.key).toString();
    }
}

