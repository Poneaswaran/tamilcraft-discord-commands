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

public final class VanishCompat {
    @Path(value="sendFakeJoinLeaveMessage")
    @SpecComment(value="Should Fake Join/Leave message be sent when players vanish/unvanish")
    public boolean sendFakeJoinLeaveMessage = true;
}

