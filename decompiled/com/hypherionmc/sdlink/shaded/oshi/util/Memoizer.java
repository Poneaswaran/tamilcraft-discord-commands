/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.util;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.GlobalConfig;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@ThreadSafe
public final class Memoizer {
    private static final Supplier<Long> defaultExpirationNanos = Memoizer.memoize(Memoizer::queryExpirationConfig, TimeUnit.MINUTES.toNanos(1L));

    private Memoizer() {
    }

    private static long queryExpirationConfig() {
        return TimeUnit.MILLISECONDS.toNanos(GlobalConfig.get("com.hypherionmc.sdlink.shaded.oshi.util.memoizer.expiration", 300));
    }

    public static long defaultExpiration() {
        return defaultExpirationNanos.get();
    }

    public static <T> Supplier<T> memoize(final Supplier<T> original, final long ttlNanos) {
        return new Supplier<T>(){
            final Supplier<T> delegate;
            volatile T value;
            volatile long expirationNanos;
            {
                this.delegate = original;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public T get() {
                long nanos = this.expirationNanos;
                long now = System.nanoTime();
                if (nanos == 0L || ttlNanos >= 0L && now - nanos >= 0L) {
                    1 var5_3 = this;
                    synchronized (var5_3) {
                        if (nanos == this.expirationNanos) {
                            Object t = this.delegate.get();
                            this.value = t;
                            nanos = now + ttlNanos;
                            this.expirationNanos = nanos == 0L ? 1L : nanos;
                            return t;
                        }
                    }
                }
                return this.value;
            }
        };
    }

    public static <T> Supplier<T> memoize(Supplier<T> original) {
        return Memoizer.memoize(original, -1L);
    }
}

