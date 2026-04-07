/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.AccountManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AccountManagerImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SelfUserImpl
extends UserImpl
implements SelfUser {
    private boolean verified;
    private boolean mfaEnabled;
    private long applicationId;

    public SelfUserImpl(long id, JDAImpl api) {
        super(id, api);
        this.applicationId = id;
    }

    @Override
    public boolean hasPrivateChannel() {
        return false;
    }

    @Override
    public PrivateChannel getPrivateChannel() {
        throw new UnsupportedOperationException("You cannot get a PrivateChannel with yourself (SelfUser)");
    }

    @Override
    @Nonnull
    public CacheRestAction<PrivateChannel> openPrivateChannel() {
        throw new UnsupportedOperationException("You cannot open a PrivateChannel with yourself (SelfUser)");
    }

    @Override
    public long getApplicationIdLong() {
        return this.applicationId;
    }

    @Override
    public boolean isVerified() {
        return this.verified;
    }

    @Override
    public boolean isMfaEnabled() {
        return this.mfaEnabled;
    }

    @Override
    public long getAllowedFileSize() {
        return 0x1900000L;
    }

    @Override
    @Nonnull
    public AccountManager getManager() {
        return new AccountManagerImpl(this);
    }

    public SelfUserImpl setVerified(boolean verified) {
        this.verified = verified;
        return this;
    }

    public SelfUserImpl setMfaEnabled(boolean enabled) {
        this.mfaEnabled = enabled;
        return this;
    }

    public SelfUserImpl setApplicationId(long id) {
        this.applicationId = id;
        return this;
    }

    public static SelfUserImpl copyOf(SelfUserImpl other, JDAImpl jda) {
        SelfUserImpl selfUser = new SelfUserImpl(other.id, jda);
        selfUser.setName(other.name).setGlobalName(other.globalName).setAvatarId(other.avatarId).setDiscriminator(other.getDiscriminatorInt()).setBot(other.bot);
        return selfUser.setVerified(other.verified).setMfaEnabled(other.mfaEnabled).setApplicationId(other.applicationId);
    }
}

