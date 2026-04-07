/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import java.util.LinkedList;
import java.util.List;

public class GuildMemberUpdateHandler
extends SocketHandler {
    public GuildMemberUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long id = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(id)) {
            return id;
        }
        DataObject userJson = content.getObject("user");
        long userId = userJson.getLong("id");
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(id);
        if (guild == null) {
            EventCache.LOG.debug("Got GuildMember update but JDA currently does not have the Guild cached. Ignoring. {}", (Object)content);
            return null;
        }
        MemberImpl member = (MemberImpl)guild.getMembersView().get(userId);
        if (member == null) {
            member = this.getJDA().getEntityBuilder().createMember(guild, content);
        } else {
            List<Role> newRoles = this.toRolesList(guild, content.getArray("roles"));
            this.getJDA().getEntityBuilder().updateMember(guild, member, content, newRoles);
        }
        this.getJDA().getEntityBuilder().updateMemberCache(member);
        this.getJDA().handleEvent(new GuildMemberUpdateEvent((JDA)this.getJDA(), this.responseNumber, member));
        return null;
    }

    private List<Role> toRolesList(GuildImpl guild, DataArray array) {
        LinkedList<Role> roles = new LinkedList<Role>();
        for (int i = 0; i < array.length(); ++i) {
            long id = array.getLong(i);
            Role r = (Role)guild.getRolesView().get(id);
            if (r == null) {
                this.getJDA().getEventCache().cache(EventCache.Type.ROLE, id, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Got GuildMember update but one of the Roles for the Member is not yet cached.");
                return null;
            }
            roles.add(r);
        }
        return roles;
    }
}

