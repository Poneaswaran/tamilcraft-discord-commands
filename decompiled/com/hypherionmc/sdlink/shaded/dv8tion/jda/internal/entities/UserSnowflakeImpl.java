/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class UserSnowflakeImpl
implements UserSnowflake {
    protected final long id;

    public UserSnowflakeImpl(long id) {
        this.id = id;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public String getAsMention() {
        return "<@" + this.getId() + ">";
    }

    @Override
    @Nonnull
    public String getDefaultAvatarId() {
        return String.valueOf((this.id >> 22) % 6L);
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserSnowflakeImpl)) {
            return false;
        }
        return ((UserSnowflakeImpl)obj).getIdLong() == this.id;
    }

    public String toString() {
        return new EntityString(this).toString();
    }
}

