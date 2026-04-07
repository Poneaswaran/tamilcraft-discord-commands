/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.Duration;

public class AutoModResponseImpl
implements AutoModResponse {
    private final AutoModResponse.Type type;
    private final GuildMessageChannel channel;
    private final String customMessage;
    private final long timeoutDuration;

    public AutoModResponseImpl(AutoModResponse.Type type) {
        this.type = type;
        this.channel = null;
        this.customMessage = null;
        this.timeoutDuration = 0L;
    }

    public AutoModResponseImpl(AutoModResponse.Type type, GuildMessageChannel channel) {
        this.type = type;
        this.channel = channel;
        this.customMessage = null;
        this.timeoutDuration = 0L;
    }

    public AutoModResponseImpl(AutoModResponse.Type type, String customMessage) {
        this.type = type;
        this.customMessage = customMessage;
        this.channel = null;
        this.timeoutDuration = 0L;
    }

    public AutoModResponseImpl(AutoModResponse.Type type, Duration duration) {
        this.type = type;
        this.timeoutDuration = duration.getSeconds();
        this.customMessage = null;
        this.channel = null;
    }

    public AutoModResponseImpl(Guild guild, DataObject json) {
        DataObject metadata = json.optObject("metadata").orElseGet(DataObject::empty);
        this.type = AutoModResponse.Type.fromKey(json.getInt("type", -1));
        this.channel = guild.getChannelById(GuildMessageChannel.class, metadata.getUnsignedLong("channel_id", 0L));
        this.customMessage = metadata.getString("custom_message", null);
        this.timeoutDuration = metadata.getUnsignedLong("duration_seconds", 0L);
    }

    @Override
    @Nonnull
    public AutoModResponse.Type getType() {
        return this.type;
    }

    @Override
    @Nullable
    public GuildMessageChannel getChannel() {
        return this.channel;
    }

    @Override
    @Nullable
    public String getCustomMessage() {
        return this.customMessage;
    }

    @Override
    @Nullable
    public Duration getTimeoutDuration() {
        return this.timeoutDuration == 0L ? null : Duration.ofSeconds(this.timeoutDuration);
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject action = DataObject.empty();
        action.put("type", this.type.getKey());
        if (this.type == AutoModResponse.Type.BLOCK_MESSAGE && this.customMessage == null) {
            return action;
        }
        DataObject metadata = DataObject.empty();
        if (this.customMessage != null) {
            metadata.put("custom_message", this.customMessage);
        }
        if (this.channel != null) {
            metadata.put("channel_id", this.channel.getId());
        }
        if (this.timeoutDuration > 0L) {
            metadata.put("duration_seconds", this.timeoutDuration);
        }
        action.put("metadata", metadata);
        return action;
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AutoModResponseImpl)) {
            return false;
        }
        AutoModResponseImpl o = (AutoModResponseImpl)obj;
        return this.type == o.type;
    }
}

