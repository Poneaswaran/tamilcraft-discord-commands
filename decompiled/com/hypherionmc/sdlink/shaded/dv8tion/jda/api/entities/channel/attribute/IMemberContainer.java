/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface IMemberContainer
extends GuildChannel {
    @Nonnull
    public @Unmodifiable List<Member> getMembers();
}

