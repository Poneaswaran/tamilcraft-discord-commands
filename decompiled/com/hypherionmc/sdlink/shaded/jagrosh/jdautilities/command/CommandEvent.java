/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.PermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.impl.CommandClientImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CommandEvent {
    public static int MAX_MESSAGES = 2;
    private final MessageReceivedEvent event;
    private final String prefix;
    private String args;
    private final CommandClient client;

    public CommandEvent(MessageReceivedEvent event, String prefix, String args2, CommandClient client) {
        this.event = event;
        this.prefix = prefix;
        this.args = args2 == null ? "" : args2;
        this.client = client;
    }

    public String getArgs() {
        return this.args;
    }

    void setArgs(String args2) {
        this.args = args2;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public MessageReceivedEvent getEvent() {
        return this.event;
    }

    public CommandClient getClient() {
        return this.client;
    }

    public void linkId(Message message) {
        Checks.check(message.getAuthor().equals(this.getSelfUser()), "Attempted to link a Message who's author was not the bot!");
        ((CommandClientImpl)this.client).linkIds(this.event.getMessageIdLong(), message);
    }

    public void reply(String message) {
        this.sendMessage(this.event.getChannel(), message);
    }

    public void reply(String message, Consumer<Message> success) {
        this.sendMessage(this.event.getChannel(), message, success);
    }

    public void reply(String message, Consumer<Message> success, Consumer<Throwable> failure) {
        this.sendMessage(this.event.getChannel(), message, success, failure);
    }

    public void reply(MessageEmbed embed) {
        this.event.getChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).queue(m -> {
            if (this.event.isFromType(ChannelType.TEXT)) {
                this.linkId((Message)m);
            }
        });
    }

    public void reply(MessageEmbed embed, Consumer<Message> success) {
        this.event.getChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).queue(m -> {
            if (this.event.isFromType(ChannelType.TEXT)) {
                this.linkId((Message)m);
            }
            success.accept((Message)m);
        });
    }

    public void reply(MessageEmbed embed, Consumer<Message> success, Consumer<Throwable> failure) {
        this.event.getChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).queue(m -> {
            if (this.event.isFromType(ChannelType.TEXT)) {
                this.linkId((Message)m);
            }
            success.accept((Message)m);
        }, failure);
    }

    public void reply(MessageCreateData message) {
        this.event.getChannel().sendMessage(message).queue(m -> {
            if (this.event.isFromType(ChannelType.TEXT)) {
                this.linkId((Message)m);
            }
        });
    }

    public void reply(MessageCreateData message, Consumer<Message> success) {
        this.event.getChannel().sendMessage(message).queue(m -> {
            if (this.event.isFromType(ChannelType.TEXT)) {
                this.linkId((Message)m);
            }
            success.accept((Message)m);
        });
    }

    public void reply(MessageCreateData message, Consumer<Message> success, Consumer<Throwable> failure) {
        this.event.getChannel().sendMessage(message).queue(m -> {
            if (this.event.isFromType(ChannelType.TEXT)) {
                this.linkId((Message)m);
            }
            success.accept((Message)m);
        }, failure);
    }

    public void reply(File file, String filename) {
        this.event.getChannel().sendFiles(FileUpload.fromData(file, filename)).queue();
    }

    public void reply(String message, File file, String filename) {
        ((MessageCreateAction)this.event.getChannel().sendFiles(FileUpload.fromData(file, filename)).addContent(message)).queue();
    }

    public void replyFormatted(String format, Object ... args2) {
        this.sendMessage(this.event.getChannel(), String.format(format, args2));
    }

    public void replyOrAlternate(MessageEmbed embed, String alternateMessage) {
        try {
            this.event.getChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).queue();
        }
        catch (PermissionException e) {
            this.reply(alternateMessage);
        }
    }

    public void replyOrAlternate(String message, File file, String filename, String alternateMessage) {
        try {
            ((MessageCreateAction)this.event.getChannel().sendFiles(FileUpload.fromData(file, filename)).addContent(message)).queue();
        }
        catch (Exception e) {
            this.reply(alternateMessage);
        }
    }

    public void replyInDm(String message) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.reply(message);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> this.sendMessage((MessageChannel)pc, message));
        }
    }

    public void replyInDm(String message, Consumer<Message> success) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.reply(message, success);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> this.sendMessage((MessageChannel)pc, message, success));
        }
    }

    public void replyInDm(String message, Consumer<Message> success, Consumer<Throwable> failure) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.reply(message, success, failure);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> this.sendMessage((MessageChannel)pc, message, success, failure), failure);
        }
    }

    public void replyInDm(MessageEmbed embed) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.reply(embed);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessageEmbeds(embed, new MessageEmbed[0]).queue());
        }
    }

    public void replyInDm(MessageEmbed embed, Consumer<Message> success) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.getPrivateChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).queue(success);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessageEmbeds(embed, new MessageEmbed[0]).queue(success));
        }
    }

    public void replyInDm(MessageEmbed embed, Consumer<Message> success, Consumer<Throwable> failure) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.getPrivateChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).queue(success, failure);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessageEmbeds(embed, new MessageEmbed[0]).queue(success, failure), failure);
        }
    }

    public void replyInDm(MessageCreateData message) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.reply(message);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessage(message).queue());
        }
    }

    public void replyInDm(MessageCreateData message, Consumer<Message> success) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.getPrivateChannel().sendMessage(message).queue(success);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessage(message).queue(success));
        }
    }

    public void replyInDm(Message message, Consumer<Message> success, Consumer<Throwable> failure) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.getPrivateChannel().sendMessage(MessageCreateData.fromMessage(message)).queue(success, failure);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessage(MessageCreateData.fromMessage(message)).queue(success, failure), failure);
        }
    }

    public void replyInDm(String message, File file, String filename) {
        if (this.event.isFromType(ChannelType.PRIVATE)) {
            this.reply(message, file, filename);
        } else {
            this.event.getAuthor().openPrivateChannel().queue(pc -> ((MessageCreateAction)pc.sendFiles(FileUpload.fromData(file, filename)).addContent(message)).queue());
        }
    }

    public void replySuccess(String message) {
        this.reply(this.client.getSuccess() + " " + message);
    }

    public void replySuccess(String message, Consumer<Message> queue) {
        this.reply(this.client.getSuccess() + " " + message, queue);
    }

    public void replyWarning(String message) {
        this.reply(this.client.getWarning() + " " + message);
    }

    public void replyWarning(String message, Consumer<Message> queue) {
        this.reply(this.client.getWarning() + " " + message, queue);
    }

    public void replyError(String message) {
        this.reply(this.client.getError() + " " + message);
    }

    public void replyError(String message, Consumer<Message> queue) {
        this.reply(this.client.getError() + " " + message, queue);
    }

    public void reactSuccess() {
        this.react(this.client.getSuccess());
    }

    public void reactWarning() {
        this.react(this.client.getWarning());
    }

    public void reactError() {
        this.react(this.client.getError());
    }

    public void async(Runnable runnable2) {
        Checks.notNull(runnable2, "Runnable");
        this.client.getScheduleExecutor().submit(runnable2);
    }

    private void react(String reaction) {
        if (reaction == null || reaction.isEmpty()) {
            return;
        }
        try {
            this.event.getMessage().addReaction(Emoji.fromFormatted(reaction.replaceAll("<a?:(.+):(\\d+)>", "$1:$2"))).queue();
        }
        catch (PermissionException permissionException) {
            // empty catch block
        }
    }

    private void sendMessage(MessageChannel chan, String message) {
        ArrayList<String> messages = CommandEvent.splitMessage(message);
        for (int i = 0; i < MAX_MESSAGES && i < messages.size(); ++i) {
            chan.sendMessage(messages.get(i)).queue(m -> {
                if (this.event.isFromType(ChannelType.TEXT)) {
                    this.linkId((Message)m);
                }
            });
        }
    }

    private void sendMessage(MessageChannel chan, String message, Consumer<Message> success) {
        ArrayList<String> messages = CommandEvent.splitMessage(message);
        for (int i = 0; i < MAX_MESSAGES && i < messages.size(); ++i) {
            if (i + 1 == MAX_MESSAGES || i + 1 == messages.size()) {
                chan.sendMessage(messages.get(i)).queue(m -> {
                    if (this.event.isFromType(ChannelType.TEXT)) {
                        this.linkId((Message)m);
                    }
                    success.accept((Message)m);
                });
                continue;
            }
            chan.sendMessage(messages.get(i)).queue(m -> {
                if (this.event.isFromType(ChannelType.TEXT)) {
                    this.linkId((Message)m);
                }
            });
        }
    }

    private void sendMessage(MessageChannel chan, String message, Consumer<Message> success, Consumer<Throwable> failure) {
        ArrayList<String> messages = CommandEvent.splitMessage(message);
        for (int i = 0; i < MAX_MESSAGES && i < messages.size(); ++i) {
            if (i + 1 == MAX_MESSAGES || i + 1 == messages.size()) {
                chan.sendMessage(messages.get(i)).queue(m -> {
                    if (this.event.isFromType(ChannelType.TEXT)) {
                        this.linkId((Message)m);
                    }
                    success.accept((Message)m);
                }, failure);
                continue;
            }
            chan.sendMessage(messages.get(i)).queue(m -> {
                if (this.event.isFromType(ChannelType.TEXT)) {
                    this.linkId((Message)m);
                }
            });
        }
    }

    public static ArrayList<String> splitMessage(String stringtoSend) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (stringtoSend != null) {
            stringtoSend = stringtoSend.replace("@everyone", "@\u0435veryone").replace("@here", "@h\u0435re").trim();
            while (stringtoSend.length() > 2000) {
                String temp;
                int leeway = 2000 - stringtoSend.length() % 2000;
                int index = stringtoSend.lastIndexOf("\n", 2000);
                if (index < leeway) {
                    index = stringtoSend.lastIndexOf(" ", 2000);
                }
                if (index < leeway) {
                    index = 2000;
                }
                if (!(temp = stringtoSend.substring(0, index).trim()).equals("")) {
                    msgs.add(temp);
                }
                stringtoSend = stringtoSend.substring(index).trim();
            }
            if (!stringtoSend.equals("")) {
                msgs.add(stringtoSend);
            }
        }
        return msgs;
    }

    public SelfUser getSelfUser() {
        return this.event.getJDA().getSelfUser();
    }

    public Member getSelfMember() {
        return this.event.getGuild() == null ? null : this.event.getGuild().getSelfMember();
    }

    public boolean isOwner() {
        if (this.event.getAuthor().getId().equals(this.getClient().getOwnerId())) {
            return true;
        }
        if (this.getClient().getCoOwnerIds() == null) {
            return false;
        }
        for (String id : this.getClient().getCoOwnerIds()) {
            if (!id.equals(this.event.getAuthor().getId())) continue;
            return true;
        }
        return false;
    }

    public User getAuthor() {
        return this.event.getAuthor();
    }

    public MessageChannel getChannel() {
        return this.event.getChannel();
    }

    public ChannelType getChannelType() {
        return this.event.getChannelType();
    }

    public Guild getGuild() {
        return this.event.getGuild();
    }

    public JDA getJDA() {
        return this.event.getJDA();
    }

    public Member getMember() {
        return this.event.getMember();
    }

    public Message getMessage() {
        return this.event.getMessage();
    }

    public PrivateChannel getPrivateChannel() {
        return this.event.getChannel().asPrivateChannel();
    }

    public long getResponseNumber() {
        return this.event.getResponseNumber();
    }

    public TextChannel getTextChannel() {
        return this.event.getChannel().asTextChannel();
    }

    public GuildMessageChannel getGuildChannel() {
        return this.event.getGuildChannel();
    }

    public boolean isFromType(ChannelType channelType) {
        return this.event.isFromType(channelType);
    }
}

