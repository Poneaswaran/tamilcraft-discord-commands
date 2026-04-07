/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.cache;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.Unit;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.functions.Function1;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okio.Buffer;
import com.hypherionmc.sdlink.shaded.okio.ForwardingSink;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0010\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u0007H\u0016J\b\u0010\u000e\u001a\u00020\u0007H\u0016J\u0018\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0014"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/cache/FaultHidingSink;", "Lcom/hypherionmc/sdlink/shaded/okio/ForwardingSink;", "delegate", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "onException", "Lcom/hypherionmc/sdlink/shaded/kotlin/Function1;", "Ljava/io/IOException;", "", "(Lokio/Sink;Lkotlin/jvm/functions/Function1;)V", "hasErrors", "", "getOnException", "()Lkotlin/jvm/functions/Function1;", "close", "flush", "write", "source", "Lcom/hypherionmc/sdlink/shaded/okio/Buffer;", "byteCount", "", "okhttp"})
public class FaultHidingSink
extends ForwardingSink {
    @NotNull
    private final Function1<IOException, Unit> onException;
    private boolean hasErrors;

    public FaultHidingSink(@NotNull Sink delegate, @NotNull Function1<? super IOException, Unit> onException) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(onException, "onException");
        super(delegate);
        this.onException = onException;
    }

    @NotNull
    public final Function1<IOException, Unit> getOnException() {
        return this.onException;
    }

    @Override
    public void write(@NotNull Buffer source2, long byteCount) {
        Intrinsics.checkNotNullParameter(source2, "source");
        if (this.hasErrors) {
            source2.skip(byteCount);
            return;
        }
        try {
            super.write(source2, byteCount);
        }
        catch (IOException e) {
            this.hasErrors = true;
            this.onException.invoke(e);
        }
    }

    @Override
    public void flush() {
        if (this.hasErrors) {
            return;
        }
        try {
            super.flush();
        }
        catch (IOException e) {
            this.hasErrors = true;
            this.onException.invoke(e);
        }
    }

    @Override
    public void close() {
        if (this.hasErrors) {
            return;
        }
        try {
            super.close();
        }
        catch (IOException e) {
            this.hasErrors = true;
            this.onException.invoke(e);
        }
    }
}

