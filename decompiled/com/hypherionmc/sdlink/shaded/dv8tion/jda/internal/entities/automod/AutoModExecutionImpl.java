/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModExecution;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModTriggerType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod.AutoModResponseImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class AutoModExecutionImpl
implements AutoModExecution {
    private final Guild guild;
    private final GuildMessageChannel channel;
    private final AutoModResponse response;
    private final AutoModTriggerType type;
    private final long userId;
    private final long ruleId;
    private final long messageId;
    private final long alertMessageId;
    private final String content;
    private final String matchedContent;
    private final String matchedKeyword;

    public AutoModExecutionImpl(Guild guild, DataObject json) {
        this.guild = guild;
        this.channel = guild.getChannelById(GuildMessageChannel.class, json.getUnsignedLong("channel_id", 0L));
        this.response = new AutoModResponseImpl(guild, json.getObject("action"));
        this.type = AutoModTriggerType.fromKey(json.getInt("rule_trigger_type", -1));
        this.userId = json.getUnsignedLong("user_id");
        this.ruleId = json.getUnsignedLong("rule_id");
        this.messageId = json.getUnsignedLong("message_id", 0L);
        this.alertMessageId = json.getUnsignedLong("alert_system_message_id", 0L);
        this.content = json.getString("content", "");
        this.matchedContent = json.getString("matched_content", null);
        this.matchedKeyword = json.getString("matched_keyword", null);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nullable
    public GuildMessageChannelUnion getChannel() {
        return (GuildMessageChannelUnion)this.channel;
    }

    @Override
    @Nonnull
    public AutoModResponse getResponse() {
        return this.response;
    }

    @Override
    @Nonnull
    public AutoModTriggerType getTriggerType() {
        return this.type;
    }

    @Override
    public long getUserIdLong() {
        return this.userId;
    }

    @Override
    public long getRuleIdLong() {
        return this.ruleId;
    }

    @Override
    public long getMessageIdLong() {
        return this.messageId;
    }

    @Override
    public long getAlertMessageIdLong() {
        return this.alertMessageId;
    }

    @Override
    @Nonnull
    public String getContent() {
        return this.content;
    }

    @Override
    @Nullable
    public String getMatchedContent() {
        return this.matchedContent;
    }

    @Override
    @Nullable
    public String getMatchedKeyword() {
        return this.matchedKeyword;
    }
}

