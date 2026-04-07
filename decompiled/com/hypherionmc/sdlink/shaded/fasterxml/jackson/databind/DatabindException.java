/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonLocation;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonProcessingException;

public abstract class DatabindException
extends JsonProcessingException {
    private static final long serialVersionUID = 3L;

    protected DatabindException(String msg, JsonLocation loc, Throwable rootCause) {
        super(msg, loc, rootCause);
    }

    protected DatabindException(String msg) {
        super(msg);
    }

    protected DatabindException(String msg, JsonLocation loc) {
        this(msg, loc, null);
    }

    protected DatabindException(String msg, Throwable rootCause) {
        this(msg, null, rootCause);
    }

    public abstract void prependPath(Object var1, String var2);

    public abstract void prependPath(Object var1, int var2);
}

