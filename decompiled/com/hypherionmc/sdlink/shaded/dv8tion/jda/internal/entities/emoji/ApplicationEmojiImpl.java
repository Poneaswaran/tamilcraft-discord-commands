/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.ApplicationEmojiManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ApplicationEmojiManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class ApplicationEmojiImpl
implements ApplicationEmoji,
EmojiUnion {
    private final long id;
    private final JDAImpl api;
    private final User owner;
    boolean animated = false;
    private String name;

    public ApplicationEmojiImpl(long id, JDAImpl api, User owner) {
        this.id = id;
        this.api = api;
        this.owner = owner;
    }

    @Override
    @Nonnull
    public Emoji.Type getType() {
        return Emoji.Type.CUSTOM;
    }

    @Override
    @Nonnull
    public String getAsReactionCode() {
        return this.name + ":" + this.id;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return DataObject.empty().put("name", this.name).put("animated", this.animated).put("id", this.id);
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nullable
    public User getOwner() {
        return this.owner;
    }

    @Override
    @Nonnull
    public ApplicationEmojiManager getManager() {
        return new ApplicationEmojiManagerImpl(this);
    }

    @Override
    public boolean isAnimated() {
        return this.animated;
    }

    @Override
    @Nonnull
    public RestAction<Void> delete() {
        Route.CompiledRoute route = Route.Applications.DELETE_APPLICATION_EMOJI.compile(this.getJDA().getSelfUser().getApplicationId(), this.getId());
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    public ApplicationEmojiImpl setName(String name) {
        this.name = name;
        return this;
    }

    public ApplicationEmojiImpl setAnimated(boolean animated) {
        this.animated = animated;
        return this;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ApplicationEmojiImpl)) {
            return false;
        }
        ApplicationEmojiImpl other = (ApplicationEmojiImpl)obj;
        return this.id == other.getIdLong();
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }

    @Override
    @Nonnull
    public UnicodeEmoji asUnicode() {
        throw new IllegalStateException("Cannot convert ApplicationEmoji to UnicodeEmoji!");
    }

    @Override
    @Nonnull
    public CustomEmoji asCustom() {
        return this;
    }

    @Override
    @Nonnull
    public RichCustomEmoji asRich() {
        throw new IllegalStateException("Cannot convert ApplicationEmoji to RichCustomEmoji!");
    }

    @Override
    @Nonnull
    public ApplicationEmoji asApplication() {
        return this;
    }
}

