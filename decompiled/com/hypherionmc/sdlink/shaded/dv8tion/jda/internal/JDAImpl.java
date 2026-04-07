/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.MDC
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.GatewayEncoding;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.DefaultSendFactory;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IAudioSendFactory;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationInfo;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RoleConnectionMetadata;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerPack;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GatewayPingEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.StatusChangeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.ShutdownEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InvalidTokenException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.IEventManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.InterfacedEventManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AudioManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Presence;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.GatewayIntent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestRateLimiter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.TestEntitlementCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.EntitlementPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.sharding.ShardManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ChunkingFilter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.Compression;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MemberCachePolicy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.Once;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.SessionController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.ApplicationEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.GuildSetupController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.hooks.EventManagerProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.CommandDataImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AudioManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.DirectAudioControllerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.PresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.Requester;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.CommandCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.CommandEditActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.CommandListUpdateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.GuildActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.TestEntitlementCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.EntitlementPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ShutdownReason;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.AbstractCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.AuthorizationConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.MetaConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.SessionConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.ThreadingConfig;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFactory;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.time.OffsetDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.MDC;

public class JDAImpl
implements JDA {
    public static final Logger LOG = JDALogger.getLog(JDA.class);
    protected final SnowflakeCacheViewImpl<User> userCache = new SnowflakeCacheViewImpl<User>(User.class, User::getName);
    protected final SnowflakeCacheViewImpl<Guild> guildCache = new SnowflakeCacheViewImpl<Guild>(Guild.class, Guild::getName);
    protected final ChannelCacheViewImpl<Channel> channelCache = new ChannelCacheViewImpl<Channel>(Channel.class);
    protected final ArrayDeque<Long> privateChannelLRU = new ArrayDeque();
    protected final AbstractCacheView<AudioManager> audioManagers = new CacheView.SimpleCacheView<AudioManager>(AudioManager.class, m -> m.getGuild().getName());
    protected final PresenceImpl presence;
    protected final Thread shutdownHook;
    protected final EntityBuilder entityBuilder = new EntityBuilder(this);
    protected final EventCache eventCache;
    protected final EventManagerProxy eventManager;
    protected final GuildSetupController guildSetupController;
    protected final DirectAudioControllerImpl audioController;
    protected final AuthorizationConfig authConfig;
    protected final ThreadingConfig threadConfig;
    protected final SessionConfig sessionConfig;
    protected final MetaConfig metaConfig;
    protected final RestConfig restConfig;
    public ShutdownReason shutdownReason = ShutdownReason.USER_SHUTDOWN;
    protected WebSocketClient client;
    protected Requester requester;
    protected IAudioSendFactory audioSendFactory = new DefaultSendFactory();
    protected SelfUser selfUser;
    protected JDA.ShardInfo shardInfo;
    protected long responseTotal;
    protected long gatewayPing = -1L;
    protected String gatewayUrl;
    protected ChunkingFilter chunkingFilter;
    protected String clientId = null;
    protected String requiredScopes = "bot";
    protected ShardManager shardManager = null;
    protected MemberCachePolicy memberCachePolicy = MemberCachePolicy.ALL;
    protected final AtomicReference<JDA.Status> status = new AtomicReference<JDA.Status>(JDA.Status.INITIALIZING);
    protected final ReentrantLock statusLock = new ReentrantLock();
    protected final Condition statusCondition = this.statusLock.newCondition();
    protected final AtomicBoolean requesterShutdown = new AtomicBoolean(false);
    protected final AtomicReference<ShutdownEvent> shutdownEvent = new AtomicReference<Object>(null);

    public JDAImpl(AuthorizationConfig authConfig) {
        this(authConfig, null, null, null, null);
    }

    public JDAImpl(AuthorizationConfig authConfig, SessionConfig sessionConfig, ThreadingConfig threadConfig, MetaConfig metaConfig, RestConfig restConfig) {
        this.authConfig = authConfig;
        this.threadConfig = threadConfig == null ? ThreadingConfig.getDefault() : threadConfig;
        this.sessionConfig = sessionConfig == null ? SessionConfig.getDefault() : sessionConfig;
        this.metaConfig = metaConfig == null ? MetaConfig.getDefault() : metaConfig;
        this.restConfig = restConfig == null ? new RestConfig() : restConfig;
        this.shutdownHook = this.metaConfig.isUseShutdownHook() ? new Thread(this::shutdownNow, "JDA Shutdown Hook") : null;
        this.presence = new PresenceImpl(this);
        this.guildSetupController = new GuildSetupController(this);
        this.audioController = new DirectAudioControllerImpl(this);
        this.eventCache = new EventCache();
        this.eventManager = new EventManagerProxy(new InterfacedEventManager(), this.threadConfig.getEventPool());
    }

    public void handleEvent(@Nonnull GenericEvent event) {
        this.eventManager.handle(event);
    }

    public boolean isRawEvents() {
        return this.sessionConfig.isRawEvents();
    }

    public boolean isEventPassthrough() {
        return this.sessionConfig.isEventPassthrough();
    }

    public boolean isCacheFlagSet(CacheFlag flag) {
        return this.metaConfig.getCacheFlags().contains((Object)flag);
    }

    public boolean isIntent(GatewayIntent intent) {
        int raw = intent.getRawValue();
        return (this.client.getGatewayIntents() & raw) == raw;
    }

    public int getLargeThreshold() {
        return this.sessionConfig.getLargeThreshold();
    }

    public int getMaxBufferSize() {
        return this.metaConfig.getMaxBufferSize();
    }

    public boolean chunkGuild(long id) {
        try {
            return this.isIntent(GatewayIntent.GUILD_MEMBERS) && this.chunkingFilter.filter(id);
        }
        catch (Exception e) {
            LOG.error("Uncaught exception from chunking filter", (Throwable)e);
            return true;
        }
    }

    public void setChunkingFilter(ChunkingFilter filter) {
        this.chunkingFilter = filter;
    }

    public boolean cacheMember(Member member) {
        try {
            return member.getUser().equals(this.getSelfUser()) || this.memberCachePolicy.cacheMember(member);
        }
        catch (Exception e) {
            LOG.error("Uncaught exception from member cache policy", (Throwable)e);
            return true;
        }
    }

    public void setMemberCachePolicy(MemberCachePolicy policy) {
        this.memberCachePolicy = policy;
    }

    public SessionController getSessionController() {
        return this.sessionConfig.getSessionController();
    }

    public GuildSetupController getGuildSetupController() {
        return this.guildSetupController;
    }

    public VoiceDispatchInterceptor getVoiceInterceptor() {
        return this.sessionConfig.getVoiceDispatchInterceptor();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void usedPrivateChannel(long id) {
        ArrayDeque<Long> arrayDeque = this.privateChannelLRU;
        synchronized (arrayDeque) {
            this.privateChannelLRU.remove(id);
            this.privateChannelLRU.addFirst(id);
            if (this.privateChannelLRU.size() > 10) {
                long removed = this.privateChannelLRU.removeLast();
                this.channelCache.remove(ChannelType.PRIVATE, removed);
            }
        }
    }

    public void initRequester() {
        if (this.requester != null) {
            return;
        }
        RestRateLimiter rateLimiter = this.restConfig.getRateLimiterFactory().apply(new RestRateLimiter.RateLimitConfig(this.threadConfig.getRateLimitScheduler(), this.threadConfig.getRateLimitElastic(), this.getSessionController().getRateLimitHandle(), this.sessionConfig.isRelativeRateLimit() && this.restConfig.isRelativeRateLimit()));
        this.requester = new Requester(this, this.authConfig, this.restConfig, rateLimiter);
        this.requester.setRetryOnTimeout(this.sessionConfig.isRetryOnTimeout());
    }

    public int login() {
        return this.login(null, null, Compression.ZLIB, true, GatewayIntent.ALL_INTENTS, GatewayEncoding.JSON);
    }

    public int login(JDA.ShardInfo shardInfo, Compression compression, boolean validateToken, int intents, GatewayEncoding encoding) {
        return this.login(null, shardInfo, compression, validateToken, intents, encoding);
    }

    public int login(String gatewayUrl, JDA.ShardInfo shardInfo, Compression compression, boolean validateToken, int intents, GatewayEncoding encoding) {
        this.shardInfo = shardInfo;
        this.threadConfig.init(this::getIdentifierString);
        this.initRequester();
        this.gatewayUrl = gatewayUrl == null ? this.getGateway() : gatewayUrl;
        Checks.notNull(this.gatewayUrl, "Gateway URL");
        this.setStatus(JDA.Status.LOGGING_IN);
        Map previousContext = null;
        ConcurrentMap<String, String> contextMap = this.metaConfig.getMdcContextMap();
        if (contextMap != null) {
            if (shardInfo != null) {
                contextMap.put("jda.shard", shardInfo.getShardString());
                contextMap.put("jda.shard.id", String.valueOf(shardInfo.getShardId()));
                contextMap.put("jda.shard.total", String.valueOf(shardInfo.getShardTotal()));
            }
            previousContext = MDC.getCopyOfContextMap();
            contextMap.forEach(MDC::put);
            this.requester.setContextReady(true);
        }
        if (validateToken) {
            this.verifyToken();
            LOG.info("Login Successful!");
        }
        this.client = new WebSocketClient(this, compression, intents, encoding);
        if (previousContext != null) {
            previousContext.forEach(MDC::put);
        }
        if (this.shutdownHook != null) {
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }
        return shardInfo == null ? -1 : shardInfo.getShardTotal();
    }

    public String getGateway() {
        return this.getSessionController().getGateway();
    }

    public SessionController.ShardedGateway getShardedGateway() {
        return this.getSessionController().getShardedGateway(this);
    }

    public ConcurrentMap<String, String> getContextMap() {
        return this.metaConfig.getMdcContextMap() == null ? null : new ConcurrentHashMap<String, String>(this.metaConfig.getMdcContextMap());
    }

    public void setContext() {
        if (this.metaConfig.getMdcContextMap() != null) {
            this.metaConfig.getMdcContextMap().forEach(MDC::put);
        }
    }

    public void setToken(String token) {
        this.authConfig.setToken(token);
    }

    public void setStatus(JDA.Status status) {
        StatusChangeEvent event = MiscUtil.locked(this.statusLock, () -> {
            JDA.Status oldStatus = this.status.getAndSet(status);
            this.statusCondition.signalAll();
            return new StatusChangeEvent(this, status, oldStatus);
        });
        if (event.getOldStatus() != event.getNewStatus()) {
            this.handleEvent(event);
        }
    }

    public void verifyToken() {
        RestActionImpl login = new RestActionImpl<DataObject>((JDA)this, Route.Self.GET_SELF.compile(new String[0])){

            @Override
            public void handleResponse(Response response, Request<DataObject> request) {
                if (response.isOk()) {
                    request.onSuccess(response.getObject());
                } else if (response.isRateLimit()) {
                    request.onFailure(new RateLimitedException(request.getRoute(), response.retryAfter));
                } else if (response.code == 401) {
                    request.onSuccess(null);
                } else {
                    request.onFailure(response);
                }
            }
        }.priority();
        try {
            DataObject userResponse = (DataObject)login.complete();
            if (userResponse != null) {
                this.getEntityBuilder().createSelfUser(userResponse);
                return;
            }
            throw new InvalidTokenException();
        }
        catch (Throwable error) {
            this.shutdownNow();
            throw error;
        }
    }

    public AuthorizationConfig getAuthorizationConfig() {
        return this.authConfig;
    }

    @Override
    @Nonnull
    public String getToken() {
        return this.authConfig.getToken();
    }

    @Override
    public boolean isBulkDeleteSplittingEnabled() {
        return this.sessionConfig.isBulkDeleteSplittingEnabled();
    }

    @Override
    public void setAutoReconnect(boolean autoReconnect) {
        this.sessionConfig.setAutoReconnect(autoReconnect);
        WebSocketClient client = this.getClient();
        if (client != null) {
            client.setAutoReconnect(autoReconnect);
        }
    }

    @Override
    public void setRequestTimeoutRetry(boolean retryOnTimeout) {
        this.requester.setRetryOnTimeout(retryOnTimeout);
    }

    @Override
    public boolean isAutoReconnect() {
        return this.sessionConfig.isAutoReconnect();
    }

    @Override
    @Nonnull
    public JDA.Status getStatus() {
        return this.status.get();
    }

    @Override
    @Nonnull
    public EnumSet<GatewayIntent> getGatewayIntents() {
        return GatewayIntent.getIntents(this.client.getGatewayIntents());
    }

    @Override
    @Nonnull
    public EnumSet<CacheFlag> getCacheFlags() {
        return Helpers.copyEnumSet(CacheFlag.class, this.metaConfig.getCacheFlags());
    }

    @Override
    public boolean unloadUser(long userId) {
        if (userId == this.selfUser.getIdLong()) {
            return false;
        }
        User user = this.getUserById(userId);
        if (user == null) {
            return false;
        }
        return this.getGuildCache().stream().filter(guild -> guild.unloadMember(userId)).count() > 0L;
    }

    @Override
    public long getGatewayPing() {
        return this.gatewayPing;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nonnull
    public JDA awaitStatus(@Nonnull JDA.Status status, JDA.Status ... failOn) throws InterruptedException {
        Checks.notNull((Object)status, "Status");
        if (this.getStatus() == JDA.Status.CONNECTED) {
            return this;
        }
        MiscUtil.tryLock(this.statusLock);
        try {
            EnumSet<JDA.Status[]> endCondition = EnumSet.of(status, failOn);
            JDA.Status current = this.getStatus();
            while (!current.isInit() || current.ordinal() < status.ordinal()) {
                if (current == JDA.Status.SHUTDOWN) {
                    throw new IllegalStateException("Was shutdown trying to await status.\nReason: " + this.shutdownReason);
                }
                if (endCondition.contains((Object)current)) {
                    JDAImpl jDAImpl = this;
                    return jDAImpl;
                }
                this.statusCondition.await();
                current = this.getStatus();
            }
        }
        finally {
            this.statusLock.unlock();
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean awaitShutdown(long timeout2, @Nonnull TimeUnit unit) throws InterruptedException {
        long deadline = (timeout2 = unit.toMillis(timeout2)) == 0L ? Long.MAX_VALUE : System.currentTimeMillis() + timeout2;
        MiscUtil.tryLock(this.statusLock);
        try {
            JDA.Status current = this.getStatus();
            while (current != JDA.Status.SHUTDOWN) {
                if (!this.statusCondition.await(deadline - System.currentTimeMillis(), TimeUnit.MILLISECONDS)) {
                    boolean bl = false;
                    return bl;
                }
                current = this.getStatus();
            }
            boolean bl = true;
            return bl;
        }
        finally {
            this.statusLock.unlock();
        }
    }

    @Override
    public int cancelRequests() {
        return this.requester.getRateLimiter().cancelRequests();
    }

    @Override
    @Nonnull
    public ScheduledExecutorService getRateLimitPool() {
        return this.threadConfig.getRateLimitScheduler();
    }

    @Override
    @Nonnull
    public ScheduledExecutorService getGatewayPool() {
        return this.threadConfig.getGatewayPool();
    }

    @Override
    @Nonnull
    public ExecutorService getCallbackPool() {
        return this.threadConfig.getCallbackPool();
    }

    @Override
    @Nonnull
    public OkHttpClient getHttpClient() {
        return this.sessionConfig.getHttpClient();
    }

    @Override
    @Nonnull
    public DirectAudioControllerImpl getDirectAudioController() {
        if (!this.isIntent(GatewayIntent.GUILD_VOICE_STATES)) {
            throw new IllegalStateException("Cannot use audio features with disabled GUILD_VOICE_STATES intent!");
        }
        return this.audioController;
    }

    @Override
    @Nonnull
    public List<Guild> getMutualGuilds(User ... users) {
        Checks.notNull(users, "users");
        return this.getMutualGuilds(Arrays.asList(users));
    }

    @Override
    @Nonnull
    public List<Guild> getMutualGuilds(@Nonnull Collection<User> users) {
        Checks.notNull(users, "users");
        for (User u : users) {
            Checks.notNull(u, "All users");
        }
        return this.getGuilds().stream().filter(guild -> users.stream().allMatch(guild::isMember)).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public CacheRestAction<User> retrieveUserById(long id) {
        return new DeferredRestAction<User, RestAction>(this, User.class, () -> this.isIntent(GatewayIntent.GUILD_MEMBERS) || this.isIntent(GatewayIntent.GUILD_PRESENCES) ? this.getUserById(id) : null, () -> {
            if (id == this.getSelfUser().getIdLong()) {
                return new CompletedRestAction<SelfUser>((JDA)this, this.getSelfUser());
            }
            Route.CompiledRoute route = Route.Users.GET_USER.compile(Long.toUnsignedString(id));
            return new RestActionImpl<User>((JDA)this, route, (response, request) -> this.getEntityBuilder().createUser(response.getObject()));
        });
    }

    @Override
    @Nonnull
    public CacheView<AudioManager> getAudioManagerCache() {
        return this.audioManagers;
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<Guild> getGuildCache() {
        return this.guildCache;
    }

    @Override
    @Nonnull
    public Set<String> getUnavailableGuilds() {
        TLongSet unavailableGuilds = this.guildSetupController.getUnavailableGuilds();
        HashSet<String> copy = new HashSet<String>();
        unavailableGuilds.forEach(id -> copy.add(Long.toUnsignedString(id)));
        return copy;
    }

    @Override
    public boolean isUnavailable(long guildId) {
        return this.guildSetupController.isUnavailable(guildId);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<Role> getRoleCache() {
        return CacheView.allSnowflakes(() -> this.guildCache.stream().map(Guild::getRoleCache));
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<RichCustomEmoji> getEmojiCache() {
        return CacheView.allSnowflakes(() -> this.guildCache.stream().map(Guild::getEmojiCache));
    }

    @Override
    @Nonnull
    public RestAction<ApplicationEmoji> createApplicationEmoji(@Nonnull String name, @Nonnull Icon icon) {
        Checks.inRange(name, 2, 32, "Emoji name");
        Checks.matches(name, Checks.ALPHANUMERIC_WITH_DASH, "Emoji name");
        Checks.notNull(icon, "Emoji icon");
        DataObject body = DataObject.empty();
        body.put("name", name);
        body.put("image", icon.getEncoding());
        Route.CompiledRoute route = Route.Applications.CREATE_APPLICATION_EMOJI.compile(this.getSelfUser().getApplicationId());
        return new RestActionImpl<ApplicationEmoji>((JDA)this, route, body, (response, request) -> {
            DataObject obj = response.getObject();
            SelfUser selfUser = this.getSelfUser();
            return this.entityBuilder.createApplicationEmoji(this, obj, selfUser);
        });
    }

    @Override
    @Nonnull
    public RestAction<List<ApplicationEmoji>> retrieveApplicationEmojis() {
        Route.CompiledRoute route = Route.Applications.GET_APPLICATION_EMOJIS.compile(this.getSelfUser().getApplicationId());
        return new RestActionImpl<List<ApplicationEmoji>>((JDA)this, route, (response, request) -> {
            DataArray emojis = response.getObject().getArray("items");
            ArrayList<ApplicationEmojiImpl> list = new ArrayList<ApplicationEmojiImpl>(emojis.length());
            for (int i = 0; i < emojis.length(); ++i) {
                try {
                    DataObject emoji = emojis.getObject(i);
                    User owner = emoji.optObject("user").map(this.entityBuilder::createUser).orElse(null);
                    list.add(this.entityBuilder.createApplicationEmoji(this, emoji, owner));
                    continue;
                }
                catch (ParsingException e) {
                    LOG.error("Failed to parse application emoji with JSON: {}", (Object)emojis.getObject(i), (Object)e);
                }
            }
            return Collections.unmodifiableList(list);
        });
    }

    @Override
    @Nonnull
    public RestAction<ApplicationEmoji> retrieveApplicationEmojiById(@Nonnull String emojiId) {
        Checks.isSnowflake(emojiId);
        Route.CompiledRoute route = Route.Applications.GET_APPLICATION_EMOJI.compile(this.getSelfUser().getApplicationId(), emojiId);
        return new RestActionImpl<ApplicationEmoji>((JDA)this, route, (response, request) -> {
            DataObject emoji = response.getObject();
            User owner = emoji.optObject("user").map(this.entityBuilder::createUser).orElse(null);
            return this.entityBuilder.createApplicationEmoji(this, emoji, owner);
        });
    }

    @Override
    @Nonnull
    public RestAction<StickerUnion> retrieveSticker(@Nonnull StickerSnowflake sticker) {
        Checks.notNull(sticker, "Sticker");
        Route.CompiledRoute route = Route.Stickers.GET_STICKER.compile(sticker.getId());
        return new RestActionImpl<StickerUnion>((JDA)this, route, (response, request) -> this.entityBuilder.createRichSticker(response.getObject()));
    }

    @Override
    @Nonnull
    public RestAction<List<StickerPack>> retrieveNitroStickerPacks() {
        Route.CompiledRoute route = Route.Stickers.LIST_PACKS.compile(new String[0]);
        return new RestActionImpl<List<StickerPack>>((JDA)this, route, (response, request) -> {
            DataArray array = response.getObject().getArray("sticker_packs");
            ArrayList<StickerPack> packs = new ArrayList<StickerPack>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                DataObject object = null;
                try {
                    object = array.getObject(i);
                    StickerPack pack = this.entityBuilder.createStickerPack(object);
                    packs.add(pack);
                    continue;
                }
                catch (ParsingException ex) {
                    EntityBuilder.LOG.error("Failed to parse sticker pack. JSON: {}", (Object)object);
                }
            }
            return Collections.unmodifiableList(packs);
        });
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<ScheduledEvent> getScheduledEventCache() {
        return CacheView.allSnowflakes(() -> this.guildCache.stream().map(Guild::getScheduledEventCache));
    }

    @Override
    @Nonnull
    public ChannelCacheView<Channel> getChannelCache() {
        return this.channelCache;
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<Category> getCategoryCache() {
        return this.channelCache.ofType(Category.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<TextChannel> getTextChannelCache() {
        return this.channelCache.ofType(TextChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<NewsChannel> getNewsChannelCache() {
        return this.channelCache.ofType(NewsChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return this.channelCache.ofType(VoiceChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<StageChannel> getStageChannelCache() {
        return this.channelCache.ofType(StageChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<ThreadChannel> getThreadChannelCache() {
        return this.channelCache.ofType(ThreadChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<ForumChannel> getForumChannelCache() {
        return this.channelCache.ofType(ForumChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<MediaChannel> getMediaChannelCache() {
        return this.channelCache.ofType(MediaChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<PrivateChannel> getPrivateChannelCache() {
        return this.channelCache.ofType(PrivateChannel.class);
    }

    @Override
    public PrivateChannel getPrivateChannelById(@Nonnull String id) {
        return this.getPrivateChannelById(MiscUtil.parseSnowflake(id));
    }

    @Override
    public PrivateChannel getPrivateChannelById(long id) {
        PrivateChannel channel = JDA.super.getPrivateChannelById(id);
        if (channel != null) {
            this.usedPrivateChannel(id);
        }
        return channel;
    }

    @Override
    public <T extends Channel> T getChannelById(@Nonnull Class<T> type, long id) {
        return (T)((ChannelCacheViewImpl.FilteredCacheView)this.channelCache.ofType(type)).getElementById(id);
    }

    @Override
    public GuildChannel getGuildChannelById(long id) {
        return (GuildChannel)((ChannelCacheViewImpl.FilteredCacheView)this.channelCache.ofType(GuildChannel.class)).getElementById(id);
    }

    @Override
    public GuildChannel getGuildChannelById(@Nonnull ChannelType type, long id) {
        Channel channel = this.channelCache.getElementById(type, id);
        return channel instanceof GuildChannel ? (GuildChannel)channel : null;
    }

    @Override
    @Nonnull
    public CacheRestAction<PrivateChannel> openPrivateChannelById(long userId) {
        if (this.selfUser != null && userId == this.selfUser.getIdLong()) {
            throw new UnsupportedOperationException("Cannot open private channel with yourself!");
        }
        return new DeferredRestAction<PrivateChannel, RestActionImpl>(this, PrivateChannel.class, () -> {
            User user = this.getUserById(userId);
            if (user instanceof UserImpl) {
                return ((UserImpl)user).getPrivateChannel();
            }
            return null;
        }, () -> {
            Route.CompiledRoute route = Route.Self.CREATE_PRIVATE_CHANNEL.compile(new String[0]);
            DataObject body = DataObject.empty().put("recipient_id", userId);
            return new RestActionImpl<PrivateChannel>((JDA)this, route, body, (response, request) -> this.getEntityBuilder().createPrivateChannel(response.getObject()));
        });
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<User> getUserCache() {
        return this.userCache;
    }

    public boolean hasSelfUser() {
        return this.selfUser != null;
    }

    @Override
    @Nonnull
    public SelfUser getSelfUser() {
        if (this.selfUser == null) {
            throw new IllegalStateException("Session is not yet ready!");
        }
        return this.selfUser;
    }

    @Override
    public synchronized void shutdownNow() {
        this.requester.stop(true, this::shutdownRequester);
        this.shutdown();
        this.threadConfig.shutdownNow();
    }

    @Override
    public synchronized void shutdown() {
        JDA.Status status = this.getStatus();
        if (status == JDA.Status.SHUTDOWN || status == JDA.Status.SHUTTING_DOWN) {
            return;
        }
        this.setStatus(JDA.Status.SHUTTING_DOWN);
        WebSocketClient client = this.getClient();
        if (client != null) {
            client.getChunkManager().shutdown();
            client.shutdown();
        } else {
            this.shutdownInternals(new ShutdownEvent(this, OffsetDateTime.now(), 1000));
        }
    }

    public void shutdownInternals(ShutdownEvent event) {
        boolean signal;
        if (this.getStatus() == JDA.Status.SHUTDOWN) {
            return;
        }
        this.closeAudioConnections();
        this.guildSetupController.close();
        this.requester.stop(false, this::shutdownRequester);
        this.threadConfig.shutdown();
        if (this.shutdownHook != null) {
            try {
                Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (signal = MiscUtil.locked(this.statusLock, () -> this.shutdownEvent.getAndSet(event) == null && this.requesterShutdown.get()).booleanValue()) {
            this.signalShutdown();
        }
    }

    public void shutdownRequester() {
        this.threadConfig.shutdownRequester();
        boolean signal = MiscUtil.locked(this.statusLock, () -> !this.requesterShutdown.getAndSet(true) && this.shutdownEvent.get() != null);
        if (signal) {
            this.signalShutdown();
        }
    }

    private void signalShutdown() {
        this.setStatus(JDA.Status.SHUTDOWN);
        this.handleEvent(this.shutdownEvent.get());
    }

    private void closeAudioConnections() {
        this.getAudioManagerCache().stream().map(AudioManagerImpl.class::cast).forEach(m -> m.closeAudioConnection(ConnectionStatus.SHUTTING_DOWN));
    }

    @Override
    public long getResponseTotal() {
        return this.responseTotal;
    }

    @Override
    public int getMaxReconnectDelay() {
        return this.sessionConfig.getMaxReconnectDelay();
    }

    @Override
    @Nonnull
    public JDA.ShardInfo getShardInfo() {
        return this.shardInfo == null ? JDA.ShardInfo.SINGLE : this.shardInfo;
    }

    @Override
    @Nonnull
    public Presence getPresence() {
        return this.presence;
    }

    @Override
    @Nonnull
    public IEventManager getEventManager() {
        return this.eventManager.getSubject();
    }

    @Override
    public void setEventManager(IEventManager eventManager) {
        this.eventManager.setSubject(eventManager);
    }

    @Override
    public void addEventListener(Object ... listeners) {
        Checks.noneNull(listeners, "listeners");
        for (Object listener : listeners) {
            this.eventManager.register(listener);
        }
    }

    @Override
    public void removeEventListener(Object ... listeners) {
        Checks.noneNull(listeners, "listeners");
        for (Object listener : listeners) {
            this.eventManager.unregister(listener);
        }
    }

    @Override
    @Nonnull
    public List<Object> getRegisteredListeners() {
        return this.eventManager.getRegisteredListeners();
    }

    @Override
    @Nonnull
    public <E extends GenericEvent> Once.Builder<E> listenOnce(@Nonnull Class<E> eventType) {
        return new Once.Builder<E>(this, eventType);
    }

    @Override
    @Nonnull
    public RestAction<List<Command>> retrieveCommands(boolean withLocalizations) {
        Route.CompiledRoute route = Route.Interactions.GET_COMMANDS.compile(this.getSelfUser().getApplicationId()).withQueryParams("with_localizations", String.valueOf(withLocalizations));
        return new RestActionImpl<List<Command>>((JDA)this, route, (response, request) -> response.getArray().stream(DataArray::getObject).map(json -> new CommandImpl(this, null, (DataObject)json)).collect(Collectors.toList()));
    }

    @Override
    @Nonnull
    public RestAction<Command> retrieveCommandById(@Nonnull String id) {
        Checks.isSnowflake(id);
        Route.CompiledRoute route = Route.Interactions.GET_COMMAND.compile(this.getSelfUser().getApplicationId(), id);
        return new RestActionImpl<Command>((JDA)this, route, (response, request) -> new CommandImpl(this, null, response.getObject()));
    }

    @Nonnull
    public CommandCreateAction upsertCommand(@Nonnull CommandData command) {
        Checks.notNull(command, "CommandData");
        return new CommandCreateActionImpl(this, (CommandDataImpl)command);
    }

    @Override
    @Nonnull
    public CommandListUpdateAction updateCommands() {
        Route.CompiledRoute route = Route.Interactions.UPDATE_COMMANDS.compile(this.getSelfUser().getApplicationId());
        return new CommandListUpdateActionImpl((JDA)this, null, route);
    }

    @Override
    @Nonnull
    public CommandEditAction editCommandById(@Nonnull String id) {
        Checks.isSnowflake(id);
        return new CommandEditActionImpl((JDA)this, id);
    }

    @Override
    @Nonnull
    public RestAction<Void> deleteCommandById(@Nonnull String commandId) {
        Checks.isSnowflake(commandId);
        Route.CompiledRoute route = Route.Interactions.DELETE_COMMAND.compile(this.getSelfUser().getApplicationId(), commandId);
        return new RestActionImpl<Void>(this, route);
    }

    @Override
    @Nonnull
    public RestAction<List<RoleConnectionMetadata>> retrieveRoleConnectionMetadata() {
        Route.CompiledRoute route = Route.Applications.GET_ROLE_CONNECTION_METADATA.compile(this.getSelfUser().getApplicationId());
        return new RestActionImpl<List<RoleConnectionMetadata>>((JDA)this, route, (response, request) -> response.getArray().stream(DataArray::getObject).map(RoleConnectionMetadata::fromData).collect(Helpers.toUnmodifiableList()));
    }

    @Override
    @Nonnull
    public RestAction<List<RoleConnectionMetadata>> updateRoleConnectionMetadata(@Nonnull Collection<? extends RoleConnectionMetadata> records) {
        Checks.noneNull(records, "Records");
        Checks.check(records.size() <= 5, "An application can have a maximum of %d metadata records", (Object)5);
        Route.CompiledRoute route = Route.Applications.UPDATE_ROLE_CONNECTION_METADATA.compile(this.getSelfUser().getApplicationId());
        DataArray array = DataArray.fromCollection(records);
        RequestBody body = RequestBody.create(array.toJson(), Requester.MEDIA_TYPE_JSON);
        return new RestActionImpl<List<RoleConnectionMetadata>>((JDA)this, route, body, (response, request) -> response.getArray().stream(DataArray::getObject).map(RoleConnectionMetadata::fromData).collect(Helpers.toUnmodifiableList()));
    }

    @Override
    @Nonnull
    public GuildActionImpl createGuild(@Nonnull String name) {
        if (this.guildCache.size() >= 10L) {
            throw new IllegalStateException("Cannot create a Guild with a Bot in 10 or more guilds!");
        }
        return new GuildActionImpl((JDA)this, name);
    }

    @Override
    @Nonnull
    public RestAction<Void> createGuildFromTemplate(@Nonnull String code, @Nonnull String name, Icon icon) {
        if (this.guildCache.size() >= 10L) {
            throw new IllegalStateException("Cannot create a Guild with a Bot in 10 or more guilds!");
        }
        Checks.notBlank(code, "Template code");
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 100, "Name");
        Route.CompiledRoute route = Route.Templates.CREATE_GUILD_FROM_TEMPLATE.compile(code);
        DataObject object = DataObject.empty();
        object.put("name", name);
        if (icon != null) {
            object.put("icon", icon.getEncoding());
        }
        return new RestActionImpl<Void>((JDA)this, route, object);
    }

    @Override
    @Nonnull
    public RestAction<Webhook> retrieveWebhookById(@Nonnull String webhookId) {
        Checks.isSnowflake(webhookId, "Webhook ID");
        Route.CompiledRoute route = Route.Webhooks.GET_WEBHOOK.compile(webhookId);
        return new RestActionImpl<Webhook>((JDA)this, route, (response, request) -> {
            DataObject object = response.getObject();
            EntityBuilder builder = this.getEntityBuilder();
            return builder.createWebhook(object, true);
        });
    }

    @Override
    @Nonnull
    public RestAction<ApplicationInfo> retrieveApplicationInfo() {
        Route.CompiledRoute route = Route.Applications.GET_BOT_APPLICATION.compile(new String[0]);
        return new RestActionImpl<ApplicationInfo>((JDA)this, route, (response, request) -> {
            ApplicationInfo info = this.getEntityBuilder().createApplicationInfo(response.getObject());
            this.clientId = info.getId();
            return info;
        });
    }

    @Override
    @Nonnull
    public EntitlementPaginationAction retrieveEntitlements() {
        return new EntitlementPaginationActionImpl(this);
    }

    @Override
    @Nonnull
    public RestAction<Entitlement> retrieveEntitlementById(long entitlementId) {
        return new RestActionImpl<Entitlement>(this, Route.Applications.GET_ENTITLEMENT.compile(this.getSelfUser().getApplicationId(), Long.toUnsignedString(entitlementId)));
    }

    @Override
    @Nonnull
    public TestEntitlementCreateAction createTestEntitlement(long skuId, long ownerId, @Nonnull TestEntitlementCreateAction.OwnerType ownerType) {
        Checks.notNull((Object)ownerType, "ownerType");
        return new TestEntitlementCreateActionImpl((JDA)this, skuId, ownerId, ownerType);
    }

    @Override
    @Nonnull
    public RestAction<Void> deleteTestEntitlement(long entitlementId) {
        Route.CompiledRoute route = Route.Applications.DELETE_TEST_ENTITLEMENT.compile(this.getSelfUser().getApplicationId(), Long.toUnsignedString(entitlementId));
        return new RestActionImpl<Void>(this, route);
    }

    @Override
    @Nonnull
    public JDA setRequiredScopes(@Nonnull Collection<String> scopes) {
        Checks.noneNull(scopes, "Scopes");
        this.requiredScopes = String.join((CharSequence)"+", scopes);
        if (!this.requiredScopes.contains("bot")) {
            this.requiredScopes = this.requiredScopes.isEmpty() ? "bot" : this.requiredScopes + "+bot";
        }
        return this;
    }

    @Override
    @Nonnull
    public String getInviteUrl(Permission ... permissions) {
        StringBuilder builder = this.buildBaseInviteUrl();
        if (permissions != null && permissions.length > 0) {
            builder.append("&permissions=").append(Permission.getRaw(permissions));
        }
        return builder.toString();
    }

    @Override
    @Nonnull
    public String getInviteUrl(Collection<Permission> permissions) {
        StringBuilder builder = this.buildBaseInviteUrl();
        if (permissions != null && !permissions.isEmpty()) {
            builder.append("&permissions=").append(Permission.getRaw(permissions));
        }
        return builder.toString();
    }

    private StringBuilder buildBaseInviteUrl() {
        if (this.clientId == null) {
            if (this.selfUser != null) {
                this.clientId = this.selfUser.getApplicationId();
            } else {
                this.retrieveApplicationInfo().complete();
            }
        }
        StringBuilder builder = new StringBuilder("https://discord.com/oauth2/authorize?client_id=");
        builder.append(this.clientId);
        builder.append("&scope=").append(this.requiredScopes);
        return builder;
    }

    public void setShardManager(ShardManager shardManager) {
        this.shardManager = shardManager;
    }

    @Override
    public ShardManager getShardManager() {
        return this.shardManager;
    }

    public EntityBuilder getEntityBuilder() {
        return this.entityBuilder;
    }

    public IAudioSendFactory getAudioSendFactory() {
        return this.audioSendFactory;
    }

    public void setAudioSendFactory(IAudioSendFactory factory2) {
        Checks.notNull(factory2, "Provided IAudioSendFactory");
        this.audioSendFactory = factory2;
    }

    public void setGatewayPing(long ping) {
        long oldPing = this.gatewayPing;
        this.gatewayPing = ping;
        this.handleEvent(new GatewayPingEvent(this, oldPing));
    }

    public Requester getRequester() {
        return this.requester;
    }

    public WebSocketFactory getWebSocketFactory() {
        return this.sessionConfig.getWebSocketFactory();
    }

    public WebSocketClient getClient() {
        return this.client;
    }

    public SnowflakeCacheViewImpl<User> getUsersView() {
        return this.userCache;
    }

    public SnowflakeCacheViewImpl<Guild> getGuildsView() {
        return this.guildCache;
    }

    public ChannelCacheViewImpl<Channel> getChannelsView() {
        return this.channelCache;
    }

    public AbstractCacheView<AudioManager> getAudioManagersView() {
        return this.audioManagers;
    }

    public void setSelfUser(SelfUser selfUser) {
        try (UnlockHook hook = this.userCache.writeLock();){
            this.userCache.getMap().put(selfUser.getIdLong(), selfUser);
        }
        this.selfUser = selfUser;
    }

    public void setResponseTotal(int responseTotal) {
        this.responseTotal = responseTotal;
    }

    public String getIdentifierString() {
        if (this.shardInfo != null) {
            return "JDA " + this.shardInfo.getShardString();
        }
        return "JDA";
    }

    public EventCache getEventCache() {
        return this.eventCache;
    }

    public String getGatewayUrl() {
        if (this.gatewayUrl == null) {
            this.gatewayUrl = this.getGateway();
            return this.gatewayUrl;
        }
        return this.gatewayUrl;
    }

    public void resetGatewayUrl() {
        this.gatewayUrl = null;
    }

    public ScheduledExecutorService getAudioLifeCyclePool() {
        return this.threadConfig.getAudioPool(this::getIdentifierString);
    }
}

