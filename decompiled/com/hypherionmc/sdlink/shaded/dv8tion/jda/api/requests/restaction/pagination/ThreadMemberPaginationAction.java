/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ThreadMemberPaginationAction
extends PaginationAction<ThreadMember, ThreadMemberPaginationAction> {
    @Nonnull
    public ThreadChannel getThreadChannel();
}

