/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.IntIterator;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/jvm/internal/ArrayIntIterator;", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/IntIterator;", "array", "", "([I)V", "index", "", "hasNext", "", "nextInt", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
final class ArrayIntIterator
extends IntIterator {
    @NotNull
    private final int[] array;
    private int index;

    public ArrayIntIterator(@NotNull int[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.array.length;
    }

    @Override
    public int nextInt() {
        int n;
        try {
            n = this.index;
            this.index = n + 1;
            n = this.array[n];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            --this.index;
            throw new NoSuchElementException(e.getMessage());
        }
        return n;
    }
}

