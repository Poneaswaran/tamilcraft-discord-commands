/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsonschema;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@JacksonAnnotation
@Deprecated
public @interface JsonSerializableSchema {
    public static final String NO_VALUE = "##irrelevant";

    public String id() default "";

    public String schemaType() default "any";

    @Deprecated
    public String schemaObjectPropertiesDefinition() default "##irrelevant";

    @Deprecated
    public String schemaItemDefinition() default "##irrelevant";
}

