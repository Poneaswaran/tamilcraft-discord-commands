/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MemberAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class MemberActionImpl
extends RestActionImpl<Void>
implements MemberAction {
    private final String accessToken;
    private final String userId;
    private final Guild guild;
    private String nick;
    private Set<Role> roles;
    private boolean mute;
    private boolean deaf;

    public MemberActionImpl(JDA api, Guild guild, String userId, String accessToken) {
        super(api, Route.Guilds.ADD_MEMBER.compile(guild.getId(), userId));
        this.accessToken = accessToken;
        this.userId = userId;
        this.guild = guild;
    }

    @Override
    @Nonnull
    public MemberAction setCheck(BooleanSupplier checks) {
        return (MemberAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public MemberAction timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (MemberAction)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public MemberAction deadline(long timestamp) {
        return (MemberAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    @Nonnull
    public String getUserId() {
        return this.userId;
    }

    @Override
    @Nullable
    public User getUser() {
        return this.getJDA().getUserById(this.userId);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MemberActionImpl setNickname(String nick) {
        if (nick != null) {
            if (Helpers.isBlank(nick)) {
                this.nick = null;
                return this;
            }
            Checks.notLonger(nick, 32, "Nickname");
        }
        this.nick = nick;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MemberActionImpl setRoles(Collection<Role> roles) {
        if (roles == null) {
            this.roles = null;
            return this;
        }
        HashSet<Role> newRoles = new HashSet<Role>(roles.size());
        for (Role role : roles) {
            this.checkAndAdd(newRoles, role);
        }
        this.roles = newRoles;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MemberActionImpl setRoles(Role ... roles) {
        if (roles == null) {
            this.roles = null;
            return this;
        }
        HashSet<Role> newRoles = new HashSet<Role>(roles.length);
        for (Role role : roles) {
            this.checkAndAdd(newRoles, role);
        }
        this.roles = newRoles;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MemberActionImpl setMute(boolean mute) {
        this.mute = mute;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MemberActionImpl setDeafen(boolean deaf) {
        this.deaf = deaf;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject obj = DataObject.empty();
        obj.put("access_token", this.accessToken);
        if (this.nick != null) {
            obj.put("nick", this.nick);
        }
        if (this.roles != null && !this.roles.isEmpty()) {
            obj.put("roles", this.roles.stream().map(ISnowflake::getId).collect(Collectors.toList()));
        }
        obj.put("mute", this.mute);
        obj.put("deaf", this.deaf);
        return this.getRequestBody(obj);
    }

    private void checkAndAdd(Set<Role> newRoles, Role role) {
        Checks.notNull(role, "Role");
        Checks.check(role.getGuild().equals(this.getGuild()), "Roles must all be from the same guild");
        newRoles.add(role);
    }
}

