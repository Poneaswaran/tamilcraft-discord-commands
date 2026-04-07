/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.Interaction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public class GenericInteractionCreateEvent
extends Event
implements Interaction {
    private final Interaction interaction;

    public GenericInteractionCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Interaction interaction) {
        super(api, responseNumber);
        this.interaction = interaction;
    }

    @Nonnull
    public Interaction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public String getToken() {
        return this.interaction.getToken();
    }

    @Override
    public int getTypeRaw() {
        return this.interaction.getTypeRaw();
    }

    @Override
    @Nullable
    public Guild getGuild() {
        return this.interaction.getGuild();
    }

    @Override
    @Nullable
    public Channel getChannel() {
        return this.interaction.getChannel();
    }

    @Override
    public long getChannelIdLong() {
        return this.interaction.getChannelIdLong();
    }

    @Override
    @Nonnull
    public DiscordLocale getUserLocale() {
        return this.interaction.getUserLocale();
    }

    @Override
    @Nonnull
    public DiscordLocale getGuildLocale() {
        return this.interaction.getGuildLocale();
    }

    @Override
    @Nullable
    public Member getMember() {
        return this.interaction.getMember();
    }

    @Override
    @Nonnull
    public User getUser() {
        return this.interaction.getUser();
    }

    @Override
    @Nonnull
    public List<Entitlement> getEntitlements() {
        return this.interaction.getEntitlements();
    }

    @Override
    public long getIdLong() {
        return this.interaction.getIdLong();
    }

    @Override
    public boolean isAcknowledged() {
        return this.interaction.isAcknowledged();
    }
}

