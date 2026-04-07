/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModRule;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod.GenericAutoModRuleEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class AutoModRuleCreateEvent
extends GenericAutoModRuleEvent {
    public AutoModRuleCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull AutoModRule rule) {
        super(api, responseNumber, rule);
    }
}

