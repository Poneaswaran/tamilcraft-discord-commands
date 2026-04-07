/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.PermissionOverrideImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.PermissionUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class PermissionOverrideActionImpl
extends AuditableRestActionImpl<PermissionOverride>
implements PermissionOverrideAction {
    private boolean isOverride = true;
    private boolean allowSet = false;
    private boolean denySet = false;
    private long allow = 0L;
    private long deny = 0L;
    private final IPermissionContainerMixin<?> channel;
    private final IPermissionHolder permissionHolder;
    private final boolean isRole;
    private final long id;

    public PermissionOverrideActionImpl(PermissionOverride override) {
        super(override.getJDA(), Route.Channels.MODIFY_PERM_OVERRIDE.compile(override.getChannel().getId(), override.getId()));
        this.channel = (IPermissionContainerMixin)override.getChannel();
        this.permissionHolder = override.getPermissionHolder();
        this.isRole = override.isRoleOverride();
        this.id = override.getIdLong();
    }

    public PermissionOverrideActionImpl(JDA api, GuildChannel channel, IPermissionHolder permissionHolder) {
        super(api, Route.Channels.CREATE_PERM_OVERRIDE.compile(channel.getId(), permissionHolder.getId()));
        this.channel = (IPermissionContainerMixin)channel;
        this.permissionHolder = permissionHolder;
        this.isRole = permissionHolder instanceof Role;
        this.id = permissionHolder.getIdLong();
    }

    public PermissionOverrideActionImpl setOverride(boolean override) {
        this.isOverride = override;
        return this;
    }

    @Override
    protected BooleanSupplier finalizeChecks() {
        return () -> {
            Member selfMember = this.getGuild().getSelfMember();
            Checks.checkAccess(selfMember, this.channel);
            if (!selfMember.hasPermission(this.channel, Permission.MANAGE_PERMISSIONS)) {
                throw new InsufficientPermissionException(this.channel, Permission.MANAGE_PERMISSIONS);
            }
            return true;
        };
    }

    @Override
    @Nonnull
    public PermissionOverrideActionImpl setCheck(BooleanSupplier checks) {
        return (PermissionOverrideActionImpl)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public PermissionOverrideActionImpl timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (PermissionOverrideActionImpl)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public PermissionOverrideActionImpl deadline(long timestamp) {
        return (PermissionOverrideActionImpl)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public PermissionOverrideAction resetAllow() {
        this.allow = this.getOriginalAllow();
        this.allowSet = false;
        return this;
    }

    @Override
    @Nonnull
    public PermissionOverrideAction resetDeny() {
        this.deny = this.getOriginalDeny();
        this.denySet = false;
        return this;
    }

    @Override
    @Nonnull
    public IPermissionContainer getChannel() {
        return this.channel;
    }

    @Override
    public Role getRole() {
        return this.isRole() ? (Role)this.permissionHolder : null;
    }

    @Override
    public Member getMember() {
        return this.isMember() ? (Member)this.permissionHolder : null;
    }

    @Override
    public long getAllowed() {
        return this.getCurrentAllow();
    }

    @Override
    public long getDenied() {
        return this.getCurrentDeny();
    }

    @Override
    public long getInherited() {
        return (this.getAllowed() ^ 0xFFFFFFFFFFFFFFFFL) & (this.getDenied() ^ 0xFFFFFFFFFFFFFFFFL);
    }

    @Override
    public boolean isMember() {
        return !this.isRole;
    }

    @Override
    public boolean isRole() {
        return this.isRole;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public PermissionOverrideActionImpl setAllowed(long allowBits) {
        this.checkPermissions(this.getOriginalAllow() ^ allowBits);
        this.allow = allowBits;
        this.deny = this.getCurrentDeny() & (allowBits ^ 0xFFFFFFFFFFFFFFFFL);
        this.denySet = true;
        this.allowSet = true;
        return this;
    }

    @Override
    @Nonnull
    public PermissionOverrideAction grant(long allowBits) {
        return this.setAllowed(this.getCurrentAllow() | allowBits);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public PermissionOverrideActionImpl setDenied(long denyBits) {
        this.checkPermissions(this.getOriginalDeny() ^ denyBits);
        this.deny = denyBits;
        this.allow = this.getCurrentAllow() & (denyBits ^ 0xFFFFFFFFFFFFFFFFL);
        this.denySet = true;
        this.allowSet = true;
        return this;
    }

    @Override
    @Nonnull
    public PermissionOverrideAction deny(long denyBits) {
        return this.setDenied(this.getCurrentDeny() | denyBits);
    }

    @Override
    @Nonnull
    public PermissionOverrideAction clear(long inheritedBits) {
        return this.setAllowed(this.getCurrentAllow() & (inheritedBits ^ 0xFFFFFFFFFFFFFFFFL)).setDenied(this.getCurrentDeny() & (inheritedBits ^ 0xFFFFFFFFFFFFFFFFL));
    }

    protected void checkPermissions(long changed) {
        long botPerms;
        EnumSet<Permission> missing;
        long channelPermissions;
        Member selfMember = this.getGuild().getSelfMember();
        if (changed != 0L && !selfMember.hasPermission(Permission.ADMINISTRATOR) && ((channelPermissions = PermissionUtil.getExplicitPermission(this.channel, selfMember, false)) & Permission.MANAGE_PERMISSIONS.getRawValue()) == 0L && !(missing = Permission.getPermissions(changed & ((botPerms = PermissionUtil.getEffectivePermission(this.channel, selfMember)) ^ 0xFFFFFFFFFFFFFFFFL))).isEmpty()) {
            throw new InsufficientPermissionException(this.channel, Permission.MANAGE_PERMISSIONS, "You must have Permission.MANAGE_PERMISSIONS on the channel explicitly in order to set permissions you don't already have!");
        }
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public PermissionOverrideActionImpl setPermissions(long allowBits, long denyBits) {
        return this.setAllowed(allowBits).setDenied(denyBits);
    }

    private long getCurrentAllow() {
        if (this.allowSet) {
            return this.allow;
        }
        return this.isOverride ? 0L : this.getOriginalAllow();
    }

    private long getCurrentDeny() {
        if (this.denySet) {
            return this.deny;
        }
        return this.isOverride ? 0L : this.getOriginalDeny();
    }

    private long getOriginalDeny() {
        PermissionOverride override = this.channel.getPermissionOverrideMap().get(this.id);
        return override == null ? 0L : override.getDeniedRaw();
    }

    private long getOriginalAllow() {
        PermissionOverride override = this.channel.getPermissionOverrideMap().get(this.id);
        return override == null ? 0L : override.getAllowedRaw();
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        object.put("type", this.isRole() ? 0 : 1);
        object.put("allow", this.getCurrentAllow());
        object.put("deny", this.getCurrentDeny());
        this.reset();
        return this.getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<PermissionOverride> request) {
        DataObject object = (DataObject)request.getRawBody();
        PermissionOverrideImpl override = new PermissionOverrideImpl(this.channel, this.id, this.isRole());
        override.setAllow(object.getLong("allow"));
        override.setDeny(object.getLong("deny"));
        request.onSuccess(override);
    }
}

