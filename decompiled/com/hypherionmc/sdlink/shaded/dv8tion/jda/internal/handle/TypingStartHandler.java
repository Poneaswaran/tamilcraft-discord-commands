/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.UserTypingEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TypingStartHandler
extends SocketHandler {
    public TypingStartHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        GuildImpl guild = null;
        if (!content.isNull("guild_id")) {
            long guildId = content.getUnsignedLong("guild_id");
            guild = (GuildImpl)this.getJDA().getGuildById(guildId);
            if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
            if (guild == null) {
                return null;
            }
        }
        long channelId = content.getLong("channel_id");
        MessageChannel channel = this.getJDA().getChannelById(MessageChannel.class, channelId);
        if (channel == null) {
            return null;
        }
        long userId = content.getLong("user_id");
        MemberImpl member = null;
        User user = channel instanceof PrivateChannel ? ((PrivateChannel)channel).getUser() : (User)this.getJDA().getUsersView().get(userId);
        if (!content.isNull("member")) {
            EntityBuilder entityBuilder = this.getJDA().getEntityBuilder();
            member = entityBuilder.createMember(guild, content.getObject("member"));
            entityBuilder.updateMemberCache(member);
            user = member.getUser();
        }
        if (user == null) {
            return null;
        }
        OffsetDateTime timestamp = Instant.ofEpochSecond(content.getInt("timestamp")).atOffset(ZoneOffset.UTC);
        this.getJDA().handleEvent(new UserTypingEvent(this.getJDA(), this.responseNumber, user, channel, timestamp, member));
        return null;
    }
}

