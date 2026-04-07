/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PaginationActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class ReactionPaginationActionImpl
extends PaginationActionImpl<User, ReactionPaginationAction>
implements ReactionPaginationAction {
    protected final MessageReaction reaction;

    public ReactionPaginationActionImpl(MessageReaction reaction) {
        super(reaction.getJDA(), Route.Messages.GET_REACTION_USERS.compile(reaction.getChannelId(), reaction.getMessageId(), ReactionPaginationActionImpl.getCode(reaction)), 1, 100, 100);
        super.order(PaginationAction.PaginationOrder.FORWARD);
        this.reaction = reaction;
    }

    public ReactionPaginationActionImpl(Message message, String code) {
        super(message.getJDA(), Route.Messages.GET_REACTION_USERS.compile(message.getChannelId(), message.getId(), code), 1, 100, 100);
        super.order(PaginationAction.PaginationOrder.FORWARD);
        this.reaction = null;
    }

    public ReactionPaginationActionImpl(MessageChannel channel, String messageId, String code) {
        super(channel.getJDA(), Route.Messages.GET_REACTION_USERS.compile(channel.getId(), messageId, code), 1, 100, 100);
        super.order(PaginationAction.PaginationOrder.FORWARD);
        this.reaction = null;
    }

    protected static String getCode(MessageReaction reaction) {
        return reaction.getEmoji().getAsReactionCode();
    }

    @Override
    @Nonnull
    public MessageReaction getReaction() {
        if (this.reaction == null) {
            throw new IllegalStateException("Cannot get reaction for this action");
        }
        return this.reaction;
    }

    @Override
    @Nonnull
    public EnumSet<PaginationAction.PaginationOrder> getSupportedOrders() {
        return EnumSet.of(PaginationAction.PaginationOrder.FORWARD);
    }

    @Override
    protected void handleSuccess(Response response, Request<List<User>> request) {
        EntityBuilder builder = this.api.getEntityBuilder();
        DataArray array = response.getArray();
        LinkedList<UserImpl> users = new LinkedList<UserImpl>();
        for (int i = 0; i < array.length(); ++i) {
            try {
                UserImpl user = builder.createUser(array.getObject(i));
                users.add(user);
                if (this.useCache) {
                    this.cached.add(user);
                }
                this.last = user;
                this.lastKey = ((User)this.last).getIdLong();
                continue;
            }
            catch (ParsingException | NullPointerException e) {
                LOG.warn("Encountered exception in ReactionPagination", (Throwable)e);
            }
        }
        request.onSuccess(users);
    }

    @Override
    protected long getKey(User it) {
        return it.getIdLong();
    }
}

