/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.update.GenericStageInstanceUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class StageInstanceUpdatePrivacyLevelEvent
extends GenericStageInstanceUpdateEvent<StageInstance.PrivacyLevel> {
    public static final String IDENTIFIER = "privacy_level";

    public StageInstanceUpdatePrivacyLevelEvent(@Nonnull JDA api, long responseNumber, @Nonnull StageInstance stageInstance, @Nonnull StageInstance.PrivacyLevel previous) {
        super(api, responseNumber, stageInstance, previous, stageInstance.getPrivacyLevel(), IDENTIFIER);
    }

    @Override
    @Nonnull
    public StageInstance.PrivacyLevel getOldValue() {
        return (StageInstance.PrivacyLevel)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public StageInstance.PrivacyLevel getNewValue() {
        return (StageInstance.PrivacyLevel)((Object)super.getNewValue());
    }
}

