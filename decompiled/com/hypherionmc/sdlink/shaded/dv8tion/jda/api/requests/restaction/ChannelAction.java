/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.BaseForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.FluentAuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public interface ChannelAction<T extends GuildChannel>
extends FluentAuditableRestAction<T, ChannelAction<T>> {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    public ChannelType getType();

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setParent(@Nullable Category var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setPosition(@Nullable Integer var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setTopic(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setNSFW(boolean var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setSlowmode(int var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setDefaultThreadSlowmode(int var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setDefaultReaction(@Nullable Emoji var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setDefaultLayout(@Nonnull ForumChannel.Layout var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setDefaultSortOrder(@Nonnull IPostContainer.SortOrder var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setAvailableTags(@Nonnull List<? extends BaseForumTag> var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<T> addPermissionOverride(@Nonnull IPermissionHolder target, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
        long allowRaw = allow != null ? Permission.getRaw(allow) : 0L;
        long denyRaw = deny != null ? Permission.getRaw(deny) : 0L;
        return this.addPermissionOverride(target, allowRaw, denyRaw);
    }

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<T> addPermissionOverride(@Nonnull IPermissionHolder target, long allow, long deny) {
        Checks.notNull(target, "Override Role/Member");
        if (target instanceof Role) {
            return this.addRolePermissionOverride(target.getIdLong(), allow, deny);
        }
        if (target instanceof Member) {
            return this.addMemberPermissionOverride(target.getIdLong(), allow, deny);
        }
        throw new IllegalArgumentException("Cannot add override for " + target.getClass().getSimpleName());
    }

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<T> addMemberPermissionOverride(long memberId, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
        long allowRaw = allow != null ? Permission.getRaw(allow) : 0L;
        long denyRaw = deny != null ? Permission.getRaw(deny) : 0L;
        return this.addMemberPermissionOverride(memberId, allowRaw, denyRaw);
    }

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<T> addRolePermissionOverride(long roleId, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
        long allowRaw = allow != null ? Permission.getRaw(allow) : 0L;
        long denyRaw = deny != null ? Permission.getRaw(deny) : 0L;
        return this.addRolePermissionOverride(roleId, allowRaw, denyRaw);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> addMemberPermissionOverride(long var1, long var3, long var5);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> addRolePermissionOverride(long var1, long var3, long var5);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> removePermissionOverride(long var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<T> removePermissionOverride(@Nonnull String id) {
        return this.removePermissionOverride(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<T> removePermissionOverride(@Nonnull IPermissionHolder holder) {
        Checks.notNull(holder, "PermissionHolder");
        return this.removePermissionOverride(holder.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> clearPermissionOverrides();

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> syncPermissionOverrides();

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setBitrate(@Nullable Integer var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setUserlimit(@Nullable Integer var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<T> setRegion(@Nullable Region var1);
}

