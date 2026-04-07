/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.javax.annotation;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.meta.TypeQualifierNickname;
import com.hypherionmc.sdlink.shaded.javax.annotation.meta.When;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Nonnull(when=When.MAYBE)
@Retention(value=RetentionPolicy.RUNTIME)
@TypeQualifierNickname
public @interface CheckForNull {
}

