/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class UnsupportedTypeSerializer
extends StdSerializer<Object> {
    private static final long serialVersionUID = 1L;
    protected final JavaType _type;
    protected final String _message;

    public UnsupportedTypeSerializer(JavaType t, String msg) {
        super(Object.class);
        this._type = t;
        this._message = msg;
    }

    @Override
    public void serialize(Object value, JsonGenerator g, SerializerProvider ctxt) throws IOException {
        ctxt.reportBadDefinition(this._type, this._message);
    }
}

