/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.sequences;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.functions.Function1;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.sequences.DistinctIterator;
import com.hypherionmc.sdlink.shaded.kotlin.sequences.Sequence;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0096\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/sequences/DistinctSequence;", "T", "K", "Lcom/hypherionmc/sdlink/shaded/kotlin/sequences/Sequence;", "source", "keySelector", "Lcom/hypherionmc/sdlink/shaded/kotlin/Function1;", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public final class DistinctSequence<T, K>
implements Sequence<T> {
    @NotNull
    private final Sequence<T> source;
    @NotNull
    private final Function1<T, K> keySelector;

    public DistinctSequence(@NotNull Sequence<? extends T> source2, @NotNull Function1<? super T, ? extends K> keySelector) {
        Intrinsics.checkNotNullParameter(source2, "source");
        Intrinsics.checkNotNullParameter(keySelector, "keySelector");
        this.source = source2;
        this.keySelector = keySelector;
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new DistinctIterator<T, K>(this.source.iterator(), this.keySelector);
    }
}

