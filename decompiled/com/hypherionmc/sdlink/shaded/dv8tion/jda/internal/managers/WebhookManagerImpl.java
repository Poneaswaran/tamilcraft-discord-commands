/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.WebhookManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;

public class WebhookManagerImpl
extends ManagerBase<WebhookManager>
implements WebhookManager {
    protected final Webhook webhook;
    protected String name;
    protected String channel;
    protected Icon avatar;

    public WebhookManagerImpl(Webhook webhook) {
        super(webhook.getJDA(), Route.Webhooks.MODIFY_WEBHOOK.compile(webhook.getId()));
        this.webhook = webhook;
        if (WebhookManagerImpl.isPermissionChecksEnabled()) {
            this.checkPermissions();
        }
    }

    @Override
    @Nonnull
    public Webhook getWebhook() {
        return this.webhook;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManagerImpl reset(long fields) {
        super.reset(fields);
        if ((fields & 1L) == 1L) {
            this.name = null;
        }
        if ((fields & 2L) == 2L) {
            this.channel = null;
        }
        if ((fields & 4L) == 4L) {
            this.avatar = null;
        }
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManagerImpl reset(long ... fields) {
        super.reset(fields);
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManagerImpl reset() {
        super.reset();
        this.name = null;
        this.channel = null;
        this.avatar = null;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManagerImpl setName(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        this.name = name;
        this.set |= 1L;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManagerImpl setAvatar(Icon icon) {
        this.avatar = icon;
        this.set |= 4L;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManagerImpl setChannel(@Nonnull TextChannel channel) {
        Checks.notNull(channel, "Channel");
        Checks.check(channel.getGuild().equals(this.getGuild()), "Channel is not from the same guild");
        this.channel = channel.getId();
        this.set |= 2L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject data = DataObject.empty();
        if (this.shouldUpdate(1L)) {
            data.put("name", this.name);
        }
        if (this.shouldUpdate(2L)) {
            data.put("channel_id", this.channel);
        }
        if (this.shouldUpdate(4L)) {
            data.put("avatar", this.avatar == null ? null : this.avatar.getEncoding());
        }
        return this.getRequestBody(data);
    }

    @Override
    protected boolean checkPermissions() {
        Member selfMember = this.getGuild().getSelfMember();
        IWebhookContainerUnion guildChannel = this.getChannel();
        Checks.checkAccess(selfMember, guildChannel);
        if (!selfMember.hasPermission((GuildChannel)guildChannel, Permission.MANAGE_WEBHOOKS)) {
            throw new InsufficientPermissionException(guildChannel, Permission.MANAGE_WEBHOOKS);
        }
        return super.checkPermissions();
    }
}

