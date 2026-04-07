/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.AbstractChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.MessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class PrivateChannelImpl
extends AbstractChannelImpl<PrivateChannelImpl>
implements PrivateChannel,
MessageChannelMixin<PrivateChannelImpl> {
    private User user;
    private long latestMessageId;

    public PrivateChannelImpl(JDA api, long id, @Nullable User user) {
        super(id, api);
        this.user = user;
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.PRIVATE;
    }

    @Override
    @Nullable
    public User getUser() {
        this.updateUser();
        return this.user;
    }

    @Override
    @Nonnull
    public RestAction<User> retrieveUser() {
        User user = this.getUser();
        if (user != null) {
            return new CompletedRestAction<User>(this.getJDA(), user);
        }
        return this.retrievePrivateChannel().map(PrivateChannel::getUser);
    }

    @Override
    @Nonnull
    public String getName() {
        User user = this.getUser();
        if (user == null) {
            return "";
        }
        return user.getName();
    }

    @Nonnull
    private RestAction<PrivateChannel> retrievePrivateChannel() {
        Route.CompiledRoute route = Route.Channels.GET_CHANNEL.compile(this.getId());
        return new RestActionImpl<PrivateChannel>(this.getJDA(), route, (response, request) -> ((JDAImpl)this.getJDA()).getEntityBuilder().createPrivateChannel(response.getObject()));
    }

    @Override
    @Nonnull
    public RestAction<Void> delete() {
        Route.CompiledRoute route = Route.Channels.DELETE_CHANNEL.compile(this.getId());
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    public long getLatestMessageIdLong() {
        return this.latestMessageId;
    }

    @Override
    public boolean canTalk() {
        return this.user == null || !this.user.isBot();
    }

    @Override
    public void checkCanAccess() {
    }

    @Override
    public void checkCanSendMessage() {
        this.checkBot();
    }

    @Override
    public void checkCanSendMessageEmbeds() {
    }

    @Override
    public void checkCanSendFiles() {
    }

    @Override
    public void checkCanViewHistory() {
    }

    @Override
    public void checkCanAddReactions() {
    }

    @Override
    public void checkCanRemoveReactions() {
    }

    @Override
    public void checkCanControlMessagePins() {
    }

    @Override
    public boolean canDeleteOtherUsersMessages() {
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public PrivateChannelImpl setLatestMessageIdLong(long latestMessageId) {
        this.latestMessageId = latestMessageId;
        return this;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PrivateChannelImpl)) {
            return false;
        }
        PrivateChannelImpl impl = (PrivateChannelImpl)obj;
        return impl.id == this.id;
    }

    private void updateUser() {
        if (this.user == null) {
            return;
        }
        User realUser = this.getJDA().getUserById(this.user.getIdLong());
        if (realUser != null) {
            this.user = realUser;
        }
    }

    private void checkBot() {
        if (this.getUser() != null && this.getUser().isBot()) {
            throw new UnsupportedOperationException("Cannot send a private message between bots.");
        }
    }
}

