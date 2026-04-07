/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.FunctorException;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import java.io.Serializable;

public final class ExceptionPredicate<T>
implements Predicate<T>,
Serializable {
    private static final long serialVersionUID = 7179106032121985545L;
    public static final Predicate INSTANCE = new ExceptionPredicate();

    public static <T> Predicate<T> exceptionPredicate() {
        return INSTANCE;
    }

    private ExceptionPredicate() {
    }

    @Override
    public boolean evaluate(T object) {
        throw new FunctorException("ExceptionPredicate invoked");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

