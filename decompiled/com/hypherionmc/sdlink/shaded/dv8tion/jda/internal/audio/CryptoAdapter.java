/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio;

import com.google.crypto.tink.aead.internal.InsecureNonceAesGcmJce;
import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20Poly1305;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.AudioEncryption;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.EnumSet;

public interface CryptoAdapter {
    public static final String AES_GCM_NO_PADDING = "AES_256/GCM/NOPADDING";

    public AudioEncryption getMode();

    public ByteBuffer encrypt(ByteBuffer var1, ByteBuffer var2);

    public byte[] decrypt(ByteBuffer var1);

    public static AudioEncryption negotiate(EnumSet<AudioEncryption> supportedModes) {
        for (AudioEncryption mode : AudioEncryption.values()) {
            if (!supportedModes.contains((Object)mode) || !CryptoAdapter.isModeSupported(mode)) continue;
            return mode;
        }
        return null;
    }

    public static boolean isModeSupported(AudioEncryption mode) {
        switch (mode) {
            case AEAD_AES256_GCM_RTPSIZE: {
                return Security.getAlgorithms("Cipher").contains(AES_GCM_NO_PADDING);
            }
            case AEAD_XCHACHA20_POLY1305_RTPSIZE: {
                return true;
            }
        }
        return false;
    }

    public static CryptoAdapter getAdapter(AudioEncryption mode, byte[] secretKey) {
        switch (mode) {
            case AEAD_AES256_GCM_RTPSIZE: {
                return new AES_GCM_Adapter(secretKey);
            }
            case AEAD_XCHACHA20_POLY1305_RTPSIZE: {
                return new XChaCha20Poly1305Adapter(secretKey);
            }
        }
        throw new IllegalStateException("Unsupported encryption mode: " + (Object)((Object)mode));
    }

    public static class AES_GCM_Adapter
    extends AbstractAaedAdapter
    implements CryptoAdapter {
        public AES_GCM_Adapter(byte[] secretKey) {
            super(secretKey, 16, 12);
        }

        @Override
        public AudioEncryption getMode() {
            return AudioEncryption.AEAD_AES256_GCM_RTPSIZE;
        }

        @Override
        protected void encryptInternally(ByteBuffer output, ByteBuffer audio, byte[] nonce) throws Exception {
            InsecureNonceAesGcmJce cipher = this.getCipher();
            byte[] input = this.getPlaintextCopy(audio);
            byte[] associatedData = this.getAssociatedData(output);
            output.put(cipher.encrypt(nonce, input, associatedData));
        }

        @Override
        public byte[] decryptInternally(byte[] cipherText, byte[] associatedData, byte[] nonce) throws Exception {
            InsecureNonceAesGcmJce cipher = this.getCipher();
            return cipher.decrypt(nonce, cipherText, associatedData);
        }

        private InsecureNonceAesGcmJce getCipher() throws GeneralSecurityException {
            return new InsecureNonceAesGcmJce(this.secretKey);
        }
    }

    public static class XChaCha20Poly1305Adapter
    extends AbstractAaedAdapter
    implements CryptoAdapter {
        public XChaCha20Poly1305Adapter(byte[] secretKey) {
            super(secretKey, 16, 24);
        }

        @Override
        public AudioEncryption getMode() {
            return AudioEncryption.AEAD_XCHACHA20_POLY1305_RTPSIZE;
        }

        @Override
        public void encryptInternally(ByteBuffer output, ByteBuffer audio, byte[] nonce) throws Exception {
            InsecureNonceXChaCha20Poly1305 cipher = this.getCipher();
            byte[] input = this.getPlaintextCopy(audio);
            byte[] associatedData = this.getAssociatedData(output);
            output.put(cipher.encrypt(nonce, input, associatedData));
        }

        @Override
        public byte[] decryptInternally(byte[] cipherText, byte[] associatedData, byte[] nonce) throws Exception {
            InsecureNonceXChaCha20Poly1305 cipher = this.getCipher();
            return cipher.decrypt(nonce, cipherText, associatedData);
        }

        private InsecureNonceXChaCha20Poly1305 getCipher() throws GeneralSecurityException {
            return new InsecureNonceXChaCha20Poly1305(this.secretKey);
        }
    }

    public static abstract class AbstractAaedAdapter
    implements CryptoAdapter {
        protected static final int nonceBytes = 4;
        protected static final SecureRandom random = new SecureRandom();
        protected final byte[] secretKey;
        protected final byte[] nonceBuffer;
        protected final int tagBytes;
        protected final int paddedNonceBytes;
        protected int encryptCounter;

        protected AbstractAaedAdapter(byte[] secretKey, int tagBytes, int paddedNonceBytes) {
            this.secretKey = secretKey;
            this.tagBytes = tagBytes;
            this.paddedNonceBytes = paddedNonceBytes;
            this.nonceBuffer = new byte[paddedNonceBytes];
            this.encryptCounter = Math.abs(random.nextInt()) % 513 + 1;
        }

        @Override
        public ByteBuffer encrypt(ByteBuffer output, ByteBuffer audio) {
            int minimumOutputSize = audio.remaining() + this.tagBytes + 4;
            if (output.remaining() < minimumOutputSize) {
                ByteBuffer newBuffer = ByteBuffer.allocate(output.capacity() + minimumOutputSize);
                output.flip();
                newBuffer.put(output);
                output = newBuffer;
            }
            IOUtil.setIntBigEndian(this.nonceBuffer, 0, this.encryptCounter);
            try {
                this.encryptInternally(output, audio, this.nonceBuffer);
                output.putInt(this.encryptCounter++);
                return output;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public byte[] decrypt(ByteBuffer packet) {
            try {
                int headerLength = packet.position();
                packet.position(0);
                byte[] associatedData = new byte[headerLength];
                packet.get(associatedData);
                byte[] cipherText = new byte[packet.remaining() - 4];
                packet.get(cipherText);
                byte[] nonce = new byte[this.paddedNonceBytes];
                packet.get(nonce, 0, 4);
                return this.decryptInternally(cipherText, associatedData, nonce);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        protected abstract void encryptInternally(ByteBuffer var1, ByteBuffer var2, byte[] var3) throws Exception;

        protected abstract byte[] decryptInternally(byte[] var1, byte[] var2, byte[] var3) throws Exception;

        protected byte[] getAssociatedData(ByteBuffer output) {
            return Arrays.copyOfRange(output.array(), output.arrayOffset(), output.arrayOffset() + output.position());
        }

        protected byte[] getPlaintextCopy(ByteBuffer audio) {
            return Arrays.copyOfRange(audio.array(), audio.arrayOffset() + audio.position(), audio.arrayOffset() + audio.limit());
        }
    }
}

