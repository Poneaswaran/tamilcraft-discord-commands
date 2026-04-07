/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.util;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@ThreadSafe
public final class Constants {
    public static final String UNKNOWN = "unknown";
    public static final String SYSFS_SERIAL_PATH = "/sys/devices/virtual/dmi/id/";
    public static final OffsetDateTime UNIX_EPOCH = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);

    private Constants() {
        throw new AssertionError();
    }
}

