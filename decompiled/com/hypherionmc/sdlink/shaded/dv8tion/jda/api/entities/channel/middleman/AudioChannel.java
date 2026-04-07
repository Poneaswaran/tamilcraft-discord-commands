/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.AudioChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface AudioChannel
extends StandardGuildChannel {
    @Override
    @Nonnull
    @CheckReturnValue
    public AudioChannelManager<?, ?> getManager();

    public int getBitrate();

    public int getUserLimit();

    @Nonnull
    default public Region getRegion() {
        return this.getRegionRaw() == null ? Region.AUTOMATIC : Region.fromKey(this.getRegionRaw());
    }

    @Nullable
    public String getRegionRaw();
}

