/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.api.messaging;

import com.hypherionmc.sdlink.util.translations.SDText;
import lombok.Generated;

public final class Result {
    private final Type type;
    private final String message;

    private Result(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public static Result success(SDText text) {
        return Result.success(text.toString());
    }

    public static Result success(String message) {
        return new Result(Type.SUCCESS, message);
    }

    public static Result error(SDText text) {
        return Result.error(text.toString());
    }

    public static Result error(String message) {
        return new Result(Type.ERROR, message);
    }

    public boolean isError() {
        return this.type == Type.ERROR;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    static enum Type {
        ERROR,
        SUCCESS;

    }
}

