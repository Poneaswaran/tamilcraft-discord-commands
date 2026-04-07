/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ISlowmodeChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.ForumChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ForumChannel
extends StandardGuildChannel,
IPostContainer,
IWebhookContainer,
IAgeRestrictedChannel,
ISlowmodeChannel {
    public static final int MAX_FORUM_TOPIC_LENGTH = 4096;
    public static final int MAX_POST_TAGS = 5;

    @Override
    @Nonnull
    default public ChannelType getType() {
        return ChannelType.FORUM;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ForumChannelManager getManager();

    @Nonnull
    @CheckReturnValue
    public ChannelAction<ForumChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<ForumChannel> createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Nonnull
    public Layout getDefaultLayout();

    public static enum Layout {
        DEFAULT_VIEW(0),
        LIST_VIEW(1),
        GALLERY_VIEW(2),
        UNKNOWN(-1);

        private final int key;

        private Layout(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static Layout fromKey(int key) {
            for (Layout layout : Layout.values()) {
                if (layout.key != key) continue;
                return layout;
            }
            return UNKNOWN;
        }
    }
}

