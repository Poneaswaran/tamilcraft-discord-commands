/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.AccessPattern;

public interface NullValueProvider {
    public Object getNullValue(DeserializationContext var1) throws JsonMappingException;

    public AccessPattern getNullAccessPattern();

    default public Object getAbsentValue(DeserializationContext ctxt) throws JsonMappingException {
        return this.getNullValue(ctxt);
    }
}

