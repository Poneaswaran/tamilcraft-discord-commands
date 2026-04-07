/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.NullValueProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.AccessPattern;
import java.io.Serializable;

public class NullsAsEmptyProvider
implements NullValueProvider,
Serializable {
    private static final long serialVersionUID = 1L;
    protected final JsonDeserializer<?> _deserializer;

    public NullsAsEmptyProvider(JsonDeserializer<?> deser) {
        this._deserializer = deser;
    }

    @Override
    public AccessPattern getNullAccessPattern() {
        return AccessPattern.DYNAMIC;
    }

    @Override
    public Object getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return this._deserializer.getEmptyValue(ctxt);
    }
}

