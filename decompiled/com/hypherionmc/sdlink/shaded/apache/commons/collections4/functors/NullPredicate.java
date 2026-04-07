/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import java.io.Serializable;

public final class NullPredicate<T>
implements Predicate<T>,
Serializable {
    private static final long serialVersionUID = 7533784454832764388L;
    public static final Predicate INSTANCE = new NullPredicate();

    public static <T> Predicate<T> nullPredicate() {
        return INSTANCE;
    }

    private NullPredicate() {
    }

    @Override
    public boolean evaluate(T object) {
        return object == null;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

