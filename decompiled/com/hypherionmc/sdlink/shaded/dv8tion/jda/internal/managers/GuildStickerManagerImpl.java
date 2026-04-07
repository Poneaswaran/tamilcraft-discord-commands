/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildStickerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.Collection;

public class GuildStickerManagerImpl
extends ManagerBase<GuildStickerManager>
implements GuildStickerManager {
    private final Guild guild;
    private final long guildId;
    private String name;
    private String description;
    private String tags;

    public GuildStickerManagerImpl(Guild guild, long guildId, StickerSnowflake sticker) {
        super(guild.getJDA(), Route.Stickers.MODIFY_GUILD_STICKER.compile(Long.toUnsignedString(guildId), sticker.getId()));
        this.guild = guild;
        this.guildId = guildId;
        if (GuildStickerManagerImpl.isPermissionChecksEnabled()) {
            this.checkPermissions();
        }
    }

    @Override
    @Nullable
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    public long getGuildIdLong() {
        return this.guildId;
    }

    @Override
    @Nonnull
    public GuildStickerManagerImpl reset(long fields) {
        super.reset(fields);
        if ((fields & 1L) == 1L) {
            this.name = null;
        }
        if ((fields & 2L) == 2L) {
            this.description = null;
        }
        if ((fields & 4L) == 4L) {
            this.tags = null;
        }
        return this;
    }

    @Override
    @Nonnull
    public GuildStickerManagerImpl reset(long ... fields) {
        super.reset(fields);
        return this;
    }

    @Override
    @Nonnull
    public GuildStickerManagerImpl reset() {
        super.reset();
        this.name = null;
        this.description = null;
        this.tags = null;
        return this;
    }

    @Override
    @Nonnull
    public GuildStickerManager setName(@Nonnull String name) {
        Checks.inRange(name, 2, 30, "Name");
        this.name = name;
        this.set |= 1L;
        return this;
    }

    @Override
    @Nonnull
    public GuildStickerManager setDescription(@Nonnull String description) {
        Checks.inRange(description, 2, 100, "Description");
        this.description = description;
        this.set |= 2L;
        return this;
    }

    @Override
    @Nonnull
    public GuildStickerManager setTags(@Nonnull Collection<String> tags) {
        Checks.notEmpty(tags, "Tags");
        for (String tag : tags) {
            Checks.notEmpty(tag, "Tags");
        }
        String csv = String.join((CharSequence)",", tags);
        Checks.notLonger(csv, 200, "List of tags");
        this.tags = csv;
        this.set |= 4L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        if (this.shouldUpdate(1L)) {
            object.put("name", this.name);
        }
        if (this.shouldUpdate(2L)) {
            object.put("description", this.description);
        }
        if (this.shouldUpdate(4L)) {
            object.put("tags", this.tags);
        }
        this.reset();
        return this.getRequestBody(object);
    }

    @Override
    protected boolean checkPermissions() {
        if (this.guild != null && !this.guild.getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS)) {
            throw new InsufficientPermissionException(this.guild, Permission.MANAGE_GUILD_EXPRESSIONS);
        }
        return super.checkPermissions();
    }
}

