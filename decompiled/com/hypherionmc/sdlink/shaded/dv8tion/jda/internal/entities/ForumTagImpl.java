/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ForumTagSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.CustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ForumTagImpl
extends ForumTagSnowflakeImpl
implements ForumTag {
    private boolean moderated;
    private String name;
    private int position;
    private Emoji emoji;

    public ForumTagImpl(long id) {
        super(id);
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isModerated() {
        return this.moderated;
    }

    @Override
    public EmojiUnion getEmoji() {
        return (EmojiUnion)this.emoji;
    }

    public ForumTagImpl setModerated(boolean moderated) {
        this.moderated = moderated;
        return this;
    }

    public ForumTagImpl setName(String name) {
        this.name = name;
        return this;
    }

    public ForumTagImpl setPosition(int position) {
        this.position = position;
        return this;
    }

    public ForumTagImpl setEmoji(DataObject json) {
        long id = json.getUnsignedLong("emoji_id", 0L);
        this.emoji = id != 0L ? new CustomEmojiImpl(json.getString("emoji_name", ""), id, false) : (!json.isNull("emoji_name") ? Emoji.fromUnicode(json.getString("emoji_name")) : null);
        return this;
    }

    @Override
    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }
}

