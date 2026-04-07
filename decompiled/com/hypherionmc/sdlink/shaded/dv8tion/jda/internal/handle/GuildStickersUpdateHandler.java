/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.CollectionUtils;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.GuildStickerAddedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.GuildStickerRemovedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateAvailableEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateDescriptionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateTagsEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.GuildStickerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import java.util.ArrayList;
import java.util.Objects;

public class GuildStickersUpdateHandler
extends SocketHandler {
    public GuildStickersUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        ArrayList<GuildStickerImpl> newStickers;
        ArrayList oldStickers;
        if (!this.getJDA().isCacheFlagSet(CacheFlag.STICKER)) {
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
        DataArray array = content.getArray("stickers");
        SnowflakeCacheViewImpl<GuildSticker> stickersView = guild.getStickersView();
        EntityBuilder builder = this.api.getEntityBuilder();
        try (UnlockHook hook = stickersView.writeLock();){
            TLongObjectMap tLongObjectMap = stickersView.getMap();
            oldStickers = new ArrayList(tLongObjectMap.valueCollection());
            newStickers = new ArrayList<GuildStickerImpl>();
            for (int i = 0; i < array.length(); ++i) {
                DataObject current = array.getObject(i);
                long stickerId = current.getLong("id");
                GuildStickerImpl sticker = (GuildStickerImpl)tLongObjectMap.get(stickerId);
                GuildStickerImpl oldSticker = null;
                if (sticker == null) {
                    sticker = (GuildStickerImpl)builder.createRichSticker(current);
                    newStickers.add(sticker);
                } else {
                    oldStickers.remove(sticker);
                    oldSticker = sticker.copy();
                }
                sticker.setName(current.getString("name"));
                sticker.setAvailable(current.getBoolean("available"));
                sticker.setDescription(current.getString("description", ""));
                sticker.setTags(Helpers.setOf(current.getString("tags").split(",\\s*")));
                tLongObjectMap.put(sticker.getIdLong(), sticker);
                this.handleReplace(guild, oldSticker, sticker);
            }
            for (GuildSticker e : oldStickers) {
                tLongObjectMap.remove(e.getIdLong());
            }
        }
        for (GuildSticker guildSticker : oldStickers) {
            this.getJDA().handleEvent(new GuildStickerRemovedEvent(this.getJDA(), this.responseNumber, guild, guildSticker));
        }
        for (GuildSticker guildSticker : newStickers) {
            this.getJDA().handleEvent(new GuildStickerAddedEvent(this.getJDA(), this.responseNumber, guild, guildSticker));
        }
        return null;
    }

    private void handleReplace(Guild guild, GuildStickerImpl oldSticker, GuildStickerImpl newSticker) {
        if (oldSticker == null || newSticker == null) {
            return;
        }
        if (!Objects.equals(oldSticker.getName(), newSticker.getName())) {
            this.getJDA().handleEvent(new GuildStickerUpdateNameEvent(this.getJDA(), this.responseNumber, guild, newSticker, oldSticker.getName()));
        }
        if (!Objects.equals(oldSticker.getDescription(), newSticker.getDescription())) {
            this.getJDA().handleEvent(new GuildStickerUpdateDescriptionEvent(this.getJDA(), this.responseNumber, guild, newSticker, oldSticker.getDescription()));
        }
        if (oldSticker.isAvailable() != newSticker.isAvailable()) {
            this.getJDA().handleEvent(new GuildStickerUpdateAvailableEvent(this.getJDA(), this.responseNumber, guild, newSticker, oldSticker.isAvailable()));
        }
        if (!CollectionUtils.isEqualCollection(oldSticker.getTags(), newSticker.getTags())) {
            this.getJDA().handleEvent(new GuildStickerUpdateTagsEvent(this.getJDA(), this.responseNumber, guild, newSticker, oldSticker.getTags()));
        }
    }
}

