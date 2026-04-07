/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModTriggerType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.build.TriggerConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AutoModRuleManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class AutoModRuleManagerImpl
extends ManagerBase<AutoModRuleManager>
implements AutoModRuleManager {
    protected final Guild guild;
    protected String name;
    protected boolean enabled;
    protected EnumMap<AutoModResponse.Type, AutoModResponse> responses;
    protected List<Role> exemptRoles;
    protected List<GuildChannel> exemptChannels;
    protected TriggerConfig triggerConfig;

    public AutoModRuleManagerImpl(Guild guild, String ruleId) {
        super(guild.getJDA(), Route.AutoModeration.UPDATE_RULE.compile(guild.getId(), ruleId));
        this.guild = guild;
    }

    @Override
    @Nonnull
    public AutoModRuleManager setName(@Nonnull String name) {
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        this.name = name;
        this.set |= 1L;
        return this;
    }

    @Override
    @Nonnull
    public AutoModRuleManager setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.set |= 2L;
        return this;
    }

    @Override
    @Nonnull
    public AutoModRuleManager setResponses(@Nonnull Collection<? extends AutoModResponse> responses) {
        Checks.noneNull(responses, "Responses");
        Checks.notEmpty(responses, "Responses");
        this.responses = new EnumMap(AutoModResponse.Type.class);
        for (AutoModResponse autoModResponse : responses) {
            AutoModResponse.Type type = autoModResponse.getType();
            Checks.check(type != AutoModResponse.Type.UNKNOWN, "Cannot add response with unknown response type!");
            this.responses.put(type, autoModResponse);
        }
        this.set |= 4L;
        return this;
    }

    @Override
    @Nonnull
    public AutoModRuleManager setExemptRoles(@Nonnull Collection<Role> roles) {
        Checks.noneNull(roles, "Roles");
        Checks.check(roles.size() <= 20, "Cannot have more than %d exempt roles!", (Object)20);
        for (Role role : roles) {
            Checks.check(role.getGuild().equals(this.guild), "Role %s is not from the same guild as this rule!", (Object)role);
        }
        this.exemptRoles = new ArrayList<Role>(roles);
        this.set |= 8L;
        return this;
    }

    @Override
    @Nonnull
    public AutoModRuleManager setExemptChannels(@Nonnull Collection<? extends GuildChannel> channels) {
        Checks.noneNull(channels, "Channels");
        Checks.check(channels.size() <= 50, "Cannot have more than %d exempt channels!", (Object)50);
        for (GuildChannel guildChannel : channels) {
            Checks.check(guildChannel.getGuild().equals(this.guild), "Channel %s is not from the same guild as this rule!", (Object)guildChannel);
        }
        this.exemptChannels = new ArrayList<GuildChannel>(channels);
        this.set |= 0x10L;
        return this;
    }

    @Override
    @Nonnull
    public AutoModRuleManager setTriggerConfig(@Nonnull TriggerConfig config) {
        Checks.notNull(config, "TriggerConfig");
        Checks.check(config.getType() != AutoModTriggerType.UNKNOWN, "Unknown trigger type!");
        this.triggerConfig = config;
        this.set |= 0x20L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject body = DataObject.empty();
        if (this.shouldUpdate(1L)) {
            body.put("name", this.name);
        }
        if (this.shouldUpdate(2L)) {
            body.put("enabled", this.enabled);
        }
        if (this.shouldUpdate(4L)) {
            body.put("actions", DataArray.fromCollection(this.responses.values()));
        }
        if (this.shouldUpdate(8L)) {
            body.put("exempt_roles", DataArray.fromCollection(this.exemptRoles.stream().map(ISnowflake::getId).collect(Collectors.toList())));
        }
        if (this.shouldUpdate(16L)) {
            body.put("exempt_channels", DataArray.fromCollection(this.exemptChannels.stream().map(ISnowflake::getId).collect(Collectors.toList())));
        }
        if (this.shouldUpdate(32L)) {
            body.put("trigger_type", this.triggerConfig.getType().getKey());
            body.put("trigger_metadata", this.triggerConfig.toData());
        }
        this.reset();
        return this.getRequestBody(body);
    }
}

