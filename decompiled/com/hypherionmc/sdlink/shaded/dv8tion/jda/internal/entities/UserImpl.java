/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.PrivateChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Formatter;
import java.util.List;

public class UserImpl
extends UserSnowflakeImpl
implements User {
    protected final JDAImpl api;
    protected short discriminator;
    protected String name;
    protected String globalName;
    protected String avatarId;
    protected User.Profile profile;
    protected long privateChannelId = 0L;
    protected boolean bot;
    protected boolean system;
    protected int flags;

    public UserImpl(long id, JDAImpl api) {
        super(id);
        this.api = api;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nullable
    public String getGlobalName() {
        return this.globalName;
    }

    @Override
    @Nonnull
    public String getDiscriminator() {
        return this.discriminator == 0 ? "0000" : Helpers.format("%04d", this.discriminator);
    }

    @Override
    @Nullable
    public String getAvatarId() {
        return this.avatarId;
    }

    @Override
    @Nonnull
    public CacheRestAction<User.Profile> retrieveProfile() {
        return new DeferredRestAction<User.Profile, RestActionImpl>(this.getJDA(), User.Profile.class, this::getProfile, () -> {
            Route.CompiledRoute route = Route.Users.GET_USER.compile(this.getId());
            return new RestActionImpl<User.Profile>((JDA)this.getJDA(), route, (response, request) -> {
                DataObject json = response.getObject();
                String bannerId = json.getString("banner", null);
                int accentColor = json.getInt("accent_color", 0x1FFFFFFF);
                return new User.Profile(this.getIdLong(), bannerId, accentColor);
            });
        });
    }

    public User.Profile getProfile() {
        return this.profile;
    }

    @Override
    @Nonnull
    public String getDefaultAvatarId() {
        return this.discriminator != 0 ? String.valueOf(this.discriminator % 5) : super.getDefaultAvatarId();
    }

    @Override
    @Nonnull
    public String getAsTag() {
        return this.getName() + '#' + this.getDiscriminator();
    }

    @Override
    public boolean hasPrivateChannel() {
        return this.privateChannelId != 0L;
    }

    @Override
    @Nonnull
    public CacheRestAction<PrivateChannel> openPrivateChannel() {
        return new DeferredRestAction<PrivateChannel, RestActionImpl>(this.getJDA(), PrivateChannel.class, this::getPrivateChannel, () -> {
            Route.CompiledRoute route = Route.Self.CREATE_PRIVATE_CHANNEL.compile(new String[0]);
            DataObject body = DataObject.empty().put("recipient_id", this.getId());
            return new RestActionImpl<PrivateChannel>((JDA)this.getJDA(), route, body, (response, request) -> {
                PrivateChannel priv = this.api.getEntityBuilder().createPrivateChannel(response.getObject(), this);
                this.privateChannelId = priv.getIdLong();
                return priv;
            });
        });
    }

    public PrivateChannel getPrivateChannel() {
        if (!this.hasPrivateChannel()) {
            return null;
        }
        PrivateChannel channel = this.getJDA().getPrivateChannelById(this.privateChannelId);
        return channel != null ? channel : new PrivateChannelImpl(this.getJDA(), this.privateChannelId, this);
    }

    @Override
    @Nonnull
    public List<Guild> getMutualGuilds() {
        return this.getJDA().getMutualGuilds(this);
    }

    @Override
    public boolean isBot() {
        return this.bot;
    }

    @Override
    public boolean isSystem() {
        return this.system;
    }

    @Override
    @Nonnull
    public JDAImpl getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public EnumSet<User.UserFlag> getFlags() {
        return User.UserFlag.getFlags(this.flags);
    }

    @Override
    public int getFlagsRaw() {
        return this.flags;
    }

    @Override
    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }

    public UserImpl setName(String name) {
        this.name = name;
        return this;
    }

    public UserImpl setGlobalName(String globalName) {
        this.globalName = globalName;
        return this;
    }

    public UserImpl setDiscriminator(short discriminator) {
        this.discriminator = discriminator;
        return this;
    }

    public UserImpl setAvatarId(String avatarId) {
        this.avatarId = avatarId;
        return this;
    }

    public UserImpl setProfile(User.Profile profile) {
        this.profile = profile;
        return this;
    }

    public UserImpl setPrivateChannel(PrivateChannel privateChannel) {
        if (privateChannel != null) {
            this.privateChannelId = privateChannel.getIdLong();
        }
        return this;
    }

    public UserImpl setBot(boolean bot) {
        this.bot = bot;
        return this;
    }

    public UserImpl setSystem(boolean system) {
        this.system = system;
        return this;
    }

    public UserImpl setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public short getDiscriminatorInt() {
        return this.discriminator;
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        boolean leftJustified;
        boolean alt = (flags & 4) == 4;
        boolean upper = (flags & 2) == 2;
        boolean bl = leftJustified = (flags & 1) == 1;
        String out = !alt ? this.getAsMention() : (this.discriminator == 0 && upper ? this.getName().toUpperCase() : (this.discriminator == 0 ? this.getName() : (upper ? this.getAsTag().toUpperCase() : this.getAsTag())));
        MiscUtil.appendTo(formatter, width, precision, leftJustified, out);
    }
}

