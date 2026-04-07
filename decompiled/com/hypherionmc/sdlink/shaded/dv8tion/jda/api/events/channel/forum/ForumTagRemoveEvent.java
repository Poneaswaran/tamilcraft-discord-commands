/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.GenericForumTagEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ForumTagRemoveEvent
extends GenericForumTagEvent {
    public ForumTagRemoveEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPostContainer channel, @Nonnull ForumTag tag) {
        super(api, responseNumber, channel, tag);
    }
}

