/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ISlowmodeChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.StageChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.StageInstanceAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.StageInstanceActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;

public interface StageChannel
extends StandardGuildChannel,
GuildMessageChannel,
AudioChannel,
IWebhookContainer,
IAgeRestrictedChannel,
ISlowmodeChannel {
    public static final int MAX_USERLIMIT = 10000;

    @Nullable
    public StageInstance getStageInstance();

    @Nonnull
    @CheckReturnValue
    default public StageInstanceAction createStageInstance(@Nonnull String topic) {
        EnumSet<Permission> permissions = this.getGuild().getSelfMember().getPermissions(this);
        EnumSet<Permission> required = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS);
        for (Permission perm : required) {
            if (permissions.contains((Object)perm)) continue;
            throw new InsufficientPermissionException(this, perm, "You must be a stage moderator to create a stage instance! Missing Permission: " + (Object)((Object)perm));
        }
        return new StageInstanceActionImpl(this).setTopic(topic);
    }

    default public boolean isModerator(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return member.hasPermission((GuildChannel)this, Permission.MANAGE_CHANNEL, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS);
    }

    @Nonnull
    @CheckReturnValue
    public ChannelAction<StageChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<StageChannel> createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public StageChannelManager getManager();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> requestToSpeak();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> cancelRequestToSpeak();
}

