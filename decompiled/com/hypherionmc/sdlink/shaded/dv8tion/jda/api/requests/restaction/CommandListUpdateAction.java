/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface CommandListUpdateAction
extends RestAction<List<Command>> {
    @Nonnull
    @CheckReturnValue
    public CommandListUpdateAction timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public CommandListUpdateAction deadline(long var1);

    @Nonnull
    @CheckReturnValue
    public CommandListUpdateAction setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public CommandListUpdateAction addCheck(@Nonnull BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public CommandListUpdateAction addCommands(@Nonnull Collection<? extends CommandData> var1);

    @Nonnull
    @CheckReturnValue
    default public CommandListUpdateAction addCommands(CommandData ... commands) {
        Checks.noneNull(commands, "Command");
        return this.addCommands(Arrays.asList(commands));
    }
}

