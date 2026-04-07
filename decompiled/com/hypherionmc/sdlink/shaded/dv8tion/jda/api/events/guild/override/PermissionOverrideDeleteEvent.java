/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class PermissionOverrideDeleteEvent
extends GenericPermissionOverrideEvent {
    public PermissionOverrideDeleteEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPermissionContainer channel, @Nonnull PermissionOverride override) {
        super(api, responseNumber, channel, override);
    }
}

