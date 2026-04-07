/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.mentions.AbstractMentions;
import com.hypherionmc.sdlink.shaded.gnu.trove.iterator.TLongIterator;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.hash.TLongHashSet;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class MessageMentionsImpl
extends AbstractMentions {
    private final TLongObjectMap<DataObject> userMentionMap;
    private final TLongSet roleMentionMap;

    public MessageMentionsImpl(JDAImpl jda, GuildImpl guild, String content, boolean mentionsEveryone, DataArray userMentions, DataArray roleMentions) {
        super(content, jda, guild, mentionsEveryone);
        this.userMentionMap = new TLongObjectHashMap<DataObject>(userMentions.length());
        this.roleMentionMap = new TLongHashSet(roleMentions.stream(DataArray::getUnsignedLong).collect(Collectors.toList()));
        userMentions.stream(DataArray::getObject).forEach(obj -> {
            if (obj.isNull("member")) {
                this.userMentionMap.put(obj.getUnsignedLong("id"), obj.put("is_member", false));
                return;
            }
            DataObject member = obj.getObject("member");
            obj.remove("member");
            member.put("user", obj).put("is_member", true);
            this.userMentionMap.put(obj.getUnsignedLong("id"), member);
        });
        this.getMembers();
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
        EntityBuilder entityBuilder = this.jda.getEntityBuilder();
        TLongHashSet unseen = new TLongHashSet(this.userMentionMap.keySet());
        List members = this.processMentions(Message.MentionType.USER, false, matcher -> {
            if (unseen.remove(Long.parseUnsignedLong(matcher.group(1)))) {
                return this.matchMember((Matcher)matcher);
            }
            return null;
        }, Collectors.toCollection(ArrayList::new));
        TLongIterator iter = unseen.iterator();
        while (iter.hasNext()) {
            DataObject mention = this.userMentionMap.get(iter.next());
            if (!mention.getBoolean("is_member")) continue;
            members.add(0, entityBuilder.createMember(this.guild, mention));
        }
        members.stream().map(MemberImpl.class::cast).forEach(entityBuilder::updateMemberCache);
        this.mentionedMembers = Collections.unmodifiableList(members);
        return this.mentionedMembers;
    }

    @Override
    @Nonnull
    public synchronized List<User> getUsers() {
        if (this.mentionedUsers != null) {
            return this.mentionedUsers;
        }
        EntityBuilder entityBuilder = this.jda.getEntityBuilder();
        TLongHashSet unseen = new TLongHashSet(this.userMentionMap.keySet());
        List users = this.processMentions(Message.MentionType.USER, false, matcher -> {
            if (unseen.remove(Long.parseUnsignedLong(matcher.group(1)))) {
                return this.matchUser((Matcher)matcher);
            }
            return null;
        }, Collectors.toCollection(ArrayList::new));
        TLongIterator iter = unseen.iterator();
        while (iter.hasNext()) {
            DataObject mention = this.userMentionMap.get(iter.next());
            if (mention.getBoolean("is_member")) {
                users.add(0, entityBuilder.createUser(mention.getObject("user")));
                continue;
            }
            users.add(0, entityBuilder.createUser(mention));
        }
        this.mentionedUsers = Collections.unmodifiableList(users);
        return this.mentionedUsers;
    }

    @Override
    protected User matchUser(Matcher matcher) {
        long userId = MiscUtil.parseSnowflake(matcher.group(1));
        DataObject mention = this.userMentionMap.get(userId);
        if (mention == null) {
            return null;
        }
        if (!mention.getBoolean("is_member")) {
            return this.jda.getEntityBuilder().createUser(mention);
        }
        Member member = this.matchMember(matcher);
        return member == null ? null : member.getUser();
    }

    @Override
    protected Member matchMember(Matcher matcher) {
        long id = Long.parseUnsignedLong(matcher.group(1));
        DataObject member = this.userMentionMap.get(id);
        return member != null && member.getBoolean("is_member") ? this.jda.getEntityBuilder().createMember(this.guild, member) : null;
    }

    @Override
    protected GuildChannel matchChannel(Matcher matcher) {
        long channelId = MiscUtil.parseSnowflake(matcher.group(1));
        return this.getJDA().getGuildChannelById(channelId);
    }

    @Override
    protected Role matchRole(Matcher matcher) {
        long roleId = MiscUtil.parseSnowflake(matcher.group(1));
        if (!this.roleMentionMap.contains(roleId)) {
            return null;
        }
        if (this.guild != null) {
            return this.guild.getRoleById(roleId);
        }
        return this.getJDA().getRoleById(roleId);
    }

    @Override
    protected boolean isUserMentioned(IMentionable mentionable) {
        return this.userMentionMap.containsKey(mentionable.getIdLong());
    }
}

