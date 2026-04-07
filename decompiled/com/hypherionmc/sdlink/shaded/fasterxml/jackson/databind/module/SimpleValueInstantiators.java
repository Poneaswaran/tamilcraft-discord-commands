/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.module;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.BeanDescription;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleValueInstantiators
extends ValueInstantiators.Base
implements Serializable {
    private static final long serialVersionUID = -8929386427526115130L;
    protected HashMap<ClassKey, ValueInstantiator> _classMappings = new HashMap();

    public SimpleValueInstantiators addValueInstantiator(Class<?> forType, ValueInstantiator inst) {
        this._classMappings.put(new ClassKey(forType), inst);
        return this;
    }

    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator) {
        ValueInstantiator inst = this._classMappings.get(new ClassKey(beanDesc.getBeanClass()));
        return inst == null ? defaultInstantiator : inst;
    }
}

