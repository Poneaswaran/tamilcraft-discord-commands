/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.map.CaseInsensitiveMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;

public class RestFuture<T>
extends CompletableFuture<T> {
    final Request<T> request;

    public RestFuture(RestActionImpl<T> restAction, boolean shouldQueue, BooleanSupplier checks, RequestBody data, Object rawData, long deadline, boolean priority, Route.CompiledRoute route, CaseInsensitiveMap<String, String> headers) {
        this.request = new Request<Object>(restAction, this::complete, this::completeExceptionally, checks, shouldQueue, data, rawData, deadline, priority, route, headers);
        ((JDAImpl)restAction.getJDA()).getRequester().request(this.request);
    }

    public RestFuture(T t) {
        this.complete(t);
        this.request = null;
    }

    public RestFuture(Throwable t) {
        this.completeExceptionally(t);
        this.request = null;
    }

    @Override
    public boolean cancel(boolean mayInterrupt) {
        if (this.request != null) {
            this.request.cancel();
        }
        return !this.isDone() && !this.isCancelled() && super.cancel(mayInterrupt);
    }
}

