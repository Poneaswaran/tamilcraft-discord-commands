/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ibm.icu.text.Normalizer
 *  com.ibm.icu.text.Normalizer$Mode
 */
package com.hypherionmc.sdlink.shaded.jasypt.normalization;

import com.hypherionmc.sdlink.shaded.jasypt.exceptions.EncryptionInitializationException;
import com.ibm.icu.text.Normalizer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Normalizer {
    private static final String ICU_NORMALIZER_CLASS_NAME = "com.ibm.icu.text.Normalizer";
    private static final String JDK_NORMALIZER_CLASS_NAME = "java.text.Normalizer";
    private static final String JDK_NORMALIZER_FORM_CLASS_NAME = "java.text.Normalizer$Form";
    private static Boolean useIcuNormalizer = null;
    private static Method javaTextNormalizerMethod = null;
    private static Object javaTextNormalizerFormNFCConstant = null;

    public static String normalizeToNfc(String message) {
        return new String(Normalizer.normalizeToNfc(message.toCharArray()));
    }

    public static char[] normalizeToNfc(char[] message) {
        if (useIcuNormalizer == null) {
            try {
                Normalizer.initializeIcu4j();
            }
            catch (ClassNotFoundException e) {
                try {
                    Normalizer.initializeJavaTextNormalizer();
                }
                catch (ClassNotFoundException e2) {
                    throw new EncryptionInitializationException("Cannot find a valid UNICODE normalizer: neither java.text.Normalizer nor com.ibm.icu.text.Normalizer have been found at the classpath. If you are using a version of the JDK older than JavaSE 6, you should include the icu4j library in your classpath.");
                }
                catch (NoSuchMethodException e2) {
                    throw new EncryptionInitializationException("Cannot find a valid UNICODE normalizer: java.text.Normalizer has been found at the classpath, but has an incompatible signature for its 'normalize' method.");
                }
                catch (NoSuchFieldException e2) {
                    throw new EncryptionInitializationException("Cannot find a valid UNICODE normalizer: java.text.Normalizer$Form has been found at the classpath, but seems to have no 'NFC' value.");
                }
                catch (IllegalAccessException e2) {
                    throw new EncryptionInitializationException("Cannot find a valid UNICODE normalizer: java.text.Normalizer$Form has been found at the classpath, but seems to have no 'NFC' value.");
                }
            }
        }
        if (useIcuNormalizer.booleanValue()) {
            return Normalizer.normalizeWithIcu4j(message);
        }
        return Normalizer.normalizeWithJavaNormalizer(message);
    }

    static void initializeIcu4j() throws ClassNotFoundException {
        Thread.currentThread().getContextClassLoader().loadClass(ICU_NORMALIZER_CLASS_NAME);
        useIcuNormalizer = Boolean.TRUE;
    }

    static void initializeJavaTextNormalizer() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        Class<?> javaTextNormalizerClass = Thread.currentThread().getContextClassLoader().loadClass(JDK_NORMALIZER_CLASS_NAME);
        Class<?> javaTextNormalizerFormClass = Thread.currentThread().getContextClassLoader().loadClass(JDK_NORMALIZER_FORM_CLASS_NAME);
        javaTextNormalizerMethod = javaTextNormalizerClass.getMethod("normalize", CharSequence.class, javaTextNormalizerFormClass);
        Field javaTextNormalizerFormNFCConstantField = javaTextNormalizerFormClass.getField("NFC");
        javaTextNormalizerFormNFCConstant = javaTextNormalizerFormNFCConstantField.get(null);
        useIcuNormalizer = Boolean.FALSE;
    }

    static char[] normalizeWithJavaNormalizer(char[] message) {
        String result;
        if (javaTextNormalizerMethod == null || javaTextNormalizerFormNFCConstant == null) {
            throw new EncryptionInitializationException("Cannot use: java.text.Normalizer$Form, as JDK-based normalization has not been initialized! (check previous execution errors)");
        }
        String messageStr = new String(message);
        try {
            result = (String)javaTextNormalizerMethod.invoke(null, messageStr, javaTextNormalizerFormNFCConstant);
        }
        catch (Exception e) {
            throw new EncryptionInitializationException("Could not perform a valid UNICODE normalization", e);
        }
        return result.toCharArray();
    }

    static char[] normalizeWithIcu4j(char[] message) {
        char[] normalizationResult = new char[message.length * 2];
        int normalizationResultSize = 0;
        while (true) {
            if ((normalizationResultSize = com.ibm.icu.text.Normalizer.normalize((char[])message, (char[])normalizationResult, (Normalizer.Mode)com.ibm.icu.text.Normalizer.NFC, (int)0)) <= normalizationResult.length) {
                char[] result = new char[normalizationResultSize];
                System.arraycopy(normalizationResult, 0, result, 0, normalizationResultSize);
                for (int i = 0; i < normalizationResult.length; ++i) {
                    normalizationResult[i] = '\u0000';
                }
                return result;
            }
            for (int i = 0; i < normalizationResult.length; ++i) {
                normalizationResult[i] = '\u0000';
            }
            normalizationResult = new char[normalizationResultSize];
        }
    }

    private Normalizer() {
    }
}

