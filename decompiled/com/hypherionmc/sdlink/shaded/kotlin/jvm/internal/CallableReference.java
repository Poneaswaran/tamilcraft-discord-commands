/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.KotlinReflectionNotSupportedError;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Reflection;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KCallable;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KDeclarationContainer;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KParameter;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KType;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KTypeParameter;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KVisibility;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public abstract class CallableReference
implements KCallable,
Serializable {
    private transient KCallable reflected;
    @SinceKotlin(version="1.1")
    protected final Object receiver;
    @SinceKotlin(version="1.4")
    private final Class owner;
    @SinceKotlin(version="1.4")
    private final String name;
    @SinceKotlin(version="1.4")
    private final String signature;
    @SinceKotlin(version="1.4")
    private final boolean isTopLevel;
    @SinceKotlin(version="1.1")
    public static final Object NO_RECEIVER = NoReceiver.access$000();

    public CallableReference() {
        this(NO_RECEIVER);
    }

    @SinceKotlin(version="1.1")
    protected CallableReference(Object receiver) {
        this(receiver, null, null, null, false);
    }

    @SinceKotlin(version="1.4")
    protected CallableReference(Object receiver, Class owner, String name, String signature, boolean isTopLevel) {
        this.receiver = receiver;
        this.owner = owner;
        this.name = name;
        this.signature = signature;
        this.isTopLevel = isTopLevel;
    }

    protected abstract KCallable computeReflected();

    @SinceKotlin(version="1.1")
    public Object getBoundReceiver() {
        return this.receiver;
    }

    @SinceKotlin(version="1.1")
    public KCallable compute() {
        KCallable result = this.reflected;
        if (result == null) {
            this.reflected = result = this.computeReflected();
        }
        return result;
    }

    @SinceKotlin(version="1.1")
    protected KCallable getReflected() {
        KCallable result = this.compute();
        if (result == this) {
            throw new KotlinReflectionNotSupportedError();
        }
        return result;
    }

    public KDeclarationContainer getOwner() {
        return this.owner == null ? null : (this.isTopLevel ? Reflection.getOrCreateKotlinPackage(this.owner) : Reflection.getOrCreateKotlinClass(this.owner));
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getSignature() {
        return this.signature;
    }

    @Override
    public List<KParameter> getParameters() {
        return this.getReflected().getParameters();
    }

    @Override
    public KType getReturnType() {
        return this.getReflected().getReturnType();
    }

    @Override
    public List<Annotation> getAnnotations() {
        return this.getReflected().getAnnotations();
    }

    @Override
    @SinceKotlin(version="1.1")
    public List<KTypeParameter> getTypeParameters() {
        return this.getReflected().getTypeParameters();
    }

    public Object call(Object ... args2) {
        return this.getReflected().call(args2);
    }

    public Object callBy(Map args2) {
        return this.getReflected().callBy(args2);
    }

    @Override
    @SinceKotlin(version="1.1")
    public KVisibility getVisibility() {
        return this.getReflected().getVisibility();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isFinal() {
        return this.getReflected().isFinal();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isOpen() {
        return this.getReflected().isOpen();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isAbstract() {
        return this.getReflected().isAbstract();
    }

    @Override
    @SinceKotlin(version="1.3")
    public boolean isSuspend() {
        return this.getReflected().isSuspend();
    }

    @SinceKotlin(version="1.2")
    private static class NoReceiver
    implements Serializable {
        private static final NoReceiver INSTANCE = new NoReceiver();

        private NoReceiver() {
        }

        private Object readResolve() throws ObjectStreamException {
            return INSTANCE;
        }

        static /* synthetic */ NoReceiver access$000() {
            return INSTANCE;
        }
    }
}

