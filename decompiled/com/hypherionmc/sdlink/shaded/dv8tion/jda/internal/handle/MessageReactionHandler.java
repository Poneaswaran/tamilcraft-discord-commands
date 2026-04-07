/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.PrivateChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageReactionHandler
extends SocketHandler {
    private final boolean add;

    public MessageReactionHandler(JDAImpl api, boolean add) {
        super(api);
        this.add = add;
    }

    @Override
    protected Long handleInternally(DataObject content) {
        User user;
        if (!content.isNull("guild_id")) {
            long guildId = content.getLong("guild_id");
            if (this.api.getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
        }
        DataObject emoji = content.getObject("emoji");
        long userId = content.getLong("user_id");
        long messageId = content.getLong("message_id");
        long channelId = content.getLong("channel_id");
        Long emojiId = emoji.isNull("id") ? null : Long.valueOf(emoji.getLong("id"));
        String emojiName = emoji.getString("name", null);
        if (emojiId == null && emojiName == null) {
            WebSocketClient.LOG.debug("Received a reaction {} with no name nor id. json: {}", (Object)(this.add ? "add" : "remove"), (Object)content);
            return null;
        }
        long guildId = content.getUnsignedLong("guild_id", 0L);
        Guild guild = this.api.getGuildById(guildId);
        MemberImpl member = null;
        if (guild != null) {
            member = (MemberImpl)guild.getMemberById(userId);
            Optional<DataObject> memberJson = content.optObject("member");
            if (memberJson.isPresent()) {
                DataObject json = memberJson.get();
                if (member == null || !member.hasTimeJoined()) {
                    member = this.api.getEntityBuilder().createMember((GuildImpl)guild, json);
                } else {
                    List<Role> roles = json.getArray("roles").stream(DataArray::getUnsignedLong).map(guild::getRoleById).filter(Objects::nonNull).collect(Collectors.toList());
                    this.api.getEntityBuilder().updateMember((GuildImpl)guild, member, json, roles);
                }
                this.api.getEntityBuilder().updateMemberCache(member);
            }
            if (member == null && this.add && guild.isLoaded()) {
                WebSocketClient.LOG.debug("Dropping reaction event for unknown member {}", (Object)content);
                return null;
            }
        }
        if ((user = this.api.getUserById(userId)) == null && member != null) {
            user = member.getUser();
        }
        if (user == null && this.add && guild != null) {
            this.api.getEventCache().cache(EventCache.Type.USER, userId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received a reaction for a user that JDA does not currently have cached. UserID: {} ChannelId: {} MessageId: {}", new Object[]{userId, channelId, messageId});
            return null;
        }
        MessageChannel channel = this.api.getChannelById(MessageChannel.class, channelId);
        if (channel == null) {
            GuildChannel actual;
            if (guild != null && (actual = guild.getGuildChannelById(channelId)) != null) {
                WebSocketClient.LOG.debug("Dropping MESSAGE_REACTION event for unexpected channel of type {}", (Object)actual.getType());
                return null;
            }
            if (guildId != 0L) {
                this.api.getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Received a reaction for a channel that JDA does not currently have cached");
                return null;
            }
            channel = this.getJDA().getEntityBuilder().createPrivateChannel(DataObject.empty().put("id", channelId));
        }
        EmojiUnion rEmoji = EntityBuilder.createEmoji(emoji);
        boolean[] self = new boolean[]{false, false};
        MessageReaction reaction = new MessageReaction(this.api, channel, rEmoji, channelId, messageId, self, null);
        if (channel.getType() == ChannelType.PRIVATE) {
            this.api.usedPrivateChannel(reaction.getChannel().getIdLong());
            PrivateChannelImpl priv = (PrivateChannelImpl)channel;
            if (priv.getUser() == null && user != null) {
                priv.setUser(user);
            }
        }
        if (this.add) {
            this.api.handleEvent(new MessageReactionAddEvent(this.api, this.responseNumber, user, member, reaction, userId, content.getUnsignedLong("message_author_id", 0L)));
        } else {
            this.api.handleEvent(new MessageReactionRemoveEvent(this.api, this.responseNumber, user, member, reaction, userId));
        }
        return null;
    }
}

