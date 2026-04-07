/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.StageInstanceManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface StageInstance
extends ISnowflake {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    public StageChannel getChannel();

    @Nonnull
    public String getTopic();

    @Nonnull
    public PrivacyLevel getPrivacyLevel();

    @Nonnull
    default public @Unmodifiable List<Member> getSpeakers() {
        return this.getChannel().getMembers().stream().filter(member -> !member.getVoiceState().isSuppressed()).collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    default public @Unmodifiable List<Member> getAudience() {
        return this.getChannel().getMembers().stream().filter(member -> member.getVoiceState().isSuppressed()).collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public StageInstanceManager getManager();

    public static enum PrivacyLevel {
        UNKNOWN(-1),
        GUILD_ONLY(2);

        private final int key;

        private PrivacyLevel(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static PrivacyLevel fromKey(int key) {
            for (PrivacyLevel level : PrivacyLevel.values()) {
                if (level.key != key) continue;
                return level;
            }
            return UNKNOWN;
        }
    }
}

