/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeSerializer;

public class AsExistingPropertyTypeSerializer
extends AsPropertyTypeSerializer {
    public AsExistingPropertyTypeSerializer(TypeIdResolver idRes, BeanProperty property, String propName) {
        super(idRes, property, propName);
    }

    @Override
    public AsExistingPropertyTypeSerializer forProperty(BeanProperty prop) {
        return this._property == prop ? this : new AsExistingPropertyTypeSerializer(this._idResolver, prop, this._typePropertyName);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXISTING_PROPERTY;
    }
}

