/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface BaseForumTag
extends SerializableData {
    @Nonnull
    public String getName();

    public boolean isModerated();

    @Nullable
    public EmojiUnion getEmoji();

    @Override
    @Nonnull
    default public DataObject toData() {
        DataObject json = DataObject.empty().put("name", this.getName()).put("moderated", this.isModerated());
        EmojiUnion emoji = this.getEmoji();
        if (emoji instanceof UnicodeEmoji) {
            json.put("emoji_name", emoji.getName());
        } else if (emoji instanceof CustomEmoji) {
            json.put("emoji_id", ((CustomEmoji)((Object)emoji)).getId());
        }
        return json;
    }
}

