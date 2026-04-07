/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;

@FunctionalInterface
public interface CacheConsumer {
    public void execute(long var1, DataObject var3);
}

