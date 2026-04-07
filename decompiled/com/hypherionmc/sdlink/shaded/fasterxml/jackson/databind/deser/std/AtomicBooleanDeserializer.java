/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonToken;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.LogicalType;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDeserializer
extends StdScalarDeserializer<AtomicBoolean> {
    private static final long serialVersionUID = 1L;

    public AtomicBooleanDeserializer() {
        super(AtomicBoolean.class);
    }

    @Override
    public AtomicBoolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken t = p.currentToken();
        if (t == JsonToken.VALUE_TRUE) {
            return new AtomicBoolean(true);
        }
        if (t == JsonToken.VALUE_FALSE) {
            return new AtomicBoolean(false);
        }
        Boolean b = this._parseBoolean(p, ctxt, AtomicBoolean.class);
        return b == null ? null : new AtomicBoolean(b);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Boolean;
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return new AtomicBoolean(false);
    }
}

