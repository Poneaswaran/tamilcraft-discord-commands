/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.ModalMapping;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface ModalInteraction
extends IReplyCallback,
IMessageEditCallback {
    @Nonnull
    public String getModalId();

    @Nonnull
    public @Unmodifiable List<ModalMapping> getValues();

    @Nullable
    default public ModalMapping getValue(@Nonnull String id) {
        Checks.notNull(id, "ID");
        return this.getValues().stream().filter(mapping -> mapping.getId().equals(id)).findFirst().orElse(null);
    }

    @Nullable
    public Message getMessage();

    @Override
    @Nonnull
    public MessageChannelUnion getChannel();

    @Override
    @Nonnull
    default public GuildMessageChannelUnion getGuildChannel() {
        return (GuildMessageChannelUnion)IReplyCallback.super.getGuildChannel();
    }
}

