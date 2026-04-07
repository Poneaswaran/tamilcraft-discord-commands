/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmName;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okio.DeflaterSink;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import java.util.zip.Deflater;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086\b\u00a8\u0006\u0005"}, d2={"deflate", "Lcom/hypherionmc/sdlink/shaded/okio/DeflaterSink;", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "deflater", "Ljava/util/zip/Deflater;", "com.hypherionmc.sdlink.shaded.okio"})
@JvmName(name="-DeflaterSinkExtensions")
public final class -DeflaterSinkExtensions {
    @NotNull
    public static final DeflaterSink deflate(@NotNull Sink $this$deflate, @NotNull Deflater deflater) {
        Intrinsics.checkNotNullParameter($this$deflate, "<this>");
        Intrinsics.checkNotNullParameter(deflater, "deflater");
        boolean $i$f$deflate = false;
        return new DeflaterSink($this$deflate, deflater);
    }

    public static /* synthetic */ DeflaterSink deflate$default(Sink $this$deflate_u24default, Deflater deflater, int n, Object object) {
        if ((n & 1) != 0) {
            deflater = new Deflater();
        }
        Intrinsics.checkNotNullParameter($this$deflate_u24default, "<this>");
        Intrinsics.checkNotNullParameter(deflater, "deflater");
        boolean $i$f$deflate = false;
        return new DeflaterSink($this$deflate_u24default, deflater);
    }
}

