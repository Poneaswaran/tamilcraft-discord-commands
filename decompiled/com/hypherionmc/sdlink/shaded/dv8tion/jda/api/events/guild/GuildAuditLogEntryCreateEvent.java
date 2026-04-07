/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogEntry;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildAuditLogEntryCreateEvent
extends GenericGuildEvent {
    private final AuditLogEntry entry;

    public GuildAuditLogEntryCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull AuditLogEntry entry) {
        super(api, responseNumber, entry.getGuild());
        this.entry = entry;
    }

    @Nonnull
    public AuditLogEntry getEntry() {
        return this.entry;
    }
}

