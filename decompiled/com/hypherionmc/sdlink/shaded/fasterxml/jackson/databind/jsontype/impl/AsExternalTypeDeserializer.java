/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;

public class AsExternalTypeDeserializer
extends AsArrayTypeDeserializer {
    private static final long serialVersionUID = 1L;

    public AsExternalTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public AsExternalTypeDeserializer(AsExternalTypeDeserializer src, BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(BeanProperty prop) {
        if (prop == this._property) {
            return this;
        }
        return new AsExternalTypeDeserializer(this, prop);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXTERNAL_PROPERTY;
    }

    @Override
    protected boolean _usesExternalId() {
        return true;
    }
}

