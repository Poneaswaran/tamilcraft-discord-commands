/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.GenericPrivilegeUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.privileges.IntegrationPrivilege;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.privileges.PrivilegeTargetType;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public class ApplicationUpdatePrivilegesEvent
extends GenericPrivilegeUpdateEvent {
    public ApplicationUpdatePrivilegesEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, long applicationId, @Nonnull List<IntegrationPrivilege> privileges) {
        super(api, responseNumber, guild, applicationId, applicationId, privileges);
    }

    @Override
    @Nonnull
    public PrivilegeTargetType getTargetType() {
        return PrivilegeTargetType.INTEGRATION;
    }
}

