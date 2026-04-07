/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class GuildCreateHandler
extends SocketHandler {
    public GuildCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long id = content.getLong("id");
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(id);
        if (guild == null) {
            this.getJDA().getGuildSetupController().onCreate(id, content);
        }
        return null;
    }
}

