/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonToken;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Arrays;

public class ThrowableDeserializer
extends BeanDeserializer {
    private static final long serialVersionUID = 1L;
    protected static final String PROP_NAME_MESSAGE = "message";
    protected static final String PROP_NAME_SUPPRESSED = "suppressed";
    protected static final String PROP_NAME_LOCALIZED_MESSAGE = "localizedMessage";

    protected ThrowableDeserializer(BeanDeserializer src, NameTransformer unwrapper) {
        super((BeanDeserializerBase)src, unwrapper);
        this._vanillaProcessing = false;
    }

    @Deprecated
    public ThrowableDeserializer(BeanDeserializer baseDeserializer) {
        this(baseDeserializer, null);
    }

    public static ThrowableDeserializer construct(DeserializationContext ctxt, BeanDeserializer baseDeserializer) {
        return new ThrowableDeserializer(baseDeserializer, (NameTransformer)null);
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
        if (this.getClass() != ThrowableDeserializer.class) {
            return this;
        }
        return new ThrowableDeserializer(this, unwrapper);
    }

    @Override
    public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased(p, ctxt);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
        }
        if (this._beanType.isAbstract()) {
            return ctxt.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), p, "abstract type (need to add/enable type information?)", new Object[0]);
        }
        boolean hasStringCreator = this._valueInstantiator.canCreateFromString();
        boolean hasDefaultCtor = this._valueInstantiator.canCreateUsingDefault();
        if (!hasStringCreator && !hasDefaultCtor) {
            return ctxt.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), p, "Throwable needs a default constructor, a single-String-arg constructor; or explicit @JsonCreator", new Object[0]);
        }
        Throwable throwable = null;
        Object[] pending = null;
        Throwable[] suppressed = null;
        int pendingIx = 0;
        while (!p.hasToken(JsonToken.END_OBJECT)) {
            String propName = p.currentName();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            p.nextToken();
            if (prop != null) {
                if (!"cause".equals(prop.getName()) || !p.hasToken(JsonToken.VALUE_NULL)) {
                    if (throwable != null) {
                        prop.deserializeAndSet(p, ctxt, throwable);
                    } else {
                        if (pending == null) {
                            int len = this._beanProperties.size();
                            pending = new Object[len + len];
                        } else if (pendingIx == pending.length) {
                            pending = Arrays.copyOf(pending, pendingIx + 16);
                        }
                        pending[pendingIx++] = prop;
                        pending[pendingIx++] = prop.deserialize(p, ctxt);
                    }
                }
            } else if (PROP_NAME_MESSAGE.equalsIgnoreCase(propName)) {
                throwable = this._instantiate(ctxt, hasStringCreator, p.getValueAsString());
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                p.skipChildren();
            } else if (PROP_NAME_SUPPRESSED.equalsIgnoreCase(propName)) {
                if (p.hasToken(JsonToken.VALUE_NULL)) {
                    suppressed = null;
                } else {
                    JsonDeserializer<Object> deser = ctxt.findRootValueDeserializer(ctxt.constructType(Throwable[].class));
                    suppressed = (Throwable[])deser.deserialize(p, ctxt);
                }
            } else if (PROP_NAME_LOCALIZED_MESSAGE.equalsIgnoreCase(propName)) {
                p.skipChildren();
            } else if (this._anySetter != null) {
                if (throwable == null) {
                    throwable = this._instantiate(ctxt, hasStringCreator, null);
                }
                this._anySetter.deserializeAndSet(p, ctxt, throwable, propName);
            } else if (PROP_NAME_MESSAGE.equalsIgnoreCase(propName)) {
                p.skipChildren();
            } else {
                this.handleUnknownProperty(p, ctxt, throwable, propName);
            }
            p.nextToken();
        }
        if (throwable == null) {
            throwable = this._instantiate(ctxt, hasStringCreator, null);
        }
        if (pending != null) {
            int len = pendingIx;
            for (int i = 0; i < len; i += 2) {
                SettableBeanProperty prop = (SettableBeanProperty)pending[i];
                prop.set(throwable, pending[i + 1]);
            }
        }
        if (suppressed != null) {
            for (void var12_19 : suppressed) {
                if (var12_19 == null) continue;
                throwable.addSuppressed((Throwable)var12_19);
            }
        }
        return throwable;
    }

    private Throwable _instantiate(DeserializationContext ctxt, boolean hasStringCreator, String valueAsString) throws IOException {
        if (hasStringCreator) {
            if (valueAsString != null) {
                return (Throwable)this._valueInstantiator.createFromString(ctxt, valueAsString);
            }
            return (Throwable)this._valueInstantiator.createFromString(ctxt, null);
        }
        return (Throwable)this._valueInstantiator.createUsingDefault(ctxt);
    }
}

