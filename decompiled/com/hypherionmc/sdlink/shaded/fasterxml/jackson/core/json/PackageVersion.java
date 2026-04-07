/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.json;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.Version;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.Versioned;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.VersionUtil;

public final class PackageVersion
implements Versioned {
    public static final Version VERSION = VersionUtil.parseVersion("2.17.2", "com.hypherionmc.sdlink.shaded.fasterxml.jackson.core", "jackson-core");

    @Override
    public Version version() {
        return VERSION;
    }
}

