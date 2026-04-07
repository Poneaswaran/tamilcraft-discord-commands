/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.AutoCompleteQuery;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionMapping;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionPayloadImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandInteractionPayloadMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.AutoCompleteCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;

public class CommandAutoCompleteInteractionImpl
extends InteractionImpl
implements CommandInteractionPayloadMixin,
CommandAutoCompleteInteraction {
    private final CommandInteractionPayload payload;
    private AutoCompleteQuery focused;

    public CommandAutoCompleteInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
        this.payload = new CommandInteractionPayloadImpl(jda, data);
        DataArray options = data.getObject("data").getArray("options");
        this.findFocused(options);
        if (this.focused == null) {
            throw new IllegalStateException("Failed to get focused option for auto complete interaction");
        }
    }

    private void findFocused(DataArray options) {
        block3: for (int i = 0; i < options.length(); ++i) {
            DataObject option = options.getObject(i);
            switch (OptionType.fromKey(option.getInt("type"))) {
                case SUB_COMMAND: 
                case SUB_COMMAND_GROUP: {
                    this.findFocused(option.getArray("options"));
                    continue block3;
                }
                default: {
                    if (!option.getBoolean("focused")) continue block3;
                    OptionMapping opt = this.getOption(option.getString("name"));
                    this.focused = new AutoCompleteQuery(opt);
                    continue block3;
                }
            }
        }
    }

    @Override
    @Nonnull
    public AutoCompleteQuery getFocusedOption() {
        return this.focused;
    }

    @Override
    @Nonnull
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)super.getChannel();
    }

    @Override
    public CommandInteractionPayload getCommandPayload() {
        return this.payload;
    }

    @Override
    @Nonnull
    public AutoCompleteCallbackAction replyChoices(@Nonnull Collection<Command.Choice> choices) {
        return new AutoCompleteCallbackActionImpl(this, this.focused.getType()).addChoices(choices);
    }
}

