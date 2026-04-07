/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class UserUpdateAvatarEvent
extends GenericUserUpdateEvent<String> {
    public static final String IDENTIFIER = "avatar";

    public UserUpdateAvatarEvent(@Nonnull JDA api, long responseNumber, @Nonnull User user, @Nullable String oldAvatar) {
        super(api, responseNumber, user, oldAvatar, user.getAvatarId(), IDENTIFIER);
    }

    @Nullable
    public String getOldAvatarId() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getOldAvatarUrl() {
        return this.previous == null ? null : String.format("https://cdn.discordapp.com/avatars/%s/%s.%s", this.getUser().getId(), this.previous, ((String)this.previous).startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    public ImageProxy getOldAvatar() {
        String oldAvatarUrl = this.getOldAvatarUrl();
        return oldAvatarUrl == null ? null : new ImageProxy(oldAvatarUrl);
    }

    @Nullable
    public String getNewAvatarId() {
        return (String)this.getNewValue();
    }

    @Nullable
    public String getNewAvatarUrl() {
        return this.next == null ? null : String.format("https://cdn.discordapp.com/avatars/%s/%s.%s", this.getUser().getId(), this.next, ((String)this.next).startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    public ImageProxy getNewAvatar() {
        String newAvatarUrl = this.getNewAvatarUrl();
        return newAvatarUrl == null ? null : new ImageProxy(newAvatarUrl);
    }
}

