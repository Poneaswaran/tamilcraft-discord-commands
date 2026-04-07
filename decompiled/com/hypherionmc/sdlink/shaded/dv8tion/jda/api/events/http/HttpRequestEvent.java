/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.http;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.Headers;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import com.hypherionmc.sdlink.shaded.okhttp3.ResponseBody;
import java.util.Set;

public class HttpRequestEvent
extends Event {
    private final com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request<?> request;
    private final Response response;

    public HttpRequestEvent(@Nonnull com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request<?> request, @Nonnull Response response) {
        super(request.getJDA());
        this.request = request;
        this.response = response;
    }

    @Nonnull
    public com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request<?> getRequest() {
        return this.request;
    }

    @Nullable
    public RequestBody getRequestBody() {
        return this.request.getBody();
    }

    @Nullable
    public Object getRequestBodyRaw() {
        return this.request.getRawBody();
    }

    @Nullable
    public Headers getRequestHeaders() {
        return this.response.getRawResponse() == null ? null : this.response.getRawResponse().request().headers();
    }

    @Nullable
    public Request getRequestRaw() {
        return this.response.getRawResponse() == null ? null : this.response.getRawResponse().request();
    }

    @Nullable
    public Response getResponse() {
        return this.response;
    }

    @Nullable
    public ResponseBody getResponseBody() {
        return this.response.getRawResponse() == null ? null : this.response.getRawResponse().body();
    }

    @Nullable
    public DataArray getResponseBodyAsArray() {
        return this.response.getArray();
    }

    @Nullable
    public DataObject getResponseBodyAsObject() {
        return this.response.getObject();
    }

    @Nullable
    public String getResponseBodyAsString() {
        return this.response.getString();
    }

    @Nullable
    public Headers getResponseHeaders() {
        return this.response.getRawResponse() == null ? null : this.response.getRawResponse().headers();
    }

    @Nullable
    public com.hypherionmc.sdlink.shaded.okhttp3.Response getResponseRaw() {
        return this.response.getRawResponse();
    }

    @Nonnull
    public Set<String> getCFRays() {
        return this.response.getCFRays();
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<?> getRestAction() {
        return this.request.getRestAction();
    }

    @Nonnull
    public Route.CompiledRoute getRoute() {
        return this.request.getRoute();
    }

    public boolean isRateLimit() {
        return this.response.isRateLimit();
    }
}

