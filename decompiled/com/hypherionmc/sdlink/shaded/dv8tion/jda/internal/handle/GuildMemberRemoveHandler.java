/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildVoiceStateImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberPresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.VoiceChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.AudioChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;

public class GuildMemberRemoveHandler
extends SocketHandler {
    public GuildMemberRemoveHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long id = content.getLong("guild_id");
        boolean setup = this.getJDA().getGuildSetupController().onRemoveMember(id, content);
        if (setup) {
            return null;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildsView().get(id);
        if (guild == null) {
            return null;
        }
        long userId = content.getObject("user").getUnsignedLong("id");
        if (userId == this.getJDA().getSelfUser().getIdLong()) {
            return null;
        }
        guild.onMemberRemove();
        CacheView.SimpleCacheView<MemberPresenceImpl> presences = guild.getPresenceView();
        if (presences != null) {
            presences.remove(userId);
        }
        UserImpl user = this.api.getEntityBuilder().createUser(content.getObject("user"));
        MemberImpl member = (MemberImpl)guild.getMembersView().remove(userId);
        if (member == null) {
            guild.getVoiceChannelCache().forEachUnordered(channel -> {
                VoiceChannelImpl impl = (VoiceChannelImpl)channel;
                Member connected = impl.getConnectedMembersMap().remove(userId);
                if (connected != null) {
                    this.getJDA().handleEvent(new GuildVoiceUpdateEvent(this.getJDA(), this.responseNumber, connected, (AudioChannel)channel));
                }
            });
            this.getJDA().handleEvent(new GuildMemberRemoveEvent(this.getJDA(), this.responseNumber, guild, user, null));
            return null;
        }
        GuildVoiceStateImpl voiceState = (GuildVoiceStateImpl)member.getVoiceState();
        if (voiceState != null && voiceState.inAudioChannel()) {
            AudioChannelUnion channel2 = voiceState.getChannel();
            voiceState.setConnectedChannel(null);
            ((AudioChannelMixin)channel2).getConnectedMembersMap().remove(userId);
            this.getJDA().handleEvent(new GuildVoiceUpdateEvent(this.getJDA(), this.responseNumber, member, channel2));
        }
        SnowflakeCacheViewImpl<User> userView = this.getJDA().getUsersView();
        try (UnlockHook hook = userView.writeLock();){
            if (userId != this.getJDA().getSelfUser().getIdLong() && this.getJDA().getGuildsView().stream().noneMatch(g -> g.getMemberById(userId) != null)) {
                userView.remove(userId);
                this.getJDA().getEventCache().clear(EventCache.Type.USER, userId);
            }
        }
        this.getJDA().handleEvent(new GuildMemberRemoveEvent(this.getJDA(), this.responseNumber, guild, user, member));
        return null;
    }
}

