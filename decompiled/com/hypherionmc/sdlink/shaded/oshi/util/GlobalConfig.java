/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.util;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.NotThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.Map;
import java.util.Properties;

@NotThreadSafe
public final class GlobalConfig {
    private static final String OSHI_PROPERTIES = "com.hypherionmc.sdlink.shaded.oshi.properties";
    private static final Properties CONFIG = FileUtil.readPropertiesFromFilename("com.hypherionmc.sdlink.shaded.oshi.properties");

    private GlobalConfig() {
    }

    public static String get(String key, String def) {
        return CONFIG.getProperty(key, def);
    }

    public static int get(String key, int def) {
        String value = CONFIG.getProperty(key);
        return value == null ? def : ParseUtil.parseIntOrDefault(value, def);
    }

    public static double get(String key, double def) {
        String value = CONFIG.getProperty(key);
        return value == null ? def : ParseUtil.parseDoubleOrDefault(value, def);
    }

    public static boolean get(String key, boolean def) {
        String value = CONFIG.getProperty(key);
        return value == null ? def : Boolean.parseBoolean(value);
    }

    public static void set(String key, Object val) {
        if (val == null) {
            CONFIG.remove(key);
        } else {
            CONFIG.setProperty(key, val.toString());
        }
    }

    public static void remove(String key) {
        CONFIG.remove(key);
    }

    public static void clear() {
        CONFIG.clear();
    }

    public static void load(Properties properties) {
        CONFIG.putAll((Map<?, ?>)properties);
    }

    public static class PropertyException
    extends RuntimeException {
        private static final long serialVersionUID = -7482581936621748005L;

        public PropertyException(String property) {
            super("Invalid property: \"" + property + "\" = " + GlobalConfig.get(property, null));
        }

        public PropertyException(String property, String message) {
            super("Invalid property \"" + property + "\": " + message);
        }
    }
}

