/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import java.util.Collection;

public interface BoundedCollection<E>
extends Collection<E> {
    public boolean isFull();

    public int maxSize();
}

