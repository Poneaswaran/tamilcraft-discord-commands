/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.BulkBanResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildWelcomeScreen;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.VanityInvite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModRule;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.build.AutoModRuleData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StandardSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.templates.Template;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.HierarchyException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.PermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.PrivilegeConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.privileges.IntegrationPrivilege;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AudioManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AutoModRuleManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildStickerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildWelcomeScreenManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.GatewayIntent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MemberAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.RoleAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ScheduledEventAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.RoleOrderAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.MemberCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.concurrent.Task;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberPresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.WebhookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod.AutoModRuleImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.RichCustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.CommandDataImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AudioManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AutoModRuleManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.GuildManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.GuildStickerManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.GuildWelcomeScreenManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.MemberChunkManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.Requester;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.ChannelActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.CommandCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.CommandEditActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.CommandListUpdateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.MemberActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.RoleActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.ScheduledEventActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.order.CategoryOrderActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.order.ChannelOrderActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.order.RoleOrderActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.AuditLogPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.BanPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.AbstractCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.MemberCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedSnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.concurrent.task.GatewayTask;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.MultipartBody;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuildImpl
implements Guild {
    private final long id;
    private final JDAImpl api;
    private final SortedSnowflakeCacheViewImpl<ScheduledEvent> scheduledEventCache = new SortedSnowflakeCacheViewImpl<ScheduledEvent>(ScheduledEvent.class, ScheduledEvent::getName, Comparator.naturalOrder());
    private final SortedChannelCacheViewImpl<GuildChannel> channelCache = new SortedChannelCacheViewImpl<GuildChannel>(GuildChannel.class);
    private final SortedSnowflakeCacheViewImpl<Role> roleCache = new SortedSnowflakeCacheViewImpl<Role>(Role.class, Role::getName, Comparator.reverseOrder());
    private final SnowflakeCacheViewImpl<RichCustomEmoji> emojicache = new SnowflakeCacheViewImpl<RichCustomEmoji>(RichCustomEmoji.class, Emoji::getName);
    private final SnowflakeCacheViewImpl<GuildSticker> stickerCache = new SnowflakeCacheViewImpl<GuildSticker>(GuildSticker.class, Sticker::getName);
    private final MemberCacheViewImpl memberCache = new MemberCacheViewImpl();
    private final CacheView.SimpleCacheView<MemberPresenceImpl> memberPresences;
    private CompletableFuture<Void> pendingRequestToSpeak;
    private Member owner;
    private String name;
    private String iconId;
    private String splashId;
    private String vanityCode;
    private String description;
    private String banner;
    private int maxPresences;
    private int maxMembers;
    private int boostCount;
    private long ownerId;
    private Set<String> features;
    private VoiceChannel afkChannel;
    private TextChannel systemChannel;
    private TextChannel rulesChannel;
    private TextChannel communityUpdatesChannel;
    private TextChannel safetyAlertsChannel;
    private Role publicRole;
    private Guild.VerificationLevel verificationLevel = Guild.VerificationLevel.UNKNOWN;
    private Guild.NotificationLevel defaultNotificationLevel = Guild.NotificationLevel.UNKNOWN;
    private Guild.MFALevel mfaLevel = Guild.MFALevel.UNKNOWN;
    private Guild.ExplicitContentLevel explicitContentLevel = Guild.ExplicitContentLevel.UNKNOWN;
    private Guild.NSFWLevel nsfwLevel = Guild.NSFWLevel.UNKNOWN;
    private Guild.Timeout afkTimeout;
    private Guild.BoostTier boostTier = Guild.BoostTier.NONE;
    private DiscordLocale preferredLocale = DiscordLocale.ENGLISH_US;
    private int memberCount;
    private boolean boostProgressBarEnabled;

    public GuildImpl(JDAImpl api, long id) {
        this.id = id;
        this.api = api;
        this.memberPresences = api.getCacheFlags().stream().anyMatch(CacheFlag::isPresence) ? new CacheView.SimpleCacheView<MemberPresenceImpl>(MemberPresenceImpl.class, null) : null;
    }

    public void invalidate() {
        this.getJDA().getGuildsView().remove(this.id);
        ChannelCacheViewImpl<Channel> channelsView = this.getJDA().getChannelsView();
        try (UnlockHook hook = channelsView.writeLock();){
            this.getChannels().forEach(channel -> channelsView.remove(channel.getType(), channel.getIdLong()));
        }
        this.getJDA().getClient().removeAudioConnection(this.id);
        AbstractCacheView<AudioManager> audioManagerView = this.getJDA().getAudioManagersView();
        AudioManagerImpl manager = (AudioManagerImpl)audioManagerView.get(this.id);
        if (manager != null) {
            manager.closeAudioConnection(ConnectionStatus.DISCONNECTED_REMOVED_FROM_GUILD);
        }
        audioManagerView.remove(this.id);
        TLongSet memberIds = this.getMembersView().keySet();
        this.getJDA().getGuildCache().stream().map(GuildImpl.class::cast).forEach(g -> memberIds.removeAll(g.getMembersView().keySet()));
        SnowflakeCacheViewImpl<User> userView = this.getJDA().getUsersView();
        try (UnlockHook hook = userView.writeLock();){
            long selfId = this.getJDA().getSelfUser().getIdLong();
            memberIds.forEach(memberId -> {
                if (memberId == selfId) {
                    return true;
                }
                userView.remove(memberId);
                this.getJDA().getEventCache().clear(EventCache.Type.USER, memberId);
                return true;
            });
        }
    }

    public void uncacheChannel(GuildChannel channel, boolean keepThreads) {
        long id = channel.getIdLong();
        if (this.channelCache.remove(channel.getType(), id) == null) {
            return;
        }
        this.api.getChannelsView().remove(channel.getType(), id);
        if (!keepThreads && channel instanceof IThreadContainer) {
            SortedChannelCacheViewImpl<GuildChannel> localView = this.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.api.getChannelsView();
            Predicate<ThreadChannel> predicate = thread -> channel.equals(thread.getParentChannel());
            try (UnlockHook hook1 = localView.writeLock();
                 UnlockHook hook2 = globalView.writeLock();){
                localView.removeIf(ThreadChannel.class, predicate);
                globalView.removeIf(ThreadChannel.class, predicate);
            }
        }
    }

    @Override
    @Nonnull
    public RestAction<List<Command>> retrieveCommands(boolean withLocalizations) {
        Route.CompiledRoute route = Route.Interactions.GET_GUILD_COMMANDS.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId()).withQueryParams("with_localizations", String.valueOf(withLocalizations));
        return new RestActionImpl<List<Command>>((JDA)this.getJDA(), route, (response, request) -> response.getArray().stream(DataArray::getObject).map(json -> new CommandImpl(this.getJDA(), this, (DataObject)json)).collect(Collectors.toList()));
    }

    @Override
    @Nonnull
    public RestAction<Command> retrieveCommandById(@Nonnull String id) {
        Checks.isSnowflake(id);
        Route.CompiledRoute route = Route.Interactions.GET_GUILD_COMMAND.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId(), id);
        return new RestActionImpl<Command>((JDA)this.getJDA(), route, (response, request) -> new CommandImpl(this.getJDA(), this, response.getObject()));
    }

    @Nonnull
    public CommandCreateAction upsertCommand(@Nonnull CommandData command) {
        Checks.notNull(command, "CommandData");
        return new CommandCreateActionImpl(this, (CommandDataImpl)command);
    }

    @Override
    @Nonnull
    public CommandListUpdateAction updateCommands() {
        Route.CompiledRoute route = Route.Interactions.UPDATE_GUILD_COMMANDS.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId());
        return new CommandListUpdateActionImpl((JDA)this.getJDA(), this, route);
    }

    @Override
    @Nonnull
    public CommandEditAction editCommandById(@Nonnull String id) {
        Checks.isSnowflake(id);
        return new CommandEditActionImpl(this, id);
    }

    @Override
    @Nonnull
    public RestAction<Void> deleteCommandById(@Nonnull String commandId) {
        Checks.isSnowflake(commandId);
        Route.CompiledRoute route = Route.Interactions.DELETE_GUILD_COMMAND.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId(), commandId);
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public RestAction<List<IntegrationPrivilege>> retrieveIntegrationPrivilegesById(@Nonnull String targetId) {
        Checks.isSnowflake(targetId, "ID");
        Route.CompiledRoute route = Route.Interactions.GET_COMMAND_PERMISSIONS.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId(), targetId);
        return new RestActionImpl<List<IntegrationPrivilege>>((JDA)this.getJDA(), route, (response, request) -> this.parsePrivilegesList(response.getObject()));
    }

    @Override
    @Nonnull
    public RestAction<PrivilegeConfig> retrieveCommandPrivileges() {
        Route.CompiledRoute route = Route.Interactions.GET_ALL_COMMAND_PERMISSIONS.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId());
        return new RestActionImpl<PrivilegeConfig>((JDA)this.getJDA(), route, (response, request) -> {
            HashMap<String, List<IntegrationPrivilege>> privileges = new HashMap<String, List<IntegrationPrivilege>>();
            response.getArray().stream(DataArray::getObject).forEach(obj -> {
                String id = obj.getString("id");
                List<IntegrationPrivilege> list = Collections.unmodifiableList(this.parsePrivilegesList((DataObject)obj));
                privileges.put(id, list);
            });
            return new PrivilegeConfig(this, privileges);
        });
    }

    private List<IntegrationPrivilege> parsePrivilegesList(DataObject obj) {
        return obj.getArray("permissions").stream(DataArray::getObject).map(this::parsePrivilege).collect(Collectors.toList());
    }

    private IntegrationPrivilege parsePrivilege(DataObject data) {
        IntegrationPrivilege.Type type = IntegrationPrivilege.Type.fromKey(data.getInt("type", 1));
        boolean enabled = data.getBoolean("permission");
        return new IntegrationPrivilege(this, type, enabled, data.getUnsignedLong("id"));
    }

    @Override
    @Nonnull
    public RestAction<EnumSet<Region>> retrieveRegions(boolean includeDeprecated) {
        Route.CompiledRoute route = Route.Guilds.GET_VOICE_REGIONS.compile(this.getId());
        return new RestActionImpl<EnumSet<Region>>((JDA)this.getJDA(), route, (response, request) -> {
            EnumSet<Region> set = EnumSet.noneOf(Region.class);
            DataArray arr = response.getArray();
            for (int i = 0; i < arr.length(); ++i) {
                String id;
                Region region;
                DataObject obj = arr.getObject(i);
                if (!includeDeprecated && obj.getBoolean("deprecated") || (region = Region.fromKey(id = obj.getString("id", ""))) == Region.UNKNOWN) continue;
                set.add(region);
            }
            return set;
        });
    }

    @Override
    @Nonnull
    public RestAction<List<AutoModRule>> retrieveAutoModRules() {
        this.checkPermission(Permission.MANAGE_SERVER);
        Route.CompiledRoute route = Route.AutoModeration.LIST_RULES.compile(this.getId());
        return new RestActionImpl<List<AutoModRule>>((JDA)this.api, route, (response, request) -> {
            DataArray array = response.getArray();
            ArrayList<AutoModRuleImpl> rules = new ArrayList<AutoModRuleImpl>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                try {
                    DataObject obj = array.getObject(i);
                    rules.add(AutoModRuleImpl.fromData(this, obj));
                    continue;
                }
                catch (ParsingException exception) {
                    EntityBuilder.LOG.error("Failed to parse AutoModRule", (Throwable)exception);
                }
            }
            return Collections.unmodifiableList(rules);
        });
    }

    @Override
    @Nonnull
    public RestAction<AutoModRule> retrieveAutoModRuleById(@Nonnull String id) {
        Checks.isSnowflake(id);
        this.checkPermission(Permission.MANAGE_SERVER);
        Route.CompiledRoute route = Route.AutoModeration.GET_RULE.compile(this.getId(), id);
        return new RestActionImpl<AutoModRule>((JDA)this.api, route, (response, request) -> AutoModRuleImpl.fromData(this, response.getObject()));
    }

    @Override
    @Nonnull
    public AuditableRestAction<AutoModRule> createAutoModRule(@Nonnull AutoModRuleData rule) {
        Checks.notNull(rule, "AutoMod Rule");
        rule.getRequiredPermissions().forEach(this::checkPermission);
        Route.CompiledRoute route = Route.AutoModeration.CREATE_RULE.compile(this.getId());
        return new AuditableRestActionImpl<AutoModRule>((JDA)this.api, route, rule.toData(), (response, request) -> AutoModRuleImpl.fromData(this, response.getObject()));
    }

    @Override
    @Nonnull
    public AutoModRuleManager modifyAutoModRuleById(@Nonnull String id) {
        Checks.isSnowflake(id);
        this.checkPermission(Permission.MANAGE_SERVER);
        return new AutoModRuleManagerImpl(this, id);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> deleteAutoModRuleById(@Nonnull String id) {
        Checks.isSnowflake(id);
        this.checkPermission(Permission.MANAGE_SERVER);
        Route.CompiledRoute route = Route.AutoModeration.DELETE_RULE.compile(this.getId(), id);
        return new AuditableRestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public MemberAction addMember(@Nonnull String accessToken, @Nonnull UserSnowflake user) {
        Checks.notBlank(accessToken, "Access-Token");
        Checks.notNull(user, "User");
        Checks.check(!this.isMember(user), "User is already in this guild");
        if (!this.getSelfMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {
            throw new InsufficientPermissionException(this, Permission.CREATE_INSTANT_INVITE);
        }
        return new MemberActionImpl((JDA)this.getJDA(), this, user.getId(), accessToken);
    }

    @Override
    public boolean isLoaded() {
        return this.getJDA().isIntent(GatewayIntent.GUILD_MEMBERS) && (long)this.getMemberCount() <= this.getMemberCache().size();
    }

    @Override
    public void pruneMemberCache() {
        try (UnlockHook h = this.memberCache.writeLock();){
            EntityBuilder builder = this.getJDA().getEntityBuilder();
            Set<Member> members = this.memberCache.asSet();
            members.forEach(m -> builder.updateMemberCache((MemberImpl)m));
        }
    }

    @Override
    public boolean unloadMember(long userId) {
        if (userId == this.api.getSelfUser().getIdLong()) {
            return false;
        }
        MemberImpl member = (MemberImpl)this.getMemberById(userId);
        if (member == null) {
            return false;
        }
        this.api.getEntityBuilder().updateMemberCache(member, true);
        return true;
    }

    @Override
    public int getMemberCount() {
        return this.memberCount;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public String getIconId() {
        return this.iconId;
    }

    @Override
    @Nonnull
    public Set<String> getFeatures() {
        return this.features;
    }

    @Override
    public String getSplashId() {
        return this.splashId;
    }

    @Override
    @Nullable
    public String getVanityCode() {
        return this.vanityCode;
    }

    @Override
    @Nonnull
    public RestAction<VanityInvite> retrieveVanityInvite() {
        this.checkPermission(Permission.MANAGE_SERVER);
        JDAImpl api = this.getJDA();
        Route.CompiledRoute route = Route.Guilds.GET_VANITY_URL.compile(this.getId());
        return new RestActionImpl<VanityInvite>((JDA)api, route, (response, request) -> new VanityInvite(this.vanityCode, response.getObject().getInt("uses")));
    }

    @Override
    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Override
    @Nonnull
    public DiscordLocale getLocale() {
        return this.preferredLocale;
    }

    @Override
    @Nullable
    public String getBannerId() {
        return this.banner;
    }

    @Override
    @Nonnull
    public Guild.BoostTier getBoostTier() {
        return this.boostTier;
    }

    @Override
    public int getBoostCount() {
        return this.boostCount;
    }

    @Override
    @Nonnull
    public List<Member> getBoosters() {
        return this.memberCache.applyStream(members -> members.filter(m -> m.getTimeBoosted() != null).sorted(Comparator.comparing(Member::getTimeBoosted)).collect(Helpers.toUnmodifiableList()));
    }

    @Override
    public int getMaxMembers() {
        return this.maxMembers;
    }

    @Override
    public int getMaxPresences() {
        return this.maxPresences;
    }

    @Override
    @Nonnull
    public RestAction<Guild.MetaData> retrieveMetaData() {
        Route.CompiledRoute route = Route.Guilds.GET_GUILD.compile(this.getId());
        route = route.withQueryParams("with_counts", "true");
        return new RestActionImpl<Guild.MetaData>((JDA)this.getJDA(), route, (response, request) -> {
            DataObject json = response.getObject();
            int memberLimit = json.getInt("max_members", 0);
            int presenceLimit = json.getInt("max_presences", 5000);
            this.maxMembers = memberLimit;
            this.maxPresences = presenceLimit;
            int approxMembers = json.getInt("approximate_member_count", this.memberCount);
            int approxPresence = json.getInt("approximate_presence_count", 0);
            return new Guild.MetaData(memberLimit, presenceLimit, approxPresence, approxMembers);
        });
    }

    @Override
    public VoiceChannel getAfkChannel() {
        return this.afkChannel;
    }

    @Override
    public TextChannel getSystemChannel() {
        return this.systemChannel;
    }

    @Override
    public TextChannel getRulesChannel() {
        return this.rulesChannel;
    }

    @Override
    @Nonnull
    public CacheRestAction<ScheduledEvent> retrieveScheduledEventById(@Nonnull String id) {
        Checks.isSnowflake(id);
        return new DeferredRestAction<ScheduledEvent, RestActionImpl>(this.getJDA(), ScheduledEvent.class, () -> this.getScheduledEventById(id), () -> {
            Route.CompiledRoute route = Route.Guilds.GET_SCHEDULED_EVENT.compile(this.getId(), id);
            return new RestActionImpl<ScheduledEvent>((JDA)this.getJDA(), route, (response, request) -> this.api.getEntityBuilder().createScheduledEvent(this, response.getObject()));
        });
    }

    @Override
    @Nonnull
    public CacheRestAction<ScheduledEvent> retrieveScheduledEventById(long id) {
        return this.retrieveScheduledEventById(Long.toUnsignedString(id));
    }

    @Override
    @Nonnull
    public ScheduledEventAction createScheduledEvent(@Nonnull String name, @Nonnull String location, @Nonnull OffsetDateTime startTime, @Nonnull OffsetDateTime endTime) {
        this.checkPermission(Permission.MANAGE_EVENTS);
        return new ScheduledEventActionImpl(name, location, startTime, endTime, this);
    }

    @Override
    @Nonnull
    public ScheduledEventAction createScheduledEvent(@Nonnull String name, @Nonnull GuildChannel channel, @Nonnull OffsetDateTime startTime) {
        this.checkPermission(Permission.MANAGE_EVENTS);
        return new ScheduledEventActionImpl(name, channel, startTime, this);
    }

    @Override
    public TextChannel getCommunityUpdatesChannel() {
        return this.communityUpdatesChannel;
    }

    @Override
    @Nullable
    public TextChannel getSafetyAlertsChannel() {
        return this.safetyAlertsChannel;
    }

    @Override
    @Nonnull
    public RestAction<List<Webhook>> retrieveWebhooks() {
        if (!this.getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
            throw new InsufficientPermissionException(this, Permission.MANAGE_WEBHOOKS);
        }
        Route.CompiledRoute route = Route.Guilds.GET_WEBHOOKS.compile(this.getId());
        return new RestActionImpl<List<Webhook>>((JDA)this.getJDA(), route, (response, request) -> {
            DataArray array = response.getArray();
            ArrayList<WebhookImpl> webhooks = new ArrayList<WebhookImpl>(array.length());
            EntityBuilder builder = this.api.getEntityBuilder();
            for (int i = 0; i < array.length(); ++i) {
                try {
                    webhooks.add(builder.createWebhook(array.getObject(i)));
                    continue;
                }
                catch (Exception e) {
                    JDAImpl.LOG.error("Error creating webhook from json", (Throwable)e);
                }
            }
            return Collections.unmodifiableList(webhooks);
        });
    }

    @Override
    public Member getOwner() {
        return this.owner;
    }

    @Override
    public long getOwnerIdLong() {
        return this.ownerId;
    }

    @Override
    @Nonnull
    public Guild.Timeout getAfkTimeout() {
        return this.afkTimeout;
    }

    @Override
    public boolean isMember(@Nonnull UserSnowflake user) {
        return this.memberCache.get(user.getIdLong()) != null;
    }

    @Override
    @Nonnull
    public Member getSelfMember() {
        Member member = this.getMember(this.getJDA().getSelfUser());
        if (member == null) {
            throw new IllegalStateException("Guild does not have a self member");
        }
        return member;
    }

    @Override
    public Member getMember(@Nonnull UserSnowflake user) {
        Checks.notNull(user, "User");
        return this.getMemberById(user.getIdLong());
    }

    @Override
    @Nonnull
    public MemberCacheView getMemberCache() {
        return this.memberCache;
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<ScheduledEvent> getScheduledEventCache() {
        return this.scheduledEventCache;
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<Category> getCategoryCache() {
        return this.channelCache.ofType(Category.class);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<TextChannel> getTextChannelCache() {
        return this.channelCache.ofType(TextChannel.class);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<NewsChannel> getNewsChannelCache() {
        return this.channelCache.ofType(NewsChannel.class);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return this.channelCache.ofType(VoiceChannel.class);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<ForumChannel> getForumChannelCache() {
        return this.channelCache.ofType(ForumChannel.class);
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<MediaChannel> getMediaChannelCache() {
        return this.channelCache.ofType(MediaChannel.class);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<StageChannel> getStageChannelCache() {
        return this.channelCache.ofType(StageChannel.class);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<ThreadChannel> getThreadChannelCache() {
        return this.channelCache.ofType(ThreadChannel.class);
    }

    @Override
    @Nonnull
    public SortedChannelCacheViewImpl<GuildChannel> getChannelCache() {
        return this.channelCache;
    }

    @Override
    @Nullable
    public GuildChannel getGuildChannelById(long id) {
        return (GuildChannel)this.channelCache.getElementById(id);
    }

    @Override
    public GuildChannel getGuildChannelById(@Nonnull ChannelType type, long id) {
        return (GuildChannel)this.channelCache.getElementById(type, id);
    }

    @Override
    @Nonnull
    public SortedSnowflakeCacheView<Role> getRoleCache() {
        return this.roleCache;
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<RichCustomEmoji> getEmojiCache() {
        return this.emojicache;
    }

    @Override
    @Nonnull
    public SnowflakeCacheView<GuildSticker> getStickerCache() {
        return this.stickerCache;
    }

    @Override
    @Nonnull
    public List<GuildChannel> getChannels(boolean includeHidden) {
        if (includeHidden) {
            return this.channelCache.applyStream(stream -> stream.filter(it -> !it.getType().isThread()).sorted().collect(Helpers.toUnmodifiableList()));
        }
        Member self = this.getSelfMember();
        TreeSet channels = new TreeSet();
        ((SortedChannelCacheViewImpl.SortedFilteredCacheView)this.channelCache.ofType(ICategorizableChannel.class)).forEachUnordered(channel -> {
            if (channel.getType().isThread() || !self.hasPermission((GuildChannel)channel, Permission.VIEW_CHANNEL)) {
                return;
            }
            Category category = channel.getParentCategory();
            channels.add(channel);
            if (category != null) {
                channels.add(category);
            }
        });
        return Collections.unmodifiableList(new ArrayList(channels));
    }

    @Override
    @Nonnull
    public RestAction<List<RichCustomEmoji>> retrieveEmojis() {
        Route.CompiledRoute route = Route.Emojis.GET_EMOJIS.compile(this.getId());
        return new RestActionImpl<List<RichCustomEmoji>>((JDA)this.getJDA(), route, (response, request) -> {
            EntityBuilder builder = this.getJDA().getEntityBuilder();
            DataArray emojis = response.getArray();
            ArrayList<RichCustomEmojiImpl> list = new ArrayList<RichCustomEmojiImpl>(emojis.length());
            for (int i = 0; i < emojis.length(); ++i) {
                DataObject emoji = emojis.getObject(i);
                list.add(builder.createEmoji(this, emoji));
            }
            return Collections.unmodifiableList(list);
        });
    }

    @Override
    @Nonnull
    public RestAction<RichCustomEmoji> retrieveEmojiById(@Nonnull String id) {
        Checks.isSnowflake(id, "Emoji ID");
        JDAImpl jda = this.getJDA();
        return new DeferredRestAction<RichCustomEmoji, AuditableRestActionImpl>(jda, RichCustomEmoji.class, () -> {
            RichCustomEmoji emoji = this.getEmojiById(id);
            if (!(emoji == null || emoji.getOwner() == null && this.getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS))) {
                return emoji;
            }
            return null;
        }, () -> {
            Route.CompiledRoute route = Route.Emojis.GET_EMOJI.compile(this.getId(), id);
            return new AuditableRestActionImpl<RichCustomEmoji>((JDA)jda, route, (response, request) -> {
                EntityBuilder builder = this.getJDA().getEntityBuilder();
                return builder.createEmoji(this, response.getObject());
            });
        });
    }

    @Override
    @Nonnull
    public RestAction<List<GuildSticker>> retrieveStickers() {
        Route.CompiledRoute route = Route.Stickers.GET_GUILD_STICKERS.compile(this.getId());
        return new RestActionImpl<List<GuildSticker>>((JDA)this.getJDA(), route, (response, request) -> {
            DataArray array = response.getArray();
            ArrayList<GuildSticker> stickers = new ArrayList<GuildSticker>(array.length());
            EntityBuilder builder = this.api.getEntityBuilder();
            for (int i = 0; i < array.length(); ++i) {
                DataObject object = null;
                try {
                    object = array.getObject(i);
                    GuildSticker sticker = (GuildSticker)((Object)builder.createRichSticker(object));
                    stickers.add(sticker);
                    continue;
                }
                catch (ParsingException | ClassCastException ex) {
                    EntityBuilder.LOG.error("Failed to parse sticker for JSON: {}", (Object)object, (Object)ex);
                }
            }
            return Collections.unmodifiableList(stickers);
        });
    }

    @Override
    @Nonnull
    public RestAction<GuildSticker> retrieveSticker(@Nonnull StickerSnowflake sticker) {
        Checks.notNull(sticker, "Sticker");
        Route.CompiledRoute route = Route.Stickers.GET_GUILD_STICKER.compile(this.getId(), sticker.getId());
        return new RestActionImpl<GuildSticker>((JDA)this.getJDA(), route, (response, request) -> {
            DataObject object = response.getObject();
            EntityBuilder builder = this.api.getEntityBuilder();
            return (GuildSticker)((Object)builder.createRichSticker(object));
        });
    }

    @Override
    @Nonnull
    public GuildStickerManager editSticker(@Nonnull StickerSnowflake sticker) {
        Checks.notNull(sticker, "Sticker");
        if (sticker instanceof GuildSticker) {
            Checks.check(((GuildSticker)sticker).getGuildIdLong() == this.id, "Cannot edit a sticker from another guild!");
        }
        Checks.check(!(sticker instanceof StandardSticker), "Cannot edit a standard sticker.");
        return new GuildStickerManagerImpl(this, this.id, sticker);
    }

    @Override
    @Nonnull
    public BanPaginationActionImpl retrieveBanList() {
        if (!this.getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            throw new InsufficientPermissionException(this, Permission.BAN_MEMBERS);
        }
        return new BanPaginationActionImpl(this);
    }

    @Override
    @Nonnull
    public RestAction<Guild.Ban> retrieveBan(@Nonnull UserSnowflake user) {
        if (!this.getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            throw new InsufficientPermissionException(this, Permission.BAN_MEMBERS);
        }
        Checks.notNull(user, "User");
        Route.CompiledRoute route = Route.Guilds.GET_BAN.compile(this.getId(), user.getId());
        return new RestActionImpl<Guild.Ban>((JDA)this.getJDA(), route, (response, request) -> {
            EntityBuilder builder = this.api.getEntityBuilder();
            DataObject bannedObj = response.getObject();
            DataObject userJson = bannedObj.getObject("user");
            return new Guild.Ban(builder.createUser(userJson), bannedObj.getString("reason", null));
        });
    }

    @Override
    @Nonnull
    public RestAction<Integer> retrievePrunableMemberCount(int days) {
        if (!this.getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
            throw new InsufficientPermissionException(this, Permission.KICK_MEMBERS);
        }
        Checks.check(days >= 1 && days <= 30, "Provided %d days must be between 1 and 30.", (Object)days);
        Route.CompiledRoute route = Route.Guilds.PRUNABLE_COUNT.compile(this.getId()).withQueryParams("days", Integer.toString(days));
        return new RestActionImpl<Integer>((JDA)this.getJDA(), route, (response, request) -> response.getObject().getInt("pruned"));
    }

    @Override
    @Nonnull
    public Role getPublicRole() {
        return this.publicRole;
    }

    @Override
    @Nullable
    public DefaultGuildChannelUnion getDefaultChannel() {
        Role role = this.getPublicRole();
        return Stream.concat(this.getTextChannelCache().stream(), this.getNewsChannelCache().stream()).filter(c -> role.hasPermission((GuildChannel)c, Permission.VIEW_CHANNEL)).min(Comparator.naturalOrder()).orElse(null);
    }

    @Override
    @Nonnull
    public GuildManager getManager() {
        return new GuildManagerImpl(this);
    }

    @Override
    public boolean isBoostProgressBarEnabled() {
        return this.boostProgressBarEnabled;
    }

    @Override
    @Nonnull
    public AuditLogPaginationAction retrieveAuditLogs() {
        return new AuditLogPaginationActionImpl(this);
    }

    @Override
    @Nonnull
    public RestAction<Void> leave() {
        if (this.getSelfMember().isOwner()) {
            throw new IllegalStateException("Cannot leave a guild that you are the owner of! Transfer guild ownership first!");
        }
        Route.CompiledRoute route = Route.Self.LEAVE_GUILD.compile(this.getId());
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public RestAction<Void> delete() {
        if (!this.getJDA().getSelfUser().isBot() && this.getJDA().getSelfUser().isMfaEnabled()) {
            throw new IllegalStateException("Cannot delete a guild without providing MFA code. Use Guild#delete(String)");
        }
        return this.delete(null);
    }

    @Override
    @Nonnull
    public RestAction<Void> delete(String mfaCode) {
        if (!this.getSelfMember().isOwner()) {
            throw new PermissionException("Cannot delete a guild that you do not own!");
        }
        DataObject mfaBody = null;
        if (!this.getJDA().getSelfUser().isBot() && this.getJDA().getSelfUser().isMfaEnabled()) {
            Checks.notEmpty(mfaCode, "Provided MultiFactor Auth code");
            mfaBody = DataObject.empty().put("code", mfaCode);
        }
        Route.CompiledRoute route = Route.Guilds.DELETE_GUILD.compile(this.getId());
        return new RestActionImpl<Void>((JDA)this.getJDA(), route, mfaBody);
    }

    @Override
    @Nonnull
    public AudioManager getAudioManager() {
        if (!this.getJDA().isIntent(GatewayIntent.GUILD_VOICE_STATES)) {
            throw new IllegalStateException("Cannot use audio features with disabled GUILD_VOICE_STATES intent!");
        }
        AbstractCacheView<AudioManager> managerMap = this.getJDA().getAudioManagersView();
        AudioManager mng = managerMap.get(this.id);
        if (mng == null) {
            try (UnlockHook hook = managerMap.writeLock();){
                GuildImpl cachedGuild = (GuildImpl)this.getJDA().getGuildById(this.id);
                if (cachedGuild == null) {
                    throw new IllegalStateException("Cannot get an AudioManager instance on an uncached Guild");
                }
                mng = managerMap.get(this.id);
                if (mng == null) {
                    mng = new AudioManagerImpl(cachedGuild);
                    managerMap.getMap().put(this.id, mng);
                }
            }
        }
        return mng;
    }

    @Override
    @Nonnull
    public synchronized Task<Void> requestToSpeak() {
        if (!this.isRequestToSpeakPending()) {
            this.pendingRequestToSpeak = new CompletableFuture();
        }
        GatewayTask<Void> task = new GatewayTask<Void>(this.pendingRequestToSpeak, this::cancelRequestToSpeak);
        this.updateRequestToSpeak();
        return task;
    }

    @Override
    @Nonnull
    public synchronized Task<Void> cancelRequestToSpeak() {
        AudioChannelUnion channel;
        if (this.isRequestToSpeakPending()) {
            this.pendingRequestToSpeak.cancel(false);
            this.pendingRequestToSpeak = null;
        }
        if ((channel = this.getSelfMember().getVoiceState().getChannel()) instanceof StageChannel) {
            CompletableFuture<Void> future = ((StageChannel)((Object)channel)).cancelRequestToSpeak().submit();
            return new GatewayTask<Void>(future, () -> future.cancel(false));
        }
        return new GatewayTask<Object>(CompletableFuture.completedFuture(null), () -> {});
    }

    @Override
    @Nonnull
    public JDAImpl getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public List<GuildVoiceState> getVoiceStates() {
        return this.getMembersView().stream().map(Member::getVoiceState).filter(Objects::nonNull).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public Guild.VerificationLevel getVerificationLevel() {
        return this.verificationLevel;
    }

    @Override
    @Nonnull
    public Guild.NotificationLevel getDefaultNotificationLevel() {
        return this.defaultNotificationLevel;
    }

    @Override
    @Nonnull
    public Guild.MFALevel getRequiredMFALevel() {
        return this.mfaLevel;
    }

    @Override
    @Nonnull
    public Guild.ExplicitContentLevel getExplicitContentLevel() {
        return this.explicitContentLevel;
    }

    @Override
    @Nonnull
    public Task<Void> loadMembers(@Nonnull Consumer<Member> callback) {
        Checks.notNull(callback, "Callback");
        if (!this.getJDA().isIntent(GatewayIntent.GUILD_MEMBERS)) {
            throw new IllegalStateException("Cannot use loadMembers without GatewayIntent.GUILD_MEMBERS!");
        }
        if (this.isLoaded()) {
            this.memberCache.forEachUnordered(callback);
            return new GatewayTask<Object>(CompletableFuture.completedFuture(null), () -> {});
        }
        MemberChunkManager chunkManager = this.getJDA().getClient().getChunkManager();
        boolean includePresences = this.getJDA().isIntent(GatewayIntent.GUILD_PRESENCES);
        MemberChunkManager.ChunkRequest handler = chunkManager.chunkGuild(this, includePresences, (last, list) -> list.forEach(callback));
        handler.exceptionally(ex -> {
            WebSocketClient.LOG.error("Encountered exception trying to handle member chunk response", ex);
            return null;
        });
        return new GatewayTask<Void>(handler, () -> handler.cancel(false)).onSetTimeout(handler::setTimeout);
    }

    @Override
    @Nonnull
    public CacheRestAction<Member> retrieveMemberById(long id) {
        JDAImpl jda = this.getJDA();
        return new DeferredRestAction<Member, RestAction>(jda, Member.class, () -> this.getMemberById(id), () -> {
            if (id == jda.getSelfUser().getIdLong()) {
                return new CompletedRestAction<Member>((JDA)jda, this.getSelfMember());
            }
            Route.CompiledRoute route = Route.Guilds.GET_MEMBER.compile(this.getId(), Long.toUnsignedString(id));
            return new RestActionImpl<Member>((JDA)jda, route, (resp, req) -> {
                MemberImpl member = jda.getEntityBuilder().createMember(this, resp.getObject());
                jda.getEntityBuilder().updateMemberCache(member);
                return member;
            });
        }).useCache(jda.isIntent(GatewayIntent.GUILD_MEMBERS));
    }

    @Override
    @Nonnull
    public Task<List<Member>> retrieveMembersByIds(boolean includePresence, long ... ids) {
        Checks.notNull(ids, "ID Array");
        Checks.check(!includePresence || this.api.isIntent(GatewayIntent.GUILD_PRESENCES), "Cannot retrieve presences of members without GUILD_PRESENCES intent!");
        if (ids.length == 0) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        Checks.check(ids.length <= 100, "You can only request 100 members at once");
        MemberChunkManager chunkManager = this.api.getClient().getChunkManager();
        ArrayList collect = new ArrayList(ids.length);
        CompletableFuture result = new CompletableFuture();
        MemberChunkManager.ChunkRequest handle = chunkManager.chunkGuild(this, includePresence, ids, (last, list) -> {
            collect.addAll(list);
            if (last.booleanValue()) {
                result.complete(collect);
            }
        });
        handle.exceptionally(ex -> {
            WebSocketClient.LOG.error("Encountered exception trying to handle member chunk response", ex);
            result.completeExceptionally((Throwable)ex);
            return null;
        });
        return new GatewayTask(result, () -> handle.cancel(false)).onSetTimeout(handle::setTimeout);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public Task<List<Member>> retrieveMembersByPrefix(@Nonnull String prefix, int limit) {
        Checks.notEmpty(prefix, "Prefix");
        Checks.positive(limit, "Limit");
        Checks.check(limit <= 100, "Limit must not be greater than 100");
        MemberChunkManager chunkManager = this.api.getClient().getChunkManager();
        ArrayList collect = new ArrayList(limit);
        CompletableFuture result = new CompletableFuture();
        MemberChunkManager.ChunkRequest handle = chunkManager.chunkGuild(this, prefix, limit, (last, list) -> {
            collect.addAll(list);
            if (last.booleanValue()) {
                result.complete(collect);
            }
        });
        handle.exceptionally(ex -> {
            WebSocketClient.LOG.error("Encountered exception trying to handle member chunk response", ex);
            result.completeExceptionally((Throwable)ex);
            return null;
        });
        return new GatewayTask(result, () -> handle.cancel(false)).onSetTimeout(handle::setTimeout);
    }

    @Override
    @Nonnull
    public RestAction<List<ThreadChannel>> retrieveActiveThreads() {
        Route.CompiledRoute route = Route.Guilds.LIST_ACTIVE_THREADS.compile(this.getId());
        return new RestActionImpl<List<ThreadChannel>>((JDA)this.api, route, (response, request) -> {
            int i;
            DataObject obj = response.getObject();
            DataArray selfThreadMembers = obj.getArray("members");
            DataArray threads = obj.getArray("threads");
            ArrayList<ThreadChannel> list = new ArrayList<ThreadChannel>(threads.length());
            EntityBuilder builder = this.api.getEntityBuilder();
            TLongObjectHashMap<DataObject> selfThreadMemberMap = new TLongObjectHashMap<DataObject>();
            for (i = 0; i < selfThreadMembers.length(); ++i) {
                DataObject selfThreadMember = selfThreadMembers.getObject(i);
                selfThreadMemberMap.put(selfThreadMember.getLong("id"), selfThreadMember);
            }
            for (i = 0; i < threads.length(); ++i) {
                DataObject threadObj = threads.getObject(i);
                DataObject selfThreadMemberObj = (DataObject)selfThreadMemberMap.get(threadObj.getLong("id", 0L));
                if (selfThreadMemberObj != null) {
                    threadObj.put("member", selfThreadMemberObj);
                }
                try {
                    ThreadChannel thread = builder.createThreadChannel(threadObj, this.getIdLong());
                    list.add(thread);
                    continue;
                }
                catch (Exception e) {
                    if ("MISSING_CHANNEL".equals(e.getMessage())) {
                        EntityBuilder.LOG.debug("Discarding thread without cached parent channel. JSON: {}", (Object)threadObj);
                        continue;
                    }
                    EntityBuilder.LOG.warn("Failed to create thread channel. JSON: {}", (Object)threadObj, (Object)e);
                }
            }
            return Collections.unmodifiableList(list);
        });
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public RestAction<List<Invite>> retrieveInvites() {
        if (!this.getSelfMember().hasPermission(Permission.MANAGE_SERVER)) {
            throw new InsufficientPermissionException(this, Permission.MANAGE_SERVER);
        }
        Route.CompiledRoute route = Route.Invites.GET_GUILD_INVITES.compile(this.getId());
        return new RestActionImpl<List<Invite>>((JDA)this.getJDA(), route, (response, request) -> {
            EntityBuilder entityBuilder = this.api.getEntityBuilder();
            DataArray array = response.getArray();
            ArrayList<Invite> invites = new ArrayList<Invite>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                invites.add(entityBuilder.createInvite(array.getObject(i)));
            }
            return Collections.unmodifiableList(invites);
        });
    }

    @Override
    @Nonnull
    public RestAction<List<Template>> retrieveTemplates() {
        if (!this.getSelfMember().hasPermission(Permission.MANAGE_SERVER)) {
            throw new InsufficientPermissionException(this, Permission.MANAGE_SERVER);
        }
        Route.CompiledRoute route = Route.Templates.GET_GUILD_TEMPLATES.compile(this.getId());
        return new RestActionImpl<List<Template>>((JDA)this.getJDA(), route, (response, request) -> {
            EntityBuilder entityBuilder = this.api.getEntityBuilder();
            DataArray array = response.getArray();
            ArrayList<Template> templates = new ArrayList<Template>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                try {
                    templates.add(entityBuilder.createTemplate(array.getObject(i)));
                    continue;
                }
                catch (Exception e) {
                    JDAImpl.LOG.error("Error creating template from json", (Throwable)e);
                }
            }
            return Collections.unmodifiableList(templates);
        });
    }

    @Override
    @Nonnull
    public RestAction<Template> createTemplate(@Nonnull String name, @Nullable String description) {
        this.checkPermission(Permission.MANAGE_SERVER);
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 100, "Name");
        if (description != null) {
            Checks.notLonger(description, 120, "Description");
        }
        Route.CompiledRoute route = Route.Templates.CREATE_TEMPLATE.compile(this.getId());
        DataObject object = DataObject.empty();
        object.put("name", name);
        object.put("description", description);
        return new RestActionImpl<Template>((JDA)this.getJDA(), route, object, (response, request) -> {
            EntityBuilder entityBuilder = this.api.getEntityBuilder();
            return entityBuilder.createTemplate(response.getObject());
        });
    }

    @Override
    @Nonnull
    public RestAction<GuildWelcomeScreen> retrieveWelcomeScreen() {
        Route.CompiledRoute route = Route.Guilds.GET_WELCOME_SCREEN.compile(this.getId());
        return new RestActionImpl<GuildWelcomeScreen>((JDA)this.getJDA(), route, (response, request) -> {
            EntityBuilder entityBuilder = this.api.getEntityBuilder();
            return entityBuilder.createWelcomeScreen(this, response.getObject());
        });
    }

    @Override
    @Nonnull
    public RestAction<Void> moveVoiceMember(@Nonnull Member member, @Nullable AudioChannel audioChannel) {
        GuildVoiceState vState;
        Checks.notNull(member, "Member");
        this.checkGuild(member.getGuild(), "Member");
        if (audioChannel != null) {
            this.checkGuild(audioChannel.getGuild(), "AudioChannel");
        }
        if ((vState = member.getVoiceState()) == null) {
            throw new IllegalStateException("Cannot move a Member with disabled CacheFlag.VOICE_STATE");
        }
        AudioChannelUnion channel = vState.getChannel();
        if (channel == null) {
            throw new IllegalStateException("You cannot move a Member who isn't in an AudioChannel!");
        }
        Member selfMember = this.getSelfMember();
        if (!selfMember.hasPermission((GuildChannel)channel, Permission.VOICE_MOVE_OTHERS)) {
            throw new InsufficientPermissionException(channel, Permission.VOICE_MOVE_OTHERS, "This account does not have Permission to MOVE_OTHERS out of the channel that the Member is currently in.");
        }
        if (audioChannel != null && !selfMember.hasPermission((GuildChannel)audioChannel, Permission.VOICE_CONNECT) && !member.hasPermission((GuildChannel)audioChannel, Permission.VOICE_CONNECT)) {
            throw new InsufficientPermissionException(audioChannel, Permission.VOICE_CONNECT, "Neither this account nor the Member that is attempting to be moved have the VOICE_CONNECT permission for the destination AudioChannel, so the move cannot be done.");
        }
        DataObject body = DataObject.empty().put("channel_id", audioChannel == null ? null : audioChannel.getId());
        Route.CompiledRoute route = Route.Guilds.MODIFY_MEMBER.compile(this.getId(), member.getUser().getId());
        return new RestActionImpl<Void>((JDA)this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> modifyNickname(@Nonnull Member member, String nickname) {
        Checks.notNull(member, "Member");
        this.checkGuild(member.getGuild(), "Member");
        if (member.equals(this.getSelfMember())) {
            if (!member.hasPermission(Permission.NICKNAME_CHANGE) && !member.hasPermission(Permission.NICKNAME_MANAGE)) {
                throw new InsufficientPermissionException((Guild)this, Permission.NICKNAME_CHANGE, "You neither have NICKNAME_CHANGE nor NICKNAME_MANAGE permission!");
            }
        } else {
            this.checkPermission(Permission.NICKNAME_MANAGE);
            this.checkPosition(member);
        }
        JDAImpl jda = this.getJDA();
        return new DeferredRestAction(jda, () -> {
            DataObject body = DataObject.empty().put("nick", nickname == null ? "" : nickname);
            Route.CompiledRoute route = member.equals(this.getSelfMember()) ? Route.Guilds.MODIFY_SELF.compile(this.getId()) : Route.Guilds.MODIFY_MEMBER.compile(this.getId(), member.getUser().getId());
            return new AuditableRestActionImpl((JDA)jda, route, body);
        }).setCacheCheck(() -> !Objects.equals(nickname, member.getNickname()));
    }

    @Override
    @Nonnull
    public AuditableRestAction<Integer> prune(int days, boolean wait, Role ... roles) {
        this.checkPermission(Permission.KICK_MEMBERS);
        Checks.check(days >= 1 && days <= 30, "Provided %d days must be between 1 and 30.", (Object)days);
        Checks.notNull(roles, "Roles");
        Route.CompiledRoute route = Route.Guilds.PRUNE_MEMBERS.compile(this.getId());
        DataObject body = DataObject.empty();
        body.put("days", days);
        if (!wait) {
            body.put("compute_prune_count", false);
        }
        if (roles.length != 0) {
            for (Role role : roles) {
                Checks.notNull(role, "Role");
                Checks.check(role.getGuild().equals(this), "Role is not from the same guild!");
            }
            body.put("include_roles", Arrays.stream(roles).map(ISnowflake::getId).collect(Collectors.toList()));
        }
        return new AuditableRestActionImpl<Integer>((JDA)this.getJDA(), route, body, (response, request) -> response.getObject().getInt("pruned", 0));
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> kick(@Nonnull UserSnowflake user) {
        Checks.notNull(user, "User");
        this.checkPermission(Permission.KICK_MEMBERS);
        this.checkOwner(user.getIdLong(), "kick");
        this.checkPosition(user);
        Route.CompiledRoute route = Route.Guilds.KICK_MEMBER.compile(this.getId(), user.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> ban(@Nonnull UserSnowflake user, int duration, @Nonnull TimeUnit unit) {
        Checks.notNull(user, "User");
        Checks.notNull((Object)unit, "TimeUnit");
        Checks.notNegative(duration, "Deletion Timeframe");
        Checks.check(unit.toDays(duration) <= 7L, "Deletion timeframe must not be larger than 7 days");
        this.checkPermission(Permission.BAN_MEMBERS);
        this.checkOwner(user.getIdLong(), "ban");
        this.checkPosition(user);
        Route.CompiledRoute route = Route.Guilds.BAN.compile(this.getId(), user.getId());
        DataObject params = DataObject.empty();
        if (duration > 0) {
            params.put("delete_message_seconds", unit.toSeconds(duration));
        }
        return new AuditableRestActionImpl<Void>((JDA)this.getJDA(), route, params);
    }

    @Override
    @Nonnull
    public AuditableRestAction<BulkBanResponse> ban(@Nonnull Collection<? extends UserSnowflake> users, @Nullable Duration deletionTime) {
        deletionTime = deletionTime == null ? Duration.ZERO : deletionTime;
        Checks.noneNull(users, "Users");
        Checks.notNegative(deletionTime.getSeconds(), "Deletion timeframe");
        Checks.check(deletionTime.getSeconds() <= TimeUnit.DAYS.toSeconds(7L), "Deletion timeframe must not be larger than 7 days. Provided: %d seconds", (Object)deletionTime.getSeconds());
        Checks.check(users.size() <= 200, "Cannot ban more than 200 users at once");
        this.checkPermission(Permission.BAN_MEMBERS);
        this.checkPermission(Permission.MANAGE_SERVER);
        for (UserSnowflake userSnowflake : users) {
            this.checkOwner(userSnowflake.getIdLong(), "ban");
            this.checkPosition(userSnowflake);
        }
        Set userIds = users.stream().map(ISnowflake::getIdLong).collect(Collectors.toSet());
        DataObject dataObject = DataObject.empty().put("user_ids", userIds).put("delete_message_seconds", deletionTime.getSeconds());
        Route.CompiledRoute route = Route.Guilds.BULK_BAN.compile(this.getId());
        return new AuditableRestActionImpl<BulkBanResponse>((JDA)this.getJDA(), route, dataObject, (res, req) -> {
            DataObject responseBody = res.getObject();
            List<UserSnowflake> bannedUsers = responseBody.getArray("banned_users").stream(DataArray::getLong).map(UserSnowflake::fromId).collect(Collectors.toList());
            List<UserSnowflake> failedUsers = responseBody.getArray("failed_users").stream(DataArray::getLong).map(UserSnowflake::fromId).collect(Collectors.toList());
            return new BulkBanResponse(bannedUsers, failedUsers);
        });
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> unban(@Nonnull UserSnowflake user) {
        Checks.notNull(user, "User");
        this.checkPermission(Permission.BAN_MEMBERS);
        Route.CompiledRoute route = Route.Guilds.UNBAN.compile(this.getId(), user.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> timeoutUntil(@Nonnull UserSnowflake user, @Nonnull TemporalAccessor temporal) {
        Checks.notNull(user, "User");
        Checks.notNull(temporal, "Temporal");
        OffsetDateTime date = Helpers.toOffsetDateTime(temporal);
        Checks.check(date.isAfter(OffsetDateTime.now()), "Cannot put a member in time out with date in the past. Provided: %s", (Object)date);
        Checks.check(date.isBefore(OffsetDateTime.now().plusDays(28L)), "Cannot put a member in time out for more than 28 days. Provided: %s", (Object)date);
        this.checkPermission(Permission.MODERATE_MEMBERS);
        this.checkOwner(user.getIdLong(), "time out");
        this.checkPosition(user);
        return this.timeoutUntilById0(user.getId(), date);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> removeTimeout(@Nonnull UserSnowflake user) {
        Checks.notNull(user, "User");
        return this.timeoutUntilById0(user.getId(), null);
    }

    @Nonnull
    private AuditableRestAction<Void> timeoutUntilById0(@Nonnull String userId, @Nullable OffsetDateTime date) {
        DataObject body = DataObject.empty().put("communication_disabled_until", date == null ? null : date.toString());
        Route.CompiledRoute route = Route.Guilds.MODIFY_MEMBER.compile(this.getId(), userId);
        return new AuditableRestActionImpl<Void>((JDA)this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> deafen(@Nonnull UserSnowflake user, boolean deafen) {
        GuildVoiceState voiceState;
        Checks.notNull(user, "User");
        Member member = this.resolveMember(user);
        if (member != null && (voiceState = member.getVoiceState()) != null) {
            AudioChannelUnion channel = voiceState.getChannel();
            if (channel == null) {
                throw new IllegalStateException("Can only deafen members who are currently in a voice channel");
            }
            if (voiceState.isGuildDeafened() == deafen) {
                return new CompletedRestAction<Void>((JDA)this.getJDA(), null);
            }
            ((GuildChannelMixin)((Object)channel)).checkPermission(Permission.VOICE_DEAF_OTHERS);
        }
        DataObject body = DataObject.empty().put("deaf", deafen);
        Route.CompiledRoute route = Route.Guilds.MODIFY_MEMBER.compile(this.getId(), user.getId());
        return new AuditableRestActionImpl<Void>((JDA)this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> mute(@Nonnull UserSnowflake user, boolean mute) {
        GuildVoiceState voiceState;
        Checks.notNull(user, "User");
        Member member = this.resolveMember(user);
        if (member != null && (voiceState = member.getVoiceState()) != null) {
            AudioChannelUnion channel = voiceState.getChannel();
            if (channel == null) {
                throw new IllegalStateException("Can only mute members who are currently in a voice channel");
            }
            if (voiceState.isGuildMuted() == mute && (mute || !voiceState.isSuppressed())) {
                return new CompletedRestAction<Void>((JDA)this.getJDA(), null);
            }
            ((GuildChannelMixin)((Object)channel)).checkPermission(Permission.VOICE_MUTE_OTHERS);
        }
        DataObject body = DataObject.empty().put("mute", mute);
        Route.CompiledRoute route = Route.Guilds.MODIFY_MEMBER.compile(this.getId(), user.getId());
        return new AuditableRestActionImpl<Void>((JDA)this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> addRoleToMember(@Nonnull UserSnowflake user, @Nonnull Role role) {
        Checks.notNull(user, "User");
        Checks.notNull(role, "Role");
        this.checkGuild(role.getGuild(), "Role");
        this.checkPermission(Permission.MANAGE_ROLES);
        this.checkPosition(role);
        Route.CompiledRoute route = Route.Guilds.ADD_MEMBER_ROLE.compile(this.getId(), user.getId(), role.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> removeRoleFromMember(@Nonnull UserSnowflake user, @Nonnull Role role) {
        Checks.notNull(user, "User");
        Checks.notNull(role, "Role");
        this.checkGuild(role.getGuild(), "Role");
        this.checkPermission(Permission.MANAGE_ROLES);
        this.checkPosition(role);
        Route.CompiledRoute route = Route.Guilds.REMOVE_MEMBER_ROLE.compile(this.getId(), user.getId(), role.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull Member member, Collection<Role> rolesToAdd, Collection<Role> rolesToRemove) {
        Checks.notNull(member, "Member");
        this.checkGuild(member.getGuild(), "Member");
        this.checkPermission(Permission.MANAGE_ROLES);
        HashSet<Role> currentRoles = new HashSet<Role>(((MemberImpl)member).getRoleSet());
        if (rolesToAdd != null) {
            this.checkRoles(rolesToAdd, "add", "to");
            currentRoles.addAll(rolesToAdd);
        }
        if (rolesToRemove != null) {
            this.checkRoles(rolesToRemove, "remove", "from");
            currentRoles.removeAll(rolesToRemove);
        }
        return this.modifyMemberRoles(member, currentRoles);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull Member member, @Nonnull Collection<Role> roles) {
        Checks.notNull(member, "Member");
        Checks.notNull(roles, "Roles");
        this.checkGuild(member.getGuild(), "Member");
        roles.forEach(role -> {
            Checks.notNull(role, "Role in collection");
            this.checkGuild(role.getGuild(), "Role: " + role.toString());
        });
        Checks.check(!roles.contains(this.getPublicRole()), "Cannot add the PublicRole of a Guild to a Member. All members have this role by default!");
        List<Role> memberRoles = member.getRoles();
        if (Helpers.deepEqualsUnordered(roles, memberRoles)) {
            return new CompletedRestAction<Void>((JDA)this.getJDA(), null);
        }
        for (Role r : memberRoles) {
            if (roles.contains(r)) continue;
            this.checkPosition(r);
            Checks.check(!r.isManaged(), "Cannot remove managed role from member. Role: %s", (Object)r);
        }
        for (Role r : roles) {
            if (memberRoles.contains(r)) continue;
            this.checkPosition(r);
            Checks.check(!r.isManaged(), "Cannot add managed role to member. Role: %s", (Object)r);
        }
        DataObject body = DataObject.empty().put("roles", roles.stream().map(ISnowflake::getId).collect(Collectors.toSet()));
        Route.CompiledRoute route = Route.Guilds.MODIFY_MEMBER.compile(this.getId(), member.getUser().getId());
        return new AuditableRestActionImpl<Void>((JDA)this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> transferOwnership(@Nonnull Member newOwner) {
        Checks.notNull(newOwner, "Member");
        this.checkGuild(newOwner.getGuild(), "Member");
        if (!this.getSelfMember().isOwner()) {
            throw new PermissionException("The logged in account must be the owner of this Guild to be able to transfer ownership");
        }
        Checks.check(!this.getSelfMember().equals(newOwner), "The member provided as the newOwner is the currently logged in account. Provide a different member to give ownership to.");
        Checks.check(!newOwner.getUser().isBot(), "Cannot transfer ownership of a Guild to a Bot!");
        DataObject body = DataObject.empty().put("owner_id", newOwner.getUser().getId());
        Route.CompiledRoute route = Route.Guilds.MODIFY_GUILD.compile(this.getId());
        return new AuditableRestActionImpl<Void>((JDA)this.getJDA(), route, body);
    }

    @Override
    @Nonnull
    public ChannelAction<TextChannel> createTextChannel(@Nonnull String name, Category parent) {
        return this.createChannel(ChannelType.TEXT, TextChannel.class, name, parent);
    }

    @Override
    @Nonnull
    public ChannelAction<NewsChannel> createNewsChannel(@Nonnull String name, Category parent) {
        return this.createChannel(ChannelType.NEWS, NewsChannel.class, name, parent);
    }

    @Override
    @Nonnull
    public ChannelAction<VoiceChannel> createVoiceChannel(@Nonnull String name, Category parent) {
        return this.createChannel(ChannelType.VOICE, VoiceChannel.class, name, parent);
    }

    @Override
    @Nonnull
    public ChannelAction<StageChannel> createStageChannel(@Nonnull String name, Category parent) {
        return this.createChannel(ChannelType.STAGE, StageChannel.class, name, parent);
    }

    @Override
    @Nonnull
    public ChannelAction<ForumChannel> createForumChannel(@Nonnull String name, Category parent) {
        return this.createChannel(ChannelType.FORUM, ForumChannel.class, name, parent);
    }

    @Override
    @Nonnull
    public ChannelAction<MediaChannel> createMediaChannel(@Nonnull String name, @Nullable Category parent) {
        return this.createChannel(ChannelType.MEDIA, MediaChannel.class, name, parent);
    }

    @Override
    @Nonnull
    public ChannelAction<Category> createCategory(@Nonnull String name) {
        return this.createChannel(ChannelType.CATEGORY, Category.class, name, null);
    }

    private <T extends GuildChannel> ChannelAction<T> createChannel(ChannelType type, Class<T> clazz, String name, Category parent) {
        this.checkCanCreateChannel(parent);
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        return new ChannelActionImpl<T>(clazz, name, this, type).setParent(parent);
    }

    @Override
    @Nonnull
    public RoleAction createRole() {
        this.checkPermission(Permission.MANAGE_ROLES);
        return new RoleActionImpl(this);
    }

    @Override
    @Nonnull
    public AuditableRestAction<RichCustomEmoji> createEmoji(@Nonnull String name, @Nonnull Icon icon, Role ... roles) {
        this.checkPermission(Permission.MANAGE_GUILD_EXPRESSIONS);
        Checks.inRange(name, 2, 32, "Emoji name");
        Checks.notNull(icon, "Emoji icon");
        Checks.notNull(roles, "Roles");
        DataObject body = DataObject.empty();
        body.put("name", name);
        body.put("image", icon.getEncoding());
        if (roles.length > 0) {
            body.put("roles", Stream.of(roles).filter(Objects::nonNull).map(ISnowflake::getId).collect(Collectors.toSet()));
        }
        JDAImpl jda = this.getJDA();
        Route.CompiledRoute route = Route.Emojis.CREATE_EMOJI.compile(this.getId());
        return new AuditableRestActionImpl<RichCustomEmoji>((JDA)jda, route, body, (response, request) -> {
            DataObject obj = response.getObject();
            return jda.getEntityBuilder().createEmoji(this, obj);
        });
    }

    @Override
    @Nonnull
    public AuditableRestAction<GuildSticker> createSticker(@Nonnull String name, @Nonnull String description, @Nonnull FileUpload file, @Nonnull Collection<String> tags) {
        MediaType mediaType;
        String extension;
        this.checkPermission(Permission.MANAGE_GUILD_EXPRESSIONS);
        Checks.inRange(name, 2, 30, "Name");
        Checks.notNull(file, "File");
        Checks.notNull(description, "Description");
        Checks.notEmpty(tags, "Tags");
        if (!description.isEmpty()) {
            Checks.inRange(description, 2, 100, "Description");
        }
        for (String t : tags) {
            Checks.notEmpty(t, "Tags");
        }
        String csv = String.join((CharSequence)",", tags);
        Checks.notLonger(csv, 200, "Tags");
        int index = file.getName().lastIndexOf(46);
        Checks.check(index > -1, "Filename for sticker is missing file extension. Provided: '" + file.getName() + "'. Must be PNG, GIF, or JSON.");
        switch (extension = file.getName().substring(index + 1).toLowerCase(Locale.ROOT)) {
            case "apng": 
            case "png": {
                mediaType = Requester.MEDIA_TYPE_PNG;
                break;
            }
            case "gif": {
                mediaType = Requester.MEDIA_TYPE_GIF;
                break;
            }
            case "json": {
                mediaType = Requester.MEDIA_TYPE_JSON;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported file extension: '." + extension + "', must be PNG, GIF, or JSON.");
            }
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("name", name);
        builder.addFormDataPart("description", description);
        builder.addFormDataPart("tags", csv);
        builder.addFormDataPart("file", file.getName(), file.getRequestBody(mediaType));
        MultipartBody body = builder.build();
        Route.CompiledRoute route = Route.Stickers.CREATE_GUILD_STICKER.compile(this.getId());
        return new AuditableRestActionImpl<GuildSticker>((JDA)this.api, route, body, (response, request) -> (GuildSticker)((Object)this.api.getEntityBuilder().createRichSticker(response.getObject())));
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> deleteSticker(@Nonnull StickerSnowflake id) {
        Checks.notNull(id, "Sticker");
        Route.CompiledRoute route = Route.Stickers.DELETE_GUILD_STICKER.compile(this.getId(), id.getId());
        return new AuditableRestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public ChannelOrderAction modifyCategoryPositions() {
        return new ChannelOrderActionImpl(this, ChannelType.CATEGORY.getSortBucket());
    }

    @Override
    @Nonnull
    public ChannelOrderAction modifyTextChannelPositions() {
        return new ChannelOrderActionImpl(this, ChannelType.TEXT.getSortBucket());
    }

    @Override
    @Nonnull
    public ChannelOrderAction modifyVoiceChannelPositions() {
        return new ChannelOrderActionImpl(this, ChannelType.VOICE.getSortBucket());
    }

    @Override
    @Nonnull
    public CategoryOrderAction modifyTextChannelPositions(@Nonnull Category category) {
        Checks.notNull(category, "Category");
        this.checkGuild(category.getGuild(), "Category");
        return new CategoryOrderActionImpl(category, ChannelType.TEXT.getSortBucket());
    }

    @Override
    @Nonnull
    public CategoryOrderAction modifyVoiceChannelPositions(@Nonnull Category category) {
        Checks.notNull(category, "Category");
        this.checkGuild(category.getGuild(), "Category");
        return new CategoryOrderActionImpl(category, ChannelType.VOICE.getSortBucket());
    }

    @Override
    @Nonnull
    public RoleOrderAction modifyRolePositions(boolean useAscendingOrder) {
        return new RoleOrderActionImpl(this, useAscendingOrder);
    }

    @Override
    @Nonnull
    public GuildWelcomeScreenManager modifyWelcomeScreen() {
        return new GuildWelcomeScreenManagerImpl(this);
    }

    protected void checkGuild(Guild providedGuild, String comment) {
        if (!this.equals(providedGuild)) {
            throw new IllegalArgumentException("Provided " + comment + " is not part of this Guild!");
        }
    }

    protected void checkPermission(Permission perm) {
        if (!this.getSelfMember().hasPermission(perm)) {
            throw new InsufficientPermissionException(this, perm);
        }
    }

    protected void checkPosition(UserSnowflake user) {
        Member member = this.resolveMember(user);
        if (member != null && !this.getSelfMember().canInteract(member)) {
            throw new HierarchyException("Can't modify a member with higher or equal highest role than yourself!");
        }
    }

    protected void checkPosition(Role role) {
        if (!this.getSelfMember().canInteract(role)) {
            throw new HierarchyException("Can't modify a role with higher or equal highest role than yourself! Role: " + role.toString());
        }
    }

    private void checkRoles(Collection<Role> roles, String type, String preposition) {
        roles.forEach(role -> {
            Checks.notNull(role, "Role in roles to " + type);
            this.checkGuild(role.getGuild(), "Role: " + role);
            this.checkPosition((Role)role);
            Checks.check(!role.isManaged(), "Cannot %s a managed role %s a Member. Role: %s", type, preposition, role.toString());
        });
    }

    private void checkCanCreateChannel(Category parent) {
        if (parent != null) {
            Checks.check(parent.getGuild().equals(this), "Category is not from the same guild!");
            if (!this.getSelfMember().hasPermission((GuildChannel)parent, Permission.MANAGE_CHANNEL)) {
                throw new InsufficientPermissionException(parent, Permission.MANAGE_CHANNEL);
            }
        } else {
            this.checkPermission(Permission.MANAGE_CHANNEL);
        }
    }

    private void checkOwner(long userId, String what) {
        if (userId == this.ownerId) {
            throw new HierarchyException("Cannot " + what + " the owner of a guild.");
        }
    }

    private Member resolveMember(UserSnowflake user) {
        Member member = this.getMemberById(user.getIdLong());
        if (member == null && user instanceof Member && !this.equals((member = (Member)user).getGuild())) {
            member = null;
        }
        return member;
    }

    private synchronized boolean isRequestToSpeakPending() {
        return this.pendingRequestToSpeak != null && !this.pendingRequestToSpeak.isDone();
    }

    public synchronized void updateRequestToSpeak() {
        block4: {
            if (!this.isRequestToSpeakPending()) {
                return;
            }
            AudioChannelUnion connectedChannel = this.getSelfMember().getVoiceState().getChannel();
            if (!(connectedChannel instanceof StageChannel)) {
                return;
            }
            StageChannel stage = (StageChannel)((Object)connectedChannel);
            CompletableFuture<Void> future = this.pendingRequestToSpeak;
            this.pendingRequestToSpeak = null;
            try {
                stage.requestToSpeak().queue(v -> future.complete(null), future::completeExceptionally);
            }
            catch (Throwable ex) {
                future.completeExceptionally(ex);
                if (!(ex instanceof Error)) break block4;
                throw ex;
            }
        }
    }

    public GuildImpl setOwner(Member owner) {
        if (owner != null && this.getMemberById(owner.getIdLong()) != null) {
            this.owner = owner;
        }
        return this;
    }

    public GuildImpl setName(String name) {
        this.name = name;
        return this;
    }

    public GuildImpl setIconId(String iconId) {
        this.iconId = iconId;
        return this;
    }

    public GuildImpl setFeatures(Set<String> features) {
        this.features = Collections.unmodifiableSet(features);
        return this;
    }

    public GuildImpl setSplashId(String splashId) {
        this.splashId = splashId;
        return this;
    }

    public GuildImpl setVanityCode(String code) {
        this.vanityCode = code;
        return this;
    }

    public GuildImpl setDescription(String description) {
        this.description = description;
        return this;
    }

    public GuildImpl setBannerId(String bannerId) {
        this.banner = bannerId;
        return this;
    }

    public GuildImpl setMaxPresences(int maxPresences) {
        this.maxPresences = maxPresences;
        return this;
    }

    public GuildImpl setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
        return this;
    }

    public GuildImpl setAfkChannel(VoiceChannel afkChannel) {
        this.afkChannel = afkChannel;
        return this;
    }

    public GuildImpl setSystemChannel(TextChannel systemChannel) {
        this.systemChannel = systemChannel;
        return this;
    }

    public GuildImpl setRulesChannel(TextChannel rulesChannel) {
        this.rulesChannel = rulesChannel;
        return this;
    }

    public GuildImpl setCommunityUpdatesChannel(TextChannel communityUpdatesChannel) {
        this.communityUpdatesChannel = communityUpdatesChannel;
        return this;
    }

    public GuildImpl setSafetyAlertsChannel(TextChannel safetyAlertsChannel) {
        this.safetyAlertsChannel = safetyAlertsChannel;
        return this;
    }

    public GuildImpl setPublicRole(Role publicRole) {
        this.publicRole = publicRole;
        return this;
    }

    public GuildImpl setVerificationLevel(Guild.VerificationLevel level) {
        this.verificationLevel = level;
        return this;
    }

    public GuildImpl setDefaultNotificationLevel(Guild.NotificationLevel level) {
        this.defaultNotificationLevel = level;
        return this;
    }

    public GuildImpl setRequiredMFALevel(Guild.MFALevel level) {
        this.mfaLevel = level;
        return this;
    }

    public GuildImpl setExplicitContentLevel(Guild.ExplicitContentLevel level) {
        this.explicitContentLevel = level;
        return this;
    }

    public GuildImpl setAfkTimeout(Guild.Timeout afkTimeout) {
        this.afkTimeout = afkTimeout;
        return this;
    }

    public GuildImpl setLocale(DiscordLocale locale) {
        this.preferredLocale = locale;
        return this;
    }

    public GuildImpl setBoostTier(int tier) {
        this.boostTier = Guild.BoostTier.fromKey(tier);
        return this;
    }

    public GuildImpl setBoostCount(int count) {
        this.boostCount = count;
        return this;
    }

    public GuildImpl setOwnerId(long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public GuildImpl setMemberCount(int count) {
        this.memberCount = count;
        return this;
    }

    public GuildImpl setNSFWLevel(Guild.NSFWLevel nsfwLevel) {
        this.nsfwLevel = nsfwLevel;
        return this;
    }

    public GuildImpl setBoostProgressBarEnabled(boolean enabled) {
        this.boostProgressBarEnabled = enabled;
        return this;
    }

    public SortedSnowflakeCacheViewImpl<ScheduledEvent> getScheduledEventsView() {
        return this.scheduledEventCache;
    }

    public SortedChannelCacheViewImpl<GuildChannel> getChannelView() {
        return this.channelCache;
    }

    public SortedSnowflakeCacheViewImpl<Role> getRolesView() {
        return this.roleCache;
    }

    public SnowflakeCacheViewImpl<RichCustomEmoji> getEmojisView() {
        return this.emojicache;
    }

    public SnowflakeCacheViewImpl<GuildSticker> getStickersView() {
        return this.stickerCache;
    }

    public MemberCacheViewImpl getMembersView() {
        return this.memberCache;
    }

    @Override
    @Nonnull
    public Guild.NSFWLevel getNSFWLevel() {
        return this.nsfwLevel;
    }

    @Nullable
    public CacheView.SimpleCacheView<MemberPresenceImpl> getPresenceView() {
        return this.memberPresences;
    }

    public void onMemberAdd() {
        ++this.memberCount;
    }

    public void onMemberRemove() {
        --this.memberCount;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GuildImpl)) {
            return false;
        }
        GuildImpl oGuild = (GuildImpl)o;
        return this.id == oGuild.id;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public String toString() {
        return new EntityString(this).setName(this.getName()).toString();
    }
}

