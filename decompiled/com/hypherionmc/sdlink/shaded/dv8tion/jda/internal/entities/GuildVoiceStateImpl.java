/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;

public class GuildVoiceStateImpl
implements GuildVoiceState {
    private final JDA api;
    private Guild guild;
    private Member member;
    private AudioChannel connectedChannel;
    private String sessionId;
    private long requestToSpeak;
    private boolean selfMuted = false;
    private boolean selfDeafened = false;
    private boolean guildMuted = false;
    private boolean guildDeafened = false;
    private boolean suppressed = false;
    private boolean stream = false;
    private boolean video = false;

    public GuildVoiceStateImpl(Member member) {
        this.api = member.getJDA();
        this.guild = member.getGuild();
        this.member = member;
    }

    @Override
    public boolean isSelfMuted() {
        return this.selfMuted;
    }

    @Override
    public boolean isSelfDeafened() {
        return this.selfDeafened;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    public long getRequestToSpeak() {
        return this.requestToSpeak;
    }

    @Override
    public OffsetDateTime getRequestToSpeakTimestamp() {
        return this.requestToSpeak == 0L ? null : Helpers.toOffset(this.requestToSpeak);
    }

    @Override
    @Nonnull
    public RestAction<Void> approveSpeaker() {
        return this.update(false);
    }

    @Override
    @Nonnull
    public RestAction<Void> declineSpeaker() {
        return this.update(true);
    }

    private RestAction<Void> update(boolean suppress) {
        if (!(this.connectedChannel instanceof StageChannel) || suppress == this.isSuppressed()) {
            return new CompletedRestAction<Void>(this.api, null);
        }
        Member selfMember = this.getGuild().getSelfMember();
        boolean isSelf = selfMember.equals(this.member);
        if (!isSelf && !selfMember.hasPermission((GuildChannel)this.connectedChannel, Permission.VOICE_MUTE_OTHERS)) {
            throw new InsufficientPermissionException(this.connectedChannel, Permission.VOICE_MUTE_OTHERS);
        }
        Route.CompiledRoute route = Route.Guilds.UPDATE_VOICE_STATE.compile(this.guild.getId(), isSelf ? "@me" : this.getId());
        DataObject body = DataObject.empty().put("channel_id", this.connectedChannel.getId()).put("suppress", suppress);
        return new RestActionImpl<Void>(this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public RestAction<Void> inviteSpeaker() {
        if (!(this.connectedChannel instanceof StageChannel)) {
            return new CompletedRestAction<Void>(this.api, null);
        }
        if (!this.getGuild().getSelfMember().hasPermission((GuildChannel)this.connectedChannel, Permission.VOICE_MUTE_OTHERS)) {
            throw new InsufficientPermissionException(this.connectedChannel, Permission.VOICE_MUTE_OTHERS);
        }
        Route.CompiledRoute route = Route.Guilds.UPDATE_VOICE_STATE.compile(this.guild.getId(), this.getId());
        DataObject body = DataObject.empty().put("channel_id", this.connectedChannel.getId()).put("suppress", false).put("request_to_speak_timestamp", OffsetDateTime.now().toString());
        return new RestActionImpl<Void>(this.getJDA(), route, body);
    }

    @Override
    public boolean isMuted() {
        return this.isSelfMuted() || this.isGuildMuted();
    }

    @Override
    public boolean isDeafened() {
        return this.isSelfDeafened() || this.isGuildDeafened();
    }

    @Override
    public boolean isGuildMuted() {
        return this.guildMuted;
    }

    @Override
    public boolean isGuildDeafened() {
        return this.guildDeafened;
    }

    @Override
    public boolean isSuppressed() {
        return this.suppressed;
    }

    @Override
    public boolean isStream() {
        return this.stream;
    }

    @Override
    public boolean isSendingVideo() {
        return this.video;
    }

    @Override
    public AudioChannelUnion getChannel() {
        return (AudioChannelUnion)this.connectedChannel;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        Guild realGuild = this.api.getGuildById(this.guild.getIdLong());
        if (realGuild != null) {
            this.guild = realGuild;
        }
        return this.guild;
    }

    @Override
    @Nonnull
    public Member getMember() {
        Member realMember = this.getGuild().getMemberById(this.member.getIdLong());
        if (realMember != null) {
            this.member = realMember;
        }
        return this.member;
    }

    @Override
    public boolean inAudioChannel() {
        return this.getChannel() != null;
    }

    @Override
    public long getIdLong() {
        return this.member.getIdLong();
    }

    public int hashCode() {
        return this.member.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GuildVoiceState)) {
            return false;
        }
        GuildVoiceState oStatus = (GuildVoiceState)obj;
        return this.member.equals(oStatus.getMember());
    }

    public String toString() {
        return new EntityString(this).addMetadata("member", this.getMember()).toString();
    }

    public GuildVoiceStateImpl setConnectedChannel(AudioChannel connectedChannel) {
        this.connectedChannel = connectedChannel;
        return this;
    }

    public GuildVoiceStateImpl setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public GuildVoiceStateImpl setSelfMuted(boolean selfMuted) {
        this.selfMuted = selfMuted;
        return this;
    }

    public GuildVoiceStateImpl setSelfDeafened(boolean selfDeafened) {
        this.selfDeafened = selfDeafened;
        return this;
    }

    public GuildVoiceStateImpl setGuildMuted(boolean guildMuted) {
        this.guildMuted = guildMuted;
        return this;
    }

    public GuildVoiceStateImpl setGuildDeafened(boolean guildDeafened) {
        this.guildDeafened = guildDeafened;
        return this;
    }

    public GuildVoiceStateImpl setSuppressed(boolean suppressed) {
        this.suppressed = suppressed;
        return this;
    }

    public GuildVoiceStateImpl setStream(boolean stream) {
        this.stream = stream;
        return this;
    }

    public GuildVoiceStateImpl setVideo(boolean video) {
        this.video = video;
        return this;
    }

    public GuildVoiceStateImpl setRequestToSpeak(OffsetDateTime timestamp) {
        this.requestToSpeak = timestamp == null ? 0L : timestamp.toInstant().toEpochMilli();
        return this;
    }
}

