/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonNode;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

public class FileSerializer
extends StdScalarSerializer<File> {
    public FileSerializer() {
        super(File.class);
    }

    @Override
    public void serialize(File value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeString(value.getAbsolutePath());
    }

    @Override
    @Deprecated
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        this.visitStringFormat(visitor, typeHint);
    }
}

