/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.FluentRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.InteractionCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateRequest;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ReplyCallbackAction
extends InteractionCallbackAction<InteractionHook>,
MessageCreateRequest<ReplyCallbackAction>,
FluentRestAction<InteractionHook, ReplyCallbackAction> {
    @Nonnull
    @CheckReturnValue
    public ReplyCallbackAction closeResources();

    @Nonnull
    @CheckReturnValue
    public ReplyCallbackAction setEphemeral(boolean var1);
}

