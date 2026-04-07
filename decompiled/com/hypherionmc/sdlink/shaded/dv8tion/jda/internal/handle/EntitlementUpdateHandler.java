/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.entitlement.EntitlementUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class EntitlementUpdateHandler
extends SocketHandler {
    public EntitlementUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        this.getJDA().handleEvent(new EntitlementUpdateEvent(this.getJDA(), this.responseNumber, this.getJDA().getEntityBuilder().createEntitlement(content)));
        return null;
    }
}

