/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jasypt.iv;

public interface IvGenerator {
    public byte[] generateIv(int var1);

    public boolean includePlainIvInEncryptionResults();
}

