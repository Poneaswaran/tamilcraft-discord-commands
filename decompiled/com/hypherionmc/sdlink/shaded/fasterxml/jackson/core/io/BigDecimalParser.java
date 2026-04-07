/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser.JavaBigDecimalParser;
import java.math.BigDecimal;

public final class BigDecimalParser {
    static final int MAX_CHARS_TO_REPORT = 1000;

    private BigDecimalParser() {
    }

    public static BigDecimal parse(String valueStr) {
        return BigDecimalParser.parse(valueStr.toCharArray());
    }

    public static BigDecimal parse(char[] chars, int off, int len) {
        try {
            if (len < 500) {
                return new BigDecimal(chars, off, len);
            }
            return JavaBigDecimalParser.parseBigDecimal(chars, off, len);
        }
        catch (ArithmeticException | NumberFormatException e) {
            throw BigDecimalParser._parseFailure(e, new String(chars, off, len));
        }
    }

    public static BigDecimal parse(char[] chars) {
        return BigDecimalParser.parse(chars, 0, chars.length);
    }

    public static BigDecimal parseWithFastParser(String valueStr) {
        try {
            return JavaBigDecimalParser.parseBigDecimal(valueStr);
        }
        catch (ArithmeticException | NumberFormatException e) {
            throw BigDecimalParser._parseFailure(e, valueStr);
        }
    }

    public static BigDecimal parseWithFastParser(char[] ch, int off, int len) {
        try {
            return JavaBigDecimalParser.parseBigDecimal(ch, off, len);
        }
        catch (ArithmeticException | NumberFormatException e) {
            throw BigDecimalParser._parseFailure(e, new String(ch, off, len));
        }
    }

    private static NumberFormatException _parseFailure(Exception e, String fullValue) {
        String desc = e.getMessage();
        if (desc == null) {
            desc = "Not a valid number representation";
        }
        String valueToReport = BigDecimalParser._getValueDesc(fullValue);
        return new NumberFormatException("Value " + valueToReport + " can not be deserialized as `java.math.BigDecimal`, reason: " + desc);
    }

    private static String _getValueDesc(String fullValue) {
        int len = fullValue.length();
        if (len <= 1000) {
            return String.format("\"%s\"", fullValue);
        }
        return String.format("\"%s\" (truncated to %d chars (from %d))", fullValue.substring(0, 1000), 1000, len);
    }
}

