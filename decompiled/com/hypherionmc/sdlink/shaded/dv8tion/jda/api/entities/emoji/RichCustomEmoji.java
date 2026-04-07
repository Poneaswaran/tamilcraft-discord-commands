/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.CustomEmojiManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.PermissionUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface RichCustomEmoji
extends CustomEmoji {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    public @Unmodifiable List<Role> getRoles();

    public boolean isManaged();

    public boolean isAvailable();

    @Nonnull
    public JDA getJDA();

    @Nullable
    public User getOwner();

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<User> retrieveOwner();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public CustomEmojiManager getManager();

    default public boolean canInteract(Member issuer) {
        return PermissionUtil.canInteract(issuer, this);
    }

    default public boolean canInteract(User issuer, MessageChannel channel) {
        return PermissionUtil.canInteract(issuer, this, channel);
    }

    default public boolean canInteract(User issuer, MessageChannel channel, boolean botOverride) {
        return PermissionUtil.canInteract(issuer, this, channel, botOverride);
    }
}

