/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.CommandDataImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class CommandEditActionImpl
extends RestActionImpl<Command>
implements CommandEditAction {
    private static final String UNDEFINED = "undefined";
    private static final int NAME_SET = 1;
    private static final int DESCRIPTION_SET = 2;
    private static final int OPTIONS_SET = 4;
    private static final int PERMISSIONS_SET = 8;
    private static final int GUILD_ONLY_SET = 16;
    private static final int NSFW_SET = 32;
    private final Guild guild;
    private int mask = 0;
    private CommandDataImpl data = new CommandDataImpl("undefined", "undefined");

    public CommandEditActionImpl(JDA api, String id) {
        super(api, Route.Interactions.EDIT_COMMAND.compile(api.getSelfUser().getApplicationId(), id));
        this.guild = null;
    }

    public CommandEditActionImpl(Guild guild, String id) {
        super(guild.getJDA(), Route.Interactions.EDIT_GUILD_COMMAND.compile(guild.getJDA().getSelfUser().getApplicationId(), guild.getId(), id));
        this.guild = guild;
    }

    @Override
    @Nonnull
    public CommandEditAction setCheck(BooleanSupplier checks) {
        return (CommandEditAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public CommandEditAction deadline(long timestamp) {
        return (CommandEditAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public CommandEditAction apply(@Nonnull CommandData commandData) {
        Checks.notNull(commandData, "Command Data");
        this.mask = 63;
        this.data = (CommandDataImpl)commandData;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction addCheck(@Nonnull BooleanSupplier checks) {
        return (CommandEditAction)super.addCheck(checks);
    }

    @Override
    @Nonnull
    public CommandEditAction timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (CommandEditAction)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public CommandEditAction setName(@Nullable String name) {
        if (name == null) {
            this.mask &= 0xFFFFFFFE;
            return this;
        }
        this.data.setName(name);
        this.mask |= 1;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction setGuildOnly(boolean guildOnly) {
        this.data.setGuildOnly(guildOnly);
        this.mask |= 0x10;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction setNSFW(boolean nsfw) {
        this.data.setNSFW(nsfw);
        this.mask |= 0x20;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction setDefaultPermissions(@Nonnull DefaultMemberPermissions permission) {
        this.data.setDefaultPermissions(permission);
        this.mask |= 8;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction setDescription(@Nullable String description) {
        if (description == null) {
            this.mask &= 0xFFFFFFFD;
            return this;
        }
        this.data.setDescription(description);
        this.mask |= 2;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction clearOptions() {
        this.data = new CommandDataImpl(this.data.getName(), this.data.getDescription());
        this.mask &= 0xFFFFFFFB;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction addOptions(OptionData ... options) {
        this.data.addOptions(options);
        this.mask |= 4;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction addSubcommands(SubcommandData ... subcommands) {
        this.data.addSubcommands(subcommands);
        this.mask |= 4;
        return this;
    }

    @Override
    @Nonnull
    public CommandEditAction addSubcommandGroups(SubcommandGroupData ... groups2) {
        this.data.addSubcommandGroups(groups2);
        this.mask |= 4;
        return this;
    }

    private boolean isUnchanged(int flag) {
        return (this.mask & flag) != flag;
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject json = this.data.toData();
        if (this.isUnchanged(1)) {
            json.remove("name");
        }
        if (this.isUnchanged(2)) {
            json.remove("description");
        }
        if (this.isUnchanged(4)) {
            json.remove("options");
        }
        if (this.isUnchanged(8)) {
            json.remove("default_member_permissions");
        }
        if (this.isUnchanged(16)) {
            json.remove("dm_permission");
        }
        if (this.isUnchanged(32)) {
            json.remove("nsfw");
        }
        this.mask = 0;
        this.data = new CommandDataImpl(UNDEFINED, UNDEFINED);
        return this.getRequestBody(json);
    }

    @Override
    protected void handleSuccess(Response response, Request<Command> request) {
        DataObject json = response.getObject();
        request.onSuccess(new CommandImpl(this.api, this.guild, json));
    }
}

