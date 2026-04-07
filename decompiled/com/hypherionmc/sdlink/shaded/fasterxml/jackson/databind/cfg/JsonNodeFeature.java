/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg.DatatypeFeature;

public enum JsonNodeFeature implements DatatypeFeature
{
    READ_NULL_PROPERTIES(true),
    WRITE_NULL_PROPERTIES(true),
    WRITE_PROPERTIES_SORTED(false),
    STRIP_TRAILING_BIGDECIMAL_ZEROES(true),
    FAIL_ON_NAN_TO_BIG_DECIMAL_COERCION(false);

    private static final int FEATURE_INDEX = 1;
    private final boolean _enabledByDefault;
    private final int _mask;

    private JsonNodeFeature(boolean enabledByDefault) {
        this._enabledByDefault = enabledByDefault;
        this._mask = 1 << this.ordinal();
    }

    @Override
    public boolean enabledByDefault() {
        return this._enabledByDefault;
    }

    @Override
    public boolean enabledIn(int flags) {
        return (flags & this._mask) != 0;
    }

    @Override
    public int getMask() {
        return this._mask;
    }

    @Override
    public int featureIndex() {
        return 1;
    }
}

