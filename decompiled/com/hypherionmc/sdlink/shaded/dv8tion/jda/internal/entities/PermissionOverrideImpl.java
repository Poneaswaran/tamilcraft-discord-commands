/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IPermissionContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.PermissionOverrideActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.Objects;

public class PermissionOverrideImpl
implements PermissionOverride {
    private final long id;
    private final boolean isRole;
    private final JDAImpl api;
    private IPermissionContainer channel;
    private long allow;
    private long deny;

    public PermissionOverrideImpl(IPermissionContainer channel, long id, boolean isRole) {
        this.isRole = isRole;
        this.api = (JDAImpl)channel.getJDA();
        this.channel = channel;
        this.id = id;
    }

    @Override
    public long getAllowedRaw() {
        return this.allow;
    }

    @Override
    public long getInheritRaw() {
        return (this.allow | this.deny) ^ 0xFFFFFFFFFFFFFFFFL;
    }

    @Override
    public long getDeniedRaw() {
        return this.deny;
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getAllowed() {
        return Permission.getPermissions(this.allow);
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getInherit() {
        return Permission.getPermissions(this.getInheritRaw());
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getDenied() {
        return Permission.getPermissions(this.deny);
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    public IPermissionHolder getPermissionHolder() {
        return this.isRole ? this.getRole() : this.getMember();
    }

    @Override
    public Member getMember() {
        return this.getGuild().getMemberById(this.id);
    }

    @Override
    public Role getRole() {
        return this.getGuild().getRoleById(this.id);
    }

    @Override
    @Nonnull
    public IPermissionContainerUnion getChannel() {
        IPermissionContainer realChannel = this.api.getChannelById(IPermissionContainer.class, this.channel.getIdLong());
        if (realChannel != null) {
            this.channel = realChannel;
        }
        return (IPermissionContainerUnion)this.channel;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.getChannel().getGuild();
    }

    @Override
    public boolean isMemberOverride() {
        return !this.isRole;
    }

    @Override
    public boolean isRoleOverride() {
        return this.isRole;
    }

    @Override
    @Nonnull
    public PermissionOverrideAction getManager() {
        this.checkPermissions();
        return new PermissionOverrideActionImpl(this).setOverride(false);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        this.checkPermissions();
        Route.CompiledRoute route = Route.Channels.DELETE_PERM_OVERRIDE.compile(this.channel.getId(), this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    public PermissionOverrideImpl setAllow(long allow) {
        this.allow = allow;
        return this;
    }

    public PermissionOverrideImpl setDeny(long deny) {
        this.deny = deny;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PermissionOverrideImpl)) {
            return false;
        }
        PermissionOverrideImpl oPerm = (PermissionOverrideImpl)o;
        return this.id == oPerm.id && this.channel.getIdLong() == oPerm.channel.getIdLong();
    }

    public int hashCode() {
        return Objects.hash(this.id, this.channel.getIdLong());
    }

    public String toString() {
        return new EntityString(this).setType(this.isMemberOverride() ? "MEMBER" : "ROLE").addMetadata("channel", this.channel).toString();
    }

    private void checkPermissions() {
        Member selfMember = this.getGuild().getSelfMember();
        IPermissionContainerUnion channel = this.getChannel();
        Checks.checkAccess(selfMember, channel);
        if (!selfMember.hasPermission((GuildChannel)channel, Permission.MANAGE_PERMISSIONS)) {
            throw new InsufficientPermissionException(channel, Permission.MANAGE_PERMISSIONS);
        }
    }
}

