/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.GenericThreadEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ThreadHiddenEvent
extends GenericThreadEvent {
    public ThreadHiddenEvent(@Nonnull JDA api, long responseNumber, ThreadChannel thread) {
        super(api, responseNumber, thread);
    }
}

