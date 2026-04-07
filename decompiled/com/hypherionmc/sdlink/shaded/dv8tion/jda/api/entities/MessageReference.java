/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class MessageReference {
    private final int type;
    private final long messageId;
    private final long channelId;
    private final long guildId;
    private final JDA api;
    private final MessageChannel channel;
    private final Guild guild;
    private Message referencedMessage;

    public MessageReference(int type, long messageId, long channelId, long guildId, @Nullable Message referencedMessage, JDA api) {
        this.type = type;
        this.messageId = messageId;
        this.channelId = channelId;
        this.guildId = guildId;
        this.referencedMessage = referencedMessage;
        this.channel = guildId == 0L ? api.getPrivateChannelById(channelId) : api.getChannelById(MessageChannel.class, channelId);
        this.guild = api.getGuildById(guildId);
        this.api = api;
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> resolve() {
        return this.resolve(true);
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> resolve(boolean update) {
        this.checkPermission(Permission.VIEW_CHANNEL);
        this.checkPermission(Permission.MESSAGE_HISTORY);
        if (this.channel == null) {
            throw new IllegalStateException("Cannot resolve a message without a channel present.");
        }
        JDAImpl jda = (JDAImpl)this.getJDA();
        Message referenced = this.getMessage();
        if (referenced != null && !update) {
            return new CompletedRestAction<Message>((JDA)jda, referenced);
        }
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE.compile(this.getChannelId(), this.getMessageId());
        return new RestActionImpl<Message>((JDA)jda, route, (response, request) -> {
            ReceivedMessage created = jda.getEntityBuilder().createMessageWithChannel(response.getObject(), this.channel, false);
            this.referencedMessage = created;
            return created;
        });
    }

    @Nullable
    public Message getMessage() {
        return this.referencedMessage;
    }

    @Nullable
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)this.channel;
    }

    @Nullable
    public Guild getGuild() {
        return this.guild;
    }

    public int getTypeRaw() {
        return this.type;
    }

    @Nonnull
    public MessageReferenceType getType() {
        return MessageReferenceType.fromId(this.type);
    }

    public long getMessageIdLong() {
        return this.messageId;
    }

    public long getChannelIdLong() {
        return this.channelId;
    }

    public long getGuildIdLong() {
        return this.guildId;
    }

    @Nonnull
    public String getMessageId() {
        return Long.toUnsignedString(this.getMessageIdLong());
    }

    @Nonnull
    public String getChannelId() {
        return Long.toUnsignedString(this.getChannelIdLong());
    }

    @Nonnull
    public String getGuildId() {
        return Long.toUnsignedString(this.getGuildIdLong());
    }

    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    private void checkPermission(Permission permission) {
        if (this.guild == null || !(this.channel instanceof GuildChannel)) {
            return;
        }
        Member selfMember = this.guild.getSelfMember();
        GuildChannel guildChannel = (GuildChannel)((Object)this.channel);
        Checks.checkAccess(selfMember, guildChannel);
        if (!selfMember.hasPermission(guildChannel, permission)) {
            throw new InsufficientPermissionException(guildChannel, permission);
        }
    }

    public static enum MessageReferenceType {
        DEFAULT(0),
        FORWARD(1),
        UNKNOWN(-1);

        private final int id;

        private MessageReferenceType(int id) {
            this.id = id;
        }

        @Nonnull
        public static MessageReferenceType fromId(int id) {
            for (MessageReferenceType type : MessageReferenceType.values()) {
                if (type.id != id) continue;
                return type;
            }
            return UNKNOWN;
        }

        public int getId() {
            return this.id;
        }
    }
}

