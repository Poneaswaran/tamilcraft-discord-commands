/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.SelectMenuInteractionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringSelectInteractionImpl
extends SelectMenuInteractionImpl<String, StringSelectMenu>
implements StringSelectInteraction {
    private final List<String> values;

    public StringSelectInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, StringSelectMenu.class, data);
        this.values = Collections.unmodifiableList(this.parseValues(data.getObject("data")));
    }

    protected List<String> parseValues(DataObject data) {
        return data.optArray("values").map(arr -> arr.stream(DataArray::getString).collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @Override
    @Nonnull
    public List<String> getValues() {
        return this.values;
    }
}

