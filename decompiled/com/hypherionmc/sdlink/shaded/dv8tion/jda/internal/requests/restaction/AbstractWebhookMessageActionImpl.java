/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.TriggerRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

public abstract class AbstractWebhookMessageActionImpl<T, R extends AbstractWebhookMessageActionImpl<T, R>>
extends TriggerRestAction<T> {
    protected String threadId;

    public AbstractWebhookMessageActionImpl(JDA api, Route.CompiledRoute route) {
        super(api, route);
    }

    public AbstractWebhookMessageActionImpl(JDA api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, handler);
    }

    @Nonnull
    public R setThreadId(@Nullable String threadId) {
        if (threadId != null) {
            Checks.isSnowflake(threadId, "Thread ID");
        }
        this.threadId = threadId;
        return (R)this;
    }

    @Nonnull
    public R setCheck(BooleanSupplier checks) {
        return (R)((AbstractWebhookMessageActionImpl)super.setCheck(checks));
    }

    @Nonnull
    public R deadline(long timestamp) {
        return (R)((AbstractWebhookMessageActionImpl)super.deadline(timestamp));
    }
}

