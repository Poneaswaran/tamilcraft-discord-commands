/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CooldownScope;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Interaction;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class Command
extends Interaction {
    protected String name = "null";
    protected String help = "no help available";
    protected Category category = null;
    protected String arguments = null;
    protected boolean nsfwOnly = false;
    protected String requiredRole = null;
    protected String[] aliases = new String[0];
    protected Command[] children = new Command[0];
    protected BiConsumer<CommandEvent, Command> helpBiConsumer = null;
    protected boolean usesTopicTags = true;
    protected boolean hidden = false;

    protected abstract void execute(CommandEvent var1);

    /*
     * WARNING - void declaration
     */
    public final void run(CommandEvent event) {
        if (!event.getArgs().isEmpty()) {
            void var5_14;
            String[] stringArray = Arrays.copyOf(event.getArgs().split("\\s+", 2), 2);
            if (this.helpBiConsumer != null && stringArray[0].equalsIgnoreCase(event.getClient().getHelpWord())) {
                this.helpBiConsumer.accept(event, this);
                return;
            }
            Command[] commandArray = this.getChildren();
            int n = commandArray.length;
            boolean bl = false;
            while (var5_14 < n) {
                Command cmd = commandArray[var5_14];
                if (cmd.isCommandFor(stringArray[0])) {
                    event.setArgs(stringArray[1] == null ? "" : stringArray[1]);
                    cmd.run(event);
                    return;
                }
                ++var5_14;
            }
        }
        if (this.ownerCommand && !event.isOwner()) {
            this.terminate(event, null);
            return;
        }
        if (this.category != null && !this.category.test(event)) {
            this.terminate(event, this.category.getFailureResponse());
            return;
        }
        if (event.isFromType(ChannelType.TEXT) && !this.isAllowed(event.getTextChannel())) {
            this.terminate(event, "That command cannot be used in this channel!");
            return;
        }
        if (this.requiredRole != null && (!event.isFromType(ChannelType.TEXT) || event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(this.requiredRole)))) {
            this.terminate(event, event.getClient().getError() + " You must have a role called `" + this.requiredRole + "` to use that!");
            return;
        }
        if (!event.isFromType(ChannelType.PRIVATE)) {
            for (Permission permission : this.userPermissions) {
                if (permission.isChannel()) {
                    if (event.getMember().hasPermission((GuildChannel)event.getGuildChannel(), permission)) continue;
                    this.terminate(event, String.format(this.userMissingPermMessage, event.getClient().getError(), permission.getName(), "channel"));
                    return;
                }
                if (event.getMember().hasPermission(permission)) continue;
                this.terminate(event, String.format(this.userMissingPermMessage, event.getClient().getError(), permission.getName(), "server"));
                return;
            }
            for (Permission permission : this.botPermissions) {
                if (permission.isChannel()) {
                    if (permission.isVoice()) {
                        AudioChannelUnion vc;
                        GuildVoiceState gvc = event.getMember().getVoiceState();
                        AudioChannelUnion audioChannelUnion = vc = gvc == null ? null : gvc.getChannel();
                        if (vc == null) {
                            this.terminate(event, event.getClient().getError() + " You must be in a voice channel to use that!");
                            return;
                        }
                        if (event.getSelfMember().hasPermission((GuildChannel)vc, permission)) continue;
                        this.terminate(event, String.format(this.botMissingPermMessage, event.getClient().getError(), permission.getName(), "voice channel"));
                        return;
                    }
                    if (event.getSelfMember().hasPermission((GuildChannel)event.getGuildChannel(), permission)) continue;
                    this.terminate(event, String.format(this.botMissingPermMessage, event.getClient().getError(), permission.getName(), "channel"));
                    return;
                }
                if (event.getSelfMember().hasPermission(permission)) continue;
                this.terminate(event, String.format(this.botMissingPermMessage, event.getClient().getError(), permission.getName(), "server"));
                return;
            }
            if (this.nsfwOnly && event.isFromType(ChannelType.TEXT) && !event.getTextChannel().isNSFW()) {
                this.terminate(event, "This command may only be used in NSFW text channels!");
                return;
            }
        } else if (this.guildOnly) {
            this.terminate(event, event.getClient().getError() + " This command cannot be used in direct messages");
            return;
        }
        if (this.cooldown > 0 && !event.isOwner()) {
            String string = this.getCooldownKey(event);
            int remaining = event.getClient().getRemainingCooldown(string);
            if (remaining > 0) {
                this.terminate(event, this.getCooldownError(event, remaining));
                return;
            }
            event.getClient().applyCooldown(string, this.cooldown);
        }
        try {
            this.execute(event);
        }
        catch (Throwable throwable) {
            if (event.getClient().getListener() != null) {
                event.getClient().getListener().onCommandException(event, this, throwable);
                return;
            }
            throw throwable;
        }
        if (event.getClient().getListener() != null) {
            event.getClient().getListener().onCompletedCommand(event, this);
        }
    }

    public boolean isCommandFor(String input) {
        if (this.name.equalsIgnoreCase(input)) {
            return true;
        }
        if (this.aliases != null) {
            for (String alias : this.aliases) {
                if (!alias.equalsIgnoreCase(input)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isAllowed(TextChannel channel) {
        String lowerCat;
        if (!this.usesTopicTags) {
            return true;
        }
        if (channel == null) {
            return true;
        }
        String topic = channel.getTopic();
        if (topic == null || topic.isEmpty()) {
            return true;
        }
        topic = topic.toLowerCase(Locale.ROOT);
        String lowerName = this.name.toLowerCase(Locale.ROOT);
        if (topic.contains("{" + lowerName + "}")) {
            return true;
        }
        if (topic.contains("{-" + lowerName + "}")) {
            return false;
        }
        String string = lowerCat = this.category == null ? null : this.category.getName().toLowerCase(Locale.ROOT);
        if (lowerCat != null) {
            if (topic.contains("{" + lowerCat + "}")) {
                return true;
            }
            if (topic.contains("{-" + lowerCat + "}")) {
                return false;
            }
        }
        return !topic.contains("{-all}");
    }

    public String getName() {
        return this.name;
    }

    public String getHelp() {
        return this.help;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getArguments() {
        return this.arguments;
    }

    public boolean isGuildOnly() {
        return this.guildOnly;
    }

    public String getRequiredRole() {
        return this.requiredRole;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public Command[] getChildren() {
        return this.children;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    private void terminate(CommandEvent event, String message) {
        if (message != null) {
            event.reply(message);
        }
        if (event.getClient().getListener() != null) {
            event.getClient().getListener().onTerminatedCommand(event, this);
        }
    }

    public String getCooldownKey(CommandEvent event) {
        switch (this.cooldownScope) {
            case USER: {
                return this.cooldownScope.genKey(this.name, event.getAuthor().getIdLong());
            }
            case USER_GUILD: {
                return event.getGuild() != null ? this.cooldownScope.genKey(this.name, event.getAuthor().getIdLong(), event.getGuild().getIdLong()) : CooldownScope.USER_CHANNEL.genKey(this.name, event.getAuthor().getIdLong(), event.getChannel().getIdLong());
            }
            case USER_CHANNEL: {
                return this.cooldownScope.genKey(this.name, event.getAuthor().getIdLong(), event.getChannel().getIdLong());
            }
            case GUILD: {
                return event.getGuild() != null ? this.cooldownScope.genKey(this.name, event.getGuild().getIdLong()) : CooldownScope.CHANNEL.genKey(this.name, event.getChannel().getIdLong());
            }
            case CHANNEL: {
                return this.cooldownScope.genKey(this.name, event.getChannel().getIdLong());
            }
            case SHARD: {
                return event.getJDA().getShardInfo() != JDA.ShardInfo.SINGLE ? this.cooldownScope.genKey(this.name, event.getJDA().getShardInfo().getShardId()) : CooldownScope.GLOBAL.genKey(this.name, 0L);
            }
            case USER_SHARD: {
                return event.getJDA().getShardInfo() != JDA.ShardInfo.SINGLE ? this.cooldownScope.genKey(this.name, event.getAuthor().getIdLong(), event.getJDA().getShardInfo().getShardId()) : CooldownScope.USER.genKey(this.name, event.getAuthor().getIdLong());
            }
            case GLOBAL: {
                return this.cooldownScope.genKey(this.name, 0L);
            }
        }
        return "";
    }

    public String getCooldownError(CommandEvent event, int remaining) {
        if (remaining <= 0) {
            return null;
        }
        String front = event.getClient().getWarning() + " That command is on cooldown for " + remaining + " more seconds";
        if (this.cooldownScope.equals((Object)CooldownScope.USER)) {
            return front + "!";
        }
        if (this.cooldownScope.equals((Object)CooldownScope.USER_GUILD) && event.getGuild() == null) {
            return front + " " + CooldownScope.USER_CHANNEL.errorSpecification + "!";
        }
        if (this.cooldownScope.equals((Object)CooldownScope.GUILD) && event.getGuild() == null) {
            return front + " " + CooldownScope.CHANNEL.errorSpecification + "!";
        }
        return front + " " + this.cooldownScope.errorSpecification + "!";
    }

    public static class Category {
        private final String name;
        private final String failResponse;
        private final Predicate<CommandEvent> predicate;

        public Category(String name) {
            this.name = name;
            this.failResponse = null;
            this.predicate = null;
        }

        public Category(String name, Predicate<CommandEvent> predicate) {
            this.name = name;
            this.failResponse = null;
            this.predicate = predicate;
        }

        public Category(String name, String failResponse, Predicate<CommandEvent> predicate) {
            this.name = name;
            this.failResponse = failResponse;
            this.predicate = predicate;
        }

        public String getName() {
            return this.name;
        }

        public String getFailureResponse() {
            return this.failResponse;
        }

        public boolean test(CommandEvent event) {
            return this.predicate == null || this.predicate.test(event);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Category)) {
                return false;
            }
            Category other = (Category)obj;
            return Objects.equals(this.name, other.name) && Objects.equals(this.predicate, other.predicate) && Objects.equals(this.failResponse, other.failResponse);
        }

        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.name);
            hash = 17 * hash + Objects.hashCode(this.failResponse);
            hash = 17 * hash + Objects.hashCode(this.predicate);
            return hash;
        }
    }
}

