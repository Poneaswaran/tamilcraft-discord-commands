/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public enum AudioEncryption {
    AEAD_AES256_GCM_RTPSIZE,
    AEAD_XCHACHA20_POLY1305_RTPSIZE;

    private final String key = this.name().toLowerCase();

    public String getKey() {
        return this.key;
    }

    public static AudioEncryption getPreferredMode(DataArray array) {
        AudioEncryption encryption = null;
        for (Object o : array) {
            try {
                String name = String.valueOf(o).toUpperCase();
                AudioEncryption e = AudioEncryption.valueOf(name);
                if (encryption != null && e.ordinal() >= encryption.ordinal()) continue;
                encryption = e;
            }
            catch (IllegalArgumentException illegalArgumentException) {}
        }
        return encryption;
    }

    public static EnumSet<AudioEncryption> fromArray(DataArray modes) {
        return modes.stream(DataArray::getString).map(mode -> mode.toLowerCase(Locale.ROOT)).map(AudioEncryption::forMode).filter(Objects::nonNull).collect(Collectors.toCollection(() -> EnumSet.noneOf(AudioEncryption.class)));
    }

    public static AudioEncryption forMode(String mode) {
        switch (mode) {
            case "aead_aes256_gcm_rtpsize": {
                return AEAD_AES256_GCM_RTPSIZE;
            }
            case "aead_xchacha20_poly1305_rtpsize": {
                return AEAD_XCHACHA20_POLY1305_RTPSIZE;
            }
        }
        return null;
    }
}

