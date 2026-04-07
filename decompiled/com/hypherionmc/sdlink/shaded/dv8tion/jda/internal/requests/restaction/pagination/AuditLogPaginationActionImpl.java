/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ActionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogEntry;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PaginationActionImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AuditLogPaginationActionImpl
extends PaginationActionImpl<AuditLogEntry, AuditLogPaginationAction>
implements AuditLogPaginationAction {
    protected final Guild guild;
    protected ActionType type = null;
    protected String userId = null;

    public AuditLogPaginationActionImpl(Guild guild) {
        super(guild.getJDA(), Route.Guilds.GET_AUDIT_LOGS.compile(guild.getId()), 1, 100, 100);
        if (!guild.getSelfMember().hasPermission(Permission.VIEW_AUDIT_LOGS)) {
            throw new InsufficientPermissionException(guild, Permission.VIEW_AUDIT_LOGS);
        }
        this.guild = guild;
        super.order(PaginationAction.PaginationOrder.BACKWARD);
    }

    @Override
    @Nonnull
    public AuditLogPaginationActionImpl type(ActionType type) {
        this.type = type;
        return this;
    }

    @Override
    @Nonnull
    public AuditLogPaginationActionImpl user(UserSnowflake user) {
        this.userId = user == null ? null : user.getId();
        return this;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    public EnumSet<PaginationAction.PaginationOrder> getSupportedOrders() {
        return EnumSet.of(PaginationAction.PaginationOrder.BACKWARD, PaginationAction.PaginationOrder.FORWARD);
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        Route.CompiledRoute route = super.finalizeRoute();
        if (this.type != null) {
            route = route.withQueryParams("action_type", String.valueOf(this.type.getKey()));
        }
        if (this.userId != null) {
            route = route.withQueryParams("user_id", this.userId);
        }
        return route;
    }

    @Override
    protected void handleSuccess(Response response, Request<List<AuditLogEntry>> request) {
        int i;
        DataObject obj = response.getObject();
        DataArray users = obj.getArray("users");
        DataArray webhooks = obj.getArray("webhooks");
        DataArray entries = obj.getArray("audit_log_entries");
        ArrayList<AuditLogEntry> list = new ArrayList<AuditLogEntry>(entries.length());
        EntityBuilder builder = this.api.getEntityBuilder();
        TLongObjectHashMap<DataObject> userMap = new TLongObjectHashMap<DataObject>();
        for (int i2 = 0; i2 < users.length(); ++i2) {
            DataObject user = users.getObject(i2);
            userMap.put(user.getLong("id"), user);
        }
        TLongObjectHashMap<DataObject> webhookMap = new TLongObjectHashMap<DataObject>();
        for (i = 0; i < webhooks.length(); ++i) {
            DataObject webhook = webhooks.getObject(i);
            webhookMap.put(webhook.getLong("id"), webhook);
        }
        for (i = 0; i < entries.length(); ++i) {
            try {
                DataObject entry = entries.getObject(i);
                DataObject user = (DataObject)userMap.get(entry.getLong("user_id", 0L));
                DataObject webhook = (DataObject)webhookMap.get(entry.getLong("target_id", 0L));
                AuditLogEntry result = builder.createAuditLogEntry((GuildImpl)this.guild, entry, user, webhook);
                list.add(result);
                continue;
            }
            catch (ParsingException | NullPointerException e) {
                LOG.warn("Encountered exception in AuditLogPagination", (Throwable)e);
            }
        }
        if (!list.isEmpty()) {
            if (this.useCache) {
                this.cached.addAll(list);
            }
            this.last = list.get(list.size() - 1);
            this.lastKey = ((AuditLogEntry)this.last).getIdLong();
        }
        request.onSuccess(list);
    }

    @Override
    protected long getKey(AuditLogEntry it) {
        return it.getIdLong();
    }
}

