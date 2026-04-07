/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.config.impl;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public final class TriggerCommandsConfig {
    @Path(value="enabled")
    @SpecComment(value="Should any of the below commands be executed when a role changes")
    public boolean enabled = false;
    @Path(value="roleAdded")
    @SpecComment(value="Commands to run when roles are added")
    public List<TriggerHolder> roleAdded = new ArrayList<TriggerHolder>();
    @Path(value="roleRemoved")
    @SpecComment(value="Commands to run when roles are removed")
    public List<TriggerHolder> roleRemoved = new ArrayList<TriggerHolder>();

    public static class TriggerHolder {
        @Path(value="discordRole")
        public String discordRole = "";
        @Path(value="minecraftCommand")
        public List<String> minecraftCommand = new ArrayList<String>();

        @Generated
        public TriggerHolder() {
        }

        @Generated
        private TriggerHolder(String discordRole, List<String> minecraftCommand) {
            this.discordRole = discordRole;
            this.minecraftCommand = minecraftCommand;
        }

        @Generated
        public static TriggerHolder of(String discordRole, List<String> minecraftCommand) {
            return new TriggerHolder(discordRole, minecraftCommand);
        }
    }
}

