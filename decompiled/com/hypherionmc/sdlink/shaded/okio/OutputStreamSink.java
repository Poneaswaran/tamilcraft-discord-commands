/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.SourceDebugExtension;
import com.hypherionmc.sdlink.shaded.okio.-SegmentedByteString;
import com.hypherionmc.sdlink.shaded.okio.Buffer;
import com.hypherionmc.sdlink.shaded.okio.Segment;
import com.hypherionmc.sdlink.shaded.okio.SegmentPool;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import com.hypherionmc.sdlink.shaded.okio.Timeout;
import java.io.OutputStream;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lcom/hypherionmc/sdlink/shaded/okio/OutputStreamSink;", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "out", "Ljava/io/OutputStream;", "timeout", "Lcom/hypherionmc/sdlink/shaded/okio/Timeout;", "(Ljava/io/OutputStream;Lokio/Timeout;)V", "close", "", "flush", "toString", "", "write", "source", "Lcom/hypherionmc/sdlink/shaded/okio/Buffer;", "byteCount", "", "com.hypherionmc.sdlink.shaded.okio"})
@SourceDebugExtension(value={"SMAP\nJvmOkio.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JvmOkio.kt\nokio/OutputStreamSink\n+ 2 Util.kt\nokio/-SegmentedByteString\n*L\n1#1,239:1\n86#2:240\n*S KotlinDebug\n*F\n+ 1 JvmOkio.kt\nokio/OutputStreamSink\n*L\n55#1:240\n*E\n"})
final class OutputStreamSink
implements Sink {
    @NotNull
    private final OutputStream out;
    @NotNull
    private final Timeout timeout;

    public OutputStreamSink(@NotNull OutputStream out, @NotNull Timeout timeout2) {
        Intrinsics.checkNotNullParameter(out, "out");
        Intrinsics.checkNotNullParameter(timeout2, "timeout");
        this.out = out;
        this.timeout = timeout2;
    }

    @Override
    public void write(@NotNull Buffer source2, long byteCount) {
        int toCopy;
        Intrinsics.checkNotNullParameter(source2, "source");
        -SegmentedByteString.checkOffsetAndCount(source2.size(), 0L, byteCount);
        for (long remaining = byteCount; remaining > 0L; remaining -= (long)toCopy) {
            Segment head;
            this.timeout.throwIfReached();
            Intrinsics.checkNotNull(source2.head);
            int b$iv = head.limit - head.pos;
            boolean $i$f$minOf = false;
            toCopy = (int)Math.min(remaining, (long)b$iv);
            this.out.write(head.data, head.pos, toCopy);
            head.pos += toCopy;
            source2.setSize$okio(source2.size() - (long)toCopy);
            if (head.pos != head.limit) continue;
            source2.head = head.pop();
            SegmentPool.recycle(head);
        }
    }

    @Override
    public void flush() {
        this.out.flush();
    }

    @Override
    public void close() {
        this.out.close();
    }

    @Override
    @NotNull
    public Timeout timeout() {
        return this.timeout;
    }

    @NotNull
    public String toString() {
        return "sink(" + this.out + ')';
    }
}

