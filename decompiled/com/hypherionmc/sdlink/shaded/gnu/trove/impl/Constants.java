/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.gnu.trove.impl;

public class Constants {
    private static final boolean VERBOSE;
    public static final int DEFAULT_CAPACITY = 10;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    public static final byte DEFAULT_BYTE_NO_ENTRY_VALUE;
    public static final short DEFAULT_SHORT_NO_ENTRY_VALUE;
    public static final char DEFAULT_CHAR_NO_ENTRY_VALUE;
    public static final int DEFAULT_INT_NO_ENTRY_VALUE;
    public static final long DEFAULT_LONG_NO_ENTRY_VALUE;
    public static final float DEFAULT_FLOAT_NO_ENTRY_VALUE;
    public static final double DEFAULT_DOUBLE_NO_ENTRY_VALUE;

    static {
        boolean verbose = false;
        try {
            verbose = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.verbose", null) != null;
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        VERBOSE = verbose;
        String property = "0";
        try {
            property = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.byte", property);
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        int value = "MAX_VALUE".equalsIgnoreCase(property) ? 127 : ("MIN_VALUE".equalsIgnoreCase(property) ? -128 : (int)Byte.valueOf(property).byteValue());
        if (value > 127) {
            value = 127;
        } else if (value < -128) {
            value = -128;
        }
        DEFAULT_BYTE_NO_ENTRY_VALUE = (byte)value;
        if (VERBOSE) {
            System.out.println("DEFAULT_BYTE_NO_ENTRY_VALUE: " + DEFAULT_BYTE_NO_ENTRY_VALUE);
        }
        property = "0";
        try {
            property = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.short", property);
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        value = "MAX_VALUE".equalsIgnoreCase(property) ? Short.MAX_VALUE : ("MIN_VALUE".equalsIgnoreCase(property) ? Short.MIN_VALUE : (int)Short.valueOf(property).shortValue());
        if (value > Short.MAX_VALUE) {
            value = Short.MAX_VALUE;
        } else if (value < Short.MIN_VALUE) {
            value = Short.MIN_VALUE;
        }
        DEFAULT_SHORT_NO_ENTRY_VALUE = (short)value;
        if (VERBOSE) {
            System.out.println("DEFAULT_SHORT_NO_ENTRY_VALUE: " + DEFAULT_SHORT_NO_ENTRY_VALUE);
        }
        property = "\u0000";
        try {
            property = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.char", property);
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        value = "MAX_VALUE".equalsIgnoreCase(property) ? 65535 : ("MIN_VALUE".equalsIgnoreCase(property) ? 0 : property.toCharArray()[0]);
        if (value > 65535) {
            value = 65535;
        } else if (value < 0) {
            value = 0;
        }
        DEFAULT_CHAR_NO_ENTRY_VALUE = (char)value;
        if (VERBOSE) {
            System.out.println("DEFAULT_CHAR_NO_ENTRY_VALUE: " + Integer.valueOf(value));
        }
        property = "0";
        try {
            property = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.int", property);
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        value = "MAX_VALUE".equalsIgnoreCase(property) ? Integer.MAX_VALUE : ("MIN_VALUE".equalsIgnoreCase(property) ? Integer.MIN_VALUE : Integer.valueOf(property));
        DEFAULT_INT_NO_ENTRY_VALUE = value;
        if (VERBOSE) {
            System.out.println("DEFAULT_INT_NO_ENTRY_VALUE: " + DEFAULT_INT_NO_ENTRY_VALUE);
        }
        String property22 = "0";
        try {
            property22 = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.long", property22);
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        long value2 = "MAX_VALUE".equalsIgnoreCase(property22) ? Long.MAX_VALUE : ("MIN_VALUE".equalsIgnoreCase(property22) ? Long.MIN_VALUE : Long.valueOf(property22));
        DEFAULT_LONG_NO_ENTRY_VALUE = value2;
        if (VERBOSE) {
            System.out.println("DEFAULT_LONG_NO_ENTRY_VALUE: " + DEFAULT_LONG_NO_ENTRY_VALUE);
        }
        property = "0";
        try {
            property = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.float", property);
        }
        catch (SecurityException property22) {
            // empty catch block
        }
        float value3 = "MAX_VALUE".equalsIgnoreCase(property) ? Float.MAX_VALUE : ("MIN_VALUE".equalsIgnoreCase(property) ? Float.MIN_VALUE : ("MIN_NORMAL".equalsIgnoreCase(property) ? Float.MIN_NORMAL : ("NEGATIVE_INFINITY".equalsIgnoreCase(property) ? Float.NEGATIVE_INFINITY : ("POSITIVE_INFINITY".equalsIgnoreCase(property) ? Float.POSITIVE_INFINITY : Float.valueOf(property).floatValue()))));
        DEFAULT_FLOAT_NO_ENTRY_VALUE = value3;
        if (VERBOSE) {
            System.out.println("DEFAULT_FLOAT_NO_ENTRY_VALUE: " + DEFAULT_FLOAT_NO_ENTRY_VALUE);
        }
        property22 = "0";
        try {
            property22 = System.getProperty("com.hypherionmc.sdlink.shaded.gnu.trove.no_entry.double", property22);
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        double value4 = "MAX_VALUE".equalsIgnoreCase(property22) ? Double.MAX_VALUE : ("MIN_VALUE".equalsIgnoreCase(property22) ? Double.MIN_VALUE : ("MIN_NORMAL".equalsIgnoreCase(property22) ? Double.MIN_NORMAL : ("NEGATIVE_INFINITY".equalsIgnoreCase(property22) ? Double.NEGATIVE_INFINITY : ("POSITIVE_INFINITY".equalsIgnoreCase(property22) ? Double.POSITIVE_INFINITY : Double.valueOf(property22)))));
        DEFAULT_DOUBLE_NO_ENTRY_VALUE = value4;
        if (VERBOSE) {
            System.out.println("DEFAULT_DOUBLE_NO_ENTRY_VALUE: " + DEFAULT_DOUBLE_NO_ENTRY_VALUE);
        }
    }
}

