/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Deprecated;
import com.hypherionmc.sdlink.shaded.kotlin.DeprecationLevel;
import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.ReplaceWith;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okio.BufferedSink;
import com.hypherionmc.sdlink.shaded.okio.BufferedSource;
import com.hypherionmc.sdlink.shaded.okio.Okio;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import com.hypherionmc.sdlink.shaded.okio.Source;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

@Deprecated(message="changed in Okio 2.x")
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0007\u001a\u00020\u0004H\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0007J\u0010\u0010\b\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J)\u0010\n\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00160\u0015\"\u00020\u0016H\u0007\u00a2\u0006\u0002\u0010\u0017J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J)\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00160\u0015\"\u00020\u0016H\u0007\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001b"}, d2={"Lcom/hypherionmc/sdlink/shaded/okio/-DeprecatedOkio;", "", "()V", "appendingSink", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "file", "Ljava/io/File;", "blackhole", "buffer", "Lcom/hypherionmc/sdlink/shaded/okio/BufferedSink;", "sink", "Lcom/hypherionmc/sdlink/shaded/okio/BufferedSource;", "source", "Lcom/hypherionmc/sdlink/shaded/okio/Source;", "outputStream", "Ljava/io/OutputStream;", "socket", "Ljava/net/Socket;", "path", "Ljava/nio/file/Path;", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Sink;", "inputStream", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Source;", "com.hypherionmc.sdlink.shaded.okio"})
public final class -DeprecatedOkio {
    @NotNull
    public static final -DeprecatedOkio INSTANCE = new -DeprecatedOkio();

    private -DeprecatedOkio() {
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="file.appendingSink()", imports={"com.hypherionmc.sdlink.shaded.okio.appendingSink"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Sink appendingSink(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return Okio.appendingSink(file);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="sink.buffer()", imports={"com.hypherionmc.sdlink.shaded.okio.buffer"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final BufferedSink buffer(@NotNull Sink sink2) {
        Intrinsics.checkNotNullParameter(sink2, "sink");
        return Okio.buffer(sink2);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="source.buffer()", imports={"com.hypherionmc.sdlink.shaded.okio.buffer"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final BufferedSource buffer(@NotNull Source source2) {
        Intrinsics.checkNotNullParameter(source2, "source");
        return Okio.buffer(source2);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="file.sink()", imports={"com.hypherionmc.sdlink.shaded.okio.sink"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Sink sink(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return Okio.sink$default(file, false, 1, null);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="outputStream.sink()", imports={"com.hypherionmc.sdlink.shaded.okio.sink"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Sink sink(@NotNull OutputStream outputStream2) {
        Intrinsics.checkNotNullParameter(outputStream2, "outputStream");
        return Okio.sink(outputStream2);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="path.sink(*options)", imports={"com.hypherionmc.sdlink.shaded.okio.sink"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Sink sink(@NotNull Path path, OpenOption ... options) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(options, "options");
        return Okio.sink(path, Arrays.copyOf(options, options.length));
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="socket.sink()", imports={"com.hypherionmc.sdlink.shaded.okio.sink"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Sink sink(@NotNull Socket socket) {
        Intrinsics.checkNotNullParameter(socket, "socket");
        return Okio.sink(socket);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="file.source()", imports={"com.hypherionmc.sdlink.shaded.okio.source"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Source source(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return Okio.source(file);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="inputStream.source()", imports={"com.hypherionmc.sdlink.shaded.okio.source"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Source source(@NotNull InputStream inputStream2) {
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        return Okio.source(inputStream2);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="path.source(*options)", imports={"com.hypherionmc.sdlink.shaded.okio.source"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Source source(@NotNull Path path, OpenOption ... options) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(options, "options");
        return Okio.source(path, Arrays.copyOf(options, options.length));
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="socket.source()", imports={"com.hypherionmc.sdlink.shaded.okio.source"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Source source(@NotNull Socket socket) {
        Intrinsics.checkNotNullParameter(socket, "socket");
        return Okio.source(socket);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="blackholeSink()", imports={"com.hypherionmc.sdlink.shaded.okio.blackholeSink"}), level=DeprecationLevel.ERROR)
    @NotNull
    public final Sink blackhole() {
        return Okio.blackhole();
    }
}

