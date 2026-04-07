/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RichPresence;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;

public class ActivityImpl
implements Activity {
    protected final String name;
    protected final String url;
    protected final String state;
    protected final Activity.ActivityType type;
    protected final Activity.Timestamps timestamps;
    protected final EmojiUnion emoji;

    protected ActivityImpl(String name) {
        this(name, null, Activity.ActivityType.PLAYING);
    }

    protected ActivityImpl(String name, String url) {
        this(name, url, Activity.ActivityType.STREAMING);
    }

    protected ActivityImpl(String name, String url, Activity.ActivityType type) {
        this(name, null, url, type, null, null);
    }

    protected ActivityImpl(String name, String state, String url, Activity.ActivityType type) {
        this(name, state, url, type, null, null);
    }

    protected ActivityImpl(String name, String state, String url, Activity.ActivityType type, Activity.Timestamps timestamps, EmojiUnion emoji) {
        this.name = name;
        this.state = state;
        this.url = url;
        this.type = type;
        this.timestamps = timestamps;
        this.emoji = emoji;
    }

    @Override
    public boolean isRich() {
        return false;
    }

    @Override
    public RichPresence asRichPresence() {
        return null;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nullable
    public String getState() {
        return this.state;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    @Nonnull
    public Activity.ActivityType getType() {
        return this.type;
    }

    @Override
    @Nullable
    public Activity.Timestamps getTimestamps() {
        return this.timestamps;
    }

    @Override
    @Nullable
    public EmojiUnion getEmoji() {
        return this.emoji;
    }

    @Override
    @Nonnull
    public Activity withState(@Nullable String state) {
        if (state != null) {
            if ((state = state.trim()).isEmpty()) {
                state = null;
            } else {
                Checks.notLonger(state, 128, "State");
            }
        }
        return new ActivityImpl(this.name, state, this.url, this.type);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ActivityImpl)) {
            return false;
        }
        ActivityImpl oGame = (ActivityImpl)o;
        return oGame.getType() == this.type && Objects.equals(this.name, oGame.getName()) && Objects.equals(this.state, oGame.state) && Objects.equals(this.url, oGame.getUrl()) && Objects.equals(this.timestamps, oGame.timestamps);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.state, this.type, this.url, this.timestamps});
    }

    public String toString() {
        EntityString entityString = new EntityString(this).setType(this.type).setName(this.name);
        if (this.url != null) {
            entityString.addMetadata("url", this.url);
        }
        return entityString.toString();
    }
}

