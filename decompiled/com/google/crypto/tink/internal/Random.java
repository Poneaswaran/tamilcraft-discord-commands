/*
 * Decompiled with CFR 0.152.
 */
package com.google.crypto.tink.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;

public final class Random {
    private static final ThreadLocal<SecureRandom> localRandom = new ThreadLocal<SecureRandom>(){

        @Override
        protected SecureRandom initialValue() {
            return Random.newDefaultSecureRandom();
        }
    };

    private static Provider getConscryptProviderWithReflection() throws GeneralSecurityException {
        try {
            Class<?> conscrypt = Class.forName("org.conscrypt.Conscrypt");
            Method getProvider = conscrypt.getMethod("newProvider", new Class[0]);
            return (Provider)getProvider.invoke(null, new Object[0]);
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            throw new GeneralSecurityException("Failed to get Conscrypt provider", e);
        }
    }

    private static SecureRandom create() {
        try {
            return SecureRandom.getInstance("SHA1PRNG", "GmsCore_OpenSSL");
        }
        catch (GeneralSecurityException generalSecurityException) {
            try {
                return SecureRandom.getInstance("SHA1PRNG", "AndroidOpenSSL");
            }
            catch (GeneralSecurityException generalSecurityException2) {
                try {
                    return SecureRandom.getInstance("SHA1PRNG", "Conscrypt");
                }
                catch (GeneralSecurityException generalSecurityException3) {
                    try {
                        return SecureRandom.getInstance("SHA1PRNG", Random.getConscryptProviderWithReflection());
                    }
                    catch (GeneralSecurityException generalSecurityException4) {
                        return new SecureRandom();
                    }
                }
            }
        }
    }

    private static SecureRandom newDefaultSecureRandom() {
        SecureRandom retval = Random.create();
        retval.nextLong();
        return retval;
    }

    public static byte[] randBytes(int size) {
        byte[] rand = new byte[size];
        localRandom.get().nextBytes(rand);
        return rand;
    }

    public static final int randInt(int max) {
        return localRandom.get().nextInt(max);
    }

    public static final int randInt() {
        return localRandom.get().nextInt();
    }

    public static final void validateUsesConscrypt() throws GeneralSecurityException {
        String providerName = localRandom.get().getProvider().getName();
        if (!(providerName.equals("GmsCore_OpenSSL") || providerName.equals("AndroidOpenSSL") || providerName.equals("Conscrypt"))) {
            throw new GeneralSecurityException("Requires GmsCore_OpenSSL, AndroidOpenSSL or Conscrypt to generate randomness, but got " + providerName);
        }
    }

    private Random() {
    }
}

