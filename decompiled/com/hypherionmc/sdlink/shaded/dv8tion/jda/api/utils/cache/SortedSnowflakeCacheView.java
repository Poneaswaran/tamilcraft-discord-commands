/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.NavigableSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface SortedSnowflakeCacheView<T extends Comparable<? super T> & ISnowflake>
extends SnowflakeCacheView<T> {
    @Override
    public void forEachUnordered(@Nonnull Consumer<? super T> var1);

    @Override
    @Nonnull
    public NavigableSet<T> asSet();

    @Nonnull
    public Stream<T> streamUnordered();

    @Nonnull
    public Stream<T> parallelStreamUnordered();
}

