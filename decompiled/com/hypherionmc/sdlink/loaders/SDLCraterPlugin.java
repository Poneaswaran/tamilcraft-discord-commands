/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.loader.CraterLoader
 *  com.hypherionmc.craterlib.api.loader.plugins.entrypoints.CraterPlugin
 *  com.hypherionmc.craterlib.core.event.CraterEventBus
 */
package com.hypherionmc.sdlink.loaders;

import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.api.loader.plugins.entrypoints.CraterPlugin;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.sdlink.client.ClientEvents;
import com.hypherionmc.sdlink.compat.MModeCompat;
import com.hypherionmc.sdlink.compat.rolesync.impl.FTBRankSync;
import com.hypherionmc.sdlink.compat.rolesync.impl.LuckPermsSync;
import com.hypherionmc.sdlink.server.ServerEvents;

public class SDLCraterPlugin
implements CraterPlugin {
    public void onLoadClient() {
        ClientEvents.init();
    }

    public void onLoadServer() {
        ServerEvents events = ServerEvents.getInstance();
        CraterEventBus.INSTANCE.registerEventListener((Object)events);
        if (CraterLoader.isModLoaded((String)"mmode")) {
            MModeCompat.init();
        }
        if (CraterLoader.isModLoaded((String)"ftbranks")) {
            CraterEventBus.INSTANCE.registerEventListener((Object)FTBRankSync.INSTANCE);
        }
        if (CraterLoader.isModLoaded((String)"luckperms")) {
            CraterEventBus.INSTANCE.registerEventListener((Object)LuckPermsSync.INSTANCE);
        }
    }

    public String getPluginId() {
        return "";
    }
}

