/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.NullValueProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDeserializer
extends CollectionDeserializer {
    private static final long serialVersionUID = 1L;

    public ArrayBlockingQueueDeserializer(JavaType containerType, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser, ValueInstantiator valueInstantiator) {
        super(containerType, valueDeser, valueTypeDeser, valueInstantiator);
    }

    protected ArrayBlockingQueueDeserializer(JavaType containerType, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser, ValueInstantiator valueInstantiator, JsonDeserializer<Object> delegateDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(containerType, valueDeser, valueTypeDeser, valueInstantiator, delegateDeser, nuller, unwrapSingle);
    }

    protected ArrayBlockingQueueDeserializer(ArrayBlockingQueueDeserializer src) {
        super(src);
    }

    @Override
    protected ArrayBlockingQueueDeserializer withResolved(JsonDeserializer<?> dd, JsonDeserializer<?> vd, TypeDeserializer vtd, NullValueProvider nuller, Boolean unwrapSingle) {
        return new ArrayBlockingQueueDeserializer(this._containerType, vd, vtd, this._valueInstantiator, dd, nuller, unwrapSingle);
    }

    @Override
    protected Collection<Object> createDefaultInstance(DeserializationContext ctxt) throws IOException {
        return null;
    }

    @Override
    protected Collection<Object> _deserializeFromArray(JsonParser p, DeserializationContext ctxt, Collection<Object> result0) throws IOException {
        if (result0 == null) {
            result0 = new ArrayList<Object>();
        }
        if ((result0 = super._deserializeFromArray(p, ctxt, result0)).isEmpty()) {
            return new ArrayBlockingQueue<Object>(1, false);
        }
        return new ArrayBlockingQueue<Object>(result0.size(), false, result0);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(p, ctxt);
    }
}

