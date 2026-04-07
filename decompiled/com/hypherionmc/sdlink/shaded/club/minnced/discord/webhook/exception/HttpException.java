/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.exception;

import com.hypherionmc.sdlink.shaded.okhttp3.Headers;
import org.jetbrains.annotations.NotNull;

public class HttpException
extends RuntimeException {
    private final int code;
    private final String body;
    private final Headers headers;

    public HttpException(int code, @NotNull String body, @NotNull Headers headers) {
        super("Request returned failure " + code + ": " + body);
        this.body = body;
        this.code = code;
        this.headers = headers;
    }

    public int getCode() {
        return this.code;
    }

    @NotNull
    public String getBody() {
        return this.body;
    }

    @NotNull
    public Headers getHeaders() {
        return this.headers;
    }
}

