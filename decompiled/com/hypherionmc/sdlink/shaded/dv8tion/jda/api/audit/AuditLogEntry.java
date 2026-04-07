/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ActionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogChange;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogKey;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.AuditLogOption;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.TargetType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.WebhookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Unmodifiable;

public class AuditLogEntry
implements ISnowflake {
    protected final long id;
    protected final long targetId;
    protected final long userId;
    protected final GuildImpl guild;
    protected final UserImpl user;
    protected final WebhookImpl webhook;
    protected final String reason;
    protected final Map<String, AuditLogChange> changes;
    protected final Map<String, Object> options;
    protected final ActionType type;
    protected final int rawType;

    public AuditLogEntry(ActionType type, int rawType, long id, long userId, long targetId, GuildImpl guild, UserImpl user, WebhookImpl webhook, String reason, Map<String, AuditLogChange> changes, Map<String, Object> options) {
        this.type = type;
        this.rawType = rawType;
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.guild = guild;
        this.user = user;
        this.webhook = webhook;
        this.reason = reason;
        this.changes = changes != null && !changes.isEmpty() ? Collections.unmodifiableMap(changes) : Collections.emptyMap();
        this.options = options != null && !options.isEmpty() ? Collections.unmodifiableMap(options) : Collections.emptyMap();
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    public long getTargetIdLong() {
        return this.targetId;
    }

    @Nonnull
    public String getTargetId() {
        return Long.toUnsignedString(this.targetId);
    }

    @Nullable
    public Webhook getWebhook() {
        return this.webhook;
    }

    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    public long getUserIdLong() {
        return this.userId;
    }

    @Nonnull
    public String getUserId() {
        return Long.toUnsignedString(this.userId);
    }

    @Nullable
    public User getUser() {
        return this.user;
    }

    @Nullable
    public String getReason() {
        return this.reason;
    }

    @Nonnull
    public JDA getJDA() {
        return this.guild.getJDA();
    }

    @Nonnull
    public Map<String, AuditLogChange> getChanges() {
        return this.changes;
    }

    @Nullable
    public AuditLogChange getChangeByKey(@Nullable AuditLogKey key) {
        return key == null ? null : this.getChangeByKey(key.getKey());
    }

    @Nullable
    public AuditLogChange getChangeByKey(@Nullable String key) {
        return this.changes.get(key);
    }

    @Nonnull
    public @Unmodifiable List<AuditLogChange> getChangesForKeys(AuditLogKey ... keys) {
        Checks.notNull(keys, "Keys");
        ArrayList<AuditLogChange> changes = new ArrayList<AuditLogChange>(keys.length);
        for (AuditLogKey key : keys) {
            AuditLogChange change = this.getChangeByKey(key);
            if (change == null) continue;
            changes.add(change);
        }
        return Collections.unmodifiableList(changes);
    }

    @Nonnull
    public Map<String, Object> getOptions() {
        return this.options;
    }

    @Nullable
    public <T> T getOptionByName(@Nullable String name) {
        return (T)this.options.get(name);
    }

    @Nullable
    public <T> T getOption(@Nonnull AuditLogOption option) {
        Checks.notNull((Object)option, "Option");
        return this.getOptionByName(option.getKey());
    }

    @Nonnull
    public @Unmodifiable List<Object> getOptions(AuditLogOption ... options) {
        Checks.notNull(options, "Options");
        ArrayList items = new ArrayList(options.length);
        for (AuditLogOption option : options) {
            Object obj = this.getOption(option);
            if (obj == null) continue;
            items.add(obj);
        }
        return Collections.unmodifiableList(items);
    }

    @Nonnull
    public ActionType getType() {
        return this.type;
    }

    public int getTypeRaw() {
        return this.rawType;
    }

    @Nonnull
    public TargetType getTargetType() {
        return this.type.getTargetType();
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AuditLogEntry)) {
            return false;
        }
        AuditLogEntry other = (AuditLogEntry)obj;
        return other.id == this.id && other.targetId == this.targetId;
    }

    public String toString() {
        return new EntityString(this).setType(this.type).addMetadata("targetId", this.targetId).addMetadata("guild", this.guild).toString();
    }
}

