/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.gnu.trove.iterator;

import com.hypherionmc.sdlink.shaded.gnu.trove.iterator.TAdvancingIterator;

public interface TIntLongIterator
extends TAdvancingIterator {
    public int key();

    public long value();

    public long setValue(long var1);
}

