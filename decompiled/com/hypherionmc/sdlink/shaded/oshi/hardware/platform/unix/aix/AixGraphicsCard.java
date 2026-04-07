/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Immutable
final class AixGraphicsCard
extends AbstractGraphicsCard {
    AixGraphicsCard(String name, String deviceId, String vendor, String versionInfo, long vram) {
        super(name, deviceId, vendor, versionInfo, vram);
    }

    public static List<GraphicsCard> getGraphicsCards(Supplier<List<String>> lscfg) {
        ArrayList<GraphicsCard> cardList = new ArrayList<GraphicsCard>();
        boolean display = false;
        String name = null;
        String vendor = null;
        ArrayList<String> versionInfo = new ArrayList<String>();
        for (String line : lscfg.get()) {
            String s = line.trim();
            if (s.startsWith("Name:") && s.contains("display")) {
                display = true;
                continue;
            }
            if (display && s.toLowerCase().contains("graphics")) {
                name = s;
                continue;
            }
            if (!display || name == null) continue;
            if (s.startsWith("Manufacture ID")) {
                vendor = ParseUtil.removeLeadingDots(s.substring(14));
                continue;
            }
            if (s.contains("Level")) {
                versionInfo.add(s.replaceAll("\\.\\.+", "="));
                continue;
            }
            if (!s.startsWith("Hardware Location Code")) continue;
            cardList.add(new AixGraphicsCard(name, "unknown", Util.isBlank(vendor) ? "unknown" : vendor, versionInfo.isEmpty() ? "unknown" : String.join((CharSequence)",", versionInfo), 0L));
            display = false;
        }
        return cardList;
    }
}

