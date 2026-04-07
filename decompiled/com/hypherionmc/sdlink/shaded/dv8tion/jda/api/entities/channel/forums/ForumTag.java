/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.BaseForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTagSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ForumTag
extends ForumTagSnowflake,
Comparable<ForumTag>,
BaseForumTag {
    public static final int MAX_NAME_LENGTH = 20;

    public int getPosition();

    @Override
    default public int compareTo(@Nonnull ForumTag o) {
        Checks.notNull(o, "ForumTag");
        return Integer.compare(this.getPosition(), o.getPosition());
    }

    @Override
    @Nonnull
    default public DataObject toData() {
        return BaseForumTag.super.toData().put("id", this.getId());
    }
}

