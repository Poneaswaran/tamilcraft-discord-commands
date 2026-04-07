/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PollVotersPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PaginationActionImpl;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class PollVotersPaginationActionImpl
extends PaginationActionImpl<User, PollVotersPaginationAction>
implements PollVotersPaginationAction {
    public PollVotersPaginationActionImpl(JDA jda, String channelId, String messageId, long answerId) {
        super(jda, Route.Messages.GET_POLL_ANSWER_VOTERS.compile(channelId, messageId, Long.toString(answerId)), 1, 100, 100);
        this.order = PaginationAction.PaginationOrder.FORWARD;
    }

    @Override
    @NotNull
    public EnumSet<PaginationAction.PaginationOrder> getSupportedOrders() {
        return EnumSet.of(PaginationAction.PaginationOrder.FORWARD);
    }

    @Override
    protected long getKey(User it) {
        return it.getIdLong();
    }

    @Override
    protected void handleSuccess(Response response, Request<List<User>> request) {
        DataArray array = response.getObject().getArray("users");
        ArrayList<UserImpl> users = new ArrayList<UserImpl>(array.length());
        EntityBuilder builder = this.api.getEntityBuilder();
        for (int i = 0; i < array.length(); ++i) {
            try {
                DataObject object = array.getObject(i);
                users.add(builder.createUser(object));
                continue;
            }
            catch (ParsingException | NullPointerException e) {
                LOG.warn("Encountered an exception in PollVotersPaginationAction", (Throwable)e);
            }
        }
        if (!users.isEmpty()) {
            if (this.useCache) {
                this.cached.addAll(users);
            }
            this.last = users.get(users.size() - 1);
            this.lastKey = ((User)this.last).getIdLong();
        }
        request.onSuccess(users);
    }
}

