/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.CategoryManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPositionableChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.CategoryManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.PermissionUtil;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class CategoryImpl
extends AbstractGuildChannelImpl<CategoryImpl>
implements Category,
IPositionableChannelMixin<CategoryImpl>,
IPermissionContainerMixin<CategoryImpl> {
    private final TLongObjectMap<PermissionOverride> overrides = MiscUtil.newLongMap();
    private int position;

    public CategoryImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.CATEGORY;
    }

    @Override
    public int getPositionRaw() {
        return this.position;
    }

    @Override
    @Nonnull
    public ChannelAction<TextChannel> createTextChannel(@Nonnull String name) {
        ChannelAction<TextChannel> action = this.getGuild().createTextChannel(name, this);
        return this.trySync(action);
    }

    @Override
    @Nonnull
    public ChannelAction<NewsChannel> createNewsChannel(@Nonnull String name) {
        ChannelAction<NewsChannel> action = this.getGuild().createNewsChannel(name, this);
        return this.trySync(action);
    }

    @Override
    @Nonnull
    public ChannelAction<VoiceChannel> createVoiceChannel(@Nonnull String name) {
        ChannelAction<VoiceChannel> action = this.getGuild().createVoiceChannel(name, this);
        return this.trySync(action);
    }

    @Override
    @Nonnull
    public ChannelAction<StageChannel> createStageChannel(@Nonnull String name) {
        ChannelAction<StageChannel> action = this.getGuild().createStageChannel(name, this);
        return this.trySync(action);
    }

    @Override
    @Nonnull
    public ChannelAction<ForumChannel> createForumChannel(@Nonnull String name) {
        ChannelAction<ForumChannel> action = this.getGuild().createForumChannel(name, this);
        return this.trySync(action);
    }

    @Override
    @Nonnull
    public ChannelAction<MediaChannel> createMediaChannel(@Nonnull String name) {
        ChannelAction<MediaChannel> action = this.getGuild().createMediaChannel(name, this);
        return this.trySync(action);
    }

    @Override
    @Nonnull
    public CategoryOrderAction modifyTextChannelPositions() {
        return this.getGuild().modifyTextChannelPositions(this);
    }

    @Override
    @Nonnull
    public CategoryOrderAction modifyVoiceChannelPositions() {
        return this.getGuild().modifyVoiceChannelPositions(this);
    }

    @Override
    @Nonnull
    public ChannelAction<Category> createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        ChannelAction<Category> action = guild.createCategory(this.name);
        if (guild.equals(this.getGuild())) {
            for (PermissionOverride o : this.overrides.valueCollection()) {
                if (o.isMemberOverride()) {
                    action.addMemberPermissionOverride(o.getIdLong(), o.getAllowedRaw(), o.getDeniedRaw());
                    continue;
                }
                action.addRolePermissionOverride(o.getIdLong(), o.getAllowedRaw(), o.getDeniedRaw());
            }
        }
        return action;
    }

    @Override
    @Nonnull
    public ChannelAction<Category> createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Override
    @Nonnull
    public CategoryManager getManager() {
        return new CategoryManagerImpl(this);
    }

    @Override
    public TLongObjectMap<PermissionOverride> getPermissionOverrideMap() {
        return this.overrides;
    }

    @Override
    public CategoryImpl setPosition(int position) {
        this.position = position;
        return this;
    }

    private <T extends GuildChannel> ChannelAction<T> trySync(ChannelAction<T> action) {
        Member selfMember = this.getGuild().getSelfMember();
        if (!selfMember.canSync(this)) {
            long botPerms = PermissionUtil.getEffectivePermission((GuildChannel)this, selfMember);
            for (PermissionOverride override : this.getPermissionOverrides()) {
                long perms = override.getDeniedRaw() | override.getAllowedRaw();
                if ((perms & (botPerms ^ 0xFFFFFFFFFFFFFFFFL)) == 0L) continue;
                return action;
            }
        }
        return action.syncPermissionOverrides();
    }
}

