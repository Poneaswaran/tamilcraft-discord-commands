/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public enum DataType {
    INT,
    FLOAT,
    STRING,
    OBJECT,
    ARRAY,
    BOOLEAN,
    NULL,
    UNKNOWN;


    @Nonnull
    public static DataType getType(@Nullable Object value) {
        for (DataType type : DataType.values()) {
            if (!type.isType(value)) continue;
            return type;
        }
        return UNKNOWN;
    }

    public boolean isType(@Nullable Object value) {
        switch (this) {
            case INT: {
                return value instanceof Integer || value instanceof Long || value instanceof Short || value instanceof Byte;
            }
            case FLOAT: {
                return value instanceof Double || value instanceof Float;
            }
            case STRING: {
                return value instanceof String;
            }
            case BOOLEAN: {
                return value instanceof Boolean;
            }
            case ARRAY: {
                return value instanceof List;
            }
            case OBJECT: {
                return value instanceof Map;
            }
            case NULL: {
                return value == null;
            }
        }
        return false;
    }
}

