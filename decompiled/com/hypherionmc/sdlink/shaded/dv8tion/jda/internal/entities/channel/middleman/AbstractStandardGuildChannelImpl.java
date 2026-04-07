/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.StandardGuildChannelMixin;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;

public abstract class AbstractStandardGuildChannelImpl<T extends AbstractStandardGuildChannelImpl<T>>
extends AbstractGuildChannelImpl<T>
implements StandardGuildChannelMixin<T> {
    protected final TLongObjectMap<PermissionOverride> overrides = MiscUtil.newLongMap();
    protected long parentCategoryId;
    protected int position;

    public AbstractStandardGuildChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    public long getParentCategoryIdLong() {
        return this.parentCategoryId;
    }

    @Override
    public int getPositionRaw() {
        return this.position;
    }

    @Override
    public TLongObjectMap<PermissionOverride> getPermissionOverrideMap() {
        return this.overrides;
    }

    @Override
    public T setParentCategory(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return (T)this;
    }

    @Override
    public T setPosition(int position) {
        this.onPositionChange();
        this.position = position;
        return (T)this;
    }

    protected final void onPositionChange() {
        this.guild.getChannelView().clearCachedLists();
    }
}

