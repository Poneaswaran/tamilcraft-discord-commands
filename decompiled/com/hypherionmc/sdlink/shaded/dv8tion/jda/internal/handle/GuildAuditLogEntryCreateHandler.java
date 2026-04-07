/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogEntry;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildAuditLogEntryCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class GuildAuditLogEntryCreateHandler
extends SocketHandler {
    public GuildAuditLogEntryCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long id = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(id)) {
            return id;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(id);
        if (guild == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, id, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received Guild Audit Log Create event for a Guild not yet cached. GuildId: {}", (Object)id);
            return null;
        }
        AuditLogEntry entry = this.api.getEntityBuilder().createAuditLogEntry(guild, content, null, null);
        this.api.handleEvent(new GuildAuditLogEntryCreateEvent((JDA)this.api, this.responseNumber, entry));
        return null;
    }
}

