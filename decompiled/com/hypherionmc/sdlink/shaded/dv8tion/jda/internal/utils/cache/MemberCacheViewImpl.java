/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.MemberCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MemberCacheViewImpl
extends SnowflakeCacheViewImpl<Member>
implements MemberCacheView {
    public MemberCacheViewImpl() {
        super(Member.class, Member::getEffectiveName);
    }

    @Override
    public Member getElementById(long id) {
        return (Member)this.get(id);
    }

    @Override
    @Nonnull
    public List<Member> getElementsByUsername(@Nonnull String name, boolean ignoreCase) {
        Checks.notEmpty(name, "Name");
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList members = new ArrayList();
        this.forEach(member -> {
            String nick = member.getUser().getName();
            if (this.equals(ignoreCase, nick, name)) {
                members.add(member);
            }
        });
        return Collections.unmodifiableList(members);
    }

    @Override
    @Nonnull
    public List<Member> getElementsByNickname(@Nullable String name, boolean ignoreCase) {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList members = new ArrayList();
        this.forEach(member -> {
            String nick = member.getNickname();
            if (nick == null) {
                if (name == null) {
                    members.add(member);
                }
                return;
            }
            if (this.equals(ignoreCase, nick, name)) {
                members.add(member);
            }
        });
        return Collections.unmodifiableList(members);
    }

    @Override
    @Nonnull
    public List<Member> getElementsWithRoles(Role ... roles) {
        Checks.notNull(roles, "Roles");
        return this.getElementsWithRoles(Arrays.asList(roles));
    }

    @Override
    @Nonnull
    public List<Member> getElementsWithRoles(@Nonnull Collection<Role> roles) {
        Checks.noneNull(roles, "Roles");
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        List rolesWithoutPublicRole = roles.stream().filter(role -> !role.isPublicRole()).collect(Collectors.toList());
        if (rolesWithoutPublicRole.isEmpty()) {
            return this.asList();
        }
        ArrayList<Member> members = new ArrayList<Member>();
        this.forEach(member -> {
            if (member.getRoles().containsAll(rolesWithoutPublicRole)) {
                members.add((Member)member);
            }
        });
        return members;
    }
}

