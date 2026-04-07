/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.Interaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.PrivateChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public class InteractionImpl
implements Interaction {
    protected final long id;
    protected final long channelId;
    protected final int type;
    protected final String token;
    protected final Guild guild;
    protected final Member member;
    protected final User user;
    protected final Channel channel;
    protected final DiscordLocale userLocale;
    protected final List<Entitlement> entitlements;
    protected final JDAImpl api;
    private boolean isAck;

    public InteractionImpl(JDAImpl jda, DataObject data) {
        this.api = jda;
        this.id = data.getUnsignedLong("id");
        this.token = data.getString("token");
        this.type = data.getInt("type");
        this.guild = jda.getGuildById(data.getUnsignedLong("guild_id", 0L));
        this.channelId = data.getUnsignedLong("channel_id", 0L);
        this.userLocale = DiscordLocale.from(data.getString("locale", "en-US"));
        DataObject channelJson = data.getObject("channel");
        if (this.guild != null) {
            this.member = jda.getEntityBuilder().createMember((GuildImpl)this.guild, data.getObject("member"));
            jda.getEntityBuilder().updateMemberCache((MemberImpl)this.member);
            this.user = this.member.getUser();
            GuildChannel channel = this.guild.getGuildChannelById(channelJson.getUnsignedLong("id"));
            if (channel == null && ChannelType.fromId(channelJson.getInt("type")).isThread()) {
                channel = this.api.getEntityBuilder().createThreadChannel((GuildImpl)this.guild, channelJson, this.guild.getIdLong(), false);
            }
            if (channel == null) {
                throw new IllegalStateException("Failed to create channel instance for interaction! Channel Type: " + channelJson.getInt("type"));
            }
            this.channel = channel;
        } else {
            this.member = null;
            long channelId = channelJson.getUnsignedLong("id");
            ChannelType type = ChannelType.fromId(channelJson.getInt("type"));
            if (type != ChannelType.PRIVATE) {
                throw new IllegalArgumentException("Received interaction in unexpected channel type! Type " + (Object)((Object)type) + " is not supported yet!");
            }
            PrivateChannel channel = jda.getPrivateChannelById(channelId);
            if (channel == null) {
                channel = jda.getEntityBuilder().createPrivateChannel(DataObject.empty().put("id", channelId).put("recipient", data.getObject("user")));
            }
            this.channel = channel;
            User user = channel.getUser();
            if (user == null) {
                user = jda.getEntityBuilder().createUser(data.getObject("user"));
                ((PrivateChannelImpl)channel).setUser(user);
                ((UserImpl)user).setPrivateChannel(channel);
            }
            this.user = user;
        }
        this.entitlements = data.optArray("entitlements").orElseGet(DataArray::empty).stream(DataArray::getObject).map(jda.getEntityBuilder()::createEntitlement).collect(Helpers.toUnmodifiableList());
    }

    public synchronized void releaseHook(boolean success) {
    }

    public synchronized boolean ack() {
        boolean wasAck = this.isAck;
        this.isAck = true;
        return wasAck;
    }

    @Override
    public synchronized boolean isAcknowledged() {
        return this.isAck;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    public int getTypeRaw() {
        return this.type;
    }

    @Override
    @Nonnull
    public String getToken() {
        return this.token;
    }

    @Override
    @Nullable
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nullable
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public long getChannelIdLong() {
        return this.channelId;
    }

    @Override
    @Nonnull
    public DiscordLocale getUserLocale() {
        return this.userLocale;
    }

    @Override
    @Nonnull
    public User getUser() {
        return this.user;
    }

    @Override
    @Nullable
    public Member getMember() {
        return this.member;
    }

    @Override
    @Nonnull
    public List<Entitlement> getEntitlements() {
        return this.entitlements;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }
}

