/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update.GenericForumTagUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ForumTagUpdateNameEvent
extends GenericForumTagUpdateEvent<String> {
    public static final String IDENTIFIER = "name";

    public ForumTagUpdateNameEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPostContainer channel, @Nonnull ForumTag tag, @Nonnull String previous) {
        super(api, responseNumber, channel, tag, previous, tag.getName(), IDENTIFIER);
    }

    @Nonnull
    public String getOldName() {
        return this.getOldValue();
    }

    @Nonnull
    public String getNewName() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public String getOldValue() {
        return (String)super.getOldValue();
    }

    @Override
    @Nonnull
    public String getNewValue() {
        return (String)super.getNewValue();
    }
}

