/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.VoiceChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractStandardGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IAgeRestrictedChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ISlowmodeChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IWebhookContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.AudioChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildMessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.VoiceChannelManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoiceChannelImpl
extends AbstractStandardGuildChannelImpl<VoiceChannelImpl>
implements VoiceChannel,
GuildMessageChannelMixin<VoiceChannelImpl>,
AudioChannelMixin<VoiceChannelImpl>,
IWebhookContainerMixin<VoiceChannelImpl>,
IAgeRestrictedChannelMixin<VoiceChannelImpl>,
ISlowmodeChannelMixin<VoiceChannelImpl> {
    private final TLongObjectMap<Member> connectedMembers = MiscUtil.newLongMap();
    private String region;
    private String status = "";
    private long latestMessageId;
    private int bitrate;
    private int userLimit;
    private int slowmode;
    private boolean nsfw;

    public VoiceChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.VOICE;
    }

    @Override
    public int getBitrate() {
        return this.bitrate;
    }

    @Override
    @Nullable
    public String getRegionRaw() {
        return this.region;
    }

    @Override
    public int getUserLimit() {
        return this.userLimit;
    }

    @Override
    public boolean isNSFW() {
        return this.nsfw;
    }

    @Override
    public int getSlowmode() {
        return this.slowmode;
    }

    @Override
    public boolean canTalk(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return member.hasPermission((GuildChannel)this, Permission.MESSAGE_SEND);
    }

    @Override
    public long getLatestMessageIdLong() {
        return this.latestMessageId;
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        return Collections.unmodifiableList(new ArrayList<Member>(this.connectedMembers.valueCollection()));
    }

    @Override
    @Nonnull
    public ChannelAction<VoiceChannel> createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        ChannelAction<VoiceChannel> action = guild.createVoiceChannel(this.name).setBitrate(this.bitrate).setUserlimit(this.userLimit);
        if (this.region != null) {
            action.setRegion(Region.fromKey(this.region));
        }
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
    public VoiceChannelManager getManager() {
        return new VoiceChannelManagerImpl(this);
    }

    @Override
    @Nonnull
    public String getStatus() {
        return this.status;
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> modifyStatus(@Nonnull String status) {
        Checks.notLonger(status, 500, "Voice Status");
        this.checkCanAccess();
        if (this.equals(this.getGuild().getSelfMember().getVoiceState().getChannel())) {
            this.checkPermission(Permission.VOICE_SET_STATUS);
        } else {
            this.checkCanManage();
        }
        Route.CompiledRoute route = Route.Channels.SET_STATUS.compile(this.getId());
        DataObject body = DataObject.empty().put("status", status);
        return new AuditableRestActionImpl<Void>((JDA)this.api, route, body);
    }

    @Override
    public TLongObjectMap<Member> getConnectedMembersMap() {
        return this.connectedMembers;
    }

    @Override
    public VoiceChannelImpl setBitrate(int bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    @Override
    public VoiceChannelImpl setRegion(String region) {
        this.region = region;
        return this;
    }

    @Override
    public VoiceChannelImpl setUserLimit(int userLimit) {
        this.userLimit = userLimit;
        return this;
    }

    @Override
    public VoiceChannelImpl setNSFW(boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    @Override
    public VoiceChannelImpl setSlowmode(int slowmode) {
        this.slowmode = slowmode;
        return this;
    }

    @Override
    public VoiceChannelImpl setLatestMessageIdLong(long latestMessageId) {
        this.latestMessageId = latestMessageId;
        return this;
    }

    public VoiceChannelImpl setStatus(String status) {
        this.status = status;
        return this;
    }
}

