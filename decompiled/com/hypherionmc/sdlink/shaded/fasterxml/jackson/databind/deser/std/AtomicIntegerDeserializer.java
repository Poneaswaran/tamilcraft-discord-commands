/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.LogicalType;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDeserializer
extends StdScalarDeserializer<AtomicInteger> {
    private static final long serialVersionUID = 1L;

    public AtomicIntegerDeserializer() {
        super(AtomicInteger.class);
    }

    @Override
    public AtomicInteger deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.isExpectedNumberIntToken()) {
            return new AtomicInteger(p.getIntValue());
        }
        Integer I = this._parseInteger(p, ctxt, AtomicInteger.class);
        return I == null ? null : new AtomicInteger(I);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Integer;
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return new AtomicInteger();
    }
}

