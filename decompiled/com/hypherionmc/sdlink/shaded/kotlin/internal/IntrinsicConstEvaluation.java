/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.internal;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationRetention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationTarget;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Retention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(allowedTargets={AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY})
@Retention(value=AnnotationRetention.BINARY)
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0081\u0002\u0018\u00002\u00020\u0001B\u0000\u00a8\u0006\u0002"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/internal/IntrinsicConstEvaluation;", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@SinceKotlin(version="1.7")
public @interface IntrinsicConstEvaluation {
}

