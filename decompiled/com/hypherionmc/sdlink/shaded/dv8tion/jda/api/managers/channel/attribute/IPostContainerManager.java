/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.BaseForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPermissionContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IThreadContainerManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public interface IPostContainerManager<T extends IPostContainer, M extends IPostContainerManager<T, M>>
extends IThreadContainerManager<T, M>,
IPermissionContainerManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M setTagRequired(boolean var1);

    @Nonnull
    @CheckReturnValue
    public M setAvailableTags(@Nonnull List<? extends BaseForumTag> var1);

    @Nonnull
    @CheckReturnValue
    public M setDefaultReaction(@Nullable Emoji var1);

    @Nonnull
    @CheckReturnValue
    public M setDefaultSortOrder(@Nonnull IPostContainer.SortOrder var1);

    @Nonnull
    @CheckReturnValue
    public M setTopic(@Nullable String var1);
}

