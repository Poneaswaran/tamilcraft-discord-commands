/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.database;

import com.hypherionmc.sdlink.core.jsondb.annotations.Document;
import com.hypherionmc.sdlink.core.jsondb.annotations.Id;
import lombok.Generated;

@Document(collection="verifiedaccounts")
public final class SDLinkAccount {
    @Id
    private String uuid;
    private String username;
    private String inGameName;
    private String discordID;
    private String verifyCode;
    private boolean isOffline;

    public String getInGameName() {
        return this.inGameName == null || this.inGameName.isEmpty() ? this.username : this.inGameName;
    }

    @Generated
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Generated
    public void setUsername(String username) {
        this.username = username;
    }

    @Generated
    public void setInGameName(String inGameName) {
        this.inGameName = inGameName;
    }

    @Generated
    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    @Generated
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Generated
    public void setOffline(boolean isOffline) {
        this.isOffline = isOffline;
    }

    @Generated
    public String getUuid() {
        return this.uuid;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getDiscordID() {
        return this.discordID;
    }

    @Generated
    public String getVerifyCode() {
        return this.verifyCode;
    }

    @Generated
    public boolean isOffline() {
        return this.isOffline;
    }
}

