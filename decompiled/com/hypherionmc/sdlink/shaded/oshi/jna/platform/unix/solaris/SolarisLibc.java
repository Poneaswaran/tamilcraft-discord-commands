/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.NativeLong
 *  com.sun.jna.Structure
 *  com.sun.jna.Structure$FieldOrder
 */
package com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.CLibrary;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

public interface SolarisLibc
extends CLibrary {
    public static final SolarisLibc INSTANCE = (SolarisLibc)Native.load((String)"c", SolarisLibc.class);
    public static final int UTX_USERSIZE = 32;
    public static final int UTX_LINESIZE = 32;
    public static final int UTX_IDSIZE = 4;
    public static final int UTX_HOSTSIZE = 257;

    public SolarisUtmpx getutxent();

    @Structure.FieldOrder(value={"tv_sec", "tv_usec"})
    public static class Timeval
    extends Structure {
        public NativeLong tv_sec;
        public NativeLong tv_usec;
    }

    @Structure.FieldOrder(value={"e_termination", "e_exit"})
    public static class Exit_status
    extends Structure {
        public short e_termination;
        public short e_exit;
    }

    @Structure.FieldOrder(value={"ut_user", "ut_id", "ut_line", "ut_pid", "ut_type", "ut_tv", "ut_session", "ut_syslen", "ut_host"})
    public static class SolarisUtmpx
    extends Structure {
        public byte[] ut_user = new byte[32];
        public byte[] ut_id = new byte[4];
        public byte[] ut_line = new byte[32];
        public int ut_pid;
        public short ut_type;
        public Timeval ut_tv;
        public int ut_session;
        public short ut_syslen;
        public byte[] ut_host = new byte[257];
    }
}

