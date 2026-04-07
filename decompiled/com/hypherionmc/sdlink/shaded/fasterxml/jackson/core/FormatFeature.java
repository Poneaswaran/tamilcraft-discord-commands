/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.JacksonFeature;

public interface FormatFeature
extends JacksonFeature {
    @Override
    public boolean enabledByDefault();

    @Override
    public int getMask();

    @Override
    public boolean enabledIn(int var1);
}

