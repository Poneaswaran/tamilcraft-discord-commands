/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jasypt.iv;

import com.hypherionmc.sdlink.shaded.jasypt.iv.IvGenerator;

public class NoIvGenerator
implements IvGenerator {
    @Override
    public byte[] generateIv(int lengthBytes) {
        return new byte[0];
    }

    @Override
    public boolean includePlainIvInEncryptionResults() {
        return false;
    }
}

