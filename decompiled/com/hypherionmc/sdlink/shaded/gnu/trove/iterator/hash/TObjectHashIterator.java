/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.gnu.trove.iterator.hash;

import com.hypherionmc.sdlink.shaded.gnu.trove.impl.hash.THashIterator;
import com.hypherionmc.sdlink.shaded.gnu.trove.impl.hash.TObjectHash;

public class TObjectHashIterator<E>
extends THashIterator<E> {
    protected final TObjectHash _objectHash;

    public TObjectHashIterator(TObjectHash<E> hash) {
        super(hash);
        this._objectHash = hash;
    }

    @Override
    protected E objectAtIndex(int index) {
        Object obj = this._objectHash._set[index];
        if (obj == TObjectHash.FREE || obj == TObjectHash.REMOVED) {
            return null;
        }
        return (E)obj;
    }
}

