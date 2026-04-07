/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.GenericSessionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.SessionState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.GuildSetupController;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ReadyEvent
extends GenericSessionEvent {
    private final int availableGuilds = (int)this.getJDA().getGuildCache().size();
    private final int unavailableGuilds;

    public ReadyEvent(@Nonnull JDA api) {
        super(api, SessionState.READY);
        GuildSetupController setupController = ((JDAImpl)this.getJDA()).getGuildSetupController();
        this.unavailableGuilds = setupController.getSetupNodes(GuildSetupController.Status.UNAVAILABLE).size() + setupController.getUnavailableGuilds().size();
    }

    public int getGuildAvailableCount() {
        return this.availableGuilds;
    }

    public int getGuildUnavailableCount() {
        return this.unavailableGuilds;
    }

    public int getGuildTotalCount() {
        return this.getGuildAvailableCount() + this.getGuildUnavailableCount();
    }
}

