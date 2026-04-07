/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;

public abstract class SocketHandler {
    public static final ThreadLocal<DataObject> CURRENT_EVENT = new ThreadLocal();
    protected final JDAImpl api;
    protected long responseNumber;
    protected DataObject allContent;

    public SocketHandler(JDAImpl api) {
        this.api = api;
    }

    public final synchronized void handle(long responseTotal, DataObject o) {
        Long guildId;
        this.allContent = o;
        this.responseNumber = responseTotal;
        if (this.getJDA().isEventPassthrough()) {
            CURRENT_EVENT.set(o);
        }
        if ((guildId = this.handleInternally(o.getObject("d"))) != null) {
            this.getJDA().getGuildSetupController().cacheEvent(guildId, o);
        }
        this.allContent = null;
        if (this.getJDA().isEventPassthrough()) {
            CURRENT_EVENT.set(null);
        }
    }

    protected JDAImpl getJDA() {
        return this.api;
    }

    protected abstract Long handleInternally(DataObject var1);

    public static class NOPHandler
    extends SocketHandler {
        public NOPHandler(JDAImpl api) {
            super(api);
        }

        @Override
        protected Long handleInternally(DataObject content) {
            return null;
        }
    }
}

