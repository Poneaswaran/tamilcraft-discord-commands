/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.stage.GenericStageInstanceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericStageInstanceUpdateEvent<T>
extends GenericStageInstanceEvent
implements UpdateEvent<StageInstance, T> {
    protected final T previous;
    protected final T next;
    protected final String identifier;

    public GenericStageInstanceUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull StageInstance stageInstance, T previous, T next, String identifier) {
        super(api, responseNumber, stageInstance);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.identifier;
    }

    @Override
    @Nonnull
    public StageInstance getEntity() {
        return this.getInstance();
    }

    @Override
    @Nullable
    public T getOldValue() {
        return this.previous;
    }

    @Override
    @Nullable
    public T getNewValue() {
        return this.next;
    }
}

