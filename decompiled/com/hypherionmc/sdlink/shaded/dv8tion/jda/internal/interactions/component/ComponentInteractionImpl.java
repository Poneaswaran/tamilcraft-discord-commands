/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ComponentInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.Modal;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ModalCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.PremiumRequiredCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.DeferrableInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.MessageEditCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.ModalCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.PremiumRequiredCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.ReplyCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class ComponentInteractionImpl
extends DeferrableInteractionImpl
implements ComponentInteraction {
    protected final String customId;
    protected final Message message;
    protected final long messageId;

    public ComponentInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
        this.customId = data.getObject("data").getString("custom_id");
        DataObject messageJson = data.getObject("message");
        this.messageId = messageJson.getUnsignedLong("id");
        if (messageJson.isNull("type")) {
            this.message = null;
        } else {
            Guild guild = this.getGuild();
            MessageChannelUnion channel = this.getChannel();
            this.message = channel != null ? jda.getEntityBuilder().createMessageWithChannel(messageJson, channel, false) : jda.getEntityBuilder().createMessageWithLookup(messageJson, guild, false);
            ((ReceivedMessage)this.message).withHook(this.getHook());
        }
    }

    @Override
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)super.getChannel();
    }

    @Override
    @Nonnull
    public String getComponentId() {
        return this.customId;
    }

    @Override
    @Nonnull
    public Message getMessage() {
        return this.message;
    }

    @Override
    public long getMessageIdLong() {
        return this.messageId;
    }

    @Override
    @Nonnull
    public MessageEditCallbackActionImpl deferEdit() {
        return new MessageEditCallbackActionImpl(this.hook);
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

