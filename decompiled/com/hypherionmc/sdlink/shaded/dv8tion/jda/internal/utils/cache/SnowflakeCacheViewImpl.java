/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.AbstractCacheView;
import java.util.function.Function;

public class SnowflakeCacheViewImpl<T extends ISnowflake>
extends AbstractCacheView<T>
implements SnowflakeCacheView<T> {
    public SnowflakeCacheViewImpl(Class<T> type, Function<T, String> nameMapper) {
        super(type, nameMapper);
    }

    @Override
    public T getElementById(long id) {
        if (this.elements.isEmpty()) {
            return null;
        }
        return (T)((ISnowflake)this.get(id));
    }
}

