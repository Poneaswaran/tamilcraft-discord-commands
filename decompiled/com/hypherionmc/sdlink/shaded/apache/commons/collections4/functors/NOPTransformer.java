/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import java.io.Serializable;

public class NOPTransformer<T>
implements Transformer<T, T>,
Serializable {
    private static final long serialVersionUID = 2133891748318574490L;
    public static final Transformer INSTANCE = new NOPTransformer();

    public static <T> Transformer<T, T> nopTransformer() {
        return INSTANCE;
    }

    private NOPTransformer() {
    }

    @Override
    public T transform(T input) {
        return input;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

