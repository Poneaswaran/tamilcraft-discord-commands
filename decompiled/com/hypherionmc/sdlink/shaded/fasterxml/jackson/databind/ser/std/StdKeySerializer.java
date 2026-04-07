/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonGenerator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializerProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

@Deprecated
public class StdKeySerializer
extends StdSerializer<Object> {
    public StdKeySerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeFieldName(value.toString());
    }
}

