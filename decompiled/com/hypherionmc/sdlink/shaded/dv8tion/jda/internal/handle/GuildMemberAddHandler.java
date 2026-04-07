/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class GuildMemberAddHandler
extends SocketHandler {
    public GuildMemberAddHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long id = content.getLong("guild_id");
        boolean setup = this.getJDA().getGuildSetupController().onAddMember(id, content);
        if (setup) {
            return null;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(id);
        if (guild == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, id, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Caching member for guild that is not yet cached. Guild ID: {} JSON: {}", (Object)id, (Object)content);
            return null;
        }
        guild.onMemberAdd();
        MemberImpl member = this.getJDA().getEntityBuilder().createMember(guild, content);
        this.getJDA().getEntityBuilder().updateMemberCache(member);
        this.getJDA().handleEvent(new GuildMemberJoinEvent((JDA)this.getJDA(), this.responseNumber, member));
        return null;
    }
}

