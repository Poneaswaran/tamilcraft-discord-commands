/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.javax.annotation.meta;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.meta.When;
import java.lang.annotation.Annotation;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface TypeQualifierValidator<A extends Annotation> {
    @Nonnull
    public When forConstantValue(@Nonnull A var1, Object var2);
}

