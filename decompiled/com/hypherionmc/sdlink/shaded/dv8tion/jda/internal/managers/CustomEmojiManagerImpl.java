/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.CustomEmojiManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.RichCustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomEmojiManagerImpl
extends ManagerBase<CustomEmojiManager>
implements CustomEmojiManager {
    protected final RichCustomEmojiImpl emoji;
    protected final List<String> roles = new ArrayList<String>();
    protected String name;

    public CustomEmojiManagerImpl(RichCustomEmojiImpl emoji) {
        super(emoji.getJDA(), Route.Emojis.MODIFY_EMOJI.compile(emoji.getGuild().getId(), emoji.getId()));
        this.emoji = emoji;
        if (CustomEmojiManagerImpl.isPermissionChecksEnabled()) {
            this.checkPermissions();
        }
    }

    @Override
    @Nonnull
    public RichCustomEmoji getEmoji() {
        return this.emoji;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManagerImpl reset(long fields) {
        super.reset(fields);
        if ((fields & 2L) == 2L) {
            this.withLock(this.roles, List::clear);
        }
        if ((fields & 1L) == 1L) {
            this.name = null;
        }
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManagerImpl reset(long ... fields) {
        super.reset(fields);
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManagerImpl reset() {
        super.reset();
        this.withLock(this.roles, List::clear);
        this.name = null;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManagerImpl setName(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.inRange(name, 2, 32, "Name");
        this.name = name;
        this.set |= 1L;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManagerImpl setRoles(Set<Role> roles) {
        if (roles == null) {
            this.withLock(this.roles, List::clear);
        } else {
            Checks.notNull(roles, "Roles");
            roles.forEach(role -> {
                Checks.notNull(role, "Roles");
                Checks.check(role.getGuild().equals(this.getGuild()), "Roles must all be from the same guild");
            });
            this.withLock(this.roles, list -> {
                list.clear();
                roles.stream().map(ISnowflake::getId).forEach(list::add);
            });
        }
        this.set |= 2L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        if (this.shouldUpdate(1L)) {
            object.put("name", this.name);
        }
        this.withLock(this.roles, list -> {
            if (this.shouldUpdate(2L)) {
                object.put("roles", DataArray.fromCollection(list));
            }
        });
        this.reset();
        return this.getRequestBody(object);
    }

    @Override
    protected boolean checkPermissions() {
        if (!this.getGuild().getSelfMember().hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS)) {
            throw new InsufficientPermissionException(this.getGuild(), Permission.MANAGE_GUILD_EXPRESSIONS);
        }
        return super.checkPermissions();
    }
}

