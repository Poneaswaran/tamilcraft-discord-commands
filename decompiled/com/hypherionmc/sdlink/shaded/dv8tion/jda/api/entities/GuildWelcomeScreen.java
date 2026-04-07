/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildWelcomeScreenManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildWelcomeScreenImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public interface GuildWelcomeScreen {
    public static final int MAX_DESCRIPTION_LENGTH = 140;
    public static final int MAX_WELCOME_CHANNELS = 5;

    @Nullable
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public GuildWelcomeScreenManager getManager();

    @Nullable
    public String getDescription();

    @Nonnull
    public List<Channel> getChannels();

    public static interface Channel
    extends ISnowflake,
    SerializableData {
        public static final int MAX_DESCRIPTION_LENGTH = 42;

        @Nonnull
        public static Channel of(@Nonnull StandardGuildChannel channel, @Nonnull String description) {
            return Channel.of(channel, description, null);
        }

        @Nonnull
        public static Channel of(@Nonnull StandardGuildChannel channel, @Nonnull String description, @Nullable Emoji emoji) {
            Checks.notNull(channel, "Channel");
            Checks.notBlank(description, "Description");
            Checks.notLonger(description, 42, "Description");
            return new GuildWelcomeScreenImpl.ChannelImpl(channel.getGuild(), channel.getIdLong(), description, (EmojiUnion)emoji);
        }

        @Nullable
        public Guild getGuild();

        @Override
        public long getIdLong();

        @Nullable
        public GuildChannel getChannel();

        @Nonnull
        public String getDescription();

        @Nullable
        public EmojiUnion getEmoji();
    }
}

