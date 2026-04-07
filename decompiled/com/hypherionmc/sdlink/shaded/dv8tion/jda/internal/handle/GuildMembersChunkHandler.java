/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;

public class GuildMembersChunkHandler
extends SocketHandler {
    public GuildMembersChunkHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        DataArray members = content.getArray("members");
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        if (guild != null) {
            if (this.api.getClient().getChunkManager().handleChunk(guildId, content)) {
                return null;
            }
            WebSocketClient.LOG.debug("Received member chunk for guild that is already in cache. GuildId: {} Count: {} Index: {}/{}", new Object[]{guildId, members.length(), content.getInt("chunk_index"), content.getInt("chunk_count")});
            EntityBuilder builder = this.getJDA().getEntityBuilder();
            TLongObjectMap presences = content.optArray("presences").map(it -> Helpers.convertToMap(o -> o.getObject("user").getUnsignedLong("id"), it)).orElseGet(TLongObjectHashMap::new);
            for (int i = 0; i < members.length(); ++i) {
                DataObject object = members.getObject(i);
                long userId = object.getObject("user").getUnsignedLong("id");
                DataObject presence = (DataObject)presences.get(userId);
                MemberImpl member = builder.createMember(guild, object, null, presence);
                builder.updateMemberCache(member);
            }
            return null;
        }
        this.getJDA().getGuildSetupController().onMemberChunk(guildId, content);
        return null;
    }
}

