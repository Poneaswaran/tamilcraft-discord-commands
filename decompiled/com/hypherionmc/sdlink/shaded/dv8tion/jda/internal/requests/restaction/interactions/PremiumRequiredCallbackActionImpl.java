/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IPremiumRequiredReplyCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.InteractionCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.PremiumRequiredCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.InteractionCallbackImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.function.BooleanSupplier;

public class PremiumRequiredCallbackActionImpl
extends InteractionCallbackImpl<Void>
implements PremiumRequiredCallbackAction {
    public PremiumRequiredCallbackActionImpl(IPremiumRequiredReplyCallback interaction) {
        super((InteractionImpl)((Object)interaction));
    }

    @Override
    protected RequestBody finalizeData() {
        return this.getRequestBody(DataObject.empty().put("type", InteractionCallbackAction.ResponseType.PREMIUM_REQUIRED.getRaw()).put("data", DataObject.empty()));
    }

    @Override
    @Nonnull
    public PremiumRequiredCallbackAction setCheck(BooleanSupplier checks) {
        return (PremiumRequiredCallbackAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public PremiumRequiredCallbackAction deadline(long timestamp) {
        return (PremiumRequiredCallbackAction)super.deadline(timestamp);
    }
}

