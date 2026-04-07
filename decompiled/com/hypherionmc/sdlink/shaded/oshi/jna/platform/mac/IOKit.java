/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.NativeLong
 *  com.sun.jna.Structure
 *  com.sun.jna.platform.mac.IOKit
 *  com.sun.jna.platform.mac.IOKit$IOConnect
 *  com.sun.jna.ptr.NativeLongByReference
 */
package com.hypherionmc.sdlink.shaded.oshi.jna.platform.mac;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.platform.mac.IOKit;
import com.sun.jna.ptr.NativeLongByReference;

public interface IOKit
extends com.sun.jna.platform.mac.IOKit {
    public static final IOKit INSTANCE = (IOKit)Native.load((String)"IOKit", IOKit.class);

    public int IOConnectCallStructMethod(IOKit.IOConnect var1, int var2, Structure var3, NativeLong var4, Structure var5, NativeLongByReference var6);
}

