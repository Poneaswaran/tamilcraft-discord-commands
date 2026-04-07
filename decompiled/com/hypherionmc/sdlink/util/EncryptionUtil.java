/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.RandomStringUtils
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.shaded.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import com.hypherionmc.sdlink.shaded.jasypt.exceptions.EncryptionOperationNotPossibleException;
import com.hypherionmc.sdlink.util.SDLinkUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

public final class EncryptionUtil {
    private final boolean canRun;
    public static EncryptionUtil INSTANCE = EncryptionUtil.getInstance();
    private final StandardPBEStringEncryptor encryptor;

    public EncryptionUtil(String encCode) {
        this.canRun = !encCode.isEmpty();
        this.encryptor = new StandardPBEStringEncryptor();
        if (this.canRun) {
            this.encryptor.setPassword(encCode);
        }
        if (!this.canRun) {
            BotController.INSTANCE.getLogger().error("Failed to initialize encryption system. Your config values will not be encrypted!");
        }
    }

    private static EncryptionUtil getInstance() {
        if (INSTANCE == null) {
            String encCode = "";
            File storageDir = new File("sdlinkstorage");
            if (storageDir.exists()) {
                storageDir.mkdirs();
            }
            try {
                File encKey = new File(storageDir.getAbsolutePath() + File.separator + "sdlink.enc");
                if (!encKey.exists()) {
                    FileUtils.writeStringToFile((File)encKey, (String)EncryptionUtil.getSaltString(), (Charset)StandardCharsets.UTF_8);
                }
                encCode = FileUtils.readFileToString((File)encKey, (Charset)StandardCharsets.UTF_8);
            }
            catch (Exception e) {
                BotController.INSTANCE.getLogger().error("Failed to initialize Encryption", (Throwable)e);
            }
            INSTANCE = new EncryptionUtil(encCode);
        }
        return INSTANCE;
    }

    public String encrypt(String input) {
        if (!this.canRun) {
            return input;
        }
        if (this.isEncrypted((String)input)) {
            return input;
        }
        input = "enc:" + (String)input;
        return this.encryptor.encrypt((String)input);
    }

    public String decrypt(String input) {
        if (!this.canRun) {
            return input;
        }
        if (!this.isEncrypted(input)) {
            return input;
        }
        if ((input = this.internalDecrypt(input)).startsWith("enc:")) {
            input = input.replaceFirst("enc:", "");
        }
        return input;
    }

    private String internalDecrypt(String input) {
        if (!this.canRun) {
            return input;
        }
        return this.encryptor.decrypt(input);
    }

    private boolean isEncrypted(String input) {
        try {
            String temp = this.internalDecrypt(input);
            return temp.startsWith("enc:");
        }
        catch (EncryptionOperationNotPossibleException encryptionOperationNotPossibleException) {
            return false;
        }
    }

    public static String getSaltString() {
        return RandomStringUtils.random((int)SDLinkUtils.intInRange(30, 100), (boolean)true, (boolean)true);
    }
}

