/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum AuditLogKey {
    ID("id"),
    TYPE("type"),
    APPLICATION_ID("application_id"),
    GUILD_NAME("name"),
    GUILD_OWNER("owner_id"),
    GUILD_REGION("region"),
    GUILD_AFK_TIMEOUT("afk_timeout"),
    GUILD_AFK_CHANNEL("afk_channel_id"),
    GUILD_SYSTEM_CHANNEL("system_channel_id"),
    GUILD_RULES_CHANNEL("rules_channel_id"),
    GUILD_COMMUNITY_UPDATES_CHANNEL("public_updates_channel_id"),
    GUILD_EXPLICIT_CONTENT_FILTER("explicit_content_filter"),
    GUILD_ICON("icon_hash"),
    GUILD_SPLASH("splash_hash"),
    GUILD_VERIFICATION_LEVEL("verification_level"),
    GUILD_NOTIFICATION_LEVEL("default_message_notifications"),
    GUILD_MFA_LEVEL("mfa_level"),
    GUILD_VANITY_URL_CODE("vanity_url_code"),
    GUILD_PRUNE_DELETE_DAYS("prune_delete_days"),
    GUILD_WIDGET_ENABLED("widget_enabled"),
    GUILD_WIDGET_CHANNEL_ID("widget_channel_id"),
    CHANNEL_NAME("name"),
    CHANNEL_FLAGS("flags"),
    CHANNEL_PARENT("parent_id"),
    CHANNEL_TOPIC("topic"),
    CHANNEL_VOICE_STATUS("status"),
    CHANNEL_SLOWMODE("rate_limit_per_user"),
    CHANNEL_DEFAULT_THREAD_SLOWMODE("default_thread_rate_limit_per_user"),
    CHANNEL_DEFAULT_REACTION_EMOJI("default_reaction_emoji"),
    CHANNEL_BITRATE("bitrate"),
    CHANNEL_USER_LIMIT("user_limit"),
    CHANNEL_NSFW("nsfw"),
    CHANNEL_REGION("rtc_region"),
    CHANNEL_TYPE("type"),
    CHANNEL_OVERRIDES("permission_overwrites"),
    CHANNEL_AVAILABLE_TAGS("available_tags"),
    CHANNEL_ID("channel_id"),
    CHANNEL_DEFAULT_SORT_ORDER("default_sort_order"),
    DEFAULT_FORUM_LAYOUT("default_forum_layout"),
    THREAD_NAME("name"),
    THREAD_AUTO_ARCHIVE_DURATION("auto_archive_duration"),
    THREAD_ARCHIVED("archived"),
    THREAD_LOCKED("locked"),
    THREAD_INVITABLE("invitable"),
    THREAD_APPLIED_TAGS("applied_tags"),
    PRIVACY_LEVEL("privacy_level"),
    MEMBER_NICK("nick"),
    MEMBER_MUTE("mute"),
    MEMBER_DEAF("deaf"),
    MEMBER_ROLES_ADD("$add"),
    MEMBER_ROLES_REMOVE("$remove"),
    MEMBER_TIME_OUT("communication_disabled_until"),
    OVERRIDE_DENY("deny"),
    OVERRIDE_ALLOW("allow"),
    OVERRIDE_TYPE("type"),
    ROLE_NAME("name"),
    ROLE_PERMISSIONS("permissions"),
    ROLE_COLOR("color"),
    ROLE_HOISTED("hoist"),
    ROLE_MENTIONABLE("mentionable"),
    EMOJI_NAME("name"),
    EMOJI_ROLES_ADD("$add"),
    EMOJI_ROLES_REMOVE("$remove"),
    STICKER_NAME("name"),
    STICKER_FORMAT("format_type"),
    STICKER_DESCRIPTION("description"),
    STICKER_TAGS("tags"),
    WEBHOOK_NAME("name"),
    WEBHOOK_ICON("avatar_hash"),
    WEBHOOK_CHANNEL("channel_id"),
    INVITE_CODE("code"),
    INVITE_MAX_AGE("max_age"),
    INVITE_TEMPORARY("temporary"),
    INVITE_INVITER("inviter"),
    INVITE_CHANNEL("channel_id"),
    INVITE_USES("uses"),
    INVITE_MAX_USES("max_uses"),
    AUTO_MODERATION_RULE_NAME("auto_moderation_rule_name"),
    AUTO_MODERATION_RULE_TRIGGER_TYPE("auto_moderation_rule_trigger_type");

    private final String key;

    private AuditLogKey(String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return this.key;
    }

    @Nonnull
    public String toString() {
        return new EntityString((Object)this).setType(this).addMetadata("key", this.key).toString();
    }
}

