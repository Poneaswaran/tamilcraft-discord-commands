/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;

public interface ICategorizableChannelMixin<T extends ICategorizableChannelMixin<T>>
extends ICategorizableChannel,
GuildChannelMixin<T>,
IPermissionContainerMixin<T> {
    @Override
    default public boolean isSynced() {
        IPermissionContainerMixin parent = (IPermissionContainerMixin)((Object)this.getParentCategory());
        if (parent == null) {
            return true;
        }
        TLongObjectMap<PermissionOverride> parentOverrides = parent.getPermissionOverrideMap();
        TLongObjectMap<PermissionOverride> overrides = this.getPermissionOverrideMap();
        if (parentOverrides.size() != overrides.size()) {
            return false;
        }
        for (PermissionOverride override : parentOverrides.valueCollection()) {
            PermissionOverride ourOverride = overrides.get(override.getIdLong());
            if (ourOverride == null) {
                return false;
            }
            if (ourOverride.getAllowedRaw() == override.getAllowedRaw() && ourOverride.getDeniedRaw() == override.getDeniedRaw()) continue;
            return false;
        }
        return true;
    }

    public T setParentCategory(long var1);
}

