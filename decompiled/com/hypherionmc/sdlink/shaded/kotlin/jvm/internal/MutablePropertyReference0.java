/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.MutablePropertyReference;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Reflection;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KCallable;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KMutableProperty0;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KProperty0;

public abstract class MutablePropertyReference0
extends MutablePropertyReference
implements KMutableProperty0 {
    public MutablePropertyReference0() {
    }

    @SinceKotlin(version="1.1")
    public MutablePropertyReference0(Object receiver) {
        super(receiver);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference0(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.mutableProperty0(this);
    }

    @Override
    public Object invoke() {
        return this.get();
    }

    @Override
    public KProperty0.Getter getGetter() {
        return ((KMutableProperty0)this.getReflected()).getGetter();
    }

    public KMutableProperty0.Setter getSetter() {
        return ((KMutableProperty0)this.getReflected()).getSetter();
    }

    @Override
    @SinceKotlin(version="1.1")
    public Object getDelegate() {
        return ((KMutableProperty0)this.getReflected()).getDelegate();
    }
}

