/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumPost;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTagSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ForumPostAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.MessageCreateBuilderMixin;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.hash.TLongHashSet;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.Collection;
import java.util.function.BooleanSupplier;

public class ForumPostActionImpl
extends RestActionImpl<ForumPost>
implements ForumPostAction,
MessageCreateBuilderMixin<ForumPostAction> {
    private final MessageCreateBuilder builder;
    private final IPostContainer channel;
    private final TLongSet appliedTags = new TLongHashSet();
    private String name;
    private ThreadChannel.AutoArchiveDuration autoArchiveDuration;

    public ForumPostActionImpl(IPostContainer channel, String name, MessageCreateBuilder builder) {
        super(channel.getJDA(), Route.Channels.CREATE_THREAD.compile(channel.getId()));
        this.builder = builder;
        this.channel = channel;
        this.setName(name);
    }

    @Override
    @Nonnull
    public ForumPostAction setCheck(BooleanSupplier checks) {
        return (ForumPostAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public ForumPostAction addCheck(@Nonnull BooleanSupplier checks) {
        return (ForumPostAction)super.addCheck(checks);
    }

    @Override
    @Nonnull
    public ForumPostAction deadline(long timestamp) {
        return (ForumPostAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.channel.getGuild();
    }

    @Override
    @Nonnull
    public IPostContainer getChannel() {
        return this.channel;
    }

    @Override
    @Nonnull
    public ForumPostAction setTags(@Nonnull Collection<? extends ForumTagSnowflake> tags) {
        Checks.noneNull(tags, "Tags");
        Checks.check(tags.size() <= 5, "Provided more than %d tags.", (Object)5);
        Checks.check(!this.channel.isTagRequired() || !tags.isEmpty(), "This forum requires at least one tag per post! See ForumChannel#isRequireTag()");
        this.appliedTags.clear();
        tags.forEach(t -> this.appliedTags.add(t.getIdLong()));
        return this;
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return ChannelType.GUILD_PUBLIC_THREAD;
    }

    @Override
    @Nonnull
    public ForumPostAction setName(@Nonnull String name) {
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        this.name = name.trim();
        return this;
    }

    @Override
    @Nonnull
    public ForumPostAction setAutoArchiveDuration(@Nonnull ThreadChannel.AutoArchiveDuration autoArchiveDuration) {
        Checks.notNull((Object)autoArchiveDuration, "AutoArchiveDuration");
        this.autoArchiveDuration = autoArchiveDuration;
        return this;
    }

    @Override
    public MessageCreateBuilder getBuilder() {
        return this.builder;
    }

    @Override
    protected RequestBody finalizeData() {
        try (MessageCreateData message = this.builder.build();){
            DataObject json = DataObject.empty();
            json.put("message", message);
            json.put("name", this.name);
            if (this.autoArchiveDuration != null) {
                json.put("auto_archive_duration", this.autoArchiveDuration.getMinutes());
            }
            if (!this.appliedTags.isEmpty()) {
                json.put("applied_tags", this.appliedTags.toArray());
            } else if (this.getChannel().isTagRequired()) {
                throw new IllegalStateException("Cannot create posts without a tag in this forum. Apply at least one tag!");
            }
            RequestBody requestBody = this.getMultipartBody(message.getFiles(), json);
            return requestBody;
        }
    }

    @Override
    protected void handleSuccess(Response response, Request<ForumPost> request) {
        DataObject json = response.getObject();
        EntityBuilder entityBuilder = this.api.getEntityBuilder();
        ThreadChannel thread = entityBuilder.createThreadChannel(json, this.getGuild().getIdLong());
        ReceivedMessage message = entityBuilder.createMessageWithChannel(json.getObject("message"), thread, false);
        request.onSuccess(new ForumPost(message, thread));
    }
}

