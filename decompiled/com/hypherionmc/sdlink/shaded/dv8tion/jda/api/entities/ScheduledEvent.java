/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.ScheduledEventManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ScheduledEventMembersPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public interface ScheduledEvent
extends ISnowflake,
Comparable<ScheduledEvent> {
    public static final String JUMP_URL = "https://discord.com/events/%s/%s";
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 1000;
    public static final int MAX_LOCATION_LENGTH = 100;
    public static final String IMAGE_URL = "https://cdn.discordapp.com/guild-events/%s/%s.%s";

    @Nonnull
    public String getName();

    @Nullable
    public String getDescription();

    @Nullable
    public String getImageUrl();

    @Nullable
    default public ImageProxy getImage() {
        String imageUrl = this.getImageUrl();
        return imageUrl == null ? null : new ImageProxy(imageUrl);
    }

    @Nullable
    public User getCreator();

    public long getCreatorIdLong();

    @Nullable
    default public String getCreatorId() {
        return this.getCreatorIdLong() == 0L ? null : Long.toUnsignedString(this.getCreatorIdLong());
    }

    @Nonnull
    public Status getStatus();

    @Nonnull
    public Type getType();

    @Nonnull
    public OffsetDateTime getStartTime();

    @Nullable
    public OffsetDateTime getEndTime();

    @Nullable
    public GuildChannelUnion getChannel();

    @Nonnull
    public String getLocation();

    @Nonnull
    public String getJumpUrl();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public ScheduledEventMembersPaginationAction retrieveInterestedMembers();

    public int getInterestedUserCount();

    @Nonnull
    public Guild getGuild();

    @Nonnull
    default public JDA getJDA() {
        return this.getGuild().getJDA();
    }

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager getManager();

    @Override
    public int compareTo(@Nonnull ScheduledEvent var1);

    public static enum Type {
        UNKNOWN(-1),
        STAGE_INSTANCE(1),
        VOICE(2),
        EXTERNAL(3);

        private final int key;

        private Type(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        public boolean isChannel() {
            return this == STAGE_INSTANCE || this == VOICE;
        }

        @Nonnull
        public static Type fromKey(int key) {
            for (Type type : Type.values()) {
                if (type.getKey() != key) continue;
                return type;
            }
            return UNKNOWN;
        }
    }

    public static enum Status {
        UNKNOWN(-1),
        SCHEDULED(1),
        ACTIVE(2),
        COMPLETED(3),
        CANCELED(4);

        private final int key;

        private Status(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static Status fromKey(int key) {
            for (Status status : Status.values()) {
                if (status.getKey() != key) continue;
                return status;
            }
            return UNKNOWN;
        }
    }
}

