/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

@FunctionalInterface
public interface Procedure<T> {
    public boolean execute(@Nonnull T var1);
}

