/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.ContextInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface MessageContextInteraction
extends ContextInteraction<Message> {
    @Override
    @Nonnull
    default public ContextInteraction.ContextTarget getTargetType() {
        return ContextInteraction.ContextTarget.MESSAGE;
    }

    @Override
    @Nullable
    public MessageChannelUnion getChannel();

    @Override
    @Nonnull
    default public GuildMessageChannelUnion getGuildChannel() {
        return (GuildMessageChannelUnion)ContextInteraction.super.getGuildChannel();
    }
}

