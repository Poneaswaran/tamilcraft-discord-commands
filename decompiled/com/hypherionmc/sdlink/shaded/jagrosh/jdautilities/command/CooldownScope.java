/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

public enum CooldownScope {
    USER("U:%d", ""),
    CHANNEL("C:%d", "in this channel"),
    USER_CHANNEL("U:%d|C:%d", "in this channel"),
    GUILD("G:%d", "in this server"),
    USER_GUILD("U:%d|G:%d", "in this server"),
    SHARD("S:%d", "on this shard"),
    USER_SHARD("U:%d|S:%d", "on this shard"),
    GLOBAL("Global", "globally");

    private final String format;
    final String errorSpecification;

    private CooldownScope(String format, String errorSpecification) {
        this.format = format;
        this.errorSpecification = errorSpecification;
    }

    String genKey(String name, long id) {
        return this.genKey(name, id, -1L);
    }

    String genKey(String name, long idOne, long idTwo) {
        if (this.equals((Object)GLOBAL)) {
            return name + "|" + this.format;
        }
        if (idTwo == -1L) {
            return name + "|" + String.format(this.format, idOne);
        }
        return name + "|" + String.format(this.format, idOne, idTwo);
    }
}

