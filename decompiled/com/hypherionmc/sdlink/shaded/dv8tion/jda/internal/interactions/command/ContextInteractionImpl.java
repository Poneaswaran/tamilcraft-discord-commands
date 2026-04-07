/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.ContextInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionPayloadImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionPayloadMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class ContextInteractionImpl<T>
extends CommandInteractionImpl
implements ContextInteraction<T>,
CommandInteractionPayloadMixin {
    private final T target;
    private final CommandInteractionPayloadImpl payload;

    public ContextInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
        this.payload = new CommandInteractionPayloadImpl(jda, data);
        this.target = this.parse(data, data.getObject("data").getObject("resolved"));
    }

    protected abstract T parse(DataObject var1, DataObject var2);

    @Override
    public CommandInteractionPayload getCommandPayload() {
        return this.payload;
    }

    @Override
    @Nonnull
    public T getTarget() {
        return this.target;
    }
}

