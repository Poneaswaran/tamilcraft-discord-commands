/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.module;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanDescription;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.KeyDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleKeyDeserializers
implements KeyDeserializers,
Serializable {
    private static final long serialVersionUID = 1L;
    protected HashMap<ClassKey, KeyDeserializer> _classMappings = null;

    public SimpleKeyDeserializers addDeserializer(Class<?> forClass, KeyDeserializer deser) {
        if (this._classMappings == null) {
            this._classMappings = new HashMap();
        }
        this._classMappings.put(new ClassKey(forClass), deser);
        return this;
    }

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }
}

