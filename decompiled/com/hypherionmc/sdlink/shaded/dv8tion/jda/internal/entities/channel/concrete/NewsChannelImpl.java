/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.NewsChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractStandardGuildMessageChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.NewsChannelManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public class NewsChannelImpl
extends AbstractStandardGuildMessageChannelImpl<NewsChannelImpl>
implements NewsChannel,
DefaultGuildChannelUnion {
    public NewsChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.NEWS;
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        return this.getGuild().getMembersView().stream().filter(m -> m.hasPermission((GuildChannel)this, Permission.VIEW_CHANNEL)).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public RestAction<Webhook.WebhookReference> follow(@Nonnull String targetChannelId) {
        Checks.notNull(targetChannelId, "Target Channel ID");
        Route.CompiledRoute route = Route.Channels.FOLLOW_CHANNEL.compile(this.getId());
        DataObject body = DataObject.empty().put("webhook_channel_id", targetChannelId);
        return new RestActionImpl<Webhook.WebhookReference>(this.getJDA(), route, body, (response, request) -> {
            DataObject json = response.getObject();
            return new Webhook.WebhookReference(request.getJDA(), json.getUnsignedLong("webhook_id"), json.getUnsignedLong("channel_id"));
        });
    }

    @Override
    @Nonnull
    public ChannelAction<NewsChannel> createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        ChannelAction<NewsChannel> action = guild.createNewsChannel(this.name).setNSFW(this.nsfw).setTopic(this.topic);
        if (guild.equals(this.getGuild())) {
            Category parent = this.getParentCategory();
            if (parent != null) {
                action.setParent(parent);
            }
            for (PermissionOverride o : this.overrides.valueCollection()) {
                if (o.isMemberOverride()) {
                    action.addMemberPermissionOverride(o.getIdLong(), o.getAllowedRaw(), o.getDeniedRaw());
                    continue;
                }
                action.addRolePermissionOverride(o.getIdLong(), o.getAllowedRaw(), o.getDeniedRaw());
            }
        }
        return action;
    }

    @Override
    @Nonnull
    public NewsChannelManager getManager() {
        return new NewsChannelManagerImpl(this);
    }
}

