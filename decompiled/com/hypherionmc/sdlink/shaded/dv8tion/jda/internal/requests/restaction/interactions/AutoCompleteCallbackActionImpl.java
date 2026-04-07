/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IAutoCompleteCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.InteractionCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.InteractionCallbackImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AutoCompleteCallbackActionImpl
extends InteractionCallbackImpl<Void>
implements AutoCompleteCallbackAction {
    private final OptionType type;
    private final List<Command.Choice> choices = new ArrayList<Command.Choice>(26);

    public AutoCompleteCallbackActionImpl(IAutoCompleteCallback interaction, OptionType type) {
        super((InteractionImpl)((Object)interaction));
        this.type = type;
    }

    @Override
    @Nonnull
    public OptionType getOptionType() {
        return this.type;
    }

    @Override
    @Nonnull
    public AutoCompleteCallbackAction addChoices(@Nonnull Collection<Command.Choice> choices) {
        Checks.noneNull(choices, "Choices");
        Checks.check(choices.size() + this.choices.size() <= 25, "Can only reply with up to %d choices. Limit your suggestions!", (Object)25);
        for (Command.Choice choice : choices) {
            Checks.inRange(choice.getName(), 1, 100, "Choice name");
            switch (this.type) {
                case INTEGER: {
                    Checks.check(choice.getType() == OptionType.INTEGER, "Choice of type %s cannot be converted to INTEGER", (Object)choice.getType());
                    long valueLong = choice.getAsLong();
                    Checks.check((double)valueLong <= 9.007199254740991E15, "Choice value cannot be larger than %f Provided: %d", 9.007199254740991E15, valueLong);
                    Checks.check((double)valueLong >= -9.007199254740991E15, "Choice value cannot be smaller than %f. Provided: %d", -9.007199254740991E15, valueLong);
                    break;
                }
                case NUMBER: {
                    Checks.check(choice.getType() == OptionType.NUMBER || choice.getType() == OptionType.INTEGER, "Choice of type %s cannot be converted to NUMBER", (Object)choice.getType());
                    double valueDouble = choice.getAsDouble();
                    Checks.check(valueDouble <= 9.007199254740991E15, "Choice value cannot be larger than %f Provided: %f", 9.007199254740991E15, valueDouble);
                    Checks.check(valueDouble >= -9.007199254740991E15, "Choice value cannot be smaller than %f. Provided: %f", -9.007199254740991E15, valueDouble);
                    break;
                }
                case STRING: {
                    String valueString = choice.getAsString();
                    Checks.inRange(valueString, 1, 100, "Choice value");
                }
            }
        }
        this.choices.addAll(choices);
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject data = DataObject.empty();
        DataArray array = DataArray.empty();
        this.choices.forEach(choice -> array.add(choice.toData(this.type)));
        data.put("choices", array);
        return this.getRequestBody(DataObject.empty().put("type", InteractionCallbackAction.ResponseType.COMMAND_AUTOCOMPLETE_CHOICES.getRaw()).put("data", data));
    }

    @Override
    @Nonnull
    public AutoCompleteCallbackAction setCheck(BooleanSupplier checks) {
        return (AutoCompleteCallbackAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public AutoCompleteCallbackAction deadline(long timestamp) {
        return (AutoCompleteCallbackAction)super.deadline(timestamp);
    }
}

