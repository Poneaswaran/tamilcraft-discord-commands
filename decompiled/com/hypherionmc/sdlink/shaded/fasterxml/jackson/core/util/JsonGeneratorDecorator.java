/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonFactory;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;

public interface JsonGeneratorDecorator {
    public JsonGenerator decorate(JsonFactory var1, JsonGenerator var2);
}

