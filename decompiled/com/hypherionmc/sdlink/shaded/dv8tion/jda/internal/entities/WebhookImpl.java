/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.WebhookManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageDeleteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageRetrieveAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.AbstractWebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.WebhookManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageEditActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class WebhookImpl
extends AbstractWebhookClient<Message>
implements Webhook {
    private final IWebhookContainer channel;
    private final WebhookType type;
    private Member owner;
    private User user;
    private User ownerUser;
    private Webhook.ChannelReference sourceChannel;
    private Webhook.GuildReference sourceGuild;

    public WebhookImpl(IWebhookContainer channel, long id, WebhookType type) {
        this(channel, channel.getJDA(), id, type);
    }

    public WebhookImpl(IWebhookContainer channel, JDA api, long id, WebhookType type) {
        super(id, null, api);
        this.channel = channel;
        this.type = type;
    }

    @Override
    @Nonnull
    public WebhookType getType() {
        return this.type;
    }

    @Override
    public boolean isPartial() {
        return this.channel == null;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        if (this.channel == null) {
            throw new IllegalStateException("Cannot provide guild for this Webhook instance because it does not belong to this shard");
        }
        return this.getChannel().getGuild();
    }

    @Override
    @Nonnull
    public IWebhookContainerUnion getChannel() {
        if (this.channel == null) {
            throw new IllegalStateException("Cannot provide channel for this Webhook instance because it does not belong to this shard");
        }
        return (IWebhookContainerUnion)this.channel;
    }

    @Override
    public Member getOwner() {
        if (this.owner == null && this.channel != null && this.ownerUser != null) {
            return this.getGuild().getMember(this.ownerUser);
        }
        return this.owner;
    }

    @Override
    public User getOwnerAsUser() {
        return this.ownerUser;
    }

    @Override
    @Nonnull
    public User getDefaultUser() {
        return this.user;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.user.getName();
    }

    @Override
    @Nonnull
    public String getUrl() {
        return "https://discord.com/api/v10/webhooks/" + this.getId() + (this.getToken() == null ? "" : "/" + this.getToken());
    }

    @Override
    public Webhook.ChannelReference getSourceChannel() {
        return this.sourceChannel;
    }

    @Override
    public Webhook.GuildReference getSourceGuild() {
        return this.sourceGuild;
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        if (this.token != null) {
            return this.delete(this.token);
        }
        if (!this.getGuild().getSelfMember().hasPermission((GuildChannel)this.getChannel(), Permission.MANAGE_WEBHOOKS)) {
            throw new InsufficientPermissionException(this.getChannel(), Permission.MANAGE_WEBHOOKS);
        }
        Route.CompiledRoute route = Route.Webhooks.DELETE_WEBHOOK.compile(this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete(@Nonnull String token) {
        Checks.notNull(token, "Token");
        Route.CompiledRoute route = Route.Webhooks.DELETE_TOKEN_WEBHOOK.compile(this.getId(), token);
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public WebhookManager getManager() {
        return new WebhookManagerImpl(this);
    }

    @Override
    public WebhookMessageCreateActionImpl<Message> sendRequest() {
        this.checkToken();
        AbstractWebhookClient client = (AbstractWebhookClient)((Object)WebhookClient.createClient(this.api, this.getId(), this.token));
        return client.sendRequest();
    }

    @Override
    public WebhookMessageEditActionImpl<Message> editRequest(String messageId) {
        this.checkToken();
        AbstractWebhookClient client = (AbstractWebhookClient)((Object)WebhookClient.createClient(this.api, this.getId(), this.token));
        return client.editRequest(messageId);
    }

    @Override
    @Nonnull
    public WebhookMessageDeleteAction deleteMessageById(@Nonnull String messageId) {
        this.checkToken();
        return WebhookClient.createClient(this.api, this.getId(), this.token).deleteMessageById(messageId);
    }

    @Override
    @Nonnull
    public WebhookMessageRetrieveAction retrieveMessageById(@Nonnull String messageId) {
        this.checkToken();
        return WebhookClient.createClient(this.api, this.getId(), this.token).retrieveMessageById(messageId);
    }

    private void checkToken() {
        if (this.token == null) {
            throw new UnsupportedOperationException("Cannot execute webhook without a token!");
        }
    }

    public WebhookImpl setOwner(Member member, User user) {
        this.owner = member;
        this.ownerUser = user;
        return this;
    }

    public WebhookImpl setToken(String token) {
        this.token = token;
        return this;
    }

    public WebhookImpl setUser(User user) {
        this.user = user;
        return this;
    }

    public WebhookImpl setSourceGuild(Webhook.GuildReference reference) {
        this.sourceGuild = reference;
        return this;
    }

    public WebhookImpl setSourceChannel(Webhook.ChannelReference reference) {
        this.sourceChannel = reference;
        return this;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WebhookImpl)) {
            return false;
        }
        WebhookImpl impl = (WebhookImpl)obj;
        return impl.id == this.id;
    }

    public String toString() {
        return new EntityString(this).setName(this.getName()).toString();
    }
}

