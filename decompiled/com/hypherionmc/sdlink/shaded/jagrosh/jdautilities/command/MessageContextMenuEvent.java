/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import java.io.File;
import org.jetbrains.annotations.NotNull;

public class MessageContextMenuEvent
extends MessageContextInteractionEvent {
    private final CommandClient client;

    public MessageContextMenuEvent(@NotNull JDA api, long responseNumber, @NotNull MessageContextInteraction interaction, CommandClient client) {
        super(api, responseNumber, interaction);
        this.client = client;
    }

    public CommandClient getClient() {
        return this.client;
    }

    public void respond(String message) {
        this.reply(message).queue();
    }

    public void respond(MessageEmbed embed) {
        this.replyEmbeds(embed, new MessageEmbed[0]).queue();
    }

    public void respond(MessageCreateData message) {
        this.reply(message).queue();
    }

    public void respond(File file, String filename, String description, boolean spoiler) {
        FileUpload fileUpload = FileUpload.fromData(file, filename);
        if (description != null && !description.isEmpty()) {
            fileUpload.setDescription(description);
        }
        if (spoiler) {
            fileUpload.asSpoiler();
        }
        this.replyFiles(fileUpload).queue();
    }

    public boolean isOwner() {
        if (this.getUser().getId().equals(this.getClient().getOwnerId())) {
            return true;
        }
        if (this.getClient().getCoOwnerIds() == null) {
            return false;
        }
        for (String id : this.getClient().getCoOwnerIds()) {
            if (!id.equals(this.getUser().getId())) continue;
            return true;
        }
        return false;
    }
}

