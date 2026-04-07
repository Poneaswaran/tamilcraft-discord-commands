/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jasypt.contrib.org.apache.commons.codec_1_3;

import com.hypherionmc.sdlink.shaded.jasypt.contrib.org.apache.commons.codec_1_3.Encoder;
import com.hypherionmc.sdlink.shaded.jasypt.contrib.org.apache.commons.codec_1_3.EncoderException;

public interface BinaryEncoder
extends Encoder {
    public byte[] encode(byte[] var1) throws EncoderException;
}

