/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.kotlin.reflect;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.internal.IntrinsicConstEvaluation;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KAnnotatedElement;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KParameter;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KType;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KTypeParameter;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KVisibility;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J%\u0010%\u001a\u00028\u00002\u0016\u0010&\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010(0'\"\u0004\u0018\u00010(H&\u00a2\u0006\u0002\u0010)J#\u0010*\u001a\u00028\u00002\u0014\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u00010(0+H&\u00a2\u0006\u0002\u0010,R\u001a\u0010\u0003\u001a\u00020\u00048&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0007R\u001a\u0010\b\u001a\u00020\u00048&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\t\u0010\u0006\u001a\u0004\b\b\u0010\u0007R\u001a\u0010\n\u001a\u00020\u00048&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u000b\u0010\u0006\u001a\u0004\b\n\u0010\u0007R\u001a\u0010\f\u001a\u00020\u00048&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\r\u0010\u0006\u001a\u0004\b\f\u0010\u0007R\u001a\u0010\u000e\u001a\u00020\u000f8&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0019X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00148&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u001e\u0010\u0006\u001a\u0004\b\u001f\u0010\u0017R\u001c\u0010 \u001a\u0004\u0018\u00010!8&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\"\u0010\u0006\u001a\u0004\b#\u0010$\u00a8\u0006-"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KCallable;", "R", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KAnnotatedElement;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isFinal", "isFinal$annotations", "isOpen", "isOpen$annotations", "isSuspend", "isSuspend$annotations", "name", "", "getName$annotations", "getName", "()Ljava/lang/String;", "parameters", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KParameter;", "getParameters", "()Ljava/util/List;", "returnType", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KType;", "getReturnType", "()Lkotlin/reflect/KType;", "typeParameters", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "call", "args", "", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "callBy", "", "(Ljava/util/Map;)Ljava/lang/Object;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public interface KCallable<R>
extends KAnnotatedElement {
    @NotNull
    public String getName();

    @NotNull
    public List<KParameter> getParameters();

    @NotNull
    public KType getReturnType();

    @NotNull
    public List<KTypeParameter> getTypeParameters();

    public R call(Object ... var1);

    public R callBy(@NotNull Map<KParameter, ? extends Object> var1);

    @Nullable
    public KVisibility getVisibility();

    public boolean isFinal();

    public boolean isOpen();

    public boolean isAbstract();

    public boolean isSuspend();

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @IntrinsicConstEvaluation
        public static /* synthetic */ void getName$annotations() {
        }

        @SinceKotlin(version="1.1")
        public static /* synthetic */ void getTypeParameters$annotations() {
        }

        @SinceKotlin(version="1.1")
        public static /* synthetic */ void getVisibility$annotations() {
        }

        @SinceKotlin(version="1.1")
        public static /* synthetic */ void isFinal$annotations() {
        }

        @SinceKotlin(version="1.1")
        public static /* synthetic */ void isOpen$annotations() {
        }

        @SinceKotlin(version="1.1")
        public static /* synthetic */ void isAbstract$annotations() {
        }

        @SinceKotlin(version="1.3")
        public static /* synthetic */ void isSuspend$annotations() {
        }
    }
}

