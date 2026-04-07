/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.temporal.TemporalAccessor;

public interface ScheduledEventManager
extends Manager<ScheduledEventManager> {
    public static final long NAME = 1L;
    public static final long DESCRIPTION = 2L;
    public static final long LOCATION = 4L;
    public static final long START_TIME = 8L;
    public static final long END_TIME = 16L;
    public static final long IMAGE = 32L;
    public static final long STATUS = 64L;

    @Override
    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager reset(long ... var1);

    @Nonnull
    public ScheduledEvent getScheduledEvent();

    @Nonnull
    default public Guild getGuild() {
        return this.getScheduledEvent().getGuild();
    }

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setDescription(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setImage(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setLocation(@Nonnull GuildChannel var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setLocation(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setStartTime(@Nonnull TemporalAccessor var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setEndTime(@Nonnull TemporalAccessor var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventManager setStatus(@Nonnull ScheduledEvent.Status var1);
}

