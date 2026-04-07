/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.FunctorException;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import java.io.Serializable;

public final class ExceptionTransformer<I, O>
implements Transformer<I, O>,
Serializable {
    private static final long serialVersionUID = 7179106032121985545L;
    public static final Transformer INSTANCE = new ExceptionTransformer();

    public static <I, O> Transformer<I, O> exceptionTransformer() {
        return INSTANCE;
    }

    private ExceptionTransformer() {
    }

    @Override
    public O transform(I input) {
        throw new FunctorException("ExceptionTransformer invoked");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

