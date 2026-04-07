/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import java.util.Iterator;

public class TransformIterator<I, O>
implements Iterator<O> {
    private Iterator<? extends I> iterator;
    private Transformer<? super I, ? extends O> transformer;

    public TransformIterator() {
    }

    public TransformIterator(Iterator<? extends I> iterator2) {
        this.iterator = iterator2;
    }

    public TransformIterator(Iterator<? extends I> iterator2, Transformer<? super I, ? extends O> transformer) {
        this.iterator = iterator2;
        this.transformer = transformer;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public O next() {
        return this.transform(this.iterator.next());
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }

    public Iterator<? extends I> getIterator() {
        return this.iterator;
    }

    public void setIterator(Iterator<? extends I> iterator2) {
        this.iterator = iterator2;
    }

    public Transformer<? super I, ? extends O> getTransformer() {
        return this.transformer;
    }

    public void setTransformer(Transformer<? super I, ? extends O> transformer) {
        this.transformer = transformer;
    }

    protected O transform(I source2) {
        return this.transformer.transform(source2);
    }
}

