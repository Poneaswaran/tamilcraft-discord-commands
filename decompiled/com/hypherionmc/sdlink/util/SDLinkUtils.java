/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.util;

public final class SDLinkUtils {
    public static boolean isNullOrEmpty(String inString) {
        return inString == null || inString.trim().isEmpty();
    }

    public static int intInRange(int min, int max) {
        return (int)(Math.random() * (double)(max - min) + (double)min);
    }

    public static String getOrElse(String inString, String defaultValue) {
        return SDLinkUtils.isNullOrEmpty(inString) ? defaultValue : inString;
    }
}

