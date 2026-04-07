/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericThreadEvent
extends Event {
    protected final ThreadChannel thread;

    public GenericThreadEvent(@Nonnull JDA api, long responseNumber, ThreadChannel thread) {
        super(api, responseNumber);
        this.thread = thread;
    }

    @Nonnull
    public ThreadChannel getThread() {
        return this.thread;
    }

    @Nonnull
    public Guild getGuild() {
        return this.thread.getGuild();
    }
}

