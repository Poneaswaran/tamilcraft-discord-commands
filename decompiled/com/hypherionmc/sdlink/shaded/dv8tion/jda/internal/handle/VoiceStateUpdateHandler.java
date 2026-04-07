/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceDeafenEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceRequestToSpeakEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfDeafenEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfMuteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceSuppressEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GuildVoiceVideoEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildVoiceStateImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.AudioChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AudioManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import java.time.OffsetDateTime;
import java.util.Objects;

public class VoiceStateUpdateHandler
extends SocketHandler {
    public VoiceStateUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        Long guildId;
        Long l = guildId = content.isNull("guild_id") ? null : Long.valueOf(content.getLong("guild_id"));
        if (guildId == null) {
            return null;
        }
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        if (content.isNull("member")) {
            WebSocketClient.LOG.debug("Discarding VOICE_STATE_UPDATE with missing member. JSON: {}", (Object)content);
            return null;
        }
        this.handleGuildVoiceState(content);
        return null;
    }

    private void handleGuildVoiceState(DataObject content) {
        Guild guild;
        long userId = content.getLong("user_id");
        long guildId = content.getLong("guild_id");
        Long channelId = !content.isNull("channel_id") ? Long.valueOf(content.getLong("channel_id")) : null;
        String sessionId = !content.isNull("session_id") ? content.getString("session_id") : null;
        boolean selfMuted = content.getBoolean("self_mute");
        boolean selfDeafened = content.getBoolean("self_deaf");
        boolean guildMuted = content.getBoolean("mute");
        boolean guildDeafened = content.getBoolean("deaf");
        boolean suppressed = content.getBoolean("suppress");
        boolean stream = content.getBoolean("self_stream");
        boolean video = content.getBoolean("self_video", false);
        String requestToSpeak = content.getString("request_to_speak_timestamp", null);
        OffsetDateTime requestToSpeakTime = null;
        long requestToSpeakTimestamp = 0L;
        if (requestToSpeak != null) {
            requestToSpeakTime = OffsetDateTime.parse(requestToSpeak);
            requestToSpeakTimestamp = requestToSpeakTime.toInstant().toEpochMilli();
        }
        if ((guild = this.getJDA().getGuildById(guildId)) == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received a VOICE_STATE_UPDATE for a Guild that has yet to be cached. JSON: {}", (Object)content);
            return;
        }
        AudioChannel channel = null;
        if (channelId != null) {
            channel = (AudioChannel)guild.getGuildChannelById(channelId);
        }
        if (channel == null && channelId != null) {
            this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received VOICE_STATE_UPDATE for an AudioChannel that has yet to be cached. JSON: {}", (Object)content);
            return;
        }
        DataObject memberJson = content.getObject("member");
        MemberImpl member = this.getJDA().getEntityBuilder().createMember((GuildImpl)guild, memberJson);
        if (member == null) {
            return;
        }
        GuildVoiceStateImpl vState = (GuildVoiceStateImpl)member.getVoiceState();
        if (vState == null) {
            return;
        }
        vState.setSessionId(sessionId);
        VoiceDispatchInterceptor voiceInterceptor = this.getJDA().getVoiceInterceptor();
        boolean isSelf = guild.getSelfMember().equals(member);
        boolean wasMute = vState.isMuted();
        boolean wasDeaf = vState.isDeafened();
        if (selfMuted != vState.isSelfMuted()) {
            vState.setSelfMuted(selfMuted);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceSelfMuteEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (selfDeafened != vState.isSelfDeafened()) {
            vState.setSelfDeafened(selfDeafened);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceSelfDeafenEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (guildMuted != vState.isGuildMuted()) {
            vState.setGuildMuted(guildMuted);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceGuildMuteEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (guildDeafened != vState.isGuildDeafened()) {
            vState.setGuildDeafened(guildDeafened);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceGuildDeafenEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (suppressed != vState.isSuppressed()) {
            vState.setSuppressed(suppressed);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceSuppressEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (stream != vState.isStream()) {
            vState.setStream(stream);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceStreamEvent(this.getJDA(), this.responseNumber, member, stream));
        }
        if (video != vState.isSendingVideo()) {
            vState.setVideo(video);
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            this.getJDA().handleEvent(new GuildVoiceVideoEvent(this.getJDA(), this.responseNumber, member, video));
        }
        if (wasMute != vState.isMuted()) {
            this.getJDA().handleEvent(new GuildVoiceMuteEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (wasDeaf != vState.isDeafened()) {
            this.getJDA().handleEvent(new GuildVoiceDeafenEvent((JDA)this.getJDA(), this.responseNumber, member));
        }
        if (requestToSpeakTimestamp != vState.getRequestToSpeak()) {
            OffsetDateTime oldRequestToSpeak = vState.getRequestToSpeakTimestamp();
            vState.setRequestToSpeak(requestToSpeakTime);
            this.getJDA().handleEvent(new GuildVoiceRequestToSpeakEvent(this.getJDA(), this.responseNumber, member, oldRequestToSpeak, requestToSpeakTime));
        }
        if (!Objects.equals(channel, vState.getChannel())) {
            AudioChannelUnion oldChannel = vState.getChannel();
            vState.setConnectedChannel(channel);
            if (oldChannel == null) {
                ((AudioChannelMixin)channel).getConnectedMembersMap().put(userId, member);
                this.getJDA().getEntityBuilder().updateMemberCache(member);
            } else if (channel == null) {
                ((AudioChannelMixin)oldChannel).getConnectedMembersMap().remove(userId);
                if (isSelf) {
                    this.getJDA().getDirectAudioController().update(guild, null);
                }
                this.getJDA().getEntityBuilder().updateMemberCache(member, memberJson.isNull("joined_at"));
            } else {
                AudioManagerImpl mng = (AudioManagerImpl)this.getJDA().getAudioManagersView().get(guildId);
                if (isSelf && mng != null && voiceInterceptor == null) {
                    if (mng.isConnected()) {
                        mng.setConnectedChannel(channel);
                    }
                    if (mng.isConnected()) {
                        this.getJDA().getDirectAudioController().update(guild, channel);
                    }
                }
                ((AudioChannelMixin)channel).getConnectedMembersMap().put(userId, member);
                ((AudioChannelMixin)oldChannel).getConnectedMembersMap().remove(userId);
                this.getJDA().getEntityBuilder().updateMemberCache(member);
            }
            this.getJDA().handleEvent(new GuildVoiceUpdateEvent(this.getJDA(), this.responseNumber, member, oldChannel));
        }
        if (isSelf && voiceInterceptor != null && voiceInterceptor.onVoiceStateUpdate(new VoiceDispatchInterceptor.VoiceStateUpdate(channel, vState, this.allContent))) {
            this.getJDA().getDirectAudioController().update(guild, channel);
        }
        ((GuildImpl)guild).updateRequestToSpeak();
    }
}

