/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence.CommandVisitor;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence.EditCommand;

public class KeepCommand<T>
extends EditCommand<T> {
    public KeepCommand(T object) {
        super(object);
    }

    @Override
    public void accept(CommandVisitor<T> visitor) {
        visitor.visitKeepCommand(this.getObject());
    }
}

