/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.random;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.DefaultConstructorMarker;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.random.AbstractPlatformRandom;
import java.io.Serializable;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000 \t2\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001\tB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/random/PlatformRandom;", "Lcom/hypherionmc/sdlink/shaded/kotlin/random/AbstractPlatformRandom;", "Ljava/io/Serializable;", "Lcom/hypherionmc/sdlink/shaded/kotlin/io/Serializable;", "impl", "Ljava/util/Random;", "(Ljava/util/Random;)V", "getImpl", "()Ljava/util/Random;", "Companion", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
final class PlatformRandom
extends AbstractPlatformRandom
implements Serializable {
    @NotNull
    private static final Companion Companion = new Companion(null);
    @NotNull
    private final Random impl;
    private static final long serialVersionUID = 0L;

    public PlatformRandom(@NotNull Random impl) {
        Intrinsics.checkNotNullParameter(impl, "impl");
        this.impl = impl;
    }

    @Override
    @NotNull
    public Random getImpl() {
        return this.impl;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/random/PlatformRandom$Companion;", "", "()V", "serialVersionUID", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
    private static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

