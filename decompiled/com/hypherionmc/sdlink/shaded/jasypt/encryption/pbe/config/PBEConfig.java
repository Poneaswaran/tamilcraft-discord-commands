/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe.config;

import com.hypherionmc.sdlink.shaded.jasypt.iv.IvGenerator;
import com.hypherionmc.sdlink.shaded.jasypt.salt.SaltGenerator;
import java.security.Provider;

public interface PBEConfig {
    public String getAlgorithm();

    public String getPassword();

    public Integer getKeyObtentionIterations();

    public SaltGenerator getSaltGenerator();

    public IvGenerator getIvGenerator();

    public String getProviderName();

    public Provider getProvider();

    public Integer getPoolSize();
}

