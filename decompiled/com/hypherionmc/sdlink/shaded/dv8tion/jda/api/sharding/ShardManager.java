/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.sharding;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationInfo;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IGuildChannelContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.GatewayIntent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ShardCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.UnifiedChannelCacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Unmodifiable;

public interface ShardManager
extends IGuildChannelContainer<Channel> {
    default public void addEventListener(Object ... listeners) {
        Checks.noneNull(listeners, "listeners");
        this.getShardCache().forEach(jda -> jda.addEventListener(listeners));
    }

    default public void removeEventListener(Object ... listeners) {
        Checks.noneNull(listeners, "listeners");
        this.getShardCache().forEach(jda -> jda.removeEventListener(listeners));
    }

    default public void addEventListeners(@Nonnull IntFunction<Object> eventListenerProvider) {
        Checks.notNull(eventListenerProvider, "event listener provider");
        this.getShardCache().forEach(jda -> {
            Object listener = eventListenerProvider.apply(jda.getShardInfo().getShardId());
            if (listener != null) {
                jda.addEventListener(listener);
            }
        });
    }

    default public void removeEventListeners(@Nonnull IntFunction<Collection<Object>> eventListenerProvider) {
        Checks.notNull(eventListenerProvider, "event listener provider");
        this.getShardCache().forEach(jda -> jda.removeEventListener(eventListenerProvider.apply(jda.getShardInfo().getShardId())));
    }

    default public void removeEventListenerProvider(@Nonnull IntFunction<Object> eventListenerProvider) {
    }

    public int getShardsQueued();

    default public int getShardsRunning() {
        return (int)this.getShardCache().size();
    }

    default public int getShardsTotal() {
        return this.getShardsQueued() + this.getShardsRunning();
    }

    @Nonnull
    default public EnumSet<GatewayIntent> getGatewayIntents() {
        return this.getShardCache().applyStream(stream -> stream.map(JDA::getGatewayIntents).findAny().orElse(EnumSet.noneOf(GatewayIntent.class)));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<ApplicationInfo> retrieveApplicationInfo() {
        return ((JDA)this.getShardCache().stream().findAny().orElseThrow(() -> new IllegalStateException("no active shards"))).retrieveApplicationInfo();
    }

    default public double getAverageGatewayPing() {
        return this.getShardCache().stream().mapToLong(JDA::getGatewayPing).filter(ping -> ping != -1L).average().orElse(-1.0);
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<Category> getCategoryCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getCategoryCache));
    }

    @Nullable
    default public RichCustomEmoji getEmojiById(long id) {
        return this.getEmojiCache().getElementById(id);
    }

    @Nullable
    default public RichCustomEmoji getEmojiById(@Nonnull String id) {
        return this.getEmojiCache().getElementById(id);
    }

    @Nonnull
    default public SnowflakeCacheView<RichCustomEmoji> getEmojiCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(JDA::getEmojiCache));
    }

    @Nonnull
    default public @Unmodifiable List<RichCustomEmoji> getEmojis() {
        return this.getEmojiCache().asList();
    }

    @Nonnull
    default public @Unmodifiable List<RichCustomEmoji> getEmojisByName(@Nonnull String name, boolean ignoreCase) {
        return this.getEmojiCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public Guild getGuildById(long id) {
        return this.getGuildCache().getElementById(id);
    }

    @Nullable
    default public Guild getGuildById(@Nonnull String id) {
        return this.getGuildById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    default public @Unmodifiable List<Guild> getGuildsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getGuildCache().getElementsByName(name, ignoreCase);
    }

    @Nonnull
    default public SnowflakeCacheView<Guild> getGuildCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(JDA::getGuildCache));
    }

    @Nonnull
    default public @Unmodifiable List<Guild> getGuilds() {
        return this.getGuildCache().asList();
    }

    @Nonnull
    default public @Unmodifiable List<Guild> getMutualGuilds(@Nonnull Collection<User> users) {
        Checks.noneNull(users, "users");
        return this.getGuildCache().stream().filter(guild -> users.stream().allMatch(guild::isMember)).collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    default public @Unmodifiable List<Guild> getMutualGuilds(User ... users) {
        Checks.notNull(users, "users");
        return this.getMutualGuilds(Arrays.asList(users));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<User> retrieveUserById(@Nonnull String id) {
        return this.retrieveUserById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<User> retrieveUserById(long id) {
        JDA api = null;
        Iterator iterator2 = this.getShardCache().iterator();
        while (iterator2.hasNext()) {
            boolean isUpdated;
            JDA shard;
            api = shard = (JDA)iterator2.next();
            EnumSet<GatewayIntent> intents = shard.getGatewayIntents();
            User user = shard.getUserById(id);
            boolean bl = isUpdated = intents.contains((Object)GatewayIntent.GUILD_PRESENCES) || intents.contains((Object)GatewayIntent.GUILD_MEMBERS);
            if (user == null || !isUpdated) continue;
            return new CompletedRestAction<User>(shard, user);
        }
        if (api == null) {
            throw new IllegalStateException("no shards active");
        }
        JDAImpl jda = (JDAImpl)api;
        Route.CompiledRoute route = Route.Users.GET_USER.compile(Long.toUnsignedString(id));
        return new RestActionImpl<User>((JDA)jda, route, (response, request) -> jda.getEntityBuilder().createUser(response.getObject()));
    }

    @Nullable
    default public User getUserByTag(@Nonnull String tag) {
        return this.getShardCache().applyStream(stream -> stream.map(jda -> jda.getUserByTag(tag)).filter(Objects::nonNull).findFirst().orElse(null));
    }

    @Nullable
    default public User getUserByTag(@Nonnull String username, @Nonnull String discriminator) {
        return this.getShardCache().applyStream(stream -> stream.map(jda -> jda.getUserByTag(username, discriminator)).filter(Objects::nonNull).findFirst().orElse(null));
    }

    @Nonnull
    default public @Unmodifiable List<PrivateChannel> getPrivateChannels() {
        return this.getPrivateChannelCache().asList();
    }

    @Nullable
    default public Role getRoleById(long id) {
        return this.getRoleCache().getElementById(id);
    }

    @Nullable
    default public Role getRoleById(@Nonnull String id) {
        return this.getRoleCache().getElementById(id);
    }

    @Nonnull
    default public SnowflakeCacheView<Role> getRoleCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(JDA::getRoleCache));
    }

    @Nonnull
    default public @Unmodifiable List<Role> getRoles() {
        return this.getRoleCache().asList();
    }

    @Nonnull
    default public @Unmodifiable List<Role> getRolesByName(@Nonnull String name, boolean ignoreCase) {
        return this.getRoleCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public PrivateChannel getPrivateChannelById(long id) {
        return this.getPrivateChannelCache().getElementById(id);
    }

    @Nullable
    default public PrivateChannel getPrivateChannelById(@Nonnull String id) {
        return this.getPrivateChannelCache().getElementById(id);
    }

    @Nonnull
    default public SnowflakeCacheView<PrivateChannel> getPrivateChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(JDA::getPrivateChannelCache));
    }

    @Override
    @Nullable
    default public GuildChannel getGuildChannelById(long id) {
        for (JDA shard : this.getShards()) {
            GuildChannel channel = shard.getGuildChannelById(id);
            if (channel == null) continue;
            return channel;
        }
        return null;
    }

    @Override
    @Nullable
    default public GuildChannel getGuildChannelById(@Nonnull ChannelType type, long id) {
        Checks.notNull((Object)type, "ChannelType");
        for (JDA shard : this.getShards()) {
            GuildChannel channel = shard.getGuildChannelById(type, id);
            if (channel == null) continue;
            return channel;
        }
        return null;
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<TextChannel> getTextChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getTextChannelCache));
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getVoiceChannelCache));
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<StageChannel> getStageChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getStageChannelCache));
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<ThreadChannel> getThreadChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getThreadChannelCache));
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<NewsChannel> getNewsChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getNewsChannelCache));
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<ForumChannel> getForumChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getForumChannelCache));
    }

    @Override
    @Nonnull
    default public SnowflakeCacheView<MediaChannel> getMediaChannelCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(IGuildChannelContainer::getMediaChannelCache));
    }

    @Override
    @Nonnull
    default public ChannelCacheView<Channel> getChannelCache() {
        return new UnifiedChannelCacheView<Channel>(() -> this.getShardCache().stream().map(IGuildChannelContainer::getChannelCache));
    }

    @Nullable
    default public JDA getShardById(int id) {
        return this.getShardCache().getElementById(id);
    }

    @Nullable
    default public JDA getShardById(@Nonnull String id) {
        return this.getShardCache().getElementById(id);
    }

    @Nonnull
    public ShardCacheView getShardCache();

    @Nonnull
    default public @Unmodifiable List<JDA> getShards() {
        return this.getShardCache().asList();
    }

    @Nullable
    default public JDA.Status getStatus(int shardId) {
        JDA jda = this.getShardCache().getElementById(shardId);
        return jda == null ? null : jda.getStatus();
    }

    @Nonnull
    default public @Unmodifiable Map<JDA, JDA.Status> getStatuses() {
        return Collections.unmodifiableMap(this.getShardCache().stream().collect(Collectors.toMap(Function.identity(), JDA::getStatus)));
    }

    @Nullable
    default public User getUserById(long id) {
        return this.getUserCache().getElementById(id);
    }

    @Nullable
    default public User getUserById(@Nonnull String id) {
        return this.getUserCache().getElementById(id);
    }

    @Nonnull
    default public SnowflakeCacheView<User> getUserCache() {
        return CacheView.allSnowflakes(() -> this.getShardCache().stream().map(JDA::getUserCache));
    }

    @Nonnull
    default public @Unmodifiable List<User> getUsers() {
        return this.getUserCache().asList();
    }

    public void restart();

    public void restart(int var1);

    default public void setActivity(@Nullable Activity activity) {
        this.setActivityProvider(id -> activity);
    }

    default public void setActivityProvider(@Nullable IntFunction<? extends Activity> activityProvider) {
        this.getShardCache().forEach(jda -> jda.getPresence().setActivity(activityProvider == null ? null : (Activity)activityProvider.apply(jda.getShardInfo().getShardId())));
    }

    default public void setIdle(boolean idle) {
        this.setIdleProvider(id -> idle);
    }

    default public void setIdleProvider(@Nonnull IntFunction<Boolean> idleProvider) {
        this.getShardCache().forEach(jda -> jda.getPresence().setIdle((Boolean)idleProvider.apply(jda.getShardInfo().getShardId())));
    }

    default public void setPresence(@Nullable OnlineStatus status, @Nullable Activity activity) {
        this.setPresenceProvider(id -> status, id -> activity);
    }

    default public void setPresenceProvider(@Nullable IntFunction<OnlineStatus> statusProvider, @Nullable IntFunction<? extends Activity> activityProvider) {
        this.getShardCache().forEach(jda -> jda.getPresence().setPresence(statusProvider == null ? null : (OnlineStatus)((Object)((Object)statusProvider.apply(jda.getShardInfo().getShardId()))), activityProvider == null ? null : (Activity)activityProvider.apply(jda.getShardInfo().getShardId())));
    }

    default public void setStatus(@Nullable OnlineStatus status) {
        this.setStatusProvider(id -> status);
    }

    default public void setStatusProvider(@Nullable IntFunction<OnlineStatus> statusProvider) {
        this.getShardCache().forEach(jda -> jda.getPresence().setStatus(statusProvider == null ? null : (OnlineStatus)((Object)((Object)statusProvider.apply(jda.getShardInfo().getShardId())))));
    }

    public void shutdown();

    public void shutdown(int var1);

    public void start(int var1);

    public void login();
}

