/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.StageInstanceAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class StageInstanceActionImpl
extends RestActionImpl<StageInstance>
implements StageInstanceAction {
    private final StageChannel channel;
    private String topic;

    public StageInstanceActionImpl(StageChannel channel) {
        super(channel.getJDA(), Route.StageInstances.CREATE_INSTANCE.compile(new String[0]));
        this.channel = channel;
    }

    @Override
    @Nonnull
    public StageInstanceAction setCheck(BooleanSupplier checks) {
        return (StageInstanceAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public StageInstanceAction timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (StageInstanceAction)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public StageInstanceAction deadline(long timestamp) {
        return (StageInstanceAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public StageInstanceAction setTopic(@Nonnull String topic) {
        Checks.notBlank(topic, "Topic");
        Checks.notLonger(topic, 120, "Topic");
        this.topic = topic;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject body = DataObject.empty();
        body.put("channel_id", this.channel.getId());
        body.put("topic", this.topic);
        return this.getRequestBody(body);
    }

    @Override
    protected void handleSuccess(Response response, Request<StageInstance> request) {
        StageInstance instance = this.api.getEntityBuilder().createStageInstance((GuildImpl)this.channel.getGuild(), response.getObject());
        request.onSuccess(instance);
    }
}

