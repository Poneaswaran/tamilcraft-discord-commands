/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;

public interface ResolvableSerializer {
    public void resolve(SerializerProvider var1) throws JsonMappingException;
}

