/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send;

import com.hypherionmc.sdlink.shaded.json.JSONArray;
import com.hypherionmc.sdlink.shaded.json.JSONObject;
import com.hypherionmc.sdlink.shaded.json.JSONString;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class AllowedMentions
implements JSONString {
    private boolean parseRoles;
    private boolean parseUsers;
    private boolean parseEveryone;
    private final Set<String> users = new HashSet<String>();
    private final Set<String> roles = new HashSet<String>();

    public static AllowedMentions all() {
        return new AllowedMentions().withParseEveryone(true).withParseRoles(true).withParseUsers(true);
    }

    public static AllowedMentions none() {
        return new AllowedMentions().withParseEveryone(false).withParseRoles(false).withParseUsers(false);
    }

    @NotNull
    public AllowedMentions withUsers(String ... userId) {
        Collections.addAll(this.users, userId);
        this.parseUsers = false;
        return this;
    }

    @NotNull
    public AllowedMentions withRoles(String ... roleId) {
        Collections.addAll(this.roles, roleId);
        this.parseRoles = false;
        return this;
    }

    @NotNull
    public AllowedMentions withUsers(@NotNull Collection<String> userId) {
        this.users.addAll(userId);
        this.parseUsers = false;
        return this;
    }

    @NotNull
    public AllowedMentions withRoles(@NotNull Collection<String> roleId) {
        this.roles.addAll(roleId);
        this.parseRoles = false;
        return this;
    }

    @NotNull
    public AllowedMentions withParseEveryone(boolean allowEveryoneMention) {
        this.parseEveryone = allowEveryoneMention;
        return this;
    }

    @NotNull
    public AllowedMentions withParseUsers(boolean allowParseUsers) {
        this.parseUsers = allowParseUsers;
        if (this.parseUsers) {
            this.users.clear();
        }
        return this;
    }

    @NotNull
    public AllowedMentions withParseRoles(boolean allowParseRoles) {
        this.parseRoles = allowParseRoles;
        if (this.parseRoles) {
            this.roles.clear();
        }
        return this;
    }

    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("parse", new JSONArray());
        if (!this.users.isEmpty()) {
            json.put("users", this.users);
        } else if (this.parseUsers) {
            json.accumulate("parse", "users");
        }
        if (!this.roles.isEmpty()) {
            json.put("roles", this.roles);
        } else if (this.parseRoles) {
            json.accumulate("parse", "roles");
        }
        if (this.parseEveryone) {
            json.accumulate("parse", "everyone");
        }
        return json.toString();
    }
}

