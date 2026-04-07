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
import com.hypherionmc.sdlink.shaded.kotlin.jvm.functions.Function0;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\fJ\r\u0010\b\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\tJ\n\u0010\n\u001a\u0004\u0018\u00010\u000bH'R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\r"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty0;", "V", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty;", "Lcom/hypherionmc/sdlink/shaded/kotlin/Function0;", "getter", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty0$Getter;", "getGetter", "()Lkotlin/reflect/KProperty0$Getter;", "get", "()Ljava/lang/Object;", "getDelegate", "", "Getter", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public interface KProperty0<V>
extends KProperty<V>,
Function0<V> {
    public V get();

    @SinceKotlin(version="1.1")
    @Nullable
    public Object getDelegate();

    @Override
    @NotNull
    public Getter<V> getGetter();

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty0$Getter;", "V", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty$Getter;", "Lcom/hypherionmc/sdlink/shaded/kotlin/Function0;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
    public static interface Getter<V>
    extends KProperty.Getter<V>,
    Function0<V> {
    }
}

