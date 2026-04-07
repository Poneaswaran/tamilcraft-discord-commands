/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModTriggerType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface AutoModExecution {
    @Nonnull
    public Guild getGuild();

    @Nullable
    public GuildMessageChannelUnion getChannel();

    @Nonnull
    public AutoModResponse getResponse();

    @Nonnull
    public AutoModTriggerType getTriggerType();

    public long getUserIdLong();

    @Nonnull
    default public String getUserId() {
        return Long.toUnsignedString(this.getUserIdLong());
    }

    public long getRuleIdLong();

    @Nonnull
    default public String getRuleId() {
        return Long.toUnsignedString(this.getRuleIdLong());
    }

    public long getMessageIdLong();

    @Nullable
    default public String getMessageId() {
        long id = this.getMessageIdLong();
        return id == 0L ? null : Long.toUnsignedString(this.getMessageIdLong());
    }

    public long getAlertMessageIdLong();

    @Nullable
    default public String getAlertMessageId() {
        long id = this.getAlertMessageIdLong();
        return id == 0L ? null : Long.toUnsignedString(this.getAlertMessageIdLong());
    }

    @Nonnull
    public String getContent();

    @Nullable
    public String getMatchedContent();

    @Nullable
    public String getMatchedKeyword();
}

