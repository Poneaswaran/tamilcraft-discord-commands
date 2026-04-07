/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase;

public class AsArrayTypeSerializer
extends TypeSerializerBase {
    public AsArrayTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
        super(idRes, property);
    }

    @Override
    public AsArrayTypeSerializer forProperty(BeanProperty prop) {
        return this._property == prop ? this : new AsArrayTypeSerializer(this._idResolver, prop);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }
}

