/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.RichSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildStickerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import org.jetbrains.annotations.Contract;

public interface GuildSticker
extends RichSticker {
    @Override
    @Nonnull
    default public Sticker.Type getType() {
        return Sticker.Type.GUILD;
    }

    public boolean isAvailable();

    public long getGuildIdLong();

    @Nonnull
    default public String getGuildId() {
        return Long.toUnsignedString(this.getGuildIdLong());
    }

    @Nullable
    public Guild getGuild();

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
    @Contract(value="->new")
    public GuildStickerManager getManager();
}

