/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.http;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.ResponseBody;
import com.hypherionmc.sdlink.shaded.okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0004\u001a\u00020\u0005H\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/http/RealResponseBody;", "Lcom/hypherionmc/sdlink/shaded/okhttp3/ResponseBody;", "contentTypeString", "", "contentLength", "", "source", "Lcom/hypherionmc/sdlink/shaded/okio/BufferedSource;", "(Ljava/lang/String;JLokio/BufferedSource;)V", "contentType", "Lcom/hypherionmc/sdlink/shaded/okhttp3/MediaType;", "okhttp"})
public final class RealResponseBody
extends ResponseBody {
    @Nullable
    private final String contentTypeString;
    private final long contentLength;
    @NotNull
    private final BufferedSource source;

    public RealResponseBody(@Nullable String contentTypeString, long contentLength, @NotNull BufferedSource source2) {
        Intrinsics.checkNotNullParameter(source2, "source");
        this.contentTypeString = contentTypeString;
        this.contentLength = contentLength;
        this.source = source2;
    }

    @Override
    public long contentLength() {
        return this.contentLength;
    }

    @Override
    @Nullable
    public MediaType contentType() {
        String string = this.contentTypeString;
        return string != null ? MediaType.Companion.parse(string) : null;
    }

    @Override
    @NotNull
    public BufferedSource source() {
        return this.source;
    }
}

