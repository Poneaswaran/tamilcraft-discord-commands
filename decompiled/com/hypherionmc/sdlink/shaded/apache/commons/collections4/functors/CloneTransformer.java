/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.PrototypeFactory;

public class CloneTransformer<T>
implements Transformer<T, T> {
    public static final Transformer INSTANCE = new CloneTransformer();

    public static <T> Transformer<T, T> cloneTransformer() {
        return INSTANCE;
    }

    private CloneTransformer() {
    }

    @Override
    public T transform(T input) {
        if (input == null) {
            return null;
        }
        return PrototypeFactory.prototypeFactory(input).create();
    }
}

