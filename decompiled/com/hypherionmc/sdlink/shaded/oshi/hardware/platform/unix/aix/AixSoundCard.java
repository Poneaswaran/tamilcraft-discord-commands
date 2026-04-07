/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.SoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Immutable
final class AixSoundCard
extends AbstractSoundCard {
    AixSoundCard(String kernelVersion, String name, String codec) {
        super(kernelVersion, name, codec);
    }

    public static List<SoundCard> getSoundCards(Supplier<List<String>> lscfg) {
        ArrayList<SoundCard> soundCards = new ArrayList<SoundCard>();
        for (String line : lscfg.get()) {
            String[] split;
            String s = line.trim();
            if (!s.startsWith("paud") || (split = ParseUtil.whitespaces.split(s, 3)).length != 3) continue;
            soundCards.add(new AixSoundCard("unknown", split[2], "unknown"));
        }
        return soundCards;
    }
}

