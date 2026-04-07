/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.RoleDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.RoleImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.RichCustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class GuildRoleDeleteHandler
extends SocketHandler {
    public GuildRoleDeleteHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        if (guild == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("GUILD_ROLE_DELETE was received for a Guild that is not yet cached: {}", (Object)content);
            return null;
        }
        long roleId = content.getLong("role_id");
        RoleImpl removedRole = (RoleImpl)guild.getRolesView().get(roleId);
        if (removedRole == null) {
            WebSocketClient.LOG.debug("GUILD_ROLE_DELETE was received for a Role that is not yet cached: {}", (Object)content);
            return null;
        }
        removedRole.freezePosition();
        guild.getRolesView().remove(roleId);
        guild.getMembersView().forEach(m -> {
            MemberImpl member = (MemberImpl)m;
            member.getRoleSet().remove(removedRole);
        });
        for (RichCustomEmoji emoji : guild.getEmojiCache()) {
            RichCustomEmojiImpl impl = (RichCustomEmojiImpl)emoji;
            impl.getRoleSet().remove(removedRole);
        }
        this.getJDA().handleEvent(new RoleDeleteEvent(this.getJDA(), this.responseNumber, removedRole));
        this.getJDA().getEventCache().clear(EventCache.Type.ROLE, roleId);
        return null;
    }
}

