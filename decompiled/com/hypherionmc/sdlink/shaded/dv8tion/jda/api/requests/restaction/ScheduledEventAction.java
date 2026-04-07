/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.FluentAuditableRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.temporal.TemporalAccessor;

public interface ScheduledEventAction
extends FluentAuditableRestAction<ScheduledEvent, ScheduledEventAction> {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction setDescription(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction setStartTime(@Nonnull TemporalAccessor var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction setEndTime(@Nullable TemporalAccessor var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction setImage(@Nullable Icon var1);
}

