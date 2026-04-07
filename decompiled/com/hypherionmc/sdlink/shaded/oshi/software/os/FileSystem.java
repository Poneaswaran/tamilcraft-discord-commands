/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSFileStore;
import java.util.List;

@ThreadSafe
public interface FileSystem {
    public List<OSFileStore> getFileStores();

    public List<OSFileStore> getFileStores(boolean var1);

    public long getOpenFileDescriptors();

    public long getMaxFileDescriptors();
}

