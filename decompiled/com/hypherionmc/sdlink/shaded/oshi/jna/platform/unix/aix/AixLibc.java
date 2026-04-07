/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 */
package com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.CLibrary;
import com.sun.jna.Native;

public interface AixLibc
extends CLibrary {
    public static final AixLibc INSTANCE = (AixLibc)Native.load((String)"c", AixLibc.class);
}

