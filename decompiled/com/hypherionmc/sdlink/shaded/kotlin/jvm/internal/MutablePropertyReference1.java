/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.MutablePropertyReference;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Reflection;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KCallable;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KMutableProperty1;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KProperty1;

public abstract class MutablePropertyReference1
extends MutablePropertyReference
implements KMutableProperty1 {
    public MutablePropertyReference1() {
    }

    @SinceKotlin(version="1.1")
    public MutablePropertyReference1(Object receiver) {
        super(receiver);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference1(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.mutableProperty1(this);
    }

    @Override
    public Object invoke(Object receiver) {
        return this.get(receiver);
    }

    @Override
    public KProperty1.Getter getGetter() {
        return ((KMutableProperty1)this.getReflected()).getGetter();
    }

    public KMutableProperty1.Setter getSetter() {
        return ((KMutableProperty1)this.getReflected()).getSetter();
    }

    @Override
    @SinceKotlin(version="1.1")
    public Object getDelegate(Object receiver) {
        return ((KMutableProperty1)this.getReflected()).getDelegate(receiver);
    }
}

