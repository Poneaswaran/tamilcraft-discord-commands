/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.StageInstanceManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.StageInstanceManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public class StageInstanceImpl
implements StageInstance {
    private final long id;
    private StageChannel channel;
    private String topic;
    private StageInstance.PrivacyLevel privacyLevel;

    public StageInstanceImpl(long id, StageChannel channel) {
        this.id = id;
        this.channel = channel;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.getChannel().getGuild();
    }

    @Override
    @Nonnull
    public StageChannel getChannel() {
        StageChannel real = this.channel.getJDA().getStageChannelById(this.channel.getIdLong());
        if (real != null) {
            this.channel = real;
        }
        return this.channel;
    }

    @Override
    @Nonnull
    public String getTopic() {
        return this.topic;
    }

    @Override
    @Nonnull
    public StageInstance.PrivacyLevel getPrivacyLevel() {
        return this.privacyLevel;
    }

    @Override
    @Nonnull
    public RestAction<Void> delete() {
        this.checkPermissions();
        Route.CompiledRoute route = Route.StageInstances.DELETE_INSTANCE.compile(this.channel.getId());
        return new RestActionImpl<Void>(this.channel.getJDA(), route);
    }

    @Override
    @Nonnull
    public StageInstanceManager getManager() {
        this.checkPermissions();
        return new StageInstanceManagerImpl(this);
    }

    public StageInstanceImpl setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public StageInstanceImpl setPrivacyLevel(StageInstance.PrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
        return this;
    }

    public String toString() {
        return new EntityString(this).addMetadata("channel", this.getChannel()).toString();
    }

    private void checkPermissions() {
        EnumSet<Permission> permissions = this.getGuild().getSelfMember().getPermissions(this.getChannel());
        EnumSet<Permission> required = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS);
        for (Permission perm : required) {
            if (permissions.contains((Object)perm)) continue;
            throw new InsufficientPermissionException(this.getChannel(), perm, "You must be a stage moderator to manage a stage instance! Missing Permission: " + (Object)((Object)perm));
        }
    }
}

