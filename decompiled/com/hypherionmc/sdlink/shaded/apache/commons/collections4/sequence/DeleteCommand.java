/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence.CommandVisitor;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.sequence.EditCommand;

public class DeleteCommand<T>
extends EditCommand<T> {
    public DeleteCommand(T object) {
        super(object);
    }

    @Override
    public void accept(CommandVisitor<T> visitor) {
        visitor.visitDeleteCommand(this.getObject());
    }
}

