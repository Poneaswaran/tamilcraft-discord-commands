/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.CryptoAdapter;
import java.net.DatagramPacket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class AudioPacket {
    public static final int RTP_HEADER_BYTE_LENGTH = 12;
    public static final byte RTP_VERSION_PAD_EXTEND = -128;
    public static final byte RTP_PAYLOAD_TYPE = 120;
    private final byte type;
    private final char seq;
    private final int timestamp;
    private final int ssrc;
    private final int extension;
    private final boolean hasExtension;
    private final int[] csrc;
    private final int headerLength;
    private final byte[] rawPacket;
    private final ByteBuffer encodedAudio;

    public AudioPacket(DatagramPacket packet) {
        this(Arrays.copyOf(packet.getData(), packet.getLength()));
    }

    public AudioPacket(byte[] rawPacket) {
        this.rawPacket = rawPacket;
        ByteBuffer buffer = ByteBuffer.wrap(rawPacket);
        byte first = buffer.get();
        this.hasExtension = (first & 0x10) != 0;
        int cc = first & 0xF;
        this.type = buffer.get();
        this.seq = buffer.getChar();
        this.timestamp = buffer.getInt();
        this.ssrc = buffer.getInt();
        this.csrc = new int[cc];
        for (int i = 0; i < cc; ++i) {
            this.csrc[i] = buffer.getInt();
        }
        if (this.hasExtension) {
            buffer.position(buffer.position() + 2);
            this.extension = buffer.getShort();
        } else {
            this.extension = 0;
        }
        this.headerLength = buffer.position();
        this.encodedAudio = buffer;
    }

    public AudioPacket(ByteBuffer buffer, char seq, int timestamp, int ssrc, ByteBuffer encodedAudio) {
        this.seq = seq;
        this.ssrc = ssrc;
        this.timestamp = timestamp;
        this.csrc = new int[0];
        this.extension = 0;
        this.hasExtension = false;
        this.headerLength = 12;
        this.type = (byte)120;
        this.rawPacket = AudioPacket.generateRawPacket(buffer, seq, timestamp, ssrc, encodedAudio);
        this.encodedAudio = encodedAudio;
    }

    public byte[] getHeader() {
        return Arrays.copyOf(this.rawPacket, this.headerLength);
    }

    public ByteBuffer getEncodedAudio() {
        return this.encodedAudio;
    }

    public char getSequence() {
        return this.seq;
    }

    public int getSSRC() {
        return this.ssrc;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public ByteBuffer asEncryptedPacket(CryptoAdapter crypto, ByteBuffer buffer) {
        ((Buffer)buffer).clear();
        AudioPacket.writeHeader(this.seq, this.timestamp, this.ssrc, buffer);
        buffer = crypto.encrypt(buffer, this.encodedAudio);
        ((Buffer)buffer).flip();
        return buffer;
    }

    public static AudioPacket decryptAudioPacket(CryptoAdapter crypto, DatagramPacket packet) {
        AudioPacket encryptedPacket = new AudioPacket(packet);
        if (encryptedPacket.type != 120) {
            return null;
        }
        byte[] decryptedPayload = crypto.decrypt(encryptedPacket.encodedAudio);
        int offset = 4 * encryptedPacket.extension;
        return new AudioPacket(null, encryptedPacket.seq, encryptedPacket.timestamp, encryptedPacket.ssrc, ByteBuffer.wrap(decryptedPayload, offset, decryptedPayload.length - offset).slice());
    }

    private static byte[] generateRawPacket(ByteBuffer buffer, char seq, int timestamp, int ssrc, ByteBuffer data) {
        if (buffer == null) {
            buffer = ByteBuffer.allocate(12 + data.remaining());
        }
        AudioPacket.populateBuffer(seq, timestamp, ssrc, data, buffer);
        return buffer.array();
    }

    private static void writeHeader(char seq, int timestamp, int ssrc, ByteBuffer buffer) {
        buffer.put((byte)-128);
        buffer.put((byte)120);
        buffer.putChar(seq);
        buffer.putInt(timestamp);
        buffer.putInt(ssrc);
    }

    private static void populateBuffer(char seq, int timestamp, int ssrc, ByteBuffer data, ByteBuffer buffer) {
        AudioPacket.writeHeader(seq, timestamp, ssrc, buffer);
        buffer.put(data);
        ((Buffer)data).flip();
    }
}

