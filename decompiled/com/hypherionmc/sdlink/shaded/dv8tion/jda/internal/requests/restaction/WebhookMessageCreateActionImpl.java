/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTagSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadCreateMetadata;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AbstractWebhookMessageActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.MessageCreateBuilderMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.List;
import java.util.function.Function;

public class WebhookMessageCreateActionImpl<T>
extends AbstractWebhookMessageActionImpl<T, WebhookMessageCreateActionImpl<T>>
implements WebhookMessageCreateAction<T>,
MessageCreateBuilderMixin<WebhookMessageCreateAction<T>> {
    private final MessageCreateBuilder builder = new MessageCreateBuilder();
    private final Function<DataObject, T> transformer;
    private boolean isInteraction = true;
    private boolean ephemeral;
    private String username;
    private String avatar;
    private ThreadCreateMetadata threadMetadata;

    public WebhookMessageCreateActionImpl(JDA api, Route.CompiledRoute route, Function<DataObject, T> transformer) {
        super(api, route);
        this.transformer = transformer;
    }

    public WebhookMessageCreateActionImpl<T> setInteraction(boolean isInteraction) {
        this.isInteraction = isInteraction;
        return this;
    }

    @Override
    public MessageCreateBuilder getBuilder() {
        return this.builder;
    }

    @Override
    @Nonnull
    public WebhookMessageCreateActionImpl<T> setEphemeral(boolean ephemeral) {
        if (!this.isInteraction && ephemeral) {
            throw new IllegalStateException("Cannot create ephemeral messages with webhooks. Use InteractionHook instead!");
        }
        this.ephemeral = ephemeral;
        return this;
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> setUsername(@Nullable String name) {
        if (this.isInteraction && this.username != null) {
            throw new IllegalStateException("Cannot set username on interaction messages.");
        }
        if (name != null) {
            name = name.trim();
            Checks.inRange(name, 1, 80, "Name");
        }
        this.username = name;
        return this;
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> setAvatarUrl(@Nullable String iconUrl) {
        if (this.isInteraction && iconUrl != null) {
            throw new IllegalStateException("Cannot set avatar on interaction messages.");
        }
        if (iconUrl != null) {
            Checks.noWhitespace(iconUrl, "Avatar URL");
            Checks.check(iconUrl.startsWith("https://") || iconUrl.startsWith("http://"), "Invalid URL format. Must start with 'https://' or 'http://'. Provided %s", (Object)iconUrl);
        }
        this.avatar = iconUrl;
        return this;
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> createThread(@Nonnull ThreadCreateMetadata threadMetadata) {
        if (this.isInteraction) {
            throw new IllegalStateException("Cannot create a thread through an interaction hook.");
        }
        Checks.notNull(threadMetadata, "Thread Metadata");
        this.threadMetadata = threadMetadata;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        try (MessageCreateData data = this.builder.build();){
            List<FileUpload> files = data.getFiles();
            DataObject json = data.toData();
            if (this.ephemeral) {
                json.put("flags", json.getInt("flags", 0) | Message.MessageFlag.EPHEMERAL.getValue());
            }
            if (this.username != null) {
                json.put("username", this.username);
            }
            if (this.avatar != null) {
                json.put("avatar_url", this.avatar);
            }
            if (this.threadId == null && this.threadMetadata != null) {
                json.put("thread_name", this.threadMetadata.getName());
                List<ForumTagSnowflake> tags = this.threadMetadata.getAppliedTags();
                if (!tags.isEmpty()) {
                    json.put("applied_tags", tags.stream().map(ISnowflake::getId).collect(Helpers.toDataArray()));
                }
            }
            RequestBody requestBody = this.getMultipartBody(files, json);
            return requestBody;
        }
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        Route.CompiledRoute route = super.finalizeRoute();
        if (this.threadId != null) {
            route = route.withQueryParams("thread_id", this.threadId);
        }
        return route;
    }

    @Override
    protected void handleSuccess(Response response, Request<T> request) {
        T message = this.transformer.apply(response.getObject());
        request.onSuccess(message);
    }
}

