/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum MessageType {
    DEFAULT(0, false, true),
    RECIPIENT_ADD(1, true, false),
    RECIPIENT_REMOVE(2, true, false),
    CALL(3, true, false),
    CHANNEL_NAME_CHANGE(4, true, false),
    CHANNEL_ICON_CHANGE(5, true, false),
    CHANNEL_PINNED_ADD(6, true, true),
    GUILD_MEMBER_JOIN(7, true, true),
    GUILD_MEMBER_BOOST(8, true, true),
    GUILD_BOOST_TIER_1(9, true, true),
    GUILD_BOOST_TIER_2(10, true, true),
    GUILD_BOOST_TIER_3(11, true, true),
    CHANNEL_FOLLOW_ADD(12, true, true),
    GUILD_DISCOVERY_DISQUALIFIED(14, true, true),
    GUILD_DISCOVERY_REQUALIFIED(15, true, true),
    GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING(16, true, true),
    GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING(17, true, true),
    THREAD_CREATED(18, true, true),
    INLINE_REPLY(19, false, true),
    SLASH_COMMAND(20, false, true),
    THREAD_STARTER_MESSAGE(21, false, false),
    GUILD_INVITE_REMINDER(22, true, true),
    CONTEXT_COMMAND(23, false, true),
    AUTO_MODERATION_ACTION(24, true, true),
    ROLE_SUBSCRIPTION_PURCHASE(25, true, true),
    INTERACTION_PREMIUM_UPSELL(26, true, true),
    STAGE_START(27, true, true),
    STAGE_END(28, true, true),
    STAGE_SPEAKER(29, true, true),
    STAGE_TOPIC(31, true, true),
    GUILD_APPLICATION_PREMIUM_SUBSCRIPTION(32, true, true),
    GUILD_INCIDENT_ALERT_MODE_ENABLED(36, true, true),
    GUILD_INCIDENT_ALERT_MODE_DISABLED(37, true, true),
    GUILD_INCIDENT_REPORT_RAID(38, true, true),
    GUILD_INCIDENT_REPORT_FALSE_ALARM(39, true, true),
    PURCHASE_NOTIFICATION(44, true, true),
    POLL_RESULT(46, true, true),
    UNKNOWN(-1, false, true);

    private final int id;
    private final boolean system;
    private final boolean deletable;

    private MessageType(int id, boolean system, boolean deletable) {
        this.id = id;
        this.system = system;
        this.deletable = deletable;
    }

    public int getId() {
        return this.id;
    }

    public boolean isSystem() {
        return this.system;
    }

    public boolean canDelete() {
        return this.deletable;
    }

    @Nonnull
    public static MessageType fromId(int id) {
        for (MessageType type : MessageType.values()) {
            if (type.id != id) continue;
            return type;
        }
        return UNKNOWN;
    }
}

