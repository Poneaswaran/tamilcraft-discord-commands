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

@Document(collection="hiddenplayers")
public final class HiddenPlayers {
    @Id
    private String identifier;
    private String displayName;
    private String type;

    @Generated
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Generated
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Generated
    public void setType(String type) {
        this.type = type;
    }

    @Generated
    public String getIdentifier() {
        return this.identifier;
    }

    @Generated
    public String getDisplayName() {
        return this.displayName;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    private HiddenPlayers(String identifier, String displayName, String type) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.type = type;
    }

    @Generated
    public static HiddenPlayers of(String identifier, String displayName, String type) {
        return new HiddenPlayers(identifier, displayName, type);
    }

    @Generated
    public HiddenPlayers() {
    }
}

