/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.order;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.order.ChannelOrderActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.stream.Collectors;

public class CategoryOrderActionImpl
extends ChannelOrderActionImpl
implements CategoryOrderAction {
    protected final Category category;

    public CategoryOrderActionImpl(Category category, int bucket) {
        super(category.getGuild(), bucket, CategoryOrderActionImpl.getChannelsOfType(category, bucket));
        this.category = category;
    }

    @Override
    @Nonnull
    public Category getCategory() {
        return this.category;
    }

    @Override
    protected void validateInput(GuildChannel entity) {
        Checks.notNull(entity, "Provided channel");
        Checks.check(entity instanceof ICategorizableChannel, "Provided channel is not an ICategorizableChannel");
        Checks.check(this.getCategory().equals(((ICategorizableChannel)entity).getParentCategory()), "Provided channel's Category is not this Category!");
        Checks.check(this.orderList.contains(entity), "Provided channel is not in the list of orderable channels!");
    }

    @Nonnull
    private static Collection<GuildChannel> getChannelsOfType(Category category, int bucket) {
        Checks.notNull(category, "Category");
        return ChannelOrderActionImpl.getChannelsOfType(category.getGuild(), bucket).stream().filter(ICategorizableChannel.class::isInstance).map(ICategorizableChannel.class::cast).filter(it -> category.equals(it.getParentCategory())).sorted().collect(Collectors.toList());
    }
}

