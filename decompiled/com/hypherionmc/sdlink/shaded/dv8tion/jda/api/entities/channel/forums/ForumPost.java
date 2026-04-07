/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ForumPost {
    private final Message message;
    private final ThreadChannel thread;

    public ForumPost(@Nonnull Message message, @Nonnull ThreadChannel thread) {
        this.message = message;
        this.thread = thread;
    }

    @Nonnull
    public Message getMessage() {
        return this.message;
    }

    @Nonnull
    public ThreadChannel getThreadChannel() {
        return this.thread;
    }
}

