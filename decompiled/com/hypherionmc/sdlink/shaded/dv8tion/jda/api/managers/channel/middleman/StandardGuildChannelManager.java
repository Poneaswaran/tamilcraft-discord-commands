/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ICategorizableChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPermissionContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPositionableChannelManager;

public interface StandardGuildChannelManager<T extends StandardGuildChannel, M extends StandardGuildChannelManager<T, M>>
extends IPermissionContainerManager<T, M>,
IPositionableChannelManager<T, M>,
ICategorizableChannelManager<T, M> {
}

