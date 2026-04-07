/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.collections;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt__MutableCollectionsKt;
import com.hypherionmc.sdlink.shaded.kotlin.collections.ReversedList;
import com.hypherionmc.sdlink.shaded.kotlin.collections.ReversedListReadOnly;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmName;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.ranges.IntRange;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0004\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\b\u001a\u001d\u0010\t\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\n\u001a\u001d\u0010\u000b\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\f\u00a8\u0006\r"}, d2={"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reverseIteratorIndex", "reverseIteratorIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"}, xs="com/hypherionmc/sdlink/shaded/kotlin/collections/CollectionsKt")
class CollectionsKt__ReversedViewsKt
extends CollectionsKt__MutableCollectionsKt {
    private static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reverseElementIndex, int index) {
        if (!new IntRange(0, CollectionsKt.getLastIndex($this$reverseElementIndex)).contains(index)) {
            throw new IndexOutOfBoundsException("Element index " + index + " must be in range [" + new IntRange(0, CollectionsKt.getLastIndex($this$reverseElementIndex)) + "].");
        }
        return CollectionsKt.getLastIndex($this$reverseElementIndex) - index;
    }

    private static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reversePositionIndex, int index) {
        if (!new IntRange(0, $this$reversePositionIndex.size()).contains(index)) {
            throw new IndexOutOfBoundsException("Position index " + index + " must be in range [" + new IntRange(0, $this$reversePositionIndex.size()) + "].");
        }
        return $this$reversePositionIndex.size() - index;
    }

    private static final int reverseIteratorIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reverseIteratorIndex, int index) {
        return CollectionsKt.getLastIndex($this$reverseIteratorIndex) - index;
    }

    @NotNull
    public static final <T> List<T> asReversed(@NotNull List<? extends T> $this$asReversed) {
        Intrinsics.checkNotNullParameter($this$asReversed, "<this>");
        return new ReversedListReadOnly<T>($this$asReversed);
    }

    @JvmName(name="asReversedMutable")
    @NotNull
    public static final <T> List<T> asReversedMutable(@NotNull List<T> $this$asReversed) {
        Intrinsics.checkNotNullParameter($this$asReversed, "<this>");
        return new ReversedList<T>($this$asReversed);
    }

    public static final /* synthetic */ int access$reverseElementIndex(List $receiver, int index) {
        return CollectionsKt__ReversedViewsKt.reverseElementIndex$CollectionsKt__ReversedViewsKt($receiver, index);
    }

    public static final /* synthetic */ int access$reversePositionIndex(List $receiver, int index) {
        return CollectionsKt__ReversedViewsKt.reversePositionIndex$CollectionsKt__ReversedViewsKt($receiver, index);
    }

    public static final /* synthetic */ int access$reverseIteratorIndex(List $receiver, int index) {
        return CollectionsKt__ReversedViewsKt.reverseIteratorIndex$CollectionsKt__ReversedViewsKt($receiver, index);
    }
}

