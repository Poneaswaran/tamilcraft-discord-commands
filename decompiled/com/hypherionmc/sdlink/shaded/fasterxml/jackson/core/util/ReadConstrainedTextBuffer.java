/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.StreamReadConstraints;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.exc.StreamConstraintsException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.BufferRecycler;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.TextBuffer;

public final class ReadConstrainedTextBuffer
extends TextBuffer {
    private final StreamReadConstraints _streamReadConstraints;

    public ReadConstrainedTextBuffer(StreamReadConstraints streamReadConstraints, BufferRecycler bufferRecycler) {
        super(bufferRecycler);
        this._streamReadConstraints = streamReadConstraints;
    }

    @Override
    protected void validateStringLength(int length) throws StreamConstraintsException {
        this._streamReadConstraints.validateStringLength(length);
    }
}

