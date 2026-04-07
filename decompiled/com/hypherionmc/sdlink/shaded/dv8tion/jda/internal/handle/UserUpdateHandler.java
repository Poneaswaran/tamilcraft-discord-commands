/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.SelfUpdateAvatarEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.SelfUpdateDiscriminatorEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.SelfUpdateGlobalNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.SelfUpdateMFAEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.SelfUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.SelfUpdateVerifiedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.SelfUserImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import java.util.Objects;

public class UserUpdateHandler
extends SocketHandler {
    public UserUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        Boolean mfaEnabled;
        SelfUserImpl self = (SelfUserImpl)this.getJDA().getSelfUser();
        String name = content.getString("username");
        String discriminator = content.getString("discriminator");
        String globalName = content.getString("global_name", null);
        String avatarId = content.getString("avatar", null);
        Boolean verified = content.hasKey("verified") ? Boolean.valueOf(content.getBoolean("verified")) : null;
        Boolean bl = mfaEnabled = content.hasKey("mfa_enabled") ? Boolean.valueOf(content.getBoolean("mfa_enabled")) : null;
        if (!Objects.equals(name, self.getName())) {
            String oldName = self.getName();
            self.setName(name);
            this.getJDA().handleEvent(new SelfUpdateNameEvent(this.getJDA(), this.responseNumber, oldName));
        }
        if (!Objects.equals(discriminator, self.getDiscriminator())) {
            String oldDiscriminator = self.getDiscriminator();
            self.setDiscriminator(Short.parseShort(discriminator));
            this.getJDA().handleEvent(new SelfUpdateDiscriminatorEvent(this.getJDA(), this.responseNumber, oldDiscriminator));
        }
        if (!Objects.equals(globalName, self.getGlobalName())) {
            String oldGlobalName = self.getGlobalName();
            self.setGlobalName(globalName);
            this.getJDA().handleEvent(new SelfUpdateGlobalNameEvent(this.getJDA(), this.responseNumber, oldGlobalName));
        }
        if (!Objects.equals(avatarId, self.getAvatarId())) {
            String oldAvatarId = self.getAvatarId();
            self.setAvatarId(avatarId);
            this.getJDA().handleEvent(new SelfUpdateAvatarEvent(this.getJDA(), this.responseNumber, oldAvatarId));
        }
        if (verified != null && verified.booleanValue() != self.isVerified()) {
            boolean wasVerified = self.isVerified();
            self.setVerified(verified);
            this.getJDA().handleEvent(new SelfUpdateVerifiedEvent(this.getJDA(), this.responseNumber, wasVerified));
        }
        if (mfaEnabled != null && mfaEnabled.booleanValue() != self.isMfaEnabled()) {
            boolean wasMfaEnabled = self.isMfaEnabled();
            self.setMfaEnabled(mfaEnabled);
            this.getJDA().handleEvent(new SelfUpdateMFAEvent(this.getJDA(), this.responseNumber, wasMfaEnabled));
        }
        return null;
    }
}

