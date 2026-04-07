/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.privileges;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Objects;

public class IntegrationPrivilege
implements ISnowflake {
    private final Guild guild;
    private final Type type;
    private final boolean enabled;
    private final long id;

    public IntegrationPrivilege(@Nonnull Guild guild, @Nonnull Type type, boolean enabled, long id) {
        this.guild = guild;
        this.type = type;
        this.enabled = enabled;
        this.id = id;
    }

    public boolean targetsEveryone() {
        return this.type == Type.ROLE && this.id == this.guild.getIdLong();
    }

    public boolean targetsAllChannels() {
        return this.type == Type.CHANNEL && this.id == this.guild.getIdLong() - 1L;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Nonnull
    public Type getType() {
        return this.type;
    }

    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isDisabled() {
        return !this.enabled;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.enabled);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntegrationPrivilege)) {
            return false;
        }
        IntegrationPrivilege other = (IntegrationPrivilege)obj;
        return other.id == this.id && other.enabled == this.enabled;
    }

    public String toString() {
        return new EntityString(this).setType(this.getType()).addMetadata("enabled", this.enabled).toString();
    }

    public static enum Type {
        UNKNOWN(-1),
        ROLE(1),
        USER(2),
        CHANNEL(3);

        private final int key;

        private Type(int key) {
            this.key = key;
        }

        @Nonnull
        public static Type fromKey(int key) {
            for (Type type : Type.values()) {
                if (type.key != key) continue;
                return type;
            }
            return UNKNOWN;
        }
    }
}

