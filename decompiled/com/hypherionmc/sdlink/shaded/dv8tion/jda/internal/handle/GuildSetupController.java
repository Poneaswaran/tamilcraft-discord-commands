/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildTimeoutEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.UnavailableGuildLeaveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.GuildSetupNode;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.MemberChunkManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.gnu.trove.iterator.TLongObjectIterator;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.hash.TLongHashSet;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;

public class GuildSetupController {
    protected static final Logger log = JDALogger.getLog(GuildSetupController.class);
    private static final long timeoutDuration = 75L;
    private static final int timeoutThreshold = 60;
    private final JDAImpl api;
    private final TLongObjectMap<GuildSetupNode> setupNodes = new TLongObjectHashMap<GuildSetupNode>();
    private final TLongSet chunkingGuilds = new TLongHashSet();
    private final TLongSet unavailableGuilds = new TLongHashSet();
    private int incompleteCount = 0;
    private Future<?> timeoutHandle;
    protected StatusListener listener = (id, oldStatus, newStatus) -> log.trace("[{}] Updated status {}->{}", new Object[]{id, oldStatus, newStatus});

    public GuildSetupController(JDAImpl api) {
        this.api = api;
    }

    JDAImpl getJDA() {
        return this.api;
    }

    void addGuildForChunking(long id, boolean join) {
        log.trace("Adding guild for chunking ID: {}", (Object)id);
        if (join || this.incompleteCount <= 0) {
            if (this.incompleteCount <= 0) {
                this.sendChunkRequest(id);
                return;
            }
            ++this.incompleteCount;
        }
        this.chunkingGuilds.add(id);
        this.tryChunking();
    }

    void remove(long id) {
        this.unavailableGuilds.remove(id);
        this.setupNodes.remove(id);
        this.chunkingGuilds.remove(id);
        this.checkReady();
    }

    public void ready(long id) {
        this.remove(id);
        --this.incompleteCount;
        this.checkReady();
    }

    private void checkReady() {
        WebSocketClient client = this.getJDA().getClient();
        if (this.incompleteCount < 1 && !client.isReady()) {
            if (this.timeoutHandle != null) {
                this.timeoutHandle.cancel(false);
            }
            this.timeoutHandle = null;
            client.ready();
        } else if (this.incompleteCount <= 60) {
            this.startTimeout();
        }
    }

    public boolean setIncompleteCount(int count) {
        this.incompleteCount = count;
        log.debug("Setting incomplete count to {}", (Object)this.incompleteCount);
        this.checkReady();
        return count != 0;
    }

    public void onReady(long id, DataObject obj) {
        log.trace("Adding id to setup cache {}", (Object)id);
        GuildSetupNode node = new GuildSetupNode(id, this, GuildSetupNode.Type.INIT);
        this.setupNodes.put(id, node);
        node.handleReady(obj);
        if (node.markedUnavailable) {
            --this.incompleteCount;
            this.tryChunking();
        }
    }

    public void onCreate(long id, DataObject obj) {
        GuildSetupNode node;
        boolean available = obj.isNull("unavailable") || !obj.getBoolean("unavailable");
        log.trace("Received guild create for id: {} available: {}", (Object)id, (Object)available);
        if (available && this.unavailableGuilds.contains(id) && !this.setupNodes.containsKey(id)) {
            this.unavailableGuilds.remove(id);
            this.setupNodes.put(id, new GuildSetupNode(id, this, GuildSetupNode.Type.AVAILABLE));
        }
        if ((node = this.setupNodes.get(id)) == null) {
            node = new GuildSetupNode(id, this, GuildSetupNode.Type.JOIN);
            this.setupNodes.put(id, node);
        } else if (node.markedUnavailable && available && this.incompleteCount > 0) {
            ++this.incompleteCount;
        }
        node.handleCreate(obj);
    }

    public boolean onDelete(long id, DataObject obj) {
        boolean available;
        boolean bl = available = obj.isNull("unavailable") || !obj.getBoolean("unavailable");
        if (this.isUnavailable(id) && available) {
            log.debug("Leaving unavailable guild with id {}", (Object)id);
            this.remove(id);
            this.api.getEventManager().handle(new UnavailableGuildLeaveEvent(this.api, this.api.getResponseTotal(), id));
            return true;
        }
        GuildSetupNode node = this.setupNodes.get(id);
        if (node == null) {
            return false;
        }
        log.debug("Received guild delete for id: {} available: {}", (Object)id, (Object)available);
        if (!available) {
            if (!node.markedUnavailable) {
                node.markedUnavailable = true;
                if (this.incompleteCount > 0) {
                    this.chunkingGuilds.remove(id);
                    --this.incompleteCount;
                }
            }
            node.reset();
        } else {
            node.cleanup();
            if (node.isJoin() && !node.requestedChunk) {
                this.remove(id);
            } else {
                this.ready(id);
            }
            this.api.getEventManager().handle(new UnavailableGuildLeaveEvent(this.api, this.api.getResponseTotal(), id));
        }
        log.debug("Updated incompleteCount to {}", (Object)this.incompleteCount);
        this.checkReady();
        return true;
    }

    public void onMemberChunk(long id, DataObject chunk) {
        DataArray members = chunk.getArray("members");
        int index = chunk.getInt("chunk_index");
        int count = chunk.getInt("chunk_count");
        log.debug("Received member chunk for guild id: {} size: {} index: {}/{}", new Object[]{id, members.length(), index, count});
        GuildSetupNode node = this.setupNodes.get(id);
        if (node != null) {
            node.handleMemberChunk(MemberChunkManager.isLastChunk(chunk), members);
        }
    }

    public boolean onAddMember(long id, DataObject member) {
        GuildSetupNode node = this.setupNodes.get(id);
        if (node == null) {
            return false;
        }
        log.debug("Received GUILD_MEMBER_ADD during setup, adding member to guild. GuildID: {}", (Object)id);
        node.handleAddMember(member);
        return true;
    }

    public boolean onRemoveMember(long id, DataObject member) {
        GuildSetupNode node = this.setupNodes.get(id);
        if (node == null) {
            return false;
        }
        log.debug("Received GUILD_MEMBER_REMOVE during setup, removing member from guild. GuildID: {}", (Object)id);
        node.handleRemoveMember(member);
        return true;
    }

    public void onSync(long id, DataObject obj) {
        GuildSetupNode node = this.setupNodes.get(id);
        if (node != null) {
            node.handleSync(obj);
        }
    }

    public boolean isLocked(long id) {
        return this.setupNodes.containsKey(id);
    }

    public boolean isUnavailable(long id) {
        return this.unavailableGuilds.contains(id);
    }

    public boolean isKnown(long id) {
        return this.isLocked(id) || this.isUnavailable(id);
    }

    public void cacheEvent(long guildId, DataObject event) {
        GuildSetupNode node = this.setupNodes.get(guildId);
        if (node != null) {
            node.cacheEvent(event);
        } else {
            log.warn("Attempted to cache event for a guild that is not locked. {}", (Object)event, (Object)new IllegalStateException());
        }
    }

    public void clearCache() {
        this.setupNodes.clear();
        this.chunkingGuilds.clear();
        this.unavailableGuilds.clear();
        this.incompleteCount = 0;
        this.close();
    }

    public void close() {
        if (this.timeoutHandle != null) {
            this.timeoutHandle.cancel(false);
        }
        this.timeoutHandle = null;
    }

    public boolean containsMember(long userId, @Nullable GuildSetupNode excludedNode) {
        TLongObjectIterator<GuildSetupNode> it = this.setupNodes.iterator();
        while (it.hasNext()) {
            it.advance();
            GuildSetupNode node = it.value();
            if (node == excludedNode || !node.containsMember(userId)) continue;
            return true;
        }
        return false;
    }

    public TLongSet getUnavailableGuilds() {
        return this.unavailableGuilds;
    }

    public Set<GuildSetupNode> getSetupNodes() {
        return new HashSet<GuildSetupNode>(this.setupNodes.valueCollection());
    }

    public Set<GuildSetupNode> getSetupNodes(Status status) {
        return this.getSetupNodes().stream().filter(node -> node.status == status).collect(Collectors.toSet());
    }

    public GuildSetupNode getSetupNodeById(long id) {
        return this.setupNodes.get(id);
    }

    public GuildSetupNode getSetupNodeById(String id) {
        return this.getSetupNodeById(MiscUtil.parseSnowflake(id));
    }

    public void setStatusListener(StatusListener listener) {
        this.listener = Objects.requireNonNull(listener);
    }

    int getIncompleteCount() {
        return this.incompleteCount;
    }

    int getChunkingCount() {
        return this.chunkingGuilds.size();
    }

    void sendChunkRequest(Object obj) {
        log.debug("Sending chunking requests for {} guilds", (Object)(obj instanceof DataArray ? ((DataArray)obj).length() : 1));
        this.getJDA().getClient().sendChunkRequest(DataObject.empty().put("guild_id", obj).put("query", "").put("limit", 0));
    }

    private void tryChunking() {
        this.chunkingGuilds.forEach(id -> {
            this.sendChunkRequest(id);
            return true;
        });
        this.chunkingGuilds.clear();
    }

    private void startTimeout() {
        if (this.timeoutHandle != null || this.incompleteCount < 1) {
            return;
        }
        log.debug("Starting {} second timeout for {} guilds", (Object)75L, (Object)this.incompleteCount);
        this.timeoutHandle = this.getJDA().getGatewayPool().schedule(this::onTimeout, 75L, TimeUnit.SECONDS);
    }

    public void onUnavailable(long id) {
        this.unavailableGuilds.add(id);
        log.debug("Guild with id {} is now marked unavailable. Total: {}", (Object)id, (Object)this.unavailableGuilds.size());
    }

    public void onTimeout() {
        if (this.incompleteCount < 1) {
            return;
        }
        log.warn("Automatically marking {} guilds as unavailable due to timeout!", (Object)this.incompleteCount);
        TLongObjectIterator<GuildSetupNode> iterator2 = this.setupNodes.iterator();
        while (iterator2.hasNext()) {
            iterator2.advance();
            GuildSetupNode node = iterator2.value();
            iterator2.remove();
            this.unavailableGuilds.add(node.getIdLong());
            this.getJDA().handleEvent(new GuildTimeoutEvent(this.getJDA(), node.getIdLong()));
        }
        this.incompleteCount = 0;
        this.checkReady();
    }

    @FunctionalInterface
    public static interface StatusListener {
        public void onStatusChange(long var1, Status var3, Status var4);
    }

    public static enum Status {
        INIT,
        CHUNKING,
        BUILDING,
        READY,
        UNAVAILABLE,
        REMOVED;

    }
}

