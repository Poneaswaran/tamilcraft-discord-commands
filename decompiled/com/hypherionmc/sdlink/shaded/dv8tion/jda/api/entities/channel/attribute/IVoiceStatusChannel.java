/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IVoiceStatusChannel
extends Channel {
    public static final int MAX_STATUS_LENGTH = 500;

    @Nonnull
    public String getStatus();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> modifyStatus(@Nonnull String var1);
}

