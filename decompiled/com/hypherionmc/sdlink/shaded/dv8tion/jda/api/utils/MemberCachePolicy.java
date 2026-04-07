/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.Incubating;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.LRUMemberCachePolicy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

@FunctionalInterface
public interface MemberCachePolicy {
    public static final MemberCachePolicy NONE = member -> false;
    public static final MemberCachePolicy ALL = member -> true;
    public static final MemberCachePolicy OWNER = Member::isOwner;
    public static final MemberCachePolicy ONLINE = member -> member.getOnlineStatus() != OnlineStatus.OFFLINE && member.getOnlineStatus() != OnlineStatus.UNKNOWN;
    public static final MemberCachePolicy VOICE = member -> {
        GuildVoiceState voiceState = member.getVoiceState();
        return voiceState != null && voiceState.getChannel() != null;
    };
    public static final MemberCachePolicy BOOSTER = Member::isBoosting;
    @Incubating
    public static final MemberCachePolicy PENDING = Member::isPending;
    public static final MemberCachePolicy DEFAULT = VOICE.or(OWNER);

    public boolean cacheMember(@Nonnull Member var1);

    @Nonnull
    default public MemberCachePolicy or(@Nonnull MemberCachePolicy policy) {
        Checks.notNull(policy, "Policy");
        return member -> this.cacheMember(member) || policy.cacheMember(member);
    }

    @Nonnull
    default public MemberCachePolicy and(@Nonnull MemberCachePolicy policy) {
        return member -> this.cacheMember(member) && policy.cacheMember(member);
    }

    @Nonnull
    public static MemberCachePolicy any(@Nonnull MemberCachePolicy policy, MemberCachePolicy ... policies) {
        Checks.notNull(policy, "Policy");
        Checks.notNull(policies, "Policy");
        for (MemberCachePolicy p : policies) {
            policy = policy.or(p);
        }
        return policy;
    }

    @Nonnull
    public static MemberCachePolicy all(@Nonnull MemberCachePolicy policy, MemberCachePolicy ... policies) {
        Checks.notNull(policy, "Policy");
        Checks.notNull(policies, "Policy");
        for (MemberCachePolicy p : policies) {
            policy = policy.and(p);
        }
        return policy;
    }

    @Nonnull
    public static LRUMemberCachePolicy lru(int maxSize) {
        return new LRUMemberCachePolicy(maxSize);
    }
}

