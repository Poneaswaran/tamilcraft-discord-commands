/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonToken;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.type.WritableTypeId;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase;
import java.io.IOException;

public class AsDeductionTypeSerializer
extends TypeSerializerBase {
    private static final AsDeductionTypeSerializer INSTANCE = new AsDeductionTypeSerializer();

    protected AsDeductionTypeSerializer() {
        super(null, null);
    }

    public static AsDeductionTypeSerializer instance() {
        return INSTANCE;
    }

    @Override
    public AsDeductionTypeSerializer forProperty(BeanProperty prop) {
        return this;
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXISTING_PROPERTY;
    }

    @Override
    public WritableTypeId writeTypePrefix(JsonGenerator g, WritableTypeId idMetadata) throws IOException {
        if (idMetadata.valueShape.isStructStart()) {
            if (g.canWriteTypeId()) {
                idMetadata.wrapperWritten = false;
                if (idMetadata.valueShape == JsonToken.START_OBJECT) {
                    g.writeStartObject(idMetadata.forValue);
                } else if (idMetadata.valueShape == JsonToken.START_ARRAY) {
                    g.writeStartArray(idMetadata.forValue);
                }
                return idMetadata;
            }
            return g.writeTypePrefix(idMetadata);
        }
        return null;
    }

    @Override
    public WritableTypeId writeTypeSuffix(JsonGenerator g, WritableTypeId idMetadata) throws IOException {
        return idMetadata == null ? null : g.writeTypeSuffix(idMetadata);
    }
}

