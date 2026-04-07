/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.MissingAccessException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.StandardGuildChannelMixin;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;

public interface AudioChannelMixin<T extends AudioChannelMixin<T>>
extends AudioChannelUnion,
StandardGuildChannelMixin<T> {
    public TLongObjectMap<Member> getConnectedMembersMap();

    public T setBitrate(int var1);

    public T setUserLimit(int var1);

    public T setRegion(String var1);

    @Override
    default public void checkCanAccess() {
        if (!this.hasPermission(Permission.VIEW_CHANNEL)) {
            throw new MissingAccessException(this, Permission.VIEW_CHANNEL);
        }
        if (!this.hasPermission(Permission.VOICE_CONNECT)) {
            throw new MissingAccessException(this, Permission.VOICE_CONNECT);
        }
    }
}

