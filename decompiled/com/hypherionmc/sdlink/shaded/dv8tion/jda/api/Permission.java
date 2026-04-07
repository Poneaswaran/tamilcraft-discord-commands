/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;

public enum Permission {
    MANAGE_CHANNEL(4, true, true, "Manage Channels"),
    MANAGE_SERVER(5, true, false, "Manage Server"),
    VIEW_AUDIT_LOGS(7, true, false, "View Audit Log"),
    VIEW_CHANNEL(10, true, true, "View Channels"),
    VIEW_GUILD_INSIGHTS(19, true, false, "View Server Insights"),
    MANAGE_ROLES(28, true, false, "Manage Roles"),
    MANAGE_PERMISSIONS(28, false, true, "Manage Permissions"),
    MANAGE_WEBHOOKS(29, true, true, "Manage Webhooks"),
    MANAGE_GUILD_EXPRESSIONS(30, true, false, "Manage Expressions"),
    MANAGE_EVENTS(33, true, true, "Manage Events"),
    USE_EMBEDDED_ACTIVITIES(39, true, true, "Use Activities"),
    VIEW_CREATOR_MONETIZATION_ANALYTICS(41, true, false, "View Creator Analytics"),
    CREATE_GUILD_EXPRESSIONS(43, true, false, "Create Expressions"),
    CREATE_SCHEDULED_EVENTS(44, true, false, "Create Events"),
    CREATE_INSTANT_INVITE(0, true, true, "Create Instant Invite"),
    KICK_MEMBERS(1, true, false, "Kick Members"),
    BAN_MEMBERS(2, true, false, "Ban Members"),
    NICKNAME_CHANGE(26, true, false, "Change Nickname"),
    NICKNAME_MANAGE(27, true, false, "Manage Nicknames"),
    MODERATE_MEMBERS(40, true, false, "Timeout Members"),
    MESSAGE_ADD_REACTION(6, true, true, "Add Reactions"),
    MESSAGE_SEND(11, true, true, "Send Messages"),
    MESSAGE_TTS(12, true, true, "Send TTS Messages"),
    MESSAGE_MANAGE(13, true, true, "Manage Messages"),
    MESSAGE_EMBED_LINKS(14, true, true, "Embed Links"),
    MESSAGE_ATTACH_FILES(15, true, true, "Attach Files"),
    MESSAGE_HISTORY(16, true, true, "Read History"),
    MESSAGE_MENTION_EVERYONE(17, true, true, "Mention Everyone"),
    MESSAGE_EXT_EMOJI(18, true, true, "Use External Emojis"),
    USE_APPLICATION_COMMANDS(31, true, true, "Use Application Commands"),
    MESSAGE_EXT_STICKER(37, true, true, "Use External Stickers"),
    MESSAGE_ATTACH_VOICE_MESSAGE(46, true, true, "Send Voice Messages"),
    MESSAGE_SEND_POLLS(49, true, true, "Create Polls"),
    USE_EXTERNAL_APPLICATIONS(50, true, true, "Use External Apps"),
    MANAGE_THREADS(34, true, true, "Manage Threads"),
    CREATE_PUBLIC_THREADS(35, true, true, "Create Public Threads"),
    CREATE_PRIVATE_THREADS(36, true, true, "Create Private Threads"),
    MESSAGE_SEND_IN_THREADS(38, true, true, "Send Messages in Threads"),
    PRIORITY_SPEAKER(8, true, true, "Priority Speaker"),
    VOICE_STREAM(9, true, true, "Video"),
    VOICE_CONNECT(20, true, true, "Connect"),
    VOICE_SPEAK(21, true, true, "Speak"),
    VOICE_MUTE_OTHERS(22, true, true, "Mute Members"),
    VOICE_DEAF_OTHERS(23, true, true, "Deafen Members"),
    VOICE_MOVE_OTHERS(24, true, true, "Move Members"),
    VOICE_USE_VAD(25, true, true, "Use Voice Activity"),
    VOICE_USE_SOUNDBOARD(42, true, true, "Use Soundboard"),
    VOICE_USE_EXTERNAL_SOUNDS(45, true, true, "Use External Sounds"),
    VOICE_SET_STATUS(48, true, true, "Set Voice Channel Status"),
    REQUEST_TO_SPEAK(32, true, true, "Request to Speak"),
    ADMINISTRATOR(3, true, false, "Administrator"),
    UNKNOWN(-1, false, false, "Unknown");

    public static final Permission[] EMPTY_PERMISSIONS;
    private final int offset;
    private final long raw;
    private final boolean isGuild;
    private final boolean isChannel;
    private final String name;

    private Permission(int offset, @Nonnull boolean isGuild, boolean isChannel, String name) {
        this.offset = offset;
        this.raw = 1L << offset;
        this.isGuild = isGuild;
        this.isChannel = isChannel;
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return this.name;
    }

    public int getOffset() {
        return this.offset;
    }

    public long getRawValue() {
        return this.raw;
    }

    public boolean isGuild() {
        return this.isGuild;
    }

    public boolean isChannel() {
        return this.isChannel;
    }

    @Nonnull
    public static Permission getFromOffset(int offset) {
        for (Permission perm : Permission.values()) {
            if (perm.offset != offset) continue;
            return perm;
        }
        return UNKNOWN;
    }

    @Nonnull
    public static EnumSet<Permission> getPermissions(long permissions) {
        if (permissions == 0L) {
            return EnumSet.noneOf(Permission.class);
        }
        EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
        for (Permission perm : Permission.values()) {
            if (perm == UNKNOWN || (permissions & perm.raw) != perm.raw) continue;
            perms.add(perm);
        }
        return perms;
    }

    public static long getRaw(Permission ... permissions) {
        long raw = 0L;
        for (Permission perm : permissions) {
            if (perm == null || perm == UNKNOWN) continue;
            raw |= perm.raw;
        }
        return raw;
    }

    public static long getRaw(@Nonnull Collection<Permission> permissions) {
        Checks.notNull(permissions, "Permission Collection");
        return Permission.getRaw(permissions.toArray(EMPTY_PERMISSIONS));
    }

    static {
        EMPTY_PERMISSIONS = new Permission[0];
    }
}

