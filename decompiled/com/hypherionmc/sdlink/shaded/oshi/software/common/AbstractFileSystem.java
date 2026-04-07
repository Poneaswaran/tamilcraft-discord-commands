/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.software.os.FileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSFileStore;
import com.hypherionmc.sdlink.shaded.oshi.util.GlobalConfig;
import java.util.Arrays;
import java.util.List;

@ThreadSafe
public abstract class AbstractFileSystem
implements FileSystem {
    public static final String OSHI_NETWORK_FILESYSTEM_TYPES = "com.hypherionmc.sdlink.shaded.oshi.network.filesystem.types";
    public static final String OSHI_PSEUDO_FILESYSTEM_TYPES = "com.hypherionmc.sdlink.shaded.oshi.pseudo.filesystem.types";
    protected static final List<String> NETWORK_FS_TYPES = Arrays.asList(GlobalConfig.get("com.hypherionmc.sdlink.shaded.oshi.network.filesystem.types", "").split(","));
    protected static final List<String> PSEUDO_FS_TYPES = Arrays.asList(GlobalConfig.get("com.hypherionmc.sdlink.shaded.oshi.pseudo.filesystem.types", "").split(","));

    @Override
    public List<OSFileStore> getFileStores() {
        return this.getFileStores(false);
    }
}

