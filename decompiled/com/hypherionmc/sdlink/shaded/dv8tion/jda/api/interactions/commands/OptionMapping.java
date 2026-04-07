/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.InteractionMentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;

public class OptionMapping {
    private final DataObject data;
    private final OptionType type;
    private final String name;
    private final TLongObjectMap<Object> resolved;
    private final Mentions mentions;

    public OptionMapping(DataObject data, TLongObjectMap<Object> resolved, JDA jda, Guild guild) {
        this.data = data;
        this.type = OptionType.fromKey(data.getInt("type", -1));
        this.name = data.getString("name");
        this.resolved = resolved;
        this.mentions = this.type == OptionType.STRING ? new InteractionMentions(this.getAsString(), resolved, (JDAImpl)jda, (GuildImpl)guild) : new InteractionMentions("", new TLongObjectHashMap<Object>(0), (JDAImpl)jda, (GuildImpl)guild);
    }

    @Nonnull
    public Mentions getMentions() {
        return this.mentions;
    }

    @Nonnull
    public OptionType getType() {
        return this.type;
    }

    @Nonnull
    public String getName() {
        return this.name;
    }

    @Nonnull
    public Message.Attachment getAsAttachment() {
        Object obj = this.resolved.get(this.getAsLong());
        if (obj instanceof Message.Attachment) {
            return (Message.Attachment)obj;
        }
        throw new IllegalStateException("Cannot resolve option of type " + (Object)((Object)this.type) + " to Attachment!");
    }

    @Nonnull
    public String getAsString() {
        return this.data.getString("value");
    }

    public boolean getAsBoolean() {
        if (this.type != OptionType.BOOLEAN) {
            throw new IllegalStateException("Cannot convert option of type " + (Object)((Object)this.type) + " to boolean");
        }
        return this.data.getBoolean("value");
    }

    public long getAsLong() {
        switch (this.type) {
            default: {
                throw new IllegalStateException("Cannot convert option of type " + (Object)((Object)this.type) + " to long");
            }
            case STRING: 
            case MENTIONABLE: 
            case CHANNEL: 
            case ROLE: 
            case USER: 
            case INTEGER: 
            case ATTACHMENT: 
        }
        return this.data.getLong("value");
    }

    public int getAsInt() {
        return Math.toIntExact(this.getAsLong());
    }

    public double getAsDouble() {
        switch (this.type) {
            default: {
                throw new IllegalStateException("Cannot convert option of type " + (Object)((Object)this.type) + " to double");
            }
            case STRING: 
            case INTEGER: 
            case NUMBER: 
        }
        return this.data.getDouble("value");
    }

    @Nonnull
    public IMentionable getAsMentionable() {
        Object entity = this.resolved.get(this.getAsLong());
        if (entity instanceof IMentionable) {
            return (IMentionable)entity;
        }
        throw new IllegalStateException("Cannot resolve option of type " + (Object)((Object)this.type) + " to IMentionable");
    }

    @Nullable
    public Member getAsMember() {
        if (this.type != OptionType.USER && this.type != OptionType.MENTIONABLE) {
            throw new IllegalStateException("Cannot resolve Member for option " + this.getName() + " of type " + (Object)((Object)this.type));
        }
        Object object = this.resolved.get(this.getAsLong());
        if (object instanceof Member) {
            return (Member)object;
        }
        return null;
    }

    @Nonnull
    public User getAsUser() {
        if (this.type != OptionType.USER && this.type != OptionType.MENTIONABLE) {
            throw new IllegalStateException("Cannot resolve User for option " + this.getName() + " of type " + (Object)((Object)this.type));
        }
        Object object = this.resolved.get(this.getAsLong());
        if (object instanceof Member) {
            return ((Member)object).getUser();
        }
        if (object instanceof User) {
            return (User)object;
        }
        throw new IllegalStateException("Could not resolve User from option type " + (Object)((Object)this.type));
    }

    @Nonnull
    public Role getAsRole() {
        if (this.type != OptionType.ROLE && this.type != OptionType.MENTIONABLE) {
            throw new IllegalStateException("Cannot resolve Role for option " + this.getName() + " of type " + (Object)((Object)this.type));
        }
        Object role = this.resolved.get(this.getAsLong());
        if (role instanceof Role) {
            return (Role)role;
        }
        throw new IllegalStateException("Could not resolve Role from option type " + (Object)((Object)this.type));
    }

    @Nonnull
    public ChannelType getChannelType() {
        return this.getAsChannel().getType();
    }

    @Nonnull
    public GuildChannelUnion getAsChannel() {
        if (this.type != OptionType.CHANNEL) {
            throw new IllegalStateException("Cannot resolve Channel for option " + this.getName() + " of type " + (Object)((Object)this.type));
        }
        Object entity = this.resolved.get(this.getAsLong());
        if (entity instanceof GuildChannel) {
            return (GuildChannelUnion)entity;
        }
        throw new IllegalStateException("Could not resolve GuildChannel!");
    }

    public String toString() {
        return new EntityString(this).setType(this.getType()).addMetadata("name", this.name).addMetadata("value", this.getAsString()).toString();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getType(), this.getName()});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OptionMapping)) {
            return false;
        }
        OptionMapping data = (OptionMapping)obj;
        return this.getType() == data.getType() && this.getName().equals(data.getName());
    }
}

