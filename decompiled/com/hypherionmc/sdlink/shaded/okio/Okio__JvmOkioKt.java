/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmOverloads;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.text.StringsKt;
import com.hypherionmc.sdlink.shaded.okio.CipherSink;
import com.hypherionmc.sdlink.shaded.okio.CipherSource;
import com.hypherionmc.sdlink.shaded.okio.FileSystem;
import com.hypherionmc.sdlink.shaded.okio.HashingSink;
import com.hypherionmc.sdlink.shaded.okio.HashingSource;
import com.hypherionmc.sdlink.shaded.okio.InputStreamSource;
import com.hypherionmc.sdlink.shaded.okio.Okio;
import com.hypherionmc.sdlink.shaded.okio.OutputStreamSink;
import com.hypherionmc.sdlink.shaded.okio.Path;
import com.hypherionmc.sdlink.shaded.okio.Sink;
import com.hypherionmc.sdlink.shaded.okio.SocketAsyncTimeout;
import com.hypherionmc.sdlink.shaded.okio.Source;
import com.hypherionmc.sdlink.shaded.okio.Timeout;
import com.hypherionmc.sdlink.shaded.okio.internal.ResourceFileSystem;
import com.hypherionmc.sdlink.shaded.okio.internal.ZipFilesKt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=48, d1={"\u0000\u0088\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\b\u001a\u00020\t*\u00020\n\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\r\u001a\u0012\u0010\u000e\u001a\u00020\u000f*\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0012\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0012\u0010\u0015\u001a\u00020\u0016*\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0012\u0010\u0015\u001a\u00020\u0016*\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u0012\u0010\u001b\u001a\u00020\u001c*\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0012\u0010\u001b\u001a\u00020\u001c*\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u0012\u0010\u001d\u001a\u00020\f*\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001f\u001a\u0016\u0010 \u001a\u00020\t*\u00020\n2\b\b\u0002\u0010!\u001a\u00020\u0004H\u0007\u001a\n\u0010 \u001a\u00020\t*\u00020\"\u001a\n\u0010 \u001a\u00020\t*\u00020#\u001a#\u0010 \u001a\u00020\t*\u00020$2\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020'0&\"\u00020'\u00a2\u0006\u0002\u0010(\u001a\n\u0010)\u001a\u00020\u0014*\u00020\n\u001a\n\u0010)\u001a\u00020\u0014*\u00020*\u001a\n\u0010)\u001a\u00020\u0014*\u00020#\u001a#\u0010)\u001a\u00020\u0014*\u00020$2\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020'0&\"\u00020'\u00a2\u0006\u0002\u0010+\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u001c\u0010\u0003\u001a\u00020\u0004*\u00060\u0005j\u0002`\u00068@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0007\u00a8\u0006,"}, d2={"logger", "Ljava/util/logging/Logger;", "com.hypherionmc.sdlink.shaded.kotlin.jvm.PlatformType", "isAndroidGetsocknameError", "", "Ljava/lang/AssertionError;", "Lcom/hypherionmc/sdlink/shaded/kotlin/AssertionError;", "(Ljava/lang/AssertionError;)Z", "appendingSink", "Lcom/hypherionmc/sdlink/shaded/okio/Sink;", "Ljava/io/File;", "asResourceFileSystem", "Lcom/hypherionmc/sdlink/shaded/okio/FileSystem;", "Ljava/lang/ClassLoader;", "cipherSink", "Lcom/hypherionmc/sdlink/shaded/okio/CipherSink;", "cipher", "Ljavax/crypto/Cipher;", "cipherSource", "Lcom/hypherionmc/sdlink/shaded/okio/CipherSource;", "Lcom/hypherionmc/sdlink/shaded/okio/Source;", "hashingSink", "Lcom/hypherionmc/sdlink/shaded/okio/HashingSink;", "digest", "Ljava/security/MessageDigest;", "mac", "Ljavax/crypto/Mac;", "hashingSource", "Lcom/hypherionmc/sdlink/shaded/okio/HashingSource;", "openZip", "zipPath", "Lcom/hypherionmc/sdlink/shaded/okio/Path;", "sink", "append", "Ljava/io/OutputStream;", "Ljava/net/Socket;", "Ljava/nio/file/Path;", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Sink;", "source", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Source;", "com.hypherionmc.sdlink.shaded.okio"}, xs="com/hypherionmc/sdlink/shaded/okio/Okio")
final class Okio__JvmOkioKt {
    private static final Logger logger = Logger.getLogger("com.hypherionmc.sdlink.shaded.okio.Okio");

    @NotNull
    public static final Sink sink(@NotNull OutputStream $this$sink) {
        Intrinsics.checkNotNullParameter($this$sink, "<this>");
        return new OutputStreamSink($this$sink, new Timeout());
    }

    @NotNull
    public static final Source source(@NotNull InputStream $this$source) {
        Intrinsics.checkNotNullParameter($this$source, "<this>");
        return new InputStreamSource($this$source, new Timeout());
    }

    @NotNull
    public static final Sink sink(@NotNull Socket $this$sink) throws IOException {
        Intrinsics.checkNotNullParameter($this$sink, "<this>");
        SocketAsyncTimeout timeout2 = new SocketAsyncTimeout($this$sink);
        OutputStream outputStream2 = $this$sink.getOutputStream();
        Intrinsics.checkNotNullExpressionValue(outputStream2, "getOutputStream(...)");
        OutputStreamSink sink2 = new OutputStreamSink(outputStream2, timeout2);
        return timeout2.sink(sink2);
    }

    @NotNull
    public static final Source source(@NotNull Socket $this$source) throws IOException {
        Intrinsics.checkNotNullParameter($this$source, "<this>");
        SocketAsyncTimeout timeout2 = new SocketAsyncTimeout($this$source);
        InputStream inputStream2 = $this$source.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream2, "getInputStream(...)");
        InputStreamSource source2 = new InputStreamSource(inputStream2, timeout2);
        return timeout2.source(source2);
    }

    @JvmOverloads
    @NotNull
    public static final Sink sink(@NotNull File $this$sink, boolean append) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter($this$sink, "<this>");
        return Okio.sink(new FileOutputStream($this$sink, append));
    }

    public static /* synthetic */ Sink sink$default(File file, boolean bl, int n, Object object) throws FileNotFoundException {
        if ((n & 1) != 0) {
            bl = false;
        }
        return Okio.sink(file, bl);
    }

    @NotNull
    public static final Sink appendingSink(@NotNull File $this$appendingSink) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter($this$appendingSink, "<this>");
        return Okio.sink(new FileOutputStream($this$appendingSink, true));
    }

    @NotNull
    public static final Source source(@NotNull File $this$source) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter($this$source, "<this>");
        return new InputStreamSource(new FileInputStream($this$source), Timeout.NONE);
    }

    @NotNull
    public static final Sink sink(@NotNull java.nio.file.Path $this$sink, OpenOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$sink, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        OutputStream outputStream2 = Files.newOutputStream($this$sink, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(outputStream2, "newOutputStream(...)");
        return Okio.sink(outputStream2);
    }

    @NotNull
    public static final Source source(@NotNull java.nio.file.Path $this$source, OpenOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$source, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        InputStream inputStream2 = Files.newInputStream($this$source, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(inputStream2, "newInputStream(...)");
        return Okio.source(inputStream2);
    }

    @NotNull
    public static final CipherSink cipherSink(@NotNull Sink $this$cipherSink, @NotNull Cipher cipher) {
        Intrinsics.checkNotNullParameter($this$cipherSink, "<this>");
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        return new CipherSink(Okio.buffer($this$cipherSink), cipher);
    }

    @NotNull
    public static final CipherSource cipherSource(@NotNull Source $this$cipherSource, @NotNull Cipher cipher) {
        Intrinsics.checkNotNullParameter($this$cipherSource, "<this>");
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        return new CipherSource(Okio.buffer($this$cipherSource), cipher);
    }

    @NotNull
    public static final HashingSink hashingSink(@NotNull Sink $this$hashingSink, @NotNull Mac mac) {
        Intrinsics.checkNotNullParameter($this$hashingSink, "<this>");
        Intrinsics.checkNotNullParameter(mac, "mac");
        return new HashingSink($this$hashingSink, mac);
    }

    @NotNull
    public static final HashingSource hashingSource(@NotNull Source $this$hashingSource, @NotNull Mac mac) {
        Intrinsics.checkNotNullParameter($this$hashingSource, "<this>");
        Intrinsics.checkNotNullParameter(mac, "mac");
        return new HashingSource($this$hashingSource, mac);
    }

    @NotNull
    public static final HashingSink hashingSink(@NotNull Sink $this$hashingSink, @NotNull MessageDigest digest) {
        Intrinsics.checkNotNullParameter($this$hashingSink, "<this>");
        Intrinsics.checkNotNullParameter(digest, "digest");
        return new HashingSink($this$hashingSink, digest);
    }

    @NotNull
    public static final HashingSource hashingSource(@NotNull Source $this$hashingSource, @NotNull MessageDigest digest) {
        Intrinsics.checkNotNullParameter($this$hashingSource, "<this>");
        Intrinsics.checkNotNullParameter(digest, "digest");
        return new HashingSource($this$hashingSource, digest);
    }

    @NotNull
    public static final FileSystem openZip(@NotNull FileSystem $this$openZip, @NotNull Path zipPath) throws IOException {
        Intrinsics.checkNotNullParameter($this$openZip, "<this>");
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        return ZipFilesKt.openZip$default(zipPath, $this$openZip, null, 4, null);
    }

    @NotNull
    public static final FileSystem asResourceFileSystem(@NotNull ClassLoader $this$asResourceFileSystem) {
        Intrinsics.checkNotNullParameter($this$asResourceFileSystem, "<this>");
        return new ResourceFileSystem($this$asResourceFileSystem, true, null, 4, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean isAndroidGetsocknameError(@NotNull AssertionError $this$isAndroidGetsocknameError) {
        Intrinsics.checkNotNullParameter($this$isAndroidGetsocknameError, "<this>");
        if (((Throwable)((Object)$this$isAndroidGetsocknameError)).getCause() == null) return false;
        String string = ((Throwable)((Object)$this$isAndroidGetsocknameError)).getMessage();
        if (string == null) return false;
        boolean bl = StringsKt.contains$default((CharSequence)string, "getsockname failed", false, 2, null);
        if (!bl) return false;
        return true;
    }

    @JvmOverloads
    @NotNull
    public static final Sink sink(@NotNull File $this$sink) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter($this$sink, "<this>");
        return Okio.sink$default($this$sink, false, 1, null);
    }

    public static final /* synthetic */ Logger access$getLogger$p() {
        return logger;
    }
}

