/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsonschema;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonNode;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import java.lang.reflect.Type;

@Deprecated
public interface SchemaAware {
    public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException;

    public JsonNode getSchema(SerializerProvider var1, Type var2, boolean var3) throws JsonMappingException;
}

