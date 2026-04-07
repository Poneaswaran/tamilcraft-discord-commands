/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.Modal;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ModalCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.PremiumRequiredCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.DeferrableInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionPayloadImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionPayloadMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.ModalCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.PremiumRequiredCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.ReplyCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class CommandInteractionImpl
extends DeferrableInteractionImpl
implements CommandInteraction,
CommandInteractionPayloadMixin {
    private final CommandInteractionPayloadImpl payload;

    public CommandInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
        this.payload = new CommandInteractionPayloadImpl(jda, data);
    }

    @Override
    public CommandInteractionPayload getCommandPayload() {
        return this.payload;
    }

    @Override
    @Nonnull
    public ReplyCallbackAction deferReply() {
        return new ReplyCallbackActionImpl(this.hook);
    }

    @Override
    @Nonnull
    public ModalCallbackAction replyModal(@Nonnull Modal modal) {
        Checks.notNull(modal, "Modal");
        return new ModalCallbackActionImpl(this, modal);
    }

    @Override
    @Nonnull
    public PremiumRequiredCallbackAction replyWithPremiumRequired() {
        return new PremiumRequiredCallbackActionImpl(this);
    }
}

