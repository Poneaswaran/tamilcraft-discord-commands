/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.SoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Immutable
final class SolarisSoundCard
extends AbstractSoundCard {
    private static final String LSHAL = "lshal";
    private static final String DEFAULT_AUDIO_DRIVER = "audio810";

    SolarisSoundCard(String kernelVersion, String name, String codec) {
        super(kernelVersion, name, codec);
    }

    public static List<SoundCard> getSoundCards() {
        HashMap<String, String> vendorMap = new HashMap<String, String>();
        HashMap<String, String> productMap = new HashMap<String, String>();
        ArrayList<String> sounds = new ArrayList<String>();
        String key = "";
        for (String line : ExecutingCommand.runNative(LSHAL)) {
            if ((line = line.trim()).startsWith("udi =")) {
                key = ParseUtil.getSingleQuoteStringValue(line);
                continue;
            }
            if (key.isEmpty() || line.isEmpty()) continue;
            if (line.contains("info.solaris.driver =") && DEFAULT_AUDIO_DRIVER.equals(ParseUtil.getSingleQuoteStringValue(line))) {
                sounds.add(key);
                continue;
            }
            if (line.contains("info.product")) {
                productMap.put(key, ParseUtil.getStringBetween(line, '\''));
                continue;
            }
            if (!line.contains("info.vendor")) continue;
            vendorMap.put(key, ParseUtil.getStringBetween(line, '\''));
        }
        ArrayList<SoundCard> soundCards = new ArrayList<SoundCard>();
        for (String _key : sounds) {
            soundCards.add(new SolarisSoundCard((String)productMap.get(_key) + " " + DEFAULT_AUDIO_DRIVER, (String)vendorMap.get(_key) + " " + (String)productMap.get(_key), (String)productMap.get(_key)));
        }
        return soundCards;
    }
}

