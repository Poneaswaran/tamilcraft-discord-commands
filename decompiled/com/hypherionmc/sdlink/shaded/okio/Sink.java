/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.okio.Buffer;
import com.hypherionmc.sdlink.shaded.okio.Timeout;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\r\u00c0\u0006\u0001"}, d2={"Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "close", "", "flush", "timeout", "Lcom/hypherionmc/sdlink/shaded/okio/Timeout;", "write", "source", "Lcom/hypherionmc/sdlink/shaded/okio/Buffer;", "byteCount", "", "com.hypherionmc.sdlink.shaded.okio"})
public interface Sink
extends Closeable,
Flushable {
    public void write(@NotNull Buffer var1, long var2) throws IOException;

    @Override
    public void flush() throws IOException;

    @NotNull
    public Timeout timeout();

    @Override
    public void close() throws IOException;
}

