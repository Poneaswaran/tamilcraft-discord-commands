/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.InviteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class InviteActionImpl
extends AuditableRestActionImpl<Invite>
implements InviteAction {
    private Integer maxAge = null;
    private Integer maxUses = null;
    private Boolean temporary = null;
    private Boolean unique = null;
    private Long targetApplication = null;
    private Long targetUser = null;
    private Invite.TargetType targetType = null;

    public InviteActionImpl(JDA api, String channelId) {
        super(api, Route.Invites.CREATE_INVITE.compile(channelId));
    }

    @Override
    @Nonnull
    public InviteActionImpl setCheck(BooleanSupplier checks) {
        return (InviteActionImpl)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public InviteActionImpl timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (InviteActionImpl)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public InviteActionImpl deadline(long timestamp) {
        return (InviteActionImpl)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteActionImpl setMaxAge(Integer maxAge) {
        if (maxAge != null) {
            Checks.notNegative(maxAge, "maxAge");
        }
        this.maxAge = maxAge;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteActionImpl setMaxAge(Long maxAge, @Nonnull TimeUnit timeUnit) {
        if (maxAge == null) {
            return this.setMaxAge(null);
        }
        Checks.notNegative(maxAge, "maxAge");
        Checks.notNull((Object)timeUnit, "timeUnit");
        return this.setMaxAge(Math.toIntExact(timeUnit.toSeconds(maxAge)));
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteActionImpl setMaxUses(Integer maxUses) {
        if (maxUses != null) {
            Checks.notNegative(maxUses, "maxUses");
        }
        this.maxUses = maxUses;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteActionImpl setTemporary(Boolean temporary) {
        this.temporary = temporary;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteActionImpl setUnique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    @Override
    @Nonnull
    public InviteAction setTargetApplication(long applicationId) {
        if (applicationId == 0L) {
            this.targetType = null;
            this.targetApplication = null;
            return this;
        }
        this.targetType = Invite.TargetType.EMBEDDED_APPLICATION;
        this.targetApplication = applicationId;
        return this;
    }

    @Override
    @Nonnull
    public InviteAction setTargetStream(long userId) {
        if (userId == 0L) {
            this.targetType = null;
            this.targetUser = null;
            return this;
        }
        this.targetType = Invite.TargetType.STREAM;
        this.targetUser = userId;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        if (this.maxAge != null) {
            object.put("max_age", this.maxAge);
        }
        if (this.maxUses != null) {
            object.put("max_uses", this.maxUses);
        }
        if (this.temporary != null) {
            object.put("temporary", this.temporary);
        }
        if (this.unique != null) {
            object.put("unique", this.unique);
        }
        if (this.targetType != null) {
            object.put("target_type", this.targetType.getId());
        }
        if (this.targetUser != null) {
            object.put("target_user_id", this.targetUser);
        }
        if (this.targetApplication != null) {
            object.put("target_application_id", this.targetApplication);
        }
        return this.getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<Invite> request) {
        request.onSuccess(this.api.getEntityBuilder().createInvite(response.getObject()));
    }
}

