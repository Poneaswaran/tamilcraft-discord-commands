/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractFirmware;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Triplet;
import java.util.function.Supplier;

@Immutable
final class FreeBsdFirmware
extends AbstractFirmware {
    private final Supplier<Triplet<String, String, String>> manufVersRelease = Memoizer.memoize(FreeBsdFirmware::readDmiDecode);

    FreeBsdFirmware() {
    }

    @Override
    public String getManufacturer() {
        return this.manufVersRelease.get().getA();
    }

    @Override
    public String getVersion() {
        return this.manufVersRelease.get().getB();
    }

    @Override
    public String getReleaseDate() {
        return this.manufVersRelease.get().getC();
    }

    private static Triplet<String, String, String> readDmiDecode() {
        String manufacturer = null;
        String version = null;
        String releaseDate = "";
        String manufacturerMarker = "Vendor:";
        String versionMarker = "Version:";
        String releaseDateMarker = "Release Date:";
        for (String checkLine : ExecutingCommand.runNative("dmidecode -t bios")) {
            if (checkLine.contains("Vendor:")) {
                manufacturer = checkLine.split("Vendor:")[1].trim();
                continue;
            }
            if (checkLine.contains("Version:")) {
                version = checkLine.split("Version:")[1].trim();
                continue;
            }
            if (!checkLine.contains("Release Date:")) continue;
            releaseDate = checkLine.split("Release Date:")[1].trim();
        }
        releaseDate = ParseUtil.parseMmDdYyyyToYyyyMmDD(releaseDate);
        return new Triplet<String, String, String>(Util.isBlank(manufacturer) ? "unknown" : manufacturer, Util.isBlank(version) ? "unknown" : version, Util.isBlank(releaseDate) ? "unknown" : releaseDate);
    }
}

