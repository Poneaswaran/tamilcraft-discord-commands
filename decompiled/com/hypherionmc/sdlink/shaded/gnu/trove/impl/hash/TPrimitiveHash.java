/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.gnu.trove.impl.hash;

import com.hypherionmc.sdlink.shaded.gnu.trove.impl.hash.THash;

public abstract class TPrimitiveHash
extends THash {
    static final long serialVersionUID = 1L;
    public transient byte[] _states;
    public static final byte FREE = 0;
    public static final byte FULL = 1;
    public static final byte REMOVED = 2;

    public TPrimitiveHash() {
    }

    public TPrimitiveHash(int initialCapacity) {
        super(initialCapacity, 0.5f);
    }

    public TPrimitiveHash(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public int capacity() {
        return this._states.length;
    }

    @Override
    protected void removeAt(int index) {
        this._states[index] = 2;
        super.removeAt(index);
    }

    @Override
    protected int setUp(int initialCapacity) {
        int capacity = super.setUp(initialCapacity);
        this._states = new byte[capacity];
        return capacity;
    }
}

