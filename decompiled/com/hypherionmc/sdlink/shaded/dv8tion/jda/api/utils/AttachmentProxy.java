/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.FutureUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class AttachmentProxy
extends FileProxy {
    public AttachmentProxy(@Nonnull String url) {
        super(url);
    }

    @Nonnull
    public String getUrl(int width, int height) {
        Checks.positive(width, "Image width");
        Checks.positive(height, "Image height");
        return IOUtil.addQuery(this.getUrl(), "width", width, "height", height);
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<InputStream> download(int width, int height) {
        return this.download(this.getUrl(width, height));
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<Path> downloadToPath(int width, int height) {
        return this.downloadToPath(this.getUrl(width, height));
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<File> downloadToFile(@Nonnull File file, int width, int height) {
        Checks.notNull(file, "File");
        CompletableFuture<Path> downloadToPathFuture = this.downloadToPath(this.getUrl(width, height), file.toPath());
        return FutureUtil.thenApplyCancellable(downloadToPathFuture, Path::toFile);
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<Path> downloadToPath(@Nonnull Path path, int width, int height) {
        Checks.notNull(path, "Path");
        return this.downloadToPath(this.getUrl(width, height), path);
    }

    @Nonnull
    @CheckReturnValue
    private CompletableFuture<Icon> downloadAsIcon(String url) {
        CompletableFuture<InputStream> downloadFuture = this.download(url);
        return FutureUtil.thenApplyCancellable(downloadFuture, stream -> {
            Icon icon;
            block8: {
                InputStream ignored = stream;
                try {
                    icon = Icon.from(stream);
                    if (ignored == null) break block8;
                }
                catch (Throwable throwable) {
                    try {
                        if (ignored != null) {
                            try {
                                ignored.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
                ignored.close();
            }
            return icon;
        });
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<Icon> downloadAsIcon() {
        return this.downloadAsIcon(this.getUrl());
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<Icon> downloadAsIcon(int width, int height) {
        return this.downloadAsIcon(this.getUrl(width, height));
    }
}

