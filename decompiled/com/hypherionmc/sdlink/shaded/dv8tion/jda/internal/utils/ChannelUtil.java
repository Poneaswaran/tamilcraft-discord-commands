/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPositionableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import java.util.EnumSet;

public class ChannelUtil {
    public static final EnumSet<ChannelType> SLOWMODE_SUPPORTED = EnumSet.of(ChannelType.TEXT, new ChannelType[]{ChannelType.FORUM, ChannelType.MEDIA, ChannelType.GUILD_PUBLIC_THREAD, ChannelType.GUILD_NEWS_THREAD, ChannelType.GUILD_PRIVATE_THREAD, ChannelType.STAGE, ChannelType.VOICE});
    public static final EnumSet<ChannelType> NSFW_SUPPORTED = EnumSet.of(ChannelType.TEXT, new ChannelType[]{ChannelType.VOICE, ChannelType.FORUM, ChannelType.MEDIA, ChannelType.NEWS, ChannelType.STAGE});
    public static final EnumSet<ChannelType> TOPIC_SUPPORTED = EnumSet.of(ChannelType.TEXT, ChannelType.FORUM, ChannelType.MEDIA, ChannelType.NEWS);
    public static final EnumSet<ChannelType> POST_CONTAINERS = EnumSet.of(ChannelType.FORUM, ChannelType.MEDIA);
    public static final EnumSet<ChannelType> THREAD_CONTAINERS = EnumSet.of(ChannelType.TEXT, ChannelType.NEWS, ChannelType.FORUM, ChannelType.MEDIA);

    public static <T extends Channel> T safeChannelCast(Object instance, Class<T> toObjectClass) {
        if (toObjectClass.isInstance(instance)) {
            return (T)((Channel)toObjectClass.cast(instance));
        }
        String cleanedClassName = instance.getClass().getSimpleName().replace("Impl", "");
        throw new IllegalStateException(Helpers.format("Cannot convert channel of type %s to %s!", cleanedClassName, toObjectClass.getSimpleName()));
    }

    public static int compare(GuildChannel a, GuildChannel b) {
        Category otherParent;
        ThreadChannel otherThread;
        Checks.notNull(b, "Channel");
        ThreadChannel thisThread = a instanceof ThreadChannel ? (ThreadChannel)a : null;
        ThreadChannel threadChannel = otherThread = b instanceof ThreadChannel ? (ThreadChannel)b : null;
        if (thisThread != null && otherThread == null) {
            if (thisThread.getParentChannel().getIdLong() == b.getIdLong()) {
                return 1;
            }
            return thisThread.getParentChannel().compareTo(b);
        }
        if (thisThread == null && otherThread != null) {
            if (otherThread.getParentChannel().getIdLong() == a.getIdLong()) {
                return -1;
            }
            return a.compareTo(otherThread.getParentChannel());
        }
        if (thisThread != null) {
            if (thisThread.getParentChannel().getIdLong() == otherThread.getParentChannel().getIdLong()) {
                return Long.compare(b.getIdLong(), a.getIdLong());
            }
            return thisThread.getParentChannel().compareTo(otherThread.getParentChannel());
        }
        Category thisParent = a instanceof ICategorizableChannel ? ((ICategorizableChannel)a).getParentCategory() : null;
        Category category = otherParent = b instanceof ICategorizableChannel ? ((ICategorizableChannel)b).getParentCategory() : null;
        if (thisParent != null && otherParent == null) {
            if (b instanceof Category) {
                if (b.getIdLong() == thisParent.getIdLong()) {
                    return 1;
                }
                return thisParent.compareTo(b);
            }
            return 1;
        }
        if (thisParent == null && otherParent != null) {
            if (a instanceof Category) {
                if (a.getIdLong() == otherParent.getIdLong()) {
                    return -1;
                }
                return a.compareTo(otherParent);
            }
            return -1;
        }
        if (thisParent != null && !thisParent.equals(otherParent)) {
            return thisParent.compareTo(otherParent);
        }
        if (a.getType().getSortBucket() != b.getType().getSortBucket()) {
            return Integer.compare(a.getType().getSortBucket(), b.getType().getSortBucket());
        }
        if (b instanceof IPositionableChannel && a instanceof IPositionableChannel) {
            IPositionableChannel oPositionableChannel = (IPositionableChannel)b;
            IPositionableChannel thisPositionableChannel = (IPositionableChannel)a;
            if (thisPositionableChannel.getPositionRaw() != oPositionableChannel.getPositionRaw()) {
                return Integer.compare(thisPositionableChannel.getPositionRaw(), oPositionableChannel.getPositionRaw());
            }
        }
        return Long.compareUnsigned(a.getIdLong(), b.getIdLong());
    }
}

