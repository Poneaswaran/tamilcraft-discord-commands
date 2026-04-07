/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class Event
implements GenericEvent {
    protected final JDA api;
    protected final long responseNumber;
    protected final DataObject rawData;

    public Event(@Nonnull JDA api, long responseNumber) {
        this.api = api;
        this.responseNumber = responseNumber;
        this.rawData = api instanceof JDAImpl && ((JDAImpl)api).isEventPassthrough() ? SocketHandler.CURRENT_EVENT.get() : null;
    }

    public Event(@Nonnull JDA api) {
        this(api, api.getResponseTotal());
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    public long getResponseNumber() {
        return this.responseNumber;
    }

    @Override
    @Nullable
    public DataObject getRawData() {
        if (this.api instanceof JDAImpl && !((JDAImpl)this.api).isEventPassthrough()) {
            throw new IllegalStateException("Event passthrough is not enabled, see JDABuilder#setEventPassthrough(boolean)");
        }
        return this.rawData;
    }

    public String toString() {
        if (this instanceof UpdateEvent) {
            UpdateEvent event = (UpdateEvent)((Object)this);
            return new EntityString(this).setType(event.getPropertyIdentifier()).addMetadata(null, event.getOldValue() + " -> " + event.getNewValue()).toString();
        }
        return new EntityString(this).toString();
    }
}

