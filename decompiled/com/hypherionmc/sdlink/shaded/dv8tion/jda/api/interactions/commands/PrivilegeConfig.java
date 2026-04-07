/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.privileges.IntegrationPrivilege;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Unmodifiable;

public class PrivilegeConfig {
    private final Guild guild;
    private final Map<String, List<IntegrationPrivilege>> privileges;

    public PrivilegeConfig(@Nonnull Guild guild, @Nonnull Map<String, List<IntegrationPrivilege>> privileges) {
        this.guild = guild;
        this.privileges = Collections.unmodifiableMap(privileges);
    }

    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Nonnull
    public JDA getJDA() {
        return this.guild.getJDA();
    }

    @Nullable
    public @Unmodifiable List<IntegrationPrivilege> getApplicationPrivileges() {
        return this.getCommandPrivileges(this.getJDA().getSelfUser().getApplicationId());
    }

    @Nullable
    public @Unmodifiable List<IntegrationPrivilege> getCommandPrivileges(@Nonnull String id) {
        Checks.notNull(id, "Id");
        return this.privileges.get(id);
    }

    @Nullable
    public @Unmodifiable List<IntegrationPrivilege> getCommandPrivileges(@Nonnull Command command) {
        Checks.notNull(command, "Command");
        return this.privileges.get(command.getId());
    }

    @Nonnull
    public Map<String, List<IntegrationPrivilege>> getAsMap() {
        return this.privileges;
    }
}

