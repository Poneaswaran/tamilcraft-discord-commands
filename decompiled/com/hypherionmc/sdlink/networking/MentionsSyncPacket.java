/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.client.mentions.MentionsController
 *  com.hypherionmc.craterlib.api.game.nbt.CraterDataTag
 *  com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf
 *  com.hypherionmc.craterlib.api.game.resources.CraterIdentifier
 *  com.hypherionmc.craterlib.core.networking.data.PacketContext
 *  com.hypherionmc.craterlib.core.networking.data.PacketSide
 */
package com.hypherionmc.sdlink.networking;

import com.hypherionmc.craterlib.api.client.mentions.MentionsController;
import com.hypherionmc.craterlib.api.game.nbt.CraterDataTag;
import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.sdlink.client.ClientEvents;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import java.util.HashMap;

public final class MentionsSyncPacket {
    public static final CraterIdentifier CHANNEL = CraterIdentifier.fromGame((String)"sdlink", (String)"syncpacket");
    private HashMap<String, String> roles;
    private HashMap<String, String> channelHashMap;
    private HashMap<String, String> users;
    private boolean mentionsEnabled = false;

    public MentionsSyncPacket() {
    }

    public MentionsSyncPacket(HashMap<String, String> roles, HashMap<String, String> channels, HashMap<String, String> users) {
        this.roles = roles;
        this.channelHashMap = channels;
        this.users = users;
    }

    public static MentionsSyncPacket decode(CraterFriendlyByteBuf buf) {
        MentionsSyncPacket p = new MentionsSyncPacket();
        CraterDataTag tag = buf.readNbt();
        if (tag == null) {
            return p;
        }
        CraterDataTag rolesTag = tag.getCompound("roles");
        CraterDataTag channelsTag = tag.getCompound("channels");
        CraterDataTag usersTag = tag.getCompound("users");
        p.roles = new HashMap();
        rolesTag.getAllKeys().forEach(k -> p.roles.put((String)k, rolesTag.getString(k)));
        p.channelHashMap = new HashMap();
        channelsTag.getAllKeys().forEach(k -> p.channelHashMap.put((String)k, channelsTag.getString(k)));
        p.users = new HashMap();
        usersTag.getAllKeys().forEach(k -> p.users.put((String)k, usersTag.getString(k)));
        p.mentionsEnabled = tag.getBoolean("mentionsenabled");
        return p;
    }

    public void write(CraterFriendlyByteBuf friendlyByteBuf) {
        CraterDataTag tag = CraterDataTag.empty();
        CraterDataTag rolesTag = CraterDataTag.empty();
        CraterDataTag channelsTag = CraterDataTag.empty();
        CraterDataTag usersTag = CraterDataTag.empty();
        this.roles.forEach((arg_0, arg_1) -> ((CraterDataTag)rolesTag).putString(arg_0, arg_1));
        this.channelHashMap.forEach((arg_0, arg_1) -> ((CraterDataTag)channelsTag).putString(arg_0, arg_1));
        this.users.forEach((arg_0, arg_1) -> ((CraterDataTag)usersTag).putString(arg_0, arg_1));
        tag.put("roles", rolesTag);
        tag.put("channels", channelsTag);
        tag.put("users", usersTag);
        tag.putBoolean("mentionsenabled", SDLinkConfig.INSTANCE.chatConfig.allowMentionsFromChat);
        friendlyByteBuf.writeNbt(tag);
    }

    public static void handle(PacketContext<MentionsSyncPacket> ctx) {
        if (PacketSide.CLIENT.equals((Object)ctx.side())) {
            MentionsSyncPacket p = (MentionsSyncPacket)ctx.message();
            if (p.roles != null && !p.roles.isEmpty()) {
                CraterIdentifier rrl = CraterIdentifier.fromGame((String)"sdlink:roles");
                MentionsController.registerMention((CraterIdentifier)rrl, p.roles.keySet(), currentWord -> currentWord.startsWith("[@") || currentWord.startsWith("@"));
            }
            if (p.channelHashMap != null && !p.channelHashMap.isEmpty()) {
                CraterIdentifier crl = CraterIdentifier.fromGame((String)"sdlink:channels");
                MentionsController.registerMention((CraterIdentifier)crl, p.channelHashMap.keySet(), currentWord -> currentWord.startsWith("[#") || currentWord.startsWith("#"));
            }
            if (p.users != null && !p.users.isEmpty()) {
                CraterIdentifier url = CraterIdentifier.fromGame((String)"sdlink:users");
                MentionsController.registerMention((CraterIdentifier)url, p.users.keySet(), currentWord -> currentWord.startsWith("[@") || currentWord.startsWith("@"));
            }
            ClientEvents.mentionsEnabled = p.mentionsEnabled;
        }
    }
}

