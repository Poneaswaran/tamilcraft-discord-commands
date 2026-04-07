/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Command;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.MessageContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.MessageContextMenuEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.UserContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.UserContextMenuEvent;

public interface CommandListener {
    default public void onCommand(CommandEvent event, Command command) {
    }

    default public void onSlashCommand(SlashCommandEvent event, SlashCommand command) {
    }

    default public void onMessageContextMenu(MessageContextMenuEvent event, MessageContextMenu menu) {
    }

    default public void onUserContextMenu(UserContextMenuEvent event, UserContextMenu menu) {
    }

    default public void onCompletedCommand(CommandEvent event, Command command) {
    }

    default public void onCompletedSlashCommand(SlashCommandEvent event, SlashCommand command) {
    }

    default public void onCompletedMessageContextMenu(MessageContextMenuEvent event, MessageContextMenu menu) {
    }

    default public void onCompletedUserContextMenu(UserContextMenuEvent event, UserContextMenu menu) {
    }

    default public void onTerminatedCommand(CommandEvent event, Command command) {
    }

    default public void onTerminatedSlashCommand(SlashCommandEvent event, SlashCommand command) {
    }

    default public void onTerminatedMessageContextMenu(MessageContextMenuEvent event, MessageContextMenu menu) {
    }

    default public void onTerminatedUserContextMenu(UserContextMenuEvent event, UserContextMenu menu) {
    }

    default public void onNonCommandMessage(MessageReceivedEvent event) {
    }

    default public void onCommandException(CommandEvent event, Command command, Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    default public void onSlashCommandException(SlashCommandEvent event, SlashCommand command, Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    default public void onMessageContextMenuException(MessageContextMenuEvent event, MessageContextMenu menu, Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    default public void onUserContextMenuException(UserContextMenuEvent event, UserContextMenu menu, Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }
}

