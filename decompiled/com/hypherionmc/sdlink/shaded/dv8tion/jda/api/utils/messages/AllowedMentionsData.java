/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class AllowedMentionsData
implements SerializableData {
    private static EnumSet<Message.MentionType> defaultParse = EnumSet.allOf(Message.MentionType.class);
    private static boolean defaultMentionRepliedUser = true;
    private EnumSet<Message.MentionType> mentionParse = AllowedMentionsData.getDefaultMentions();
    private final Set<String> mentionUsers = new HashSet<String>();
    private final Set<String> mentionRoles = new HashSet<String>();
    private boolean mentionRepliedUser = defaultMentionRepliedUser;

    AllowedMentionsData() {
    }

    public static void setDefaultMentions(@Nullable Collection<Message.MentionType> allowedMentions) {
        defaultParse = allowedMentions == null ? EnumSet.allOf(Message.MentionType.class) : Helpers.copyEnumSet(Message.MentionType.class, allowedMentions);
    }

    @Nonnull
    public static EnumSet<Message.MentionType> getDefaultMentions() {
        return defaultParse.clone();
    }

    public static void setDefaultMentionRepliedUser(boolean mention) {
        defaultMentionRepliedUser = mention;
    }

    public static boolean isDefaultMentionRepliedUser() {
        return defaultMentionRepliedUser;
    }

    public void clear() {
        this.mentionParse = AllowedMentionsData.getDefaultMentions();
        this.mentionUsers.clear();
        this.mentionRoles.clear();
        this.mentionRepliedUser = defaultMentionRepliedUser;
    }

    @Nonnull
    public AllowedMentionsData copy() {
        AllowedMentionsData copy = new AllowedMentionsData();
        copy.mentionParse = this.mentionParse;
        copy.mentionUsers.addAll(this.mentionUsers);
        copy.mentionRoles.addAll(this.mentionRoles);
        copy.mentionRepliedUser = this.mentionRepliedUser;
        return copy;
    }

    public void mentionRepliedUser(boolean mention) {
        this.mentionRepliedUser = mention;
    }

    public void setAllowedMentions(@Nullable Collection<Message.MentionType> allowedMentions) {
        this.mentionParse = allowedMentions == null ? EnumSet.allOf(Message.MentionType.class) : Helpers.copyEnumSet(Message.MentionType.class, allowedMentions);
    }

    public void mention(@Nonnull Collection<? extends IMentionable> mentions) {
        Checks.noneNull(mentions, "Mentionables");
        for (IMentionable iMentionable : mentions) {
            if (iMentionable instanceof UserSnowflake) {
                this.mentionUsers.add(iMentionable.getId());
                continue;
            }
            if (!(iMentionable instanceof Role)) continue;
            this.mentionRoles.add(iMentionable.getId());
        }
    }

    public void mentionUsers(@Nonnull Collection<String> userIds) {
        Checks.noneNull(userIds, "User Id");
        this.mentionUsers.addAll(userIds);
    }

    public void mentionRoles(@Nonnull Collection<String> roleIds) {
        Checks.noneNull(roleIds, "Role Id");
        this.mentionRoles.addAll(roleIds);
    }

    @Nonnull
    public Set<String> getMentionedUsers() {
        return Collections.unmodifiableSet(new HashSet<String>(this.mentionUsers));
    }

    @Nonnull
    public Set<String> getMentionedRoles() {
        return Collections.unmodifiableSet(new HashSet<String>(this.mentionRoles));
    }

    @Nonnull
    public EnumSet<Message.MentionType> getAllowedMentions() {
        return this.mentionParse.clone();
    }

    public boolean isMentionRepliedUser() {
        return this.mentionRepliedUser;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject allowedMentionsObj = DataObject.empty();
        DataArray parsable = DataArray.empty();
        if (this.mentionParse != null) {
            this.mentionParse.stream().map(Message.MentionType::getParseKey).filter(Objects::nonNull).distinct().forEach(parsable::add);
        }
        if (!this.mentionUsers.isEmpty()) {
            parsable.remove(Message.MentionType.USER.getParseKey());
            allowedMentionsObj.put("users", DataArray.fromCollection(this.mentionUsers));
        }
        if (!this.mentionRoles.isEmpty()) {
            parsable.remove(Message.MentionType.ROLE.getParseKey());
            allowedMentionsObj.put("roles", DataArray.fromCollection(this.mentionRoles));
        }
        allowedMentionsObj.put("replied_user", this.mentionRepliedUser);
        return allowedMentionsObj.put("parse", parsable);
    }
}

