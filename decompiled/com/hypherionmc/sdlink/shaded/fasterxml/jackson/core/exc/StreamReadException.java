/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.exc;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonLocation;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.RequestPayload;

public abstract class StreamReadException
extends JsonProcessingException {
    static final long serialVersionUID = 2L;
    protected transient JsonParser _processor;
    protected RequestPayload _requestPayload;

    protected StreamReadException(String msg) {
        this(null, msg, null, null);
    }

    protected StreamReadException(JsonParser p, String msg) {
        this(p, msg, StreamReadException._currentLocation(p), null);
    }

    protected StreamReadException(JsonParser p, String msg, Throwable rootCause) {
        this(p, msg, StreamReadException._currentLocation(p), rootCause);
    }

    protected StreamReadException(JsonParser p, String msg, JsonLocation loc) {
        this(p, msg, loc, null);
    }

    protected StreamReadException(String msg, JsonLocation loc, Throwable rootCause) {
        this(null, msg, loc, rootCause);
    }

    protected StreamReadException(JsonParser p, String msg, JsonLocation loc, Throwable rootCause) {
        super(msg, loc, rootCause);
        this._processor = p;
    }

    public abstract StreamReadException withParser(JsonParser var1);

    public abstract StreamReadException withRequestPayload(RequestPayload var1);

    @Override
    public JsonParser getProcessor() {
        return this._processor;
    }

    public RequestPayload getRequestPayload() {
        return this._requestPayload;
    }

    public String getRequestPayloadAsString() {
        return this._requestPayload != null ? this._requestPayload.toString() : null;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if (this._requestPayload != null) {
            msg = msg + "\nRequest payload : " + this._requestPayload.toString();
        }
        return msg;
    }

    protected static JsonLocation _currentLocation(JsonParser p) {
        return p == null ? null : p.currentLocation();
    }
}

