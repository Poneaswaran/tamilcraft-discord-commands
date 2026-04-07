/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.ChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ChannelUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class AbstractChannelImpl<T extends AbstractChannelImpl<T>>
implements ChannelMixin<T> {
    protected final long id;
    protected final JDAImpl api;
    protected String name;

    public AbstractChannelImpl(long id, JDA api) {
        this.id = id;
        this.api = (JDAImpl)api;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public T setName(String name) {
        this.name = name;
        return (T)this;
    }

    @Override
    @Nonnull
    public PrivateChannel asPrivateChannel() {
        return ChannelUtil.safeChannelCast(this, PrivateChannel.class);
    }

    @Override
    @Nonnull
    public TextChannel asTextChannel() {
        return ChannelUtil.safeChannelCast(this, TextChannel.class);
    }

    @Override
    @Nonnull
    public NewsChannel asNewsChannel() {
        return ChannelUtil.safeChannelCast(this, NewsChannel.class);
    }

    @Override
    @Nonnull
    public VoiceChannel asVoiceChannel() {
        return ChannelUtil.safeChannelCast(this, VoiceChannel.class);
    }

    @Override
    @Nonnull
    public StageChannel asStageChannel() {
        return ChannelUtil.safeChannelCast(this, StageChannel.class);
    }

    @Override
    @Nonnull
    public ThreadChannel asThreadChannel() {
        return ChannelUtil.safeChannelCast(this, ThreadChannel.class);
    }

    @Override
    @Nonnull
    public Category asCategory() {
        return ChannelUtil.safeChannelCast(this, Category.class);
    }

    @Override
    @Nonnull
    public ForumChannel asForumChannel() {
        return ChannelUtil.safeChannelCast(this, ForumChannel.class);
    }

    @Override
    @Nonnull
    public MediaChannel asMediaChannel() {
        return ChannelUtil.safeChannelCast(this, MediaChannel.class);
    }

    @Override
    @Nonnull
    public MessageChannel asMessageChannel() {
        return ChannelUtil.safeChannelCast(this, MessageChannel.class);
    }

    @Override
    @Nonnull
    public AudioChannel asAudioChannel() {
        return ChannelUtil.safeChannelCast(this, AudioChannel.class);
    }

    @Override
    @Nonnull
    public IThreadContainer asThreadContainer() {
        return ChannelUtil.safeChannelCast(this, IThreadContainer.class);
    }

    @Override
    @Nonnull
    public GuildChannel asGuildChannel() {
        return ChannelUtil.safeChannelCast(this, GuildChannel.class);
    }

    @Override
    @Nonnull
    public GuildMessageChannel asGuildMessageChannel() {
        return ChannelUtil.safeChannelCast(this, GuildMessageChannel.class);
    }

    @Override
    @Nonnull
    public StandardGuildChannel asStandardGuildChannel() {
        return ChannelUtil.safeChannelCast(this, StandardGuildChannel.class);
    }

    @Override
    @Nonnull
    public StandardGuildMessageChannel asStandardGuildMessageChannel() {
        return ChannelUtil.safeChannelCast(this, StandardGuildMessageChannel.class);
    }

    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }
}

