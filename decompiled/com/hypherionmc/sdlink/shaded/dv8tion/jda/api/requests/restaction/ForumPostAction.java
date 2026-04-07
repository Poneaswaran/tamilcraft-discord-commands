/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumPost;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTagSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.FluentRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AbstractThreadCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;

public interface ForumPostAction
extends AbstractThreadCreateAction<ForumPost, ForumPostAction>,
MessageCreateRequest<ForumPostAction>,
FluentRestAction<ForumPost, ForumPostAction> {
    @Nonnull
    public IPostContainer getChannel();

    @Nonnull
    @CheckReturnValue
    public ForumPostAction setTags(@Nonnull Collection<? extends ForumTagSnowflake> var1);

    @Nonnull
    @CheckReturnValue
    default public ForumPostAction setTags(ForumTagSnowflake ... tags) {
        Checks.noneNull(tags, "Tags");
        return this.setTags(Arrays.asList(tags));
    }
}

