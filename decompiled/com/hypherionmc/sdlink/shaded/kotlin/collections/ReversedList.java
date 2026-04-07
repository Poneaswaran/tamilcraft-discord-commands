/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.collections;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.AbstractMutableList;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt__ReversedViewsKt;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010)\n\u0000\n\u0002\u0010+\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0002\u0010\u0005J\u001d\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u000bH\u0016J\u0016\u0010\u0010\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u0007H\u0096\u0002\u00a2\u0006\u0002\u0010\u0011J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0096\u0002J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00152\u0006\u0010\f\u001a\u00020\u0007H\u0016J\u0015\u0010\u0016\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u0007H\u0016\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0017\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u0018R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0019"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/collections/ReversedList;", "T", "Lcom/hypherionmc/sdlink/shaded/kotlin/collections/AbstractMutableList;", "delegate", "", "(Ljava/util/List;)V", "size", "", "getSize", "()I", "add", "", "index", "element", "(ILjava/lang/Object;)V", "clear", "get", "(I)Ljava/lang/Object;", "iterator", "", "listIterator", "", "removeAt", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
final class ReversedList<T>
extends AbstractMutableList<T> {
    @NotNull
    private final List<T> delegate;

    public ReversedList(@NotNull List<T> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override
    public int getSize() {
        return this.delegate.size();
    }

    @Override
    public T get(int index) {
        return this.delegate.get(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, index));
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public T removeAt(int index) {
        return this.delegate.remove(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, index));
    }

    @Override
    public T set(int index, T element) {
        return this.delegate.set(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, index), element);
    }

    @Override
    public void add(int index, T element) {
        this.delegate.add(CollectionsKt__ReversedViewsKt.access$reversePositionIndex(this, index), element);
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return this.listIterator(0);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator() {
        return this.listIterator(0);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>(this, index){
            @NotNull
            private final ListIterator<T> delegateIterator;
            final /* synthetic */ ReversedList<T> this$0;
            {
                this.this$0 = $receiver;
                this.delegateIterator = ReversedList.access$getDelegate$p($receiver).listIterator(CollectionsKt__ReversedViewsKt.access$reversePositionIndex($receiver, $index));
            }

            @NotNull
            public final ListIterator<T> getDelegateIterator() {
                return this.delegateIterator;
            }

            public boolean hasNext() {
                return this.delegateIterator.hasPrevious();
            }

            public boolean hasPrevious() {
                return this.delegateIterator.hasNext();
            }

            public T next() {
                return this.delegateIterator.previous();
            }

            public int nextIndex() {
                return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(this.this$0, this.delegateIterator.previousIndex());
            }

            public T previous() {
                return this.delegateIterator.next();
            }

            public int previousIndex() {
                return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(this.this$0, this.delegateIterator.nextIndex());
            }

            public void add(T element) {
                this.delegateIterator.add(element);
                this.delegateIterator.previous();
            }

            public void remove() {
                this.delegateIterator.remove();
            }

            public void set(T element) {
                this.delegateIterator.set(element);
            }
        };
    }

    public static final /* synthetic */ List access$getDelegate$p(ReversedList $this) {
        return $this.delegate;
    }
}

