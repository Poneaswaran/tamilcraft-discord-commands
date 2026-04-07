/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 */
package com.hypherionmc.sdlink.core.config.impl.compat;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;

public final class CommonCompat {
    @Path(value="vanish")
    @SpecComment(value="Should SDLink integrate with Vanish Mod")
    public boolean vanish = true;
    @Path(value="ftbessentials")
    @SpecComment(value="Should SDLink integrate with FTB Essentials")
    public boolean ftbessentials = true;
    @Path(value="ftbranks")
    @SpecComment(value="Should SDLink integrate with FTB Ranks")
    public boolean ftbranks = true;
    @Path(value="luckperms")
    @SpecComment(value="Should SDLink integrate with Luckperms (Group Syncing only)")
    public boolean luckperms = true;
    @Path(value="playerroles")
    @SpecComment(value="Should SDLink integrate with Player Roles")
    public boolean playerroles = true;
    @Path(value="cobblemonguilds")
    @SpecComment(value="Should SDLink integrate with Cobblemon Guilds")
    public boolean cobblemonguilds = true;
    @Path(value="ftbteams_chat")
    @SpecComment(value="Should chats from the /ftbteams chat command be relayed to discord")
    public boolean ftbteams_chat = false;
}

