/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationRetention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationTarget;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Retention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(allowedTargets={AnnotationTarget.CLASS})
@Retention(value=AnnotationRetention.BINARY)
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.TYPE})
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\u0002\u0018\u00002\u00020\u0001B\u000e\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003R\u0015\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/jvm/internal/SourceDebugExtension;", "", "value", "", "", "()[Ljava/lang/String;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@SinceKotlin(version="1.8")
public @interface SourceDebugExtension {
    public String[] value();
}

