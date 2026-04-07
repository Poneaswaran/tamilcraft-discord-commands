/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ScheduledEventAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class ScheduledEventActionImpl
extends AuditableRestActionImpl<ScheduledEvent>
implements ScheduledEventAction {
    protected final Guild guild;
    protected String name;
    protected String description;
    protected Icon image;
    protected long channelId;
    protected String location;
    protected OffsetDateTime startTime;
    protected OffsetDateTime endTime;
    protected final ScheduledEvent.Type entityType;

    public ScheduledEventActionImpl(String name, String location, TemporalAccessor startTime, TemporalAccessor endTime, Guild guild) {
        super(guild.getJDA(), Route.Guilds.CREATE_SCHEDULED_EVENT.compile(guild.getId()));
        this.guild = guild;
        this.setName(name);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        Checks.notNull(location, "Location");
        Checks.notBlank(location, "Location");
        Checks.notEmpty(location, "Location");
        Checks.notLonger(location, 100, "Location");
        this.location = location;
        this.entityType = ScheduledEvent.Type.EXTERNAL;
    }

    public ScheduledEventActionImpl(String name, GuildChannel channel, TemporalAccessor startTime, Guild guild) {
        super(guild.getJDA(), Route.Guilds.CREATE_SCHEDULED_EVENT.compile(guild.getId()));
        this.guild = guild;
        this.setName(name);
        this.setStartTime(startTime);
        Checks.notNull(channel, "Channel");
        if (!channel.getGuild().equals(guild)) {
            throw new IllegalArgumentException("Invalid parameter: Channel has to be from the same guild as the scheduled event!");
        }
        if (channel instanceof StageChannel) {
            this.channelId = channel.getIdLong();
            this.entityType = ScheduledEvent.Type.STAGE_INSTANCE;
        } else if (channel instanceof VoiceChannel) {
            this.channelId = channel.getIdLong();
            this.entityType = ScheduledEvent.Type.VOICE;
        } else {
            throw new IllegalArgumentException("Invalid parameter: Can only set location to Voice and Stage Channels!");
        }
    }

    @Override
    @Nonnull
    public ScheduledEventActionImpl setCheck(BooleanSupplier checks) {
        return (ScheduledEventActionImpl)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public ScheduledEventActionImpl timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (ScheduledEventActionImpl)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public ScheduledEventActionImpl deadline(long timestamp) {
        return (ScheduledEventActionImpl)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public ScheduledEventActionImpl reason(@Nullable String reason) {
        return (ScheduledEventActionImpl)super.reason(reason);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    public ScheduledEventActionImpl setName(@Nullable String name) {
        Checks.notBlank(name, "Name");
        Checks.notLonger(name, 100, "Name");
        this.name = name;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ScheduledEventActionImpl setDescription(@Nullable String description) {
        if (description != null) {
            Checks.notLonger(description, 1000, "Description");
        }
        this.description = description;
        return this;
    }

    @Override
    @Nonnull
    public ScheduledEventAction setStartTime(@Nonnull TemporalAccessor startTime) {
        Checks.notNull(startTime, "Start Time");
        OffsetDateTime offsetStartTime = Helpers.toOffsetDateTime(startTime);
        Checks.check(offsetStartTime.isAfter(OffsetDateTime.now()), "Cannot schedule event in the past!");
        Checks.check(offsetStartTime.isBefore(OffsetDateTime.now().plusYears(5L)), "Scheduled start and end times must be within five years.");
        this.startTime = offsetStartTime;
        return this;
    }

    @Override
    @Nonnull
    public ScheduledEventAction setEndTime(@Nullable TemporalAccessor endTime) {
        Checks.notNull(endTime, "End Time");
        OffsetDateTime offsetEndTime = Helpers.toOffsetDateTime(endTime);
        Checks.check(offsetEndTime.isAfter(this.startTime), "Cannot schedule event to end before its starting!");
        Checks.check(offsetEndTime.isBefore(OffsetDateTime.now().plusYears(5L)), "Scheduled start and end times must be within five years.");
        this.endTime = offsetEndTime;
        return this;
    }

    @Override
    @Nonnull
    public ScheduledEventAction setImage(@Nullable Icon icon) {
        this.image = icon;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        object.put("entity_type", this.entityType.getKey());
        object.put("privacy_level", 2);
        object.put("name", this.name);
        object.put("scheduled_start_time", this.startTime.format(DateTimeFormatter.ISO_DATE_TIME));
        switch (this.entityType) {
            case STAGE_INSTANCE: 
            case VOICE: {
                object.put("channel_id", this.channelId);
                break;
            }
            case EXTERNAL: {
                object.put("entity_metadata", DataObject.empty().put("location", this.location));
                break;
            }
            default: {
                throw new IllegalStateException("ScheduledEventType " + (Object)((Object)this.entityType) + " is not supported!");
            }
        }
        if (this.description != null) {
            object.put("description", this.description);
        }
        if (this.image != null) {
            object.put("image", this.image.getEncoding());
        }
        if (this.endTime != null) {
            object.put("scheduled_end_time", this.endTime.format(DateTimeFormatter.ISO_DATE_TIME));
        }
        return this.getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<ScheduledEvent> request) {
        request.onSuccess(this.api.getEntityBuilder().createScheduledEvent((GuildImpl)this.guild, response.getObject()));
    }
}

