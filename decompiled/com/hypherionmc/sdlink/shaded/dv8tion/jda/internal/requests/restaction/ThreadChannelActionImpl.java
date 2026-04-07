/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class ThreadChannelActionImpl
extends AuditableRestActionImpl<ThreadChannel>
implements ThreadChannelAction {
    protected final Guild guild;
    protected final ChannelType type;
    protected final String parentMessageId;
    protected String name;
    protected ThreadChannel.AutoArchiveDuration autoArchiveDuration = null;
    protected Boolean invitable = null;

    public ThreadChannelActionImpl(GuildChannel channel, String name, ChannelType type) {
        super(channel.getJDA(), Route.Channels.CREATE_THREAD.compile(channel.getId()));
        this.guild = channel.getGuild();
        this.type = type;
        this.parentMessageId = null;
        this.name = name;
    }

    public ThreadChannelActionImpl(GuildChannel channel, String name, String parentMessageId) {
        super(channel.getJDA(), Route.Channels.CREATE_THREAD_FROM_MESSAGE.compile(channel.getId(), parentMessageId));
        this.guild = channel.getGuild();
        this.type = channel.getType() == ChannelType.TEXT ? ChannelType.GUILD_PUBLIC_THREAD : ChannelType.GUILD_NEWS_THREAD;
        this.parentMessageId = parentMessageId;
        this.name = name;
    }

    @Override
    @Nonnull
    public ThreadChannelActionImpl reason(String reason) {
        return (ThreadChannelActionImpl)super.reason(reason);
    }

    @Override
    @Nonnull
    public ThreadChannelActionImpl setCheck(BooleanSupplier checks) {
        return (ThreadChannelActionImpl)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public ThreadChannelActionImpl timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (ThreadChannelActionImpl)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public ThreadChannelActionImpl deadline(long timestamp) {
        return (ThreadChannelActionImpl)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return this.type;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ThreadChannelActionImpl setName(@Nonnull String name) {
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        this.name = name;
        return this;
    }

    @Override
    @Nonnull
    public ThreadChannelAction setAutoArchiveDuration(@Nonnull ThreadChannel.AutoArchiveDuration autoArchiveDuration) {
        Checks.notNull((Object)autoArchiveDuration, "autoArchiveDuration");
        this.autoArchiveDuration = autoArchiveDuration;
        return this;
    }

    @Override
    @Nonnull
    public ThreadChannelAction setInvitable(boolean invitable) {
        if (this.type != ChannelType.GUILD_PRIVATE_THREAD) {
            throw new UnsupportedOperationException("Can only set invitable on private threads");
        }
        this.invitable = invitable;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        object.put("name", this.name);
        if (this.parentMessageId == null) {
            object.put("type", this.type.getId());
        }
        if (this.autoArchiveDuration != null) {
            object.put("auto_archive_duration", this.autoArchiveDuration.getMinutes());
        }
        if (this.invitable != null) {
            object.put("invitable", this.invitable);
        }
        return this.getRequestBody(object);
    }

    @Override
    protected void handleSuccess(Response response, Request<ThreadChannel> request) {
        ThreadChannel channel = this.api.getEntityBuilder().createThreadChannel(response.getObject(), this.guild.getIdLong());
        request.onSuccess(channel);
    }
}

