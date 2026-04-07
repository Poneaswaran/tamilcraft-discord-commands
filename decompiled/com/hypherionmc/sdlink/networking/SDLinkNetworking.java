/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.resources.CraterIdentifier
 *  com.hypherionmc.craterlib.core.networking.CraterPacketNetwork
 */
package com.hypherionmc.sdlink.networking;

import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.sdlink.networking.MentionsSyncPacket;

public final class SDLinkNetworking {
    public static void registerPackets() {
        CraterPacketNetwork.registerPacket((CraterIdentifier)MentionsSyncPacket.CHANNEL, MentionsSyncPacket.class, MentionsSyncPacket::write, MentionsSyncPacket::decode, MentionsSyncPacket::handle);
    }
}

