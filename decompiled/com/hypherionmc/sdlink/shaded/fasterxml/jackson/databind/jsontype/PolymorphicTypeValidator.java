/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.jsontype;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
import java.io.Serializable;

public abstract class PolymorphicTypeValidator
implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract Validity validateBaseType(MapperConfig<?> var1, JavaType var2);

    public abstract Validity validateSubClassName(MapperConfig<?> var1, JavaType var2, String var3) throws JsonMappingException;

    public abstract Validity validateSubType(MapperConfig<?> var1, JavaType var2, JavaType var3) throws JsonMappingException;

    public static abstract class Base
    extends PolymorphicTypeValidator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Validity validateBaseType(MapperConfig<?> config, JavaType baseType) {
            return Validity.INDETERMINATE;
        }

        @Override
        public Validity validateSubClassName(MapperConfig<?> config, JavaType baseType, String subClassName) throws JsonMappingException {
            return Validity.INDETERMINATE;
        }

        @Override
        public Validity validateSubType(MapperConfig<?> config, JavaType baseType, JavaType subType) throws JsonMappingException {
            return Validity.INDETERMINATE;
        }
    }

    public static enum Validity {
        ALLOWED,
        DENIED,
        INDETERMINATE;

    }
}

