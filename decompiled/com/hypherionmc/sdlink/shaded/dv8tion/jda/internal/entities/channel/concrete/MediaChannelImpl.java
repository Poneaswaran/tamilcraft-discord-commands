/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.BaseForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.MediaChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IAgeRestrictedChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPostContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ISlowmodeChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ITopicChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IWebhookContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.StandardGuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.CustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.MediaChannelManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedSnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class MediaChannelImpl
extends AbstractGuildChannelImpl<MediaChannelImpl>
implements MediaChannel,
GuildChannelUnion,
StandardGuildChannelMixin<MediaChannelImpl>,
IAgeRestrictedChannelMixin<MediaChannelImpl>,
ISlowmodeChannelMixin<MediaChannelImpl>,
IWebhookContainerMixin<MediaChannelImpl>,
IPostContainerMixin<MediaChannelImpl>,
ITopicChannelMixin<MediaChannelImpl> {
    private final TLongObjectMap<PermissionOverride> overrides = MiscUtil.newLongMap();
    private final SortedSnowflakeCacheViewImpl<ForumTag> tagCache = new SortedSnowflakeCacheViewImpl<ForumTag>(ForumTag.class, BaseForumTag::getName, Comparator.naturalOrder());
    private Emoji defaultReaction;
    private String topic;
    private long parentCategoryId;
    private boolean nsfw = false;
    private int position;
    private int flags;
    private int slowmode;
    private int defaultSortOrder;
    protected int defaultThreadSlowmode;

    public MediaChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nonnull
    public MediaChannelManager getManager() {
        return new MediaChannelManagerImpl(this);
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        return this.getGuild().getMembers().stream().filter(m -> m.hasPermission((GuildChannel)this, Permission.VIEW_CHANNEL)).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public ChannelAction<MediaChannel> createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        ChannelAction<MediaChannel> action = guild.createMediaChannel(this.name).setNSFW(this.nsfw).setTopic(this.topic).setSlowmode(this.slowmode).setAvailableTags(this.getAvailableTags());
        if (this.defaultSortOrder != -1) {
            action.setDefaultSortOrder(IPostContainer.SortOrder.fromKey(this.defaultSortOrder));
        }
        if (this.defaultReaction instanceof UnicodeEmoji) {
            action.setDefaultReaction(this.defaultReaction);
        }
        if (guild.equals(this.getGuild())) {
            Category parent = this.getParentCategory();
            action.setDefaultReaction(this.defaultReaction);
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
    public EnumSet<ChannelFlag> getFlags() {
        return ChannelFlag.fromRaw(this.flags);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheViewImpl<ForumTag> getAvailableTagCache() {
        return this.tagCache;
    }

    @Override
    public TLongObjectMap<PermissionOverride> getPermissionOverrideMap() {
        return this.overrides;
    }

    @Override
    public boolean isNSFW() {
        return this.nsfw;
    }

    @Override
    public int getPositionRaw() {
        return this.position;
    }

    @Override
    public long getParentCategoryIdLong() {
        return this.parentCategoryId;
    }

    @Override
    public int getSlowmode() {
        return this.slowmode;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public EmojiUnion getDefaultReaction() {
        return (EmojiUnion)this.defaultReaction;
    }

    @Override
    public int getDefaultThreadSlowmode() {
        return this.defaultThreadSlowmode;
    }

    @Override
    @Nonnull
    public IPostContainer.SortOrder getDefaultSortOrder() {
        return IPostContainer.SortOrder.fromKey(this.defaultSortOrder);
    }

    @Override
    public int getRawFlags() {
        return this.flags;
    }

    @Override
    public int getRawSortOrder() {
        return this.defaultSortOrder;
    }

    @Override
    public MediaChannelImpl setParentCategory(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return this;
    }

    @Override
    public MediaChannelImpl setPosition(int position) {
        this.position = position;
        return this;
    }

    @Override
    public MediaChannelImpl setDefaultThreadSlowmode(int defaultThreadSlowmode) {
        this.defaultThreadSlowmode = defaultThreadSlowmode;
        return this;
    }

    @Override
    public MediaChannelImpl setNSFW(boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    @Override
    public MediaChannelImpl setSlowmode(int slowmode) {
        this.slowmode = slowmode;
        return this;
    }

    @Override
    public MediaChannelImpl setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    @Override
    public MediaChannelImpl setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    @Override
    public MediaChannelImpl setDefaultReaction(DataObject emoji) {
        this.defaultReaction = emoji != null && !emoji.isNull("emoji_id") ? new CustomEmojiImpl("", emoji.getUnsignedLong("emoji_id"), false) : (emoji != null && !emoji.isNull("emoji_name") ? Emoji.fromUnicode(emoji.getString("emoji_name")) : null);
        return this;
    }

    @Override
    public MediaChannelImpl setDefaultSortOrder(int defaultSortOrder) {
        this.defaultSortOrder = defaultSortOrder;
        return this;
    }
}

