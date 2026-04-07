/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod.AutoModRuleCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod.AutoModRuleDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.automod.AutoModRuleUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.automod.AutoModRuleImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class AutoModRuleHandler
extends SocketHandler {
    private final String type;

    public AutoModRuleHandler(JDAImpl api, String type) {
        super(api);
        this.type = type;
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getUnsignedLong("guild_id");
        if (this.api.getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        Guild guild = this.api.getGuildById(guildId);
        if (guild == null) {
            this.api.getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received a AUTO_MODERATION_RULE_{} for a guild that is not yet cached. JSON: {}", (Object)this.type, (Object)content);
            return null;
        }
        AutoModRuleImpl rule = AutoModRuleImpl.fromData(guild, content);
        switch (this.type) {
            case "CREATE": {
                this.api.handleEvent(new AutoModRuleCreateEvent(this.api, this.responseNumber, rule));
                break;
            }
            case "UPDATE": {
                this.api.handleEvent(new AutoModRuleUpdateEvent(this.api, this.responseNumber, rule));
                break;
            }
            case "DELETE": {
                this.api.handleEvent(new AutoModRuleDeleteEvent(this.api, this.responseNumber, rule));
            }
        }
        return null;
    }
}

