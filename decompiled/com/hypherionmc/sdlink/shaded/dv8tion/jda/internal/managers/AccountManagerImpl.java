/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AccountManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;

public class AccountManagerImpl
extends ManagerBase<AccountManager>
implements AccountManager {
    protected final SelfUser selfUser;
    protected String name;
    protected Icon avatar;
    protected Icon banner;

    public AccountManagerImpl(SelfUser selfUser) {
        super(selfUser.getJDA(), Route.Self.MODIFY_SELF.compile(new String[0]));
        this.selfUser = selfUser;
    }

    @Override
    @Nonnull
    public SelfUser getSelfUser() {
        return this.selfUser;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManagerImpl reset(long fields) {
        super.reset(fields);
        if ((fields & 2L) == 2L) {
            this.avatar = null;
        }
        if ((fields & 4L) == 4L) {
            this.banner = null;
        }
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManagerImpl reset(long ... fields) {
        super.reset(fields);
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManagerImpl reset() {
        super.reset();
        this.avatar = null;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManagerImpl setName(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 32, "Name");
        this.name = name;
        this.set |= 1L;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManagerImpl setAvatar(Icon avatar) {
        this.avatar = avatar;
        this.set |= 2L;
        return this;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManager setBanner(Icon banner) {
        this.banner = banner;
        this.set |= 4L;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject body = DataObject.empty();
        if (this.shouldUpdate(1L)) {
            body.put("username", this.name);
        }
        if (this.shouldUpdate(2L)) {
            body.put("avatar", this.avatar == null ? null : this.avatar.getEncoding());
        }
        if (this.shouldUpdate(4L)) {
            body.put("banner", this.banner == null ? null : this.banner.getEncoding());
        }
        this.reset();
        return this.getRequestBody(body);
    }

    @Override
    protected void handleSuccess(Response response, Request<Void> request) {
        request.onSuccess(null);
    }
}

