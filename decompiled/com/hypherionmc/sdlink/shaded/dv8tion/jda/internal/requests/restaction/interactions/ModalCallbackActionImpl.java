/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IModalCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.Modal;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.InteractionCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ModalCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.InteractionCallbackImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.function.BooleanSupplier;

public class ModalCallbackActionImpl
extends InteractionCallbackImpl<Void>
implements ModalCallbackAction {
    private final Modal modal;

    public ModalCallbackActionImpl(IModalCallback interaction, Modal modal) {
        super((InteractionImpl)((Object)interaction));
        this.modal = modal;
    }

    @Override
    protected RequestBody finalizeData() {
        return this.getRequestBody(DataObject.empty().put("type", InteractionCallbackAction.ResponseType.MODAL.getRaw()).put("data", this.modal));
    }

    @Override
    @Nonnull
    public ModalCallbackAction setCheck(BooleanSupplier checks) {
        return (ModalCallbackAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public ModalCallbackAction deadline(long timestamp) {
        return (ModalCallbackAction)super.deadline(timestamp);
    }
}

