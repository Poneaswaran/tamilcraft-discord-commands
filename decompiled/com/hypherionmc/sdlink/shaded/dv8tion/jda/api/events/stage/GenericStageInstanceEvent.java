/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericStageInstanceEvent
extends GenericGuildEvent {
    protected final StageInstance instance;

    public GenericStageInstanceEvent(@Nonnull JDA api, long responseNumber, @Nonnull StageInstance stageInstance) {
        super(api, responseNumber, stageInstance.getGuild());
        this.instance = stageInstance;
    }

    @Nonnull
    public StageInstance getInstance() {
        return this.instance;
    }

    @Nonnull
    public StageChannel getChannel() {
        return this.instance.getChannel();
    }
}

