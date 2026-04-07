/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildBanEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class GuildBanHandler
extends SocketHandler {
    private final boolean banned;

    public GuildBanHandler(JDAImpl api, boolean banned) {
        super(api);
        this.banned = banned;
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long id = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(id)) {
            return id;
        }
        DataObject userJson = content.getObject("user");
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(id);
        if (guild == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, id, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received Guild Member {} event for a Guild not yet cached.", (Object)(this.banned ? "Ban" : "Unban"));
            return null;
        }
        UserImpl user = this.getJDA().getEntityBuilder().createUser(userJson);
        if (this.banned) {
            this.getJDA().handleEvent(new GuildBanEvent(this.getJDA(), this.responseNumber, guild, user));
        } else {
            this.getJDA().handleEvent(new GuildUnbanEvent(this.getJDA(), this.responseNumber, guild, user));
        }
        return null;
    }
}

