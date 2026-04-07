/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.ModalInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.ModalMapping;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public class ModalInteractionEvent
extends GenericInteractionCreateEvent
implements ModalInteraction {
    private final ModalInteraction interaction;

    public ModalInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull ModalInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public ModalInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public String getModalId() {
        return this.interaction.getModalId();
    }

    @Override
    @Nonnull
    public List<ModalMapping> getValues() {
        return this.interaction.getValues();
    }

    @Override
    @Nullable
    public Message getMessage() {
        return this.interaction.getMessage();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ReplyCallbackAction deferReply() {
        return this.interaction.deferReply();
    }

    @Override
    @Nonnull
    public InteractionHook getHook() {
        return this.interaction.getHook();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MessageEditCallbackAction deferEdit() {
        return this.interaction.deferEdit();
    }

    @Override
    @Nonnull
    public MessageChannelUnion getChannel() {
        return this.interaction.getChannel();
    }
}

