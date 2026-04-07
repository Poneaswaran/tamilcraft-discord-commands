/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.CollectionUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.IteratorUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EnumerationIterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

public class EnumerationUtils {
    private EnumerationUtils() {
    }

    public static <T> T get(Enumeration<T> e, int index) {
        int i = index;
        CollectionUtils.checkIndexBounds(i);
        while (e.hasMoreElements()) {
            if (--i == -1) {
                return e.nextElement();
            }
            e.nextElement();
        }
        throw new IndexOutOfBoundsException("Entry does not exist: " + i);
    }

    public static <E> List<E> toList(Enumeration<? extends E> enumeration) {
        return IteratorUtils.toList(new EnumerationIterator<E>(enumeration));
    }

    public static List<String> toList(StringTokenizer stringTokenizer) {
        ArrayList<String> result = new ArrayList<String>(stringTokenizer.countTokens());
        while (stringTokenizer.hasMoreTokens()) {
            result.add(stringTokenizer.nextToken());
        }
        return result;
    }
}

