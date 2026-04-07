/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.node;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonToken;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.node.NumericNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DecimalNode
extends NumericNode {
    public static final DecimalNode ZERO = new DecimalNode(BigDecimal.ZERO);
    private static final BigDecimal MIN_INTEGER = BigDecimal.valueOf(Integer.MIN_VALUE);
    private static final BigDecimal MAX_INTEGER = BigDecimal.valueOf(Integer.MAX_VALUE);
    private static final BigDecimal MIN_LONG = BigDecimal.valueOf(Long.MIN_VALUE);
    private static final BigDecimal MAX_LONG = BigDecimal.valueOf(Long.MAX_VALUE);
    protected final BigDecimal _value;

    public DecimalNode(BigDecimal v) {
        this._value = v;
    }

    public static DecimalNode valueOf(BigDecimal d) {
        return new DecimalNode(d);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.BIG_DECIMAL;
    }

    @Override
    public boolean isFloatingPointNumber() {
        return true;
    }

    @Override
    public boolean isBigDecimal() {
        return true;
    }

    @Override
    public boolean canConvertToInt() {
        return this._value.compareTo(MIN_INTEGER) >= 0 && this._value.compareTo(MAX_INTEGER) <= 0;
    }

    @Override
    public boolean canConvertToLong() {
        return this._value.compareTo(MIN_LONG) >= 0 && this._value.compareTo(MAX_LONG) <= 0;
    }

    @Override
    public boolean canConvertToExactIntegral() {
        return this._value.signum() == 0 || this._value.scale() <= 0 || this._value.stripTrailingZeros().scale() <= 0;
    }

    @Override
    public Number numberValue() {
        return this._value;
    }

    @Override
    public short shortValue() {
        return this._value.shortValue();
    }

    @Override
    public int intValue() {
        return this._value.intValue();
    }

    @Override
    public long longValue() {
        return this._value.longValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return this._bigIntFromBigDec(this._value);
    }

    @Override
    public float floatValue() {
        return this._value.floatValue();
    }

    @Override
    public double doubleValue() {
        return this._value.doubleValue();
    }

    @Override
    public BigDecimal decimalValue() {
        return this._value;
    }

    @Override
    public String asText() {
        return this._value.toString();
    }

    @Override
    public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeNumber(this._value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof DecimalNode) {
            DecimalNode otherNode = (DecimalNode)o;
            if (otherNode._value == null) {
                return this._value == null;
            }
            if (this._value == null) {
                return false;
            }
            return otherNode._value.compareTo(this._value) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this._value == null) {
            return 0;
        }
        return Double.hashCode(this.doubleValue());
    }
}

