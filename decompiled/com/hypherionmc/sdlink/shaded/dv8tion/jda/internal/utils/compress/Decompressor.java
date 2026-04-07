/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.compress;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.Compression;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.zip.DataFormatException;
import org.slf4j.Logger;

public interface Decompressor {
    public static final Logger LOG = JDALogger.getLog(Decompressor.class);

    public Compression getType();

    public void reset();

    public void shutdown();

    @Nullable
    public byte[] decompress(byte[] var1) throws DataFormatException;
}

