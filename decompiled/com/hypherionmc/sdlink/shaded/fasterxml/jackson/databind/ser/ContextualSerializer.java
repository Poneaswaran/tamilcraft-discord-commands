/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonSerializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;

public interface ContextualSerializer {
    public JsonSerializer<?> createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException;
}

