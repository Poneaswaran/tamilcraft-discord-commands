/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Bag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.BagUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.HashBag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.SlashCommandReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SelectMenuMentions
implements Mentions {
    private final DataObject resolved;
    private final JDAImpl jda;
    private final GuildImpl guild;
    private final List<String> values;
    private List<User> cachedUsers;
    private List<Member> cachedMembers;
    private List<Role> cachedRoles;
    private List<GuildChannel> cachedChannels;

    public SelectMenuMentions(JDAImpl jda, GuildImpl guild, DataObject resolved, DataArray values) {
        this.jda = jda;
        this.guild = guild;
        this.resolved = resolved;
        this.values = values.stream(DataArray::getString).collect(Collectors.toList());
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.jda;
    }

    @Override
    public boolean mentionsEveryone() {
        return false;
    }

    @Override
    @Nonnull
    public List<User> getUsers() {
        if (this.cachedUsers != null) {
            return this.cachedUsers;
        }
        DataObject userMap = this.resolved.optObject("users").orElseGet(DataObject::empty);
        EntityBuilder builder = this.jda.getEntityBuilder();
        this.cachedUsers = this.values.stream().map(id -> userMap.optObject((String)id).orElse(null)).filter(Objects::nonNull).map(builder::createUser).collect(Helpers.toUnmodifiableList());
        return this.cachedUsers;
    }

    @Override
    @Nonnull
    public Bag<User> getUsersBag() {
        return new HashBag<User>(this.getUsers());
    }

    @Override
    @Nonnull
    public List<GuildChannel> getChannels() {
        if (this.cachedChannels != null) {
            return this.cachedChannels;
        }
        DataObject channelMap = this.resolved.optObject("channels").orElseGet(DataObject::empty);
        this.cachedChannels = this.values.stream().map(id -> channelMap.optObject((String)id).orElse(null)).filter(Objects::nonNull).map(json -> this.jda.getGuildChannelById(ChannelType.fromId(json.getInt("type", -1)), json.getUnsignedLong("id"))).filter(Objects::nonNull).collect(Helpers.toUnmodifiableList());
        return this.cachedChannels;
    }

    @Override
    @Nonnull
    public Bag<GuildChannel> getChannelsBag() {
        return new HashBag<GuildChannel>(this.getChannels());
    }

    @Override
    @Nonnull
    public <T extends GuildChannel> List<T> getChannels(@Nonnull Class<T> clazz) {
        return this.getChannels().stream().filter(clazz::isInstance).map(clazz::cast).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public <T extends GuildChannel> Bag<T> getChannelsBag(@Nonnull Class<T> clazz) {
        return new HashBag<T>(this.getChannels(clazz));
    }

    @Override
    @Nonnull
    public List<Role> getRoles() {
        if (this.cachedRoles != null) {
            return this.cachedRoles;
        }
        DataObject roleMap = this.resolved.optObject("roles").orElseGet(DataObject::empty);
        this.cachedRoles = this.values.stream().filter(roleMap::hasKey).map(this.jda::getRoleById).filter(Objects::nonNull).collect(Helpers.toUnmodifiableList());
        return this.cachedRoles;
    }

    @Override
    @Nonnull
    public Bag<Role> getRolesBag() {
        return new HashBag<Role>(this.getRoles());
    }

    @Override
    @Nonnull
    public List<CustomEmoji> getCustomEmojis() {
        return Collections.emptyList();
    }

    @Override
    @Nonnull
    public Bag<CustomEmoji> getCustomEmojisBag() {
        return BagUtils.emptyBag();
    }

    @Override
    @Nonnull
    public List<SlashCommandReference> getSlashCommands() {
        return Collections.emptyList();
    }

    @Override
    @Nonnull
    public Bag<SlashCommandReference> getSlashCommandsBag() {
        return BagUtils.emptyBag();
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        if (this.cachedMembers != null) {
            return this.cachedMembers;
        }
        DataObject memberMap = this.resolved.optObject("members").orElseGet(DataObject::empty);
        DataObject userMap = this.resolved.optObject("users").orElseGet(DataObject::empty);
        EntityBuilder builder = this.jda.getEntityBuilder();
        this.cachedMembers = this.values.stream().map(id -> memberMap.optObject((String)id).map(m -> m.put("id", id)).orElse(null)).filter(Objects::nonNull).map(json -> json.put("user", userMap.getObject(json.getString("id")))).map(json -> builder.createMember(this.guild, (DataObject)json)).filter(Objects::nonNull).filter(member -> {
            builder.updateMemberCache((MemberImpl)member);
            return true;
        }).collect(Helpers.toUnmodifiableList());
        return this.cachedMembers;
    }

    @Override
    @Nonnull
    public Bag<Member> getMembersBag() {
        return new HashBag<Member>(this.getMembers());
    }

    @Override
    @Nonnull
    public List<IMentionable> getMentions(Message.MentionType ... types) {
        if (types.length == 0) {
            return this.getMentions(Message.MentionType.values());
        }
        ArrayList<ISnowflake> mentions = new ArrayList<ISnowflake>();
        EnumSet<Message.MentionType[]> set = EnumSet.of(types[0], types);
        for (Message.MentionType mentionType : set) {
            switch (mentionType) {
                case USER: {
                    List<Member> members = this.getMembers();
                    List<User> users = this.getUsers();
                    mentions.addAll(members);
                    users.stream().filter(u -> members.stream().noneMatch(m -> m.getIdLong() == u.getIdLong())).forEach(mentions::add);
                    break;
                }
                case ROLE: {
                    mentions.addAll(this.getRoles());
                    break;
                }
                case CHANNEL: {
                    mentions.addAll(this.getChannels());
                }
            }
        }
        mentions.sort(Comparator.comparingInt(it -> this.values.indexOf(it.getId())));
        return Collections.unmodifiableList(mentions);
    }

    @Override
    public boolean isMentioned(@Nonnull IMentionable mentionable, Message.MentionType ... types) {
        Checks.notNull(types, "Mention Types");
        if (types.length == 0) {
            return this.isMentioned(mentionable, Message.MentionType.values());
        }
        String id = mentionable.getId();
        block5: for (Message.MentionType type : types) {
            switch (type) {
                case USER: {
                    boolean mentioned;
                    if (!(mentionable instanceof UserSnowflake) || !(mentioned = this.resolved.optObject("users").map(obj -> obj.hasKey(id)).orElse(false).booleanValue())) continue block5;
                    return true;
                }
                case ROLE: {
                    boolean mentioned;
                    if (!(mentionable instanceof Member ? (mentioned = ((Member)mentionable).getRoles().stream().anyMatch(role -> this.isMentioned((IMentionable)role, Message.MentionType.ROLE))) : (mentionable instanceof User ? (mentioned = this.getMembers().stream().filter(it -> it.getIdLong() == mentionable.getIdLong()).findFirst().map(member -> this.isMentioned((IMentionable)member, Message.MentionType.ROLE)).orElse(false).booleanValue()) : mentionable instanceof Role && (mentioned = this.resolved.optObject("roles").map(obj -> obj.hasKey(id)).orElse(false).booleanValue())))) continue block5;
                    return true;
                }
                case CHANNEL: {
                    if (!(mentionable instanceof GuildChannel) || !this.getChannels().contains(mentionable)) continue block5;
                    return true;
                }
            }
        }
        return false;
    }
}

