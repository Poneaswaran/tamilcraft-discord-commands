/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.StageInstanceManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;

public class StageInstanceManagerImpl
extends ManagerBase<StageInstanceManager>
implements StageInstanceManager {
    private final StageInstance instance;
    private String topic;

    public StageInstanceManagerImpl(StageInstance instance) {
        super(instance.getChannel().getJDA(), Route.StageInstances.UPDATE_INSTANCE.compile(instance.getChannel().getId()));
        this.instance = instance;
    }

    @Override
    @Nonnull
    public StageInstance getStageInstance() {
        return this.instance;
    }

    @Override
    @Nonnull
    public StageInstanceManager setTopic(@Nullable String topic) {
        if (topic != null) {
            topic = topic.trim();
            Checks.notLonger(topic, 120, "Topic");
            if (topic.isEmpty()) {
                topic = null;
            }
        }
        this.topic = topic;
        this.set |= 1L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject body = DataObject.empty();
        if (this.shouldUpdate(1L) && this.topic != null) {
            body.put("topic", this.topic);
        }
        return this.getRequestBody(body);
    }
}

