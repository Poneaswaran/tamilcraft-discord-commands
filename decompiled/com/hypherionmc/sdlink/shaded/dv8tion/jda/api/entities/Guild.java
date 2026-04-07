/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.DeprecatedSince;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.ForRemoval;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.Incubating;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.ReplaceWith;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.BulkBanResponse;
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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICopyableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IGuildChannelContainer;
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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.templates.Template;
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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.BanPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.MemberCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.concurrent.Task;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.CommandDataImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.concurrent.task.GatewayTask;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Unmodifiable;

public interface Guild
extends IGuildChannelContainer<GuildChannel>,
ISnowflake {
    public static final String ICON_URL = "https://cdn.discordapp.com/icons/%s/%s.%s";
    public static final String SPLASH_URL = "https://cdn.discordapp.com/splashes/%s/%s.png";
    public static final String BANNER_URL = "https://cdn.discordapp.com/banners/%s/%s.%s";

    @Nonnull
    @CheckReturnValue
    default public RestAction<List<Command>> retrieveCommands() {
        return this.retrieveCommands(false);
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<List<Command>> retrieveCommands(boolean var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Command> retrieveCommandById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Command> retrieveCommandById(long id) {
        return this.retrieveCommandById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Command> upsertCommand(@Nonnull CommandData var1);

    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction upsertCommand(@Nonnull String name, @Nonnull String description) {
        return (CommandCreateAction)this.upsertCommand(new CommandDataImpl(name, description));
    }

    @Nonnull
    @CheckReturnValue
    public CommandListUpdateAction updateCommands();

    @Nonnull
    @CheckReturnValue
    public CommandEditAction editCommandById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public CommandEditAction editCommandById(long id) {
        return this.editCommandById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> deleteCommandById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> deleteCommandById(long commandId) {
        return this.deleteCommandById(Long.toUnsignedString(commandId));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<List<IntegrationPrivilege>> retrieveIntegrationPrivilegesById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<List<IntegrationPrivilege>> retrieveIntegrationPrivilegesById(long targetId) {
        return this.retrieveIntegrationPrivilegesById(Long.toUnsignedString(targetId));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<PrivilegeConfig> retrieveCommandPrivileges();

    @Nonnull
    @CheckReturnValue
    default public RestAction<EnumSet<Region>> retrieveRegions() {
        return this.retrieveRegions(true);
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<EnumSet<Region>> retrieveRegions(boolean var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<AutoModRule>> retrieveAutoModRules();

    @Nonnull
    @CheckReturnValue
    public RestAction<AutoModRule> retrieveAutoModRuleById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<AutoModRule> retrieveAutoModRuleById(long id) {
        return this.retrieveAutoModRuleById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<AutoModRule> createAutoModRule(@Nonnull AutoModRuleData var1);

    @Nonnull
    @CheckReturnValue
    public AutoModRuleManager modifyAutoModRuleById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public AutoModRuleManager modifyAutoModRuleById(long id) {
        return this.modifyAutoModRuleById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> deleteAutoModRuleById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> deleteAutoModRuleById(long id) {
        return this.deleteAutoModRuleById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    public MemberAction addMember(@Nonnull String var1, @Nonnull UserSnowflake var2);

    public boolean isLoaded();

    public void pruneMemberCache();

    public boolean unloadMember(long var1);

    public int getMemberCount();

    @Nonnull
    public String getName();

    @Nullable
    public String getIconId();

    @Nullable
    default public String getIconUrl() {
        String iconId = this.getIconId();
        return iconId == null ? null : String.format(ICON_URL, this.getId(), iconId, iconId.startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    default public ImageProxy getIcon() {
        String iconUrl = this.getIconUrl();
        return iconUrl == null ? null : new ImageProxy(iconUrl);
    }

    @Nonnull
    public @Unmodifiable Set<String> getFeatures();

    default public boolean isInvitesDisabled() {
        return this.getFeatures().contains("INVITES_DISABLED");
    }

    @Nullable
    public String getSplashId();

    @Nullable
    default public String getSplashUrl() {
        String splashId = this.getSplashId();
        return splashId == null ? null : String.format(SPLASH_URL, this.getId(), splashId);
    }

    @Nullable
    default public ImageProxy getSplash() {
        String splashUrl = this.getSplashUrl();
        return splashUrl == null ? null : new ImageProxy(splashUrl);
    }

    @Nullable
    public String getVanityCode();

    @Nullable
    default public String getVanityUrl() {
        return this.getVanityCode() == null ? null : "https://discord.gg/" + this.getVanityCode();
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<VanityInvite> retrieveVanityInvite();

    @Nullable
    public String getDescription();

    @Nonnull
    public DiscordLocale getLocale();

    @Nullable
    public String getBannerId();

    @Nullable
    default public String getBannerUrl() {
        String bannerId = this.getBannerId();
        return bannerId == null ? null : String.format(BANNER_URL, this.getId(), bannerId, bannerId.startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    default public ImageProxy getBanner() {
        String bannerUrl = this.getBannerUrl();
        return bannerUrl == null ? null : new ImageProxy(bannerUrl);
    }

    @Nonnull
    public BoostTier getBoostTier();

    public int getBoostCount();

    @Nonnull
    public @Unmodifiable List<Member> getBoosters();

    default public int getMaxBitrate() {
        int maxBitrate = this.getFeatures().contains("VIP_REGIONS") ? 384000 : 96000;
        return Math.max(maxBitrate, this.getBoostTier().getMaxBitrate());
    }

    default public long getMaxFileSize() {
        return this.getBoostTier().getMaxFileSize();
    }

    default public int getMaxEmojis() {
        int max = this.getFeatures().contains("MORE_EMOJI") ? 200 : 50;
        return Math.max(max, this.getBoostTier().getMaxEmojis());
    }

    public int getMaxMembers();

    public int getMaxPresences();

    @Nonnull
    @CheckReturnValue
    public RestAction<MetaData> retrieveMetaData();

    @Nullable
    public VoiceChannel getAfkChannel();

    @Nullable
    public TextChannel getSystemChannel();

    @Nullable
    public TextChannel getRulesChannel();

    @Nullable
    public TextChannel getCommunityUpdatesChannel();

    @Nullable
    public TextChannel getSafetyAlertsChannel();

    @Nullable
    public Member getOwner();

    public long getOwnerIdLong();

    @Nonnull
    default public String getOwnerId() {
        return Long.toUnsignedString(this.getOwnerIdLong());
    }

    @Nonnull
    public Timeout getAfkTimeout();

    public boolean isMember(@Nonnull UserSnowflake var1);

    @Nonnull
    public Member getSelfMember();

    @Nonnull
    public NSFWLevel getNSFWLevel();

    @Nullable
    public Member getMember(@Nonnull UserSnowflake var1);

    @Nullable
    default public Member getMemberById(@Nonnull String userId) {
        return this.getMemberCache().getElementById(userId);
    }

    @Nullable
    default public Member getMemberById(long userId) {
        return this.getMemberCache().getElementById(userId);
    }

    @Nullable
    default public Member getMemberByTag(@Nonnull String tag) {
        User user = this.getJDA().getUserByTag(tag);
        return user == null ? null : this.getMember(user);
    }

    @Nullable
    default public Member getMemberByTag(@Nonnull String username, @Nonnull String discriminator) {
        User user = this.getJDA().getUserByTag(username, discriminator);
        return user == null ? null : this.getMember(user);
    }

    @Nonnull
    default public @Unmodifiable List<Member> getMembers() {
        return this.getMemberCache().asList();
    }

    @Nonnull
    @Incubating
    default public @Unmodifiable List<Member> getMembersByName(@Nonnull String name, boolean ignoreCase) {
        return this.getMemberCache().getElementsByUsername(name, ignoreCase);
    }

    @Nonnull
    default public @Unmodifiable List<Member> getMembersByNickname(@Nullable String nickname, boolean ignoreCase) {
        return this.getMemberCache().getElementsByNickname(nickname, ignoreCase);
    }

    @Nonnull
    default public @Unmodifiable List<Member> getMembersByEffectiveName(@Nonnull String name, boolean ignoreCase) {
        return this.getMemberCache().getElementsByName(name, ignoreCase);
    }

    @Nonnull
    default public @Unmodifiable List<Member> getMembersWithRoles(Role ... roles) {
        Checks.notNull(roles, "Roles");
        return this.getMembersWithRoles(Arrays.asList(roles));
    }

    @Nonnull
    default public @Unmodifiable List<Member> getMembersWithRoles(@Nonnull Collection<Role> roles) {
        Checks.noneNull(roles, "Roles");
        for (Role role : roles) {
            Checks.check(this.equals(role.getGuild()), "All roles must be from the same guild!");
        }
        return this.getMemberCache().getElementsWithRoles(roles);
    }

    @Nonnull
    public MemberCacheView getMemberCache();

    @Nonnull
    public SortedSnowflakeCacheView<ScheduledEvent> getScheduledEventCache();

    @Nonnull
    default public @Unmodifiable List<ScheduledEvent> getScheduledEventsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getScheduledEventCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public ScheduledEvent getScheduledEventById(@Nonnull String id) {
        return (ScheduledEvent)this.getScheduledEventCache().getElementById(id);
    }

    @Nullable
    default public ScheduledEvent getScheduledEventById(long id) {
        return (ScheduledEvent)this.getScheduledEventCache().getElementById(id);
    }

    @Nonnull
    default public @Unmodifiable List<ScheduledEvent> getScheduledEvents() {
        return this.getScheduledEventCache().asList();
    }

    @Nonnull
    public SortedSnowflakeCacheView<StageChannel> getStageChannelCache();

    @Nonnull
    public SortedSnowflakeCacheView<ThreadChannel> getThreadChannelCache();

    @Nonnull
    public SortedSnowflakeCacheView<Category> getCategoryCache();

    @Nonnull
    public SortedSnowflakeCacheView<TextChannel> getTextChannelCache();

    @Nonnull
    public SortedSnowflakeCacheView<NewsChannel> getNewsChannelCache();

    @Nonnull
    public SortedSnowflakeCacheView<VoiceChannel> getVoiceChannelCache();

    @Nonnull
    public SortedSnowflakeCacheView<ForumChannel> getForumChannelCache();

    @Override
    @Nonnull
    public SortedChannelCacheView<GuildChannel> getChannelCache();

    @Nonnull
    default public @Unmodifiable List<GuildChannel> getChannels() {
        return this.getChannels(true);
    }

    @Nonnull
    public @Unmodifiable List<GuildChannel> getChannels(boolean var1);

    @Nullable
    default public Role getRoleById(@Nonnull String id) {
        return (Role)this.getRoleCache().getElementById(id);
    }

    @Nullable
    default public Role getRoleById(long id) {
        return (Role)this.getRoleCache().getElementById(id);
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
    default public Role getRoleByBot(long userId) {
        return this.getRoleCache().applyStream(stream -> stream.filter(role -> role.getTags().getBotIdLong() == userId).findFirst().orElse(null));
    }

    @Nullable
    default public Role getRoleByBot(@Nonnull String userId) {
        return this.getRoleByBot(MiscUtil.parseSnowflake(userId));
    }

    @Nullable
    default public Role getRoleByBot(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.getRoleByBot(user.getIdLong());
    }

    @Nullable
    default public Role getBotRole() {
        return this.getRoleByBot(this.getJDA().getSelfUser());
    }

    @Nullable
    default public Role getBoostRole() {
        return this.getRoleCache().applyStream(stream -> stream.filter(role -> role.getTags().isBoost()).findFirst().orElse(null));
    }

    @Nonnull
    public SortedSnowflakeCacheView<Role> getRoleCache();

    @Nullable
    default public RichCustomEmoji getEmojiById(@Nonnull String id) {
        return this.getEmojiCache().getElementById(id);
    }

    @Nullable
    default public RichCustomEmoji getEmojiById(long id) {
        return this.getEmojiCache().getElementById(id);
    }

    @Nonnull
    default public @Unmodifiable List<RichCustomEmoji> getEmojis() {
        return this.getEmojiCache().asList();
    }

    @Nonnull
    default public @Unmodifiable List<RichCustomEmoji> getEmojisByName(@Nonnull String name, boolean ignoreCase) {
        return this.getEmojiCache().getElementsByName(name, ignoreCase);
    }

    @Nonnull
    public SnowflakeCacheView<RichCustomEmoji> getEmojiCache();

    @Nullable
    default public GuildSticker getStickerById(@Nonnull String id) {
        return this.getStickerCache().getElementById(id);
    }

    @Nullable
    default public GuildSticker getStickerById(long id) {
        return this.getStickerCache().getElementById(id);
    }

    @Nonnull
    default public @Unmodifiable List<GuildSticker> getStickers() {
        return this.getStickerCache().asList();
    }

    @Nonnull
    default public @Unmodifiable List<GuildSticker> getStickersByName(@Nonnull String name, boolean ignoreCase) {
        return this.getStickerCache().getElementsByName(name, ignoreCase);
    }

    @Nonnull
    public SnowflakeCacheView<GuildSticker> getStickerCache();

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<RichCustomEmoji>> retrieveEmojis();

    @Nonnull
    @CheckReturnValue
    public RestAction<RichCustomEmoji> retrieveEmojiById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<RichCustomEmoji> retrieveEmojiById(long id) {
        return this.retrieveEmojiById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<RichCustomEmoji> retrieveEmoji(@Nonnull CustomEmoji emoji) {
        Checks.notNull(emoji, "Emoji");
        if (emoji instanceof RichCustomEmoji && ((RichCustomEmoji)emoji).getGuild() != null) {
            Checks.check(((RichCustomEmoji)emoji).getGuild().equals(this), "Emoji must be from the same Guild!");
        }
        JDA jda = this.getJDA();
        return new DeferredRestAction<RichCustomEmoji, RestAction>(jda, RichCustomEmoji.class, () -> {
            RichCustomEmoji richEmoji;
            if (emoji instanceof RichCustomEmoji && ((richEmoji = (RichCustomEmoji)emoji).getOwner() != null || !this.getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS))) {
                return richEmoji;
            }
            return null;
        }, () -> this.retrieveEmojiById(emoji.getId()));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<GuildSticker>> retrieveStickers();

    @Nonnull
    @CheckReturnValue
    public RestAction<GuildSticker> retrieveSticker(@Nonnull StickerSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public GuildStickerManager editSticker(@Nonnull StickerSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public BanPaginationAction retrieveBanList();

    @Nonnull
    @CheckReturnValue
    public RestAction<Ban> retrieveBan(@Nonnull UserSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Integer> retrievePrunableMemberCount(int var1);

    @Nonnull
    public Role getPublicRole();

    @Nullable
    public DefaultGuildChannelUnion getDefaultChannel();

    @Nonnull
    @CheckReturnValue
    public GuildManager getManager();

    public boolean isBoostProgressBarEnabled();

    @Nonnull
    @CheckReturnValue
    public AuditLogPaginationAction retrieveAuditLogs();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> leave();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> delete(@Nullable String var1);

    @Nonnull
    public AudioManager getAudioManager();

    @Nonnull
    public Task<Void> requestToSpeak();

    @Nonnull
    public Task<Void> cancelRequestToSpeak();

    @Nonnull
    public JDA getJDA();

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<Invite>> retrieveInvites();

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<Template>> retrieveTemplates();

    @Nonnull
    @CheckReturnValue
    public RestAction<Template> createTemplate(@Nonnull String var1, @Nullable String var2);

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<Webhook>> retrieveWebhooks();

    @Nonnull
    @CheckReturnValue
    public RestAction<GuildWelcomeScreen> retrieveWelcomeScreen();

    @Nonnull
    public List<GuildVoiceState> getVoiceStates();

    @Nonnull
    public VerificationLevel getVerificationLevel();

    @Nonnull
    public NotificationLevel getDefaultNotificationLevel();

    @Nonnull
    public MFALevel getRequiredMFALevel();

    @Nonnull
    public ExplicitContentLevel getExplicitContentLevel();

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> loadMembers() {
        return this.findMembers(m -> true);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> findMembers(@Nonnull Predicate<? super Member> filter) {
        Checks.notNull(filter, "Filter");
        ArrayList list = new ArrayList();
        CompletableFuture future = new CompletableFuture();
        Task<Void> reference = this.loadMembers(member -> {
            if (filter.test((Member)member)) {
                list.add(member);
            }
        });
        GatewayTask<List<Member>> task = new GatewayTask(future, reference::cancel).onSetTimeout(timeout2 -> reference.setTimeout(Duration.ofMillis(timeout2)));
        reference.onSuccess(it -> future.complete(list)).onError(future::completeExceptionally);
        return task;
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> findMembersWithRoles(@Nonnull Collection<Role> roles) {
        Checks.noneNull(roles, "Roles");
        for (Role role2 : roles) {
            Checks.check(this.equals(role2.getGuild()), "All roles must be from the same guild!");
        }
        if (this.isLoaded()) {
            CompletableFuture<List<Member>> future = CompletableFuture.completedFuture(this.getMembersWithRoles(roles));
            return new GatewayTask<List<Member>>(future, () -> {});
        }
        List rolesWithoutPublicRole = roles.stream().filter(role -> !role.isPublicRole()).collect(Collectors.toList());
        if (rolesWithoutPublicRole.isEmpty()) {
            return this.loadMembers();
        }
        return this.findMembers(member -> member.getRoles().containsAll(rolesWithoutPublicRole));
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> findMembersWithRoles(Role ... roles) {
        Checks.noneNull(roles, "Roles");
        return this.findMembersWithRoles(Arrays.asList(roles));
    }

    @Nonnull
    public Task<Void> loadMembers(@Nonnull Consumer<Member> var1);

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<Member> retrieveMember(@Nonnull UserSnowflake user) {
        Checks.notNull(user, "User");
        return this.retrieveMemberById(user.getId());
    }

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<Member> retrieveOwner() {
        return this.retrieveMemberById(this.getOwnerIdLong());
    }

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<Member> retrieveMemberById(@Nonnull String id) {
        return this.retrieveMemberById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<Member> retrieveMemberById(long var1);

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembers(@Nonnull Collection<? extends UserSnowflake> users) {
        Checks.noneNull(users, "Users");
        if (users.isEmpty()) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        long[] ids = users.stream().mapToLong(ISnowflake::getIdLong).toArray();
        return this.retrieveMembersByIds(ids);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembersByIds(@Nonnull Collection<Long> ids) {
        Checks.noneNull(ids, "IDs");
        if (ids.isEmpty()) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        long[] arr = ids.stream().mapToLong(Long::longValue).toArray();
        return this.retrieveMembersByIds(arr);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembersByIds(String ... ids) {
        Checks.notNull(ids, "Array");
        if (ids.length == 0) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        long[] arr = new long[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            arr[i] = MiscUtil.parseSnowflake(ids[i]);
        }
        return this.retrieveMembersByIds(arr);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembersByIds(long ... ids) {
        boolean presence = this.getJDA().getGatewayIntents().contains((Object)GatewayIntent.GUILD_PRESENCES);
        return this.retrieveMembersByIds(presence, ids);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembers(boolean includePresence, @Nonnull Collection<? extends UserSnowflake> users) {
        Checks.noneNull(users, "Users");
        if (users.isEmpty()) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        long[] ids = users.stream().mapToLong(ISnowflake::getIdLong).toArray();
        return this.retrieveMembersByIds(includePresence, ids);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembersByIds(boolean includePresence, @Nonnull Collection<Long> ids) {
        Checks.noneNull(ids, "IDs");
        if (ids.isEmpty()) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        long[] arr = ids.stream().mapToLong(Long::longValue).toArray();
        return this.retrieveMembersByIds(includePresence, arr);
    }

    @Nonnull
    @CheckReturnValue
    default public Task<List<Member>> retrieveMembersByIds(boolean includePresence, String ... ids) {
        Checks.notNull(ids, "Array");
        if (ids.length == 0) {
            return new GatewayTask<List<Member>>(CompletableFuture.completedFuture(Collections.emptyList()), () -> {});
        }
        long[] arr = new long[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            arr[i] = MiscUtil.parseSnowflake(ids[i]);
        }
        return this.retrieveMembersByIds(includePresence, arr);
    }

    @Nonnull
    @CheckReturnValue
    public Task<List<Member>> retrieveMembersByIds(boolean var1, long ... var2);

    @Nonnull
    @CheckReturnValue
    public Task<List<Member>> retrieveMembersByPrefix(@Nonnull String var1, int var2);

    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<ThreadChannel>> retrieveActiveThreads();

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<ScheduledEvent> retrieveScheduledEventById(long id) {
        return this.retrieveScheduledEventById(Long.toUnsignedString(id));
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<ScheduledEvent> retrieveScheduledEventById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> moveVoiceMember(@Nonnull Member var1, @Nullable AudioChannel var2);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> kickVoiceMember(@Nonnull Member member) {
        return this.moveVoiceMember(member, null);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> modifyNickname(@Nonnull Member var1, @Nullable String var2);

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Integer> prune(int days, Role ... roles) {
        return this.prune(days, true, roles);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Integer> prune(int var1, boolean var2, Role ... var3);

    @Nonnull
    @CheckReturnValue
    @Deprecated
    @ForRemoval
    @ReplaceWith(value="kick(user).reason(reason)")
    @DeprecatedSince(value="5.0.0")
    default public AuditableRestAction<Void> kick(@Nonnull UserSnowflake user, @Nullable String reason) {
        return this.kick(user).reason(reason);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> kick(@Nonnull UserSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> ban(@Nonnull UserSnowflake var1, int var2, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<BulkBanResponse> ban(@Nonnull Collection<? extends UserSnowflake> var1, @Nullable Duration var2);

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<BulkBanResponse> ban(@Nonnull Collection<? extends UserSnowflake> users, int deletionTimeframe, @Nonnull TimeUnit unit) {
        Checks.notNull((Object)unit, "TimeUnit");
        return this.ban(users, Duration.ofSeconds(unit.toSeconds(deletionTimeframe)));
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> unban(@Nonnull UserSnowflake var1);

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> timeoutFor(@Nonnull UserSnowflake user, long amount, @Nonnull TimeUnit unit) {
        Checks.check(amount >= 1L, "The amount must be more than 0");
        Checks.notNull((Object)unit, "TimeUnit");
        return this.timeoutUntil(user, Helpers.toOffset(System.currentTimeMillis() + unit.toMillis(amount)));
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> timeoutFor(@Nonnull UserSnowflake user, @Nonnull Duration duration) {
        Checks.notNull(duration, "Duration");
        return this.timeoutUntil(user, Helpers.toOffset(System.currentTimeMillis() + duration.toMillis()));
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> timeoutUntil(@Nonnull UserSnowflake var1, @Nonnull TemporalAccessor var2);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> removeTimeout(@Nonnull UserSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> deafen(@Nonnull UserSnowflake var1, boolean var2);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> mute(@Nonnull UserSnowflake var1, boolean var2);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> addRoleToMember(@Nonnull UserSnowflake var1, @Nonnull Role var2);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> removeRoleFromMember(@Nonnull UserSnowflake var1, @Nonnull Role var2);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull Member var1, @Nullable Collection<Role> var2, @Nullable Collection<Role> var3);

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> modifyMemberRoles(@Nonnull Member member, Role ... roles) {
        return this.modifyMemberRoles(member, Arrays.asList(roles));
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> modifyMemberRoles(@Nonnull Member var1, @Nonnull Collection<Role> var2);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> transferOwnership(@Nonnull Member var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<TextChannel> createTextChannel(@Nonnull String name) {
        return this.createTextChannel(name, null);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<TextChannel> createTextChannel(@Nonnull String var1, @Nullable Category var2);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<NewsChannel> createNewsChannel(@Nonnull String name) {
        return this.createNewsChannel(name, null);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<NewsChannel> createNewsChannel(@Nonnull String var1, @Nullable Category var2);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<VoiceChannel> createVoiceChannel(@Nonnull String name) {
        return this.createVoiceChannel(name, null);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<VoiceChannel> createVoiceChannel(@Nonnull String var1, @Nullable Category var2);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<StageChannel> createStageChannel(@Nonnull String name) {
        return this.createStageChannel(name, null);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<StageChannel> createStageChannel(@Nonnull String var1, @Nullable Category var2);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<ForumChannel> createForumChannel(@Nonnull String name) {
        return this.createForumChannel(name, null);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<ForumChannel> createForumChannel(@Nonnull String var1, @Nullable Category var2);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<MediaChannel> createMediaChannel(@Nonnull String name) {
        return this.createMediaChannel(name, null);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<MediaChannel> createMediaChannel(@Nonnull String var1, @Nullable Category var2);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<Category> createCategory(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public <T extends ICopyableChannel> ChannelAction<T> createCopyOfChannel(@Nonnull T channel) {
        Checks.notNull(channel, "Channel");
        return channel.createCopy(this);
    }

    @Nonnull
    @CheckReturnValue
    public RoleAction createRole();

    @Nonnull
    @CheckReturnValue
    default public RoleAction createCopyOfRole(@Nonnull Role role) {
        Checks.notNull(role, "Role");
        return role.createCopy(this);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<RichCustomEmoji> createEmoji(@Nonnull String var1, @Nonnull Icon var2, Role ... var3);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<GuildSticker> createSticker(@Nonnull String var1, @Nonnull String var2, @Nonnull FileUpload var3, @Nonnull Collection<String> var4);

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<GuildSticker> createSticker(@Nonnull String name, @Nonnull String description, @Nonnull FileUpload file, @Nonnull String tag, String ... tags) {
        ArrayList<String> list = new ArrayList<String>(tags.length + 1);
        list.add(tag);
        Collections.addAll(list, tags);
        return this.createSticker(name, description, file, list);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> deleteSticker(@Nonnull StickerSnowflake var1);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction createScheduledEvent(@Nonnull String var1, @Nonnull String var2, @Nonnull OffsetDateTime var3, @Nonnull OffsetDateTime var4);

    @Nonnull
    @CheckReturnValue
    public ScheduledEventAction createScheduledEvent(@Nonnull String var1, @Nonnull GuildChannel var2, @Nonnull OffsetDateTime var3);

    @Nonnull
    @CheckReturnValue
    public ChannelOrderAction modifyCategoryPositions();

    @Nonnull
    @CheckReturnValue
    public ChannelOrderAction modifyTextChannelPositions();

    @Nonnull
    @CheckReturnValue
    public ChannelOrderAction modifyVoiceChannelPositions();

    @Nonnull
    @CheckReturnValue
    public CategoryOrderAction modifyTextChannelPositions(@Nonnull Category var1);

    @Nonnull
    @CheckReturnValue
    public CategoryOrderAction modifyVoiceChannelPositions(@Nonnull Category var1);

    @Nonnull
    @CheckReturnValue
    default public RoleOrderAction modifyRolePositions() {
        return this.modifyRolePositions(false);
    }

    @Nonnull
    @CheckReturnValue
    public RoleOrderAction modifyRolePositions(boolean var1);

    @Nonnull
    @CheckReturnValue
    public GuildWelcomeScreenManager modifyWelcomeScreen();

    public static enum BoostTier {
        NONE(0, 96000, 50),
        TIER_1(1, 128000, 100),
        TIER_2(2, 256000, 150),
        TIER_3(3, 384000, 250),
        UNKNOWN(-1, Integer.MAX_VALUE, Integer.MAX_VALUE);

        private final int key;
        private final int maxBitrate;
        private final int maxEmojis;

        private BoostTier(int key, int maxBitrate, int maxEmojis) {
            this.key = key;
            this.maxBitrate = maxBitrate;
            this.maxEmojis = maxEmojis;
        }

        public int getKey() {
            return this.key;
        }

        public int getMaxBitrate() {
            return this.maxBitrate;
        }

        public int getMaxEmojis() {
            return this.maxEmojis;
        }

        public long getMaxFileSize() {
            if (this.key == 2) {
                return 0x3200000L;
            }
            if (this.key == 3) {
                return 0x6400000L;
            }
            return 0x1900000L;
        }

        @Nonnull
        public static BoostTier fromKey(int key) {
            for (BoostTier tier : BoostTier.values()) {
                if (tier.key != key) continue;
                return tier;
            }
            return UNKNOWN;
        }
    }

    public static class MetaData {
        private final int memberLimit;
        private final int presenceLimit;
        private final int approximatePresences;
        private final int approximateMembers;

        public MetaData(int memberLimit, int presenceLimit, int approximatePresences, int approximateMembers) {
            this.memberLimit = memberLimit;
            this.presenceLimit = presenceLimit;
            this.approximatePresences = approximatePresences;
            this.approximateMembers = approximateMembers;
        }

        public int getMemberLimit() {
            return this.memberLimit;
        }

        public int getPresenceLimit() {
            return this.presenceLimit;
        }

        public int getApproximatePresences() {
            return this.approximatePresences;
        }

        public int getApproximateMembers() {
            return this.approximateMembers;
        }
    }

    public static class Ban {
        protected final User user;
        protected final String reason;

        public Ban(User user, String reason) {
            this.user = user;
            this.reason = reason;
        }

        @Nonnull
        public User getUser() {
            return this.user;
        }

        @Nullable
        public String getReason() {
            return this.reason;
        }

        public String toString() {
            return new EntityString(this).addMetadata("user", this.user).addMetadata("reason", this.reason).toString();
        }
    }

    public static enum NSFWLevel {
        DEFAULT(0),
        EXPLICIT(1),
        SAFE(2),
        AGE_RESTRICTED(3),
        UNKNOWN(-1);

        private final int key;

        private NSFWLevel(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static NSFWLevel fromKey(int key) {
            for (NSFWLevel level : NSFWLevel.values()) {
                if (level.getKey() != key) continue;
                return level;
            }
            return UNKNOWN;
        }
    }

    public static enum ExplicitContentLevel {
        OFF(0, "Don't scan any messages."),
        NO_ROLE(1, "Scan messages from members without a role."),
        ALL(2, "Scan messages sent by all members."),
        UNKNOWN(-1, "Unknown filter level!");

        private final int key;
        private final String description;

        private ExplicitContentLevel(int key, String description) {
            this.key = key;
            this.description = description;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public String getDescription() {
            return this.description;
        }

        @Nonnull
        public static ExplicitContentLevel fromKey(int key) {
            for (ExplicitContentLevel level : ExplicitContentLevel.values()) {
                if (level.key != key) continue;
                return level;
            }
            return UNKNOWN;
        }
    }

    public static enum MFALevel {
        NONE(0),
        TWO_FACTOR_AUTH(1),
        UNKNOWN(-1);

        private final int key;

        private MFALevel(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static MFALevel fromKey(int key) {
            for (MFALevel level : MFALevel.values()) {
                if (level.getKey() != key) continue;
                return level;
            }
            return UNKNOWN;
        }
    }

    public static enum NotificationLevel {
        ALL_MESSAGES(0),
        MENTIONS_ONLY(1),
        UNKNOWN(-1);

        private final int key;

        private NotificationLevel(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static NotificationLevel fromKey(int key) {
            for (NotificationLevel level : NotificationLevel.values()) {
                if (level.getKey() != key) continue;
                return level;
            }
            return UNKNOWN;
        }
    }

    public static enum VerificationLevel {
        NONE(0),
        LOW(1),
        MEDIUM(2),
        HIGH(3),
        VERY_HIGH(4),
        UNKNOWN(-1);

        private final int key;

        private VerificationLevel(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static VerificationLevel fromKey(int key) {
            for (VerificationLevel level : VerificationLevel.values()) {
                if (level.getKey() != key) continue;
                return level;
            }
            return UNKNOWN;
        }
    }

    public static enum Timeout {
        SECONDS_60(60),
        SECONDS_300(300),
        SECONDS_900(900),
        SECONDS_1800(1800),
        SECONDS_3600(3600);

        private final int seconds;

        private Timeout(int seconds) {
            this.seconds = seconds;
        }

        public int getSeconds() {
            return this.seconds;
        }

        @Nonnull
        public static Timeout fromKey(int seconds) {
            for (Timeout t : Timeout.values()) {
                if (t.getSeconds() != seconds) continue;
                return t;
            }
            throw new IllegalArgumentException("Provided key was not recognized. Seconds: " + seconds);
        }
    }
}

