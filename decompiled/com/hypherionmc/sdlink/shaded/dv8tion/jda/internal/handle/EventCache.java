/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.CacheConsumer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.gnu.trove.iterator.TLongObjectIterator;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

public class EventCache {
    public static final Logger LOG = JDALogger.getLog(EventCache.class);
    public static final long TIMEOUT_AMOUNT = 100L;
    private final EnumMap<Type, TLongObjectMap<List<CacheNode>>> eventCache = new EnumMap(Type.class);

    public synchronized void timeout(long responseTotal) {
        if (this.eventCache.isEmpty()) {
            return;
        }
        AtomicInteger count = new AtomicInteger();
        this.eventCache.forEach((type, map) -> {
            if (map.isEmpty()) {
                return;
            }
            TLongObjectIterator iterator2 = map.iterator();
            while (iterator2.hasNext()) {
                iterator2.advance();
                long triggerId = iterator2.key();
                List cache = (List)iterator2.value();
                cache.removeIf(node -> {
                    boolean remove;
                    boolean bl = remove = responseTotal - ((CacheNode)node).responseTotal > 100L;
                    if (remove) {
                        count.incrementAndGet();
                        LOG.trace("Removing type {}/{} from event cache with payload {}", new Object[]{type, triggerId, ((CacheNode)node).event});
                    }
                    return remove;
                });
                if (!cache.isEmpty()) continue;
                iterator2.remove();
            }
        });
        int amount = count.get();
        if (amount > 0) {
            LOG.debug("Removed {} events from cache that were too old to be recycled", (Object)amount);
        }
    }

    public synchronized void cache(Type type, long triggerId, long responseTotal, DataObject event, CacheConsumer handler) {
        TLongObjectMap triggerCache = this.eventCache.computeIfAbsent(type, k -> new TLongObjectHashMap());
        LinkedList<CacheNode> items = (LinkedList<CacheNode>)triggerCache.get(triggerId);
        if (items == null) {
            items = new LinkedList<CacheNode>();
            triggerCache.put(triggerId, items);
        }
        items.add(new CacheNode(responseTotal, event, handler));
    }

    public synchronized void playbackCache(Type type, long triggerId) {
        TLongObjectMap<List<CacheNode>> typeCache = this.eventCache.get((Object)type);
        if (typeCache == null) {
            return;
        }
        List<CacheNode> items = typeCache.remove(triggerId);
        if (items != null && !items.isEmpty()) {
            LOG.debug("Replaying {} events from the EventCache for type {} with id: {}", new Object[]{items.size(), type, triggerId});
            for (CacheNode item : items) {
                item.execute();
            }
        }
    }

    public synchronized int size() {
        return (int)this.eventCache.values().stream().mapToLong(typeMap -> typeMap.valueCollection().stream().mapToLong(List::size).sum()).sum();
    }

    public synchronized void clear() {
        this.eventCache.clear();
    }

    public synchronized void clear(Type type, long id) {
        TLongObjectMap<List<CacheNode>> typeCache = this.eventCache.get((Object)type);
        if (typeCache == null) {
            return;
        }
        List<CacheNode> events = typeCache.remove(id);
        if (events != null) {
            LOG.debug("Clearing cache for type {} with ID {} (Size: {})", new Object[]{type, id, events.size()});
        }
    }

    public static enum Type {
        USER,
        MEMBER,
        GUILD,
        CHANNEL,
        ROLE,
        RELATIONSHIP,
        CALL,
        SCHEDULED_EVENT;

    }

    private class CacheNode {
        private final long responseTotal;
        private final DataObject event;
        private final CacheConsumer callback;

        public CacheNode(long responseTotal, DataObject event, CacheConsumer callback) {
            this.responseTotal = responseTotal;
            this.event = event;
            this.callback = callback;
        }

        void execute() {
            this.callback.execute(this.responseTotal, this.event);
        }
    }
}

