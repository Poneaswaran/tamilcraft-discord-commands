/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.cache;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/cache/CacheRequest;", "", "abort", "", "body", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "okhttp"})
public interface CacheRequest {
    @NotNull
    public Sink body() throws IOException;

    public void abort();
}

