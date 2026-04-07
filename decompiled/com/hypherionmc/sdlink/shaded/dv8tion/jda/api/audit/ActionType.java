/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.TargetType;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum ActionType {
    GUILD_UPDATE(1, TargetType.GUILD),
    CHANNEL_CREATE(10, TargetType.CHANNEL),
    CHANNEL_UPDATE(11, TargetType.CHANNEL),
    CHANNEL_DELETE(12, TargetType.CHANNEL),
    CHANNEL_OVERRIDE_CREATE(13, TargetType.CHANNEL),
    CHANNEL_OVERRIDE_UPDATE(14, TargetType.CHANNEL),
    CHANNEL_OVERRIDE_DELETE(15, TargetType.CHANNEL),
    KICK(20, TargetType.MEMBER),
    PRUNE(21, TargetType.MEMBER),
    BAN(22, TargetType.MEMBER),
    UNBAN(23, TargetType.MEMBER),
    MEMBER_UPDATE(24, TargetType.MEMBER),
    MEMBER_ROLE_UPDATE(25, TargetType.MEMBER),
    MEMBER_VOICE_MOVE(26, TargetType.MEMBER),
    MEMBER_VOICE_KICK(27, TargetType.MEMBER),
    BOT_ADD(28, TargetType.MEMBER),
    ROLE_CREATE(30, TargetType.ROLE),
    ROLE_UPDATE(31, TargetType.ROLE),
    ROLE_DELETE(32, TargetType.ROLE),
    INVITE_CREATE(40, TargetType.INVITE),
    INVITE_UPDATE(41, TargetType.INVITE),
    INVITE_DELETE(42, TargetType.INVITE),
    WEBHOOK_CREATE(50, TargetType.WEBHOOK),
    WEBHOOK_UPDATE(51, TargetType.WEBHOOK),
    WEBHOOK_REMOVE(52, TargetType.WEBHOOK),
    EMOJI_CREATE(60, TargetType.EMOJI),
    EMOJI_UPDATE(61, TargetType.EMOJI),
    EMOJI_DELETE(62, TargetType.EMOJI),
    MESSAGE_CREATE(70, TargetType.UNKNOWN),
    MESSAGE_UPDATE(71, TargetType.UNKNOWN),
    MESSAGE_DELETE(72, TargetType.MEMBER),
    MESSAGE_BULK_DELETE(73, TargetType.CHANNEL),
    MESSAGE_PIN(74, TargetType.CHANNEL),
    MESSAGE_UNPIN(75, TargetType.CHANNEL),
    INTEGRATION_CREATE(80, TargetType.INTEGRATION),
    INTEGRATION_UPDATE(81, TargetType.INTEGRATION),
    INTEGRATION_DELETE(82, TargetType.INTEGRATION),
    STAGE_INSTANCE_CREATE(83, TargetType.STAGE_INSTANCE),
    STAGE_INSTANCE_UPDATE(84, TargetType.STAGE_INSTANCE),
    STAGE_INSTANCE_DELETE(85, TargetType.STAGE_INSTANCE),
    SCHEDULED_EVENT_CREATE(100, TargetType.SCHEDULED_EVENT),
    SCHEDULED_EVENT_UPDATE(101, TargetType.SCHEDULED_EVENT),
    SCHEDULED_EVENT_DELETE(102, TargetType.SCHEDULED_EVENT),
    STICKER_CREATE(90, TargetType.STICKER),
    STICKER_UPDATE(91, TargetType.STICKER),
    STICKER_DELETE(92, TargetType.STICKER),
    THREAD_CREATE(110, TargetType.THREAD),
    THREAD_UPDATE(111, TargetType.THREAD),
    THREAD_DELETE(112, TargetType.THREAD),
    APPLICATION_COMMAND_PRIVILEGES_UPDATE(121, TargetType.INTEGRATION),
    AUTO_MODERATION_RULE_CREATE(140, TargetType.AUTO_MODERATION_RULE),
    AUTO_MODERATION_RULE_UPDATE(141, TargetType.AUTO_MODERATION_RULE),
    AUTO_MODERATION_RULE_DELETE(142, TargetType.AUTO_MODERATION_RULE),
    AUTO_MODERATION_RULE_BLOCK_MESSAGE(143, TargetType.MEMBER),
    AUTO_MODERATION_FLAG_TO_CHANNEL(144, TargetType.MEMBER),
    AUTO_MODERATION_MEMBER_TIMEOUT(145, TargetType.MEMBER),
    VOICE_CHANNEL_STATUS_UPDATE(192, TargetType.CHANNEL),
    VOICE_CHANNEL_STATUS_DELETE(193, TargetType.CHANNEL),
    UNKNOWN(-1, TargetType.UNKNOWN);

    private final int key;
    private final TargetType target;

    private ActionType(int key, TargetType target) {
        this.key = key;
        this.target = target;
    }

    public int getKey() {
        return this.key;
    }

    @Nonnull
    public TargetType getTargetType() {
        return this.target;
    }

    @Nonnull
    public static ActionType from(int key) {
        for (ActionType type : ActionType.values()) {
            if (type.key != key) continue;
            return type;
        }
        return UNKNOWN;
    }
}

