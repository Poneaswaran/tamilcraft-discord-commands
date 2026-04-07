/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.UnknownNullability;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.GenericForumTagEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericForumTagUpdateEvent<T>
extends GenericForumTagEvent
implements UpdateEvent<ForumTag, T> {
    private final T previous;
    private final T next;
    private final String identifier;

    public GenericForumTagUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPostContainer channel, @Nonnull ForumTag tag, T previous, T next, @Nonnull String identifier) {
        super(api, responseNumber, channel, tag);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Override
    @Nonnull
    public ForumTag getEntity() {
        return this.getTag();
    }

    @Override
    @UnknownNullability
    public T getOldValue() {
        return this.previous;
    }

    @Override
    @UnknownNullability
    public T getNewValue() {
        return this.next;
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.identifier;
    }
}

