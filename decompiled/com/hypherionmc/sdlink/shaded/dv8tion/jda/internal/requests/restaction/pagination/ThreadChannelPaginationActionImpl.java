/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IThreadContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ThreadChannelPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ThreadChannelPaginationActionImpl
extends PaginationActionImpl<ThreadChannel, ThreadChannelPaginationAction>
implements ThreadChannelPaginationAction {
    protected final IThreadContainer channel;
    protected final boolean useID;

    public ThreadChannelPaginationActionImpl(JDA api, Route.CompiledRoute route, IThreadContainer channel, boolean useID) {
        super(api, route, 2, 100, 100);
        this.channel = channel;
        this.useID = useID;
    }

    @Override
    @Nonnull
    public IThreadContainerUnion getChannel() {
        return (IThreadContainerUnion)this.channel;
    }

    @Override
    @Nonnull
    public EnumSet<PaginationAction.PaginationOrder> getSupportedOrders() {
        return EnumSet.of(PaginationAction.PaginationOrder.BACKWARD);
    }

    @Override
    @Nonnull
    protected String getPaginationLastEvaluatedKey(long lastId, ThreadChannel last) {
        if (this.useID) {
            return Long.toUnsignedString(lastId);
        }
        if (this.order == PaginationAction.PaginationOrder.FORWARD && lastId == 0L) {
            return "2015-01-01T00:00:00.000";
        }
        if (last == null) {
            return OffsetDateTime.now(ZoneOffset.UTC).toString();
        }
        return last.getTimeArchiveInfoLastModified().toString();
    }

    @Override
    protected void handleSuccess(Response response, Request<List<ThreadChannel>> request) {
        DataObject obj = response.getObject();
        DataArray selfThreadMembers = obj.getArray("members");
        DataArray threads = obj.getArray("threads");
        ArrayList<ThreadChannel> list = new ArrayList<ThreadChannel>(threads.length());
        EntityBuilder builder = this.api.getEntityBuilder();
        TLongObjectMap<DataObject> selfThreadMemberMap = Helpers.convertToMap(o -> o.getUnsignedLong("id"), selfThreadMembers);
        for (int i = 0; i < threads.length(); ++i) {
            try {
                DataObject threadObj = threads.getObject(i);
                DataObject selfThreadMemberObj = selfThreadMemberMap.get(threadObj.getLong("id", 0L));
                if (selfThreadMemberObj != null) {
                    threadObj.put("member", selfThreadMemberObj);
                }
                try {
                    ThreadChannel thread = builder.createThreadChannel(threadObj, this.getGuild().getIdLong());
                    list.add(thread);
                    if (this.useCache) {
                        this.cached.add(thread);
                    }
                    this.last = thread;
                    this.lastKey = ((ThreadChannel)this.last).getIdLong();
                }
                catch (Exception e) {
                    if ("MISSING_CHANNEL".equals(e.getMessage())) {
                        EntityBuilder.LOG.debug("Discarding thread without cached parent channel. JSON: {}", (Object)threadObj);
                        continue;
                    }
                    EntityBuilder.LOG.warn("Failed to create thread channel. JSON: {}", (Object)threadObj, (Object)e);
                }
                continue;
            }
            catch (ParsingException | NullPointerException e) {
                LOG.warn("Encountered exception in ThreadChannelPagination", (Throwable)e);
            }
        }
        request.onSuccess(list);
    }

    @Override
    protected long getKey(ThreadChannel it) {
        return it.getIdLong();
    }
}

