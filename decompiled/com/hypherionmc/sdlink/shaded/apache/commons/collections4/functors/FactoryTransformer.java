/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Factory;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import java.io.Serializable;

public class FactoryTransformer<I, O>
implements Transformer<I, O>,
Serializable {
    private static final long serialVersionUID = -6817674502475353160L;
    private final Factory<? extends O> iFactory;

    public static <I, O> Transformer<I, O> factoryTransformer(Factory<? extends O> factory2) {
        if (factory2 == null) {
            throw new NullPointerException("Factory must not be null");
        }
        return new FactoryTransformer<I, O>(factory2);
    }

    public FactoryTransformer(Factory<? extends O> factory2) {
        this.iFactory = factory2;
    }

    @Override
    public O transform(I input) {
        return this.iFactory.create();
    }

    public Factory<? extends O> getFactory() {
        return this.iFactory;
    }
}

