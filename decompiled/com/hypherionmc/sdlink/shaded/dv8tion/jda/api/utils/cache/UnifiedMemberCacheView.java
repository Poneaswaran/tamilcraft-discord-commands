/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface UnifiedMemberCacheView
extends CacheView<Member> {
    @Nonnull
    public List<Member> getElementsById(long var1);

    @Nonnull
    default public List<Member> getElementsById(@Nonnull String id) {
        return this.getElementsById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    public @Unmodifiable List<Member> getElementsByUsername(@Nonnull String var1, boolean var2);

    @Nonnull
    default public @Unmodifiable List<Member> getElementsByUsername(@Nonnull String name) {
        return this.getElementsByUsername(name, false);
    }

    @Nonnull
    public @Unmodifiable List<Member> getElementsByNickname(@Nullable String var1, boolean var2);

    @Nonnull
    default public @Unmodifiable List<Member> getElementsByNickname(@Nullable String name) {
        return this.getElementsByNickname(name, false);
    }

    @Nonnull
    public @Unmodifiable List<Member> getElementsWithRoles(Role ... var1);

    @Nonnull
    public @Unmodifiable List<Member> getElementsWithRoles(@Nonnull Collection<Role> var1);
}

