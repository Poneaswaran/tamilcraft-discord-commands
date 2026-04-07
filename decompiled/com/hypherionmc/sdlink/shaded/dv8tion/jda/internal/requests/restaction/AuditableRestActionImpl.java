/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.map.CaseInsensitiveMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ThreadLocalReason;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EncodingUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

public class AuditableRestActionImpl<T>
extends RestActionImpl<T>
implements AuditableRestAction<T> {
    protected String reason = null;

    public AuditableRestActionImpl(JDA api, Route.CompiledRoute route) {
        super(api, route);
    }

    public AuditableRestActionImpl(JDA api, Route.CompiledRoute route, RequestBody data) {
        super(api, route, data);
    }

    public AuditableRestActionImpl(JDA api, Route.CompiledRoute route, DataObject data) {
        super(api, route, data);
    }

    public AuditableRestActionImpl(JDA api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, handler);
    }

    public AuditableRestActionImpl(JDA api, Route.CompiledRoute route, DataObject data, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, data, handler);
    }

    public AuditableRestActionImpl(JDA api, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, data, handler);
    }

    @Override
    @Nonnull
    public AuditableRestAction<T> setCheck(BooleanSupplier checks) {
        return (AuditableRestAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public AuditableRestAction<T> timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (AuditableRestAction)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public AuditableRestAction<T> deadline(long timestamp) {
        return (AuditableRestAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AuditableRestActionImpl<T> reason(@Nullable String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    protected CaseInsensitiveMap<String, String> finalizeHeaders() {
        CaseInsensitiveMap<String, String> headers = super.finalizeHeaders();
        if (this.reason == null || this.reason.isEmpty()) {
            String localReason = ThreadLocalReason.getCurrent();
            if (localReason == null || localReason.isEmpty()) {
                return headers;
            }
            return this.generateHeaders(headers, localReason);
        }
        return this.generateHeaders(headers, this.reason);
    }

    @Nonnull
    private CaseInsensitiveMap<String, String> generateHeaders(CaseInsensitiveMap<String, String> headers, String reason) {
        if (headers == null) {
            headers = new CaseInsensitiveMap();
        }
        headers.put("X-Audit-Log-Reason", this.uriEncode(reason));
        return headers;
    }

    private String uriEncode(String input) {
        String formEncode = EncodingUtil.encodeUTF8(input);
        return formEncode.replace('+', ' ');
    }
}

