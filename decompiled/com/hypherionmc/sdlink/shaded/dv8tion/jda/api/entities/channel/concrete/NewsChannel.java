/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.NewsChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface NewsChannel
extends StandardGuildMessageChannel {
    @Nonnull
    @CheckReturnValue
    public RestAction<Webhook.WebhookReference> follow(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Webhook.WebhookReference> follow(long targetChannelId) {
        return this.follow(Long.toUnsignedString(targetChannelId));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Webhook.WebhookReference> follow(@Nonnull TextChannel targetChannel) {
        Checks.notNull(targetChannel, "Target Channel");
        Member selfMember = targetChannel.getGuild().getSelfMember();
        Checks.checkAccess(selfMember, targetChannel);
        if (!selfMember.hasPermission((GuildChannel)targetChannel, Permission.MANAGE_WEBHOOKS)) {
            throw new InsufficientPermissionException(targetChannel, Permission.MANAGE_WEBHOOKS);
        }
        return this.follow(targetChannel.getId());
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Message> crosspostMessageById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId);
        Checks.checkAccess(this.getGuild().getSelfMember(), this);
        Route.CompiledRoute route = Route.Messages.CROSSPOST_MESSAGE.compile(this.getId(), messageId);
        return new RestActionImpl<Message>(this.getJDA(), route, (response, request) -> request.getJDA().getEntityBuilder().createMessageWithChannel(response.getObject(), this, false));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Message> crosspostMessageById(long messageId) {
        return this.crosspostMessageById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<NewsChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<NewsChannel> createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public NewsChannelManager getManager();
}

