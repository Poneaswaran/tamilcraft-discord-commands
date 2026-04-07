/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.utils;

public final class SafeIdUtil {
    public static long safeConvert(String id) {
        try {
            long l = Long.parseLong(id.trim());
            if (l < 0L) {
                return 0L;
            }
            return l;
        }
        catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static boolean checkId(String id) {
        try {
            long l = Long.parseLong(id.trim());
            return l >= 0L;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private SafeIdUtil() {
    }
}

