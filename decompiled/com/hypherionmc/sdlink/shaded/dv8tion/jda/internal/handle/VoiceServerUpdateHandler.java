/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.AudioConnection;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AudioManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class VoiceServerUpdateHandler
extends SocketHandler {
    public VoiceServerUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        Guild guild = this.getJDA().getGuildById(guildId);
        if (guild == null) {
            throw new IllegalArgumentException("Attempted to start audio connection with Guild that doesn't exist!");
        }
        this.getJDA().getDirectAudioController().update(guild, guild.getSelfMember().getVoiceState().getChannel());
        if (content.isNull("endpoint")) {
            return null;
        }
        String endpoint = content.getString("endpoint").replace(":80", "");
        String token = content.getString("token");
        String sessionId = guild.getSelfMember().getVoiceState().getSessionId();
        if (sessionId == null) {
            throw new IllegalArgumentException("Attempted to create audio connection without having a session ID. Did VOICE_STATE_UPDATED fail?");
        }
        VoiceDispatchInterceptor voiceInterceptor = this.getJDA().getVoiceInterceptor();
        if (voiceInterceptor != null) {
            voiceInterceptor.onVoiceServerUpdate(new VoiceDispatchInterceptor.VoiceServerUpdate(guild, endpoint, token, sessionId, this.allContent));
            return null;
        }
        AudioManagerImpl audioManager = (AudioManagerImpl)this.getJDA().getAudioManagersView().get(guildId);
        if (audioManager == null) {
            WebSocketClient.LOG.debug("Received a VOICE_SERVER_UPDATE but JDA is not currently connected nor attempted to connect to a VoiceChannel. Assuming that this is caused by another client running on this account. Ignoring the event.");
            return null;
        }
        MiscUtil.locked(audioManager.CONNECTION_LOCK, () -> {
            AudioChannelUnion target = guild.getSelfMember().getVoiceState().getChannel();
            if (target == null) {
                WebSocketClient.LOG.warn("Ignoring VOICE_SERVER_UPDATE for unknown channel");
                return;
            }
            AudioConnection connection = new AudioConnection(audioManager, endpoint, sessionId, token, target);
            audioManager.setAudioConnection(connection);
            connection.startConnection();
        });
        return null;
    }
}

