/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.KeyDeserializer;

public interface ContextualKeyDeserializer {
    public KeyDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException;
}

