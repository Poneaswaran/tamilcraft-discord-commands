/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Blocking
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.concurrent;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.jetbrains.annotations.Blocking;

public interface Task<T> {
    public boolean isStarted();

    @Nonnull
    public Task<T> onError(@Nonnull Consumer<? super Throwable> var1);

    @Nonnull
    public Task<T> onSuccess(@Nonnull Consumer<? super T> var1);

    @Nonnull
    public Task<T> setTimeout(@Nonnull Duration var1);

    @Nonnull
    default public Task<T> setTimeout(long timeout2, TimeUnit unit) {
        Checks.notNull((Object)unit, "TimeUnit");
        return this.setTimeout(Duration.ofMillis(unit.toMillis(timeout2)));
    }

    @Nonnull
    @Blocking
    public T get();

    public void cancel();
}

