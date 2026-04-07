/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.ContextInteractionImpl;

public class MessageContextInteractionImpl
extends ContextInteractionImpl<Message>
implements MessageContextInteraction {
    public MessageContextInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
    }

    @Override
    protected Message parse(DataObject interaction, DataObject resolved) {
        long guildId;
        DataObject messages = resolved.getObject("messages");
        DataObject message = messages.getObject(messages.keys().iterator().next());
        Guild guild = null;
        if (!interaction.isNull("guild_id") && (guild = this.api.getGuildById(guildId = interaction.getUnsignedLong("guild_id"))) == null) {
            throw new IllegalStateException("Cannot find guild for resolved message object.");
        }
        return this.api.getEntityBuilder().createMessageWithLookup(message, guild, false);
    }

    @Override
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)super.getChannel();
    }
}

