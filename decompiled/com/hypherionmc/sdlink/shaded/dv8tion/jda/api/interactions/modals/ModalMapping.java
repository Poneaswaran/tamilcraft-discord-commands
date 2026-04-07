/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Objects;

public class ModalMapping {
    private final String id;
    private final String value;
    private final Component.Type type;

    public ModalMapping(DataObject object) {
        this.id = object.getString("custom_id");
        this.value = object.getString("value");
        this.type = Component.Type.fromKey(object.getInt("type"));
    }

    @Nonnull
    public String getId() {
        return this.id;
    }

    @Nonnull
    public Component.Type getType() {
        return this.type;
    }

    @Nonnull
    public String getAsString() {
        return this.value;
    }

    public String toString() {
        return new EntityString(this).setType(this.getType()).addMetadata("value", this.getAsString()).toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModalMapping)) {
            return false;
        }
        ModalMapping that = (ModalMapping)o;
        return this.type == that.type && Objects.equals(this.id, that.id) && Objects.equals(this.value, that.value);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.value, this.type});
    }
}

