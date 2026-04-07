/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod.AutoModExecutionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod.GenericAutoModRuleEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.GenericEmojiEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildAuditLogEntryCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildBanEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.GenericScheduledEventUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.GenericMessageEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.GenericGuildStickerEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.UserTypingEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

public enum GatewayIntent {
    GUILD_MEMBERS(1),
    GUILD_MODERATION(2),
    GUILD_EMOJIS_AND_STICKERS(3),
    GUILD_EXPRESSIONS(3),
    GUILD_WEBHOOKS(5),
    GUILD_INVITES(6),
    GUILD_VOICE_STATES(7),
    GUILD_PRESENCES(8),
    GUILD_MESSAGES(9),
    GUILD_MESSAGE_REACTIONS(10),
    GUILD_MESSAGE_TYPING(11),
    DIRECT_MESSAGES(12),
    DIRECT_MESSAGE_REACTIONS(13),
    DIRECT_MESSAGE_TYPING(14),
    MESSAGE_CONTENT(15),
    SCHEDULED_EVENTS(16),
    AUTO_MODERATION_CONFIGURATION(20),
    AUTO_MODERATION_EXECUTION(21),
    GUILD_MESSAGE_POLLS(24),
    DIRECT_MESSAGE_POLLS(25);

    public static final int ALL_INTENTS;
    public static final int DEFAULT;
    private final int rawValue;
    private final int offset;

    private GatewayIntent(int offset) {
        this.offset = offset;
        this.rawValue = 1 << offset;
    }

    public int getRawValue() {
        return this.rawValue;
    }

    public int getOffset() {
        return this.offset;
    }

    @Nonnull
    public static EnumSet<GatewayIntent> getIntents(int raw) {
        EnumSet<GatewayIntent> set = EnumSet.noneOf(GatewayIntent.class);
        for (GatewayIntent intent : GatewayIntent.values()) {
            if ((intent.getRawValue() & raw) == 0) continue;
            set.add(intent);
        }
        return set;
    }

    public static int getRaw(@Nonnull Collection<GatewayIntent> set) {
        int raw = 0;
        for (GatewayIntent intent : set) {
            raw |= intent.rawValue;
        }
        return raw;
    }

    public static int getRaw(@Nonnull GatewayIntent intent, GatewayIntent ... set) {
        Checks.notNull((Object)intent, "Intent");
        Checks.notNull(set, "Intent");
        return GatewayIntent.getRaw(EnumSet.of(intent, set));
    }

    @Nonnull
    public static EnumSet<GatewayIntent> fromCacheFlags(@Nonnull CacheFlag flag, CacheFlag ... other) {
        Checks.notNull((Object)flag, "CacheFlag");
        Checks.noneNull((Object[])other, "CacheFlag");
        return GatewayIntent.fromCacheFlags(EnumSet.of(flag, other));
    }

    @Nonnull
    public static EnumSet<GatewayIntent> fromCacheFlags(@Nonnull Collection<CacheFlag> flags) {
        EnumSet<GatewayIntent> intents = EnumSet.noneOf(GatewayIntent.class);
        for (CacheFlag flag : flags) {
            Checks.notNull((Object)flag, "CacheFlag");
            GatewayIntent intent = flag.getRequiredIntent();
            if (intent == null) continue;
            intents.add(intent);
        }
        return intents;
    }

    @Nonnull
    @SafeVarargs
    public static EnumSet<GatewayIntent> fromEvents(Class<? extends GenericEvent> ... events) {
        Checks.noneNull(events, "Event");
        return GatewayIntent.fromEvents(Arrays.asList(events));
    }

    @Nonnull
    public static EnumSet<GatewayIntent> fromEvents(@Nonnull Collection<Class<? extends GenericEvent>> events) {
        EnumSet<GatewayIntent> intents = EnumSet.noneOf(GatewayIntent.class);
        for (Class<? extends GenericEvent> event : events) {
            Checks.notNull(event, "Event");
            if (GenericUserPresenceEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_PRESENCES);
                continue;
            }
            if (GenericUserUpdateEvent.class.isAssignableFrom(event) || GenericGuildMemberEvent.class.isAssignableFrom(event) || GuildMemberRemoveEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_MEMBERS);
                continue;
            }
            if (GuildBanEvent.class.isAssignableFrom(event) || GuildUnbanEvent.class.isAssignableFrom(event) || GuildAuditLogEntryCreateEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_MODERATION);
                continue;
            }
            if (GenericEmojiEvent.class.isAssignableFrom(event) || GenericGuildStickerEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_EXPRESSIONS);
                continue;
            }
            if (GenericScheduledEventUpdateEvent.class.isAssignableFrom(event)) {
                intents.add(SCHEDULED_EVENTS);
                continue;
            }
            if (GenericGuildInviteEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_INVITES);
                continue;
            }
            if (GenericGuildVoiceEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_VOICE_STATES);
                continue;
            }
            if (MessageBulkDeleteEvent.class.isAssignableFrom(event)) {
                intents.add(GUILD_MESSAGES);
                continue;
            }
            if (GenericMessageReactionEvent.class.isAssignableFrom(event)) {
                Collections.addAll(intents, GUILD_MESSAGE_REACTIONS, DIRECT_MESSAGE_REACTIONS);
                continue;
            }
            if (GenericMessageEvent.class.isAssignableFrom(event)) {
                Collections.addAll(intents, GUILD_MESSAGES, DIRECT_MESSAGES);
                continue;
            }
            if (UserTypingEvent.class.isAssignableFrom(event)) {
                Collections.addAll(intents, GUILD_MESSAGE_TYPING, DIRECT_MESSAGE_TYPING);
                continue;
            }
            if (AutoModExecutionEvent.class.isAssignableFrom(event)) {
                intents.add(AUTO_MODERATION_EXECUTION);
                continue;
            }
            if (!GenericAutoModRuleEvent.class.isAssignableFrom(event)) continue;
            intents.add(AUTO_MODERATION_CONFIGURATION);
        }
        return intents;
    }

    @Nonnull
    public static EnumSet<GatewayIntent> from(@Nonnull Collection<Class<? extends GenericEvent>> events, @Nonnull Collection<CacheFlag> flags) {
        EnumSet<GatewayIntent> intents = GatewayIntent.fromEvents(events);
        intents.addAll(GatewayIntent.fromCacheFlags(flags));
        return intents;
    }

    static {
        ALL_INTENTS = 1 | GatewayIntent.getRaw(EnumSet.allOf(GatewayIntent.class));
        DEFAULT = ALL_INTENTS & ~GatewayIntent.getRaw(GUILD_MEMBERS, GUILD_PRESENCES, MESSAGE_CONTENT, GUILD_WEBHOOKS, GUILD_MESSAGE_TYPING, DIRECT_MESSAGE_TYPING);
    }
}

