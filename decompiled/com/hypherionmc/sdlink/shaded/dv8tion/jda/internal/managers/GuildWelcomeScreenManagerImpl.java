/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildWelcomeScreen;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.GuildWelcomeScreenManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuildWelcomeScreenManagerImpl
extends ManagerBase<GuildWelcomeScreenManager>
implements GuildWelcomeScreenManager {
    private final Guild guild;
    protected boolean enabled;
    protected String description;
    protected final List<GuildWelcomeScreen.Channel> channels = new ArrayList<GuildWelcomeScreen.Channel>(5);

    public GuildWelcomeScreenManagerImpl(Guild guild) {
        super(guild.getJDA(), Route.Guilds.MODIFY_WELCOME_SCREEN.compile(guild.getId()));
        this.guild = guild;
        if (GuildWelcomeScreenManagerImpl.isPermissionChecksEnabled()) {
            this.checkPermissions();
        }
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public GuildWelcomeScreenManagerImpl reset(long fields) {
        super.reset(fields);
        if ((fields & 1L) == 1L) {
            this.enabled = false;
        }
        if ((fields & 2L) == 2L) {
            this.description = null;
        }
        if ((fields & 4L) == 4L) {
            this.channels.clear();
        }
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public GuildWelcomeScreenManagerImpl reset(long ... fields) {
        super.reset(fields);
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public GuildWelcomeScreenManagerImpl reset() {
        super.reset(7L);
        return this;
    }

    @Override
    @Nonnull
    public GuildWelcomeScreenManager setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.set |= 1L;
        return this;
    }

    @Override
    @Nonnull
    public GuildWelcomeScreenManager setDescription(@Nullable String description) {
        if (description != null) {
            Checks.notLonger(description, 140, "Description");
        }
        this.description = description;
        this.set |= 2L;
        return this;
    }

    @Override
    @Nonnull
    public List<GuildWelcomeScreen.Channel> getWelcomeChannels() {
        return Collections.unmodifiableList(this.channels);
    }

    @Override
    @Nonnull
    public GuildWelcomeScreenManager clearWelcomeChannels() {
        this.withLock(this.channels, List::clear);
        this.set |= 4L;
        return this;
    }

    @Override
    @Nonnull
    public GuildWelcomeScreenManager setWelcomeChannels(@Nonnull Collection<? extends GuildWelcomeScreen.Channel> channels) {
        Checks.noneNull(channels, "Welcome channels");
        Checks.check(channels.size() <= 5, "Cannot have more than %d welcome channels", (Object)5);
        this.withLock(this.channels, c -> {
            c.clear();
            c.addAll(channels);
        });
        this.set |= 4L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        if (this.shouldUpdate(1L)) {
            object.put("enabled", this.enabled);
        }
        if (this.shouldUpdate(2L)) {
            object.put("description", this.description);
        }
        this.withLock(this.channels, list -> {
            if (this.shouldUpdate(4L)) {
                object.put("welcome_channels", DataArray.fromCollection(list));
            }
        });
        this.reset();
        return this.getRequestBody(object);
    }

    @Override
    protected boolean checkPermissions() {
        if (!this.getGuild().getSelfMember().hasPermission(Permission.MANAGE_SERVER)) {
            throw new InsufficientPermissionException(this.getGuild(), Permission.MANAGE_SERVER);
        }
        return super.checkPermissions();
    }
}

