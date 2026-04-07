/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModEventType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModRule;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod.AutoModTriggerType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod.AutoModResponseImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.gnu.trove.list.TLongList;
import com.hypherionmc.sdlink.shaded.gnu.trove.list.array.TLongArrayList;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class AutoModRuleImpl
implements AutoModRule {
    private final long id;
    private Guild guild;
    private long ownerId;
    private String name = "";
    private AutoModEventType eventType = AutoModEventType.UNKNOWN;
    private AutoModTriggerType triggerType = AutoModTriggerType.UNKNOWN;
    private boolean enabled = false;
    private TLongList exemptRoles = new TLongArrayList();
    private TLongList exemptChannels = new TLongArrayList();
    private List<AutoModResponse> actions = Collections.emptyList();
    private List<String> filteredKeywords = Collections.emptyList();
    private List<String> filteredRegex = Collections.emptyList();
    private EnumSet<AutoModRule.KeywordPreset> filteredPresets = EnumSet.noneOf(AutoModRule.KeywordPreset.class);
    private List<String> allowlist = Collections.emptyList();
    private int mentionLimit = -1;
    private boolean isMentionRaidProtectionEnabled = false;

    public AutoModRuleImpl(Guild guild, long id) {
        this.id = id;
        this.guild = guild;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        Guild realGuild = this.guild.getJDA().getGuildById(this.guild.getIdLong());
        if (realGuild != null) {
            this.guild = realGuild;
        }
        return this.guild;
    }

    @Override
    public long getCreatorIdLong() {
        return this.ownerId;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public AutoModEventType getEventType() {
        return this.eventType;
    }

    @Override
    @Nonnull
    public AutoModTriggerType getTriggerType() {
        return this.triggerType;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    @Nonnull
    public List<Role> getExemptRoles() {
        ArrayList<Role> roles = new ArrayList<Role>(this.exemptRoles.size());
        for (int i = 0; i < this.exemptRoles.size(); ++i) {
            long roleId = this.exemptRoles.get(i);
            Role role = this.guild.getRoleById(roleId);
            if (role == null) continue;
            roles.add(role);
        }
        return Collections.unmodifiableList(roles);
    }

    @Override
    @Nonnull
    public List<GuildChannel> getExemptChannels() {
        ArrayList<GuildChannel> channels = new ArrayList<GuildChannel>(this.exemptChannels.size());
        for (int i = 0; i < this.exemptChannels.size(); ++i) {
            long channelId = this.exemptChannels.get(i);
            GuildChannel channel = this.guild.getGuildChannelById(channelId);
            if (channel == null) continue;
            channels.add(channel);
        }
        return Collections.unmodifiableList(channels);
    }

    @Override
    @Nonnull
    public List<AutoModResponse> getActions() {
        return this.actions;
    }

    @Override
    @Nonnull
    public List<String> getFilteredKeywords() {
        return this.filteredKeywords;
    }

    @Override
    @Nonnull
    public List<String> getFilteredRegex() {
        return this.filteredRegex;
    }

    @Override
    @Nonnull
    public EnumSet<AutoModRule.KeywordPreset> getFilteredPresets() {
        return Helpers.copyEnumSet(AutoModRule.KeywordPreset.class, this.filteredPresets);
    }

    @Override
    @Nonnull
    public List<String> getAllowlist() {
        return this.allowlist;
    }

    @Override
    public int getMentionLimit() {
        return this.mentionLimit;
    }

    @Override
    public boolean isMentionRaidProtectionEnabled() {
        return this.isMentionRaidProtectionEnabled;
    }

    public AutoModRuleImpl setName(String name) {
        this.name = name;
        return this;
    }

    public AutoModRuleImpl setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public AutoModRuleImpl setOwnerId(long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public AutoModRuleImpl setEventType(AutoModEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public AutoModRuleImpl setTriggerType(AutoModTriggerType triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public AutoModRuleImpl setExemptRoles(TLongList exemptRoles) {
        this.exemptRoles = exemptRoles;
        return this;
    }

    public AutoModRuleImpl setExemptChannels(TLongList exemptChannels) {
        this.exemptChannels = exemptChannels;
        return this;
    }

    public AutoModRuleImpl setActions(List<AutoModResponse> actions) {
        this.actions = actions;
        return this;
    }

    public AutoModRuleImpl setFilteredKeywords(List<String> filteredKeywords) {
        this.filteredKeywords = filteredKeywords;
        return this;
    }

    public AutoModRuleImpl setFilteredRegex(List<String> filteredRegex) {
        this.filteredRegex = filteredRegex;
        return this;
    }

    public AutoModRuleImpl setFilteredPresets(EnumSet<AutoModRule.KeywordPreset> filteredPresets) {
        this.filteredPresets = filteredPresets;
        return this;
    }

    public AutoModRuleImpl setAllowlist(List<String> allowlist) {
        this.allowlist = allowlist;
        return this;
    }

    public AutoModRuleImpl setMentionLimit(int mentionLimit) {
        this.mentionLimit = mentionLimit;
        return this;
    }

    public AutoModRuleImpl setMentionRaidProtectionEnabled(boolean mentionRaidProtectionEnabled) {
        this.isMentionRaidProtectionEnabled = mentionRaidProtectionEnabled;
        return this;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AutoModRuleImpl)) {
            return false;
        }
        AutoModRuleImpl oRule = (AutoModRuleImpl)obj;
        return this.id == oRule.id;
    }

    public String toString() {
        return new EntityString(this).setType(this.triggerType).setName(this.name).addMetadata("id", this.getId()).toString();
    }

    public static AutoModRuleImpl fromData(Guild guild, DataObject data) {
        long id = data.getUnsignedLong("id");
        AutoModRuleImpl rule = new AutoModRuleImpl(guild, id);
        rule.setName(data.getString("name")).setEnabled(data.getBoolean("enabled", true)).setOwnerId(data.getUnsignedLong("creator_id", 0L)).setEventType(AutoModEventType.fromKey(data.getInt("event_type", -1))).setTriggerType(AutoModTriggerType.fromKey(data.getInt("trigger_type", -1)));
        data.optArray("exempt_roles").ifPresent(array -> rule.setExemptRoles(AutoModRuleImpl.parseList(array)));
        data.optArray("exempt_channels").ifPresent(array -> rule.setExemptChannels(AutoModRuleImpl.parseList(array)));
        data.optArray("actions").ifPresent(array -> rule.setActions(array.stream(DataArray::getObject).map(obj -> new AutoModResponseImpl(guild, (DataObject)obj)).collect(Helpers.toUnmodifiableList())));
        data.optObject("trigger_metadata").ifPresent(metadata -> {
            metadata.optArray("keyword_filter").ifPresent(array -> rule.setFilteredKeywords(array.stream(DataArray::getString).collect(Helpers.toUnmodifiableList())));
            metadata.optArray("regex_patterns").ifPresent(array -> rule.setFilteredRegex(array.stream(DataArray::getString).collect(Helpers.toUnmodifiableList())));
            metadata.optArray("allow_list").ifPresent(array -> rule.setAllowlist(array.stream(DataArray::getString).collect(Helpers.toUnmodifiableList())));
            metadata.optArray("presets").ifPresent(array -> rule.setFilteredPresets(array.stream(DataArray::getInt).map(AutoModRule.KeywordPreset::fromKey).collect(Collectors.toCollection(() -> EnumSet.noneOf(AutoModRule.KeywordPreset.class)))));
            rule.setMentionLimit(metadata.getInt("mention_total_limit", 0));
            rule.setMentionRaidProtectionEnabled(metadata.getBoolean("mention_raid_protection_enabled"));
        });
        return rule;
    }

    private static TLongList parseList(DataArray array) {
        TLongArrayList list = new TLongArrayList(array.length());
        for (int i = 0; i < array.length(); ++i) {
            list.add(array.getUnsignedLong(i));
        }
        return list;
    }
}

