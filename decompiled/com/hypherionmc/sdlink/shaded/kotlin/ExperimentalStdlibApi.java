/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.RequiresOptIn;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationRetention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationTarget;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.MustBeDocumented;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Retention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(value=AnnotationRetention.BINARY)
@Target(allowedTargets={AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.TYPEALIAS})
@MustBeDocumented
@Documented
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000\u00a8\u0006\u0002"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/ExperimentalStdlibApi;", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@RequiresOptIn(level=RequiresOptIn.Level.ERROR)
@SinceKotlin(version="1.3")
public @interface ExperimentalStdlibApi {
}

