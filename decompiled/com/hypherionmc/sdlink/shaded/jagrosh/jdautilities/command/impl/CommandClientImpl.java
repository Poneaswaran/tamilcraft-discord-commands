/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.impl;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildJoinEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.ReadyEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.ShutdownEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.EventListener;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.AnnotatedModuleCompiler;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Command;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandListener;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.ContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.GuildSettingsManager;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.GuildSettingsProvider;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.MessageContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.MessageContextMenuEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.UserContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.UserContextMenuEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.utils.FixedSizeCache;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.utils.SafeIdUtil;
import com.hypherionmc.sdlink.shaded.json.JSONObject;
import com.hypherionmc.sdlink.shaded.json.JSONTokener;
import com.hypherionmc.sdlink.shaded.okhttp3.Call;
import com.hypherionmc.sdlink.shaded.okhttp3.Callback;
import com.hypherionmc.sdlink.shaded.okhttp3.FormBody;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import java.io.IOException;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandClientImpl
implements CommandClient,
EventListener {
    private static final Logger LOG = LoggerFactory.getLogger(CommandClient.class);
    private static final String DEFAULT_PREFIX = "@mention";
    private final OffsetDateTime start;
    private final Activity activity;
    private final OnlineStatus status;
    private final String ownerId;
    private final String[] coOwnerIds;
    private final String prefix;
    private final String altprefix;
    private final String[] prefixes;
    private final Function<MessageReceivedEvent, String> prefixFunction;
    private final Function<MessageReceivedEvent, Boolean> commandPreProcessFunction;
    private final BiFunction<MessageReceivedEvent, Command, Boolean> commandPreProcessBiFunction;
    private final String serverInvite;
    private final HashMap<String, Integer> commandIndex;
    private final HashMap<String, Integer> slashCommandIndex;
    private final ArrayList<Command> commands;
    private final ArrayList<SlashCommand> slashCommands;
    private final ArrayList<String> slashCommandIds;
    private final ArrayList<ContextMenu> contextMenus;
    private final HashMap<String, Integer> contextMenuIndex;
    private final String forcedGuildId;
    private final boolean manualUpsert;
    private final String success;
    private final String warning;
    private final String error;
    private final String botsKey;
    private final String carbonKey;
    private final HashMap<String, OffsetDateTime> cooldowns;
    private final HashMap<String, Integer> uses;
    private final FixedSizeCache<Long, Set<Message>> linkMap;
    private final boolean useHelp;
    private final boolean shutdownAutomatically;
    private final Consumer<CommandEvent> helpConsumer;
    private final String helpWord;
    private final ScheduledExecutorService executor;
    private final AnnotatedModuleCompiler compiler;
    private final GuildSettingsManager manager;
    private String textPrefix;
    private CommandListener listener = null;
    private int totalGuilds;

    public CommandClientImpl(String ownerId, String[] coOwnerIds, String prefix, String altprefix, String[] prefixes, Function<MessageReceivedEvent, String> prefixFunction, Function<MessageReceivedEvent, Boolean> commandPreProcessFunction, BiFunction<MessageReceivedEvent, Command, Boolean> commandPreProcessBiFunction, Activity activity, OnlineStatus status, String serverInvite, String success, String warning, String error, String carbonKey, String botsKey, ArrayList<Command> commands, ArrayList<SlashCommand> slashCommands, ArrayList<ContextMenu> contextMenus, String forcedGuildId, boolean manualUpsert, boolean useHelp, boolean shutdownAutomatically, Consumer<CommandEvent> helpConsumer, String helpWord, ScheduledExecutorService executor, int linkedCacheSize, AnnotatedModuleCompiler compiler, GuildSettingsManager manager) {
        Checks.check(ownerId != null, "Owner ID was set null or not set! Please provide an User ID to register as the owner!");
        if (!SafeIdUtil.checkId(ownerId)) {
            LOG.warn(String.format("The provided Owner ID (%s) was found unsafe! Make sure ID is a non-negative long!", ownerId));
        }
        if (coOwnerIds != null) {
            for (String coOwnerId : coOwnerIds) {
                if (SafeIdUtil.checkId(coOwnerId)) continue;
                LOG.warn(String.format("The provided CoOwner ID (%s) was found unsafe! Make sure ID is a non-negative long!", coOwnerId));
            }
        }
        this.start = OffsetDateTime.now();
        this.ownerId = ownerId;
        this.coOwnerIds = coOwnerIds;
        this.prefix = prefix == null || prefix.isEmpty() ? DEFAULT_PREFIX : prefix;
        this.altprefix = altprefix == null || altprefix.isEmpty() ? null : altprefix;
        String[] stringArray = this.prefixes = prefixes == null || prefixes.length == 0 ? null : prefixes;
        if (this.prefixes != null) {
            Arrays.sort(this.prefixes, Comparator.reverseOrder());
        }
        this.prefixFunction = prefixFunction;
        this.commandPreProcessFunction = commandPreProcessFunction;
        this.commandPreProcessBiFunction = commandPreProcessBiFunction;
        this.textPrefix = prefix;
        this.activity = activity;
        this.status = status;
        this.serverInvite = serverInvite;
        this.success = success == null ? "" : success;
        this.warning = warning == null ? "" : warning;
        this.error = error == null ? "" : error;
        this.carbonKey = carbonKey;
        this.botsKey = botsKey;
        this.commandIndex = new HashMap();
        this.slashCommandIndex = new HashMap();
        this.commands = new ArrayList();
        this.slashCommands = new ArrayList();
        this.slashCommandIds = new ArrayList();
        this.contextMenus = new ArrayList();
        this.contextMenuIndex = new HashMap();
        this.forcedGuildId = forcedGuildId;
        this.manualUpsert = manualUpsert;
        this.cooldowns = new HashMap();
        this.uses = new HashMap();
        this.linkMap = linkedCacheSize > 0 ? new FixedSizeCache(linkedCacheSize) : null;
        this.useHelp = useHelp;
        this.shutdownAutomatically = shutdownAutomatically;
        this.helpWord = helpWord == null ? "help" : helpWord;
        this.executor = executor == null ? Executors.newSingleThreadScheduledExecutor() : executor;
        this.compiler = compiler;
        this.manager = manager;
        this.helpConsumer = helpConsumer == null ? event -> {
            StringBuilder builder = new StringBuilder("**" + event.getSelfUser().getName() + "** commands:\n");
            Command.Category category = null;
            for (Command command : commands) {
                if (command.isHidden() || command.isOwnerCommand() && !event.isOwner()) continue;
                if (!Objects.equals(category, command.getCategory())) {
                    category = command.getCategory();
                    builder.append("\n\n  __").append(category == null ? "No Category" : category.getName()).append("__:\n");
                }
                builder.append("\n`").append(this.textPrefix).append(prefix == null ? " " : "").append(command.getName()).append(command.getArguments() == null ? "`" : " " + command.getArguments() + "`").append(" - ").append(command.getHelp());
            }
            User owner = event.getJDA().getUserById(ownerId);
            if (owner != null) {
                builder.append("\n\nFor additional help, contact **").append(owner.getName()).append("**#").append(owner.getDiscriminator());
                if (serverInvite != null) {
                    builder.append(" or join ").append(serverInvite);
                }
            }
            event.replyInDm(builder.toString(), unused -> {
                if (event.isFromType(ChannelType.TEXT)) {
                    event.reactSuccess();
                }
            }, t -> event.replyWarning("Help cannot be sent because you are blocking Direct Messages."));
        } : helpConsumer;
        for (Command command : commands) {
            this.addCommand(command);
        }
        for (SlashCommand command : slashCommands) {
            this.addSlashCommand(command);
        }
        for (ContextMenu menu : contextMenus) {
            this.addContextMenu(menu);
        }
    }

    @Override
    public void setListener(CommandListener listener) {
        this.listener = listener;
    }

    @Override
    public CommandListener getListener() {
        return this.listener;
    }

    @Override
    public List<Command> getCommands() {
        return this.commands;
    }

    @Override
    public List<SlashCommand> getSlashCommands() {
        return this.slashCommands;
    }

    @Override
    public List<ContextMenu> getContextMenus() {
        return this.contextMenus;
    }

    @Override
    public boolean isManualUpsert() {
        return this.manualUpsert;
    }

    @Override
    public String forcedGuildId() {
        return this.forcedGuildId;
    }

    @Override
    public OffsetDateTime getStartTime() {
        return this.start;
    }

    @Override
    public OffsetDateTime getCooldown(String name) {
        return this.cooldowns.get(name);
    }

    @Override
    public int getRemainingCooldown(String name) {
        if (this.cooldowns.containsKey(name)) {
            int time = (int)Math.ceil((double)OffsetDateTime.now().until(this.cooldowns.get(name), ChronoUnit.MILLIS) / 1000.0);
            if (time <= 0) {
                this.cooldowns.remove(name);
                return 0;
            }
            return time;
        }
        return 0;
    }

    @Override
    public void applyCooldown(String name, int seconds) {
        this.cooldowns.put(name, OffsetDateTime.now().plusSeconds(seconds));
    }

    @Override
    public void cleanCooldowns() {
        OffsetDateTime now = OffsetDateTime.now();
        this.cooldowns.keySet().stream().filter(str -> this.cooldowns.get(str).isBefore(now)).collect(Collectors.toList()).forEach(this.cooldowns::remove);
    }

    @Override
    public int getCommandUses(Command command) {
        return this.getCommandUses(command.getName());
    }

    @Override
    public int getCommandUses(String name) {
        return this.uses.getOrDefault(name, 0);
    }

    @Override
    public void addCommand(Command command) {
        this.addCommand(command, this.commands.size());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addCommand(Command command, int index) {
        if (index > this.commands.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index specified is invalid: [" + index + "/" + this.commands.size() + "]");
        }
        HashMap<String, Integer> hashMap = this.commandIndex;
        synchronized (hashMap) {
            String name = command.getName().toLowerCase(Locale.ROOT);
            if (this.commandIndex.containsKey(name)) {
                throw new IllegalArgumentException("Command added has a name or alias that has already been indexed: \"" + name + "\"!");
            }
            for (String alias : command.getAliases()) {
                if (!this.commandIndex.containsKey(alias.toLowerCase(Locale.ROOT))) continue;
                throw new IllegalArgumentException("Command added has a name or alias that has already been indexed: \"" + alias + "\"!");
            }
            if (index < this.commands.size()) {
                this.commandIndex.entrySet().stream().filter(entry -> (Integer)entry.getValue() >= index).collect(Collectors.toList()).forEach(entry -> this.commandIndex.put((String)entry.getKey(), (Integer)entry.getValue() + 1));
            }
            this.commandIndex.put(name, index);
            for (String alias : command.getAliases()) {
                this.commandIndex.put(alias.toLowerCase(Locale.ROOT), index);
            }
        }
        this.commands.add(index, command);
    }

    @Override
    public void addSlashCommand(SlashCommand command) {
        this.addSlashCommand(command, this.slashCommands.size());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSlashCommand(SlashCommand command, int index) {
        if (index > this.slashCommands.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index specified is invalid: [" + index + "/" + this.slashCommands.size() + "]");
        }
        HashMap<String, Integer> hashMap = this.slashCommandIndex;
        synchronized (hashMap) {
            String name = command.getName().toLowerCase(Locale.ROOT);
            if (this.slashCommandIndex.containsKey(name)) {
                throw new IllegalArgumentException("Command added has a name that has already been indexed: \"" + name + "\"!");
            }
            if (index < this.slashCommands.size()) {
                this.slashCommandIndex.entrySet().stream().filter(entry -> (Integer)entry.getValue() >= index).collect(Collectors.toList()).forEach(entry -> this.slashCommandIndex.put((String)entry.getKey(), (Integer)entry.getValue() + 1));
            }
            this.slashCommandIndex.put(name, index);
        }
        this.slashCommands.add(index, command);
    }

    @Override
    public void addContextMenu(ContextMenu menu) {
        this.addContextMenu(menu, this.contextMenus.size());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addContextMenu(ContextMenu menu, int index) {
        if (index > this.contextMenus.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index specified is invalid: [" + index + "/" + this.contextMenus.size() + "]");
        }
        HashMap<String, Integer> hashMap = this.contextMenuIndex;
        synchronized (hashMap) {
            String name = menu.getName();
            if (this.contextMenuIndex.containsKey(name) && this.contextMenuIndex.get(name).getClass().getName().equals(menu.getClass().getName())) {
                throw new IllegalArgumentException("Context Menu added has a name and class that has already been indexed: \"" + name + "\"!");
            }
            if (index < this.contextMenuIndex.size()) {
                this.contextMenuIndex.entrySet().stream().filter(entry -> (Integer)entry.getValue() >= index).collect(Collectors.toList()).forEach(entry -> this.contextMenuIndex.put((String)entry.getKey(), (Integer)entry.getValue() + 1));
            }
            this.contextMenuIndex.put(name, index);
        }
        this.contextMenus.add(index, menu);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeCommand(String name) {
        HashMap<String, Integer> hashMap = this.commandIndex;
        synchronized (hashMap) {
            if (!this.commandIndex.containsKey(name.toLowerCase(Locale.ROOT))) {
                throw new IllegalArgumentException("Name provided is not indexed: \"" + name + "\"!");
            }
            int targetIndex = this.commandIndex.remove(name.toLowerCase(Locale.ROOT));
            Command removedCommand = this.commands.remove(targetIndex);
            for (String alias : removedCommand.getAliases()) {
                this.commandIndex.remove(alias.toLowerCase(Locale.ROOT));
            }
            this.commandIndex.entrySet().stream().filter(entry -> (Integer)entry.getValue() > targetIndex).collect(Collectors.toList()).forEach(entry -> this.commandIndex.put((String)entry.getKey(), (Integer)entry.getValue() - 1));
        }
    }

    @Override
    public void addAnnotatedModule(Object module) {
        this.compiler.compile(module).forEach(this::addCommand);
    }

    @Override
    public void addAnnotatedModule(Object module, Function<Command, Integer> mapFunction) {
        this.compiler.compile(module).forEach(command -> this.addCommand((Command)command, (Integer)mapFunction.apply((Command)command)));
    }

    @Override
    public String getOwnerId() {
        return this.ownerId;
    }

    @Override
    public long getOwnerIdLong() {
        return Long.parseLong(this.ownerId);
    }

    @Override
    public String[] getCoOwnerIds() {
        return this.coOwnerIds;
    }

    @Override
    public long[] getCoOwnerIdsLong() {
        if (this.coOwnerIds == null) {
            return null;
        }
        long[] ids = new long[this.coOwnerIds.length];
        for (int i = 0; i < ids.length; ++i) {
            ids[i] = Long.parseLong(this.coOwnerIds[i]);
        }
        return ids;
    }

    @Override
    public String getSuccess() {
        return this.success;
    }

    @Override
    public String getWarning() {
        return this.warning;
    }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public ScheduledExecutorService getScheduleExecutor() {
        return this.executor;
    }

    @Override
    public String getServerInvite() {
        return this.serverInvite;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String[] getPrefixes() {
        return this.prefixes;
    }

    @Override
    public Function<MessageReceivedEvent, String> getPrefixFunction() {
        return this.prefixFunction;
    }

    @Override
    public String getAltPrefix() {
        return this.altprefix;
    }

    @Override
    public String getTextualPrefix() {
        return this.textPrefix;
    }

    @Override
    public int getTotalGuilds() {
        return this.totalGuilds;
    }

    @Override
    public String getHelpWord() {
        return this.helpWord;
    }

    @Override
    public boolean usesLinkedDeletion() {
        return this.linkMap != null;
    }

    @Override
    public <S> S getSettingsFor(Guild guild) {
        if (this.manager == null) {
            return null;
        }
        return (S)this.manager.getSettings(guild);
    }

    @Override
    public <M extends GuildSettingsManager> M getSettingsManager() {
        return (M)this.manager;
    }

    @Override
    public void shutdown() {
        Object manager = this.getSettingsManager();
        if (manager != null) {
            manager.shutdown();
        }
        this.executor.shutdown();
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            this.onMessageReceived((MessageReceivedEvent)event);
        } else if (event instanceof SlashCommandInteractionEvent) {
            this.onSlashCommand((SlashCommandInteractionEvent)event);
        } else if (event instanceof MessageContextInteractionEvent) {
            this.onMessageContextMenu((MessageContextInteractionEvent)event);
        } else if (event instanceof UserContextInteractionEvent) {
            this.onUserContextMenu((UserContextInteractionEvent)event);
        } else if (event instanceof CommandAutoCompleteInteractionEvent) {
            this.onCommandAutoComplete((CommandAutoCompleteInteractionEvent)event);
        } else if (event instanceof MessageDeleteEvent && this.usesLinkedDeletion()) {
            this.onMessageDelete((MessageDeleteEvent)event);
        } else if (event instanceof GuildJoinEvent) {
            if (((GuildJoinEvent)event).getGuild().getSelfMember().getTimeJoined().plusMinutes(10L).isAfter(OffsetDateTime.now())) {
                this.sendStats(event.getJDA());
            }
        } else if (event instanceof GuildLeaveEvent) {
            this.sendStats(event.getJDA());
        } else if (event instanceof ReadyEvent) {
            this.onReady((ReadyEvent)event);
        } else if (event instanceof ShutdownEvent && this.shutdownAutomatically) {
            this.shutdown();
        }
    }

    private void onReady(ReadyEvent event) {
        Object manager;
        if (!event.getJDA().getSelfUser().isBot()) {
            LOG.error("JDA-Utilities does not support CLIENT accounts.");
            event.getJDA().shutdown();
            return;
        }
        String string = this.textPrefix = this.prefix.equals(DEFAULT_PREFIX) ? "@" + event.getJDA().getSelfUser().getName() + " " : this.prefix;
        if (this.activity != null) {
            event.getJDA().getPresence().setPresence(this.status == null ? OnlineStatus.ONLINE : this.status, "default".equals(this.activity.getName()) ? Activity.playing("Type " + this.textPrefix + this.helpWord) : this.activity);
        }
        if ((manager = this.getSettingsManager()) != null) {
            manager.init();
        }
        if (!this.manualUpsert) {
            this.upsertInteractions(event.getJDA());
        }
        this.sendStats(event.getJDA());
    }

    @Override
    public void upsertInteractions(JDA jda) {
        this.upsertInteractions(jda, this.forcedGuildId);
    }

    @Override
    public void upsertInteractions(JDA jda, String serverId) {
        ArrayList<CommandData> data = new ArrayList<CommandData>();
        List<SlashCommand> slashCommands = this.getSlashCommands();
        HashMap<String, SlashCommand> slashCommandMap = new HashMap<String, SlashCommand>();
        List<ContextMenu> contextMenus = this.getContextMenus();
        HashMap<String, ContextMenu> contextMenuMap = new HashMap<String, ContextMenu>();
        for (SlashCommand command : slashCommands) {
            data.add(command.buildCommandData());
            slashCommandMap.put(command.getName(), command);
        }
        for (ContextMenu menu : contextMenus) {
            data.add(menu.buildCommandData());
            contextMenuMap.put(menu.getName(), menu);
        }
        if (serverId != null) {
            Guild server = jda.getGuildById(serverId);
            if (server == null) {
                LOG.error("Specified forced guild is null! Slash Commands will NOT be added! Is the bot added?");
                return;
            }
            server.updateCommands().addCommands(data).queue(priv -> LOG.debug("Successfully added " + this.commands.size() + " slash commands and " + contextMenus.size() + " menus to server " + server.getName()), error -> LOG.error("Could not upsert commands! Does the bot have the applications.commands scope?" + error));
        } else {
            jda.updateCommands().addCommands(data).queue(commands -> LOG.debug("Successfully added " + commands.size() + " slash commands!"));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        MessageParts parts = this.getParts(event);
        if (parts != null) {
            if (this.useHelp && parts.command.equalsIgnoreCase(this.helpWord)) {
                CommandEvent cevent = new CommandEvent(event, parts.prefixUsed, parts.args, this);
                if (this.listener != null) {
                    this.listener.onCommand(cevent, null);
                }
                this.helpConsumer.accept(cevent);
                if (this.listener != null) {
                    this.listener.onCompletedCommand(cevent, null);
                }
                return;
            }
            if (event.isFromType(ChannelType.PRIVATE) || event.getChannel().canTalk()) {
                Command command;
                String name = parts.command;
                String args2 = parts.args;
                HashMap<String, Integer> hashMap = this.commandIndex;
                synchronized (hashMap) {
                    int i = this.commandIndex.getOrDefault(name.toLowerCase(Locale.ROOT), -1);
                    command = i != -1 ? this.commands.get(i) : null;
                }
                if (command != null) {
                    CommandEvent cevent = new CommandEvent(event, parts.prefixUsed, args2, this);
                    if (this.listener != null) {
                        this.listener.onCommand(cevent, command);
                    }
                    this.uses.put(command.getName(), this.uses.getOrDefault(command.getName(), 0) + 1);
                    if (this.commandPreProcessFunction != null || this.commandPreProcessBiFunction != null) {
                        if (this.commandPreProcessFunction != null && this.commandPreProcessFunction.apply(event).booleanValue()) {
                            command.run(cevent);
                            return;
                        }
                        if (this.commandPreProcessBiFunction != null && this.commandPreProcessBiFunction.apply(event, command).booleanValue()) {
                            command.run(cevent);
                            return;
                        }
                        return;
                    }
                    command.run(cevent);
                    return;
                }
            }
        }
        if (this.listener != null) {
            this.listener.onNonCommandMessage(event);
        }
    }

    @Nullable
    private MessageParts getParts(MessageReceivedEvent event) {
        Collection<String> prefixes;
        String prefix;
        GuildSettingsProvider settings;
        String rawContent = event.getMessage().getContentRaw();
        GuildSettingsProvider guildSettingsProvider = settings = event.isFromType(ChannelType.TEXT) ? this.provideSettings(event.getGuild()) : null;
        if ((this.prefix.equals(DEFAULT_PREFIX) || this.altprefix != null && this.altprefix.equals(DEFAULT_PREFIX)) && (rawContent.startsWith("<@" + event.getJDA().getSelfUser().getId() + ">") || rawContent.startsWith("<@!" + event.getJDA().getSelfUser().getId() + ">"))) {
            int prefixLength = rawContent.indexOf(62) + 2;
            return this.makeMessageParts(rawContent, prefixLength);
        }
        if (this.prefixFunction != null && (prefix = this.prefixFunction.apply(event)) != null && rawContent.startsWith(prefix)) {
            int prefixLength = prefix.length();
            return this.makeMessageParts(rawContent, prefixLength);
        }
        String lowerCaseContent = rawContent.toLowerCase(Locale.ROOT);
        if (lowerCaseContent.startsWith(this.prefix.toLowerCase(Locale.ROOT))) {
            int prefixLength = this.prefix.length();
            return this.makeMessageParts(rawContent, prefixLength);
        }
        if (this.altprefix != null && lowerCaseContent.startsWith(this.altprefix.toLowerCase(Locale.ROOT))) {
            int prefixLength = this.altprefix.length();
            return this.makeMessageParts(rawContent, prefixLength);
        }
        if (this.prefixes != null) {
            for (String pre : this.prefixes) {
                if (!lowerCaseContent.startsWith(pre.toLowerCase(Locale.ROOT))) continue;
                int prefixLength = pre.length();
                return this.makeMessageParts(rawContent, prefixLength);
            }
        }
        if (settings != null && (prefixes = settings.getPrefixes()) != null) {
            for (String prefix2 : prefixes) {
                if (!lowerCaseContent.startsWith(prefix2.toLowerCase(Locale.ROOT))) continue;
                int prefixLength = prefix2.length();
                return this.makeMessageParts(rawContent, prefixLength);
            }
        }
        return null;
    }

    @NotNull
    private MessageParts makeMessageParts(String rawContent, int prefixLength) {
        String cmd = null;
        for (int i = prefixLength; i < rawContent.length(); ++i) {
            if (!Character.isWhitespace(rawContent.charAt(i))) continue;
            cmd = rawContent.substring(prefixLength, i);
            break;
        }
        String args2 = "";
        if (cmd == null) {
            cmd = rawContent.substring(prefixLength);
        } else {
            for (int i = prefixLength + cmd.length(); i < rawContent.length(); ++i) {
                if (Character.isWhitespace(rawContent.charAt(i))) continue;
                args2 = rawContent.substring(i);
                break;
            }
        }
        LOG.trace("Received command named '{}' with args '{}'", (Object)cmd, (Object)args2);
        return new MessageParts(rawContent.substring(0, prefixLength), cmd, args2);
    }

    private void onSlashCommand(SlashCommandInteractionEvent event) {
        SlashCommand command = this.findSlashCommand(event.getFullCommandName());
        SlashCommandEvent commandEvent = new SlashCommandEvent(event, this);
        if (command != null) {
            if (this.listener != null) {
                this.listener.onSlashCommand(commandEvent, command);
            }
            this.uses.put(command.getName(), this.uses.getOrDefault(command.getName(), 0) + 1);
            command.run(commandEvent);
        }
    }

    private void onCommandAutoComplete(CommandAutoCompleteInteractionEvent event) {
        SlashCommand command = this.findSlashCommand(event.getFullCommandName());
        if (command != null) {
            command.onAutoComplete(event);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SlashCommand findSlashCommand(String path) {
        String[] parts = path.split(" ");
        SlashCommand[] slashCommandArray = this.slashCommandIndex;
        synchronized (this.slashCommandIndex) {
            int i = this.slashCommandIndex.getOrDefault(parts[0].toLowerCase(Locale.ROOT), -1);
            SlashCommand command = i != -1 ? this.slashCommands.get(i) : null;
            // ** MonitorExit[var4_3] (shouldn't be in output)
            if (command == null) {
                return null;
            }
            switch (parts.length) {
                case 1: {
                    return command;
                }
                case 2: {
                    for (SlashCommand cmd : command.getChildren()) {
                        if (!cmd.isCommandFor(parts[1]) || cmd.getSubcommandGroup() != null) continue;
                        return cmd;
                    }
                    return null;
                }
                case 3: {
                    for (SlashCommand cmd : command.getChildren()) {
                        if (!cmd.isCommandFor(parts[2]) || cmd.getSubcommandGroup() == null || !cmd.getSubcommandGroup().getName().equals(parts[1])) continue;
                        return cmd;
                    }
                    return null;
                }
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onUserContextMenu(UserContextInteractionEvent event) {
        UserContextMenu menu;
        HashMap<String, Integer> hashMap = this.contextMenuIndex;
        synchronized (hashMap) {
            int i = this.contextMenuIndex.getOrDefault(event.getName(), -1);
            ContextMenu c = i != -1 ? this.contextMenus.get(i) : null;
            menu = c instanceof UserContextMenu ? (UserContextMenu)c : null;
        }
        UserContextMenuEvent menuEvent = new UserContextMenuEvent(event.getJDA(), event.getResponseNumber(), event, this);
        if (menu != null) {
            if (this.listener != null) {
                this.listener.onUserContextMenu(menuEvent, menu);
            }
            this.uses.put(menu.getName(), this.uses.getOrDefault(menu.getName(), 0) + 1);
            menu.run(menuEvent);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onMessageContextMenu(MessageContextInteractionEvent event) {
        MessageContextMenu menu;
        HashMap<String, Integer> hashMap = this.contextMenuIndex;
        synchronized (hashMap) {
            int i = this.contextMenuIndex.getOrDefault(event.getName(), -1);
            ContextMenu c = i != -1 ? this.contextMenus.get(i) : null;
            menu = c instanceof MessageContextMenu ? (MessageContextMenu)c : null;
        }
        MessageContextMenuEvent menuEvent = new MessageContextMenuEvent(event.getJDA(), event.getResponseNumber(), event, this);
        if (menu != null) {
            if (this.listener != null) {
                this.listener.onMessageContextMenu(menuEvent, menu);
            }
            this.uses.put(menu.getName(), this.uses.getOrDefault(menu.getName(), 0) + 1);
            menu.run(menuEvent);
        }
    }

    private void sendStats(JDA jda) {
        Request.Builder builder;
        OkHttpClient client = jda.getHttpClient();
        if (this.carbonKey != null) {
            FormBody.Builder bodyBuilder = new FormBody.Builder().add("key", this.carbonKey).add("servercount", Integer.toString(jda.getGuilds().size()));
            if (jda.getShardInfo() != JDA.ShardInfo.SINGLE) {
                bodyBuilder.add("shard_id", Integer.toString(jda.getShardInfo().getShardId())).add("shard_count", Integer.toString(jda.getShardInfo().getShardTotal()));
            }
            builder = new Request.Builder().post(bodyBuilder.build()).url("https://www.carbonitex.net/discord/data/botdata.php");
            client.newCall(builder.build()).enqueue(new Callback(){

                @Override
                public void onResponse(Call call, Response response) {
                    LOG.info("Successfully send information to carbonitex.net");
                    response.close();
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    LOG.error("Failed to send information to carbonitex.net ", (Throwable)e);
                }
            });
        }
        if (this.botsKey != null) {
            JSONObject body = new JSONObject().put("guildCount", jda.getGuilds().size());
            if (jda.getShardInfo() != JDA.ShardInfo.SINGLE) {
                body.put("shardId", jda.getShardInfo().getShardId()).put("shardCount", jda.getShardInfo().getShardTotal());
            }
            builder = new Request.Builder().post(RequestBody.create(MediaType.parse("application/json"), body.toString())).url("https://discord.bots.gg/api/v1/bots/" + jda.getSelfUser().getId() + "/stats").header("Authorization", this.botsKey).header("Content-Type", "application/json");
            client.newCall(builder.build()).enqueue(new Callback(){

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        LOG.info("Successfully sent information to discord.bots.gg");
                        try (Reader reader = response.body().charStream();){
                            CommandClientImpl.this.totalGuilds = new JSONObject(new JSONTokener(reader)).getInt("guildCount");
                        }
                        catch (Exception ex) {
                            LOG.error("Failed to retrieve bot shard information from discord.bots.gg ", (Throwable)ex);
                        }
                    } else {
                        LOG.error("Failed to send information to discord.bots.gg: " + response.body().string());
                    }
                    response.close();
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    LOG.error("Failed to send information to discord.bots.gg ", (Throwable)e);
                }
            });
        } else {
            this.totalGuilds = jda.getShardManager() != null ? (int)jda.getShardManager().getGuildCache().size() : (int)jda.getGuildCache().size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onMessageDelete(MessageDeleteEvent event) {
        if (!event.isFromGuild()) {
            return;
        }
        FixedSizeCache<Long, Set<Message>> fixedSizeCache = this.linkMap;
        synchronized (fixedSizeCache) {
            if (this.linkMap.contains(event.getMessageIdLong())) {
                Set<Message> messages = this.linkMap.get(event.getMessageIdLong());
                if (messages.size() > 1 && event.getGuild().getSelfMember().hasPermission((GuildChannel)event.getChannel().asTextChannel(), Permission.MESSAGE_MANAGE)) {
                    event.getChannel().asTextChannel().deleteMessages(messages).queue(unused -> {}, ignored -> {});
                } else if (messages.size() > 0) {
                    messages.forEach(m -> m.delete().queue(unused -> {}, ignored -> {}));
                }
            }
        }
    }

    private GuildSettingsProvider provideSettings(Guild guild) {
        Object settings = this.getSettingsFor(guild);
        if (settings instanceof GuildSettingsProvider) {
            return (GuildSettingsProvider)settings;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void linkIds(long callId, Message message) {
        if (!this.usesLinkedDeletion()) {
            return;
        }
        FixedSizeCache<Long, Set<Message>> fixedSizeCache = this.linkMap;
        synchronized (fixedSizeCache) {
            Set<Message> stored = this.linkMap.get(callId);
            if (stored != null) {
                stored.add(message);
            } else {
                stored = new HashSet<Message>();
                stored.add(message);
                this.linkMap.add(callId, stored);
            }
        }
    }

    private static class MessageParts {
        private final String prefixUsed;
        private final String command;
        private final String args;

        private MessageParts(String prefixUsed, String command, String args2) {
            this.prefixUsed = prefixUsed;
            this.command = command;
            this.args = args2;
        }
    }
}

