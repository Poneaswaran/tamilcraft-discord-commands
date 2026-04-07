/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IModalCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IPremiumRequiredReplyCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ComponentInteraction
extends IReplyCallback,
IMessageEditCallback,
IModalCallback,
IPremiumRequiredReplyCallback {
    @Nonnull
    public String getComponentId();

    @Nonnull
    public ActionComponent getComponent();

    @Nonnull
    public Message getMessage();

    public long getMessageIdLong();

    @Nonnull
    default public String getMessageId() {
        return Long.toUnsignedString(this.getMessageIdLong());
    }

    @Nonnull
    public Component.Type getComponentType();

    @Override
    @Nonnull
    public MessageChannelUnion getChannel();

    @Override
    @Nonnull
    default public GuildMessageChannelUnion getGuildChannel() {
        return (GuildMessageChannelUnion)IReplyCallback.super.getGuildChannel();
    }
}

