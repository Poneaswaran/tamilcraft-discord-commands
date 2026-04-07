/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonToken;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.type.WritableTypeId;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonNode;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public abstract class ToStringSerializerBase
extends StdSerializer<Object> {
    public ToStringSerializerBase(Class<?> handledType) {
        super(handledType, false);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object value) {
        return this.valueToString(value).isEmpty();
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(this.valueToString(value));
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_STRING));
        this.serialize(value, g, provider);
        typeSer.writeTypeSuffix(g, typeIdDef);
    }

    @Override
    @Deprecated
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        this.visitStringFormat(visitor, typeHint);
    }

    public abstract String valueToString(Object var1);
}

