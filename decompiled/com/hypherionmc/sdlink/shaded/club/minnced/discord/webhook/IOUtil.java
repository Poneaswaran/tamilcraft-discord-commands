/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook;

import com.hypherionmc.sdlink.shaded.json.JSONObject;
import com.hypherionmc.sdlink.shaded.json.JSONTokener;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okhttp3.ResponseBody;
import com.hypherionmc.sdlink.shaded.okio.BufferedSink;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IOUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType OCTET = MediaType.parse("application/octet-stream; charset=utf-8");
    public static final byte[] EMPTY_BYTES = new byte[0];
    private static final CompletableFuture[] EMPTY_FUTURES = new CompletableFuture[0];

    @NotNull
    public static byte[] readAllBytes(@NotNull InputStream stream) throws IOException {
        int count = 0;
        int pos = 0;
        byte[] output = EMPTY_BYTES;
        byte[] buf = new byte[1024];
        while ((count = stream.read(buf)) > 0) {
            if (pos + count >= output.length) {
                byte[] tmp = output;
                output = new byte[pos + count];
                System.arraycopy(tmp, 0, output, 0, tmp.length);
            }
            for (int i = 0; i < count; ++i) {
                output[pos++] = buf[i];
            }
        }
        return output;
    }

    @Nullable
    public static InputStream getBody(@NotNull Response req) throws IOException {
        List<String> encoding = req.headers("content-encoding");
        ResponseBody body = req.body();
        if (!encoding.isEmpty() && body != null) {
            return new GZIPInputStream(body.byteStream());
        }
        return body != null ? body.byteStream() : null;
    }

    @NotNull
    public static JSONObject toJSON(@NotNull InputStream input) {
        return new JSONObject(new JSONTokener(input));
    }

    @NotNull
    public static <T> CompletableFuture<List<T>> flipFuture(@NotNull List<CompletableFuture<T>> list) {
        ArrayList result = new ArrayList(list.size());
        ArrayList updatedStages = new ArrayList(list.size());
        list.stream().map(it -> it.thenAccept(result::add)).forEach(updatedStages::add);
        CompletableFuture<Void> tracker = CompletableFuture.allOf(updatedStages.toArray(EMPTY_FUTURES));
        CompletableFuture future = new CompletableFuture();
        ((CompletableFuture)tracker.thenRun(() -> future.complete(result))).exceptionally(e -> {
            future.completeExceptionally((Throwable)e);
            return null;
        });
        return future;
    }

    public static class OctetBody
    extends RequestBody {
        private final byte[] data;

        public OctetBody(@NotNull byte[] data) {
            this.data = data;
        }

        @Override
        public MediaType contentType() {
            return OCTET;
        }

        @Override
        public void writeTo(BufferedSink sink2) throws IOException {
            sink2.write(this.data);
        }
    }

    public static class Lazy {
        private final SilentSupplier<?> supply;

        public Lazy(SilentSupplier<?> supply) {
            this.supply = supply;
        }

        @NotNull
        public String toString() {
            try {
                return String.valueOf(this.supply.get());
            }
            catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static interface SilentSupplier<T> {
        @Nullable
        public T get() throws Exception;
    }
}

