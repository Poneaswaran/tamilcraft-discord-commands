/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.forum.update.GenericForumTagUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class ForumTagUpdateEmojiEvent
extends GenericForumTagUpdateEvent<EmojiUnion> {
    public static final String IDENTIFIER = "emoji";

    public ForumTagUpdateEmojiEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPostContainer channel, @Nonnull ForumTag tag, @Nullable EmojiUnion previous) {
        super(api, responseNumber, channel, tag, previous, tag.getEmoji(), IDENTIFIER);
    }

    @Nullable
    public EmojiUnion getOldEmoji() {
        return (EmojiUnion)this.getOldValue();
    }

    @Nullable
    public EmojiUnion getNewEmoji() {
        return (EmojiUnion)this.getNewValue();
    }
}

