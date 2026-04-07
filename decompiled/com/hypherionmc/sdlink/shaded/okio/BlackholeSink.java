/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okio.Buffer;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import com.hypherionmc.sdlink.shaded.okio.Timeout;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016\u00a8\u0006\r"}, d2={"Lcom/hypherionmc/sdlink/shaded/okio/BlackholeSink;", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "()V", "close", "", "flush", "timeout", "Lcom/hypherionmc/sdlink/shaded/okio/Timeout;", "write", "source", "Lcom/hypherionmc/sdlink/shaded/okio/Buffer;", "byteCount", "", "com.hypherionmc.sdlink.shaded.okio"})
final class BlackholeSink
implements Sink {
    @Override
    public void write(@NotNull Buffer source2, long byteCount) {
        Intrinsics.checkNotNullParameter(source2, "source");
        source2.skip(byteCount);
    }

    @Override
    public void flush() {
    }

    @Override
    @NotNull
    public Timeout timeout() {
        return Timeout.NONE;
    }

    @Override
    public void close() {
    }
}

