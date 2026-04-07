/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.ScheduledEventManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ScheduledEventMembersPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ScheduledEventManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.ScheduledEventMembersPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class ScheduledEventImpl
implements ScheduledEvent {
    private final long id;
    private final Guild guild;
    private String name;
    private String description;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String image;
    private ScheduledEvent.Status status;
    private ScheduledEvent.Type type;
    private User creator;
    private long creatorId;
    private int interestedUserCount;
    private String location;

    public ScheduledEventImpl(long id, Guild guild) {
        this.id = id;
        this.guild = guild;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Override
    @Nullable
    public String getImageUrl() {
        return this.image == null ? null : String.format("https://cdn.discordapp.com/guild-events/%s/%s.%s", this.getId(), this.image, this.image.startsWith("a_") ? "gif" : "png");
    }

    @Override
    @Nullable
    public User getCreator() {
        return this.creator;
    }

    @Override
    public long getCreatorIdLong() {
        return this.creatorId;
    }

    @Override
    @Nonnull
    public ScheduledEvent.Status getStatus() {
        return this.status;
    }

    @Override
    @Nonnull
    public ScheduledEvent.Type getType() {
        return this.type;
    }

    @Override
    @Nonnull
    public OffsetDateTime getStartTime() {
        return this.startTime;
    }

    @Override
    @Nullable
    public OffsetDateTime getEndTime() {
        return this.endTime;
    }

    @Override
    @Nullable
    public GuildChannelUnion getChannel() {
        if (this.type.isChannel()) {
            return (GuildChannelUnion)this.guild.getGuildChannelById(this.location);
        }
        return null;
    }

    @Override
    @Nonnull
    public String getLocation() {
        return this.location;
    }

    @Override
    @Nonnull
    public String getJumpUrl() {
        return Helpers.format("https://discord.com/events/%s/%s", this.getGuild().getId(), this.getId());
    }

    @Override
    public int getInterestedUserCount() {
        return this.interestedUserCount;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public ScheduledEventManager getManager() {
        return new ScheduledEventManagerImpl(this);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        Guild guild = this.getGuild();
        if (!guild.getSelfMember().hasPermission(Permission.MANAGE_EVENTS)) {
            throw new InsufficientPermissionException(guild, Permission.MANAGE_EVENTS);
        }
        Route.CompiledRoute route = Route.Guilds.DELETE_SCHEDULED_EVENT.compile(guild.getId(), this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public ScheduledEventMembersPaginationAction retrieveInterestedMembers() {
        return new ScheduledEventMembersPaginationActionImpl(this);
    }

    public ScheduledEventImpl setName(String name) {
        this.name = name;
        return this;
    }

    public ScheduledEventImpl setType(ScheduledEvent.Type type) {
        this.type = type;
        return this;
    }

    public ScheduledEventImpl setLocation(String location) {
        this.location = location;
        return this;
    }

    public ScheduledEventImpl setDescription(String description) {
        this.description = description;
        return this;
    }

    public ScheduledEventImpl setImage(String image) {
        this.image = image;
        return this;
    }

    public ScheduledEventImpl setCreatorId(long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public ScheduledEventImpl setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public ScheduledEventImpl setStatus(ScheduledEvent.Status status) {
        this.status = status;
        return this;
    }

    public ScheduledEventImpl setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ScheduledEventImpl setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public ScheduledEventImpl setInterestedUserCount(int interestedUserCount) {
        this.interestedUserCount = interestedUserCount;
        return this;
    }

    @Override
    public int compareTo(@Nonnull ScheduledEvent scheduledEvent) {
        Checks.notNull(scheduledEvent, "Scheduled Event");
        Checks.check(this.getGuild().equals(scheduledEvent.getGuild()), "Cannot compare two Scheduled Events belonging to seperate guilds!");
        int startTimeComparison = OffsetDateTime.timeLineOrder().compare(this.getStartTime(), scheduledEvent.getStartTime());
        if (startTimeComparison == 0) {
            return Long.compare(this.getIdLong(), scheduledEvent.getIdLong());
        }
        return startTimeComparison;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScheduledEventImpl)) {
            return false;
        }
        return this.id == ((ScheduledEventImpl)o).id;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public String toString() {
        return "ScheduledEvent:" + this.getName() + '(' + this.id + ')';
    }
}

