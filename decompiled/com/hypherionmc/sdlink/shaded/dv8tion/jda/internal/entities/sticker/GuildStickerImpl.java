/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ErrorResponseException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildStickerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.ErrorResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.RichStickerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.GuildStickerManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

public class GuildStickerImpl
extends RichStickerImpl
implements GuildSticker {
    private final long guildId;
    private final JDA jda;
    private Guild guild;
    private User owner;
    private boolean available;

    public GuildStickerImpl(long id, Sticker.StickerFormat format, String name, Set<String> tags, String description, boolean available, long guildId, JDA jda, User owner) {
        super(id, format, name, tags, description);
        this.available = available;
        this.guildId = guildId;
        this.jda = jda;
        this.guild = jda.getGuildById(guildId);
        this.owner = owner;
    }

    @Override
    @Nonnull
    public GuildSticker asGuildSticker() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return this.available;
    }

    @Override
    public long getGuildIdLong() {
        return this.guildId;
    }

    @Override
    @Nullable
    public Guild getGuild() {
        Guild realGuild = this.jda.getGuildById(this.guildId);
        if (realGuild != null) {
            this.guild = realGuild;
        }
        return this.guild;
    }

    @Override
    @Nullable
    public User getOwner() {
        User realOwner;
        if (this.owner != null && (realOwner = this.jda.getUserById(this.owner.getIdLong())) != null) {
            this.owner = realOwner;
        }
        return this.owner;
    }

    @Override
    @Nonnull
    public CacheRestAction<User> retrieveOwner() {
        Guild g = this.getGuild();
        if (g != null && !g.getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS)) {
            throw new InsufficientPermissionException(g, Permission.MANAGE_GUILD_EXPRESSIONS);
        }
        return new DeferredRestAction<User, RestActionImpl>(this.jda, User.class, this::getOwner, () -> {
            Route.CompiledRoute route = Route.Stickers.GET_GUILD_STICKER.compile(this.getGuildId(), this.getId());
            return new RestActionImpl<User>(this.jda, route, (response, request) -> {
                DataObject json = response.getObject();
                this.owner = json.optObject("user").map(user -> ((JDAImpl)this.jda).getEntityBuilder().createUser(json.getObject("user"))).orElseThrow(() -> ErrorResponseException.create(ErrorResponse.MISSING_PERMISSIONS, response));
                return this.owner;
            });
        });
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        if (this.guild != null) {
            return this.guild.deleteSticker(this);
        }
        Route.CompiledRoute route = Route.Stickers.DELETE_GUILD_STICKER.compile(this.getGuildId(), this.getId());
        return new AuditableRestActionImpl<Void>(this.jda, route);
    }

    @Override
    @Nonnull
    public GuildStickerManager getManager() {
        return new GuildStickerManagerImpl(this.getGuild(), this.getGuildIdLong(), this);
    }

    public GuildStickerImpl setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    public GuildStickerImpl copy() {
        return new GuildStickerImpl(this.id, this.format, this.name, this.tags, this.description, this.available, this.guildId, this.jda, this.owner);
    }

    @Override
    public String toString() {
        return new EntityString(this).setName(this.name).addMetadata("guild", this.getGuildId()).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.format, this.name, this.getType(), this.tags, this.description, this.available, this.guildId});
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GuildStickerImpl)) {
            return false;
        }
        GuildStickerImpl other = (GuildStickerImpl)obj;
        return this.id == other.id && this.format == other.format && this.getType() == other.getType() && this.available == other.available && this.guildId == other.guildId && Objects.equals(this.name, other.name) && Objects.equals(this.description, other.description) && Helpers.deepEqualsUnordered(this.tags, other.tags);
    }
}

