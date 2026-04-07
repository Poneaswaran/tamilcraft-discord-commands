/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.menu.ButtonEmbedPaginator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class MessageUtil {
    public static ButtonEmbedPaginator.Builder defaultPaginator() {
        return ((ButtonEmbedPaginator.Builder)((ButtonEmbedPaginator.Builder)new ButtonEmbedPaginator.Builder().setTimeout(1L, TimeUnit.MINUTES)).setEventWaiter(BotController.INSTANCE.getEventWaiter())).waitOnSinglePage(false).setFinalAction(m -> m.editMessageComponents(new LayoutComponent[0]).queue());
    }

    public static <T> Stream<List<T>> listBatches(List<T> source2, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length = " + length);
        }
        int size = source2.size();
        if (size <= 0) {
            return Stream.empty();
        }
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(n -> source2.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }
}

