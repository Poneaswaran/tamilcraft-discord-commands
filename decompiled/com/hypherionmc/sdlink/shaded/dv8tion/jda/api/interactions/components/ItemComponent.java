/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;

public interface ItemComponent
extends Component {
    default public int getMaxPerRow() {
        return this.getType().getMaxPerRow();
    }

    @Override
    default public boolean isModalCompatible() {
        return this.getType().isModalCompatible();
    }

    @Override
    default public boolean isMessageCompatible() {
        return this.getType().isMessageCompatible();
    }
}

