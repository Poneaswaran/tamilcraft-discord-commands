/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.FutureUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ImageProxy
extends FileProxy {
    public ImageProxy(@Nonnull String url) {
        super(url);
    }

    @Nonnull
    public String getUrl(int size) {
        Checks.positive(size, "Image size");
        return IOUtil.addQuery(this.getUrl(), "size", size);
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<InputStream> download(int size) {
        return this.download(this.getUrl(size));
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<Path> downloadToPath(int size) {
        return this.downloadToPath(this.getUrl(size));
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<File> downloadToFile(@Nonnull File file, int size) {
        Checks.notNull(file, "File");
        CompletableFuture<Path> downloadToPathFuture = this.downloadToPath(this.getUrl(size), file.toPath());
        return FutureUtil.thenApplyCancellable(downloadToPathFuture, Path::toFile);
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<Path> downloadToPath(@Nonnull Path path, int size) {
        Checks.notNull(path, "Path");
        return this.downloadToPath(this.getUrl(size), path);
    }
}

