/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ErrorResponseException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

@FunctionalInterface
public interface ErrorMapper {
    @Nullable
    public Throwable apply(@Nonnull Response var1, @Nonnull Request<?> var2, @Nonnull ErrorResponseException var3);
}

