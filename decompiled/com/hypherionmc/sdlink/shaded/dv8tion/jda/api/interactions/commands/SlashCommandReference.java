/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.ICommandReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;
import java.util.StringJoiner;

public class SlashCommandReference
implements ICommandReference {
    private final long id;
    private final String name;
    private final String subcommand;
    private final String subcommandGroup;

    public SlashCommandReference(@Nonnull String name, @Nullable String subcommandGroup, @Nullable String subcommand, long id) {
        this.name = name;
        this.subcommandGroup = subcommandGroup;
        this.subcommand = subcommand;
        this.id = id;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Nullable
    public String getSubcommandName() {
        return this.subcommand;
    }

    @Nullable
    public String getSubcommandGroup() {
        return this.subcommandGroup;
    }

    @Override
    @Nonnull
    public String getFullCommandName() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.name);
        if (this.subcommandGroup != null) {
            joiner.add(this.subcommandGroup);
        }
        if (this.subcommand != null) {
            joiner.add(this.subcommand);
        }
        return joiner.toString();
    }

    public String toString() {
        return new EntityString(this).setName(this.getFullCommandName()).toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SlashCommandReference)) {
            return false;
        }
        SlashCommandReference that = (SlashCommandReference)o;
        return this.id == that.id && this.name.equals(that.name) && Objects.equals(this.subcommand, that.subcommand) && Objects.equals(this.subcommandGroup, that.subcommandGroup);
    }

    public int hashCode() {
        return Objects.hash(this.id, this.name, this.subcommand, this.subcommandGroup);
    }
}

