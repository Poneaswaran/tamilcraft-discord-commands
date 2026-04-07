/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.update.GenericStageInstanceUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class StageInstanceUpdateTopicEvent
extends GenericStageInstanceUpdateEvent<String> {
    public static final String IDENTIFIER = "topic";

    public StageInstanceUpdateTopicEvent(@Nonnull JDA api, long responseNumber, @Nonnull StageInstance stageInstance, String previous) {
        super(api, responseNumber, stageInstance, previous, stageInstance.getTopic(), IDENTIFIER);
    }

    @Override
    @Nonnull
    public String getOldValue() {
        return (String)super.getOldValue();
    }

    @Override
    @Nonnull
    public String getNewValue() {
        return (String)super.getNewValue();
    }
}

