/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanDescription;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonSerializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.ArrayType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.CollectionType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.MapLikeType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.MapType;
import java.io.Serializable;
import java.util.List;

public abstract class BeanSerializerModifier
implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        return beanProperties;
    }

    public List<BeanPropertyWriter> orderProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        return beanProperties;
    }

    public BeanSerializerBuilder updateBuilder(SerializationConfig config, BeanDescription beanDesc, BeanSerializerBuilder builder) {
        return builder;
    }

    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyArraySerializer(SerializationConfig config, ArrayType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyCollectionSerializer(SerializationConfig config, CollectionType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyCollectionLikeSerializer(SerializationConfig config, CollectionLikeType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyMapSerializer(SerializationConfig config, MapType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyMapLikeSerializer(SerializationConfig config, MapLikeType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }

    public JsonSerializer<?> modifyKeySerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return serializer;
    }
}

