/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public final class UniquePredicate<T>
implements Predicate<T>,
Serializable {
    private static final long serialVersionUID = -3319417438027438040L;
    private final Set<T> iSet = new HashSet<T>();

    public static <T> Predicate<T> uniquePredicate() {
        return new UniquePredicate<T>();
    }

    @Override
    public boolean evaluate(T object) {
        return this.iSet.add(object);
    }
}

