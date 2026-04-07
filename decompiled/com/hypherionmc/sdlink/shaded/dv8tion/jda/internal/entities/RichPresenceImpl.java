/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ActivityFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RichPresence;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ActivityImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;

public class RichPresenceImpl
extends ActivityImpl
implements RichPresence {
    protected final long applicationId;
    protected final RichPresence.Party party;
    protected final String details;
    protected final RichPresence.Image largeImage;
    protected final RichPresence.Image smallImage;
    protected final String sessionId;
    protected final String syncId;
    protected final int flags;

    protected RichPresenceImpl(Activity.ActivityType type, String name, String url, long applicationId, EmojiUnion emoji, RichPresence.Party party, String details, String state, Activity.Timestamps timestamps, String syncId, String sessionId, int flags, String largeImageKey, String largeImageText, String smallImageKey, String smallImageText) {
        super(name, state, url, type, timestamps, emoji);
        this.applicationId = applicationId;
        this.party = party;
        this.details = details;
        this.sessionId = sessionId;
        this.syncId = syncId;
        this.flags = flags;
        this.largeImage = largeImageKey != null ? new RichPresence.Image(applicationId, largeImageKey, largeImageText) : null;
        this.smallImage = smallImageKey != null ? new RichPresence.Image(applicationId, smallImageKey, smallImageText) : null;
    }

    @Override
    public boolean isRich() {
        return true;
    }

    @Override
    public RichPresence asRichPresence() {
        return this;
    }

    @Override
    public long getApplicationIdLong() {
        return this.applicationId;
    }

    @Override
    @Nonnull
    public String getApplicationId() {
        return Long.toUnsignedString(this.applicationId);
    }

    @Override
    @Nullable
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    @Nullable
    public String getSyncId() {
        return this.syncId;
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    @Override
    @Nonnull
    public EnumSet<ActivityFlag> getFlagSet() {
        return ActivityFlag.getFlags(this.getFlags());
    }

    @Override
    @Nullable
    public String getDetails() {
        return this.details;
    }

    @Override
    @Nullable
    public RichPresence.Party getParty() {
        return this.party;
    }

    @Override
    @Nullable
    public RichPresence.Image getLargeImage() {
        return this.largeImage;
    }

    @Override
    @Nullable
    public RichPresence.Image getSmallImage() {
        return this.smallImage;
    }

    @Override
    public String toString() {
        return new EntityString(this).setName(this.name).addMetadata("applicationId", this.applicationId).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.applicationId, this.state, this.details, this.party, this.sessionId, this.syncId, this.flags, this.timestamps, this.largeImage, this.smallImage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RichPresenceImpl)) {
            return false;
        }
        RichPresenceImpl p = (RichPresenceImpl)o;
        return this.applicationId == p.applicationId && Objects.equals(this.name, p.name) && Objects.equals(this.url, p.url) && Objects.equals((Object)this.type, (Object)p.type) && Objects.equals(this.state, p.state) && Objects.equals(this.details, p.details) && Objects.equals(this.party, p.party) && Objects.equals(this.sessionId, p.sessionId) && Objects.equals(this.syncId, p.syncId) && Objects.equals(this.flags, p.flags) && Objects.equals(this.timestamps, p.timestamps) && Objects.equals(this.largeImage, p.largeImage) && Objects.equals(this.smallImage, p.smallImage);
    }
}

