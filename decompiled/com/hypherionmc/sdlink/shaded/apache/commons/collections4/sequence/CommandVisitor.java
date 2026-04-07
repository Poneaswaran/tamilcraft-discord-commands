/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence;

public interface CommandVisitor<T> {
    public void visitInsertCommand(T var1);

    public void visitKeepCommand(T var1);

    public void visitDeleteCommand(T var1);
}

