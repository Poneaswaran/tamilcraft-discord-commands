/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Factory;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.FunctorException;
import java.io.Serializable;

public final class ExceptionFactory<T>
implements Factory<T>,
Serializable {
    private static final long serialVersionUID = 7179106032121985545L;
    public static final Factory INSTANCE = new ExceptionFactory();

    public static <T> Factory<T> exceptionFactory() {
        return INSTANCE;
    }

    private ExceptionFactory() {
    }

    @Override
    public T create() {
        throw new FunctorException("ExceptionFactory invoked");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

