/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.config.impl.compat;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public final class RoleSyncCompat {
    @Path(value="syncToMinecraft")
    @SpecComment(value="Sync Groups/Ranks to Minecraft from Discord Roles")
    public boolean syncToMinecraft = false;
    @Path(value="syncToDiscord")
    @SpecComment(value="Sync Groups/Ranks to Discord roles from Minecraft")
    public boolean syncToDiscord = false;
    @Path(value="syncs")
    @SpecComment(value="List of Ranks and Roles that will be synced. Check the wiki on how to configure this")
    public List<Sync> syncs = new ArrayList<Sync>();

    public static class Sync {
        public String rank;
        public String role;

        @Generated
        private Sync(String rank, String role) {
            this.rank = rank;
            this.role = role;
        }

        @Generated
        public static Sync of(String rank, String role) {
            return new Sync(rank, role);
        }

        @Generated
        public Sync() {
        }
    }
}

