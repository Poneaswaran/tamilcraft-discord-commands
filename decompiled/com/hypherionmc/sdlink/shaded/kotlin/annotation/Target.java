/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.annotation;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationTarget;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.MustBeDocumented;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(allowedTargets={AnnotationTarget.ANNOTATION_CLASS})
@MustBeDocumented
@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value={ElementType.ANNOTATION_TYPE})
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0014\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004R\u0017\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/annotation/Target;", "", "allowedTargets", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/annotation/AnnotationTarget;", "()[Lkotlin/annotation/AnnotationTarget;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public @interface Target {
    public AnnotationTarget[] allowedTargets();
}

