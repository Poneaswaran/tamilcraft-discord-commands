/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.random;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.random.AbstractPlatformRandom;
import com.hypherionmc.sdlink.shaded.kotlin.random.FallbackThreadLocalRandom;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\b\b\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/random/FallbackThreadLocalRandom;", "Lcom/hypherionmc/sdlink/shaded/kotlin/random/AbstractPlatformRandom;", "()V", "impl", "Ljava/util/Random;", "getImpl", "()Ljava/util/Random;", "implStorage", "com/hypherionmc/sdlink/shaded/kotlin/random/FallbackThreadLocalRandom$implStorage$1", "Lcom/hypherionmc/sdlink/shaded/kotlin/random/FallbackThreadLocalRandom$implStorage$1;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public final class FallbackThreadLocalRandom
extends AbstractPlatformRandom {
    @NotNull
    private final implStorage.1 implStorage = new ThreadLocal<Random>(){

        @NotNull
        protected Random initialValue() {
            return new Random();
        }
    };

    @Override
    @NotNull
    public Random getImpl() {
        Object t = this.implStorage.get();
        Intrinsics.checkNotNullExpressionValue(t, "implStorage.get()");
        return (Random)t;
    }
}

