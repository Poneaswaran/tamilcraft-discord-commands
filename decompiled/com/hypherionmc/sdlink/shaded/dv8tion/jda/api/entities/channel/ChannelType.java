/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.GroupChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public enum ChannelType {
    TEXT(TextChannel.class, 0, 0, true),
    PRIVATE(PrivateChannel.class, 1, -1),
    VOICE(VoiceChannel.class, 2, 1, true),
    GROUP(GroupChannel.class, 3, -1),
    CATEGORY(Category.class, 4, 2, true),
    NEWS(NewsChannel.class, 5, 0, true),
    STAGE(StageChannel.class, 13, 1, true),
    GUILD_NEWS_THREAD(ThreadChannel.class, 10, -1, true),
    GUILD_PUBLIC_THREAD(ThreadChannel.class, 11, -1, true),
    GUILD_PRIVATE_THREAD(ThreadChannel.class, 12, -1, true),
    FORUM(ForumChannel.class, 15, 0, true),
    MEDIA(MediaChannel.class, 16, 0, true),
    UNKNOWN(Channel.class, -1, -2);

    private final int sortBucket;
    private final int id;
    private final boolean isGuild;
    private final Class<? extends Channel> clazz;

    private ChannelType(Class<? extends Channel> clazz, int id, int sortBucket) {
        this(clazz, id, sortBucket, false);
    }

    private ChannelType(Class<? extends Channel> clazz, int id, int sortBucket, boolean isGuild) {
        this.clazz = clazz;
        this.id = id;
        this.sortBucket = sortBucket;
        this.isGuild = isGuild;
    }

    @Nonnull
    public Class<? extends Channel> getInterface() {
        return this.clazz;
    }

    public int getSortBucket() {
        return this.sortBucket;
    }

    public int getId() {
        return this.id;
    }

    public boolean isGuild() {
        return this.isGuild;
    }

    public boolean isAudio() {
        switch (this) {
            case VOICE: 
            case STAGE: {
                return true;
            }
        }
        return false;
    }

    public boolean isMessage() {
        switch (this) {
            case VOICE: 
            case STAGE: 
            case TEXT: 
            case NEWS: 
            case PRIVATE: 
            case GROUP: {
                return true;
            }
        }
        return this.isThread();
    }

    public boolean isThread() {
        switch (this) {
            case GUILD_NEWS_THREAD: 
            case GUILD_PUBLIC_THREAD: 
            case GUILD_PRIVATE_THREAD: {
                return true;
            }
        }
        return false;
    }

    @Nonnull
    public static EnumSet<ChannelType> guildTypes() {
        return EnumSet.complementOf(EnumSet.of(PRIVATE, GROUP, UNKNOWN));
    }

    @Nonnull
    public static ChannelType fromId(int id) {
        for (ChannelType type : ChannelType.values()) {
            if (type.id != id) continue;
            return type;
        }
        return UNKNOWN;
    }

    @Nonnull
    public static EnumSet<ChannelType> fromSortBucket(int bucket) {
        EnumSet<ChannelType> types = EnumSet.noneOf(ChannelType.class);
        for (ChannelType type : ChannelType.values()) {
            if (type.getSortBucket() != bucket) continue;
            types.add(type);
        }
        return types;
    }
}

