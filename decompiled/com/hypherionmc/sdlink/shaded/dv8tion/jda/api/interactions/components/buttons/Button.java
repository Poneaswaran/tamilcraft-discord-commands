/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SkuSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.ButtonImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface Button
extends ActionComponent {
    public static final int LABEL_MAX_LENGTH = 80;
    public static final int ID_MAX_LENGTH = 100;
    public static final int URL_MAX_LENGTH = 512;

    @Nonnull
    public String getLabel();

    @Nonnull
    public ButtonStyle getStyle();

    @Nullable
    public String getUrl();

    @Nullable
    public SkuSnowflake getSku();

    @Nullable
    public EmojiUnion getEmoji();

    @Override
    @Nonnull
    @CheckReturnValue
    default public Button asDisabled() {
        return this.withDisabled(true);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public Button asEnabled() {
        return this.withDisabled(false);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public Button withDisabled(boolean disabled) {
        return new ButtonImpl(this.getId(), this.getLabel(), this.getStyle(), this.getUrl(), this.getSku(), disabled, this.getEmoji()).checkValid();
    }

    @Nonnull
    @CheckReturnValue
    default public Button withEmoji(@Nullable Emoji emoji) {
        return new ButtonImpl(this.getId(), this.getLabel(), this.getStyle(), this.getUrl(), this.getSku(), this.isDisabled(), emoji).checkValid();
    }

    @Nonnull
    @CheckReturnValue
    default public Button withLabel(@Nonnull String label) {
        return new ButtonImpl(this.getId(), label, this.getStyle(), this.getUrl(), this.getSku(), this.isDisabled(), this.getEmoji()).checkValid();
    }

    @Nonnull
    @CheckReturnValue
    default public Button withId(@Nonnull String id) {
        return new ButtonImpl(id, this.getLabel(), this.getStyle(), this.getUrl(), this.getSku(), this.isDisabled(), this.getEmoji()).checkValid();
    }

    @Nonnull
    @CheckReturnValue
    default public Button withUrl(@Nonnull String url) {
        return new ButtonImpl(this.getId(), this.getLabel(), this.getStyle(), url, this.getSku(), this.isDisabled(), this.getEmoji()).checkValid();
    }

    @Nonnull
    @CheckReturnValue
    default public Button withSku(@Nonnull SkuSnowflake sku) {
        return new ButtonImpl(this.getId(), this.getLabel(), this.getStyle(), this.getUrl(), sku, this.isDisabled(), this.getEmoji()).checkValid();
    }

    @Nonnull
    @CheckReturnValue
    default public Button withStyle(@Nonnull ButtonStyle style) {
        Checks.notNull((Object)style, "Style");
        Checks.check(style != ButtonStyle.UNKNOWN, "Cannot make button with unknown style!");
        if (this.getStyle() == ButtonStyle.LINK && style != ButtonStyle.LINK) {
            throw new IllegalArgumentException("You cannot change a link button to another style!");
        }
        if (this.getStyle() != ButtonStyle.LINK && style == ButtonStyle.LINK) {
            throw new IllegalArgumentException("You cannot change a styled button to a link button!");
        }
        if (this.getStyle() == ButtonStyle.PREMIUM && style != ButtonStyle.PREMIUM) {
            throw new IllegalArgumentException("You cannot change a premium button to another style!");
        }
        if (this.getStyle() != ButtonStyle.PREMIUM && style == ButtonStyle.PREMIUM) {
            throw new IllegalArgumentException("You cannot change a styled button to a premium button!");
        }
        return new ButtonImpl(this.getId(), this.getLabel(), style, this.getUrl(), this.getSku(), this.isDisabled(), this.getEmoji()).checkValid();
    }

    @Nonnull
    public static Button primary(@Nonnull String id, @Nonnull String label) {
        return new ButtonImpl(id, label, ButtonStyle.PRIMARY, false, null).checkValid();
    }

    @Nonnull
    public static Button primary(@Nonnull String id, @Nonnull Emoji emoji) {
        return new ButtonImpl(id, null, ButtonStyle.PRIMARY, false, emoji).checkValid();
    }

    @Nonnull
    public static Button secondary(@Nonnull String id, @Nonnull String label) {
        return new ButtonImpl(id, label, ButtonStyle.SECONDARY, false, null).checkValid();
    }

    @Nonnull
    public static Button secondary(@Nonnull String id, @Nonnull Emoji emoji) {
        return new ButtonImpl(id, null, ButtonStyle.SECONDARY, false, emoji).checkValid();
    }

    @Nonnull
    public static Button success(@Nonnull String id, @Nonnull String label) {
        return new ButtonImpl(id, label, ButtonStyle.SUCCESS, false, null).checkValid();
    }

    @Nonnull
    public static Button success(@Nonnull String id, @Nonnull Emoji emoji) {
        return new ButtonImpl(id, null, ButtonStyle.SUCCESS, false, emoji).checkValid();
    }

    @Nonnull
    public static Button danger(@Nonnull String id, @Nonnull String label) {
        return new ButtonImpl(id, label, ButtonStyle.DANGER, false, null).checkValid();
    }

    @Nonnull
    public static Button danger(@Nonnull String id, @Nonnull Emoji emoji) {
        return new ButtonImpl(id, null, ButtonStyle.DANGER, false, emoji).checkValid();
    }

    @Nonnull
    public static Button link(@Nonnull String url, @Nonnull String label) {
        return new ButtonImpl(null, label, ButtonStyle.LINK, url, null, false, null).checkValid();
    }

    @Nonnull
    public static Button link(@Nonnull String url, @Nonnull Emoji emoji) {
        return new ButtonImpl(null, null, ButtonStyle.LINK, url, null, false, emoji).checkValid();
    }

    @Nonnull
    public static Button premium(@Nonnull SkuSnowflake sku) {
        return new ButtonImpl(null, null, ButtonStyle.PREMIUM, null, sku, false, null).checkValid();
    }

    @Nonnull
    public static Button of(@Nonnull ButtonStyle style, @Nonnull String idOrUrl, @Nonnull String label) {
        Checks.check(style != ButtonStyle.PREMIUM, "Premium buttons don't support labels");
        if (style == ButtonStyle.LINK) {
            return Button.link(idOrUrl, label);
        }
        return new ButtonImpl(idOrUrl, label, style, false, null).checkValid();
    }

    @Nonnull
    public static Button of(@Nonnull ButtonStyle style, @Nonnull String idOrUrl, @Nonnull Emoji emoji) {
        Checks.check(style != ButtonStyle.PREMIUM, "Premium buttons don't support emojis");
        if (style == ButtonStyle.LINK) {
            return Button.link(idOrUrl, emoji);
        }
        return new ButtonImpl(idOrUrl, null, style, false, emoji).checkValid();
    }

    @Nonnull
    public static Button of(@Nonnull ButtonStyle style, @Nonnull String idOrUrlOrSku, @Nullable String label, @Nullable Emoji emoji) {
        Checks.notNull((Object)style, "ButtonStyle");
        switch (style) {
            case LINK: {
                return new ButtonImpl(null, label, style, idOrUrlOrSku, null, false, emoji).checkValid();
            }
            case PREMIUM: {
                return new ButtonImpl(null, label, style, null, SkuSnowflake.fromId(idOrUrlOrSku), false, emoji).checkValid();
            }
        }
        return new ButtonImpl(idOrUrlOrSku, label, style, null, null, false, emoji).checkValid();
    }
}

