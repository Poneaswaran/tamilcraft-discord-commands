/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.ThreadRevealedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class ThreadListSyncHandler
extends SocketHandler {
    public ThreadListSyncHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        if (this.api.getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        EntityBuilder entityBuilder = this.api.getEntityBuilder();
        DataArray threadsArrayJson = content.getArray("threads");
        for (int i = 0; i < threadsArrayJson.length(); ++i) {
            DataObject threadJson = threadsArrayJson.getObject(i);
            try {
                ThreadChannel thread = entityBuilder.createThreadChannel(threadJson, guildId);
                this.api.handleEvent(new ThreadRevealedEvent(this.api, this.responseNumber, thread));
                continue;
            }
            catch (IllegalArgumentException ex) {
                if (!"MISSING_CHANNEL".equals(ex.getMessage())) {
                    throw ex;
                }
                EntityBuilder.LOG.debug("Discarding thread on sync because of missing parent channel cache. JSON: {}", (Object)threadJson);
            }
        }
        return null;
    }
}

