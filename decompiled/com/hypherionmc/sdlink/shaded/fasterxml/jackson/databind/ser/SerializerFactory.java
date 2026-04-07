/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonSerializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.Serializers;

public abstract class SerializerFactory {
    public abstract SerializerFactory withAdditionalSerializers(Serializers var1);

    public abstract SerializerFactory withAdditionalKeySerializers(Serializers var1);

    public abstract SerializerFactory withSerializerModifier(BeanSerializerModifier var1);

    public abstract JsonSerializer<Object> createSerializer(SerializerProvider var1, JavaType var2) throws JsonMappingException;

    public abstract TypeSerializer createTypeSerializer(SerializationConfig var1, JavaType var2) throws JsonMappingException;

    public JsonSerializer<Object> createKeySerializer(SerializerProvider prov, JavaType type, JsonSerializer<Object> defaultImpl) throws JsonMappingException {
        return this.createKeySerializer(prov.getConfig(), type, defaultImpl);
    }

    @Deprecated
    public abstract JsonSerializer<Object> createKeySerializer(SerializationConfig var1, JavaType var2, JsonSerializer<Object> var3) throws JsonMappingException;
}

