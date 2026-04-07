/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.javax.annotation;

import com.hypherionmc.sdlink.shaded.javax.annotation.meta.TypeQualifier;
import com.hypherionmc.sdlink.shaded.javax.annotation.meta.TypeQualifierValidator;
import com.hypherionmc.sdlink.shaded.javax.annotation.meta.When;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@TypeQualifier
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Nonnull {
    public When when() default When.ALWAYS;

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Checker
    implements TypeQualifierValidator<Nonnull> {
        @Override
        public When forConstantValue(Nonnull qualifierArgument, Object value) {
            if (value == null) {
                return When.NEVER;
            }
            return When.ALWAYS;
        }
    }
}

