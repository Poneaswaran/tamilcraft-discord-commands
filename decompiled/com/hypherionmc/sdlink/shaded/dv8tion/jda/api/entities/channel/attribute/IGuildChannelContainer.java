/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface IGuildChannelContainer<C extends Channel> {
    @Nonnull
    public ChannelCacheView<C> getChannelCache();

    @Nullable
    default public <T extends C> T getChannelById(@Nonnull Class<T> type, @Nonnull String id) {
        return this.getChannelById(type, MiscUtil.parseSnowflake(id));
    }

    @Nullable
    default public <T extends C> T getChannelById(@Nonnull Class<T> type, long id) {
        Checks.notNull(type, "Class");
        return (T)((Channel)this.getChannelCache().ofType(type).getElementById(id));
    }

    @Nullable
    default public GuildChannel getGuildChannelById(@Nonnull String id) {
        return this.getGuildChannelById(MiscUtil.parseSnowflake(id));
    }

    @Nullable
    default public GuildChannel getGuildChannelById(long id) {
        Channel channel = (Channel)this.getChannelCache().getElementById(id);
        return channel instanceof GuildChannel ? (GuildChannel)channel : null;
    }

    @Nullable
    default public GuildChannel getGuildChannelById(@Nonnull ChannelType type, @Nonnull String id) {
        return this.getGuildChannelById(type, MiscUtil.parseSnowflake(id));
    }

    @Nullable
    default public GuildChannel getGuildChannelById(@Nonnull ChannelType type, long id) {
        C channel = this.getChannelCache().getElementById(type, id);
        return channel instanceof GuildChannel ? (GuildChannel)channel : null;
    }

    @Nonnull
    public SnowflakeCacheView<StageChannel> getStageChannelCache();

    @Nonnull
    default public @Unmodifiable List<StageChannel> getStageChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getStageChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public StageChannel getStageChannelById(@Nonnull String id) {
        return (StageChannel)this.getChannelCache().getElementById(ChannelType.STAGE, id);
    }

    @Nullable
    default public StageChannel getStageChannelById(long id) {
        return (StageChannel)this.getChannelCache().getElementById(ChannelType.STAGE, id);
    }

    @Nonnull
    default public @Unmodifiable List<StageChannel> getStageChannels() {
        return this.getStageChannelCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<ThreadChannel> getThreadChannelCache();

    @Nonnull
    default public @Unmodifiable List<ThreadChannel> getThreadChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getThreadChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public ThreadChannel getThreadChannelById(@Nonnull String id) {
        return (ThreadChannel)this.getChannelCache().getElementById(ChannelType.GUILD_PUBLIC_THREAD, id);
    }

    @Nullable
    default public ThreadChannel getThreadChannelById(long id) {
        return (ThreadChannel)this.getChannelCache().getElementById(ChannelType.GUILD_PUBLIC_THREAD, id);
    }

    @Nonnull
    default public @Unmodifiable List<ThreadChannel> getThreadChannels() {
        return this.getThreadChannelCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<Category> getCategoryCache();

    @Nonnull
    default public @Unmodifiable List<Category> getCategoriesByName(@Nonnull String name, boolean ignoreCase) {
        return this.getCategoryCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public Category getCategoryById(@Nonnull String id) {
        return (Category)this.getChannelCache().getElementById(ChannelType.CATEGORY, id);
    }

    @Nullable
    default public Category getCategoryById(long id) {
        return (Category)this.getChannelCache().getElementById(ChannelType.CATEGORY, id);
    }

    @Nonnull
    default public @Unmodifiable List<Category> getCategories() {
        return this.getCategoryCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<TextChannel> getTextChannelCache();

    @Nonnull
    default public @Unmodifiable List<TextChannel> getTextChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getTextChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public TextChannel getTextChannelById(@Nonnull String id) {
        return (TextChannel)this.getChannelCache().getElementById(ChannelType.TEXT, id);
    }

    @Nullable
    default public TextChannel getTextChannelById(long id) {
        return (TextChannel)this.getChannelCache().getElementById(ChannelType.TEXT, id);
    }

    @Nonnull
    default public @Unmodifiable List<TextChannel> getTextChannels() {
        return this.getTextChannelCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<NewsChannel> getNewsChannelCache();

    @Nonnull
    default public @Unmodifiable List<NewsChannel> getNewsChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getNewsChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public NewsChannel getNewsChannelById(@Nonnull String id) {
        return (NewsChannel)this.getChannelCache().getElementById(ChannelType.NEWS, id);
    }

    @Nullable
    default public NewsChannel getNewsChannelById(long id) {
        return (NewsChannel)this.getChannelCache().getElementById(ChannelType.NEWS, id);
    }

    @Nonnull
    default public @Unmodifiable List<NewsChannel> getNewsChannels() {
        return this.getNewsChannelCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<VoiceChannel> getVoiceChannelCache();

    @Nonnull
    default public @Unmodifiable List<VoiceChannel> getVoiceChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getVoiceChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public VoiceChannel getVoiceChannelById(@Nonnull String id) {
        return (VoiceChannel)this.getChannelCache().getElementById(ChannelType.VOICE, id);
    }

    @Nullable
    default public VoiceChannel getVoiceChannelById(long id) {
        return (VoiceChannel)this.getChannelCache().getElementById(ChannelType.VOICE, id);
    }

    @Nonnull
    default public @Unmodifiable List<VoiceChannel> getVoiceChannels() {
        return this.getVoiceChannelCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<ForumChannel> getForumChannelCache();

    @Nonnull
    default public @Unmodifiable List<ForumChannel> getForumChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getForumChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public ForumChannel getForumChannelById(@Nonnull String id) {
        return (ForumChannel)this.getChannelCache().getElementById(ChannelType.FORUM, id);
    }

    @Nullable
    default public ForumChannel getForumChannelById(long id) {
        return (ForumChannel)this.getChannelCache().getElementById(ChannelType.FORUM, id);
    }

    @Nonnull
    default public @Unmodifiable List<ForumChannel> getForumChannels() {
        return this.getForumChannelCache().asList();
    }

    @Nonnull
    public SnowflakeCacheView<MediaChannel> getMediaChannelCache();

    @Nonnull
    default public @Unmodifiable List<MediaChannel> getMediaChannelsByName(@Nonnull String name, boolean ignoreCase) {
        return this.getMediaChannelCache().getElementsByName(name, ignoreCase);
    }

    @Nullable
    default public MediaChannel getMediaChannelById(@Nonnull String id) {
        return (MediaChannel)this.getChannelCache().getElementById(ChannelType.MEDIA, id);
    }

    @Nullable
    default public MediaChannel getMediaChannelById(long id) {
        return (MediaChannel)this.getChannelCache().getElementById(ChannelType.MEDIA, id);
    }

    @Nonnull
    default public @Unmodifiable List<MediaChannel> getMediaChannels() {
        return this.getMediaChannelCache().asList();
    }
}

