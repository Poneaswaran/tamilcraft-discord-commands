/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.TypeBase;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.TypeBindings;

abstract class IdentityEqualityType
extends TypeBase {
    private static final long serialVersionUID = 1L;

    protected IdentityEqualityType(Class<?> raw, TypeBindings bindings, JavaType superClass, JavaType[] superInts, int hash, Object valueHandler, Object typeHandler, boolean asStatic) {
        super(raw, bindings, superClass, superInts, hash, valueHandler, typeHandler, asStatic);
    }

    @Override
    public final boolean equals(Object o) {
        return o == this;
    }

    @Override
    public final int hashCode() {
        return System.identityHashCode(this);
    }
}

