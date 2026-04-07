/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.http2;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmField;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.http2.ErrorCode;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/http2/StreamResetException;", "Ljava/io/IOException;", "errorCode", "Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/http2/ErrorCode;", "(Lokhttp3/internal/http2/ErrorCode;)V", "okhttp"})
public final class StreamResetException
extends IOException {
    @JvmField
    @NotNull
    public final ErrorCode errorCode;

    public StreamResetException(@NotNull ErrorCode errorCode) {
        Intrinsics.checkNotNullParameter((Object)errorCode, "errorCode");
        super("stream was reset: " + (Object)((Object)errorCode));
        this.errorCode = errorCode;
    }
}

