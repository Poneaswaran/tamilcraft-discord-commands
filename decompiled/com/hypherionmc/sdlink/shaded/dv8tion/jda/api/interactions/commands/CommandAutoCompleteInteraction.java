/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.AutoCompleteQuery;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IAutoCompleteCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface CommandAutoCompleteInteraction
extends IAutoCompleteCallback,
CommandInteractionPayload {
    @Nonnull
    public AutoCompleteQuery getFocusedOption();

    @Override
    @Nonnull
    public MessageChannelUnion getChannel();

    @Override
    @Nonnull
    default public GuildMessageChannelUnion getGuildChannel() {
        return (GuildMessageChannelUnion)IAutoCompleteCallback.super.getGuildChannel();
    }
}

