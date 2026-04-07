/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsonFormatVisitors;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;

public interface JsonFormatVisitorWithSerializerProvider {
    public SerializerProvider getProvider();

    public void setProvider(SerializerProvider var1);
}

