/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModExecution;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModTriggerType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class AutoModExecutionEvent
extends Event
implements AutoModExecution {
    private final AutoModExecution execution;

    public AutoModExecutionEvent(@Nonnull JDA api, long responseNumber, @Nonnull AutoModExecution execution) {
        super(api, responseNumber);
        this.execution = execution;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.execution.getGuild();
    }

    @Override
    @Nullable
    public GuildMessageChannelUnion getChannel() {
        return this.execution.getChannel();
    }

    @Override
    @Nonnull
    public AutoModResponse getResponse() {
        return this.execution.getResponse();
    }

    @Override
    @Nonnull
    public AutoModTriggerType getTriggerType() {
        return this.execution.getTriggerType();
    }

    @Override
    public long getUserIdLong() {
        return this.execution.getUserIdLong();
    }

    @Override
    public long getRuleIdLong() {
        return this.execution.getRuleIdLong();
    }

    @Override
    public long getMessageIdLong() {
        return this.execution.getMessageIdLong();
    }

    @Override
    public long getAlertMessageIdLong() {
        return this.execution.getAlertMessageIdLong();
    }

    @Override
    @Nonnull
    public String getContent() {
        return this.execution.getContent();
    }

    @Override
    @Nullable
    public String getMatchedContent() {
        return this.execution.getMatchedContent();
    }

    @Override
    @Nullable
    public String getMatchedKeyword() {
        return this.execution.getMatchedKeyword();
    }
}

