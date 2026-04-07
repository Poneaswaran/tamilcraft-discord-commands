/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.List;

@Immutable
final class MacGraphicsCard
extends AbstractGraphicsCard {
    MacGraphicsCard(String name, String deviceId, String vendor, String versionInfo, long vram) {
        super(name, deviceId, vendor, versionInfo, vram);
    }

    public static List<GraphicsCard> getGraphicsCards() {
        ArrayList<GraphicsCard> cardList = new ArrayList<GraphicsCard>();
        List<String> sp = ExecutingCommand.runNative("system_profiler SPDisplaysDataType");
        String name = "unknown";
        String deviceId = "unknown";
        String vendor = "unknown";
        ArrayList<String> versionInfoList = new ArrayList<String>();
        long vram = 0L;
        int cardNum = 0;
        for (String line : sp) {
            String[] split = line.trim().split(":", 2);
            if (split.length != 2) continue;
            String prefix = split[0].toLowerCase();
            if (prefix.equals("chipset model")) {
                if (cardNum++ > 0) {
                    cardList.add(new MacGraphicsCard(name, deviceId, vendor, versionInfoList.isEmpty() ? "unknown" : String.join((CharSequence)", ", versionInfoList), vram));
                    versionInfoList.clear();
                }
                name = split[1].trim();
                continue;
            }
            if (prefix.equals("device id")) {
                deviceId = split[1].trim();
                continue;
            }
            if (prefix.equals("vendor")) {
                vendor = split[1].trim();
                continue;
            }
            if (prefix.contains("version") || prefix.contains("revision")) {
                versionInfoList.add(line.trim());
                continue;
            }
            if (!prefix.startsWith("vram")) continue;
            vram = ParseUtil.parseDecimalMemorySizeToBinary(split[1].trim());
        }
        cardList.add(new MacGraphicsCard(name, deviceId, vendor, versionInfoList.isEmpty() ? "unknown" : String.join((CharSequence)", ", versionInfoList), vram));
        return cardList;
    }
}

