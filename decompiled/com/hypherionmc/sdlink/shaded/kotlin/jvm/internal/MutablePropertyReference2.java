/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.MutablePropertyReference;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Reflection;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KCallable;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KMutableProperty2;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KProperty2;

public abstract class MutablePropertyReference2
extends MutablePropertyReference
implements KMutableProperty2 {
    public MutablePropertyReference2() {
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference2(Class owner, String name, String signature, int flags) {
        super(NO_RECEIVER, owner, name, signature, flags);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.mutableProperty2(this);
    }

    @Override
    public Object invoke(Object receiver1, Object receiver2) {
        return this.get(receiver1, receiver2);
    }

    @Override
    public KProperty2.Getter getGetter() {
        return ((KMutableProperty2)this.getReflected()).getGetter();
    }

    public KMutableProperty2.Setter getSetter() {
        return ((KMutableProperty2)this.getReflected()).getSetter();
    }

    @Override
    @SinceKotlin(version="1.1")
    public Object getDelegate(Object receiver1, Object receiver2) {
        return ((KMutableProperty2)this.getReflected()).getDelegate(receiver1, receiver2);
    }
}

