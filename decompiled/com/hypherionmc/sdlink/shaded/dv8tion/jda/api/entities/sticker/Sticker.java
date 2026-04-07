/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface Sticker
extends StickerSnowflake {
    public static final String ICON_URL = "https://cdn.discordapp.com/stickers/%s.%s";

    @Nonnull
    public static StickerSnowflake fromId(long id) {
        return StickerSnowflake.fromId(id);
    }

    @Nonnull
    public static StickerSnowflake fromId(@Nonnull String id) {
        return Sticker.fromId(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    public StickerFormat getFormatType();

    @Nonnull
    public String getName();

    @Nonnull
    default public String getIconUrl() {
        return Helpers.format(ICON_URL, this.getId(), this.getFormatType().getExtension());
    }

    @Nonnull
    default public ImageProxy getIcon() {
        return new ImageProxy(this.getIconUrl());
    }

    public static enum StickerFormat {
        PNG(1, "png"),
        APNG(2, "png"),
        LOTTIE(3, "json"),
        GIF(4, "gif"),
        UNKNOWN(-1, null);

        private final int id;
        private final String extension;

        private StickerFormat(int id, String extension) {
            this.id = id;
            this.extension = extension;
        }

        @Nonnull
        public String getExtension() {
            if (this == UNKNOWN) {
                throw new IllegalStateException("Cannot get file extension for StickerFormat.UNKNOWN");
            }
            return this.extension;
        }

        @Nonnull
        public static StickerFormat fromId(int id) {
            for (StickerFormat stickerFormat : StickerFormat.values()) {
                if (stickerFormat.id != id) continue;
                return stickerFormat;
            }
            return UNKNOWN;
        }
    }

    public static enum Type {
        STANDARD(1),
        GUILD(2),
        UNKNOWN(-1);

        private final int id;

        private Type(int id) {
            this.id = id;
        }

        @Nonnull
        public static Type fromId(int id) {
            for (Type type : Type.values()) {
                if (type.id != id) continue;
                return type;
            }
            return UNKNOWN;
        }

        public int getId() {
            return this.id;
        }
    }
}

