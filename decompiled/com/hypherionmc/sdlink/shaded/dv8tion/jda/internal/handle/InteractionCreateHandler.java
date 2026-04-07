/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandAutoCompleteInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.MessageContextInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.SlashCommandInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.UserContextInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.ButtonInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.EntitySelectInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.StringSelectInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.modal.ModalInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class InteractionCreateHandler
extends SocketHandler {
    public InteractionCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        int type = content.getInt("type");
        int version = content.getInt("version", 1);
        if (version != 1) {
            WebSocketClient.LOG.debug("Received interaction with version {}. This version is currently unsupported by this version of JDA. Consider updating!", (Object)version);
            return null;
        }
        long guildId = content.getUnsignedLong("guild_id", 0L);
        Guild guild = this.api.getGuildById(guildId);
        if (this.api.getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        if (guildId != 0L && guild == null) {
            return null;
        }
        DataObject channelJson = content.getObject("channel");
        ChannelType channelType = ChannelType.fromId(channelJson.getInt("type"));
        if (!channelType.isMessage() || channelType == ChannelType.GROUP) {
            WebSocketClient.LOG.debug("Discarding INTERACTION_CREATE event from unexpected channel type. Channel: {}", (Object)channelJson);
            return null;
        }
        switch (InteractionType.fromKey(type)) {
            case COMMAND: {
                this.handleCommand(content);
                break;
            }
            case COMPONENT: {
                this.handleAction(content);
                break;
            }
            case COMMAND_AUTOCOMPLETE: {
                this.api.handleEvent(new CommandAutoCompleteInteractionEvent((JDA)this.api, this.responseNumber, new CommandAutoCompleteInteractionImpl(this.api, content)));
                break;
            }
            case MODAL_SUBMIT: {
                this.api.handleEvent(new ModalInteractionEvent((JDA)this.api, this.responseNumber, new ModalInteractionImpl(this.api, content)));
                break;
            }
            default: {
                this.api.handleEvent(new GenericInteractionCreateEvent(this.api, this.responseNumber, new InteractionImpl(this.api, content)));
            }
        }
        return null;
    }

    private void handleCommand(DataObject content) {
        switch (Command.Type.fromId(content.getObject("data").getInt("type"))) {
            case SLASH: {
                this.api.handleEvent(new SlashCommandInteractionEvent((JDA)this.api, this.responseNumber, new SlashCommandInteractionImpl(this.api, content)));
                break;
            }
            case MESSAGE: {
                this.api.handleEvent(new MessageContextInteractionEvent((JDA)this.api, this.responseNumber, new MessageContextInteractionImpl(this.api, content)));
                break;
            }
            case USER: {
                this.api.handleEvent(new UserContextInteractionEvent((JDA)this.api, this.responseNumber, new UserContextInteractionImpl(this.api, content)));
            }
        }
    }

    private void handleAction(DataObject content) {
        switch (Component.Type.fromKey(content.getObject("data").getInt("component_type"))) {
            case BUTTON: {
                this.api.handleEvent(new ButtonInteractionEvent((JDA)this.api, this.responseNumber, new ButtonInteractionImpl(this.api, content)));
                break;
            }
            case STRING_SELECT: {
                this.api.handleEvent(new StringSelectInteractionEvent((JDA)this.api, this.responseNumber, new StringSelectInteractionImpl(this.api, content)));
                break;
            }
            case USER_SELECT: 
            case ROLE_SELECT: 
            case MENTIONABLE_SELECT: 
            case CHANNEL_SELECT: {
                this.api.handleEvent(new EntitySelectInteractionEvent((JDA)this.api, this.responseNumber, new EntitySelectInteractionImpl(this.api, content)));
            }
        }
    }
}

