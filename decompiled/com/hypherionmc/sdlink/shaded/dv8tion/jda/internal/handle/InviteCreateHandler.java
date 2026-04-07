/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.InviteImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import java.time.OffsetDateTime;
import java.util.Optional;

public class InviteCreateHandler
extends SocketHandler {
    public InviteCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        InviteImpl.InviteTargetImpl target;
        long guildId = content.getUnsignedLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        Guild realGuild = this.getJDA().getGuildById(guildId);
        if (realGuild == null) {
            EventCache.LOG.debug("Caching INVITE_CREATE for unknown guild with id {}", (Object)guildId);
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            return null;
        }
        long channelId = content.getUnsignedLong("channel_id");
        GuildChannel realChannel = realGuild.getGuildChannelById(channelId);
        if (realChannel == null) {
            EventCache.LOG.debug("Caching INVITE_CREATE for unknown channel with id {} in guild with id {}", (Object)channelId, (Object)guildId);
            this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
            return null;
        }
        String code = content.getString("code");
        boolean temporary = content.getBoolean("temporary");
        int maxAge = content.getInt("max_age", -1);
        int maxUses = content.getInt("max_uses", -1);
        OffsetDateTime creationTime = content.opt("created_at").map(String::valueOf).map(OffsetDateTime::parse).orElse(null);
        Optional<DataObject> inviterJson = content.optObject("inviter");
        boolean expanded = maxUses != -1;
        User inviter = inviterJson.map(json -> this.getJDA().getEntityBuilder().createUser((DataObject)json)).orElse(null);
        InviteImpl.ChannelImpl channel = new InviteImpl.ChannelImpl(realChannel);
        InviteImpl.GuildImpl guild = new InviteImpl.GuildImpl(realGuild);
        Invite.TargetType targetType = Invite.TargetType.fromId(content.getInt("target_type", 0));
        switch (targetType) {
            case STREAM: {
                DataObject targetUserObject = content.getObject("target_user");
                target = new InviteImpl.InviteTargetImpl(targetType, null, this.getJDA().getEntityBuilder().createUser(targetUserObject));
                break;
            }
            case EMBEDDED_APPLICATION: {
                DataObject applicationObject = content.getObject("target_application");
                InviteImpl.EmbeddedApplicationImpl application = new InviteImpl.EmbeddedApplicationImpl(applicationObject.getString("icon", null), applicationObject.getString("name"), applicationObject.getString("description"), applicationObject.getString("summary"), applicationObject.getLong("id"), applicationObject.getInt("max_participants", -1));
                target = new InviteImpl.InviteTargetImpl(targetType, application, null);
                break;
            }
            case NONE: {
                target = null;
                break;
            }
            default: {
                target = new InviteImpl.InviteTargetImpl(targetType, null, null);
            }
        }
        InviteImpl invite = new InviteImpl(this.getJDA(), code, expanded, inviter, maxAge, maxUses, temporary, creationTime, 0, channel, guild, null, target, Invite.InviteType.GUILD);
        this.getJDA().handleEvent(new GuildInviteCreateEvent((JDA)this.getJDA(), this.responseNumber, invite, realChannel));
        return null;
    }
}

