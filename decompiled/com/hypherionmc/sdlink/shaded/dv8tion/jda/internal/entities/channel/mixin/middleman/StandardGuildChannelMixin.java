/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ICategorizableChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IInviteContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPositionableChannelMixin;

public interface StandardGuildChannelMixin<T extends StandardGuildChannelMixin<T>>
extends StandardGuildChannel,
ICategorizableChannelMixin<T>,
IPositionableChannelMixin<T>,
IPermissionContainerMixin<T>,
IInviteContainerMixin<T> {
}

