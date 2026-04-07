/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModEventType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public enum AutoModTriggerType {
    KEYWORD(1, 6, AutoModEventType.MESSAGE_SEND, AutoModEventType.MEMBER_UPDATE),
    SPAM(3, 1, AutoModEventType.MESSAGE_SEND),
    KEYWORD_PRESET(4, 1, AutoModEventType.MESSAGE_SEND),
    MENTION_SPAM(5, 1, AutoModEventType.MESSAGE_SEND),
    MEMBER_PROFILE_KEYWORD(6, 1, AutoModEventType.MEMBER_UPDATE),
    UNKNOWN(-1, 0, new AutoModEventType[0]);

    private final int key;
    private final int maxPerGuild;
    private final EnumSet<AutoModEventType> eventTypes;

    private AutoModTriggerType(int key, int maxPerGuild, AutoModEventType ... supportedEvents) {
        this.key = key;
        this.maxPerGuild = maxPerGuild;
        this.eventTypes = supportedEvents.length > 0 ? EnumSet.of(supportedEvents[0], supportedEvents) : EnumSet.noneOf(AutoModEventType.class);
    }

    public int getKey() {
        return this.key;
    }

    public int getMaxPerGuild() {
        return this.maxPerGuild;
    }

    @Nonnull
    public EnumSet<AutoModEventType> getSupportedEventTypes() {
        return Helpers.copyEnumSet(AutoModEventType.class, this.eventTypes);
    }

    public boolean isEventTypeSupported(@Nonnull AutoModEventType type) {
        return type != null && this.eventTypes.contains((Object)type);
    }

    @Nonnull
    public static AutoModTriggerType fromKey(int key) {
        for (AutoModTriggerType trigger : AutoModTriggerType.values()) {
            if (trigger.key != key) continue;
            return trigger;
        }
        return UNKNOWN;
    }
}

