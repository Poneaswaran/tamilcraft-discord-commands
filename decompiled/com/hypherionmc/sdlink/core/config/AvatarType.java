/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.config;

import com.hypherionmc.sdlink.core.config.SDLinkConfig;

public enum AvatarType {
    AVATAR("https://skinatar.firstdark.dev/avatar/{uuid}"),
    HEAD("https://skinatar.firstdark.dev/isometric/{uuid}"),
    BODY("https://skinatar.firstdark.dev/body/{uuid}"),
    COMBO("https://skinatar.firstdark.dev/avatar/{uuid}"),
    CUSTOM("");

    private final String url;

    private AvatarType(String url) {
        this.url = url;
    }

    public String toString() {
        return this.url;
    }

    public String resolve(String uuid) {
        if (this == CUSTOM) {
            return SDLinkConfig.INSTANCE.chatConfig.customAvatarService.replace("{uuid}", uuid);
        }
        return this.url.replace("{uuid}", uuid);
    }
}

