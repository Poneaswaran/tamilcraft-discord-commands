/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public interface JsonSerializable {
    public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException;

    public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException;

    public static abstract class Base
    implements JsonSerializable {
        public boolean isEmpty(SerializerProvider serializers) {
            return false;
        }
    }
}

