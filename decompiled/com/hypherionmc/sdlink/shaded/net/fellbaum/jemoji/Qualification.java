/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import java.util.Arrays;
import java.util.List;

public enum Qualification {
    COMPONENT("component"),
    FULLY_QUALIFIED("fully-qualified"),
    MINIMALLY_QUALIFIED("minimally-qualified"),
    UNQUALIFIED("unqualified");

    private static final List<Qualification> QUALIFICATION_LIST;
    private final String qualification;

    private Qualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualification() {
        return this.qualification;
    }

    public static Qualification fromString(String qualification) {
        for (Qualification q : QUALIFICATION_LIST) {
            if (!q.getQualification().equals(qualification)) continue;
            return q;
        }
        throw new IllegalArgumentException("Unknown qualification encountered");
    }

    static {
        QUALIFICATION_LIST = Arrays.asList(Qualification.values());
    }
}

