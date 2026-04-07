/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.impl;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonLocation;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.deser.std.JsonLocationInstantiator;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public abstract class JDKValueInstantiators {
    public static ValueInstantiator findStdValueInstantiator(DeserializationConfig config, Class<?> raw) {
        if (raw == JsonLocation.class) {
            return new JsonLocationInstantiator();
        }
        if (Collection.class.isAssignableFrom(raw)) {
            if (raw == ArrayList.class) {
                return ArrayListInstantiator.INSTANCE;
            }
            if (raw == HashSet.class) {
                return HashSetInstantiator.INSTANCE;
            }
            if (raw == LinkedList.class) {
                return new LinkedListInstantiator();
            }
            if (raw == TreeSet.class) {
                return new TreeSetInstantiator();
            }
            if (raw == Collections.emptySet().getClass()) {
                return new ConstantValueInstantiator(Collections.emptySet());
            }
            if (raw == Collections.emptyList().getClass()) {
                return new ConstantValueInstantiator(Collections.emptyList());
            }
        } else if (Map.class.isAssignableFrom(raw)) {
            if (raw == LinkedHashMap.class) {
                return LinkedHashMapInstantiator.INSTANCE;
            }
            if (raw == HashMap.class) {
                return HashMapInstantiator.INSTANCE;
            }
            if (raw == ConcurrentHashMap.class) {
                return new ConcurrentHashMapInstantiator();
            }
            if (raw == TreeMap.class) {
                return new TreeMapInstantiator();
            }
            if (raw == Collections.emptyMap().getClass()) {
                return new ConstantValueInstantiator(Collections.emptyMap());
            }
        }
        return null;
    }

    private static class ConstantValueInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;
        protected final Object _value;

        public ConstantValueInstantiator(Object value) {
            super(value.getClass());
            this._value = value;
        }

        @Override
        public final Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return this._value;
        }
    }

    private static class TreeMapInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;

        public TreeMapInstantiator() {
            super(TreeMap.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new TreeMap();
        }
    }

    private static class LinkedHashMapInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;
        static final LinkedHashMapInstantiator INSTANCE = new LinkedHashMapInstantiator();

        public LinkedHashMapInstantiator() {
            super(LinkedHashMap.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new LinkedHashMap();
        }
    }

    private static class HashMapInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;
        static final HashMapInstantiator INSTANCE = new HashMapInstantiator();

        public HashMapInstantiator() {
            super(HashMap.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new HashMap();
        }
    }

    private static class ConcurrentHashMapInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;

        public ConcurrentHashMapInstantiator() {
            super(ConcurrentHashMap.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new ConcurrentHashMap();
        }
    }

    private static class TreeSetInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;

        public TreeSetInstantiator() {
            super(TreeSet.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new TreeSet();
        }
    }

    private static class HashSetInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;
        static final HashSetInstantiator INSTANCE = new HashSetInstantiator();

        public HashSetInstantiator() {
            super(HashSet.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new HashSet();
        }
    }

    private static class LinkedListInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;

        public LinkedListInstantiator() {
            super(LinkedList.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new LinkedList();
        }
    }

    private static class ArrayListInstantiator
    extends JDKValueInstantiator {
        private static final long serialVersionUID = 2L;
        static final ArrayListInstantiator INSTANCE = new ArrayListInstantiator();

        public ArrayListInstantiator() {
            super(ArrayList.class);
        }

        @Override
        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            return new ArrayList();
        }
    }

    private static abstract class JDKValueInstantiator
    extends ValueInstantiator.Base
    implements Serializable {
        private static final long serialVersionUID = 2L;

        public JDKValueInstantiator(Class<?> type) {
            super(type);
        }

        @Override
        public final boolean canInstantiate() {
            return true;
        }

        @Override
        public final boolean canCreateUsingDefault() {
            return true;
        }

        @Override
        public abstract Object createUsingDefault(DeserializationContext var1) throws IOException;
    }
}

