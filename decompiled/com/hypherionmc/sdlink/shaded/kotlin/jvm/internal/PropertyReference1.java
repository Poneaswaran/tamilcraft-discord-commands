/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.PropertyReference;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Reflection;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KCallable;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KProperty1;

public abstract class PropertyReference1
extends PropertyReference
implements KProperty1 {
    public PropertyReference1() {
    }

    @SinceKotlin(version="1.1")
    public PropertyReference1(Object receiver) {
        super(receiver);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference1(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.property1(this);
    }

    @Override
    public Object invoke(Object receiver) {
        return this.get(receiver);
    }

    public KProperty1.Getter getGetter() {
        return ((KProperty1)this.getReflected()).getGetter();
    }

    @SinceKotlin(version="1.1")
    public Object getDelegate(Object receiver) {
        return ((KProperty1)this.getReflected()).getDelegate(receiver);
    }
}

