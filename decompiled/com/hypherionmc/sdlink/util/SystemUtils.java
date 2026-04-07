/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.util;

import java.text.StringCharacterIterator;

public final class SystemUtils {
    public static String byteToHuman(long bytes) {
        long absB;
        long l = absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024L) {
            return bytes + " B";
        }
        long value = absB;
        StringCharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xFFFCCCCCCCCCCCCL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        return String.format("%.1f %ciB", (double)(value *= (long)Long.signum(bytes)) / 1024.0, Character.valueOf(ci.current()));
    }

    public static String secondsToTimestamp(long sec) {
        long seconds = sec % 60L;
        long minutes = sec / 60L % 60L;
        long hours = sec / 3600L % 24L;
        long days = sec / 86400L;
        String timeString = String.format("%02d hour(s), %02d minute(s), %02d second(s)", hours, minutes, seconds);
        if (days > 0L) {
            timeString = String.format("%d day(s), %s", days, timeString);
        }
        return timeString;
    }

    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }
}

