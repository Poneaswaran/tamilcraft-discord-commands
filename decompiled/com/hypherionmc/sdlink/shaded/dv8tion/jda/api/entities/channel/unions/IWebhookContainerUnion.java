/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IWebhookContainerUnion
extends IWebhookContainer {
    @Nonnull
    public TextChannel asTextChannel();

    @Nonnull
    public NewsChannel asNewsChannel();

    @Nonnull
    public ForumChannel asForumChannel();

    @Nonnull
    public MediaChannel asMediaChannel();

    @Nonnull
    public IThreadContainer asThreadContainer();

    @Nonnull
    public VoiceChannel asVoiceChannel();

    @Nonnull
    public StageChannel asStageChannel();

    @Nonnull
    public GuildMessageChannel asGuildMessageChannel();

    @Nonnull
    public StandardGuildChannel asStandardGuildChannel();

    @Nonnull
    public StandardGuildMessageChannel asStandardGuildMessageChannel();

    @Nonnull
    public AudioChannel asAudioChannel();
}

