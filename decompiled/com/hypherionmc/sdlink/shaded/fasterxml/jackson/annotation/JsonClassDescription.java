/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotation
public @interface JsonClassDescription {
    public String value() default "";
}

