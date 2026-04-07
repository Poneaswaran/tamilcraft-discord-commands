/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ErrorResponseException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.CustomEmojiManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.ErrorResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.CustomEmojiManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RichCustomEmojiImpl
implements RichCustomEmoji,
EmojiUnion {
    private final long id;
    private final JDAImpl api;
    private final Set<Role> roles;
    private GuildImpl guild;
    private boolean managed = false;
    private boolean available = true;
    private boolean animated = false;
    private String name;
    private User owner;

    public RichCustomEmojiImpl(long id, GuildImpl guild) {
        this.id = id;
        this.api = guild.getJDA();
        this.guild = guild;
        this.roles = ConcurrentHashMap.newKeySet();
    }

    @Override
    @Nonnull
    public Emoji.Type getType() {
        return Emoji.Type.CUSTOM;
    }

    @Override
    @Nonnull
    public String getAsReactionCode() {
        return this.name + ":" + this.id;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return DataObject.empty().put("name", this.name).put("animated", this.animated).put("id", this.id);
    }

    @Override
    @Nonnull
    public GuildImpl getGuild() {
        GuildImpl realGuild = (GuildImpl)this.api.getGuildById(this.guild.getIdLong());
        if (realGuild != null) {
            this.guild = realGuild;
        }
        return this.guild;
    }

    @Override
    @Nonnull
    public List<Role> getRoles() {
        return Collections.unmodifiableList(new ArrayList<Role>(this.roles));
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isManaged() {
        return this.managed;
    }

    @Override
    public boolean isAvailable() {
        return this.available;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public JDAImpl getJDA() {
        return this.api;
    }

    @Override
    public User getOwner() {
        return this.owner;
    }

    @Override
    @Nonnull
    public CacheRestAction<User> retrieveOwner() {
        GuildImpl guild = this.getGuild();
        if (!guild.getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS)) {
            throw new InsufficientPermissionException(guild, Permission.MANAGE_GUILD_EXPRESSIONS);
        }
        return new DeferredRestAction<User, RestActionImpl>(this.api, User.class, this::getOwner, () -> {
            Route.CompiledRoute route = Route.Emojis.GET_EMOJI.compile(guild.getId(), this.getId());
            return new RestActionImpl<User>((JDA)this.api, route, (response, request) -> {
                DataObject data = response.getObject();
                if (data.isNull("user")) {
                    throw ErrorResponseException.create(ErrorResponse.MISSING_PERMISSIONS, response);
                }
                DataObject user = data.getObject("user");
                this.owner = this.api.getEntityBuilder().createUser(user);
                return this.owner;
            });
        });
    }

    @Override
    @Nonnull
    public CustomEmojiManager getManager() {
        return new CustomEmojiManagerImpl(this);
    }

    @Override
    public boolean isAnimated() {
        return this.animated;
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        if (this.managed) {
            throw new UnsupportedOperationException("You cannot delete a managed emoji!");
        }
        if (!this.getGuild().getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS)) {
            throw new InsufficientPermissionException(this.getGuild(), Permission.MANAGE_GUILD_EXPRESSIONS);
        }
        Route.CompiledRoute route = Route.Emojis.DELETE_EMOJI.compile(this.getGuild().getId(), this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    public RichCustomEmojiImpl setName(String name) {
        this.name = name;
        return this;
    }

    public RichCustomEmojiImpl setAnimated(boolean animated) {
        this.animated = animated;
        return this;
    }

    public RichCustomEmojiImpl setManaged(boolean val) {
        this.managed = val;
        return this;
    }

    public RichCustomEmojiImpl setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    public RichCustomEmojiImpl setOwner(User user) {
        this.owner = user;
        return this;
    }

    public Set<Role> getRoleSet() {
        return this.roles;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CustomEmoji)) {
            return false;
        }
        CustomEmoji other = (CustomEmoji)obj;
        return this.id == other.getIdLong();
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }

    public RichCustomEmojiImpl copy() {
        RichCustomEmojiImpl copy = new RichCustomEmojiImpl(this.id, this.getGuild()).setOwner(this.owner).setManaged(this.managed).setAnimated(this.animated).setName(this.name);
        copy.roles.addAll(this.roles);
        return copy;
    }

    @Override
    @Nonnull
    public UnicodeEmoji asUnicode() {
        throw new IllegalStateException("Cannot convert CustomEmoji to UnicodeEmoji!");
    }

    @Override
    @Nonnull
    public CustomEmoji asCustom() {
        return this;
    }

    @Override
    @Nonnull
    public RichCustomEmoji asRich() {
        return this;
    }

    @Override
    @Nonnull
    public ApplicationEmoji asApplication() {
        throw new IllegalStateException("Cannot convert RichCustomEmoji to ApplicationEmoji!");
    }
}

