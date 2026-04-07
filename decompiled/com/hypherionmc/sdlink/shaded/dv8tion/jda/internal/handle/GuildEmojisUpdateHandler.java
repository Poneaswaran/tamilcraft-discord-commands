/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.CollectionUtils;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.EmojiAddedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.EmojiRemovedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update.EmojiUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update.EmojiUpdateRolesEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.RichCustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GuildEmojisUpdateHandler
extends SocketHandler {
    public GuildEmojisUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        ArrayList<RichCustomEmojiImpl> newEmojis;
        ArrayList oldEmojis;
        if (!this.getJDA().isCacheFlagSet(CacheFlag.EMOJI)) {
            return null;
        }
        long guildId = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        if (guild == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
            return null;
        }
        DataArray array = content.getArray("emojis");
        SnowflakeCacheViewImpl<RichCustomEmoji> emojiView = guild.getEmojisView();
        try (UnlockHook hook = emojiView.writeLock();){
            TLongObjectMap tLongObjectMap = emojiView.getMap();
            oldEmojis = new ArrayList(tLongObjectMap.valueCollection());
            newEmojis = new ArrayList<RichCustomEmojiImpl>();
            for (int i = 0; i < array.length(); ++i) {
                DataObject current = array.getObject(i);
                long emojiId = current.getLong("id");
                RichCustomEmojiImpl emoji = (RichCustomEmojiImpl)tLongObjectMap.get(emojiId);
                RichCustomEmojiImpl oldEmoji = null;
                if (emoji == null) {
                    emoji = new RichCustomEmojiImpl(emojiId, guild);
                    newEmojis.add(emoji);
                } else {
                    oldEmojis.remove(emoji);
                    oldEmoji = emoji.copy();
                }
                emoji.setName(current.getString("name")).setAnimated(current.getBoolean("animated")).setManaged(current.getBoolean("managed"));
                DataArray roles = current.getArray("roles");
                Set<Role> newRoles = emoji.getRoleSet();
                HashSet<Role> oldRoles = new HashSet<Role>(newRoles);
                for (int j = 0; j < roles.length(); ++j) {
                    Role role = guild.getRoleById(roles.getString(j));
                    if (role == null) continue;
                    newRoles.add(role);
                    oldRoles.remove(role);
                }
                for (Role r : oldRoles) {
                    newRoles.remove(r);
                }
                tLongObjectMap.put(emoji.getIdLong(), emoji);
                this.handleReplace(oldEmoji, emoji);
            }
            for (RichCustomEmoji e : oldEmojis) {
                tLongObjectMap.remove(e.getIdLong());
            }
        }
        for (RichCustomEmoji richCustomEmoji : oldEmojis) {
            this.getJDA().handleEvent(new EmojiRemovedEvent(this.getJDA(), this.responseNumber, richCustomEmoji));
        }
        for (RichCustomEmoji richCustomEmoji : newEmojis) {
            this.getJDA().handleEvent(new EmojiAddedEvent(this.getJDA(), this.responseNumber, richCustomEmoji));
        }
        return null;
    }

    private void handleReplace(RichCustomEmoji oldEmoji, RichCustomEmoji newEmoji) {
        if (oldEmoji == null || newEmoji == null) {
            return;
        }
        if (!Objects.equals(oldEmoji.getName(), newEmoji.getName())) {
            this.getJDA().handleEvent(new EmojiUpdateNameEvent(this.getJDA(), this.responseNumber, newEmoji, oldEmoji.getName()));
        }
        if (!CollectionUtils.isEqualCollection(oldEmoji.getRoles(), newEmoji.getRoles())) {
            this.getJDA().handleEvent(new EmojiUpdateRolesEvent(this.getJDA(), this.responseNumber, newEmoji, oldEmoji.getRoles()));
        }
    }
}

