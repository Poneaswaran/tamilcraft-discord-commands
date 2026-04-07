/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.ForumTagAddEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.ForumTagRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateEmojiEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateModeratedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateBitrateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateDefaultLayoutEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateDefaultReactionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateDefaultSortOrderEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateDefaultThreadSlowmodeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateFlagsEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateNSFWEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateParentEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdatePositionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateRegionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateSlowmodeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateTopicEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateTypeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateUserLimitEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.ThreadHiddenEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ForumTagImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.PermissionOverrideImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ForumChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IAgeRestrictedChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ICategorizableChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPositionableChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPostContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ISlowmodeChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IThreadContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ITopicChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.AudioChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.MessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.AbstractCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ReadWriteLockCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.hash.TLongHashSet;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class ChannelUpdateHandler
extends SocketHandler {
    public ChannelUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        String name;
        ChannelType type = ChannelType.fromId(content.getInt("type"));
        if (type == ChannelType.GROUP) {
            WebSocketClient.LOG.warn("Ignoring CHANNEL_UPDATE for a group which we don't support");
            return null;
        }
        if (!content.isNull("guild_id")) {
            long guildId = content.getUnsignedLong("guild_id");
            if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
        }
        long channelId = content.getUnsignedLong("id");
        AbstractGuildChannelImpl<?> channel = (AbstractGuildChannelImpl<?>)this.getJDA().getGuildChannelById(channelId);
        if (channel == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("CHANNEL_UPDATE attempted to update a channel that does not exist. JSON: {}", (Object)content);
            return null;
        }
        if ((channel = this.handleChannelTypeChange(channel, content, type)) == null) {
            return null;
        }
        String oldName = channel.getName();
        if (!Objects.equals(oldName, name = content.getString("name", oldName))) {
            channel.setName(name);
            this.getJDA().handleEvent(new ChannelUpdateNameEvent(this.getJDA(), this.responseNumber, channel, oldName, name));
        }
        if (channel instanceof ITopicChannelMixin) {
            this.handleTopic((ITopicChannelMixin)((Object)channel), content.getString("topic", null));
        }
        if (channel instanceof ISlowmodeChannelMixin) {
            this.handleSlowmode((ISlowmodeChannelMixin)((Object)channel), content.getInt("rate_limit_per_user", 0));
        }
        if (channel instanceof IAgeRestrictedChannelMixin) {
            this.handleNsfw((IAgeRestrictedChannelMixin)((Object)channel), content.getBoolean("nsfw"));
        }
        if (channel instanceof ICategorizableChannelMixin) {
            this.handleParentCategory((ICategorizableChannelMixin)((Object)channel), content.getUnsignedLong("parent_id", 0L));
        }
        if (channel instanceof IPositionableChannelMixin) {
            this.handlePosition((IPositionableChannelMixin)((Object)channel), content.getInt("position", 0));
        }
        if (channel instanceof IThreadContainerMixin) {
            this.handleThreadContainer((IThreadContainerMixin)((Object)channel), content);
        }
        if (channel instanceof AudioChannelMixin) {
            this.handleAudioChannel((AudioChannelMixin)((Object)channel), content);
        }
        if (channel instanceof IPostContainerMixin) {
            this.handlePostContainer((IPostContainerMixin)((Object)channel), content);
        }
        switch (type) {
            case FORUM: {
                ForumChannelImpl forumChannel = (ForumChannelImpl)channel;
                int layout = content.getInt("default_forum_layout", ((ForumChannelImpl)channel).getRawLayout());
                int oldLayout = forumChannel.getRawLayout();
                if (oldLayout == layout) break;
                forumChannel.setDefaultLayout(layout);
                this.getJDA().handleEvent(new ChannelUpdateDefaultLayoutEvent(this.getJDA(), this.responseNumber, forumChannel, ForumChannel.Layout.fromKey(oldLayout), ForumChannel.Layout.fromKey(layout)));
                break;
            }
            case VOICE: 
            case TEXT: 
            case NEWS: 
            case STAGE: 
            case CATEGORY: {
                break;
            }
            default: {
                WebSocketClient.LOG.debug("CHANNEL_UPDATE provided an unrecognized channel type JSON: {}", (Object)content);
            }
        }
        DataArray permOverwrites = content.getArray("permission_overwrites");
        this.applyPermissions((IPermissionContainerMixin)((Object)channel), permOverwrites);
        boolean hasAccessToChannel = channel.getGuild().getSelfMember().hasPermission(channel, Permission.VIEW_CHANNEL);
        if (channel instanceof IThreadContainer && !hasAccessToChannel) {
            this.handleHideChildThreads((IThreadContainer)((Object)channel));
        }
        return null;
    }

    private AbstractGuildChannelImpl<?> handleChannelTypeChange(AbstractGuildChannelImpl<?> channel, DataObject content, ChannelType newChannelType) {
        if (channel.getType() == newChannelType) {
            return channel;
        }
        EntityBuilder builder = this.getJDA().getEntityBuilder();
        GuildImpl guild = channel.getGuild();
        ChannelType oldType = channel.getType();
        EnumSet<ChannelType[]> expectedTypes = EnumSet.complementOf(EnumSet.of(ChannelType.PRIVATE, new ChannelType[]{ChannelType.GROUP, ChannelType.GUILD_NEWS_THREAD, ChannelType.GUILD_PRIVATE_THREAD, ChannelType.GUILD_PUBLIC_THREAD, ChannelType.UNKNOWN}));
        if (!expectedTypes.contains((Object)oldType) || !expectedTypes.contains((Object)newChannelType)) {
            WebSocketClient.LOG.warn("Unexpected channel type change {}->{}, discarding from cache.", (Object)channel.getType().getId(), (Object)content.getInt("type"));
            guild.uncacheChannel(channel, false);
            return null;
        }
        guild.uncacheChannel(channel, true);
        GuildChannel newChannel = builder.createGuildChannel(guild, content);
        if (channel instanceof IThreadContainer) {
            if (newChannel instanceof IThreadContainer) {
                guild.getThreadChannelCache().forEachUnordered(ThreadChannel::getParentChannel);
            } else {
                WebSocketClient.LOG.error("ThreadContainer channel transitioned into type that is not ThreadContainer? {} -> {}", (Object)channel.getType(), (Object)newChannel.getType());
            }
        }
        if (newChannel instanceof MessageChannelMixin && channel instanceof MessageChannel) {
            long latestMessageIdLong = ((MessageChannel)((Object)channel)).getLatestMessageIdLong();
            ((MessageChannelMixin)((Object)channel)).setLatestMessageIdLong(latestMessageIdLong);
        }
        this.getJDA().handleEvent(new ChannelUpdateTypeEvent(this.getJDA(), this.responseNumber, newChannel, oldType, newChannelType));
        return channel;
    }

    private void applyPermissions(IPermissionContainerMixin<?> channel, DataArray permOverwrites) {
        TLongObjectHashMap<PermissionOverride> currentOverrides = new TLongObjectHashMap<PermissionOverride>(channel.getPermissionOverrideMap());
        ArrayList<IPermissionHolder> changed = new ArrayList<IPermissionHolder>(currentOverrides.size());
        Guild guild = channel.getGuild();
        for (int i = 0; i < permOverwrites.length(); ++i) {
            DataObject overrideJson = permOverwrites.getObject(i);
            long id = overrideJson.getUnsignedLong("id", 0L);
            if (!this.handlePermissionOverride((PermissionOverride)currentOverrides.remove(id), overrideJson, id, channel)) continue;
            this.addPermissionHolder(changed, guild, id);
        }
        currentOverrides.forEachValue(override -> {
            channel.getPermissionOverrideMap().remove(override.getIdLong());
            this.addPermissionHolder(changed, guild, override.getIdLong());
            this.api.handleEvent(new PermissionOverrideDeleteEvent(this.api, this.responseNumber, channel, (PermissionOverride)override));
            return true;
        });
    }

    private void addPermissionHolder(List<IPermissionHolder> changed, Guild guild, long id) {
        IPermissionHolder holder = guild.getRoleById(id);
        if (holder == null) {
            holder = guild.getMemberById(id);
        }
        if (holder != null) {
            changed.add(holder);
        }
    }

    private boolean handlePermissionOverride(PermissionOverride currentOverride, DataObject override, long overrideId, IPermissionContainerMixin<?> channel) {
        boolean isRole;
        long allow = override.getLong("allow");
        long deny = override.getLong("deny");
        int type = override.getInt("type");
        boolean bl = isRole = type == 0;
        if (!isRole) {
            if (type != 1) {
                EntityBuilder.LOG.debug("Ignoring unknown invite of type '{}'. JSON: {}", (Object)type, (Object)override);
                return false;
            }
            if (!this.api.isCacheFlagSet(CacheFlag.MEMBER_OVERRIDES) && overrideId != this.api.getSelfUser().getIdLong()) {
                return false;
            }
        }
        if (currentOverride != null) {
            long oldAllow = currentOverride.getAllowedRaw();
            long oldDeny = currentOverride.getDeniedRaw();
            PermissionOverrideImpl impl = (PermissionOverrideImpl)currentOverride;
            if (oldAllow == allow && oldDeny == deny) {
                return false;
            }
            if (overrideId == channel.getGuild().getIdLong() && (allow | deny) == 0L) {
                channel.getPermissionOverrideMap().remove(overrideId);
                this.api.handleEvent(new PermissionOverrideDeleteEvent(this.api, this.responseNumber, channel, currentOverride));
                return true;
            }
            impl.setAllow(allow);
            impl.setDeny(deny);
            this.api.handleEvent(new PermissionOverrideUpdateEvent(this.api, this.responseNumber, channel, currentOverride, oldAllow, oldDeny));
        } else {
            if (overrideId == channel.getGuild().getIdLong() && (allow | deny) == 0L) {
                return false;
            }
            PermissionOverrideImpl impl = new PermissionOverrideImpl(channel, overrideId, isRole);
            currentOverride = impl;
            impl.setAllow(allow);
            impl.setDeny(deny);
            channel.getPermissionOverrideMap().put(overrideId, currentOverride);
            this.api.handleEvent(new PermissionOverrideCreateEvent(this.api, this.responseNumber, channel, currentOverride));
        }
        return true;
    }

    private void handleHideChildThreads(IThreadContainer channel) {
        List<ThreadChannel> threads = channel.getThreadChannels();
        if (threads.isEmpty()) {
            return;
        }
        for (ThreadChannel thread : threads) {
            GuildImpl guild = (GuildImpl)channel.getGuild();
            SortedChannelCacheViewImpl<GuildChannel> guildThreadView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> threadView = this.getJDA().getChannelsView();
            UnlockHook vlock = guildThreadView.writeLock();
            try {
                UnlockHook jlock = threadView.writeLock();
                try {
                    threadView.remove(thread.getType(), thread.getIdLong());
                    guildThreadView.remove(thread);
                }
                finally {
                    if (jlock == null) continue;
                    jlock.close();
                }
            }
            finally {
                if (vlock == null) continue;
                vlock.close();
            }
        }
        for (ThreadChannel thread : threads) {
            this.api.handleEvent(new ThreadHiddenEvent(this.api, this.responseNumber, thread));
        }
    }

    private void handleTagsUpdate(IPostContainerMixin<?> channel, DataArray tags) {
        if (!this.api.isCacheFlagSet(CacheFlag.FORUM_TAGS)) {
            return;
        }
        EntityBuilder builder = this.api.getEntityBuilder();
        SortedSnowflakeCacheView view = channel.getAvailableTagCache();
        try (UnlockHook hook = ((ReadWriteLockCache)((Object)view)).writeLock();){
            TLongObjectMap cache = ((AbstractCacheView)((Object)view)).getMap();
            TLongHashSet removedTags = new TLongHashSet(cache.keySet());
            for (int i = 0; i < tags.length(); ++i) {
                DataObject tagJson = tags.getObject(i);
                long id2 = tagJson.getUnsignedLong("id");
                if (removedTags.remove(id2)) {
                    ForumTagImpl impl = (ForumTagImpl)cache.get(id2);
                    if (impl == null) continue;
                    String name = tagJson.getString("name");
                    boolean moderated = tagJson.getBoolean("moderated");
                    String oldName = impl.getName();
                    EmojiUnion oldEmoji = impl.getEmoji();
                    impl.setEmoji(tagJson);
                    impl.setPosition(i);
                    if (!Objects.equals(oldEmoji, impl.getEmoji())) {
                        this.api.handleEvent(new ForumTagUpdateEmojiEvent(this.api, this.responseNumber, channel, impl, oldEmoji));
                    }
                    if (!name.equals(oldName)) {
                        impl.setName(name);
                        this.api.handleEvent(new ForumTagUpdateNameEvent(this.api, this.responseNumber, channel, impl, oldName));
                    }
                    if (moderated == impl.isModerated()) continue;
                    impl.setModerated(moderated);
                    this.api.handleEvent(new ForumTagUpdateModeratedEvent(this.api, this.responseNumber, channel, impl, moderated));
                    continue;
                }
                ForumTagImpl tag = builder.createForumTag(channel, tagJson, i);
                cache.put(id2, tag);
                this.api.handleEvent(new ForumTagAddEvent(this.api, this.responseNumber, channel, tag));
            }
            removedTags.forEach(id -> {
                ForumTag tag = (ForumTag)cache.remove(id);
                if (tag != null) {
                    this.api.handleEvent(new ForumTagRemoveEvent(this.api, this.responseNumber, channel, tag));
                }
                return true;
            });
        }
    }

    private void handleTopic(ITopicChannelMixin<?> channel, String topic) {
        String oldTopic = channel.getTopic();
        if (Objects.equals(oldTopic, topic)) {
            return;
        }
        channel.setTopic(topic);
        this.api.handleEvent(new ChannelUpdateTopicEvent(this.api, this.responseNumber, channel, oldTopic, topic));
    }

    private void handleSlowmode(ISlowmodeChannelMixin<?> channel, int slowmode) {
        int oldSlowmode = channel.getSlowmode();
        if (oldSlowmode == slowmode) {
            return;
        }
        channel.setSlowmode(slowmode);
        this.api.handleEvent(new ChannelUpdateSlowmodeEvent(this.api, this.responseNumber, channel, oldSlowmode, slowmode));
    }

    private void handleNsfw(IAgeRestrictedChannelMixin<?> channel, boolean nsfw) {
        boolean oldNsfw = channel.isNSFW();
        if (oldNsfw == nsfw) {
            return;
        }
        channel.setNSFW(nsfw);
        this.api.handleEvent(new ChannelUpdateNSFWEvent(this.api, this.responseNumber, channel, oldNsfw, nsfw));
    }

    private void handleParentCategory(ICategorizableChannelMixin<?> channel, long parentId) {
        long oldParentId = channel.getParentCategoryIdLong();
        if (oldParentId == parentId) {
            return;
        }
        Category oldParent = channel.getParentCategory();
        channel.setParentCategory(parentId);
        Category newParent = channel.getParentCategory();
        this.api.handleEvent(new ChannelUpdateParentEvent(this.api, this.responseNumber, channel, oldParent, newParent));
    }

    private void handlePosition(IPositionableChannelMixin<?> channel, int position) {
        int oldPosition = channel.getPositionRaw();
        if (oldPosition == position) {
            return;
        }
        channel.setPosition(position);
        this.api.handleEvent(new ChannelUpdatePositionEvent(this.api, this.responseNumber, channel, oldPosition, position));
    }

    private void handleThreadContainer(IThreadContainerMixin<?> channel, DataObject content) {
        int defaultThreadSlowmode;
        int oldDefaultThreadSlowmode = channel.getDefaultThreadSlowmode();
        if (oldDefaultThreadSlowmode != (defaultThreadSlowmode = content.getInt("default_thread_rate_limit_per_user", 0))) {
            channel.setDefaultThreadSlowmode(defaultThreadSlowmode);
            this.api.handleEvent(new ChannelUpdateDefaultThreadSlowmodeEvent(this.api, this.responseNumber, channel, oldDefaultThreadSlowmode, defaultThreadSlowmode));
        }
    }

    private void handleAudioChannel(AudioChannelMixin<?> channel, DataObject content) {
        String regionRaw;
        String oldRegion;
        int bitrate;
        int oldBitrate = channel.getBitrate();
        if (oldBitrate != (bitrate = content.getInt("bitrate"))) {
            channel.setBitrate(bitrate);
            this.api.handleEvent(new ChannelUpdateBitrateEvent(this.api, this.responseNumber, channel, oldBitrate, bitrate));
        }
        int userLimit = content.getInt("user_limit");
        int oldLimit = channel.getUserLimit();
        if (oldLimit != userLimit) {
            channel.setUserLimit(userLimit);
            this.getJDA().handleEvent(new ChannelUpdateUserLimitEvent(this.getJDA(), this.responseNumber, channel, oldLimit, userLimit));
        }
        if (!Objects.equals(oldRegion = channel.getRegionRaw(), regionRaw = content.getString("rtc_region", null))) {
            channel.setRegion(regionRaw);
            this.api.handleEvent(new ChannelUpdateRegionEvent(this.api, this.responseNumber, channel, Region.fromKey(oldRegion), Region.fromKey(regionRaw)));
        }
    }

    private void handlePostContainer(IPostContainerMixin<?> channel, DataObject content) {
        content.optArray("available_tags").ifPresent(array -> this.handleTagsUpdate(channel, (DataArray)array));
        EmojiUnion defaultReaction = content.optObject("default_reaction_emoji").map(json -> EntityBuilder.createEmoji(json, "emoji_name", "emoji_id")).orElse(null);
        EmojiUnion oldDefaultReaction = channel.getDefaultReaction();
        if (!Objects.equals(oldDefaultReaction, defaultReaction)) {
            channel.setDefaultReaction(content.optObject("default_reaction_emoji").orElse(null));
            this.getJDA().handleEvent(new ChannelUpdateDefaultReactionEvent(this.getJDA(), this.responseNumber, channel, oldDefaultReaction, defaultReaction));
        }
        int sortOrder = content.getInt("default_sort_order", channel.getRawSortOrder());
        int oldSortOrder = channel.getRawSortOrder();
        if (oldSortOrder != sortOrder) {
            channel.setDefaultSortOrder(sortOrder);
            this.getJDA().handleEvent(new ChannelUpdateDefaultSortOrderEvent(this.getJDA(), this.responseNumber, channel, IPostContainer.SortOrder.fromKey(oldSortOrder)));
        }
        int newFlags = content.getInt("flags", 0);
        int oldFlags = channel.getRawFlags();
        if (oldFlags != newFlags) {
            channel.setFlags(newFlags);
            this.getJDA().handleEvent(new ChannelUpdateFlagsEvent(this.getJDA(), this.responseNumber, channel, ChannelFlag.fromRaw(oldFlags), ChannelFlag.fromRaw(newFlags)));
        }
    }
}

