/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.experimental;

import com.hypherionmc.sdlink.core.discord.BotController;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import lombok.Generated;

public final class ExperimentalFeatures {
    public static final ExperimentalFeatures INSTANCE = new ExperimentalFeatures();
    public boolean RELAY_SERVER = false;

    public void loadFeatures() {
        File f = new File("./sdlinkstorage/IKNOWWHATIMDOING");
        if (!f.exists()) {
            return;
        }
        try {
            Properties props = new Properties();
            props.load(new FileReader(f));
            this.RELAY_SERVER = Boolean.parseBoolean(props.getProperty("RELAY_SERVER", "false"));
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to load Experimental Features", (Throwable)e);
        }
    }

    @Generated
    private ExperimentalFeatures() {
    }
}

