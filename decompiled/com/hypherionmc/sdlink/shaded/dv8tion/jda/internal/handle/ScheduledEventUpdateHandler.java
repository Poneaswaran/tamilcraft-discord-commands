/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateDescriptionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateEndTimeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateImageEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateLocationEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateStartTimeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateStatusEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ScheduledEventImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import java.time.OffsetDateTime;
import java.util.Objects;

public class ScheduledEventUpdateHandler
extends SocketHandler {
    public ScheduledEventUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        if (!this.getJDA().isCacheFlagSet(CacheFlag.SCHEDULED_EVENTS)) {
            return null;
        }
        long guildId = content.getUnsignedLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        if (guild == null) {
            EventCache.LOG.debug("Caching SCHEDULED_EVENT_UPDATE for uncached guild with id {}", (Object)guildId);
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            return null;
        }
        ScheduledEventImpl event = (ScheduledEventImpl)guild.getScheduledEventById(content.getUnsignedLong("id"));
        if (event == null) {
            this.api.getEntityBuilder().createScheduledEvent(guild, content);
            return null;
        }
        String name = content.getString("name");
        String description = content.getString("description", null);
        OffsetDateTime startTime = content.getOffsetDateTime("scheduled_start_time");
        OffsetDateTime endTime = content.getOffsetDateTime("scheduled_end_time", null);
        ScheduledEvent.Status status = ScheduledEvent.Status.fromKey(content.getInt("status", -1));
        String imageUrl = content.getString("image", null);
        String location = content.getString("channel_id", null);
        GuildChannel channel = null;
        String oldLocation = event.getLocation();
        if (location != null) {
            channel = guild.getGuildChannelById(location);
        } else {
            location = content.optObject("entity_metadata").map(o -> o.getString("location", "")).orElse("");
        }
        if (!Objects.equals(name, event.getName())) {
            String oldName = event.getName();
            event.setName(name);
            this.getJDA().handleEvent(new ScheduledEventUpdateNameEvent(this.getJDA(), this.responseNumber, event, oldName));
        }
        if (!Objects.equals(description, event.getDescription())) {
            String oldDescription = event.getDescription();
            event.setDescription(description);
            this.getJDA().handleEvent(new ScheduledEventUpdateDescriptionEvent(this.getJDA(), this.responseNumber, event, oldDescription));
        }
        if (!Objects.equals(startTime, event.getStartTime())) {
            OffsetDateTime oldStartTime = event.getStartTime();
            event.setStartTime(startTime);
            this.getJDA().handleEvent(new ScheduledEventUpdateStartTimeEvent(this.getJDA(), this.responseNumber, event, oldStartTime));
        }
        if (!Objects.equals(endTime, event.getEndTime())) {
            OffsetDateTime oldEndTime = event.getEndTime();
            event.setEndTime(endTime);
            this.getJDA().handleEvent(new ScheduledEventUpdateEndTimeEvent(this.getJDA(), this.responseNumber, event, oldEndTime));
        }
        if (!Objects.equals((Object)status, (Object)event.getStatus())) {
            ScheduledEvent.Status oldStatus = event.getStatus();
            event.setStatus(status);
            this.getJDA().handleEvent(new ScheduledEventUpdateStatusEvent(this.getJDA(), this.responseNumber, event, oldStatus));
        }
        if (channel == null && !location.equals(event.getLocation())) {
            event.setLocation(location);
            event.setType(ScheduledEvent.Type.EXTERNAL);
            this.getJDA().handleEvent(new ScheduledEventUpdateLocationEvent(this.getJDA(), this.responseNumber, event, oldLocation));
        }
        if (channel instanceof StageChannel && !location.equals(event.getLocation())) {
            event.setLocation(channel.getId());
            event.setType(ScheduledEvent.Type.STAGE_INSTANCE);
            this.getJDA().handleEvent(new ScheduledEventUpdateLocationEvent(this.getJDA(), this.responseNumber, event, oldLocation));
        }
        if (channel instanceof VoiceChannel && !location.equals(event.getLocation())) {
            event.setLocation(channel.getId());
            event.setType(ScheduledEvent.Type.VOICE);
            this.getJDA().handleEvent(new ScheduledEventUpdateLocationEvent(this.getJDA(), this.responseNumber, event, oldLocation));
        }
        if (!Objects.equals(imageUrl, event.getImageUrl())) {
            String oldImageUrl = event.getImageUrl();
            event.setImage(imageUrl);
            this.getJDA().handleEvent(new ScheduledEventUpdateImageEvent(this.getJDA(), this.responseNumber, event, oldImageUrl));
        }
        return null;
    }
}

