/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.SoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.List;

@Immutable
final class MacSoundCard
extends AbstractSoundCard {
    private static final String APPLE = "Apple Inc.";

    MacSoundCard(String kernelVersion, String name, String codec) {
        super(kernelVersion, name, codec);
    }

    public static List<SoundCard> getSoundCards() {
        ArrayList<SoundCard> soundCards = new ArrayList<SoundCard>();
        String manufacturer = APPLE;
        String kernelVersion = "AppleHDAController";
        String codec = "AppleHDACodec";
        boolean version = false;
        String versionMarker = "<key>com.apple.driver.AppleHDAController</key>";
        for (String checkLine : FileUtil.readFile("/System/Library/Extensions/AppleHDA.kext/Contents/Info.plist")) {
            if (checkLine.contains(versionMarker)) {
                version = true;
                continue;
            }
            if (!version) continue;
            kernelVersion = "AppleHDAController " + ParseUtil.getTextBetweenStrings(checkLine, "<string>", "</string>");
            version = false;
        }
        soundCards.add(new MacSoundCard(kernelVersion, manufacturer, codec));
        return soundCards;
    }
}

