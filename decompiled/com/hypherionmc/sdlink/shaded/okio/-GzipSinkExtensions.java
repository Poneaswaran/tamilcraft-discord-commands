/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.sdlink.shaded.okio.GzipSink
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmName;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okio.GzipSink;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u00a8\u0006\u0003"}, d2={"gzip", "Lcom/hypherionmc/sdlink/shaded/okio/GzipSink;", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "com.hypherionmc.sdlink.shaded.okio"})
@JvmName(name="-GzipSinkExtensions")
public final class -GzipSinkExtensions {
    @NotNull
    public static final GzipSink gzip(@NotNull Sink $this$gzip) {
        Intrinsics.checkNotNullParameter($this$gzip, "<this>");
        boolean $i$f$gzip = false;
        return new GzipSink($this$gzip);
    }
}

