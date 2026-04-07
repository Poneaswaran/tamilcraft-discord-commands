/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.client;

import com.hypherionmc.sdlink.networking.SDLinkNetworking;

public final class ClientEvents {
    public static boolean mentionsEnabled = false;

    public static void init() {
        SDLinkNetworking.registerPackets();
    }
}

