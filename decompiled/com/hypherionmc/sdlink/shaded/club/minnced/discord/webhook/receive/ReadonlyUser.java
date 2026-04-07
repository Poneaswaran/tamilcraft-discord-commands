/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive;

import com.hypherionmc.sdlink.shaded.json.JSONObject;
import com.hypherionmc.sdlink.shaded.json.JSONPropertyName;
import com.hypherionmc.sdlink.shaded.json.JSONString;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReadonlyUser
implements JSONString {
    private final long id;
    private final short discriminator;
    private final boolean bot;
    private final String name;
    private final String avatar;

    public ReadonlyUser(long id, short discriminator, boolean bot, @NotNull String name, @Nullable String avatar) {
        this.id = id;
        this.discriminator = discriminator;
        this.bot = bot;
        this.name = name;
        this.avatar = avatar;
    }

    public long getId() {
        return this.id;
    }

    public String getDiscriminator() {
        return String.format(Locale.ROOT, "%04d", this.discriminator);
    }

    public boolean isBot() {
        return this.bot;
    }

    @JSONPropertyName(value="username")
    @NotNull
    public String getName() {
        return this.name;
    }

    @JSONPropertyName(value="avatar_id")
    @Nullable
    public String getAvatarId() {
        return this.avatar;
    }

    public String toString() {
        return this.toJSONString();
    }

    @Override
    public String toJSONString() {
        return new JSONObject(this).toString();
    }
}

