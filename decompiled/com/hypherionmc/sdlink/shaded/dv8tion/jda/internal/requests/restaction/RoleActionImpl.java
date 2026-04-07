/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.RoleAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class RoleActionImpl
extends AuditableRestActionImpl<Role>
implements RoleAction {
    protected final Guild guild;
    protected Long permissions;
    protected String name = null;
    protected Integer color = null;
    protected Boolean hoisted = null;
    protected Boolean mentionable = null;
    protected Icon icon = null;
    protected String emoji = null;

    public RoleActionImpl(Guild guild) {
        super(guild.getJDA(), Route.Roles.CREATE_ROLE.compile(guild.getId()));
        this.guild = guild;
    }

    @Override
    @Nonnull
    public RoleActionImpl setCheck(BooleanSupplier checks) {
        return (RoleActionImpl)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public RoleActionImpl timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (RoleActionImpl)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public RoleActionImpl deadline(long timestamp) {
        return (RoleActionImpl)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setName(String name) {
        if (name != null) {
            Checks.notEmpty(name, "Name");
            Checks.notLonger(name, 100, "Name");
        }
        this.name = name;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setHoisted(Boolean hoisted) {
        this.hoisted = hoisted;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setMentionable(Boolean mentionable) {
        this.mentionable = mentionable;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setColor(Integer rgb) {
        this.color = rgb;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setPermissions(Long permissions) {
        if (permissions != null) {
            for (Permission p : Permission.getPermissions(permissions)) {
                this.checkPermission(p);
            }
        }
        this.permissions = permissions;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setIcon(Icon icon) {
        this.icon = icon;
        this.emoji = null;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleActionImpl setIcon(String emoji) {
        this.emoji = emoji;
        this.icon = null;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        if (this.name != null) {
            object.put("name", this.name);
        }
        if (this.color != null) {
            object.put("color", this.color & 0xFFFFFF);
        }
        if (this.permissions != null) {
            object.put("permissions", this.permissions);
        }
        if (this.hoisted != null) {
            object.put("hoist", this.hoisted);
        }
        if (this.mentionable != null) {
            object.put("mentionable", this.mentionable);
        }
        if (this.icon != null) {
            object.put("icon", this.icon.getEncoding());
        }
        if (this.emoji != null) {
            object.put("unicode_emoji", this.emoji);
        }
        return this.getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<Role> request) {
        request.onSuccess(this.api.getEntityBuilder().createRole((GuildImpl)this.guild, response.getObject(), this.guild.getIdLong()));
    }

    private void checkPermission(Permission permission) {
        if (!this.guild.getSelfMember().hasPermission(permission)) {
            throw new InsufficientPermissionException(this.guild, permission);
        }
    }
}

