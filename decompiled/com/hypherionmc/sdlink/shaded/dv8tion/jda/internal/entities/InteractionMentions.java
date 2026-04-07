/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.mentions.AbstractMentions;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import java.util.regex.Matcher;

public class InteractionMentions
extends AbstractMentions {
    protected final TLongObjectMap<Object> resolved;

    public InteractionMentions(String content, TLongObjectMap<Object> resolved, JDAImpl jda, GuildImpl guild) {
        super(content, jda, guild, false);
        this.resolved = resolved;
    }

    @Override
    protected User matchUser(Matcher matcher) {
        long userId = MiscUtil.parseSnowflake(matcher.group(1));
        Object it = this.resolved.get(userId);
        return it instanceof User ? (User)it : (it instanceof Member ? ((Member)it).getUser() : null);
    }

    @Override
    protected Member matchMember(Matcher matcher) {
        long userId = MiscUtil.parseSnowflake(matcher.group(1));
        Object it = this.resolved.get(userId);
        return it instanceof Member ? (Member)it : null;
    }

    @Override
    protected GuildChannel matchChannel(Matcher matcher) {
        long channelId = MiscUtil.parseSnowflake(matcher.group(1));
        Object it = this.resolved.get(channelId);
        return it instanceof GuildChannel ? (GuildChannel)it : null;
    }

    @Override
    protected Role matchRole(Matcher matcher) {
        long roleId = MiscUtil.parseSnowflake(matcher.group(1));
        Object it = this.resolved.get(roleId);
        return it instanceof Role ? (Role)it : null;
    }

    @Override
    protected boolean isUserMentioned(IMentionable mentionable) {
        return this.resolved.containsKey(mentionable.getIdLong()) && (this.content.contains("<@!" + mentionable.getId() + ">") || this.content.contains("<@" + mentionable.getId() + ">"));
    }
}

