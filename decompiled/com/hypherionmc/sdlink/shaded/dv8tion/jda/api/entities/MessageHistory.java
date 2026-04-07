/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.map.ListOrderedMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;

public class MessageHistory {
    protected final MessageChannel channel;
    protected static final Logger LOG = JDALogger.getLog(MessageHistory.class);
    protected final ListOrderedMap<Long, Message> history = new ListOrderedMap();

    public MessageHistory(@Nonnull MessageChannel channel) {
        Checks.notNull(channel, "Channel");
        this.channel = channel;
        if (channel instanceof GuildChannel) {
            GuildChannel guildChannel = (GuildChannel)((Object)channel);
            Member selfMember = guildChannel.getGuild().getSelfMember();
            Checks.checkAccess(selfMember, guildChannel);
            if (!selfMember.hasPermission(guildChannel, Permission.MESSAGE_HISTORY)) {
                throw new InsufficientPermissionException(guildChannel, Permission.MESSAGE_HISTORY);
            }
        }
    }

    @Nonnull
    public JDA getJDA() {
        return this.channel.getJDA();
    }

    public int size() {
        return this.history.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Nonnull
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)this.channel;
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<Message>> retrievePast(int amount) {
        if (amount > 100 || amount < 1) {
            throw new IllegalArgumentException("Message retrieval limit is between 1 and 100 messages. No more, no less. Limit provided: " + amount);
        }
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE_HISTORY.compile(this.channel.getId()).withQueryParams("limit", Integer.toString(amount));
        if (!this.history.isEmpty()) {
            route = route.withQueryParams("before", String.valueOf(this.history.lastKey()));
        }
        JDAImpl jda = (JDAImpl)this.getJDA();
        return new RestActionImpl<List<Message>>((JDA)jda, route, (response, request) -> {
            EntityBuilder builder = jda.getEntityBuilder();
            LinkedList<ReceivedMessage> messages = new LinkedList<ReceivedMessage>();
            DataArray historyJson = response.getArray();
            for (int i = 0; i < historyJson.length(); ++i) {
                try {
                    messages.add(builder.createMessageWithChannel(historyJson.getObject(i), this.channel, false));
                    continue;
                }
                catch (Exception e) {
                    LOG.warn("Encountered exception when retrieving messages ", (Throwable)e);
                }
            }
            messages.forEach(msg -> this.history.put(msg.getIdLong(), (Message)msg));
            return messages;
        });
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<Message>> retrieveFuture(int amount) {
        if (amount > 100 || amount < 1) {
            throw new IllegalArgumentException("Message retrieval limit is between 1 and 100 messages. No more, no less. Limit provided: " + amount);
        }
        if (this.history.isEmpty()) {
            throw new IllegalStateException("No messages have been retrieved yet, so there is no message to act as a marker to retrieve more recent messages based on.");
        }
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE_HISTORY.compile(this.channel.getId()).withQueryParams("limit", Integer.toString(amount), "after", String.valueOf(this.history.firstKey()));
        JDAImpl jda = (JDAImpl)this.getJDA();
        return new RestActionImpl<List<Message>>((JDA)jda, route, (response, request) -> {
            EntityBuilder builder = jda.getEntityBuilder();
            LinkedList<ReceivedMessage> messages = new LinkedList<ReceivedMessage>();
            DataArray historyJson = response.getArray();
            for (int i = 0; i < historyJson.length(); ++i) {
                try {
                    messages.add(builder.createMessageWithChannel(historyJson.getObject(i), this.channel, false));
                    continue;
                }
                catch (Exception e) {
                    LOG.warn("Encountered exception when retrieving messages ", (Throwable)e);
                }
            }
            Iterator it = messages.descendingIterator();
            while (it.hasNext()) {
                Message m = (Message)it.next();
                this.history.put(0, m.getIdLong(), m);
            }
            return messages;
        });
    }

    @Nonnull
    public @Unmodifiable List<Message> getRetrievedHistory() {
        int size = this.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        if (size == 1) {
            return Collections.singletonList(this.history.getValue(0));
        }
        return Collections.unmodifiableList(new ArrayList<Message>(this.history.values()));
    }

    @Nullable
    public Message getMessageById(@Nonnull String id) {
        return this.getMessageById(MiscUtil.parseSnowflake(id));
    }

    @Nullable
    public Message getMessageById(long id) {
        return (Message)this.history.get(id);
    }

    @Nonnull
    @CheckReturnValue
    public static MessageRetrieveAction getHistoryAfter(@Nonnull MessageChannel channel, @Nonnull String messageId) {
        MessageHistory.checkArguments(channel, messageId);
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE_HISTORY.compile(channel.getId()).withQueryParams("after", messageId);
        return new MessageRetrieveAction(route, channel);
    }

    @Nonnull
    @CheckReturnValue
    public static MessageRetrieveAction getHistoryBefore(@Nonnull MessageChannel channel, @Nonnull String messageId) {
        MessageHistory.checkArguments(channel, messageId);
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE_HISTORY.compile(channel.getId()).withQueryParams("before", messageId);
        return new MessageRetrieveAction(route, channel);
    }

    @Nonnull
    @CheckReturnValue
    public static MessageRetrieveAction getHistoryAround(@Nonnull MessageChannel channel, @Nonnull String messageId) {
        MessageHistory.checkArguments(channel, messageId);
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE_HISTORY.compile(channel.getId()).withQueryParams("around", messageId);
        return new MessageRetrieveAction(route, channel);
    }

    @Nonnull
    @CheckReturnValue
    public static MessageRetrieveAction getHistoryFromBeginning(@Nonnull MessageChannel channel) {
        return MessageHistory.getHistoryAfter(channel, "0");
    }

    private static void checkArguments(MessageChannel channel, String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notNull(channel, "Channel");
        if (channel instanceof GuildChannel) {
            GuildChannel guildChannel = (GuildChannel)((Object)channel);
            Member selfMember = guildChannel.getGuild().getSelfMember();
            Checks.checkAccess(selfMember, guildChannel);
            if (!selfMember.hasPermission(guildChannel, Permission.MESSAGE_HISTORY)) {
                throw new InsufficientPermissionException(guildChannel, Permission.MESSAGE_HISTORY);
            }
        }
    }

    public static class MessageRetrieveAction
    extends RestActionImpl<MessageHistory> {
        private final MessageChannel channel;
        private Integer limit;

        protected MessageRetrieveAction(Route.CompiledRoute route, MessageChannel channel) {
            super(channel.getJDA(), route);
            this.channel = channel;
        }

        @Nonnull
        @CheckReturnValue
        public MessageRetrieveAction limit(@Nullable Integer limit) {
            if (limit != null) {
                Checks.positive(limit, "Limit");
                Checks.check(limit <= 100, "Limit may not exceed 100!");
            }
            this.limit = limit;
            return this;
        }

        @Override
        protected Route.CompiledRoute finalizeRoute() {
            Route.CompiledRoute route = super.finalizeRoute();
            return this.limit == null ? route : route.withQueryParams("limit", String.valueOf(this.limit));
        }

        @Override
        protected void handleSuccess(Response response, Request<MessageHistory> request) {
            MessageHistory result = new MessageHistory(this.channel);
            DataArray array = response.getArray();
            EntityBuilder builder = this.api.getEntityBuilder();
            for (int i = 0; i < array.length(); ++i) {
                try {
                    DataObject obj = array.getObject(i);
                    result.history.put(obj.getLong("id"), builder.createMessageWithChannel(obj, this.channel, false));
                    continue;
                }
                catch (Exception e) {
                    LOG.warn("Encountered exception in MessagePagination", (Throwable)e);
                }
            }
            request.onSuccess(result);
        }
    }
}

