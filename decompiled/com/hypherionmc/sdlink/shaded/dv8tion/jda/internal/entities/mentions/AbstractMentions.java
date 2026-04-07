/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.mentions;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Bag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.CollectionUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.HashBag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.ICommandReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.SlashCommandReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class AbstractMentions
implements Mentions {
    protected final String content;
    protected final JDAImpl jda;
    protected final GuildImpl guild;
    protected final boolean mentionsEveryone;
    protected List<User> mentionedUsers;
    protected List<Member> mentionedMembers;
    protected List<Role> mentionedRoles;
    protected List<GuildChannel> mentionedChannels;
    protected List<CustomEmoji> mentionedEmojis;
    protected List<SlashCommandReference> mentionedSlashCommands;

    public AbstractMentions(String content, JDAImpl jda, GuildImpl guild, boolean mentionsEveryone) {
        this.content = content;
        this.jda = jda;
        this.guild = guild;
        this.mentionsEveryone = mentionsEveryone;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.jda;
    }

    @Override
    public boolean mentionsEveryone() {
        return this.mentionsEveryone;
    }

    @Override
    @Nonnull
    public synchronized List<User> getUsers() {
        if (this.mentionedUsers != null) {
            return this.mentionedUsers;
        }
        this.mentionedUsers = this.processMentions(Message.MentionType.USER, true, this::matchUser, Helpers.toUnmodifiableList());
        return this.mentionedUsers;
    }

    @Override
    @Nonnull
    public Bag<User> getUsersBag() {
        Bag bag = this.processMentions(Message.MentionType.USER, false, this::matchUser, AbstractMentions.toBag());
        for (User user : this.getUsers()) {
            if (bag.contains(user)) continue;
            bag.add(user, 1);
        }
        return bag;
    }

    @Override
    @Nonnull
    public synchronized List<GuildChannel> getChannels() {
        if (this.mentionedChannels != null) {
            return this.mentionedChannels;
        }
        this.mentionedChannels = this.processMentions(Message.MentionType.CHANNEL, true, this::matchChannel, Helpers.toUnmodifiableList());
        return this.mentionedChannels;
    }

    @Override
    @Nonnull
    public Bag<GuildChannel> getChannelsBag() {
        return this.processMentions(Message.MentionType.CHANNEL, false, this::matchChannel, AbstractMentions.toBag());
    }

    @Override
    @Nonnull
    public <T extends GuildChannel> List<T> getChannels(@Nonnull Class<T> clazz) {
        Checks.notNull(clazz, "clazz");
        return this.getChannels().stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
    }

    @Override
    @Nonnull
    public <T extends GuildChannel> Bag<T> getChannelsBag(@Nonnull Class<T> clazz) {
        Checks.notNull(clazz, "clazz");
        Function<Matcher, GuildChannel> matchTypedChannel = matcher -> {
            GuildChannel channel = this.matchChannel((Matcher)matcher);
            return clazz.isInstance(channel) ? (GuildChannel)clazz.cast(channel) : null;
        };
        return this.processMentions(Message.MentionType.CHANNEL, false, matchTypedChannel, AbstractMentions.toBag());
    }

    @Override
    @Nonnull
    public synchronized List<Role> getRoles() {
        if (this.guild == null) {
            return Collections.emptyList();
        }
        if (this.mentionedRoles != null) {
            return this.mentionedRoles;
        }
        this.mentionedRoles = this.processMentions(Message.MentionType.ROLE, true, this::matchRole, Helpers.toUnmodifiableList());
        return this.mentionedRoles;
    }

    @Override
    @Nonnull
    public Bag<Role> getRolesBag() {
        if (this.guild == null) {
            return new HashBag<Role>();
        }
        return this.processMentions(Message.MentionType.ROLE, false, this::matchRole, AbstractMentions.toBag());
    }

    @Override
    @Nonnull
    public synchronized List<CustomEmoji> getCustomEmojis() {
        if (this.mentionedEmojis != null) {
            return this.mentionedEmojis;
        }
        this.mentionedEmojis = this.processMentions(Message.MentionType.EMOJI, true, this::matchEmoji, Helpers.toUnmodifiableList());
        return this.mentionedEmojis;
    }

    @Override
    @Nonnull
    public Bag<CustomEmoji> getCustomEmojisBag() {
        return this.processMentions(Message.MentionType.EMOJI, false, this::matchEmoji, AbstractMentions.toBag());
    }

    @Override
    @Nonnull
    public synchronized List<Member> getMembers() {
        if (this.guild == null) {
            return Collections.emptyList();
        }
        if (this.mentionedMembers != null) {
            return this.mentionedMembers;
        }
        this.mentionedMembers = this.processMentions(Message.MentionType.USER, true, this::matchMember, Helpers.toUnmodifiableList());
        return this.mentionedMembers;
    }

    @Override
    @Nonnull
    public Bag<Member> getMembersBag() {
        if (this.guild == null) {
            return new HashBag<Member>();
        }
        Bag bag = this.processMentions(Message.MentionType.USER, false, this::matchMember, AbstractMentions.toBag());
        for (Member member : this.getMembers()) {
            if (bag.contains(member)) continue;
            bag.add(member, 1);
        }
        return bag;
    }

    @Override
    @Nonnull
    public synchronized List<SlashCommandReference> getSlashCommands() {
        if (this.mentionedSlashCommands != null) {
            return this.mentionedSlashCommands;
        }
        this.mentionedSlashCommands = this.processMentions(Message.MentionType.SLASH_COMMAND, true, this::matchSlashCommand, Helpers.toUnmodifiableList());
        return this.mentionedSlashCommands;
    }

    @Override
    @Nonnull
    public Bag<SlashCommandReference> getSlashCommandsBag() {
        return this.processMentions(Message.MentionType.SLASH_COMMAND, false, this::matchSlashCommand, AbstractMentions.toBag());
    }

    @Override
    @Nonnull
    public List<IMentionable> getMentions(Message.MentionType ... types) {
        if (types == null || types.length == 0) {
            return this.getMentions(Message.MentionType.values());
        }
        ArrayList<Object> mentions = new ArrayList<Object>();
        for (Message.MentionType mentionType : EnumSet.of(types[0], types)) {
            switch (mentionType) {
                case CHANNEL: {
                    mentions.addAll(this.getChannels());
                    break;
                }
                case USER: {
                    TLongObjectHashMap<UserSnowflake> set = new TLongObjectHashMap<UserSnowflake>();
                    for (User u : this.getUsers()) {
                        set.put(u.getIdLong(), u);
                    }
                    for (Member m : this.getMembers()) {
                        set.put(m.getIdLong(), m);
                    }
                    mentions.addAll(set.valueCollection());
                    break;
                }
                case ROLE: {
                    mentions.addAll(this.getRoles());
                    break;
                }
                case EMOJI: {
                    mentions.addAll(this.getCustomEmojis());
                    break;
                }
                case SLASH_COMMAND: {
                    mentions.addAll(this.getSlashCommands());
                }
            }
        }
        mentions.sort(Comparator.comparingInt(it -> this.content.indexOf(it.getId())));
        return Collections.unmodifiableList(mentions);
    }

    @Override
    public boolean isMentioned(@Nonnull IMentionable mentionable, Message.MentionType ... types) {
        Checks.notNull(types, "Mention Types");
        if (types.length == 0) {
            return this.isMentioned(mentionable, Message.MentionType.values());
        }
        block9: for (Message.MentionType type : types) {
            switch (type) {
                case HERE: {
                    if (!this.isMass("@here") || !(mentionable instanceof UserSnowflake)) continue block9;
                    return true;
                }
                case EVERYONE: {
                    if (!this.isMass("@everyone") || !(mentionable instanceof UserSnowflake)) continue block9;
                    return true;
                }
                case USER: {
                    if (!this.isUserMentioned(mentionable)) continue block9;
                    return true;
                }
                case ROLE: {
                    if (!this.isRoleMentioned(mentionable)) continue block9;
                    return true;
                }
                case CHANNEL: {
                    if (!(mentionable instanceof GuildChannel) || !this.getChannels().contains(mentionable)) continue block9;
                    return true;
                }
                case EMOJI: {
                    if (!(mentionable instanceof CustomEmoji) || !this.getCustomEmojis().contains(mentionable)) continue block9;
                    return true;
                }
                case SLASH_COMMAND: {
                    if (!this.isSlashCommandMentioned(mentionable)) continue block9;
                    return true;
                }
            }
        }
        return false;
    }

    protected <T, A, C extends Collection<T>> C processMentions(Message.MentionType type, boolean distinct2, Function<Matcher, ? extends T> mapping, Collector<? super T, A, C> collector) {
        HashSet<T> unique;
        A accumulator = collector.supplier().get();
        Matcher matcher = type.getPattern().matcher(this.content);
        HashSet<T> hashSet = unique = distinct2 ? new HashSet<T>() : null;
        while (matcher.find()) {
            try {
                T elem = mapping.apply(matcher);
                if (elem == null || unique != null && !unique.add(elem)) continue;
                collector.accumulator().accept(accumulator, elem);
            }
            catch (NumberFormatException numberFormatException) {}
        }
        return (C)((Collection)collector.finisher().apply(accumulator));
    }

    protected static <T> Collector<T, ?, HashBag<T>> toBag() {
        return Collectors.toCollection(HashBag::new);
    }

    protected abstract User matchUser(Matcher var1);

    protected abstract Member matchMember(Matcher var1);

    protected abstract GuildChannel matchChannel(Matcher var1);

    protected abstract Role matchRole(Matcher var1);

    protected CustomEmoji matchEmoji(Matcher m) {
        long emojiId = MiscUtil.parseSnowflake(m.group(2));
        String name = m.group(1);
        boolean animated = m.group(0).startsWith("<a:");
        CustomEmoji emoji = this.getJDA().getEmojiById(emojiId);
        if (emoji == null) {
            emoji = Emoji.fromCustom(name, emojiId, animated);
        }
        return emoji;
    }

    protected SlashCommandReference matchSlashCommand(Matcher matcher) {
        return new SlashCommandReference(matcher.group(1), matcher.group(2), matcher.group(3), Long.parseLong(matcher.group(4)));
    }

    protected abstract boolean isUserMentioned(IMentionable var1);

    protected boolean isRoleMentioned(IMentionable mentionable) {
        if (mentionable instanceof Role) {
            return this.getRoles().contains(mentionable);
        }
        Member member = null;
        if (mentionable instanceof Member) {
            member = (Member)mentionable;
        } else if (this.guild != null && mentionable instanceof User) {
            member = this.guild.getMember((User)mentionable);
        }
        return member != null && CollectionUtils.containsAny(this.getRoles(), member.getRoles());
    }

    protected boolean isSlashCommandMentioned(IMentionable mentionable) {
        if (mentionable instanceof ICommandReference) {
            ICommandReference reference = (ICommandReference)mentionable;
            for (SlashCommandReference r : this.getSlashCommands()) {
                if (!r.getFullCommandName().equals(reference.getFullCommandName()) || r.getIdLong() != reference.getIdLong()) continue;
                return true;
            }
        }
        return false;
    }

    protected boolean isMass(String s) {
        return this.mentionsEveryone && this.content.contains(s);
    }
}

