/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class EntityString {
    private final Object entity;
    private Object type;
    private String name;
    private List<String> metadata;

    public EntityString(Object entity) {
        this.entity = entity;
    }

    public EntityString setType(@Nonnull Enum<?> type) {
        this.type = type.name();
        return this;
    }

    public EntityString setType(@Nonnull Object type) {
        this.type = type;
        return this;
    }

    public EntityString setName(@Nonnull String name) {
        this.name = name;
        return this;
    }

    public EntityString addMetadata(@Nullable String key, @Nullable Object value) {
        if (this.metadata == null) {
            this.metadata = new ArrayList<String>();
        }
        this.metadata.add(key == null ? String.valueOf(value) : key + "=" + value);
        return this;
    }

    @Nonnull
    public String toString() {
        boolean isSnowflake;
        String entityName = this.entity instanceof String ? (String)this.entity : (this.entity instanceof Class ? EntityString.getCleanedClassName((Class)this.entity) : EntityString.getCleanedClassName(this.entity.getClass()));
        StringBuilder sb = new StringBuilder(entityName);
        if (this.type != null) {
            sb.append('[').append(this.type).append(']');
        }
        if (this.name != null) {
            sb.append(':').append(this.name);
        }
        if ((isSnowflake = this.entity instanceof ISnowflake) || this.metadata != null) {
            StringJoiner metadataJoiner = new StringJoiner(", ", "(", ")");
            if (isSnowflake) {
                metadataJoiner.add("id=" + ((ISnowflake)this.entity).getId());
            }
            if (this.metadata != null) {
                for (String metadataItem : this.metadata) {
                    metadataJoiner.add(metadataItem.toString());
                }
            }
            sb.append(metadataJoiner);
        }
        return sb.toString();
    }

    @Nonnull
    private static String getCleanedClassName(@Nonnull Class<?> clazz) {
        String packageName = clazz.getPackage().getName();
        String fullName = clazz.getName();
        String simpleName = fullName.substring(packageName.length() + 1);
        return simpleName.replace("$", ".").replace("Impl", "");
    }
}

