/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Bag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.SlashCommandReference;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface Mentions {
    @Nonnull
    public JDA getJDA();

    public boolean mentionsEveryone();

    @Nonnull
    public @Unmodifiable List<User> getUsers();

    @Nonnull
    public Bag<User> getUsersBag();

    @Nonnull
    public @Unmodifiable List<GuildChannel> getChannels();

    @Nonnull
    public Bag<GuildChannel> getChannelsBag();

    @Nonnull
    public <T extends GuildChannel> @Unmodifiable List<T> getChannels(@Nonnull Class<T> var1);

    @Nonnull
    public <T extends GuildChannel> Bag<T> getChannelsBag(@Nonnull Class<T> var1);

    @Nonnull
    public @Unmodifiable List<Role> getRoles();

    @Nonnull
    public Bag<Role> getRolesBag();

    @Nonnull
    public @Unmodifiable List<CustomEmoji> getCustomEmojis();

    @Nonnull
    public Bag<CustomEmoji> getCustomEmojisBag();

    @Nonnull
    public List<Member> getMembers();

    @Nonnull
    public Bag<Member> getMembersBag();

    @Nonnull
    public @Unmodifiable List<SlashCommandReference> getSlashCommands();

    @Nonnull
    public Bag<SlashCommandReference> getSlashCommandsBag();

    @Nonnull
    public @Unmodifiable List<IMentionable> getMentions(Message.MentionType ... var1);

    public boolean isMentioned(@Nonnull IMentionable var1, Message.MentionType ... var2);
}

