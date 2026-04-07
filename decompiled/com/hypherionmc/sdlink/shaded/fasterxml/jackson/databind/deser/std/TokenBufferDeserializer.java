/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.LogicalType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;

@JacksonStdImpl
public class TokenBufferDeserializer
extends StdScalarDeserializer<TokenBuffer> {
    private static final long serialVersionUID = 1L;

    public TokenBufferDeserializer() {
        super(TokenBuffer.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override
    public TokenBuffer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return ctxt.bufferForInputBuffering(p).deserialize(p, ctxt);
    }
}

