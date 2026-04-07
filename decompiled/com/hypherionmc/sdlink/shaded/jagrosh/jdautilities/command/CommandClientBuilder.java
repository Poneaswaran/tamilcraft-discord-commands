/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.DeprecatedSince;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.ForRemoval;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.AnnotatedModuleCompiler;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Command;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandListener;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.ContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.GuildSettingsManager;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.impl.AnnotatedModuleCompilerImpl;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.impl.CommandClientImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommandClientBuilder {
    private Activity activity = Activity.playing("default");
    private OnlineStatus status = OnlineStatus.ONLINE;
    private String ownerId;
    private String[] coOwnerIds;
    private String prefix;
    private String altprefix;
    private String[] prefixes;
    private Function<MessageReceivedEvent, String> prefixFunction;
    private Function<MessageReceivedEvent, Boolean> commandPreProcessFunction;
    private BiFunction<MessageReceivedEvent, Command, Boolean> commandPreProcessBiFunction;
    private String serverInvite;
    private String success;
    private String warning;
    private String error;
    private String carbonKey;
    private String botsKey;
    private final LinkedList<Command> commands = new LinkedList();
    private final LinkedList<SlashCommand> slashCommands = new LinkedList();
    private final LinkedList<ContextMenu> contextMenus = new LinkedList();
    private String forcedGuildId = null;
    private boolean manualUpsert = false;
    private CommandListener listener;
    private boolean useHelp = true;
    private boolean shutdownAutomatically = true;
    private Consumer<CommandEvent> helpConsumer;
    private String helpWord;
    private ScheduledExecutorService executor;
    private int linkedCacheSize = 0;
    private AnnotatedModuleCompiler compiler = new AnnotatedModuleCompilerImpl();
    private GuildSettingsManager manager = null;

    public CommandClient build() {
        CommandClientImpl client = new CommandClientImpl(this.ownerId, this.coOwnerIds, this.prefix, this.altprefix, this.prefixes, this.prefixFunction, this.commandPreProcessFunction, this.commandPreProcessBiFunction, this.activity, this.status, this.serverInvite, this.success, this.warning, this.error, this.carbonKey, this.botsKey, new ArrayList<Command>(this.commands), new ArrayList<SlashCommand>(this.slashCommands), new ArrayList<ContextMenu>(this.contextMenus), this.forcedGuildId, this.manualUpsert, this.useHelp, this.shutdownAutomatically, this.helpConsumer, this.helpWord, this.executor, this.linkedCacheSize, this.compiler, this.manager);
        if (this.listener != null) {
            client.setListener(this.listener);
        }
        return client;
    }

    public CommandClientBuilder setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public CommandClientBuilder setOwnerId(long ownerId) {
        this.ownerId = String.valueOf(ownerId);
        return this;
    }

    public CommandClientBuilder setCoOwnerIds(String ... coOwnerIds) {
        this.coOwnerIds = coOwnerIds;
        return this;
    }

    public CommandClientBuilder setCoOwnerIds(long ... coOwnerIds) {
        this.coOwnerIds = (String[])Arrays.stream(coOwnerIds).mapToObj(String::valueOf).toArray(String[]::new);
        return this;
    }

    public CommandClientBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public CommandClientBuilder setAlternativePrefix(String prefix) {
        this.altprefix = prefix;
        return this;
    }

    public CommandClientBuilder setPrefixes(String[] prefixes) {
        this.prefixes = prefixes;
        return this;
    }

    public CommandClientBuilder setPrefixFunction(Function<MessageReceivedEvent, String> prefixFunction) {
        this.prefixFunction = prefixFunction;
        return this;
    }

    @Deprecated
    @DeprecatedSince(value="1.24.0")
    @ForRemoval(deadline="2.0")
    public CommandClientBuilder setCommandPreProcessFunction(Function<MessageReceivedEvent, Boolean> commandPreProcessFunction) {
        this.commandPreProcessFunction = commandPreProcessFunction;
        return this;
    }

    public CommandClientBuilder setCommandPreProcessBiFunction(BiFunction<MessageReceivedEvent, Command, Boolean> commandPreProcessBiFunction) {
        this.commandPreProcessBiFunction = commandPreProcessBiFunction;
        return this;
    }

    public CommandClientBuilder useHelpBuilder(boolean useHelp) {
        this.useHelp = useHelp;
        return this;
    }

    public CommandClientBuilder setHelpConsumer(Consumer<CommandEvent> helpConsumer) {
        this.helpConsumer = helpConsumer;
        return this;
    }

    public CommandClientBuilder setHelpWord(String helpWord) {
        this.helpWord = helpWord;
        return this;
    }

    public CommandClientBuilder setServerInvite(String serverInvite) {
        this.serverInvite = serverInvite;
        return this;
    }

    public CommandClientBuilder setEmojis(String success, String warning, String error) {
        this.success = success;
        this.warning = warning;
        this.error = error;
        return this;
    }

    public CommandClientBuilder setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public CommandClientBuilder useDefaultGame() {
        this.activity = Activity.playing("default");
        return this;
    }

    public CommandClientBuilder setStatus(OnlineStatus status) {
        this.status = status;
        return this;
    }

    public CommandClientBuilder addCommand(Command command) {
        this.commands.add(command);
        return this;
    }

    public CommandClientBuilder addCommands(Command ... commands) {
        for (Command command : commands) {
            this.addCommand(command);
        }
        return this;
    }

    public CommandClientBuilder addSlashCommand(SlashCommand command) {
        this.slashCommands.add(command);
        return this;
    }

    public CommandClientBuilder addSlashCommands(SlashCommand ... commands) {
        for (SlashCommand command : commands) {
            this.addSlashCommand(command);
        }
        return this;
    }

    public CommandClientBuilder addContextMenu(ContextMenu contextMenu) {
        this.contextMenus.add(contextMenu);
        return this;
    }

    public CommandClientBuilder addContextMenus(ContextMenu ... contextMenus) {
        for (ContextMenu contextMenu : contextMenus) {
            this.addContextMenu(contextMenu);
        }
        return this;
    }

    public CommandClientBuilder forceGuildOnly(String guildId) {
        this.forcedGuildId = guildId;
        return this;
    }

    public CommandClientBuilder forceGuildOnly(long guildId) {
        this.forcedGuildId = String.valueOf(guildId);
        return this;
    }

    public CommandClientBuilder setManualUpsert(boolean manualUpsert) {
        this.manualUpsert = manualUpsert;
        return this;
    }

    public CommandClientBuilder addAnnotatedModule(Object module) {
        this.commands.addAll(this.compiler.compile(module));
        return this;
    }

    public CommandClientBuilder addAnnotatedModules(Object ... modules) {
        for (Object command : modules) {
            this.addAnnotatedModule(command);
        }
        return this;
    }

    public CommandClientBuilder setAnnotatedCompiler(AnnotatedModuleCompiler compiler) {
        this.compiler = compiler;
        return this;
    }

    public CommandClientBuilder setCarbonitexKey(String key) {
        this.carbonKey = key;
        return this;
    }

    public CommandClientBuilder setDiscordBotsKey(String key) {
        this.botsKey = key;
        return this;
    }

    @Deprecated
    public CommandClientBuilder setDiscordBotListKey(String key) {
        return this;
    }

    public CommandClientBuilder setListener(CommandListener listener) {
        this.listener = listener;
        return this;
    }

    public CommandClientBuilder setScheduleExecutor(ScheduledExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public CommandClientBuilder setShutdownAutomatically(boolean shutdownAutomatically) {
        this.shutdownAutomatically = shutdownAutomatically;
        return this;
    }

    public CommandClientBuilder setLinkedCacheSize(int linkedCacheSize) {
        this.linkedCacheSize = linkedCacheSize;
        return this;
    }

    public CommandClientBuilder setGuildSettingsManager(GuildSettingsManager manager) {
        this.manager = manager;
        return this;
    }
}

