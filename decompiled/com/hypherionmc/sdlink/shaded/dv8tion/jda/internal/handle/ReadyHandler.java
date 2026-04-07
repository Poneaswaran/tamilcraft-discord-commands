/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;

public class ReadyHandler
extends SocketHandler {
    public ReadyHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        EntityBuilder builder = this.getJDA().getEntityBuilder();
        DataArray guilds = content.getArray("guilds");
        TLongObjectHashMap<DataObject> distinctGuilds = new TLongObjectHashMap<DataObject>();
        for (int i = 0; i < guilds.length(); ++i) {
            DataObject guild2 = guilds.getObject(i);
            long id2 = guild2.getUnsignedLong("id");
            DataObject previous = distinctGuilds.put(id2, guild2);
            if (previous == null) continue;
            WebSocketClient.LOG.warn("Found duplicate guild for id {} in ready payload", (Object)id2);
        }
        DataObject selfJson = content.getObject("user");
        selfJson.put("application_id", content.optObject("application").map(obj -> obj.getUnsignedLong("id")).orElse(selfJson.getUnsignedLong("id")));
        builder.createSelfUser(selfJson);
        if (this.getJDA().getGuildSetupController().setIncompleteCount(distinctGuilds.size())) {
            distinctGuilds.forEachEntry((id, guild) -> {
                this.getJDA().getGuildSetupController().onReady(id, (DataObject)guild);
                return true;
            });
        }
        this.handleReady(content);
        return null;
    }

    public void handleReady(DataObject content) {
        EntityBuilder builder = this.getJDA().getEntityBuilder();
        DataArray privateChannels = content.getArray("private_channels");
        block3: for (int i = 0; i < privateChannels.length(); ++i) {
            DataObject chan = privateChannels.getObject(i);
            ChannelType type = ChannelType.fromId(chan.getInt("type"));
            switch (type) {
                case PRIVATE: {
                    builder.createPrivateChannel(chan);
                    continue block3;
                }
                default: {
                    WebSocketClient.LOG.warn("Received a Channel in the private_channels array in READY of an unknown type! Type: {}", (Object)type);
                }
            }
        }
    }
}

