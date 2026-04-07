/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.requestbody;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.requestbody.TypedBody;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okio.BufferedSink;
import com.hypherionmc.sdlink.shaded.okio.Source;
import java.io.IOException;
import java.util.function.Supplier;

public class DataSupplierBody
extends TypedBody<DataSupplierBody> {
    private final Supplier<? extends Source> streamSupply;

    public DataSupplierBody(MediaType type, Supplier<? extends Source> streamSupply) {
        super(type);
        this.streamSupply = streamSupply;
    }

    @Override
    @Nonnull
    public DataSupplierBody withType(@Nonnull MediaType newType) {
        if (this.type.equals(newType)) {
            return this;
        }
        return new DataSupplierBody(newType, this.streamSupply);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeTo(@Nonnull BufferedSink bufferedSink) throws IOException {
        Supplier<? extends Source> supplier = this.streamSupply;
        synchronized (supplier) {
            try (Source stream = this.streamSupply.get();){
                bufferedSink.writeAll(stream);
            }
        }
    }
}

