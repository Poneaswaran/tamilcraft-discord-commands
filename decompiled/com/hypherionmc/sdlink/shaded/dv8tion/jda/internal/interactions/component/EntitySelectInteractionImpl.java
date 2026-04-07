/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.EntitySelectInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.SelectMenuMentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.SelectMenuInteractionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public class EntitySelectInteractionImpl
extends SelectMenuInteractionImpl<IMentionable, EntitySelectMenu>
implements EntitySelectInteraction {
    private final Mentions mentions;

    public EntitySelectInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, EntitySelectMenu.class, data);
        DataObject content = data.getObject("data");
        this.mentions = new SelectMenuMentions(jda, (GuildImpl)this.getGuild(), content.optObject("resolved").orElseGet(DataObject::empty), content.optArray("values").orElseGet(DataArray::empty));
    }

    @Override
    @Nonnull
    public Mentions getMentions() {
        return this.mentions;
    }

    @Override
    @Nonnull
    public List<IMentionable> getValues() {
        return this.mentions.getMentions(new Message.MentionType[0]);
    }
}

