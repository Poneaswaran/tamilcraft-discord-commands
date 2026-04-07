/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jasypt.salt;

public interface SaltGenerator {
    public byte[] generateSalt(int var1);

    public boolean includePlainSaltInEncryptionResults();
}

