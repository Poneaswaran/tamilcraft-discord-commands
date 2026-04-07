/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.LogicalType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferDeserializer
extends StdScalarDeserializer<ByteBuffer> {
    private static final long serialVersionUID = 1L;

    protected ByteBufferDeserializer() {
        super(ByteBuffer.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Binary;
    }

    @Override
    public ByteBuffer deserialize(JsonParser parser, DeserializationContext cx) throws IOException {
        byte[] b = parser.getBinaryValue();
        return ByteBuffer.wrap(b);
    }

    @Override
    public ByteBuffer deserialize(JsonParser jp, DeserializationContext ctxt, ByteBuffer intoValue) throws IOException {
        ByteBufferBackedOutputStream out = new ByteBufferBackedOutputStream(intoValue);
        jp.readBinaryValue(ctxt.getBase64Variant(), out);
        out.close();
        return intoValue;
    }
}

