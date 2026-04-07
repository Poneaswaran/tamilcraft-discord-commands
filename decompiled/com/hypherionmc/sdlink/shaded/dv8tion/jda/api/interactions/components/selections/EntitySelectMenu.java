/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.EntitySelectMenuImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.Unmodifiable;

public interface EntitySelectMenu
extends SelectMenu {
    @Override
    @Nonnull
    default public EntitySelectMenu asDisabled() {
        return (EntitySelectMenu)SelectMenu.super.asDisabled();
    }

    @Override
    @Nonnull
    default public EntitySelectMenu asEnabled() {
        return (EntitySelectMenu)SelectMenu.super.asEnabled();
    }

    @Override
    @Nonnull
    default public EntitySelectMenu withDisabled(boolean disabled) {
        return ((Builder)this.createCopy().setDisabled(disabled)).build();
    }

    @Nonnull
    public EnumSet<SelectTarget> getEntityTypes();

    @Nonnull
    public EnumSet<ChannelType> getChannelTypes();

    @Nonnull
    public @Unmodifiable List<DefaultValue> getDefaultValues();

    @Nonnull
    @CheckReturnValue
    default public Builder createCopy() {
        Builder builder = EntitySelectMenu.create(this.getId(), this.getEntityTypes());
        EnumSet<ChannelType> channelTypes = this.getChannelTypes();
        if (!channelTypes.isEmpty()) {
            builder.setChannelTypes(channelTypes);
        }
        builder.setRequiredRange(this.getMinValues(), this.getMaxValues());
        builder.setPlaceholder(this.getPlaceholder());
        builder.setDisabled(this.isDisabled());
        builder.setDefaultValues(this.getDefaultValues());
        return builder;
    }

    @Nonnull
    @CheckReturnValue
    public static Builder create(@Nonnull String customId, @Nonnull Collection<SelectTarget> types) {
        return new Builder(customId).setEntityTypes(types);
    }

    @Nonnull
    @CheckReturnValue
    public static Builder create(@Nonnull String customId, @Nonnull SelectTarget type, SelectTarget ... types) {
        Checks.notNull((Object)type, "Type");
        Checks.noneNull((Object[])types, "Types");
        return EntitySelectMenu.create(customId, EnumSet.of(type, types));
    }

    public static class Builder
    extends SelectMenu.Builder<EntitySelectMenu, Builder> {
        protected Component.Type componentType;
        protected EnumSet<ChannelType> channelTypes = EnumSet.noneOf(ChannelType.class);
        protected List<DefaultValue> defaultValues = new ArrayList<DefaultValue>();

        protected Builder(@Nonnull String customId) {
            super(customId);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nonnull
        public Builder setEntityTypes(@Nonnull Collection<SelectTarget> types) {
            Checks.notEmpty(types, "Types");
            Checks.noneNull(types, "Types");
            EnumSet<SelectTarget> set = Helpers.copyEnumSet(SelectTarget.class, types);
            if (set.size() == 1) {
                if (set.contains((Object)SelectTarget.CHANNEL)) {
                    this.componentType = Component.Type.CHANNEL_SELECT;
                    return this;
                } else if (set.contains((Object)SelectTarget.ROLE)) {
                    this.componentType = Component.Type.ROLE_SELECT;
                    return this;
                } else {
                    if (!set.contains((Object)SelectTarget.USER)) return this;
                    this.componentType = Component.Type.USER_SELECT;
                }
                return this;
            } else {
                if (set.size() != 2) throw new IllegalArgumentException("The provided combination of select targets is not supported. Provided: " + set);
                if (!set.contains((Object)SelectTarget.USER) || !set.contains((Object)SelectTarget.ROLE)) throw new IllegalArgumentException("The provided combination of select targets is not supported. Provided: " + set);
                this.componentType = Component.Type.MENTIONABLE_SELECT;
            }
            return this;
        }

        @Nonnull
        public Builder setEntityTypes(@Nonnull SelectTarget type, SelectTarget ... types) {
            Checks.notNull((Object)type, "Type");
            Checks.noneNull((Object[])types, "Types");
            return this.setEntityTypes(EnumSet.of(type, types));
        }

        @Nonnull
        public Builder setChannelTypes(@Nonnull Collection<ChannelType> types) {
            Checks.noneNull(types, "Types");
            for (ChannelType type : types) {
                Checks.check(type.isGuild(), "Only guild channel types are allowed! Provided: %s", (Object)type);
            }
            this.channelTypes = Helpers.copyEnumSet(ChannelType.class, types);
            return this;
        }

        @Nonnull
        public Builder setChannelTypes(ChannelType ... types) {
            return this.setChannelTypes(Arrays.asList(types));
        }

        @Nonnull
        public Builder setDefaultValues(DefaultValue ... values) {
            Checks.noneNull(values, "Default Values");
            return this.setDefaultValues(Arrays.asList(values));
        }

        @Nonnull
        public Builder setDefaultValues(@Nonnull Collection<? extends DefaultValue> values) {
            Checks.noneNull(values, "Default Values");
            Checks.check(values.size() <= 25, "Cannot add more than %d default values to a select menu!", (Object)25);
            for (DefaultValue defaultValue : values) {
                SelectTarget type = defaultValue.getType();
                String error = "The select menu supports types %s, but provided default value has type SelectTarget.%s!";
                switch (this.componentType) {
                    case ROLE_SELECT: {
                        Checks.check(type == SelectTarget.ROLE, error, new Object[]{"SelectTarget.ROLE", type});
                        break;
                    }
                    case USER_SELECT: {
                        Checks.check(type == SelectTarget.USER, error, new Object[]{"SelectTarget.USER", type});
                        break;
                    }
                    case CHANNEL_SELECT: {
                        Checks.check(type == SelectTarget.CHANNEL, error, new Object[]{"SelectTarget.CHANNEL", type});
                        break;
                    }
                    case MENTIONABLE_SELECT: {
                        Checks.check(type == SelectTarget.ROLE || type == SelectTarget.USER, error, new Object[]{"SelectTarget.ROLE and SelectTarget.USER", type});
                    }
                }
            }
            this.defaultValues.clear();
            this.defaultValues.addAll(values);
            return this;
        }

        @Override
        @Nonnull
        public EntitySelectMenu build() {
            Checks.check(this.minValues <= this.maxValues, "Min values cannot be greater than max values!");
            EnumSet<ChannelType> channelTypes = this.componentType == Component.Type.CHANNEL_SELECT ? this.channelTypes : EnumSet.noneOf(ChannelType.class);
            ArrayList<DefaultValue> defaultValues = new ArrayList<DefaultValue>(this.defaultValues);
            return new EntitySelectMenuImpl(this.customId, this.placeholder, this.minValues, this.maxValues, this.disabled, this.componentType, channelTypes, defaultValues);
        }
    }

    public static class DefaultValue
    implements ISnowflake,
    SerializableData {
        private final long id;
        private final SelectTarget type;

        protected DefaultValue(long id, @Nonnull SelectTarget type) {
            this.id = id;
            this.type = type;
        }

        @Nonnull
        public static DefaultValue fromData(@Nonnull DataObject object) {
            Checks.notNull(object, "DataObject");
            long id = object.getUnsignedLong("id");
            switch (object.getString("type")) {
                case "role": {
                    return DefaultValue.role(id);
                }
                case "user": {
                    return DefaultValue.user(id);
                }
                case "channel": {
                    return DefaultValue.channel(id);
                }
            }
            throw new IllegalArgumentException("Unknown value type '" + object.getString("type", null) + "'");
        }

        @Nonnull
        public static DefaultValue from(@Nonnull UserSnowflake user) {
            Checks.notNull(user, "User");
            return DefaultValue.user(user.getIdLong());
        }

        @Nonnull
        public static DefaultValue from(@Nonnull Role role) {
            Checks.notNull(role, "Role");
            return DefaultValue.role(role.getIdLong());
        }

        @Nonnull
        public static DefaultValue from(@Nonnull GuildChannel channel) {
            Checks.notNull(channel, "Channel");
            return DefaultValue.channel(channel.getIdLong());
        }

        @Nonnull
        public static DefaultValue role(long id) {
            return new DefaultValue(id, SelectTarget.ROLE);
        }

        @Nonnull
        public static DefaultValue role(@Nonnull String id) {
            return new DefaultValue(MiscUtil.parseSnowflake(id), SelectTarget.ROLE);
        }

        @Nonnull
        public static DefaultValue user(long id) {
            return new DefaultValue(id, SelectTarget.USER);
        }

        @Nonnull
        public static DefaultValue user(@Nonnull String id) {
            return new DefaultValue(MiscUtil.parseSnowflake(id), SelectTarget.USER);
        }

        @Nonnull
        public static DefaultValue channel(long id) {
            return new DefaultValue(id, SelectTarget.CHANNEL);
        }

        @Nonnull
        public static DefaultValue channel(@Nonnull String id) {
            return new DefaultValue(MiscUtil.parseSnowflake(id), SelectTarget.CHANNEL);
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Nonnull
        public SelectTarget getType() {
            return this.type;
        }

        @Override
        @Nonnull
        public DataObject toData() {
            return DataObject.empty().put("type", this.type.name().toLowerCase(Locale.ROOT)).put("id", this.getId());
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.type, this.id});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DefaultValue)) {
                return false;
            }
            DefaultValue other = (DefaultValue)obj;
            return this.id == other.id && this.type == other.type;
        }

        public String toString() {
            return new EntityString(this).setType(this.type).toString();
        }
    }

    public static enum SelectTarget {
        USER,
        ROLE,
        CHANNEL;

    }
}

