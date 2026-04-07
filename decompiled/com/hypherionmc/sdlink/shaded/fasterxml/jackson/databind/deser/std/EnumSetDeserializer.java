/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonFormat;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonToken;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationFeature;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.NullValueProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.LogicalType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;

public class EnumSetDeserializer
extends StdDeserializer<EnumSet<?>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 2L;
    protected final JavaType _enumType;
    protected JsonDeserializer<Enum<?>> _enumDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;
    protected final NullValueProvider _nullProvider;
    protected final boolean _skipNullValues;
    protected final Boolean _unwrapSingle;

    public EnumSetDeserializer(JavaType enumType, JsonDeserializer<?> deser, TypeDeserializer valueTypeDeser) {
        super(EnumSet.class);
        this._enumType = enumType;
        if (!enumType.isEnumType()) {
            throw new IllegalArgumentException("Type " + enumType + " not Java Enum type");
        }
        this._enumDeserializer = deser;
        this._valueTypeDeserializer = valueTypeDeser;
        this._unwrapSingle = null;
        this._nullProvider = null;
        this._skipNullValues = false;
    }

    @Deprecated
    public EnumSetDeserializer(JavaType enumType, JsonDeserializer<?> deser) {
        this(enumType, deser, null);
    }

    @Deprecated
    protected EnumSetDeserializer(EnumSetDeserializer base, JsonDeserializer<?> deser, Boolean unwrapSingle) {
        this(base, deser, base._nullProvider, unwrapSingle);
    }

    protected EnumSetDeserializer(EnumSetDeserializer base, JsonDeserializer<?> deser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(base);
        this._enumType = base._enumType;
        this._enumDeserializer = deser;
        this._valueTypeDeserializer = base._valueTypeDeserializer;
        this._nullProvider = nuller;
        this._skipNullValues = NullsConstantProvider.isSkipper(nuller);
        this._unwrapSingle = unwrapSingle;
    }

    public EnumSetDeserializer withDeserializer(JsonDeserializer<?> deser) {
        if (this._enumDeserializer == deser) {
            return this;
        }
        return new EnumSetDeserializer(this, deser, this._nullProvider, this._unwrapSingle);
    }

    public EnumSetDeserializer withResolved(JsonDeserializer<?> deser, TypeDeserializer valueTypeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        if (Objects.equals(this._unwrapSingle, unwrapSingle) && this._enumDeserializer == deser && this._valueTypeDeserializer == valueTypeDeser && this._nullProvider == deser) {
            return this;
        }
        return new EnumSetDeserializer(this, deser, nuller, unwrapSingle);
    }

    @Deprecated
    public EnumSetDeserializer withResolved(JsonDeserializer<?> deser, NullValueProvider nuller, Boolean unwrapSingle) {
        return this.withResolved(deser, this._valueTypeDeserializer, nuller, unwrapSingle);
    }

    @Override
    public boolean isCachable() {
        return this._enumType.getValueHandler() == null && this._valueTypeDeserializer == null;
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Collection;
    }

    @Override
    public Boolean supportsUpdate(DeserializationConfig config) {
        return Boolean.TRUE;
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return this.constructSet();
    }

    @Override
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.DYNAMIC;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        Boolean unwrapSingle = this.findFormatFeature(ctxt, property, EnumSet.class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonDeserializer<Object> deser = this._enumDeserializer;
        deser = deser == null ? ctxt.findContextualValueDeserializer(this._enumType, property) : ctxt.handleSecondaryContextualization(deser, property, this._enumType);
        TypeDeserializer valueTypeDeser = this._valueTypeDeserializer;
        if (valueTypeDeser != null) {
            valueTypeDeser = valueTypeDeser.forProperty(property);
        }
        return this.withResolved(deser, valueTypeDeser, this.findContentNullProvider(ctxt, property, deser), unwrapSingle);
    }

    @Override
    public EnumSet<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        EnumSet result = this.constructSet();
        if (!p.isExpectedStartArrayToken()) {
            return this.handleNonArray(p, ctxt, result);
        }
        return this._deserialize(p, ctxt, result);
    }

    @Override
    public EnumSet<?> deserialize(JsonParser p, DeserializationContext ctxt, EnumSet<?> result) throws IOException {
        if (!p.isExpectedStartArrayToken()) {
            return this.handleNonArray(p, ctxt, result);
        }
        return this._deserialize(p, ctxt, result);
    }

    protected final EnumSet<?> _deserialize(JsonParser p, DeserializationContext ctxt, EnumSet result) throws IOException {
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        try {
            JsonToken t;
            while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
                Enum value;
                if (t == JsonToken.VALUE_NULL) {
                    if (this._skipNullValues) continue;
                    value = (Enum)this._nullProvider.getNullValue(ctxt);
                } else {
                    value = typeDeser == null ? this._enumDeserializer.deserialize(p, ctxt) : (Enum)this._enumDeserializer.deserializeWithType(p, ctxt, typeDeser);
                }
                if (value == null) continue;
                result.add(value);
            }
        }
        catch (Exception e) {
            throw JsonMappingException.wrapWithPath((Throwable)e, (Object)result, result.size());
        }
        return result;
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(p, ctxt);
    }

    private EnumSet constructSet() {
        return EnumSet.noneOf(this._enumType.getRawClass());
    }

    protected EnumSet<?> handleNonArray(JsonParser p, DeserializationContext ctxt, EnumSet result) throws IOException {
        boolean canWrap;
        boolean bl = canWrap = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        if (!canWrap) {
            return (EnumSet)ctxt.handleUnexpectedToken(EnumSet.class, p);
        }
        if (p.hasToken(JsonToken.VALUE_NULL)) {
            return (EnumSet)ctxt.handleUnexpectedToken(this._enumType, p);
        }
        try {
            Enum<?> value = this._enumDeserializer.deserialize(p, ctxt);
            if (value != null) {
                result.add(value);
            }
        }
        catch (Exception e) {
            throw JsonMappingException.wrapWithPath((Throwable)e, (Object)result, result.size());
        }
        return result;
    }
}

