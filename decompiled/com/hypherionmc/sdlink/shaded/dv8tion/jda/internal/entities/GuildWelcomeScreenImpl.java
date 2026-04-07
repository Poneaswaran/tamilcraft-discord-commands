/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildWelcomeScreen;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildWelcomeScreenManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public class GuildWelcomeScreenImpl
implements GuildWelcomeScreen {
    private final Guild guild;
    private final String description;
    private final List<GuildWelcomeScreen.Channel> channels;

    public GuildWelcomeScreenImpl(@Nullable Guild guild, @Nullable String description, @Nonnull List<GuildWelcomeScreen.Channel> channels) {
        this.guild = guild;
        this.description = description;
        this.channels = channels;
    }

    @Override
    @Nullable
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    public GuildWelcomeScreenManager getManager() {
        if (this.guild == null) {
            throw new IllegalStateException("Cannot modify a guild welcome screen from an Invite");
        }
        return this.guild.modifyWelcomeScreen();
    }

    @Override
    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Override
    @Nonnull
    public List<GuildWelcomeScreen.Channel> getChannels() {
        return this.channels;
    }

    public static class ChannelImpl
    implements GuildWelcomeScreen.Channel {
        private final Guild guild;
        private final long id;
        private final String description;
        private final EmojiUnion emoji;

        public ChannelImpl(@Nullable Guild guild, long id, @Nonnull String description, @Nullable EmojiUnion emoji) {
            this.guild = guild;
            this.id = id;
            this.description = description;
            this.emoji = emoji;
        }

        @Override
        @Nullable
        public Guild getGuild() {
            return this.guild;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Override
        @Nullable
        public GuildChannel getChannel() {
            if (this.guild == null) {
                return null;
            }
            return this.guild.getGuildChannelById(this.id);
        }

        @Override
        @Nonnull
        public String getDescription() {
            return this.description;
        }

        @Override
        @Nullable
        public EmojiUnion getEmoji() {
            return this.emoji;
        }

        @Override
        @Nonnull
        public DataObject toData() {
            DataObject data = DataObject.empty();
            data.put("channel_id", this.id);
            data.put("description", this.description);
            if (this.emoji != null) {
                if (this.emoji.getType() == Emoji.Type.CUSTOM) {
                    data.put("emoji_id", ((CustomEmoji)((Object)this.emoji)).getId());
                }
                data.put("emoji_name", this.emoji.getName());
            }
            return data;
        }
    }
}

