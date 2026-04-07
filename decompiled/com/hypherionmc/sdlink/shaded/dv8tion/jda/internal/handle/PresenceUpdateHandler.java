/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ClientType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.UserActivityEndEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.UserActivityStartEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateActivitiesEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateActivityOrderEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberPresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.slf4j.Logger;

public class PresenceUpdateHandler
extends SocketHandler {
    private static final Logger log = JDALogger.getLog(PresenceUpdateHandler.class);

    public PresenceUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        if (content.isNull("guild_id")) {
            log.debug("Received PRESENCE_UPDATE without guild_id. Ignoring event.");
            return null;
        }
        if (this.api.getCacheFlags().stream().noneMatch(CacheFlag::isPresence)) {
            return null;
        }
        long guildId = content.getUnsignedLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        if (guild == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received a PRESENCE_UPDATE for a guild that is not yet cached! GuildId:{} UserId: {}", (Object)guildId, content.getObject("user").get("id"));
            return null;
        }
        CacheView.SimpleCacheView<MemberPresenceImpl> presences = guild.getPresenceView();
        if (presences == null) {
            return null;
        }
        DataObject jsonUser = content.getObject("user");
        long userId = jsonUser.getUnsignedLong("id");
        MemberImpl member = (MemberImpl)guild.getMemberById(userId);
        MemberPresenceImpl presence = (MemberPresenceImpl)presences.get(userId);
        OnlineStatus status = OnlineStatus.fromKey(content.getString("status"));
        if (status == OnlineStatus.OFFLINE) {
            presences.remove(userId);
        }
        if (presence == null) {
            presence = new MemberPresenceImpl();
            if (status != OnlineStatus.OFFLINE) {
                try (UnlockHook lock = presences.writeLock();){
                    presences.getMap().put(userId, presence);
                }
            }
        }
        DataArray activityArray = !this.getJDA().isCacheFlagSet(CacheFlag.ACTIVITY) || content.isNull("activities") ? null : content.getArray("activities");
        ArrayList<Activity> newActivities = new ArrayList<Activity>();
        boolean parsedActivity = this.parseActivities(userId, activityArray, newActivities);
        if (this.getJDA().isCacheFlagSet(CacheFlag.CLIENT_STATUS) && !content.isNull("client_status")) {
            this.handleClientStatus(content, presence);
        }
        if (parsedActivity) {
            this.handleActivities(newActivities, member, presence);
        }
        if (presence.getOnlineStatus() != status) {
            OnlineStatus oldStatus = presence.getOnlineStatus();
            presence.setOnlineStatus(status);
            if (member != null) {
                this.getJDA().getEntityBuilder().updateMemberCache(member);
                this.getJDA().handleEvent(new UserUpdateOnlineStatusEvent(this.getJDA(), this.responseNumber, member, oldStatus));
            }
        }
        return null;
    }

    private boolean parseActivities(long userId, DataArray activityArray, List<Activity> newActivities) {
        boolean parsedActivity = false;
        try {
            if (activityArray != null) {
                for (int i = 0; i < activityArray.length(); ++i) {
                    newActivities.add(EntityBuilder.createActivity(activityArray.getObject(i)));
                }
                parsedActivity = true;
            }
        }
        catch (Exception ex) {
            if (EntityBuilder.LOG.isDebugEnabled()) {
                EntityBuilder.LOG.warn("Encountered exception trying to parse a presence! UserID: {} JSON: {}", new Object[]{userId, activityArray, ex});
            }
            EntityBuilder.LOG.warn("Encountered exception trying to parse a presence! UserID: {} Message: {} Enable debug for details", (Object)userId, (Object)ex.getMessage());
        }
        return parsedActivity;
    }

    private void handleActivities(List<Activity> newActivities, @Nullable MemberImpl member, MemberPresenceImpl presence) {
        List<Activity> oldActivities = presence.getActivities();
        presence.setActivities(newActivities);
        if (member == null) {
            return;
        }
        boolean unorderedEquals = Helpers.deepEqualsUnordered(oldActivities, newActivities);
        if (unorderedEquals) {
            boolean deepEquals = Helpers.deepEquals(oldActivities, newActivities);
            if (!deepEquals) {
                this.getJDA().handleEvent(new UserUpdateActivityOrderEvent(this.getJDA(), this.responseNumber, oldActivities, member));
            }
        } else {
            this.getJDA().getEntityBuilder().updateMemberCache(member);
            ArrayList<Activity> stoppedActivities = new ArrayList<Activity>(oldActivities);
            ArrayList<Activity> startedActivities = new ArrayList<Activity>();
            for (Activity activity : newActivities) {
                if (stoppedActivities.remove(activity)) continue;
                startedActivities.add(activity);
            }
            for (Activity activity : startedActivities) {
                this.getJDA().handleEvent(new UserActivityStartEvent(this.getJDA(), this.responseNumber, member, activity));
            }
            for (Activity activity : stoppedActivities) {
                this.getJDA().handleEvent(new UserActivityEndEvent(this.getJDA(), this.responseNumber, member, activity));
            }
            this.getJDA().handleEvent(new UserUpdateActivitiesEvent(this.getJDA(), this.responseNumber, member, oldActivities));
        }
    }

    private void handleClientStatus(DataObject content, MemberPresenceImpl presence) {
        DataObject json = content.getObject("client_status");
        EnumSet<ClientType> types = EnumSet.of(ClientType.UNKNOWN);
        for (String key : json.keys()) {
            ClientType type = ClientType.fromKey(key);
            types.add(type);
            String raw = String.valueOf(json.get(key));
            OnlineStatus clientStatus = OnlineStatus.fromKey(raw);
            presence.setOnlineStatus(type, clientStatus);
        }
        for (ClientType type : EnumSet.complementOf(types)) {
            presence.setOnlineStatus(type, null);
        }
    }
}

