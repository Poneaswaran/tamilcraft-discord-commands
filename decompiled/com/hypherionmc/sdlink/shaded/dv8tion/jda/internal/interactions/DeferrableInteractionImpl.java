/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InteractionFailureException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionHookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class DeferrableInteractionImpl
extends InteractionImpl
implements IDeferrableCallback {
    protected final InteractionHookImpl hook;

    public DeferrableInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
        this.hook = new InteractionHookImpl(this, jda);
    }

    @Override
    public synchronized void releaseHook(boolean success) {
        if (success) {
            this.hook.ready();
        } else {
            this.hook.fail(new InteractionFailureException());
        }
    }

    @Override
    @Nonnull
    public InteractionHook getHook() {
        return this.hook;
    }
}

