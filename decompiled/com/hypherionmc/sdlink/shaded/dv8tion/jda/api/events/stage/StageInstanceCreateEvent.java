/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.GenericStageInstanceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class StageInstanceCreateEvent
extends GenericStageInstanceEvent {
    public StageInstanceCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull StageInstance stageInstance) {
        super(api, responseNumber, stageInstance);
    }
}

