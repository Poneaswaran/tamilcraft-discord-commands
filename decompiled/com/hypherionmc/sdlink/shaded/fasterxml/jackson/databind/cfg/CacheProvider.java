/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonSerializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.LookupCache;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.TypeKey;
import java.io.Serializable;

public interface CacheProvider
extends Serializable {
    public LookupCache<JavaType, JsonDeserializer<Object>> forDeserializerCache(DeserializationConfig var1);

    public LookupCache<TypeKey, JsonSerializer<Object>> forSerializerCache(SerializationConfig var1);

    public LookupCache<Object, JavaType> forTypeFactory();
}

