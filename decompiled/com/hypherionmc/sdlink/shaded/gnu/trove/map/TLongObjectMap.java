/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.gnu.trove.map;

import com.hypherionmc.sdlink.shaded.gnu.trove.function.TObjectFunction;
import com.hypherionmc.sdlink.shaded.gnu.trove.iterator.TLongObjectIterator;
import com.hypherionmc.sdlink.shaded.gnu.trove.procedure.TLongObjectProcedure;
import com.hypherionmc.sdlink.shaded.gnu.trove.procedure.TLongProcedure;
import com.hypherionmc.sdlink.shaded.gnu.trove.procedure.TObjectProcedure;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import java.util.Collection;
import java.util.Map;

public interface TLongObjectMap<V> {
    public long getNoEntryKey();

    public int size();

    public boolean isEmpty();

    public boolean containsKey(long var1);

    public boolean containsValue(Object var1);

    public V get(long var1);

    public V put(long var1, V var3);

    public V putIfAbsent(long var1, V var3);

    public V remove(long var1);

    public void putAll(Map<? extends Long, ? extends V> var1);

    public void putAll(TLongObjectMap<? extends V> var1);

    public void clear();

    public TLongSet keySet();

    public long[] keys();

    public long[] keys(long[] var1);

    public Collection<V> valueCollection();

    public Object[] values();

    public V[] values(V[] var1);

    public TLongObjectIterator<V> iterator();

    public boolean forEachKey(TLongProcedure var1);

    public boolean forEachValue(TObjectProcedure<? super V> var1);

    public boolean forEachEntry(TLongObjectProcedure<? super V> var1);

    public void transformValues(TObjectFunction<V, V> var1);

    public boolean retainEntries(TLongObjectProcedure<? super V> var1);

    public boolean equals(Object var1);

    public int hashCode();
}

