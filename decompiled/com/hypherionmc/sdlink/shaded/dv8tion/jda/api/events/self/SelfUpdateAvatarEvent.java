/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.GenericSelfUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class SelfUpdateAvatarEvent
extends GenericSelfUpdateEvent<String> {
    public static final String IDENTIFIER = "avatar";
    private static final String AVATAR_URL = "https://cdn.discordapp.com/avatars/%s/%s%s";

    public SelfUpdateAvatarEvent(@Nonnull JDA api, long responseNumber, @Nullable String oldAvatarId) {
        super(api, responseNumber, oldAvatarId, api.getSelfUser().getAvatarId(), IDENTIFIER);
    }

    @Nullable
    public String getOldAvatarId() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getOldAvatarUrl() {
        return this.previous == null ? null : String.format(AVATAR_URL, this.getSelfUser().getId(), this.previous, ((String)this.previous).startsWith("a_") ? ".gif" : ".png");
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
        return this.next == null ? null : String.format(AVATAR_URL, this.getSelfUser().getId(), this.next, ((String)this.next).startsWith("a_") ? ".gif" : ".png");
    }

    @Nullable
    public ImageProxy getNewAvatar() {
        String newAvatarUrl = this.getNewAvatarUrl();
        return newAvatarUrl == null ? null : new ImageProxy(newAvatarUrl);
    }
}

