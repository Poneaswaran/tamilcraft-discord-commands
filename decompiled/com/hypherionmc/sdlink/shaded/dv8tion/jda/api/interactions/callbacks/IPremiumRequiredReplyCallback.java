/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.PremiumRequiredCallbackAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

@Deprecated
public interface IPremiumRequiredReplyCallback
extends IDeferrableCallback {
    @Nonnull
    @Deprecated
    @CheckReturnValue
    public PremiumRequiredCallbackAction replyWithPremiumRequired();
}

