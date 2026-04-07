/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandListUpdateActionImpl
extends RestActionImpl<List<Command>>
implements CommandListUpdateAction {
    private final List<CommandData> commands = new ArrayList<CommandData>();
    private final GuildImpl guild;
    private int slash;
    private int user;
    private int message;

    public CommandListUpdateActionImpl(JDA api, GuildImpl guild, Route.CompiledRoute route) {
        super(api, route);
        this.guild = guild;
    }

    @Override
    @Nonnull
    public CommandListUpdateAction timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (CommandListUpdateAction)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public CommandListUpdateAction addCheck(@Nonnull BooleanSupplier checks) {
        return (CommandListUpdateAction)super.addCheck(checks);
    }

    @Override
    @Nonnull
    public CommandListUpdateAction setCheck(BooleanSupplier checks) {
        return (CommandListUpdateAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public CommandListUpdateAction deadline(long timestamp) {
        return (CommandListUpdateAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public CommandListUpdateAction addCommands(@Nonnull Collection<? extends CommandData> commands) {
        Checks.noneNull(commands, "Command");
        int newSlash = 0;
        int newUser = 0;
        int newMessage = 0;
        for (CommandData commandData : commands) {
            switch (commandData.getType()) {
                case SLASH: {
                    ++newSlash;
                    break;
                }
                case MESSAGE: {
                    ++newMessage;
                    break;
                }
                case USER: {
                    ++newUser;
                }
            }
        }
        Checks.check(this.slash + newSlash <= 100, "Cannot have more than %d slash commands! Try using subcommands instead.", (Object)100);
        Checks.check(this.user + newUser <= 5, "Cannot have more than %d user context commands!", (Object)5);
        Checks.check(this.message + newMessage <= 5, "Cannot have more than %d message context commands!", (Object)5);
        Checks.checkUnique(Stream.concat(commands.stream(), this.commands.stream()).map((? super T c) -> (Object)((Object)c.getType()) + " " + c.getName()), "Cannot have multiple commands of the same type with identical names. Name: \"%s\" with type %s appeared %d times!", (count, value) -> {
            String[] tuple = value.split(" ", 2);
            return new Object[]{tuple[1], tuple[0], count};
        });
        this.slash += newSlash;
        this.user += newUser;
        this.message += newMessage;
        this.commands.addAll(commands);
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        DataArray json = DataArray.empty();
        json.addAll(this.commands);
        return this.getRequestBody(json);
    }

    @Override
    protected void handleSuccess(Response response, Request<List<Command>> request) {
        List commands = response.getArray().stream(DataArray::getObject).map((? super T obj) -> new CommandImpl(this.api, this.guild, (DataObject)obj)).collect(Collectors.toList());
        request.onSuccess(commands);
    }
}

