/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.GenericSelfUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class SelfUpdateGlobalNameEvent
extends GenericSelfUpdateEvent<String> {
    public static final String IDENTIFIER = "global_name";

    public SelfUpdateGlobalNameEvent(JDA api, long responseNumber, String oldName) {
        super(api, responseNumber, oldName, api.getSelfUser().getGlobalName(), IDENTIFIER);
    }

    @Nullable
    public String getOldGlobalName() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getNewGlobalName() {
        return (String)this.getNewValue();
    }

    @Override
    public String toString() {
        return "SelfUpdateGlobalName(" + (String)this.getOldValue() + "->" + (String)this.getNewValue() + ')';
    }
}

