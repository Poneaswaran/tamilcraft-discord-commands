/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.CollectionUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.map.CaseInsensitiveMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ActionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogChange;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogEntry;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationInfo;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationTeam;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ClientType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.EmbedType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildWelcomeScreen;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageActivity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RichPresence;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RoleIcon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.TeamMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.messages.MessagePoll;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.messages.MessageSnapshot;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StandardSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerItem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerPack;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.templates.Template;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.templates.TemplateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.templates.TemplateGuild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.templates.TemplateRole;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateFlagsEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateTimeOutEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateFlagsEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateGlobalNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ActivityImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ApplicationInfoImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ApplicationTeamImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntitlementImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ForumTagImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildVoiceStateImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildWelcomeScreenImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.InviteImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberPresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MessageMentionsImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.PermissionOverrideImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.RichPresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.RoleImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ScheduledEventImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.SelfUserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.StageInstanceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.TeamMemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ThreadMemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.WebhookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.CategoryImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ForumChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.MediaChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.NewsChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.PrivateChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.StageChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.TextChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.VoiceChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPostContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.AudioChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.ApplicationEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.CustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.RichCustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.UnicodeEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.messages.MessagePollImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.GuildStickerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.RichStickerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.StandardStickerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.StickerItemImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.StickerPackImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.AbstractCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.MemberCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ReadWriteLockCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedSnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;

public class EntityBuilder {
    public static final Logger LOG = JDALogger.getLog(EntityBuilder.class);
    public static final String MISSING_CHANNEL = "MISSING_CHANNEL";
    public static final String MISSING_USER = "MISSING_USER";
    public static final String UNKNOWN_MESSAGE_TYPE = "UNKNOWN_MESSAGE_TYPE";
    private static final Set<String> richGameFields;
    protected final JDAImpl api;

    public EntityBuilder(JDA api) {
        this.api = (JDAImpl)api;
    }

    public JDAImpl getJDA() {
        return this.api;
    }

    public SelfUser createSelfUser(DataObject self) {
        SelfUserImpl selfUser = (SelfUserImpl)(this.getJDA().hasSelfUser() ? this.getJDA().getSelfUser() : null);
        if (selfUser == null) {
            long id = self.getLong("id");
            selfUser = new SelfUserImpl(id, this.getJDA());
            this.getJDA().setSelfUser(selfUser);
        }
        SnowflakeCacheViewImpl<User> userView = this.getJDA().getUsersView();
        try (UnlockHook hook = userView.writeLock();){
            if (userView.getElementById(selfUser.getIdLong()) == null) {
                userView.getMap().put(selfUser.getIdLong(), selfUser);
            }
        }
        if (!self.isNull("application_id")) {
            selfUser.setApplicationId(self.getUnsignedLong("application_id"));
        }
        selfUser.setVerified(self.getBoolean("verified")).setMfaEnabled(self.getBoolean("mfa_enabled")).setName(self.getString("username")).setGlobalName(self.getString("global_name", null)).setDiscriminator(Short.parseShort(self.getString("discriminator", "0"))).setAvatarId(self.getString("avatar", null)).setBot(self.getBoolean("bot")).setSystem(false);
        return selfUser;
    }

    public static Activity createActivity(String name, String url, Activity.ActivityType type) {
        return new ActivityImpl(name, url, type);
    }

    public static EmojiUnion createEmoji(DataObject emoji) {
        return EntityBuilder.createEmoji(emoji, "name", "id");
    }

    public static EmojiUnion createEmoji(DataObject emoji, String nameKey, String idKey) {
        long id = emoji.getUnsignedLong(idKey, 0L);
        if (id == 0L) {
            return new UnicodeEmojiImpl(emoji.getString(nameKey));
        }
        return new CustomEmojiImpl(emoji.getString(nameKey, ""), id, emoji.getBoolean("animated"));
    }

    private void createGuildEmojiPass(GuildImpl guildObj, DataArray array) {
        if (!this.getJDA().isCacheFlagSet(CacheFlag.EMOJI)) {
            return;
        }
        SnowflakeCacheViewImpl<RichCustomEmoji> emojiView = guildObj.getEmojisView();
        try (UnlockHook hook = emojiView.writeLock();){
            TLongObjectMap emojiMap = emojiView.getMap();
            for (int i = 0; i < array.length(); ++i) {
                DataObject object = array.getObject(i);
                if (object.isNull("id")) {
                    LOG.error("Received GUILD_CREATE with an emoji with a null ID. JSON: {}", (Object)object);
                    continue;
                }
                long emojiId = object.getLong("id");
                emojiMap.put(emojiId, this.createEmoji(guildObj, object));
            }
        }
    }

    private void createScheduledEventPass(GuildImpl guildObj, DataArray array) {
        if (!this.getJDA().isCacheFlagSet(CacheFlag.SCHEDULED_EVENTS)) {
            return;
        }
        for (int i = 0; i < array.length(); ++i) {
            DataObject object = array.getObject(i);
            try {
                if (object.isNull("id")) {
                    LOG.error("Received GUILD_CREATE with a scheduled event with a null ID. JSON: {}", (Object)object);
                    continue;
                }
                this.createScheduledEvent(guildObj, object);
                continue;
            }
            catch (ParsingException exception) {
                LOG.error("Received GUILD_CREATE with a scheduled event that failed to parse. JSON: {}", (Object)object, (Object)exception);
            }
        }
    }

    private void createGuildStickerPass(GuildImpl guildObj, DataArray array) {
        if (!this.getJDA().isCacheFlagSet(CacheFlag.STICKER)) {
            return;
        }
        SnowflakeCacheViewImpl<GuildSticker> stickerView = guildObj.getStickersView();
        try (UnlockHook hook = stickerView.writeLock();){
            TLongObjectMap stickerMap = stickerView.getMap();
            for (int i = 0; i < array.length(); ++i) {
                DataObject object = array.getObject(i);
                if (object.isNull("id")) {
                    LOG.error("Received GUILD_CREATE with a sticker with a null ID. GuildId: {} JSON: {}", (Object)guildObj.getId(), (Object)object);
                    continue;
                }
                if (object.getInt("type", -1) != Sticker.Type.GUILD.getId()) {
                    LOG.error("Received GUILD_CREATE with sticker that had an unexpected type. GuildId: {} Type: {} JSON: {}", new Object[]{guildObj.getId(), object.getInt("type", -1), object});
                    continue;
                }
                RichStickerImpl sticker = this.createRichSticker(object);
                stickerMap.put(sticker.getIdLong(), (GuildSticker)((Object)sticker));
            }
        }
    }

    public GuildImpl createGuild(long guildId, DataObject guildJson, TLongObjectMap<DataObject> members, int memberCount) {
        GuildImpl guildObj = new GuildImpl(this.getJDA(), guildId);
        String name = guildJson.getString("name", "");
        String iconId = guildJson.getString("icon", null);
        String splashId = guildJson.getString("splash", null);
        String description = guildJson.getString("description", null);
        String vanityCode = guildJson.getString("vanity_url_code", null);
        String bannerId = guildJson.getString("banner", null);
        String locale = guildJson.getString("preferred_locale", "en-US");
        DataArray roleArray = guildJson.getArray("roles");
        DataArray channelArray = guildJson.getArray("channels");
        DataArray threadArray = guildJson.getArray("threads");
        DataArray scheduledEventsArray = guildJson.getArray("guild_scheduled_events");
        DataArray emojisArray = guildJson.getArray("emojis");
        DataArray voiceStateArray = guildJson.getArray("voice_states");
        Optional<DataArray> stickersArray = guildJson.optArray("stickers");
        Optional<DataArray> featuresArray = guildJson.optArray("features");
        Optional<DataArray> presencesArray = guildJson.optArray("presences");
        long ownerId = guildJson.getUnsignedLong("owner_id", 0L);
        long afkChannelId = guildJson.getUnsignedLong("afk_channel_id", 0L);
        long systemChannelId = guildJson.getUnsignedLong("system_channel_id", 0L);
        long rulesChannelId = guildJson.getUnsignedLong("rules_channel_id", 0L);
        long communityUpdatesChannelId = guildJson.getUnsignedLong("public_updates_channel_id", 0L);
        long safetyAlertsChannelId = guildJson.getUnsignedLong("safety_alerts_channel_id", 0L);
        int boostCount = guildJson.getInt("premium_subscription_count", 0);
        int boostTier = guildJson.getInt("premium_tier", 0);
        int maxMembers = guildJson.getInt("max_members", 0);
        int maxPresences = guildJson.getInt("max_presences", 5000);
        int mfaLevel = guildJson.getInt("mfa_level", 0);
        int afkTimeout = guildJson.getInt("afk_timeout", 0);
        int verificationLevel = guildJson.getInt("verification_level", 0);
        int notificationLevel = guildJson.getInt("default_message_notifications", 0);
        int explicitContentLevel = guildJson.getInt("explicit_content_filter", 0);
        int nsfwLevel = guildJson.getInt("nsfw_level", -1);
        boolean boostProgressBarEnabled = guildJson.getBoolean("premium_progress_bar_enabled");
        guildObj.setName(name).setIconId(iconId).setSplashId(splashId).setDescription(description).setBannerId(bannerId).setVanityCode(vanityCode).setMaxMembers(maxMembers).setMaxPresences(maxPresences).setOwnerId(ownerId).setAfkTimeout(Guild.Timeout.fromKey(afkTimeout)).setVerificationLevel(Guild.VerificationLevel.fromKey(verificationLevel)).setDefaultNotificationLevel(Guild.NotificationLevel.fromKey(notificationLevel)).setExplicitContentLevel(Guild.ExplicitContentLevel.fromKey(explicitContentLevel)).setRequiredMFALevel(Guild.MFALevel.fromKey(mfaLevel)).setLocale(DiscordLocale.from(locale)).setBoostCount(boostCount).setBoostTier(boostTier).setMemberCount(memberCount).setNSFWLevel(Guild.NSFWLevel.fromKey(nsfwLevel)).setBoostProgressBarEnabled(boostProgressBarEnabled);
        SnowflakeCacheViewImpl<Guild> guildView = this.getJDA().getGuildsView();
        try (UnlockHook hook = guildView.writeLock();){
            guildView.getMap().put(guildId, guildObj);
        }
        guildObj.setFeatures(featuresArray.map(array -> array.stream(DataArray::getString).map(String::intern).collect(Collectors.toSet())).orElse(Collections.emptySet()));
        SortedSnowflakeCacheViewImpl<Role> roleView = guildObj.getRolesView();
        try (UnlockHook hook = roleView.writeLock();){
            TLongObjectMap map = roleView.getMap();
            for (int i = 0; i < roleArray.length(); ++i) {
                DataObject obj = roleArray.getObject(i);
                Role role = this.createRole(guildObj, obj, guildId);
                map.put(role.getIdLong(), role);
                if (role.getIdLong() != guildObj.getIdLong()) continue;
                guildObj.setPublicRole(role);
            }
        }
        for (int i = 0; i < channelArray.length(); ++i) {
            DataObject channelJson = channelArray.getObject(i);
            this.createGuildChannel(guildObj, channelJson);
        }
        TLongObjectMap<DataObject> voiceStates = Helpers.convertToMap(o -> o.getUnsignedLong("user_id", 0L), voiceStateArray);
        TLongObjectMap presences = presencesArray.map(o1 -> Helpers.convertToMap(o2 -> o2.getObject("user").getUnsignedLong("id"), o1)).orElseGet(TLongObjectHashMap::new);
        try (UnlockHook h1 = guildObj.getMembersView().writeLock();
             UnlockHook h2 = this.getJDA().getUsersView().writeLock();){
            for (DataObject memberJson : members.valueCollection()) {
                long userId = memberJson.getObject("user").getUnsignedLong("id");
                DataObject voiceState = voiceStates.get(userId);
                DataObject presence = (DataObject)presences.get(userId);
                this.updateMemberCache(this.createMember(guildObj, memberJson, voiceState, presence));
            }
        }
        if (guildObj.getOwner() == null) {
            LOG.debug("Finished setup for guild with a null owner. GuildId: {} OwnerId: {}", (Object)guildId, guildJson.opt("owner_id").orElse(null));
        }
        if (guildObj.getMember(this.api.getSelfUser()) == null) {
            LOG.error("Guild is missing a SelfMember. GuildId: {}", (Object)guildId);
            LOG.debug("Guild is missing a SelfMember. GuildId: {} JSON: \n{}", (Object)guildId, (Object)guildJson);
            guildObj.retrieveMembersByIds(this.api.getSelfUser().getIdLong()).onSuccess(m -> {
                if (m.isEmpty()) {
                    LOG.warn("Was unable to recover SelfMember for guild with id {}. This guild might be corrupted!", (Object)guildId);
                } else {
                    LOG.debug("Successfully recovered SelfMember for guild with id {}.", (Object)guildId);
                }
            });
        }
        for (int i = 0; i < threadArray.length(); ++i) {
            DataObject threadJson = threadArray.getObject(i);
            try {
                this.createThreadChannel(guildObj, threadJson, guildObj.getIdLong());
                continue;
            }
            catch (Exception ex) {
                if (MISSING_CHANNEL.equals(ex.getMessage())) {
                    LOG.debug("Discarding thread without cached parent channel. JSON: {}", (Object)threadJson);
                    continue;
                }
                LOG.warn("Failed to create thread channel for guild with id {}.\nJSON: {}", new Object[]{guildId, threadJson, ex});
            }
        }
        this.createScheduledEventPass(guildObj, scheduledEventsArray);
        this.createGuildEmojiPass(guildObj, emojisArray);
        stickersArray.ifPresent(stickers -> this.createGuildStickerPass(guildObj, (DataArray)stickers));
        guildJson.optArray("stage_instances").map(arr -> arr.stream(DataArray::getObject)).ifPresent(list -> list.forEach(it -> this.createStageInstance(guildObj, (DataObject)it)));
        guildObj.setAfkChannel(guildObj.getVoiceChannelById(afkChannelId)).setSystemChannel(guildObj.getTextChannelById(systemChannelId)).setRulesChannel(guildObj.getTextChannelById(rulesChannelId)).setCommunityUpdatesChannel(guildObj.getTextChannelById(communityUpdatesChannelId)).setSafetyAlertsChannel(guildObj.getTextChannelById(safetyAlertsChannelId));
        return guildObj;
    }

    public GuildChannel createGuildChannel(GuildImpl guildObj, DataObject channelData) {
        ChannelType channelType = ChannelType.fromId(channelData.getInt("type"));
        switch (channelType) {
            case TEXT: {
                return this.createTextChannel(guildObj, channelData, guildObj.getIdLong());
            }
            case NEWS: {
                return this.createNewsChannel(guildObj, channelData, guildObj.getIdLong());
            }
            case STAGE: {
                return this.createStageChannel(guildObj, channelData, guildObj.getIdLong());
            }
            case VOICE: {
                return this.createVoiceChannel(guildObj, channelData, guildObj.getIdLong());
            }
            case CATEGORY: {
                return this.createCategory(guildObj, channelData, guildObj.getIdLong());
            }
            case FORUM: {
                return this.createForumChannel(guildObj, channelData, guildObj.getIdLong());
            }
            case MEDIA: {
                return this.createMediaChannel(guildObj, channelData, guildObj.getIdLong());
            }
        }
        LOG.debug("Cannot create channel for type " + channelData.getInt("type"));
        return null;
    }

    public UserImpl createUser(DataObject user) {
        User.Profile profile;
        UserImpl userObj;
        boolean newUser = false;
        long id = user.getLong("id");
        SnowflakeCacheViewImpl<User> userView = this.getJDA().getUsersView();
        try (UnlockHook hook = userView.readLock();){
            userObj = (UserImpl)userView.getElementById(id);
            if (userObj == null) {
                userObj = new UserImpl(id, this.getJDA());
                newUser = true;
            }
        }
        User.Profile profile2 = profile = user.hasKey("banner") ? new User.Profile(id, user.getString("banner", null), user.getInt("accent_color", 0x1FFFFFFF)) : null;
        if (newUser) {
            userObj.setName(user.getString("username")).setGlobalName(user.getString("global_name", null)).setDiscriminator(Short.parseShort(user.getString("discriminator", "0"))).setAvatarId(user.getString("avatar", null)).setBot(user.getBoolean("bot")).setSystem(user.getBoolean("system")).setFlags(user.getInt("public_flags", 0)).setProfile(profile);
        } else {
            this.updateUser(userObj, user);
        }
        return userObj;
    }

    public void updateUser(UserImpl userObj, DataObject user) {
        String oldName = userObj.getName();
        String newName = user.getString("username");
        String oldGlobalName = userObj.getGlobalName();
        String newGlobalName = user.getString("global_name", null);
        short oldDiscriminator = userObj.getDiscriminatorInt();
        short newDiscriminator = Short.parseShort(user.getString("discriminator", "0"));
        String oldAvatar = userObj.getAvatarId();
        String newAvatar = user.getString("avatar", null);
        int oldFlags = userObj.getFlagsRaw();
        int newFlags = user.getInt("public_flags", 0);
        JDAImpl jda = this.getJDA();
        long responseNumber = jda.getResponseTotal();
        if (!oldName.equals(newName)) {
            userObj.setName(newName);
            jda.handleEvent(new UserUpdateNameEvent(jda, responseNumber, userObj, oldName));
        }
        if (!Objects.equals(oldGlobalName, newGlobalName)) {
            userObj.setGlobalName(newGlobalName);
            jda.handleEvent(new UserUpdateGlobalNameEvent(jda, responseNumber, userObj, oldGlobalName));
        }
        if (oldDiscriminator != newDiscriminator) {
            String oldDiscrimString = userObj.getDiscriminator();
            userObj.setDiscriminator(newDiscriminator);
            jda.handleEvent(new UserUpdateDiscriminatorEvent(jda, responseNumber, userObj, oldDiscrimString));
        }
        if (!Objects.equals(oldAvatar, newAvatar)) {
            userObj.setAvatarId(newAvatar);
            jda.handleEvent(new UserUpdateAvatarEvent(jda, responseNumber, userObj, oldAvatar));
        }
        if (oldFlags != newFlags) {
            userObj.setFlags(newFlags);
            jda.handleEvent(new UserUpdateFlagsEvent(jda, responseNumber, userObj, User.UserFlag.getFlags(oldFlags)));
        }
    }

    public boolean updateMemberCache(MemberImpl member) {
        return this.updateMemberCache(member, false);
    }

    public boolean updateMemberCache(MemberImpl member, boolean forceRemove) {
        GuildImpl guild = member.getGuild();
        UserImpl user = (UserImpl)member.getUser();
        MemberCacheViewImpl membersView = guild.getMembersView();
        if (forceRemove || !this.getJDA().cacheMember(member)) {
            GuildVoiceStateImpl voiceState;
            if (membersView.remove(member.getIdLong()) == null) {
                return false;
            }
            LOG.trace("Unloading member {}", (Object)member);
            if (user.getMutualGuilds().isEmpty()) {
                this.getJDA().getUsersView().remove(user.getIdLong());
            }
            if ((voiceState = (GuildVoiceStateImpl)member.getVoiceState()) != null) {
                AudioChannelUnion connectedChannel = voiceState.getChannel();
                if (connectedChannel instanceof AudioChannelMixin) {
                    ((AudioChannelMixin)connectedChannel).getConnectedMembersMap().remove(member.getIdLong());
                }
                voiceState.setConnectedChannel(null);
            }
            return false;
        }
        if (guild.getMemberById(member.getIdLong()) != null) {
            return true;
        }
        LOG.trace("Loading member {}", (Object)member);
        if (this.getJDA().getUserById(user.getIdLong()) == null) {
            SnowflakeCacheViewImpl<User> usersView = this.getJDA().getUsersView();
            try (UnlockHook hook1 = usersView.writeLock();){
                usersView.getMap().put(user.getIdLong(), user);
            }
        }
        try (UnlockHook hook = membersView.writeLock();){
            membersView.getMap().put(member.getIdLong(), member);
            if (member.isOwner()) {
                guild.setOwner(member);
            }
        }
        long hashId = guild.getIdLong() ^ user.getIdLong();
        this.getJDA().getEventCache().playbackCache(EventCache.Type.USER, member.getIdLong());
        this.getJDA().getEventCache().playbackCache(EventCache.Type.MEMBER, hashId);
        return true;
    }

    public MemberImpl createMember(GuildImpl guild, DataObject memberJson) {
        return this.createMember(guild, memberJson, null, null);
    }

    public MemberImpl createMember(GuildImpl guild, DataObject memberJson, DataObject voiceStateJson, DataObject presence) {
        boolean playbackCache = false;
        UserImpl user = this.createUser(memberJson.getObject("user"));
        DataArray roleArray = memberJson.getArray("roles");
        MemberImpl member = (MemberImpl)guild.getMember(user);
        if (member == null) {
            member = new MemberImpl(guild, user);
            member.setNickname(memberJson.getString("nick", null));
            member.setAvatarId(memberJson.getString("avatar", null));
            if (!memberJson.isNull("flags")) {
                member.setFlags(memberJson.getInt("flags"));
            }
            long boostTimestamp = memberJson.isNull("premium_since") ? 0L : Helpers.toTimestamp(memberJson.getString("premium_since"));
            member.setBoostDate(boostTimestamp);
            long timeOutTimestamp = memberJson.isNull("communication_disabled_until") ? 0L : Helpers.toTimestamp(memberJson.getString("communication_disabled_until"));
            member.setTimeOutEnd(timeOutTimestamp);
            if (!memberJson.isNull("pending")) {
                member.setPending(memberJson.getBoolean("pending"));
            }
            Set<Role> roles = member.getRoleSet();
            for (int i = 0; i < roleArray.length(); ++i) {
                long roleId = roleArray.getUnsignedLong(i);
                Role role = guild.getRoleById(roleId);
                if (role == null) continue;
                roles.add(role);
            }
        } else {
            ArrayList<Role> roles = new ArrayList<Role>(roleArray.length());
            for (int i = 0; i < roleArray.length(); ++i) {
                long roleId = roleArray.getUnsignedLong(i);
                Role role = guild.getRoleById(roleId);
                if (role == null) continue;
                roles.add(role);
            }
            this.updateMember(guild, member, memberJson, roles);
        }
        if (!memberJson.isNull("joined_at") && !member.hasTimeJoined()) {
            member.setJoinDate(Helpers.toTimestamp(memberJson.getString("joined_at")));
        }
        if (voiceStateJson != null && member.getVoiceState() != null) {
            this.createVoiceState(guild, voiceStateJson, user, member);
        }
        if (presence != null) {
            this.createPresence(member, presence);
        }
        return member;
    }

    private void createVoiceState(GuildImpl guild, DataObject voiceStateJson, User user, MemberImpl member) {
        GuildVoiceStateImpl voiceState = (GuildVoiceStateImpl)member.getVoiceState();
        long channelId = voiceStateJson.getLong("channel_id");
        AudioChannel audioChannel = (AudioChannel)guild.getGuildChannelById(channelId);
        if (audioChannel != null) {
            ((AudioChannelMixin)audioChannel).getConnectedMembersMap().put(member.getIdLong(), member);
        } else {
            LOG.error("Received a GuildVoiceState with a channel ID for a non-existent channel! ChannelId: {} GuildId: {} UserId: {}", new Object[]{channelId, guild.getId(), user.getId()});
        }
        String requestToSpeak = voiceStateJson.getString("request_to_speak_timestamp", null);
        OffsetDateTime timestamp = null;
        if (requestToSpeak != null) {
            timestamp = OffsetDateTime.parse(requestToSpeak);
        }
        voiceState.setSelfMuted(voiceStateJson.getBoolean("self_mute")).setSelfDeafened(voiceStateJson.getBoolean("self_deaf")).setGuildMuted(voiceStateJson.getBoolean("mute")).setGuildDeafened(voiceStateJson.getBoolean("deaf")).setSuppressed(voiceStateJson.getBoolean("suppress")).setSessionId(voiceStateJson.getString("session_id")).setStream(voiceStateJson.getBoolean("self_stream")).setRequestToSpeak(timestamp).setConnectedChannel(audioChannel);
    }

    public void updateMember(GuildImpl guild, MemberImpl member, DataObject content, List<Role> newRoles) {
        int oldFlags;
        int flags;
        boolean oldPending;
        boolean pending;
        OffsetDateTime oldTime;
        String newAvatarId;
        String oldAvatarId;
        String newNick;
        String oldNick;
        long responseNumber = this.getJDA().getResponseTotal();
        if (newRoles != null) {
            this.updateMemberRoles(member, newRoles, responseNumber);
        }
        if (content.hasKey("nick") && !Objects.equals(oldNick = member.getNickname(), newNick = content.getString("nick", null))) {
            member.setNickname(newNick);
            this.getJDA().handleEvent(new GuildMemberUpdateNicknameEvent(this.getJDA(), responseNumber, member, oldNick));
        }
        if (content.hasKey("avatar") && !Objects.equals(oldAvatarId = member.getAvatarId(), newAvatarId = content.getString("avatar", null))) {
            member.setAvatarId(newAvatarId);
            this.getJDA().handleEvent(new GuildMemberUpdateAvatarEvent(this.getJDA(), responseNumber, member, oldAvatarId));
        }
        if (content.hasKey("premium_since")) {
            long epoch = 0L;
            if (!content.isNull("premium_since")) {
                epoch = Helpers.toTimestamp(content.getString("premium_since"));
            }
            if (epoch != member.getBoostDateRaw()) {
                oldTime = member.getTimeBoosted();
                member.setBoostDate(epoch);
                this.getJDA().handleEvent(new GuildMemberUpdateBoostTimeEvent(this.getJDA(), responseNumber, member, oldTime));
            }
        }
        if (content.hasKey("communication_disabled_until")) {
            long epoch = 0L;
            if (!content.isNull("communication_disabled_until")) {
                epoch = Helpers.toTimestamp(content.getString("communication_disabled_until"));
            }
            if (epoch != member.getTimeOutEndRaw()) {
                oldTime = member.getTimeOutEnd();
                member.setTimeOutEnd(epoch);
                this.getJDA().handleEvent(new GuildMemberUpdateTimeOutEvent(this.getJDA(), responseNumber, member, oldTime));
            }
        }
        if (!content.isNull("joined_at") && !member.hasTimeJoined()) {
            String joinedAtRaw = content.getString("joined_at");
            TemporalAccessor joinedAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(joinedAtRaw);
            long joinEpoch = Instant.from(joinedAt).toEpochMilli();
            member.setJoinDate(joinEpoch);
        }
        if (!content.isNull("pending") && (pending = content.getBoolean("pending")) != (oldPending = member.isPending())) {
            member.setPending(pending);
            this.getJDA().handleEvent(new GuildMemberUpdatePendingEvent(this.getJDA(), responseNumber, member, oldPending));
        }
        if (!content.isNull("flags") && (flags = content.getInt("flags")) != (oldFlags = member.getFlagsRaw())) {
            member.setFlags(flags);
            this.getJDA().handleEvent(new GuildMemberUpdateFlagsEvent(this.getJDA(), responseNumber, member, Member.MemberFlag.fromRaw(oldFlags)));
        }
        this.updateUser((UserImpl)member.getUser(), content.getObject("user"));
    }

    private void updateMemberRoles(MemberImpl member, List<Role> newRoles, long responseNumber) {
        Set<Role> currentRoles = member.getRoleSet();
        LinkedList<Role> removedRoles = new LinkedList<Role>();
        block0: for (Role role : currentRoles) {
            Iterator<Role> it = newRoles.iterator();
            while (it.hasNext()) {
                Role r = it.next();
                if (!role.equals(r)) continue;
                it.remove();
                continue block0;
            }
            removedRoles.add(role);
        }
        if (removedRoles.size() > 0) {
            currentRoles.removeAll(removedRoles);
        }
        if (newRoles.size() > 0) {
            currentRoles.addAll(newRoles);
        }
        if (removedRoles.size() > 0) {
            this.getJDA().handleEvent(new GuildMemberRoleRemoveEvent(this.getJDA(), responseNumber, member, removedRoles));
        }
        if (newRoles.size() > 0) {
            this.getJDA().handleEvent(new GuildMemberRoleAddEvent(this.getJDA(), responseNumber, member, newRoles));
        }
    }

    public void createPresence(MemberImpl member, DataObject presenceJson) {
        if (member == null) {
            throw new NullPointerException("Provided member was null!");
        }
        OnlineStatus onlineStatus = OnlineStatus.fromKey(presenceJson.getString("status"));
        if (onlineStatus == OnlineStatus.OFFLINE) {
            return;
        }
        MemberPresenceImpl presence = member.getPresence();
        if (presence == null) {
            CacheView.SimpleCacheView<MemberPresenceImpl> view = member.getGuild().getPresenceView();
            if (view == null) {
                return;
            }
            presence = new MemberPresenceImpl();
            try (UnlockHook lock = view.writeLock();){
                view.getMap().put(member.getIdLong(), presence);
            }
        }
        boolean cacheGame = this.getJDA().isCacheFlagSet(CacheFlag.ACTIVITY);
        boolean cacheStatus = this.getJDA().isCacheFlagSet(CacheFlag.CLIENT_STATUS);
        DataArray activityArray = !cacheGame || presenceJson.isNull("activities") ? null : presenceJson.getArray("activities");
        DataObject clientStatusJson = !cacheStatus || presenceJson.isNull("client_status") ? null : presenceJson.getObject("client_status");
        ArrayList<Activity> activities = new ArrayList<Activity>();
        boolean parsedActivity = false;
        if (cacheGame && activityArray != null) {
            for (int i = 0; i < activityArray.length(); ++i) {
                try {
                    activities.add(EntityBuilder.createActivity(activityArray.getObject(i)));
                    parsedActivity = true;
                    continue;
                }
                catch (Exception ex) {
                    String userId = member.getId();
                    if (LOG.isDebugEnabled()) {
                        LOG.warn("Encountered exception trying to parse a presence! UserId: {} JSON: {}", new Object[]{userId, activityArray, ex});
                        continue;
                    }
                    LOG.warn("Encountered exception trying to parse a presence! UserId: {} Message: {} Enable debug for details", (Object)userId, (Object)ex.getMessage());
                }
            }
        }
        if (cacheGame && parsedActivity) {
            presence.setActivities(activities);
        }
        presence.setOnlineStatus(onlineStatus);
        if (clientStatusJson != null) {
            for (String key : clientStatusJson.keys()) {
                ClientType type = ClientType.fromKey(key);
                OnlineStatus status = OnlineStatus.fromKey(clientStatusJson.getString(key));
                presence.setOnlineStatus(type, status);
            }
        }
    }

    public static Activity createActivity(DataObject gameJson) {
        String state;
        Activity.ActivityType type;
        String name = String.valueOf(gameJson.get("name"));
        String url = gameJson.isNull("url") ? null : String.valueOf(gameJson.get("url"));
        try {
            type = gameJson.isNull("type") ? Activity.ActivityType.PLAYING : Activity.ActivityType.fromKey(Integer.parseInt(gameJson.get("type").toString()));
        }
        catch (NumberFormatException e) {
            type = Activity.ActivityType.PLAYING;
        }
        Activity.Timestamps timestamps = null;
        if (!gameJson.isNull("timestamps")) {
            DataObject obj = gameJson.getObject("timestamps");
            long start = obj.getLong("start", 0L);
            long end = obj.getLong("end", 0L);
            timestamps = new Activity.Timestamps(start, end);
        }
        EmojiUnion emoji = null;
        if (!gameJson.isNull("emoji")) {
            emoji = EntityBuilder.createEmoji(gameJson.getObject("emoji"));
        }
        if (type == Activity.ActivityType.CUSTOM_STATUS && gameJson.hasKey("state")) {
            name = gameJson.getString("state", "");
            gameJson = gameJson.remove("state");
        }
        String string = state = gameJson.isNull("state") ? null : String.valueOf(gameJson.get("state"));
        if (!CollectionUtils.containsAny(gameJson.keys(), richGameFields)) {
            return new ActivityImpl(name, state, url, type, timestamps, emoji);
        }
        long id = gameJson.getLong("application_id", 0L);
        String sessionId = gameJson.getString("session_id", null);
        String syncId = gameJson.getString("sync_id", null);
        int flags = gameJson.getInt("flags", 0);
        String details = gameJson.isNull("details") ? null : String.valueOf(gameJson.get("details"));
        RichPresence.Party party = null;
        if (!gameJson.isNull("party")) {
            DataObject obj = gameJson.getObject("party");
            String partyId = obj.isNull("id") ? null : obj.getString("id");
            DataArray sizeArr = obj.isNull("size") ? null : obj.getArray("size");
            long size = 0L;
            long max = 0L;
            if (sizeArr != null && sizeArr.length() > 0) {
                size = sizeArr.getLong(0);
                max = sizeArr.length() < 2 ? 0L : sizeArr.getLong(1);
            }
            party = new RichPresence.Party(partyId, size, max);
        }
        String smallImageKey = null;
        String smallImageText = null;
        String largeImageKey = null;
        String largeImageText = null;
        if (!gameJson.isNull("assets")) {
            DataObject assets = gameJson.getObject("assets");
            if (!assets.isNull("small_image")) {
                smallImageKey = String.valueOf(assets.get("small_image"));
                String string2 = smallImageText = assets.isNull("small_text") ? null : String.valueOf(assets.get("small_text"));
            }
            if (!assets.isNull("large_image")) {
                largeImageKey = String.valueOf(assets.get("large_image"));
                largeImageText = assets.isNull("large_text") ? null : String.valueOf(assets.get("large_text"));
            }
        }
        return new RichPresenceImpl(type, name, url, id, emoji, party, details, state, timestamps, syncId, sessionId, flags, largeImageKey, largeImageText, smallImageKey, smallImageText);
    }

    public RichCustomEmojiImpl createEmoji(GuildImpl guildObj, DataObject json) {
        DataArray emojiRoles = json.optArray("roles").orElseGet(DataArray::empty);
        long emojiId = json.getLong("id");
        UserImpl user = json.isNull("user") ? null : this.createUser(json.getObject("user"));
        RichCustomEmojiImpl emojiObj = (RichCustomEmojiImpl)guildObj.getEmojiById(emojiId);
        if (emojiObj == null) {
            emojiObj = new RichCustomEmojiImpl(emojiId, guildObj);
        }
        Set<Role> roleSet = emojiObj.getRoleSet();
        roleSet.clear();
        for (int j = 0; j < emojiRoles.length(); ++j) {
            Role role = guildObj.getRoleById(emojiRoles.getString(j));
            if (role == null) continue;
            roleSet.add(role);
        }
        if (user != null) {
            emojiObj.setOwner(user);
        }
        return emojiObj.setName(json.getString("name", "")).setAnimated(json.getBoolean("animated")).setManaged(json.getBoolean("managed")).setAvailable(json.getBoolean("available", true));
    }

    public ApplicationEmojiImpl createApplicationEmoji(JDAImpl api, DataObject json, User owner) {
        long emojiId = json.getUnsignedLong("id");
        return new ApplicationEmojiImpl(emojiId, api, owner).setAnimated(json.getBoolean("animated")).setName(json.getString("name"));
    }

    public ScheduledEvent createScheduledEvent(GuildImpl guild, DataObject json) {
        long id = json.getLong("id");
        ScheduledEventImpl scheduledEvent = (ScheduledEventImpl)guild.getScheduledEventsView().get(id);
        if (scheduledEvent == null) {
            SortedSnowflakeCacheViewImpl<ScheduledEvent> scheduledEventView = guild.getScheduledEventsView();
            try (UnlockHook hook = scheduledEventView.writeLock();){
                scheduledEvent = new ScheduledEventImpl(id, guild);
                if (this.getJDA().isCacheFlagSet(CacheFlag.SCHEDULED_EVENTS)) {
                    scheduledEventView.getMap().put(id, scheduledEvent);
                }
            }
        }
        scheduledEvent.setName(json.getString("name")).setDescription(json.getString("description", null)).setStatus(ScheduledEvent.Status.fromKey(json.getInt("status", -1))).setInterestedUserCount(json.getInt("user_count", -1)).setStartTime(json.getOffsetDateTime("scheduled_start_time")).setEndTime(json.getOffsetDateTime("scheduled_end_time", null)).setImage(json.getString("image", null));
        long creatorId = json.getLong("creator_id", 0L);
        scheduledEvent.setCreatorId(creatorId);
        if (creatorId != 0L) {
            if (json.hasKey("creator")) {
                scheduledEvent.setCreator(this.createUser(json.getObject("creator")));
            } else {
                scheduledEvent.setCreator(this.getJDA().getUserById(creatorId));
            }
        }
        ScheduledEvent.Type type = ScheduledEvent.Type.fromKey(json.getInt("entity_type"));
        scheduledEvent.setType(type);
        switch (type) {
            case STAGE_INSTANCE: 
            case VOICE: {
                scheduledEvent.setLocation(json.getString("channel_id"));
                break;
            }
            case EXTERNAL: {
                String externalLocation = json.isNull("entity_metadata") || json.getObject("entity_metadata").isNull("location") ? "" : json.getObject("entity_metadata").getString("location");
                scheduledEvent.setLocation(externalLocation);
            }
        }
        return scheduledEvent;
    }

    public Category createCategory(DataObject json, long guildId) {
        return this.createCategory(null, json, guildId);
    }

    public Category createCategory(GuildImpl guild, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        CategoryImpl channel = (CategoryImpl)this.getJDA().getCategoryById(id);
        if (channel == null) {
            if (guild == null) {
                guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new CategoryImpl(id, guild);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        ((CategoryImpl)channel.setName(json.getString("name"))).setPosition(json.getInt("position"));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public TextChannel createTextChannel(DataObject json, long guildId) {
        return this.createTextChannel(null, json, guildId);
    }

    public TextChannel createTextChannel(GuildImpl guildObj, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        TextChannelImpl channel = (TextChannelImpl)this.getJDA().getTextChannelById(id);
        if (channel == null) {
            if (guildObj == null) {
                guildObj = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guildObj.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new TextChannelImpl(id, guildObj);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        ((TextChannelImpl)((TextChannelImpl)((TextChannelImpl)((TextChannelImpl)((TextChannelImpl)((TextChannelImpl)((TextChannelImpl)channel.setParentCategory(json.getLong("parent_id", 0L))).setLatestMessageIdLong(json.getLong("last_message_id", 0L))).setName(json.getString("name"))).setTopic(json.getString("topic", null))).setPosition(json.getInt("position"))).setNSFW(json.getBoolean("nsfw"))).setDefaultThreadSlowmode(json.getInt("default_thread_rate_limit_per_user", 0))).setSlowmode(json.getInt("rate_limit_per_user", 0));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public NewsChannel createNewsChannel(DataObject json, long guildId) {
        return this.createNewsChannel(null, json, guildId);
    }

    public NewsChannel createNewsChannel(GuildImpl guildObj, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        NewsChannelImpl channel = (NewsChannelImpl)this.getJDA().getNewsChannelById(id);
        if (channel == null) {
            if (guildObj == null) {
                guildObj = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guildObj.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new NewsChannelImpl(id, guildObj);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        ((NewsChannelImpl)((NewsChannelImpl)((NewsChannelImpl)((NewsChannelImpl)((NewsChannelImpl)channel.setParentCategory(json.getLong("parent_id", 0L))).setLatestMessageIdLong(json.getLong("last_message_id", 0L))).setName(json.getString("name"))).setTopic(json.getString("topic", null))).setPosition(json.getInt("position"))).setNSFW(json.getBoolean("nsfw"));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public VoiceChannel createVoiceChannel(DataObject json, long guildId) {
        return this.createVoiceChannel(null, json, guildId);
    }

    public VoiceChannel createVoiceChannel(GuildImpl guild, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        VoiceChannelImpl channel = (VoiceChannelImpl)this.getJDA().getVoiceChannelById(id);
        if (channel == null) {
            if (guild == null) {
                guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new VoiceChannelImpl(id, guild);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        ((VoiceChannelImpl)((VoiceChannelImpl)((VoiceChannelImpl)channel.setParentCategory(json.getLong("parent_id", 0L))).setLatestMessageIdLong(json.getLong("last_message_id", 0L)).setName(json.getString("name"))).setStatus(json.getString("status", "")).setPosition(json.getInt("position"))).setUserLimit(json.getInt("user_limit")).setNSFW(json.getBoolean("nsfw")).setBitrate(json.getInt("bitrate")).setRegion(json.getString("rtc_region", null)).setSlowmode(json.getInt("rate_limit_per_user", 0));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public StageChannel createStageChannel(DataObject json, long guildId) {
        return this.createStageChannel(null, json, guildId);
    }

    public StageChannel createStageChannel(GuildImpl guild, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        StageChannelImpl channel = (StageChannelImpl)this.getJDA().getStageChannelById(id);
        if (channel == null) {
            if (guild == null) {
                guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new StageChannelImpl(id, guild);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        ((StageChannelImpl)((StageChannelImpl)((StageChannelImpl)channel.setParentCategory(json.getLong("parent_id", 0L))).setLatestMessageIdLong(json.getLong("last_message_id", 0L)).setName(json.getString("name"))).setPosition(json.getInt("position"))).setBitrate(json.getInt("bitrate")).setUserLimit(json.getInt("user_limit", 0)).setNSFW(json.getBoolean("nsfw")).setRegion(json.getString("rtc_region", null)).setSlowmode(json.getInt("rate_limit_per_user", 0));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public ThreadChannel createThreadChannel(DataObject json, long guildId) {
        return this.createThreadChannel(null, json, guildId);
    }

    public ThreadChannel createThreadChannel(GuildImpl guild, DataObject json, long guildId) {
        return this.createThreadChannel(guild, json, guildId, true);
    }

    public ThreadChannel createThreadChannel(GuildImpl guild, DataObject json, long guildId, boolean modifyCache) {
        IThreadContainer parent;
        boolean playbackCache = false;
        long id = json.getUnsignedLong("id");
        long parentId = json.getUnsignedLong("parent_id");
        ChannelType type = ChannelType.fromId(json.getInt("type"));
        if (guild == null) {
            guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
        }
        if ((parent = guild.getChannelById(IThreadContainer.class, parentId)) == null) {
            throw new IllegalArgumentException(MISSING_CHANNEL);
        }
        ThreadChannelImpl channel = (ThreadChannelImpl)this.getJDA().getThreadChannelById(id);
        if (channel == null) {
            SortedChannelCacheViewImpl<GuildChannel> guildThreadView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> threadView = this.getJDA().getChannelsView();
            try (UnlockHook vlock = guildThreadView.writeLock();
                 UnlockHook jlock = threadView.writeLock();){
                channel = new ThreadChannelImpl(id, guild, type);
                if (modifyCache) {
                    guildThreadView.put(channel);
                    playbackCache = threadView.put(channel) == null;
                }
            }
        }
        DataObject threadMetadata = json.getObject("thread_metadata");
        if (!json.isNull("applied_tags") && this.api.isCacheFlagSet(CacheFlag.FORUM_TAGS)) {
            DataArray array = json.getArray("applied_tags");
            channel.setAppliedTags(IntStream.range(0, array.length()).mapToLong(array::getUnsignedLong));
        }
        ((ThreadChannelImpl)channel.setName(json.getString("name"))).setFlags(json.getInt("flags", 0)).setParentChannel(parent).setOwnerId(json.getLong("owner_id")).setMemberCount(json.getInt("member_count")).setMessageCount(json.getInt("message_count")).setTotalMessageCount(json.getInt("total_message_count", 0)).setLatestMessageIdLong(json.getLong("last_message_id", 0L)).setSlowmode(json.getInt("rate_limit_per_user", 0)).setLocked(threadMetadata.getBoolean("locked")).setArchived(threadMetadata.getBoolean("archived")).setInvitable(threadMetadata.getBoolean("invitable")).setArchiveTimestamp(Helpers.toTimestamp(threadMetadata.getString("archive_timestamp"))).setCreationTimestamp(threadMetadata.isNull("create_timestamp") ? 0L : Helpers.toTimestamp(threadMetadata.getString("create_timestamp"))).setAutoArchiveDuration(ThreadChannel.AutoArchiveDuration.fromKey(threadMetadata.getInt("auto_archive_duration")));
        if (!json.isNull("member")) {
            ThreadMember selfThreadMember = this.createThreadMember(channel, guild.getSelfMember(), json.getObject("member"));
            CacheView.SimpleCacheView<ThreadMember> view = channel.getThreadMemberView();
            try (UnlockHook lock = view.writeLock();){
                view.getMap().put(selfThreadMember.getIdLong(), selfThreadMember);
            }
        }
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public ThreadMember createThreadMember(GuildImpl guild, ThreadChannelImpl threadChannel, DataObject json) {
        DataObject memberJson = json.getObject("member");
        DataObject presenceJson = json.isNull("presence") ? null : json.getObject("presence");
        MemberImpl member = this.createMember(guild, memberJson, null, presenceJson);
        return this.createThreadMember(threadChannel, member, json);
    }

    public ThreadMember createThreadMember(ThreadChannelImpl threadChannel, Member member, DataObject json) {
        ThreadMemberImpl threadMember = new ThreadMemberImpl(member, threadChannel);
        threadMember.setJoinedTimestamp(Helpers.toTimestamp(json.getString("join_timestamp")));
        return threadMember;
    }

    public ForumChannel createForumChannel(DataObject json, long guildId) {
        return this.createForumChannel(null, json, guildId);
    }

    public ForumChannel createForumChannel(GuildImpl guild, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        ForumChannelImpl channel = (ForumChannelImpl)this.getJDA().getForumChannelById(id);
        if (channel == null) {
            if (guild == null) {
                guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new ForumChannelImpl(id, guild);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        if (this.api.isCacheFlagSet(CacheFlag.FORUM_TAGS)) {
            DataArray tags = json.getArray("available_tags");
            for (int i = 0; i < tags.length(); ++i) {
                this.createForumTag(channel, tags.getObject(i), i);
            }
        }
        ((ForumChannelImpl)channel.setParentCategory(json.getLong("parent_id", 0L)).setFlags(json.getInt("flags", 0)).setDefaultReaction(json.optObject("default_reaction_emoji").orElse(null)).setDefaultSortOrder(json.getInt("default_sort_order", -1)).setDefaultLayout(json.getInt("default_forum_layout", -1)).setName(json.getString("name"))).setTopic(json.getString("topic", null)).setPosition(json.getInt("position")).setDefaultThreadSlowmode(json.getInt("default_thread_rate_limit_per_user", 0)).setSlowmode(json.getInt("rate_limit_per_user", 0)).setNSFW(json.getBoolean("nsfw"));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public MediaChannel createMediaChannel(DataObject json, long guildId) {
        return this.createMediaChannel(null, json, guildId);
    }

    public MediaChannel createMediaChannel(GuildImpl guild, DataObject json, long guildId) {
        boolean playbackCache = false;
        long id = json.getLong("id");
        MediaChannelImpl channel = (MediaChannelImpl)this.getJDA().getMediaChannelById(id);
        if (channel == null) {
            if (guild == null) {
                guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
            }
            SortedChannelCacheViewImpl<GuildChannel> guildView = guild.getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.getJDA().getChannelsView();
            try (UnlockHook glock = guildView.writeLock();
                 UnlockHook jlock = globalView.writeLock();){
                channel = new MediaChannelImpl(id, guild);
                guildView.put(channel);
                playbackCache = globalView.put(channel) == null;
            }
        }
        if (this.api.isCacheFlagSet(CacheFlag.FORUM_TAGS)) {
            DataArray tags = json.getArray("available_tags");
            for (int i = 0; i < tags.length(); ++i) {
                this.createForumTag(channel, tags.getObject(i), i);
            }
        }
        ((MediaChannelImpl)channel.setParentCategory(json.getLong("parent_id", 0L)).setFlags(json.getInt("flags", 0)).setDefaultReaction(json.optObject("default_reaction_emoji").orElse(null)).setDefaultSortOrder(json.getInt("default_sort_order", -1)).setName(json.getString("name"))).setTopic(json.getString("topic", null)).setPosition(json.getInt("position")).setDefaultThreadSlowmode(json.getInt("default_thread_rate_limit_per_user", 0)).setSlowmode(json.getInt("rate_limit_per_user", 0)).setNSFW(json.getBoolean("nsfw"));
        this.createOverridesPass(channel, json.getArray("permission_overwrites"));
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, id);
        }
        return channel;
    }

    public ForumTagImpl createForumTag(IPostContainerMixin<?> channel, DataObject json, int index) {
        long id = json.getUnsignedLong("id");
        SortedSnowflakeCacheView cache = channel.getAvailableTagCache();
        ForumTagImpl tag = (ForumTagImpl)((AbstractCacheView)((Object)cache)).get(id);
        if (tag == null) {
            try (UnlockHook lock = ((ReadWriteLockCache)((Object)cache)).writeLock();){
                tag = new ForumTagImpl(id);
                ((AbstractCacheView)((Object)cache)).getMap().put(id, tag);
            }
        }
        tag.setName(json.getString("name")).setModerated(json.getBoolean("moderated")).setEmoji(json).setPosition(index);
        return tag;
    }

    public PrivateChannel createPrivateChannel(DataObject json) {
        return this.createPrivateChannel(json, null);
    }

    public PrivateChannel createPrivateChannel(DataObject json, UserImpl user) {
        long channelId = json.getUnsignedLong("id");
        PrivateChannelImpl channel = (PrivateChannelImpl)this.api.getPrivateChannelById(channelId);
        if (channel == null) {
            channel = new PrivateChannelImpl(this.getJDA(), channelId, user).setLatestMessageIdLong(json.getLong("last_message_id", 0L));
        }
        UserImpl recipient = user;
        if (channel.getUser() == null) {
            if (recipient == null && (json.hasKey("recipients") || json.hasKey("recipient"))) {
                DataObject recipientJson = json.hasKey("recipients") ? json.getArray("recipients").getObject(0) : json.getObject("recipient");
                long userId = recipientJson.getUnsignedLong("id");
                recipient = (UserImpl)this.getJDA().getUserById(userId);
                if (recipient == null) {
                    recipient = this.createUser(recipientJson);
                }
            }
            if (recipient != null) {
                channel.setUser(recipient);
            }
        }
        if (recipient != null) {
            recipient.setPrivateChannel(channel);
        }
        this.cachePrivateChannel(channel);
        this.api.usedPrivateChannel(channelId);
        return channel;
    }

    private void cachePrivateChannel(PrivateChannelImpl priv) {
        ChannelCacheViewImpl<Channel> privateView = this.getJDA().getChannelsView();
        privateView.put(priv);
        this.api.usedPrivateChannel(priv.getIdLong());
        this.getJDA().getEventCache().playbackCache(EventCache.Type.CHANNEL, priv.getIdLong());
    }

    @Nullable
    public StageInstance createStageInstance(GuildImpl guild, DataObject json) {
        long channelId = json.getUnsignedLong("channel_id");
        StageChannelImpl channel = (StageChannelImpl)guild.getStageChannelById(channelId);
        if (channel == null) {
            return null;
        }
        long id = json.getUnsignedLong("id");
        String topic = json.getString("topic");
        StageInstance.PrivacyLevel level = StageInstance.PrivacyLevel.fromKey(json.getInt("privacy_level", -1));
        StageInstanceImpl instance = (StageInstanceImpl)channel.getStageInstance();
        if (instance == null) {
            instance = new StageInstanceImpl(id, channel);
            channel.setStageInstance(instance);
        }
        return instance.setPrivacyLevel(level).setTopic(topic);
    }

    public void createOverridesPass(IPermissionContainerMixin<?> channel, DataArray overrides) {
        for (int i = 0; i < overrides.length(); ++i) {
            try {
                this.createPermissionOverride(overrides.getObject(i), channel);
                continue;
            }
            catch (NoSuchElementException e) {
                LOG.debug("{}. Ignoring PermissionOverride.", (Object)e.getMessage());
                continue;
            }
            catch (IllegalArgumentException e) {
                LOG.warn("{}. Ignoring PermissionOverride.", (Object)e.getMessage());
            }
        }
    }

    public Role createRole(GuildImpl guild, DataObject roleJson, long guildId) {
        RoleImpl role;
        boolean playbackCache = false;
        long id = roleJson.getLong("id");
        if (guild == null) {
            guild = (GuildImpl)this.getJDA().getGuildsView().get(guildId);
        }
        if ((role = (RoleImpl)guild.getRolesView().get(id)) == null) {
            SortedSnowflakeCacheViewImpl<Role> roleView = guild.getRolesView();
            try (UnlockHook hook = roleView.writeLock();){
                role = new RoleImpl(id, guild);
                playbackCache = roleView.getMap().put(id, role) == null;
            }
        }
        int color = roleJson.getInt("color");
        role.setName(roleJson.getString("name")).setRawPosition(roleJson.getInt("position")).setRawPermissions(roleJson.getLong("permissions")).setManaged(roleJson.getBoolean("managed")).setHoisted(roleJson.getBoolean("hoist")).setColor(color == 0 ? 0x1FFFFFFF : color).setMentionable(roleJson.getBoolean("mentionable")).setTags(roleJson.optObject("tags").orElseGet(DataObject::empty));
        String iconId = roleJson.getString("icon", null);
        String emoji = roleJson.getString("unicode_emoji", null);
        if (iconId == null && emoji == null) {
            role.setIcon(null);
        } else {
            role.setIcon(new RoleIcon(iconId, emoji, id));
        }
        if (playbackCache) {
            this.getJDA().getEventCache().playbackCache(EventCache.Type.ROLE, id);
        }
        return role;
    }

    public ReceivedMessage createMessageBestEffort(DataObject json, MessageChannel channel, Guild guild) {
        if (channel != null) {
            return this.createMessageWithChannel(json, channel, false);
        }
        return this.createMessageFromWebhook(json, guild);
    }

    public ReceivedMessage createMessageFromWebhook(DataObject json, @Nullable Guild guild) {
        return this.createMessage0(json, null, (GuildImpl)guild, false);
    }

    public ReceivedMessage createMessageWithChannel(DataObject json, @Nonnull MessageChannel channel, boolean modifyCache) {
        if (channel instanceof GuildMessageChannel) {
            return this.createMessage0(json, channel, (GuildImpl)((GuildMessageChannel)channel).getGuild(), modifyCache);
        }
        if (channel instanceof PrivateChannel) {
            return this.createMessageWithLookup(json, null, modifyCache);
        }
        throw new IllegalArgumentException(MISSING_CHANNEL);
    }

    public ReceivedMessage createMessageWithLookup(DataObject json, @Nullable Guild guild, boolean modifyCache) {
        if (guild == null) {
            return this.createMessage0(json, this.createPrivateChannelByMessage(json), null, modifyCache);
        }
        MessageChannel channel = guild.getChannelById(GuildMessageChannel.class, json.getUnsignedLong("channel_id"));
        return this.createMessage0(json, channel, (GuildImpl)guild, modifyCache);
    }

    private PrivateChannel createPrivateChannelByMessage(DataObject message) {
        boolean isRecipient;
        long channelId = message.getLong("channel_id");
        DataObject author = message.getObject("author");
        PrivateChannelImpl channel = (PrivateChannelImpl)this.getJDA().getPrivateChannelById(channelId);
        boolean bl = isRecipient = !author.getBoolean("bot");
        if (channel == null) {
            DataObject channelData = DataObject.empty().put("id", channelId);
            if (isRecipient) {
                channelData.put("recipient", author);
            }
            channel = (PrivateChannelImpl)this.createPrivateChannel(channelData);
        } else if (channel.getUser() == null && isRecipient) {
            UserImpl user = this.createUser(author);
            channel.setUser(user);
            user.setPrivateChannel(channel);
        }
        return channel;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ReceivedMessage createMessage0(DataObject jsonObject, @Nullable MessageChannel channel, @Nullable GuildImpl guild, boolean modifyCache) {
        User user;
        MessageType type = MessageType.fromId(jsonObject.getInt("type"));
        if (type == MessageType.UNKNOWN) {
            throw new IllegalArgumentException(UNKNOWN_MESSAGE_TYPE);
        }
        long id = jsonObject.getLong("id");
        DataObject author = jsonObject.getObject("author");
        long authorId = author.getLong("id");
        long channelId = jsonObject.getUnsignedLong("channel_id");
        long guildId = channel instanceof GuildChannel ? ((GuildChannel)((Object)channel)).getGuild().getIdLong() : jsonObject.getUnsignedLong("guild_id", 0L);
        MemberImpl member = null;
        if (guild != null && !jsonObject.isNull("member")) {
            DataObject memberJson = jsonObject.getObject("member");
            memberJson.put("user", author);
            member = this.createMember(guild, memberJson);
            if (modifyCache) {
                this.updateMemberCache(member);
            }
        }
        String content = jsonObject.getString("content", "");
        boolean fromWebhook = jsonObject.hasKey("webhook_id");
        long applicationId = jsonObject.getUnsignedLong("application_id", 0L);
        boolean pinned = jsonObject.getBoolean("pinned");
        boolean tts = jsonObject.getBoolean("tts");
        boolean mentionsEveryone = jsonObject.getBoolean("mention_everyone");
        OffsetDateTime editTime = jsonObject.isNull("edited_timestamp") ? null : OffsetDateTime.parse(jsonObject.getString("edited_timestamp"));
        String nonce = jsonObject.isNull("nonce") ? null : jsonObject.get("nonce").toString();
        int flags = jsonObject.getInt("flags", 0);
        MessageChannel tmpChannel = channel;
        List<Message.Attachment> attachments = this.map(jsonObject, "attachments", this::createMessageAttachment);
        List<MessageEmbed> embeds = this.map(jsonObject, "embeds", this::createMessageEmbed);
        List<MessageReaction> reactions = this.map(jsonObject, "reactions", obj -> this.createMessageReaction(tmpChannel, channelId, id, (DataObject)obj));
        List<StickerItem> stickers = this.map(jsonObject, "sticker_items", this::createStickerItem);
        List<LayoutComponent> components = this.map(jsonObject, "components", ActionRow::fromData, obj -> obj.getInt("type", -1) == 1);
        MessagePoll poll = jsonObject.optObject("poll").map(EntityBuilder::createMessagePoll).orElse(null);
        MessageActivity activity = null;
        if (!jsonObject.isNull("activity")) {
            activity = EntityBuilder.createMessageActivity(jsonObject);
        }
        if (guild != null) {
            if (member == null) {
                member = (MemberImpl)guild.getMemberById(authorId);
            }
            User user2 = user = member != null ? member.getUser() : null;
            if (user == null) {
                if (!fromWebhook && modifyCache) throw new IllegalArgumentException(MISSING_USER);
                user = this.createUser(author);
            }
        } else {
            user = channel instanceof PrivateChannel ? (authorId == this.getJDA().getSelfUser().getIdLong() ? this.getJDA().getSelfUser() : ((PrivateChannel)channel).getUser()) : this.createUser(author);
        }
        if (modifyCache && !fromWebhook) {
            this.updateUser((UserImpl)user, author);
        }
        ReceivedMessage referencedMessage = null;
        if (!jsonObject.isNull("referenced_message")) {
            DataObject referenceJson = jsonObject.getObject("referenced_message");
            try {
                referencedMessage = this.createMessage0(referenceJson, channel, guild, false);
            }
            catch (IllegalArgumentException ex) {
                if (UNKNOWN_MESSAGE_TYPE.equals(ex.getMessage())) {
                    LOG.debug("Received referenced message with unknown type. Type: {}", (Object)referenceJson.getInt("type", -1));
                }
                if (!MISSING_CHANNEL.equals(ex.getMessage())) throw ex;
                LOG.debug("Received referenced message with unknown channel. channel_id: {} Type: {}", (Object)referenceJson.getUnsignedLong("channel_id", 0L), (Object)referenceJson.getInt("type", -1));
            }
        }
        List<MessageSnapshot> snapshots2 = Collections.emptyList();
        MessageReference messageReference = null;
        if (!jsonObject.isNull("message_reference")) {
            DataObject messageReferenceJson = jsonObject.getObject("message_reference");
            MessageReference finalReference = messageReference = new MessageReference(messageReferenceJson.getInt("type", -1), messageReferenceJson.getLong("message_id", 0L), messageReferenceJson.getLong("channel_id", 0L), messageReferenceJson.getLong("guild_id", 0L), referencedMessage, this.api);
            snapshots2 = this.map(jsonObject, "message_snapshots", obj -> this.createMessageSnapshot(finalReference, obj.getObject("message")));
        }
        Message.Interaction messageInteraction = null;
        if (!jsonObject.isNull("interaction")) {
            messageInteraction = this.createMessageInteraction(guild, jsonObject.getObject("interaction"));
        }
        MessageMentionsImpl mentions = new MessageMentionsImpl(this.api, guild, content, mentionsEveryone, jsonObject.getArray("mentions"), jsonObject.getArray("mention_roles"));
        ThreadChannel startedThread = null;
        if (guild != null && !jsonObject.isNull("thread")) {
            startedThread = this.createThreadChannel(guild, jsonObject.getObject("thread"), guild.getIdLong());
        }
        int position = jsonObject.getInt("position", -1);
        return new ReceivedMessage(id, channelId, guildId, this.api, guild, channel, type, messageReference, fromWebhook, applicationId, tts, pinned, content, nonce, user, member, activity, poll, editTime, mentions, reactions, attachments, embeds, stickers, components, snapshots2, flags, messageInteraction, startedThread, position);
    }

    private static MessageActivity createMessageActivity(DataObject jsonObject) {
        DataObject activityData = jsonObject.getObject("activity");
        MessageActivity.ActivityType activityType = MessageActivity.ActivityType.fromId(activityData.getInt("type"));
        String partyId = activityData.getString("party_id", null);
        MessageActivity.Application application = null;
        if (!jsonObject.isNull("application")) {
            DataObject applicationData = jsonObject.getObject("application");
            String name = applicationData.getString("name");
            String description = applicationData.getString("description", "");
            String iconId = applicationData.getString("icon", null);
            String coverId = applicationData.getString("cover_image", null);
            long applicationId = applicationData.getLong("id");
            application = new MessageActivity.Application(name, description, iconId, coverId, applicationId);
        }
        if (activityType == MessageActivity.ActivityType.UNKNOWN) {
            LOG.debug("Received an unknown ActivityType, Activity: {}", (Object)activityData);
        }
        return new MessageActivity(activityType, partyId, application);
    }

    public static MessagePollImpl createMessagePoll(DataObject data) {
        MessagePoll.LayoutType layout = MessagePoll.LayoutType.fromKey(data.getInt("layout_type"));
        OffsetDateTime expiresAt = data.isNull("expiry") ? null : data.getOffsetDateTime("expiry");
        boolean isMultiAnswer = data.getBoolean("allow_multiselect");
        DataArray answersData = data.getArray("answers");
        DataObject questionData = data.getObject("question");
        DataObject resultsData = data.optObject("results").orElseGet(() -> DataObject.empty().put("answer_counts", DataArray.empty()));
        boolean isFinalized = resultsData.getBoolean("is_finalized");
        DataArray resultVotes = resultsData.getArray("answer_counts");
        TLongObjectHashMap voteMapping = new TLongObjectHashMap();
        resultVotes.stream(DataArray::getObject).forEach(votes -> voteMapping.put(votes.getLong("id"), votes));
        MessagePoll.Question question = new MessagePoll.Question(questionData.getString("text"), questionData.optObject("emoji").map(Emoji::fromData).orElse(null));
        List<MessagePoll.Answer> answers = answersData.stream(DataArray::getObject).map(answer -> {
            long answerId = answer.getLong("answer_id");
            DataObject media = answer.getObject("poll_media");
            DataObject votes = (DataObject)voteMapping.get(answerId);
            return new MessagePoll.Answer(answerId, media.getString("text"), media.optObject("emoji").map(Emoji::fromData).orElse(null), votes != null ? votes.getInt("count") : 0, votes != null && votes.getBoolean("me_voted"));
        }).collect(Helpers.toUnmodifiableList());
        return new MessagePollImpl(layout, question, answers, expiresAt, isMultiAnswer, isFinalized);
    }

    public MessageReaction createMessageReaction(MessageChannel chan, long channelId, long messageId, DataObject obj) {
        DataObject emoji = obj.getObject("emoji");
        int[] count = new int[]{obj.getInt("count", 0), obj.optObject("count_details").map(o -> o.getInt("normal", 0)).orElse(0), obj.optObject("count_details").map(o -> o.getInt("burst", 0)).orElse(0)};
        boolean[] me = new boolean[]{obj.getBoolean("me"), obj.getBoolean("me_burst")};
        EmojiUnion emojiObj = EntityBuilder.createEmoji(emoji);
        return new MessageReaction(this.api, chan, emojiObj, channelId, messageId, me, count);
    }

    public Message.Attachment createMessageAttachment(DataObject jsonObject) {
        boolean ephemeral = jsonObject.getBoolean("ephemeral", false);
        int width = jsonObject.getInt("width", -1);
        int height = jsonObject.getInt("height", -1);
        int size = jsonObject.getInt("size");
        String url = jsonObject.getString("url");
        String proxyUrl = jsonObject.getString("proxy_url");
        String filename = jsonObject.getString("filename");
        String contentType = jsonObject.getString("content_type", null);
        String description = jsonObject.getString("description", null);
        long id = jsonObject.getLong("id");
        String waveform = jsonObject.getString("waveform", null);
        double duration = jsonObject.getDouble("duration_secs", 0.0);
        return new Message.Attachment(id, url, proxyUrl, filename, contentType, description, size, height, width, ephemeral, waveform, duration, this.getJDA());
    }

    public MessageEmbed createMessageEmbed(DataObject content) {
        MessageEmbed.ImageInfo image;
        MessageEmbed.Footer footer;
        MessageEmbed.VideoInfo video;
        MessageEmbed.AuthorInfo author;
        MessageEmbed.Provider provider;
        MessageEmbed.Thumbnail thumbnail;
        int color;
        if (content.isNull("type")) {
            throw new IllegalStateException("Encountered embed object with missing/null type field for Json: " + content);
        }
        EmbedType type = EmbedType.fromKey(content.getString("type"));
        String url = content.getString("url", null);
        String title = content.getString("title", null);
        String description = content.getString("description", null);
        OffsetDateTime timestamp = content.isNull("timestamp") ? null : OffsetDateTime.parse(content.getString("timestamp"));
        int n = color = content.isNull("color") ? 0x1FFFFFFF : content.getInt("color");
        if (content.isNull("thumbnail")) {
            thumbnail = null;
        } else {
            DataObject obj2 = content.getObject("thumbnail");
            thumbnail = new MessageEmbed.Thumbnail(obj2.getString("url", null), obj2.getString("proxy_url", null), obj2.getInt("width", -1), obj2.getInt("height", -1));
        }
        if (content.isNull("provider")) {
            provider = null;
        } else {
            DataObject obj3 = content.getObject("provider");
            provider = new MessageEmbed.Provider(obj3.getString("name", null), obj3.getString("url", null));
        }
        if (content.isNull("author")) {
            author = null;
        } else {
            DataObject obj4 = content.getObject("author");
            author = new MessageEmbed.AuthorInfo(obj4.getString("name", null), obj4.getString("url", null), obj4.getString("icon_url", null), obj4.getString("proxy_icon_url", null));
        }
        if (content.isNull("video")) {
            video = null;
        } else {
            DataObject obj5 = content.getObject("video");
            video = new MessageEmbed.VideoInfo(obj5.getString("url", null), obj5.getString("proxy_url", null), obj5.getInt("width", -1), obj5.getInt("height", -1));
        }
        if (content.isNull("footer")) {
            footer = null;
        } else {
            DataObject obj6 = content.getObject("footer");
            footer = new MessageEmbed.Footer(obj6.getString("text", null), obj6.getString("icon_url", null), obj6.getString("proxy_icon_url", null));
        }
        if (content.isNull("image")) {
            image = null;
        } else {
            DataObject obj7 = content.getObject("image");
            image = new MessageEmbed.ImageInfo(obj7.getString("url", null), obj7.getString("proxy_url", null), obj7.getInt("width", -1), obj7.getInt("height", -1));
        }
        List<MessageEmbed.Field> fields = this.map(content, "fields", obj -> new MessageEmbed.Field(obj.getString("name", null), obj.getString("value", null), obj.getBoolean("inline"), false));
        return EntityBuilder.createMessageEmbed(url, title, description, type, timestamp, color, thumbnail, provider, author, video, footer, image, fields);
    }

    public static MessageEmbed createMessageEmbed(String url, String title, String description, EmbedType type, OffsetDateTime timestamp, int color, MessageEmbed.Thumbnail thumbnail, MessageEmbed.Provider siteProvider, MessageEmbed.AuthorInfo author, MessageEmbed.VideoInfo videoInfo, MessageEmbed.Footer footer, MessageEmbed.ImageInfo image, List<MessageEmbed.Field> fields) {
        return new MessageEmbed(url, title, description, type, timestamp, color, thumbnail, siteProvider, author, videoInfo, footer, image, fields);
    }

    public StickerItem createStickerItem(DataObject content) {
        long id = content.getLong("id");
        String name = content.getString("name");
        Sticker.StickerFormat format = Sticker.StickerFormat.fromId(content.getInt("format_type"));
        return new StickerItemImpl(id, format, name);
    }

    public RichStickerImpl createRichSticker(DataObject content) {
        long id = content.getLong("id");
        String name = content.getString("name");
        Sticker.StickerFormat format = Sticker.StickerFormat.fromId(content.getInt("format_type"));
        Sticker.Type type = Sticker.Type.fromId(content.getInt("type", -1));
        String description = content.getString("description", "");
        Set<String> tags = Collections.emptySet();
        if (!content.isNull("tags")) {
            String[] array = content.getString("tags").split(",\\s*");
            tags = Helpers.setOf(array);
        }
        switch (type) {
            case GUILD: {
                boolean available = content.getBoolean("available");
                long guildId = content.getUnsignedLong("guild_id", 0L);
                UserImpl owner = content.isNull("user") ? null : this.createUser(content.getObject("user"));
                return new GuildStickerImpl(id, format, name, tags, description, available, guildId, this.api, owner);
            }
            case STANDARD: {
                long packId = content.getUnsignedLong("pack_id", 0L);
                int sortValue = content.getInt("sort_value", -1);
                return new StandardStickerImpl(id, format, name, tags, description, packId, sortValue);
            }
        }
        throw new IllegalArgumentException("Unknown sticker type. Type: " + (Object)((Object)type) + " JSON: " + content);
    }

    public StickerPack createStickerPack(DataObject content) {
        long id = content.getUnsignedLong("id");
        String name = content.getString("name");
        String description = content.getString("description", "");
        long skuId = content.getUnsignedLong("sku_id", 0L);
        long coverId = content.getUnsignedLong("cover_sticker_id", 0L);
        long bannerId = content.getUnsignedLong("banner_asset_id", 0L);
        DataArray stickerArr = content.getArray("stickers");
        ArrayList<StandardSticker> stickers = new ArrayList<StandardSticker>(stickerArr.length());
        for (int i = 0; i < stickerArr.length(); ++i) {
            DataObject object = null;
            try {
                object = stickerArr.getObject(i);
                StandardSticker sticker = (StandardSticker)((Object)this.createRichSticker(object));
                stickers.add(sticker);
                continue;
            }
            catch (ParsingException | ClassCastException ex) {
                LOG.error("Sticker contained in pack {} ({}) could not be parsed. JSON: {}", new Object[]{name, id, object});
            }
        }
        return new StickerPackImpl(id, stickers, name, description, coverId, bannerId, skuId);
    }

    public Message.Interaction createMessageInteraction(GuildImpl guildImpl, DataObject content) {
        long id = content.getLong("id");
        int type = content.getInt("type");
        String name = content.getString("name");
        DataObject userJson = content.getObject("user");
        User user = null;
        MemberImpl member = null;
        if (!content.isNull("member") && guildImpl != null) {
            DataObject memberJson = content.getObject("member");
            memberJson.put("user", userJson);
            member = this.createMember(guildImpl, memberJson);
            user = member.getUser();
        } else {
            user = this.createUser(userJson);
        }
        return new Message.Interaction(id, type, name, user, member);
    }

    public MessageSnapshot createMessageSnapshot(MessageReference messageReference, DataObject jsonObject) {
        MessageType type = MessageType.fromId(jsonObject.getInt("type"));
        String content = jsonObject.getString("content", "");
        OffsetDateTime editTime = jsonObject.isNull("edited_timestamp") ? null : OffsetDateTime.parse(jsonObject.getString("edited_timestamp"));
        int flags = jsonObject.getInt("flags", 0);
        boolean mentionsEveryone = jsonObject.getBoolean("mention_everyone");
        List<Message.Attachment> attachments = this.map(jsonObject, "attachments", this::createMessageAttachment);
        List<MessageEmbed> embeds = this.map(jsonObject, "embeds", this::createMessageEmbed);
        List<StickerItem> stickers = this.map(jsonObject, "sticker_items", this::createStickerItem);
        List<LayoutComponent> components = this.map(jsonObject, "components", ActionRow::fromData, obj -> obj.getInt("type", -1) == 1);
        Guild guild = messageReference.getGuild();
        MessageMentionsImpl mentions = new MessageMentionsImpl(this.api, guild instanceof GuildImpl ? (GuildImpl)guild : null, content, mentionsEveryone, jsonObject.getArray("mentions"), jsonObject.optArray("mention_roles").orElseGet(DataArray::empty));
        return new MessageSnapshot(type, mentions, editTime, content, attachments, embeds, components, stickers, flags);
    }

    @Nullable
    public PermissionOverride createPermissionOverride(DataObject override, IPermissionContainerMixin<?> chan) {
        boolean role;
        int type = override.getInt("type");
        long id = override.getLong("id");
        boolean bl = role = type == 0;
        if (role && chan.getGuild().getRoleById(id) == null) {
            throw new NoSuchElementException("Attempted to create a PermissionOverride for a non-existent role! JSON: " + override);
        }
        if (!role && type != 1) {
            throw new IllegalArgumentException("Provided with an unknown PermissionOverride type! JSON: " + override);
        }
        if (!role && id != this.api.getSelfUser().getIdLong() && !this.api.isCacheFlagSet(CacheFlag.MEMBER_OVERRIDES)) {
            return null;
        }
        long allow = override.getLong("allow");
        long deny = override.getLong("deny");
        if (id == chan.getGuild().getIdLong() && (allow | deny) == 0L) {
            return null;
        }
        PermissionOverrideImpl permOverride = (PermissionOverrideImpl)chan.getPermissionOverrideMap().get(id);
        if (permOverride == null) {
            permOverride = new PermissionOverrideImpl(chan, id, role);
            chan.getPermissionOverrideMap().put(id, permOverride);
        }
        return permOverride.setAllow(allow).setDeny(deny);
    }

    public WebhookImpl createWebhook(DataObject object) {
        return this.createWebhook(object, false);
    }

    public WebhookImpl createWebhook(DataObject object, boolean allowMissingChannel) {
        DataObject source2;
        long id = object.getLong("id");
        long guildId = object.getUnsignedLong("guild_id");
        long channelId = object.getUnsignedLong("channel_id");
        String token = object.getString("token", null);
        WebhookType type = WebhookType.fromKey(object.getInt("type", -1));
        IWebhookContainer channel = this.getJDA().getChannelById(IWebhookContainer.class, channelId);
        if (channel == null && !allowMissingChannel) {
            throw new NullPointerException(String.format("Tried to create Webhook for an un-cached IWebhookContainer channel! WebhookId: %s ChannelId: %s GuildId: %s", id, channelId, guildId));
        }
        Object name = !object.isNull("name") ? object.get("name") : null;
        Object avatar = !object.isNull("avatar") ? object.get("avatar") : null;
        DataObject fakeUser = DataObject.empty().put("username", name).put("discriminator", "0000").put("id", id).put("avatar", avatar);
        UserImpl defaultUser = this.createUser(fakeUser);
        Optional<DataObject> ownerJson = object.optObject("user");
        User owner = null;
        if (ownerJson.isPresent()) {
            DataObject json = ownerJson.get();
            long userId = json.getLong("id");
            owner = this.getJDA().getUserById(userId);
            if (owner == null) {
                json.put("id", userId);
                owner = this.createUser(json);
            }
        }
        Member ownerMember = owner == null || channel == null ? null : channel.getGuild().getMember(owner);
        WebhookImpl webhook = new WebhookImpl(channel, this.getJDA(), id, type).setToken(token).setOwner(ownerMember, owner).setUser(defaultUser);
        if (!object.isNull("source_channel")) {
            source2 = object.getObject("source_channel");
            webhook.setSourceChannel(new Webhook.ChannelReference(source2.getUnsignedLong("id"), source2.getString("name")));
        }
        if (!object.isNull("source_guild")) {
            source2 = object.getObject("source_guild");
            webhook.setSourceGuild(new Webhook.GuildReference(source2.getUnsignedLong("id"), source2.getString("name")));
        }
        return webhook;
    }

    public Invite createInvite(DataObject object) {
        OffsetDateTime timeCreated;
        boolean temporary;
        int uses;
        int maxUses;
        int maxAge;
        boolean expanded;
        InviteImpl.InviteTargetImpl target;
        InviteImpl.GroupImpl group;
        InviteImpl.ChannelImpl channel;
        InviteImpl.GuildImpl guild;
        Invite.InviteType type;
        String code = object.getString("code");
        UserImpl inviter = object.hasKey("inviter") ? this.createUser(object.getObject("inviter")) : null;
        DataObject channelObject = object.getObject("channel");
        ChannelType channelType = ChannelType.fromId(channelObject.getInt("type"));
        Invite.TargetType targetType = Invite.TargetType.fromId(object.getInt("target_type", 0));
        if (channelType == ChannelType.GROUP) {
            type = Invite.InviteType.GROUP;
            guild = null;
            channel = null;
            String groupName = channelObject.getString("name", "");
            long groupId = channelObject.getLong("id");
            String groupIconId = channelObject.getString("icon", null);
            List<String> usernames = channelObject.isNull("recipients") ? null : this.map(channelObject, "recipients", json -> json.getString("username"));
            group = new InviteImpl.GroupImpl(groupIconId, groupName, groupId, usernames);
        } else if (channelType.isGuild()) {
            type = Invite.InviteType.GUILD;
            DataObject guildObject = object.getObject("guild");
            String guildIconId = guildObject.getString("icon", null);
            long guildId = guildObject.getLong("id");
            String guildName = guildObject.getString("name");
            String guildSplashId = guildObject.getString("splash", null);
            Guild.VerificationLevel guildVerificationLevel = Guild.VerificationLevel.fromKey(guildObject.getInt("verification_level", -1));
            int presenceCount = object.getInt("approximate_presence_count", -1);
            int memberCount = object.getInt("approximate_member_count", -1);
            Set<String> guildFeatures = guildObject.isNull("features") ? Collections.emptySet() : Collections.unmodifiableSet(StreamSupport.stream(guildObject.getArray("features").spliterator(), false).map(String::valueOf).collect(Collectors.toSet()));
            GuildWelcomeScreen welcomeScreen = guildObject.isNull("welcome_screen") ? null : this.createWelcomeScreen(null, guildObject.getObject("welcome_screen"));
            guild = new InviteImpl.GuildImpl(guildId, guildIconId, guildName, guildSplashId, guildVerificationLevel, presenceCount, memberCount, guildFeatures, welcomeScreen);
            String channelName = channelObject.getString("name");
            long channelId = channelObject.getLong("id");
            channel = new InviteImpl.ChannelImpl(channelId, channelName, channelType);
            group = null;
        } else {
            type = Invite.InviteType.UNKNOWN;
            guild = null;
            channel = null;
            group = null;
        }
        switch (targetType) {
            case EMBEDDED_APPLICATION: {
                DataObject applicationObject = object.getObject("target_application");
                InviteImpl.EmbeddedApplicationImpl application = new InviteImpl.EmbeddedApplicationImpl(applicationObject.getString("icon", null), applicationObject.getString("name"), applicationObject.getString("description"), applicationObject.getString("summary"), applicationObject.getLong("id"), applicationObject.getInt("max_participants", -1));
                target = new InviteImpl.InviteTargetImpl(targetType, application, null);
                break;
            }
            case STREAM: {
                DataObject targetUserObject = object.getObject("target_user");
                target = new InviteImpl.InviteTargetImpl(targetType, null, this.createUser(targetUserObject));
                break;
            }
            case NONE: {
                target = null;
                break;
            }
            default: {
                target = new InviteImpl.InviteTargetImpl(targetType, null, null);
            }
        }
        if (object.hasKey("max_uses")) {
            expanded = true;
            maxAge = object.getInt("max_age");
            maxUses = object.getInt("max_uses");
            uses = object.getInt("uses");
            temporary = object.getBoolean("temporary");
            timeCreated = OffsetDateTime.parse(object.getString("created_at"));
        } else {
            expanded = false;
            maxAge = -1;
            maxUses = -1;
            uses = -1;
            temporary = false;
            timeCreated = null;
        }
        return new InviteImpl(this.getJDA(), code, expanded, inviter, maxAge, maxUses, temporary, timeCreated, uses, channel, guild, group, target, type);
    }

    public GuildWelcomeScreen createWelcomeScreen(Guild guild, DataObject object) {
        DataArray welcomeChannelsArray = object.getArray("welcome_channels");
        ArrayList<GuildWelcomeScreenImpl.ChannelImpl> welcomeChannels = new ArrayList<GuildWelcomeScreenImpl.ChannelImpl>(welcomeChannelsArray.length());
        for (int i = 0; i < welcomeChannelsArray.length(); ++i) {
            DataObject welcomeChannelObj = welcomeChannelsArray.getObject(i);
            EmojiUnion emoji = null;
            if (!welcomeChannelObj.isNull("emoji_id") || !welcomeChannelObj.isNull("emoji_name")) {
                emoji = EntityBuilder.createEmoji(welcomeChannelObj, "emoji_name", "emoji_id");
            }
            welcomeChannels.add(new GuildWelcomeScreenImpl.ChannelImpl(guild, welcomeChannelObj.getLong("channel_id"), welcomeChannelObj.getString("description"), emoji));
        }
        return new GuildWelcomeScreenImpl(guild, object.getString("description", null), Collections.unmodifiableList(welcomeChannels));
    }

    public Template createTemplate(DataObject object) {
        String code = object.getString("code");
        String name = object.getString("name");
        String description = object.getString("description", null);
        int uses = object.getInt("usage_count");
        UserImpl creator = this.createUser(object.getObject("creator"));
        OffsetDateTime createdAt = OffsetDateTime.parse(object.getString("created_at"));
        OffsetDateTime updatedAt = OffsetDateTime.parse(object.getString("updated_at"));
        long guildId = object.getLong("source_guild_id");
        DataObject guildObject = object.getObject("serialized_source_guild");
        String guildName = guildObject.getString("name");
        String guildDescription = guildObject.getString("description", null);
        String guildIconId = guildObject.getString("icon_hash", null);
        Guild.VerificationLevel guildVerificationLevel = Guild.VerificationLevel.fromKey(guildObject.getInt("verification_level", -1));
        Guild.NotificationLevel notificationLevel = Guild.NotificationLevel.fromKey(guildObject.getInt("default_message_notifications", 0));
        Guild.ExplicitContentLevel explicitContentLevel = Guild.ExplicitContentLevel.fromKey(guildObject.getInt("explicit_content_filter", 0));
        DiscordLocale locale = DiscordLocale.from(guildObject.getString("preferred_locale", "en-US"));
        Guild.Timeout afkTimeout = Guild.Timeout.fromKey(guildObject.getInt("afk_timeout", 0));
        DataArray roleArray = guildObject.getArray("roles");
        DataArray channelsArray = guildObject.getArray("channels");
        long afkChannelId = guildObject.getLong("afk_channel_id", -1L);
        long systemChannelId = guildObject.getLong("system_channel_id", -1L);
        ArrayList<TemplateRole> roles = new ArrayList<TemplateRole>();
        for (int i = 0; i < roleArray.length(); ++i) {
            DataObject obj = roleArray.getObject(i);
            long roleId = obj.getLong("id");
            String roleName = obj.getString("name");
            int roleColor = obj.getInt("color");
            boolean hoisted = obj.getBoolean("hoist");
            boolean mentionable = obj.getBoolean("mentionable");
            long rawPermissions = obj.getLong("permissions");
            roles.add(new TemplateRole(roleId, roleName, roleColor == 0 ? 0x1FFFFFFF : roleColor, hoisted, mentionable, rawPermissions));
        }
        ArrayList<TemplateChannel> channels = new ArrayList<TemplateChannel>();
        for (int i = 0; i < channelsArray.length(); ++i) {
            DataObject obj = channelsArray.getObject(i);
            long channelId = obj.getLong("id");
            int type = obj.getInt("type");
            ChannelType channelType = ChannelType.fromId(type);
            String channelName = obj.getString("name");
            String topic = obj.getString("topic", null);
            int rawPosition = obj.getInt("position");
            long parentId = obj.getLong("parent_id", -1L);
            boolean nsfw = obj.getBoolean("nsfw");
            int slowmode = obj.getInt("rate_limit_per_user");
            int bitrate = obj.getInt("bitrate");
            int userLimit = obj.getInt("user_limit");
            ArrayList<TemplateChannel.PermissionOverride> permissionOverrides = new ArrayList<TemplateChannel.PermissionOverride>();
            DataArray overrides = obj.getArray("permission_overwrites");
            for (int j = 0; j < overrides.length(); ++j) {
                DataObject overrideObj = overrides.getObject(j);
                long overrideId = overrideObj.getLong("id");
                long allow = overrideObj.getLong("allow");
                long deny = overrideObj.getLong("deny");
                permissionOverrides.add(new TemplateChannel.PermissionOverride(overrideId, allow, deny));
            }
            channels.add(new TemplateChannel(channelId, channelType, channelName, topic, rawPosition, parentId, type == 5, permissionOverrides, nsfw, slowmode, bitrate, userLimit));
        }
        TemplateChannel afkChannel = channels.stream().filter(templateChannel -> templateChannel.getIdLong() == afkChannelId).findFirst().orElse(null);
        TemplateChannel systemChannel = channels.stream().filter(templateChannel -> templateChannel.getIdLong() == systemChannelId).findFirst().orElse(null);
        TemplateGuild guild = new TemplateGuild(guildId, guildName, guildDescription, guildIconId, guildVerificationLevel, notificationLevel, explicitContentLevel, locale, afkTimeout, afkChannel, systemChannel, roles, channels);
        boolean synced = !object.getBoolean("is_dirty", false);
        return new Template(this.getJDA(), code, name, description, uses, creator, createdAt, updatedAt, guild, synced);
    }

    public ApplicationInfo createApplicationInfo(DataObject object) {
        String description = object.getString("description");
        String termsOfServiceUrl = object.getString("terms_of_service_url", null);
        String privacyPolicyUrl = object.getString("privacy_policy_url", null);
        boolean doesBotRequireCodeGrant = object.getBoolean("bot_require_code_grant");
        String iconId = object.getString("icon", null);
        long id = object.getUnsignedLong("id");
        long flags = object.getUnsignedLong("flags", 0L);
        String name = object.getString("name");
        boolean isBotPublic = object.getBoolean("bot_public");
        UserImpl owner = this.createUser(object.getObject("owner"));
        ApplicationTeam team = !object.isNull("team") ? this.createApplicationTeam(object.getObject("team")) : null;
        String customAuthUrl = object.getString("custom_install_url", null);
        List<String> tags = object.optArray("tags").orElseGet(DataArray::empty).stream(DataArray::getString).collect(Collectors.toList());
        List<String> redirectUris = object.optArray("redirect_uris").orElseGet(DataArray::empty).stream(DataArray::getString).collect(Collectors.toList());
        String interactionsEndpointUrl = object.getString("interactions_endpoint_url", null);
        String roleConnectionsVerificationUrl = object.getString("role_connections_verification_url", null);
        Optional<DataObject> installParams = object.optObject("install_params");
        long defaultAuthUrlPerms = installParams.map(o -> o.getLong("permissions")).orElse(0L);
        List<String> defaultAuthUrlScopes = installParams.map(obj -> obj.getArray("scopes").stream(DataArray::getString).collect(Collectors.toList())).orElse(Collections.emptyList());
        return new ApplicationInfoImpl(this.getJDA(), description, doesBotRequireCodeGrant, iconId, id, flags, isBotPublic, name, termsOfServiceUrl, privacyPolicyUrl, owner, team, tags, redirectUris, interactionsEndpointUrl, roleConnectionsVerificationUrl, customAuthUrl, defaultAuthUrlPerms, defaultAuthUrlScopes);
    }

    public ApplicationTeam createApplicationTeam(DataObject object) {
        String iconId = object.getString("icon", null);
        long id = object.getUnsignedLong("id");
        long ownerId = object.getUnsignedLong("owner_user_id", 0L);
        List<TeamMember> members = this.map(object, "members", o -> {
            DataObject userJson = o.getObject("user");
            TeamMember.MembershipState state = TeamMember.MembershipState.fromKey(o.getInt("membership_state"));
            UserImpl user = this.createUser(userJson);
            TeamMember.RoleType roleType = user.getIdLong() == ownerId ? TeamMember.RoleType.OWNER : TeamMember.RoleType.fromKey(o.getString("role", ""));
            return new TeamMemberImpl(user, state, roleType, id);
        });
        return new ApplicationTeamImpl(iconId, members, id, ownerId);
    }

    public AuditLogEntry createAuditLogEntry(GuildImpl guild, DataObject entryJson, DataObject userJson, DataObject webhookJson) {
        Set<AuditLogChange> changesList;
        long targetId = entryJson.getLong("target_id", 0L);
        long userId = entryJson.getLong("user_id", 0L);
        long id = entryJson.getLong("id");
        int typeKey = entryJson.getInt("action_type");
        DataArray changes = entryJson.isNull("changes") ? null : entryJson.getArray("changes");
        DataObject options = entryJson.isNull("options") ? null : entryJson.getObject("options");
        String reason = entryJson.getString("reason", null);
        UserImpl user = userJson == null ? null : this.createUser(userJson);
        WebhookImpl webhook = webhookJson == null ? null : this.createWebhook(webhookJson);
        ActionType type = ActionType.from(typeKey);
        if (changes != null) {
            changesList = new HashSet(changes.length());
            for (int i = 0; i < changes.length(); ++i) {
                DataObject object = changes.getObject(i);
                AuditLogChange change = this.createAuditLogChange(object);
                changesList.add(change);
            }
        } else {
            changesList = Collections.emptySet();
        }
        CaseInsensitiveMap<String, AuditLogChange> changeMap = new CaseInsensitiveMap<String, AuditLogChange>(this.changeToMap(changesList));
        CaseInsensitiveMap<String, Object> optionMap = options != null ? new CaseInsensitiveMap<String, Object>(options.toMap()) : null;
        return new AuditLogEntry(type, typeKey, id, userId, targetId, guild, user, webhook, reason, changeMap, optionMap);
    }

    public AuditLogChange createAuditLogChange(DataObject change) {
        String key = change.getString("key");
        Object oldValue = change.isNull("old_value") ? null : change.get("old_value");
        Object newValue = change.isNull("new_value") ? null : change.get("new_value");
        return new AuditLogChange(oldValue, newValue, key);
    }

    public Entitlement createEntitlement(DataObject object) {
        return new EntitlementImpl(this.getJDA(), object.getUnsignedLong("id"), object.getUnsignedLong("sku_id"), object.getUnsignedLong("application_id"), object.getUnsignedLong("user_id", 0L), object.getUnsignedLong("guild_id", 0L), Entitlement.EntitlementType.fromKey(object.getInt("type")), object.getBoolean("deleted"), object.getOffsetDateTime("starts_at", null), object.getOffsetDateTime("ends_at", null), object.getBoolean("consumed", false));
    }

    private Map<String, AuditLogChange> changeToMap(Set<AuditLogChange> changesList) {
        return changesList.stream().collect(Collectors.toMap(AuditLogChange::getKey, UnaryOperator.identity()));
    }

    private <T> List<T> map(DataObject jsonObject, String key, Function<DataObject, T> convert) {
        return this.map(jsonObject, key, convert, ignored -> true);
    }

    private <T> List<T> map(DataObject jsonObject, String key, Function<DataObject, T> convert, Predicate<DataObject> filter) {
        if (jsonObject.isNull(key)) {
            return Collections.emptyList();
        }
        DataArray arr = jsonObject.getArray(key);
        ArrayList<T> mappedObjects = new ArrayList<T>(arr.length());
        for (int i = 0; i < arr.length(); ++i) {
            DataObject obj = arr.getObject(i);
            try {
                T result;
                if (!filter.test(obj) || (result = convert.apply(obj)) == null) continue;
                mappedObjects.add(result);
                continue;
            }
            catch (Exception e) {
                LOG.error("Failed to parse element in {} with content {}", new Object[]{key, obj, e});
            }
        }
        return mappedObjects;
    }

    static {
        HashSet<String> tmp = new HashSet<String>();
        tmp.add("application_id");
        tmp.add("assets");
        tmp.add("details");
        tmp.add("flags");
        tmp.add("party");
        tmp.add("session_id");
        tmp.add("sync_id");
        richGameFields = Collections.unmodifiableSet(tmp);
    }
}

