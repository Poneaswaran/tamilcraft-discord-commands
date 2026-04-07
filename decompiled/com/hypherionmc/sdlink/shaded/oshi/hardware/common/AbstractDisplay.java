/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Display;
import com.hypherionmc.sdlink.shaded.oshi.util.EdidUtil;
import java.util.Arrays;

@Immutable
public abstract class AbstractDisplay
implements Display {
    private final byte[] edid;

    protected AbstractDisplay(byte[] edid) {
        this.edid = Arrays.copyOf(edid, edid.length);
    }

    @Override
    public byte[] getEdid() {
        return Arrays.copyOf(this.edid, this.edid.length);
    }

    public String toString() {
        return EdidUtil.toString(this.edid);
    }
}

