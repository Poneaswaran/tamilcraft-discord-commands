/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.reflect;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.Unit;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KFunction;
import com.hypherionmc.sdlink.shaded.kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u0007R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KMutableProperty;", "V", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty;", "setter", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KMutableProperty$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty$Setter;", "Setter", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public interface KMutableProperty<V>
extends KProperty<V> {
    @NotNull
    public Setter<V> getSetter();

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a8\u0006\u0005"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KMutableProperty$Setter;", "V", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KProperty$Accessor;", "Lcom/hypherionmc/sdlink/shaded/kotlin/reflect/KFunction;", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
    public static interface Setter<V>
    extends KProperty.Accessor<V>,
    KFunction<Unit> {
    }
}

