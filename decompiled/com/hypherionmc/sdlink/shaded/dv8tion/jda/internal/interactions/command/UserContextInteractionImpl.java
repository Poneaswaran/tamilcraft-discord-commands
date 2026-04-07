/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.UserContextInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.ContextInteractionImpl;

public class UserContextInteractionImpl
extends ContextInteractionImpl<User>
implements UserContextInteraction {
    private MemberImpl member;

    public UserContextInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
    }

    @Override
    protected User parse(DataObject interaction, DataObject resolved) {
        DataObject users = resolved.getObject("users");
        DataObject user = users.getObject(users.keys().iterator().next());
        resolved.optObject("members").filter(m -> !m.keys().isEmpty()).ifPresent(members -> {
            DataObject member = members.getObject(members.keys().iterator().next());
            this.member = this.api.getEntityBuilder().createMember((GuildImpl)this.guild, member);
            this.api.getEntityBuilder().updateMemberCache(this.member);
        });
        return this.api.getEntityBuilder().createUser(user);
    }

    @Override
    public Member getTargetMember() {
        return this.member;
    }
}

