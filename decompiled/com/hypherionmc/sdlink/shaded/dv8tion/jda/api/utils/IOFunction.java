/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.UnknownNullability;
import java.io.IOException;

@FunctionalInterface
public interface IOFunction<T, R> {
    @UnknownNullability
    public R apply(T var1) throws IOException;
}

