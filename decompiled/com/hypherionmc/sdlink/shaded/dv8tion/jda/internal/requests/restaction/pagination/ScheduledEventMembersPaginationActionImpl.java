/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ScheduledEventMembersPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PaginationActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScheduledEventMembersPaginationActionImpl
extends PaginationActionImpl<Member, ScheduledEventMembersPaginationAction>
implements ScheduledEventMembersPaginationAction {
    protected final Guild guild;

    public ScheduledEventMembersPaginationActionImpl(ScheduledEvent event) {
        super(event.getGuild().getJDA(), Route.Guilds.GET_SCHEDULED_EVENT_USERS.compile(event.getGuild().getId(), event.getId()).withQueryParams("with_member", "true"), 1, 100, 100);
        this.guild = event.getGuild();
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    protected void handleSuccess(Response response, Request<List<Member>> request) {
        DataArray array = response.getArray();
        ArrayList<MemberImpl> members = new ArrayList<MemberImpl>(array.length());
        EntityBuilder builder = this.api.getEntityBuilder();
        for (int i = 0; i < array.length(); ++i) {
            try {
                DataObject object = array.getObject(i);
                if (object.isNull("member")) continue;
                DataObject userObject = object.getObject("user");
                DataObject memberObject = object.getObject("member");
                MemberImpl member = builder.createMember((GuildImpl)this.guild, memberObject.put("user", userObject));
                members.add(member);
                continue;
            }
            catch (ParsingException | NullPointerException e) {
                LOG.warn("Encountered an exception in ScheduledEventPagination", (Throwable)e);
            }
        }
        if (this.order == PaginationAction.PaginationOrder.BACKWARD) {
            Collections.reverse(members);
        }
        if (this.useCache) {
            this.cached.addAll(members);
        }
        if (!members.isEmpty()) {
            this.last = members.get(members.size() - 1);
            this.lastKey = ((Member)this.last).getIdLong();
        }
        request.onSuccess(members);
    }

    @Override
    protected long getKey(Member it) {
        return it.getIdLong();
    }
}

