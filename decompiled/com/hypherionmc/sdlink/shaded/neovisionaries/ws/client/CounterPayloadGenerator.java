/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.neovisionaries.ws.client;

import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.Misc;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.PayloadGenerator;

class CounterPayloadGenerator
implements PayloadGenerator {
    private long mCount;

    CounterPayloadGenerator() {
    }

    public byte[] generate() {
        return Misc.getBytesUTF8(String.valueOf(this.increment()));
    }

    private long increment() {
        this.mCount = Math.max(this.mCount + 1L, 1L);
        return this.mCount;
    }
}

