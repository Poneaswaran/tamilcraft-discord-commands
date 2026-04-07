/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ChannelManager<T extends GuildChannel, M extends ChannelManager<T, M>>
extends Manager<M> {
    public static final long NAME = 1L;
    public static final long PARENT = 2L;
    public static final long TOPIC = 4L;
    public static final long POSITION = 8L;
    public static final long NSFW = 16L;
    public static final long USERLIMIT = 32L;
    public static final long BITRATE = 64L;
    public static final long PERMISSION = 128L;
    public static final long SLOWMODE = 256L;
    public static final long TYPE = 512L;
    public static final long REGION = 1024L;
    public static final long AUTO_ARCHIVE_DURATION = 2048L;
    public static final long ARCHIVED = 4096L;
    public static final long LOCKED = 8192L;
    public static final long INVITEABLE = 16384L;
    public static final long AVAILABLE_TAGS = 32768L;
    public static final long APPLIED_TAGS = 65536L;
    public static final long PINNED = 131072L;
    public static final long REQUIRE_TAG = 262144L;
    public static final long DEFAULT_REACTION = 524288L;
    public static final long DEFAULT_LAYOUT = 0x100000L;
    public static final long DEFAULT_SORT_ORDER = 0x200000L;
    public static final long HIDE_MEDIA_DOWNLOAD_OPTIONS = 0x400000L;
    public static final long DEFAULT_THREAD_SLOWMODE = 0x800000L;

    @Override
    @Nonnull
    @CheckReturnValue
    public M reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public M reset(long ... var1);

    @Nonnull
    public T getChannel();

    @Nonnull
    default public Guild getGuild() {
        return this.getChannel().getGuild();
    }

    @Nonnull
    @CheckReturnValue
    public M setName(@Nonnull String var1);
}

