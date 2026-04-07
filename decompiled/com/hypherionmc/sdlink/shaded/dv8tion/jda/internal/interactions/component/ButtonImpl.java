/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SkuSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;

public class ButtonImpl
implements Button {
    private final String id;
    private final String label;
    private final ButtonStyle style;
    private final String url;
    private final SkuSnowflake sku;
    private final boolean disabled;
    private final EmojiUnion emoji;

    public ButtonImpl(DataObject data) {
        this(data.getString("custom_id", null), data.getString("label", ""), ButtonStyle.fromKey(data.getInt("style")), data.getString("url", null), data.hasKey("sku_id") ? SkuSnowflake.fromId(data.getLong("sku_id")) : null, data.getBoolean("disabled"), data.optObject("emoji").map(EntityBuilder::createEmoji).orElse(null));
    }

    public ButtonImpl(String id, String label, ButtonStyle style, boolean disabled, Emoji emoji) {
        this(id, label, style, null, null, disabled, emoji);
    }

    public ButtonImpl(String id, String label, ButtonStyle style, String url, SkuSnowflake sku, boolean disabled, Emoji emoji) {
        this.id = id;
        this.label = label == null ? "" : label;
        this.style = style;
        this.url = url;
        this.sku = sku;
        this.disabled = disabled;
        this.emoji = (EmojiUnion)emoji;
    }

    public ButtonImpl checkValid() {
        Checks.notNull((Object)this.style, "Style");
        Checks.notLonger(this.label, 80, "Label");
        Checks.check(this.style != ButtonStyle.UNKNOWN, "Cannot make button with unknown style!");
        switch (this.style) {
            case PRIMARY: 
            case SECONDARY: 
            case SUCCESS: 
            case DANGER: {
                Checks.check(this.url == null, "Cannot set an URL on action buttons");
                Checks.check(this.sku == null, "Cannot set an SKU on action buttons");
                Checks.check(this.emoji != null || !this.label.isEmpty(), "Action buttons must have either an emoji or label");
                Checks.notEmpty(this.id, "Id");
                Checks.notLonger(this.id, 100, "Id");
                break;
            }
            case LINK: {
                Checks.check(this.id == null, "Cannot set an ID on link buttons");
                Checks.check(this.url != null, "You must set an URL on link buttons");
                Checks.check(this.sku == null, "Cannot set an SKU on link buttons");
                Checks.check(this.emoji != null || !this.label.isEmpty(), "Link buttons must have either an emoji or label");
                Checks.notEmpty(this.url, "URL");
                Checks.notLonger(this.url, 512, "URL");
                break;
            }
            case PREMIUM: {
                Checks.check(this.id == null, "Cannot set an ID on premium buttons");
                Checks.check(this.url == null, "Cannot set an URL on premium buttons");
                Checks.check(this.emoji == null, "Cannot set an emoji on premium buttons");
                Checks.check(this.label.isEmpty(), "Cannot set a label on premium buttons");
                Checks.notNull(this.sku, "SKU");
            }
        }
        return this;
    }

    @Override
    @Nonnull
    public Component.Type getType() {
        return Component.Type.BUTTON;
    }

    @Override
    @Nullable
    public String getId() {
        return this.id;
    }

    @Override
    @Nonnull
    public String getLabel() {
        return this.label;
    }

    @Override
    @Nonnull
    public ButtonStyle getStyle() {
        return this.style;
    }

    @Override
    @Nullable
    public String getUrl() {
        return this.url;
    }

    @Override
    @Nullable
    public SkuSnowflake getSku() {
        return this.sku;
    }

    @Override
    @Nullable
    public EmojiUnion getEmoji() {
        return this.emoji;
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject json = DataObject.empty();
        json.put("type", 2);
        if (!this.label.isEmpty()) {
            json.put("label", this.label);
        }
        json.put("style", this.style.getKey());
        json.put("disabled", this.disabled);
        if (this.emoji != null) {
            json.put("emoji", this.emoji);
        }
        if (this.url != null) {
            json.put("url", this.url);
        } else if (this.id != null) {
            json.put("custom_id", this.id);
        } else {
            json.put("sku_id", this.sku.getId());
        }
        return json;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.label, this.style, this.url, this.disabled, this.emoji});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ButtonImpl)) {
            return false;
        }
        ButtonImpl other = (ButtonImpl)obj;
        return Objects.equals(other.id, this.id) && Objects.equals(other.label, this.label) && Objects.equals(other.url, this.url) && Objects.equals(other.emoji, this.emoji) && other.disabled == this.disabled && other.style == this.style;
    }

    public String toString() {
        return new EntityString(this).setName(this.label).addMetadata("id", this.id).toString();
    }
}

