/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.api.accounts;

import lombok.Generated;

public final class DiscordUser {
    private String effectiveName;
    private String avatarUrl;
    private long userId;
    private String asMention;
    private int roleColor;

    @Generated
    public void setEffectiveName(String effectiveName) {
        this.effectiveName = effectiveName;
    }

    @Generated
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Generated
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Generated
    public void setAsMention(String asMention) {
        this.asMention = asMention;
    }

    @Generated
    public void setRoleColor(int roleColor) {
        this.roleColor = roleColor;
    }

    @Generated
    public String getEffectiveName() {
        return this.effectiveName;
    }

    @Generated
    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    @Generated
    public long getUserId() {
        return this.userId;
    }

    @Generated
    public String getAsMention() {
        return this.asMention;
    }

    @Generated
    public int getRoleColor() {
        return this.roleColor;
    }

    @Generated
    private DiscordUser(String effectiveName, String avatarUrl, long userId, String asMention, int roleColor) {
        this.effectiveName = effectiveName;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
        this.asMention = asMention;
        this.roleColor = roleColor;
    }

    @Generated
    public static DiscordUser of(String effectiveName, String avatarUrl, long userId, String asMention, int roleColor) {
        return new DiscordUser(effectiveName, avatarUrl, userId, asMention, roleColor);
    }
}

