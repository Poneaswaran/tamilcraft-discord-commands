/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.requestbody;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;

public abstract class TypedBody<T extends TypedBody<T>>
extends RequestBody {
    protected final MediaType type;

    protected TypedBody(MediaType type) {
        this.type = type;
    }

    @Nonnull
    public abstract T withType(@Nonnull MediaType var1);

    @Override
    @Nullable
    public MediaType contentType() {
        return this.type;
    }
}

