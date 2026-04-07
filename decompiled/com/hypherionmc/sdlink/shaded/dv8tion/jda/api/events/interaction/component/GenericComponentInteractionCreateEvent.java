/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ComponentInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.Modal;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ModalCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.PremiumRequiredCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericComponentInteractionCreateEvent
extends GenericInteractionCreateEvent
implements ComponentInteraction {
    private final ComponentInteraction interaction;

    public GenericComponentInteractionCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull ComponentInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public ComponentInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public MessageChannelUnion getChannel() {
        return this.interaction.getChannel();
    }

    @Override
    @Nonnull
    public String getComponentId() {
        return this.interaction.getComponentId();
    }

    @Override
    @Nonnull
    public ActionComponent getComponent() {
        return this.interaction.getComponent();
    }

    @Override
    @Nonnull
    public Message getMessage() {
        return this.interaction.getMessage();
    }

    @Override
    public long getMessageIdLong() {
        return this.interaction.getMessageIdLong();
    }

    @Override
    @Nonnull
    public Component.Type getComponentType() {
        return this.interaction.getComponentType();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public MessageEditCallbackAction deferEdit() {
        return this.interaction.deferEdit();
    }

    @Override
    @Nonnull
    public InteractionHook getHook() {
        return this.interaction.getHook();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ReplyCallbackAction deferReply() {
        return this.interaction.deferReply();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ModalCallbackAction replyModal(@Nonnull Modal modal) {
        return this.interaction.replyModal(modal);
    }

    @Override
    @Nonnull
    @Deprecated
    @CheckReturnValue
    public PremiumRequiredCallbackAction replyWithPremiumRequired() {
        return this.interaction.replyWithPremiumRequired();
    }
}

