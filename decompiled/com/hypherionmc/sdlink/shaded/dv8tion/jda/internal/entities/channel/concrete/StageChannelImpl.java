/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.StageChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.StageInstanceAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractStandardGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IAgeRestrictedChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ISlowmodeChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IWebhookContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.AudioChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildMessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.StageChannelManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.StageInstanceActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class StageChannelImpl
extends AbstractStandardGuildChannelImpl<StageChannelImpl>
implements StageChannel,
AudioChannelMixin<StageChannelImpl>,
GuildMessageChannelMixin<StageChannelImpl>,
IWebhookContainerMixin<StageChannelImpl>,
IAgeRestrictedChannelMixin<StageChannelImpl>,
ISlowmodeChannelMixin<StageChannelImpl> {
    private final TLongObjectMap<Member> connectedMembers = MiscUtil.newLongMap();
    private StageInstance instance;
    private String region;
    private int bitrate;
    private int userlimit;
    private int slowmode;
    private boolean ageRestricted;
    private long latestMessageId;

    public StageChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.STAGE;
    }

    @Override
    public int getBitrate() {
        return this.bitrate;
    }

    @Override
    public int getUserLimit() {
        return this.userlimit;
    }

    @Override
    @Nullable
    public String getRegionRaw() {
        return this.region;
    }

    @Override
    @Nullable
    public StageInstance getStageInstance() {
        return this.instance;
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        return Collections.unmodifiableList(new ArrayList<Member>(this.connectedMembers.valueCollection()));
    }

    @Override
    @Nonnull
    public StageInstanceAction createStageInstance(@Nonnull String topic) {
        EnumSet<Permission> permissions = this.getGuild().getSelfMember().getPermissions(this);
        EnumSet<Permission> required = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS);
        for (Permission perm : required) {
            if (permissions.contains((Object)perm)) continue;
            throw new InsufficientPermissionException(this, perm, "You must be a stage moderator to create a stage instance! Missing Permission: " + (Object)((Object)perm));
        }
        return new StageInstanceActionImpl(this).setTopic(topic);
    }

    @Override
    @Nonnull
    public ChannelAction<StageChannel> createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        ChannelAction<StageChannel> action = guild.createStageChannel(this.name).setBitrate(this.bitrate);
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
    public int getSlowmode() {
        return this.slowmode;
    }

    @Override
    public boolean isNSFW() {
        return this.ageRestricted;
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
    public StageChannelManager getManager() {
        return new StageChannelManagerImpl(this);
    }

    @Override
    @Nonnull
    public RestAction<Void> requestToSpeak() {
        GuildImpl guild = this.getGuild();
        Route.CompiledRoute route = Route.Guilds.UPDATE_VOICE_STATE.compile(guild.getId(), "@me");
        DataObject body = DataObject.empty().put("channel_id", this.getId());
        if (guild.getSelfMember().hasPermission((GuildChannel)this, Permission.VOICE_MUTE_OTHERS)) {
            body.putNull("request_to_speak_timestamp").put("suppress", false);
        } else {
            body.put("request_to_speak_timestamp", OffsetDateTime.now().toString());
        }
        if (!this.equals(guild.getSelfMember().getVoiceState().getChannel())) {
            throw new IllegalStateException("Cannot request to speak without being connected to the stage channel!");
        }
        return new RestActionImpl<Void>(this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public RestAction<Void> cancelRequestToSpeak() {
        GuildImpl guild = this.getGuild();
        Route.CompiledRoute route = Route.Guilds.UPDATE_VOICE_STATE.compile(guild.getId(), "@me");
        DataObject body = DataObject.empty().putNull("request_to_speak_timestamp").put("suppress", true).put("channel_id", this.getId());
        if (!this.equals(guild.getSelfMember().getVoiceState().getChannel())) {
            throw new IllegalStateException("Cannot cancel request to speak without being connected to the stage channel!");
        }
        return new RestActionImpl<Void>(this.getJDA(), route, body);
    }

    @Override
    public TLongObjectMap<Member> getConnectedMembersMap() {
        return this.connectedMembers;
    }

    @Override
    public StageChannelImpl setBitrate(int bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    @Override
    public StageChannelImpl setUserLimit(int userlimit) {
        this.userlimit = userlimit;
        return this;
    }

    @Override
    public StageChannelImpl setRegion(String region) {
        this.region = region;
        return this;
    }

    public StageChannelImpl setStageInstance(StageInstance instance) {
        this.instance = instance;
        return this;
    }

    @Override
    public StageChannelImpl setNSFW(boolean ageRestricted) {
        this.ageRestricted = ageRestricted;
        return this;
    }

    @Override
    public StageChannelImpl setSlowmode(int slowmode) {
        this.slowmode = slowmode;
        return this;
    }

    @Override
    public StageChannelImpl setLatestMessageIdLong(long latestMessageId) {
        this.latestMessageId = latestMessageId;
        return this;
    }
}

