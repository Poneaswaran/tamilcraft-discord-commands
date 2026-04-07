/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Command;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandListener;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.ContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.GuildSettingsManager;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

public interface CommandClient {
    public String getPrefix();

    public String getAltPrefix();

    public String[] getPrefixes();

    public Function<MessageReceivedEvent, String> getPrefixFunction();

    public String getTextualPrefix();

    public void addCommand(Command var1);

    public void addCommand(Command var1, int var2);

    public void addSlashCommand(SlashCommand var1);

    public void addSlashCommand(SlashCommand var1, int var2);

    public void addContextMenu(ContextMenu var1);

    public void addContextMenu(ContextMenu var1, int var2);

    public void removeCommand(String var1);

    public void addAnnotatedModule(Object var1);

    public void addAnnotatedModule(Object var1, Function<Command, Integer> var2);

    public void setListener(CommandListener var1);

    public CommandListener getListener();

    public List<Command> getCommands();

    public List<SlashCommand> getSlashCommands();

    public List<ContextMenu> getContextMenus();

    public boolean isManualUpsert();

    public String forcedGuildId();

    public OffsetDateTime getStartTime();

    public OffsetDateTime getCooldown(String var1);

    public int getRemainingCooldown(String var1);

    public void applyCooldown(String var1, int var2);

    public void cleanCooldowns();

    public int getCommandUses(Command var1);

    public int getCommandUses(String var1);

    public String getOwnerId();

    public long getOwnerIdLong();

    public String[] getCoOwnerIds();

    public long[] getCoOwnerIdsLong();

    public String getSuccess();

    public String getWarning();

    public String getError();

    public ScheduledExecutorService getScheduleExecutor();

    public String getServerInvite();

    public int getTotalGuilds();

    public String getHelpWord();

    public boolean usesLinkedDeletion();

    public <S> S getSettingsFor(Guild var1);

    public <M extends GuildSettingsManager> M getSettingsManager();

    public void shutdown();

    public void upsertInteractions(JDA var1);

    public void upsertInteractions(JDA var1, String var2);
}

