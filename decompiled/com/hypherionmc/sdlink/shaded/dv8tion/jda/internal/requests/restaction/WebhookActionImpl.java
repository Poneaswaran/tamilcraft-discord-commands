/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.WebhookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class WebhookActionImpl
extends AuditableRestActionImpl<Webhook>
implements WebhookAction {
    protected final IWebhookContainer channel;
    protected String name;
    protected Icon avatar = null;

    public WebhookActionImpl(JDA api, IWebhookContainer channel, String name) {
        super(api, Route.Channels.CREATE_WEBHOOK.compile(channel.getId()));
        this.channel = channel;
        this.name = name;
    }

    @Override
    @Nonnull
    public WebhookActionImpl setCheck(BooleanSupplier checks) {
        return (WebhookActionImpl)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public WebhookActionImpl timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (WebhookActionImpl)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public WebhookActionImpl deadline(long timestamp) {
        return (WebhookActionImpl)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public IWebhookContainerUnion getChannel() {
        return (IWebhookContainerUnion)this.channel;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookActionImpl setName(@Nonnull String name) {
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        this.name = name;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookActionImpl setAvatar(Icon icon) {
        this.avatar = icon;
        return this;
    }

    @Override
    public RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        object.put("name", this.name);
        object.put("avatar", this.avatar != null ? this.avatar.getEncoding() : null);
        return this.getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<Webhook> request) {
        DataObject json = response.getObject();
        WebhookImpl webhook = this.api.getEntityBuilder().createWebhook(json);
        request.onSuccess(webhook);
    }
}

