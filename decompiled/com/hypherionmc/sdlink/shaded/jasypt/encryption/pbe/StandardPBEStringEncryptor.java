/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe;

import com.hypherionmc.sdlink.shaded.jasypt.commons.CommonUtils;
import com.hypherionmc.sdlink.shaded.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe.config.PBEConfig;
import com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe.config.StringPBEConfig;
import com.hypherionmc.sdlink.shaded.jasypt.exceptions.AlreadyInitializedException;
import com.hypherionmc.sdlink.shaded.jasypt.exceptions.EncryptionInitializationException;
import com.hypherionmc.sdlink.shaded.jasypt.exceptions.EncryptionOperationNotPossibleException;
import com.hypherionmc.sdlink.shaded.jasypt.iv.IvGenerator;
import com.hypherionmc.sdlink.shaded.jasypt.salt.SaltGenerator;
import java.security.Provider;

public final class StandardPBEStringEncryptor
implements PBEStringCleanablePasswordEncryptor {
    private static final String MESSAGE_CHARSET = "UTF-8";
    private static final String ENCRYPTED_MESSAGE_CHARSET = "US-ASCII";
    public static final String DEFAULT_STRING_OUTPUT_TYPE = "base64";
    private StringPBEConfig stringPBEConfig = null;
    private String stringOutputType = "base64";
    private boolean stringOutputTypeBase64 = true;
    private boolean stringOutputTypeSet = false;
    private final StandardPBEByteEncryptor byteEncryptor;
    private final Base64 base64;

    public StandardPBEStringEncryptor() {
        this.byteEncryptor = new StandardPBEByteEncryptor();
        this.base64 = new Base64();
    }

    private StandardPBEStringEncryptor(StandardPBEByteEncryptor standardPBEByteEncryptor) {
        this.byteEncryptor = standardPBEByteEncryptor;
        this.base64 = new Base64();
    }

    public synchronized void setConfig(PBEConfig config) {
        this.byteEncryptor.setConfig(config);
        if (config != null && config instanceof StringPBEConfig) {
            this.stringPBEConfig = (StringPBEConfig)config;
        }
    }

    public void setAlgorithm(String algorithm) {
        this.byteEncryptor.setAlgorithm(algorithm);
    }

    @Override
    public void setPassword(String password) {
        this.byteEncryptor.setPassword(password);
    }

    @Override
    public void setPasswordCharArray(char[] password) {
        this.byteEncryptor.setPasswordCharArray(password);
    }

    public void setKeyObtentionIterations(int keyObtentionIterations) {
        this.byteEncryptor.setKeyObtentionIterations(keyObtentionIterations);
    }

    public void setSaltGenerator(SaltGenerator saltGenerator) {
        this.byteEncryptor.setSaltGenerator(saltGenerator);
    }

    public void setIvGenerator(IvGenerator ivGenerator) {
        this.byteEncryptor.setIvGenerator(ivGenerator);
    }

    public void setProviderName(String providerName) {
        this.byteEncryptor.setProviderName(providerName);
    }

    public void setProvider(Provider provider) {
        this.byteEncryptor.setProvider(provider);
    }

    public synchronized void setStringOutputType(String stringOutputType) {
        CommonUtils.validateNotEmpty(stringOutputType, "String output type cannot be set empty");
        if (this.isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.stringOutputType = CommonUtils.getStandardStringOutputType(stringOutputType);
        this.stringOutputTypeSet = true;
    }

    synchronized StandardPBEStringEncryptor[] cloneAndInitializeEncryptor(int size) {
        StandardPBEByteEncryptor[] byteEncryptorClones = this.byteEncryptor.cloneAndInitializeEncryptor(size);
        this.initializeSpecifics();
        StandardPBEStringEncryptor[] clones = new StandardPBEStringEncryptor[size];
        clones[0] = this;
        for (int i = 1; i < size; ++i) {
            clones[i] = new StandardPBEStringEncryptor(byteEncryptorClones[i]);
            if (!CommonUtils.isNotEmpty(this.stringOutputType)) continue;
            clones[i].setStringOutputType(this.stringOutputType);
        }
        return clones;
    }

    public boolean isInitialized() {
        return this.byteEncryptor.isInitialized();
    }

    public synchronized void initialize() {
        if (!this.isInitialized()) {
            this.initializeSpecifics();
            this.byteEncryptor.initialize();
        }
    }

    private void initializeSpecifics() {
        if (this.stringPBEConfig != null) {
            String configStringOutputType = this.stringPBEConfig.getStringOutputType();
            this.stringOutputType = this.stringOutputTypeSet || configStringOutputType == null ? this.stringOutputType : configStringOutputType;
        }
        this.stringOutputTypeBase64 = DEFAULT_STRING_OUTPUT_TYPE.equalsIgnoreCase(this.stringOutputType);
    }

    @Override
    public String encrypt(String message) {
        if (message == null) {
            return null;
        }
        if (!this.isInitialized()) {
            this.initialize();
        }
        try {
            byte[] messageBytes = message.getBytes(MESSAGE_CHARSET);
            byte[] encryptedMessage = this.byteEncryptor.encrypt(messageBytes);
            String result = null;
            if (this.stringOutputTypeBase64) {
                encryptedMessage = this.base64.encode(encryptedMessage);
                result = new String(encryptedMessage, ENCRYPTED_MESSAGE_CHARSET);
            } else {
                result = CommonUtils.toHexadecimal(encryptedMessage);
            }
            return result;
        }
        catch (EncryptionInitializationException e) {
            throw e;
        }
        catch (EncryptionOperationNotPossibleException e) {
            throw e;
        }
        catch (Exception e) {
            throw new EncryptionOperationNotPossibleException();
        }
    }

    @Override
    public String decrypt(String encryptedMessage) {
        if (encryptedMessage == null) {
            return null;
        }
        if (!this.isInitialized()) {
            this.initialize();
        }
        try {
            byte[] encryptedMessageBytes = null;
            if (this.stringOutputTypeBase64) {
                encryptedMessageBytes = encryptedMessage.getBytes(ENCRYPTED_MESSAGE_CHARSET);
                encryptedMessageBytes = this.base64.decode(encryptedMessageBytes);
            } else {
                encryptedMessageBytes = CommonUtils.fromHexadecimal(encryptedMessage);
            }
            byte[] message = this.byteEncryptor.decrypt(encryptedMessageBytes);
            return new String(message, MESSAGE_CHARSET);
        }
        catch (EncryptionInitializationException e) {
            throw e;
        }
        catch (EncryptionOperationNotPossibleException e) {
            throw e;
        }
        catch (Exception e) {
            throw new EncryptionOperationNotPossibleException();
        }
    }
}

