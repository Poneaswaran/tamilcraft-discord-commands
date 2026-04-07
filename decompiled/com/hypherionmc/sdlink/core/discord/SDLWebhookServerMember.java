/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.core.discord;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ClientType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import lombok.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class SDLWebhookServerMember
implements Member {
    private final User user;
    private final Guild guild;
    private final JDA jda;

    @Override
    @NotNull
    public User getUser() {
        return this.user;
    }

    @Override
    @NotNull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @NotNull
    public EnumSet<Permission> getPermissions() {
        return Permission.getPermissions(Permission.getRaw(Permission.values()));
    }

    @Override
    @NotNull
    public EnumSet<Permission> getPermissions(GuildChannel guildChannel) {
        return Permission.getPermissions(Permission.getRaw(Permission.values()));
    }

    @Override
    @NotNull
    public EnumSet<Permission> getPermissionsExplicit() {
        return Permission.getPermissions(Permission.getRaw(Permission.values()));
    }

    @Override
    @NotNull
    public EnumSet<Permission> getPermissionsExplicit(@NotNull GuildChannel guildChannel) {
        return Permission.getPermissions(Permission.getRaw(Permission.values()));
    }

    @Override
    public boolean hasPermission(Permission ... permissions) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull Collection<Permission> collection) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull GuildChannel guildChannel, Permission ... permissions) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull GuildChannel guildChannel, @NotNull Collection<Permission> collection) {
        return true;
    }

    @Override
    public boolean canSync(@NotNull IPermissionContainer iPermissionContainer, @NotNull IPermissionContainer iPermissionContainer1) {
        return true;
    }

    @Override
    public boolean canSync(@NotNull IPermissionContainer iPermissionContainer) {
        return true;
    }

    @Override
    @NotNull
    public JDA getJDA() {
        return this.jda;
    }

    @Override
    @NotNull
    public OffsetDateTime getTimeJoined() {
        return this.user.getTimeCreated();
    }

    @Override
    public boolean hasTimeJoined() {
        return false;
    }

    @Override
    public OffsetDateTime getTimeBoosted() {
        return null;
    }

    @Override
    public boolean isBoosting() {
        return false;
    }

    @Override
    public OffsetDateTime getTimeOutEnd() {
        return null;
    }

    @Override
    public GuildVoiceState getVoiceState() {
        return null;
    }

    @Override
    @NotNull
    public List<Activity> getActivities() {
        return List.of();
    }

    @Override
    @NotNull
    public OnlineStatus getOnlineStatus() {
        return OnlineStatus.ONLINE;
    }

    @Override
    @NotNull
    public OnlineStatus getOnlineStatus(@NotNull ClientType clientType) {
        return OnlineStatus.ONLINE;
    }

    @Override
    @NotNull
    public EnumSet<ClientType> getActiveClients() {
        return EnumSet.of(ClientType.DESKTOP);
    }

    @Override
    public String getNickname() {
        return this.user.getName();
    }

    @Override
    @NotNull
    public String getEffectiveName() {
        return this.user.getEffectiveName();
    }

    @Override
    public String getAvatarId() {
        return this.user.getAvatarId();
    }

    @Override
    @NotNull
    public List<Role> getRoles() {
        return List.of();
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public int getColorRaw() {
        return Color.white.getRGB();
    }

    @Override
    public int getFlagsRaw() {
        return this.user.getFlagsRaw();
    }

    @Override
    public boolean canInteract(@NotNull Member member) {
        return true;
    }

    @Override
    public boolean canInteract(@NotNull Role role) {
        return true;
    }

    @Override
    public boolean canInteract(@NotNull RichCustomEmoji richCustomEmoji) {
        return true;
    }

    @Override
    public boolean isOwner() {
        return false;
    }

    @Override
    public boolean isPending() {
        return false;
    }

    @Override
    public DefaultGuildChannelUnion getDefaultChannel() {
        return null;
    }

    @Override
    @NotNull
    public String getAsMention() {
        return this.user.getAsMention();
    }

    @Override
    @NotNull
    public String getDefaultAvatarId() {
        return this.user.getDefaultAvatarId();
    }

    @Override
    public long getIdLong() {
        return this.user.getIdLong();
    }

    @Generated
    private SDLWebhookServerMember(User user, Guild guild, JDA jda) {
        this.user = user;
        this.guild = guild;
        this.jda = jda;
    }

    @Generated
    public static SDLWebhookServerMember of(User user, Guild guild, JDA jda) {
        return new SDLWebhookServerMember(user, guild, jda);
    }
}

