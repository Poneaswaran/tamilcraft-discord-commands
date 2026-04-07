/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelField;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class ChannelUpdateDefaultReactionEvent
extends GenericChannelUpdateEvent<EmojiUnion> {
    public static final ChannelField FIELD = ChannelField.DEFAULT_REACTION_EMOJI;
    public static final String IDENTIFIER = FIELD.getFieldName();

    public ChannelUpdateDefaultReactionEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPostContainer channel, @Nullable EmojiUnion oldValue, @Nullable EmojiUnion newValue) {
        super(api, responseNumber, channel, FIELD, oldValue, newValue);
    }
}

