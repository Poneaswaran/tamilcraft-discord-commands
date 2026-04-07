/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser.AbstractBigDecimalParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser.FastDoubleSwar;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser.FastIntegerMath;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser.FftMultiplier;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.doubleparser.ParseDigitsTaskCharSequence;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NavigableMap;

final class JavaBigDecimalFromCharSequence
extends AbstractBigDecimalParser {
    public BigDecimal parseBigDecimalString(CharSequence str, int offset, int length) {
        try {
            int exponentIndicatorIndex;
            long exponent;
            int digitCount;
            boolean isNegative;
            int index;
            int size = str.length();
            int endIndex = JavaBigDecimalFromCharSequence.checkBounds(size, offset, length);
            if (JavaBigDecimalFromCharSequence.hasManyDigits(length)) {
                return this.parseBigDecimalStringWithManyDigits(str, offset, length);
            }
            long significand = 0L;
            int decimalPointIndex = -1;
            char ch = JavaBigDecimalFromCharSequence.charAt(str, index, endIndex);
            boolean illegal = false;
            boolean bl = isNegative = ch == '-';
            if ((isNegative || ch == '+') && (ch = JavaBigDecimalFromCharSequence.charAt(str, ++index, endIndex)) == '\u0000') {
                throw new NumberFormatException("illegal syntax");
            }
            int integerPartIndex = index;
            for (index = offset; index < endIndex; ++index) {
                int digits;
                ch = str.charAt(index);
                if (FastDoubleSwar.isDigit(ch)) {
                    significand = 10L * significand + (long)ch - 48L;
                    continue;
                }
                if (ch != '.') break;
                illegal |= decimalPointIndex >= 0;
                decimalPointIndex = index;
                while (index < endIndex - 4 && (digits = FastDoubleSwar.tryToParseFourDigits(str, index + 1)) >= 0) {
                    significand = 10000L * significand + (long)digits;
                    index += 4;
                }
            }
            int significandEndIndex = index;
            if (decimalPointIndex < 0) {
                digitCount = significandEndIndex - integerPartIndex;
                decimalPointIndex = significandEndIndex;
                exponent = 0L;
            } else {
                digitCount = significandEndIndex - integerPartIndex - 1;
                exponent = decimalPointIndex - significandEndIndex + 1;
            }
            long expNumber = 0L;
            if ((ch | 0x20) == 101) {
                boolean isExponentNegative;
                exponentIndicatorIndex = index++;
                ch = JavaBigDecimalFromCharSequence.charAt(str, index, endIndex);
                boolean bl2 = isExponentNegative = ch == '-';
                if (isExponentNegative || ch == '+') {
                    ch = JavaBigDecimalFromCharSequence.charAt(str, ++index, endIndex);
                }
                illegal |= !FastDoubleSwar.isDigit(ch);
                do {
                    if (expNumber >= Integer.MAX_VALUE) continue;
                    expNumber = 10L * expNumber + (long)ch - 48L;
                } while (FastDoubleSwar.isDigit(ch = JavaBigDecimalFromCharSequence.charAt(str, ++index, endIndex)));
                if (isExponentNegative) {
                    expNumber = -expNumber;
                }
                exponent += expNumber;
            } else {
                exponentIndicatorIndex = endIndex;
            }
            JavaBigDecimalFromCharSequence.checkParsedBigDecimalBounds(illegal |= digitCount == 0, index, endIndex, digitCount, exponent);
            if (digitCount < 19) {
                return new BigDecimal(isNegative ? -significand : significand).scaleByPowerOfTen((int)exponent);
            }
            return this.valueOfBigDecimalString(str, integerPartIndex, decimalPointIndex, decimalPointIndex + 1, exponentIndicatorIndex, isNegative, (int)exponent);
        }
        catch (ArithmeticException e) {
            NumberFormatException nfe = new NumberFormatException("value exceeds limits");
            nfe.initCause(e);
            throw nfe;
        }
    }

    BigDecimal parseBigDecimalStringWithManyDigits(CharSequence str, int offset, int length) {
        int exponentIndicatorIndex;
        long exponent;
        int digitCountWithoutLeadingZeros;
        boolean isNegative;
        int index;
        int decimalPointIndex = -1;
        int nonZeroFractionalPartIndex = -1;
        int endIndex = offset + length;
        char ch = JavaBigDecimalFromCharSequence.charAt(str, index, endIndex);
        boolean illegal = false;
        boolean bl = isNegative = ch == '-';
        if ((isNegative || ch == '+') && (ch = JavaBigDecimalFromCharSequence.charAt(str, ++index, endIndex)) == '\u0000') {
            throw new NumberFormatException("illegal syntax");
        }
        int integerPartIndex = index;
        for (index = offset; index < endIndex - 8 && FastDoubleSwar.isEightZeroes(str, index); index += 8) {
        }
        while (index < endIndex && str.charAt(index) == '0') {
            ++index;
        }
        int nonZeroIntegerPartIndex = index;
        while (index < endIndex - 8 && FastDoubleSwar.isEightDigits(str, index)) {
            index += 8;
        }
        while (index < endIndex && FastDoubleSwar.isDigit(ch = str.charAt(index))) {
            ++index;
        }
        if (ch == '.') {
            decimalPointIndex = index++;
            while (index < endIndex - 8 && FastDoubleSwar.isEightZeroes(str, index)) {
                index += 8;
            }
            while (index < endIndex && str.charAt(index) == '0') {
                ++index;
            }
            nonZeroFractionalPartIndex = index;
            while (index < endIndex - 8 && FastDoubleSwar.isEightDigits(str, index)) {
                index += 8;
            }
            while (index < endIndex && FastDoubleSwar.isDigit(ch = str.charAt(index))) {
                ++index;
            }
        }
        int significandEndIndex = index;
        if (decimalPointIndex < 0) {
            digitCountWithoutLeadingZeros = significandEndIndex - nonZeroIntegerPartIndex;
            decimalPointIndex = significandEndIndex;
            nonZeroFractionalPartIndex = significandEndIndex;
            exponent = 0L;
        } else {
            digitCountWithoutLeadingZeros = nonZeroIntegerPartIndex == decimalPointIndex ? significandEndIndex - nonZeroFractionalPartIndex : significandEndIndex - nonZeroIntegerPartIndex - 1;
            exponent = decimalPointIndex - significandEndIndex + 1;
        }
        long expNumber = 0L;
        if ((ch | 0x20) == 101) {
            boolean isExponentNegative;
            exponentIndicatorIndex = index++;
            ch = JavaBigDecimalFromCharSequence.charAt(str, index, endIndex);
            boolean bl2 = isExponentNegative = ch == '-';
            if (isExponentNegative || ch == '+') {
                ch = JavaBigDecimalFromCharSequence.charAt(str, ++index, endIndex);
            }
            boolean bl3 = illegal = !FastDoubleSwar.isDigit(ch);
            do {
                if (expNumber >= Integer.MAX_VALUE) continue;
                expNumber = 10L * expNumber + (long)ch - 48L;
            } while (FastDoubleSwar.isDigit(ch = JavaBigDecimalFromCharSequence.charAt(str, ++index, endIndex)));
            if (isExponentNegative) {
                expNumber = -expNumber;
            }
            exponent += expNumber;
        } else {
            exponentIndicatorIndex = endIndex;
        }
        JavaBigDecimalFromCharSequence.checkParsedBigDecimalBounds(illegal |= integerPartIndex == decimalPointIndex && decimalPointIndex == exponentIndicatorIndex, index, endIndex, digitCountWithoutLeadingZeros, exponent);
        return this.valueOfBigDecimalString(str, nonZeroIntegerPartIndex, decimalPointIndex, nonZeroFractionalPartIndex, exponentIndicatorIndex, isNegative, (int)exponent);
    }

    BigDecimal valueOfBigDecimalString(CharSequence str, int integerPartIndex, int decimalPointIndex, int nonZeroFractionalPartIndex, int exponentIndicatorIndex, boolean isNegative, int exponent) {
        BigInteger significand;
        BigInteger integerPart;
        int fractionDigitsCount = exponentIndicatorIndex - decimalPointIndex - 1;
        int nonZeroFractionDigitsCount = exponentIndicatorIndex - nonZeroFractionalPartIndex;
        int integerDigitsCount = decimalPointIndex - integerPartIndex;
        NavigableMap<Integer, BigInteger> powersOfTen = null;
        if (integerDigitsCount > 0) {
            if (integerDigitsCount > 400) {
                powersOfTen = FastIntegerMath.createPowersOfTenFloor16Map();
                FastIntegerMath.fillPowersOfNFloor16Recursive(powersOfTen, integerPartIndex, decimalPointIndex);
                integerPart = ParseDigitsTaskCharSequence.parseDigitsRecursive(str, integerPartIndex, decimalPointIndex, powersOfTen, 400);
            } else {
                integerPart = ParseDigitsTaskCharSequence.parseDigitsIterative(str, integerPartIndex, decimalPointIndex);
            }
        } else {
            integerPart = BigInteger.ZERO;
        }
        if (fractionDigitsCount > 0) {
            BigInteger fractionalPart;
            if (nonZeroFractionDigitsCount > 400) {
                if (powersOfTen == null) {
                    powersOfTen = FastIntegerMath.createPowersOfTenFloor16Map();
                }
                FastIntegerMath.fillPowersOfNFloor16Recursive(powersOfTen, nonZeroFractionalPartIndex, exponentIndicatorIndex);
                fractionalPart = ParseDigitsTaskCharSequence.parseDigitsRecursive(str, nonZeroFractionalPartIndex, exponentIndicatorIndex, powersOfTen, 400);
            } else {
                fractionalPart = ParseDigitsTaskCharSequence.parseDigitsIterative(str, nonZeroFractionalPartIndex, exponentIndicatorIndex);
            }
            if (integerPart.signum() == 0) {
                significand = fractionalPart;
            } else {
                BigInteger integerFactor = FastIntegerMath.computePowerOfTen(powersOfTen, fractionDigitsCount);
                significand = FftMultiplier.multiply(integerPart, integerFactor).add(fractionalPart);
            }
        } else {
            significand = integerPart;
        }
        return new BigDecimal(isNegative ? significand.negate() : significand, -exponent);
    }
}

