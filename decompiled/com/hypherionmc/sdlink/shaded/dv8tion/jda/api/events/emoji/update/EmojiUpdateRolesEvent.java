/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update.GenericEmojiUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public class EmojiUpdateRolesEvent
extends GenericEmojiUpdateEvent<List<Role>> {
    public static final String IDENTIFIER = "roles";

    public EmojiUpdateRolesEvent(@Nonnull JDA api, long responseNumber, @Nonnull RichCustomEmoji emoji, @Nonnull List<Role> oldRoles) {
        super(api, responseNumber, emoji, oldRoles, emoji.getRoles(), IDENTIFIER);
    }

    @Nonnull
    public List<Role> getOldRoles() {
        return this.getOldValue();
    }

    @Nonnull
    public List<Role> getNewRoles() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public List<Role> getOldValue() {
        return (List)super.getOldValue();
    }

    @Override
    @Nonnull
    public List<Role> getNewValue() {
        return (List)super.getNewValue();
    }
}

