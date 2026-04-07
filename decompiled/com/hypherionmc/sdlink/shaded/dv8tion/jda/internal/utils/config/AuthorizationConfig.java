/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public final class AuthorizationConfig {
    private String token;

    public AuthorizationConfig(@Nonnull String token) {
        Checks.notEmpty(token, "Token");
        Checks.noWhitespace(token, "Token");
        this.setToken(token);
    }

    @Nonnull
    public String getToken() {
        return this.token;
    }

    public void setToken(@Nonnull String token) {
        this.token = "Bot " + token;
    }
}

