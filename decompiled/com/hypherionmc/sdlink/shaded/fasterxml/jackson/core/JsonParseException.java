/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonLocation;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.exc.StreamReadException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.RequestPayload;

public class JsonParseException
extends StreamReadException {
    private static final long serialVersionUID = 2L;

    public JsonParseException(String msg) {
        this(null, msg, null, null);
    }

    public JsonParseException(JsonParser p, String msg) {
        this(p, msg, JsonParseException._currentLocation(p), null);
    }

    public JsonParseException(JsonParser p, String msg, Throwable rootCause) {
        this(p, msg, JsonParseException._currentLocation(p), rootCause);
    }

    public JsonParseException(JsonParser p, String msg, JsonLocation loc) {
        this(p, msg, loc, null);
    }

    public JsonParseException(JsonParser p, String msg, JsonLocation loc, Throwable rootCause) {
        super(p, msg, loc, rootCause);
    }

    @Deprecated
    public JsonParseException(String msg, JsonLocation loc) {
        this(null, msg, loc, null);
    }

    @Deprecated
    public JsonParseException(String msg, JsonLocation loc, Throwable rootCause) {
        this(null, msg, loc, rootCause);
    }

    @Override
    public JsonParseException withParser(JsonParser p) {
        this._processor = p;
        return this;
    }

    @Override
    public JsonParseException withRequestPayload(RequestPayload payload) {
        this._requestPayload = payload;
        return this;
    }

    @Override
    public JsonParser getProcessor() {
        return super.getProcessor();
    }

    @Override
    public RequestPayload getRequestPayload() {
        return super.getRequestPayload();
    }

    @Override
    public String getRequestPayloadAsString() {
        return super.getRequestPayloadAsString();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

