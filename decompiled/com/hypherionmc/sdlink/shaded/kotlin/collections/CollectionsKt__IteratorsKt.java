/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.collections;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.Unit;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt__IteratorsJVMKt;
import com.hypherionmc.sdlink.shaded.kotlin.collections.IndexedValue;
import com.hypherionmc.sdlink.shaded.kotlin.collections.IndexingIterator;
import com.hypherionmc.sdlink.shaded.kotlin.internal.InlineOnly;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.functions.Function1;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\u0086\b\u00f8\u0001\u0000\u001a\u001f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\n\u001a\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b0\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\t"}, d2={"forEach", "", "T", "", "operation", "Lcom/hypherionmc/sdlink/shaded/kotlin/Function1;", "iterator", "withIndex", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/IndexedValue;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"}, xs="com/hypherionmc/sdlink/shaded/kotlin/collections/CollectionsKt")
class CollectionsKt__IteratorsKt
extends CollectionsKt__IteratorsJVMKt {
    @InlineOnly
    private static final <T> Iterator<T> iterator(Iterator<? extends T> $this$iterator) {
        Intrinsics.checkNotNullParameter($this$iterator, "<this>");
        return $this$iterator;
    }

    @NotNull
    public static final <T> Iterator<IndexedValue<T>> withIndex(@NotNull Iterator<? extends T> $this$withIndex) {
        Intrinsics.checkNotNullParameter($this$withIndex, "<this>");
        return new IndexingIterator<T>($this$withIndex);
    }

    public static final <T> void forEach(@NotNull Iterator<? extends T> $this$forEach, @NotNull Function1<? super T, Unit> operation) {
        Intrinsics.checkNotNullParameter($this$forEach, "<this>");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$forEach = false;
        Iterator<T> iterator2 = $this$forEach;
        while (iterator2.hasNext()) {
            T element = iterator2.next();
            operation.invoke(element);
        }
    }
}

