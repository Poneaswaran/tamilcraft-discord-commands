/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.jvm;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationRetention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationTarget;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.MustBeDocumented;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Retention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(allowedTargets={AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER})
@Retention(value=AnnotationRetention.RUNTIME)
@MustBeDocumented
@Documented
@java.lang.annotation.Retention(value=RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value={ElementType.METHOD})
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000\u00a8\u0006\u0002"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/jvm/JvmStatic;", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
public @interface JvmStatic {
}

