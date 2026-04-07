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
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongDeserializer
extends StdScalarDeserializer<AtomicLong> {
    private static final long serialVersionUID = 1L;

    public AtomicLongDeserializer() {
        super(AtomicLong.class);
    }

    @Override
    public AtomicLong deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.isExpectedNumberIntToken()) {
            return new AtomicLong(p.getLongValue());
        }
        Long L = this._parseLong(p, ctxt, AtomicLong.class);
        return L == null ? null : new AtomicLong(L.intValue());
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Integer;
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return new AtomicLong();
    }
}

