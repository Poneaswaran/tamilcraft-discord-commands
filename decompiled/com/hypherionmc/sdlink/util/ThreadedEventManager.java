/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.InterfacedEventManager;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public final class ThreadedEventManager
extends InterfacedEventManager {
    @Override
    public void handle(@NotNull GenericEvent event) {
        try {
            if (BotController.INSTANCE.taskManager.isShutdown() || BotController.INSTANCE.taskManager.isTerminated()) {
                return;
            }
            CompletableFuture.runAsync(() -> super.handle(event), BotController.INSTANCE.taskManager);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

