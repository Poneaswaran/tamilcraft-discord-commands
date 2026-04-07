/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AbstractThreadCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.FluentAuditableRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ThreadChannelAction
extends AbstractThreadCreateAction<ThreadChannel, ThreadChannelAction>,
FluentAuditableRestAction<ThreadChannel, ThreadChannelAction> {
    @Nonnull
    @CheckReturnValue
    public ThreadChannelAction setInvitable(boolean var1);
}

