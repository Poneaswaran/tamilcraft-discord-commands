/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.kotlin;

import com.hypherionmc.sdlink.shaded.kotlin.Lazy;
import com.hypherionmc.sdlink.shaded.kotlin.LazyThreadSafetyMode;
import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.NoWhenBranchMatchedException;
import com.hypherionmc.sdlink.shaded.kotlin.SafePublicationLazyImpl;
import com.hypherionmc.sdlink.shaded.kotlin.SynchronizedLazyImpl;
import com.hypherionmc.sdlink.shaded.kotlin.UnsafeLazyImpl;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.functions.Function0;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u00a8\u0006\t"}, d2={"lazy", "Lcom/hypherionmc/sdlink/shaded/kotlin/Lazy;", "T", "initializer", "Lcom/hypherionmc/sdlink/shaded/kotlin/Function0;", "lock", "", "mode", "Lcom/hypherionmc/sdlink/shaded/kotlin/LazyThreadSafetyMode;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"}, xs="com/hypherionmc/sdlink/shaded/kotlin/LazyKt")
class LazyKt__LazyJVMKt {
    @NotNull
    public static final <T> Lazy<T> lazy(@NotNull Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        return new SynchronizedLazyImpl(initializer, null, 2, null);
    }

    @NotNull
    public static final <T> Lazy<T> lazy(@NotNull LazyThreadSafetyMode mode, @NotNull Function0<? extends T> initializer) {
        Lazy lazy;
        Intrinsics.checkNotNullParameter((Object)mode, "mode");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        switch (WhenMappings.$EnumSwitchMapping$0[mode.ordinal()]) {
            case 1: {
                lazy = new SynchronizedLazyImpl(initializer, null, 2, null);
                break;
            }
            case 2: {
                lazy = new SafePublicationLazyImpl<T>(initializer);
                break;
            }
            case 3: {
                lazy = new UnsafeLazyImpl<T>(initializer);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return lazy;
    }

    @NotNull
    public static final <T> Lazy<T> lazy(@Nullable Object lock, @NotNull Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        return new SynchronizedLazyImpl<T>(initializer, lock);
    }

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[LazyThreadSafetyMode.values().length];
            try {
                nArray[LazyThreadSafetyMode.SYNCHRONIZED.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[LazyThreadSafetyMode.PUBLICATION.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[LazyThreadSafetyMode.NONE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

