/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin;

import com.hypherionmc.sdlink.shaded.kotlin.KotlinVersion;
import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007\u00a8\u0006\u0005"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/KotlinVersionCurrentValue;", "", "()V", "get", "Lcom/hypherionmc/sdlink/shaded/kotlin/KotlinVersion;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
final class KotlinVersionCurrentValue {
    @NotNull
    public static final KotlinVersionCurrentValue INSTANCE = new KotlinVersionCurrentValue();

    private KotlinVersionCurrentValue() {
    }

    @JvmStatic
    @NotNull
    public static final KotlinVersion get() {
        return new KotlinVersion(1, 9, 10);
    }
}

