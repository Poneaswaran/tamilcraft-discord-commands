/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.BooleanIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.ByteIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CharIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.DoubleIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.FloatIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.IntIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.LongIterator;
import com.hypherionmc.sdlink.shaded.kotlin.collections.ShortIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayBooleanIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayByteIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayCharIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayDoubleIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayFloatIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayIntIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayLongIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.ArrayShortIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0019\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0002\u0018\u0002\n\u0002\u0010\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\u0010\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0017\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0005\u001a\u000e\u0010\u0000\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0007\u001a\u000e\u0010\u0000\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\t\u001a\u000e\u0010\u0000\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u000b\u001a\u000e\u0010\u0000\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\r\u001a\u000e\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000f\u001a\u000e\u0010\u0000\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0011\u00a8\u0006\u0012"}, d2={"iterator", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/BooleanIterator;", "array", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/ByteIterator;", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/CharIterator;", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/DoubleIterator;", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/FloatIterator;", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/IntIterator;", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/LongIterator;", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/ShortIterator;", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public final class ArrayIteratorsKt {
    @NotNull
    public static final ByteIterator iterator(@NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayByteIterator(array);
    }

    @NotNull
    public static final CharIterator iterator(@NotNull char[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayCharIterator(array);
    }

    @NotNull
    public static final ShortIterator iterator(@NotNull short[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayShortIterator(array);
    }

    @NotNull
    public static final IntIterator iterator(@NotNull int[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayIntIterator(array);
    }

    @NotNull
    public static final LongIterator iterator(@NotNull long[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayLongIterator(array);
    }

    @NotNull
    public static final FloatIterator iterator(@NotNull float[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayFloatIterator(array);
    }

    @NotNull
    public static final DoubleIterator iterator(@NotNull double[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayDoubleIterator(array);
    }

    @NotNull
    public static final BooleanIterator iterator(@NotNull boolean[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return new ArrayBooleanIterator(array);
    }
}

