/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.DirectAudioController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class DirectAudioControllerImpl
implements DirectAudioController {
    private final JDAImpl api;

    public DirectAudioControllerImpl(JDAImpl api) {
        this.api = api;
    }

    @Override
    @Nonnull
    public JDAImpl getJDA() {
        return this.api;
    }

    @Override
    public void connect(@Nonnull AudioChannel channel) {
        Checks.notNull(channel, "Audio Channel");
        JDAImpl jda = this.getJDA();
        WebSocketClient client = jda.getClient();
        client.queueAudioConnect(channel);
    }

    @Override
    public void disconnect(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        JDAImpl jda = this.getJDA();
        WebSocketClient client = jda.getClient();
        client.queueAudioDisconnect(guild);
    }

    @Override
    public void reconnect(@Nonnull AudioChannel channel) {
        Checks.notNull(channel, "Audio Channel");
        JDAImpl jda = this.getJDA();
        WebSocketClient client = jda.getClient();
        client.queueAudioReconnect(channel);
    }

    public void update(Guild guild, AudioChannel channel) {
        Checks.notNull(guild, "Guild");
        JDAImpl jda = this.getJDA();
        WebSocketClient client = jda.getClient();
        client.updateAudioConnection(guild.getIdLong(), channel);
    }
}

