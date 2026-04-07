/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SlashCommandInteractionImpl
extends CommandInteractionImpl
implements SlashCommandInteraction {
    public SlashCommandInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
    }

    @Override
    @Nonnull
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)super.getChannel();
    }
}

