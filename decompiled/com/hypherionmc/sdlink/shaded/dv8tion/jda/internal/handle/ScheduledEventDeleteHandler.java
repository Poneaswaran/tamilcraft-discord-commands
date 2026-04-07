/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class ScheduledEventDeleteHandler
extends SocketHandler {
    public ScheduledEventDeleteHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        if (!this.getJDA().isCacheFlagSet(CacheFlag.SCHEDULED_EVENTS)) {
            return null;
        }
        long guildId = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        if (guild == null) {
            EventCache.LOG.debug("SCHEDULED_EVENT_DELETE was received for a Guild that is not yet cached: {}", (Object)content);
            return null;
        }
        long eventId = content.getLong("id");
        ScheduledEvent removedEvent = (ScheduledEvent)guild.getScheduledEventsView().remove(eventId);
        if (removedEvent != null) {
            this.getJDA().handleEvent(new ScheduledEventDeleteEvent((JDA)this.getJDA(), this.responseNumber, removedEvent));
        }
        return null;
    }
}

