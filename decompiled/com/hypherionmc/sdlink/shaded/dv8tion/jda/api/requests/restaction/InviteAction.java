/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface InviteAction
extends AuditableRestAction<Invite> {
    @Override
    @Nonnull
    @CheckReturnValue
    public InviteAction setCheck(@Nullable BooleanSupplier var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteAction timeout(long var1, @Nonnull TimeUnit var3);

    @Override
    @Nonnull
    @CheckReturnValue
    public InviteAction deadline(long var1);

    @Nonnull
    @CheckReturnValue
    public InviteAction setMaxAge(@Nullable Integer var1);

    @Nonnull
    @CheckReturnValue
    public InviteAction setMaxAge(@Nullable Long var1, @Nonnull TimeUnit var2);

    @Nonnull
    @CheckReturnValue
    public InviteAction setMaxUses(@Nullable Integer var1);

    @Nonnull
    @CheckReturnValue
    public InviteAction setTemporary(@Nullable Boolean var1);

    @Nonnull
    @CheckReturnValue
    public InviteAction setUnique(@Nullable Boolean var1);

    @Nonnull
    @CheckReturnValue
    public InviteAction setTargetApplication(long var1);

    @Nonnull
    @CheckReturnValue
    default public InviteAction setTargetApplication(@Nonnull String applicationId) {
        return this.setTargetApplication(MiscUtil.parseSnowflake(applicationId));
    }

    @Nonnull
    @CheckReturnValue
    public InviteAction setTargetStream(long var1);

    @Nonnull
    @CheckReturnValue
    default public InviteAction setTargetStream(@Nonnull String userId) {
        return this.setTargetStream(MiscUtil.parseSnowflake(userId));
    }

    @Nonnull
    @CheckReturnValue
    default public InviteAction setTargetStream(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.setTargetStream(user.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    default public InviteAction setTargetStream(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return this.setTargetStream(member.getIdLong());
    }
}

