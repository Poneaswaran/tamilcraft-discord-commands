/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionHookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.InteractionCallbackImpl;

public abstract class DeferrableCallbackActionImpl
extends InteractionCallbackImpl<InteractionHook> {
    protected final InteractionHookImpl hook;

    public DeferrableCallbackActionImpl(InteractionHookImpl hook) {
        super(hook.getInteraction());
        this.hook = hook;
    }

    @Override
    protected void handleSuccess(Response response, Request<InteractionHook> request) {
        this.interaction.releaseHook(true);
        request.onSuccess(this.hook);
    }
}

