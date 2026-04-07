/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IInviteContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.InviteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.InviteActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IInviteContainerMixin<T extends IInviteContainerMixin<T>>
extends IInviteContainer,
GuildChannelMixin<T> {
    @Override
    @Nonnull
    default public InviteAction createInvite() {
        this.checkPermission(Permission.CREATE_INSTANT_INVITE);
        return new InviteActionImpl(this.getJDA(), this.getId());
    }

    @Override
    @Nonnull
    default public RestAction<List<Invite>> retrieveInvites() {
        this.checkPermission(Permission.MANAGE_CHANNEL);
        Route.CompiledRoute route = Route.Invites.GET_CHANNEL_INVITES.compile(this.getId());
        JDAImpl jda = (JDAImpl)this.getJDA();
        return new RestActionImpl<List<Invite>>((JDA)jda, route, (response, request) -> {
            EntityBuilder entityBuilder = jda.getEntityBuilder();
            DataArray array = response.getArray();
            ArrayList<Invite> invites = new ArrayList<Invite>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                invites.add(entityBuilder.createInvite(array.getObject(i)));
            }
            return Collections.unmodifiableList(invites);
        });
    }
}

