/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModRule;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericAutoModRuleEvent
extends Event {
    private final AutoModRule rule;

    public GenericAutoModRuleEvent(@Nonnull JDA api, long responseNumber, @Nonnull AutoModRule rule) {
        super(api, responseNumber);
        this.rule = rule;
    }

    @Nonnull
    public AutoModRule getRule() {
        return this.rule;
    }
}

