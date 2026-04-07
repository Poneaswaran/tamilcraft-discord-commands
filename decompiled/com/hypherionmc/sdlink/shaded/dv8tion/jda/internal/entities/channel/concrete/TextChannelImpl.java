/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractStandardGuildMessageChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ISlowmodeChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.TextChannelManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public class TextChannelImpl
extends AbstractStandardGuildMessageChannelImpl<TextChannelImpl>
implements TextChannel,
DefaultGuildChannelUnion,
ISlowmodeChannelMixin<TextChannelImpl> {
    private int slowmode;

    public TextChannelImpl(long id, GuildImpl guild) {
        super(id, guild);
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.TEXT;
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        return this.getGuild().getMembersView().stream().filter(m -> m.hasPermission((GuildChannel)this, Permission.VIEW_CHANNEL)).collect(Helpers.toUnmodifiableList());
    }

    @Override
    public int getSlowmode() {
        return this.slowmode;
    }

    @Override
    @Nonnull
    public ChannelAction<TextChannel> createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        ChannelAction<TextChannel> action = guild.createTextChannel(this.name).setNSFW(this.nsfw).setTopic(this.topic).setSlowmode(this.slowmode);
        if (guild.equals(this.getGuild())) {
            Category parent = this.getParentCategory();
            if (parent != null) {
                action.setParent(parent);
            }
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
    public TextChannelManager getManager() {
        return new TextChannelManagerImpl(this);
    }

    @Override
    public TextChannelImpl setSlowmode(int slowmode) {
        this.slowmode = slowmode;
        return this;
    }
}

