/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Method;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EncodingUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Route {
    public static final List<String> MAJOR_PARAMETER_NAMES = Helpers.listOf("guild_id", "channel_id", "webhook_id", "interaction_token");
    private final Method method;
    private final int paramCount;
    private final String[] template;
    private final boolean isInteraction;

    @Nonnull
    public static Route custom(@Nonnull Method method, @Nonnull String route) {
        Checks.notNull((Object)method, "Method");
        Checks.notEmpty(route, "Route");
        Checks.noWhitespace(route, "Route");
        return new Route(method, route);
    }

    @Nonnull
    public static Route delete(@Nonnull String route) {
        return Route.custom(Method.DELETE, route);
    }

    @Nonnull
    public static Route post(@Nonnull String route) {
        return Route.custom(Method.POST, route);
    }

    @Nonnull
    public static Route put(@Nonnull String route) {
        return Route.custom(Method.PUT, route);
    }

    @Nonnull
    public static Route patch(@Nonnull String route) {
        return Route.custom(Method.PATCH, route);
    }

    @Nonnull
    public static Route get(@Nonnull String route) {
        return Route.custom(Method.GET, route);
    }

    private Route(Method method, String route, boolean isInteraction) {
        this.method = method;
        this.template = Helpers.split(route, "/");
        this.isInteraction = isInteraction;
        int paramCount = 0;
        for (String element : this.template) {
            int opening = Helpers.countMatches(element, '{');
            int closing = Helpers.countMatches(element, '}');
            if (element.startsWith("{") && element.endsWith("}")) {
                Checks.check(closing == 1 && opening == 1, "Route element has invalid syntax: '%s'", (Object)element);
                ++paramCount;
                continue;
            }
            if (opening <= 0 && closing <= 0) continue;
            throw new IllegalArgumentException("Route element has invalid syntax: '" + element + "'");
        }
        this.paramCount = paramCount;
    }

    private Route(Method method, String route) {
        this(method, route, false);
    }

    public boolean isInteractionBucket() {
        return this.isInteraction;
    }

    @Nonnull
    public Method getMethod() {
        return this.method;
    }

    @Nonnull
    public String getRoute() {
        return String.join((CharSequence)"/", this.template);
    }

    public int getParamCount() {
        return this.paramCount;
    }

    @Nonnull
    public CompiledRoute compile(String ... params) {
        Checks.noneNull(params, "Arguments");
        Checks.check(params.length == this.paramCount, "Error Compiling Route: [%s], incorrect amount of parameters provided. Expected: %d, Provided: %d", this, this.paramCount, params.length);
        StringJoiner major = new StringJoiner(":").setEmptyValue("n/a");
        StringJoiner compiledRoute = new StringJoiner("/");
        int paramIndex = 0;
        for (String element : this.template) {
            if (element.charAt(0) == '{') {
                String name = element.substring(1, element.length() - 1);
                String value = params[paramIndex++];
                if (MAJOR_PARAMETER_NAMES.contains(name)) {
                    if (value.length() > 30) {
                        major.add(name + "=" + Integer.toUnsignedString(value.hashCode()));
                    } else {
                        major.add(name + "=" + value);
                    }
                }
                compiledRoute.add(EncodingUtil.encodeUTF8(value));
                continue;
            }
            compiledRoute.add(element);
        }
        return new CompiledRoute(this, compiledRoute.toString(), major.toString());
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.method, Arrays.hashCode(this.template)});
    }

    public boolean equals(Object o) {
        if (!(o instanceof Route)) {
            return false;
        }
        Route oRoute = (Route)o;
        return this.method.equals((Object)oRoute.method) && Arrays.equals(this.template, oRoute.template);
    }

    public String toString() {
        return (Object)((Object)this.method) + "/" + this.getRoute();
    }

    public class CompiledRoute {
        private final Route baseRoute;
        private final String major;
        private final String compiledRoute;
        private final List<String> query;

        private CompiledRoute(Route baseRoute, String compiledRoute, String major) {
            this.baseRoute = baseRoute;
            this.compiledRoute = compiledRoute;
            this.major = major;
            this.query = null;
        }

        private CompiledRoute(CompiledRoute original, List<String> query) {
            this.baseRoute = original.baseRoute;
            this.compiledRoute = original.compiledRoute;
            this.major = original.major;
            this.query = query;
        }

        @Nonnull
        @CheckReturnValue
        public CompiledRoute withQueryParams(String ... params) {
            ArrayList<String> newQuery;
            Checks.notNull(params, "Params");
            Checks.check(params.length >= 2, "Params length must be at least 2");
            Checks.check((params.length & 1) == 0, "Params length must be a multiple of 2");
            if (this.query == null) {
                newQuery = new ArrayList<String>(params.length / 2);
            } else {
                newQuery = new ArrayList(this.query.size() + params.length / 2);
                newQuery.addAll(this.query);
            }
            for (int i = 0; i < params.length; i += 2) {
                Checks.notEmpty(params[i], "Query key [" + i / 2 + "]");
                Checks.notNull(params[i + 1], "Query value [" + i / 2 + "]");
                newQuery.add(params[i] + '=' + EncodingUtil.encodeUTF8(params[i + 1]));
            }
            return new CompiledRoute(this, newQuery);
        }

        @Nonnull
        public String getMajorParameters() {
            return this.major;
        }

        @Nonnull
        public String getCompiledRoute() {
            if (this.query == null) {
                return this.compiledRoute;
            }
            return this.compiledRoute + '?' + String.join((CharSequence)"&", this.query);
        }

        @Nonnull
        public Route getBaseRoute() {
            return this.baseRoute;
        }

        @Nonnull
        public Method getMethod() {
            return this.baseRoute.method;
        }

        public int hashCode() {
            return (this.compiledRoute + Route.this.method.toString()).hashCode();
        }

        public boolean equals(Object o) {
            if (!(o instanceof CompiledRoute)) {
                return false;
            }
            CompiledRoute oCompiled = (CompiledRoute)o;
            return this.baseRoute.equals(oCompiled.getBaseRoute()) && this.compiledRoute.equals(oCompiled.compiledRoute);
        }

        public String toString() {
            return new EntityString(this).setType(Route.this.method).addMetadata("compiledRoute", this.compiledRoute).toString();
        }
    }

    public static class Templates {
        public static final Route GET_TEMPLATE = new Route(Method.GET, "guilds/templates/{code}");
        public static final Route SYNC_TEMPLATE = new Route(Method.PUT, "guilds/{guild_id}/templates/{code}");
        public static final Route CREATE_TEMPLATE = new Route(Method.POST, "guilds/{guild_id}/templates");
        public static final Route MODIFY_TEMPLATE = new Route(Method.PATCH, "guilds/{guild_id}/templates/{code}");
        public static final Route DELETE_TEMPLATE = new Route(Method.DELETE, "guilds/{guild_id}/templates/{code}");
        public static final Route GET_GUILD_TEMPLATES = new Route(Method.GET, "guilds/{guild_id}/templates");
        public static final Route CREATE_GUILD_FROM_TEMPLATE = new Route(Method.POST, "guilds/templates/{code}");
    }

    public static class Invites {
        public static final Route GET_INVITE = new Route(Method.GET, "invites/{code}");
        public static final Route GET_GUILD_INVITES = new Route(Method.GET, "guilds/{guild_id}/invites");
        public static final Route GET_CHANNEL_INVITES = new Route(Method.GET, "channels/{channel_id}/invites");
        public static final Route CREATE_INVITE = new Route(Method.POST, "channels/{channel_id}/invites");
        public static final Route DELETE_INVITE = new Route(Method.DELETE, "invites/{code}");
    }

    public static class Messages {
        public static final Route EDIT_MESSAGE = new Route(Method.PATCH, "channels/{channel_id}/messages/{message_id}");
        public static final Route SEND_MESSAGE = new Route(Method.POST, "channels/{channel_id}/messages");
        public static final Route GET_PINNED_MESSAGES = new Route(Method.GET, "channels/{channel_id}/pins");
        public static final Route ADD_PINNED_MESSAGE = new Route(Method.PUT, "channels/{channel_id}/pins/{message_id}");
        public static final Route REMOVE_PINNED_MESSAGE = new Route(Method.DELETE, "channels/{channel_id}/pins/{message_id}");
        public static final Route ADD_REACTION = new Route(Method.PUT, "channels/{channel_id}/messages/{message_id}/reactions/{reaction_code}/{user_id}");
        public static final Route REMOVE_REACTION = new Route(Method.DELETE, "channels/{channel_id}/messages/{message_id}/reactions/{reaction_code}/{user_id}");
        public static final Route REMOVE_ALL_REACTIONS = new Route(Method.DELETE, "channels/{channel_id}/messages/{message_id}/reactions");
        public static final Route GET_REACTION_USERS = new Route(Method.GET, "channels/{channel_id}/messages/{message_id}/reactions/{reaction_code}");
        public static final Route CLEAR_EMOJI_REACTIONS = new Route(Method.DELETE, "channels/{channel_id}/messages/{message_id}/reactions/{reaction_code}");
        public static final Route DELETE_MESSAGE = new Route(Method.DELETE, "channels/{channel_id}/messages/{message_id}");
        public static final Route GET_MESSAGE_HISTORY = new Route(Method.GET, "channels/{channel_id}/messages");
        public static final Route CROSSPOST_MESSAGE = new Route(Method.POST, "channels/{channel_id}/messages/{message_id}/crosspost");
        public static final Route GET_MESSAGE = new Route(Method.GET, "channels/{channel_id}/messages/{message_id}");
        public static final Route DELETE_MESSAGES = new Route(Method.POST, "channels/{channel_id}/messages/bulk-delete");
        public static final Route END_POLL = new Route(Method.POST, "channels/{channel_id}/polls/{message_id}/expire");
        public static final Route GET_POLL_ANSWER_VOTERS = new Route(Method.GET, "channels/{channel_id}/polls/{message_id}/answers/{answer_id}");
    }

    public static class AutoModeration {
        public static final Route LIST_RULES = new Route(Method.GET, "guilds/{guild_id}/auto-moderation/rules");
        public static final Route GET_RULE = new Route(Method.GET, "guilds/{guild_id}/auto-moderation/rules/{rule_id}");
        public static final Route CREATE_RULE = new Route(Method.POST, "guilds/{guild_id}/auto-moderation/rules");
        public static final Route UPDATE_RULE = new Route(Method.PATCH, "guilds/{guild_id}/auto-moderation/rules/{rule_id}");
        public static final Route DELETE_RULE = new Route(Method.DELETE, "guilds/{guild_id}/auto-moderation/rules/{rule_id}");
    }

    public static class StageInstances {
        public static final Route GET_INSTANCE = new Route(Method.GET, "stage-instances/{channel_id}");
        public static final Route DELETE_INSTANCE = new Route(Method.DELETE, "stage-instances/{channel_id}");
        public static final Route UPDATE_INSTANCE = new Route(Method.PATCH, "stage-instances/{channel_id}");
        public static final Route CREATE_INSTANCE = new Route(Method.POST, "stage-instances");
    }

    public static class Channels {
        public static final Route DELETE_CHANNEL = new Route(Method.DELETE, "channels/{channel_id}");
        public static final Route MODIFY_CHANNEL = new Route(Method.PATCH, "channels/{channel_id}");
        public static final Route GET_CHANNEL = new Route(Method.GET, "channels/{channel_id}");
        public static final Route GET_WEBHOOKS = new Route(Method.GET, "channels/{channel_id}/webhooks");
        public static final Route CREATE_WEBHOOK = new Route(Method.POST, "channels/{channel_id}/webhooks");
        public static final Route CREATE_PERM_OVERRIDE = new Route(Method.PUT, "channels/{channel_id}/permissions/{permoverride_id}");
        public static final Route MODIFY_PERM_OVERRIDE = new Route(Method.PUT, "channels/{channel_id}/permissions/{permoverride_id}");
        public static final Route DELETE_PERM_OVERRIDE = new Route(Method.DELETE, "channels/{channel_id}/permissions/{permoverride_id}");
        public static final Route SET_STATUS = new Route(Method.PUT, "channels/{channel_id}/voice-status");
        public static final Route SEND_TYPING = new Route(Method.POST, "channels/{channel_id}/typing");
        public static final Route GET_PERMISSIONS = new Route(Method.GET, "channels/{channel_id}/permissions");
        public static final Route GET_PERM_OVERRIDE = new Route(Method.GET, "channels/{channel_id}/permissions/{permoverride_id}");
        public static final Route FOLLOW_CHANNEL = new Route(Method.POST, "channels/{channel_id}/followers");
        public static final Route CREATE_THREAD_FROM_MESSAGE = new Route(Method.POST, "channels/{channel_id}/messages/{message_id}/threads");
        public static final Route CREATE_THREAD = new Route(Method.POST, "channels/{channel_id}/threads");
        public static final Route JOIN_THREAD = new Route(Method.PUT, "channels/{channel_id}/thread-members/@me");
        public static final Route ADD_THREAD_MEMBER = new Route(Method.PUT, "channels/{channel_id}/thread-members/{user_id}");
        public static final Route LEAVE_THREAD = new Route(Method.DELETE, "channels/{channel_id}/thread-members/@me");
        public static final Route REMOVE_THREAD_MEMBER = new Route(Method.DELETE, "channels/{channel_id}/thread-members/{user_id}");
        public static final Route GET_THREAD_MEMBER = new Route(Method.GET, "channels/{channel_id}/thread-members/{user_id}");
        public static final Route LIST_THREAD_MEMBERS = new Route(Method.GET, "channels/{channel_id}/thread-members");
        public static final Route LIST_PUBLIC_ARCHIVED_THREADS = new Route(Method.GET, "channels/{channel_id}/threads/archived/public");
        public static final Route LIST_PRIVATE_ARCHIVED_THREADS = new Route(Method.GET, "channels/{channel_id}/threads/archived/private");
        public static final Route LIST_JOINED_PRIVATE_ARCHIVED_THREADS = new Route(Method.GET, "channels/{channel_id}/users/@me/threads/archived/private");
    }

    public static class Roles {
        public static final Route GET_ROLES = new Route(Method.GET, "guilds/{guild_id}/roles");
        public static final Route CREATE_ROLE = new Route(Method.POST, "guilds/{guild_id}/roles");
        public static final Route GET_ROLE = new Route(Method.GET, "guilds/{guild_id}/roles/{role_id}");
        public static final Route MODIFY_ROLE = new Route(Method.PATCH, "guilds/{guild_id}/roles/{role_id}");
        public static final Route DELETE_ROLE = new Route(Method.DELETE, "guilds/{guild_id}/roles/{role_id}");
    }

    public static class Webhooks {
        public static final Route GET_WEBHOOK = new Route(Method.GET, "webhooks/{webhook_id}");
        public static final Route GET_TOKEN_WEBHOOK = new Route(Method.GET, "webhooks/{webhook_id}/{token}");
        public static final Route DELETE_WEBHOOK = new Route(Method.DELETE, "webhooks/{webhook_id}");
        public static final Route DELETE_TOKEN_WEBHOOK = new Route(Method.DELETE, "webhooks/{webhook_id}/{token}");
        public static final Route MODIFY_WEBHOOK = new Route(Method.PATCH, "webhooks/{webhook_id}");
        public static final Route MODIFY_TOKEN_WEBHOOK = new Route(Method.PATCH, "webhooks/{webhook_id}/{token}");
        public static final Route EXECUTE_WEBHOOK = new Route(Method.POST, "webhooks/{webhook_id}/{token}");
        public static final Route EXECUTE_WEBHOOK_FETCH = new Route(Method.GET, "webhooks/{webhook_id}/{token}/messages/{message_id}");
        public static final Route EXECUTE_WEBHOOK_EDIT = new Route(Method.PATCH, "webhooks/{webhook_id}/{token}/messages/{message_id}");
        public static final Route EXECUTE_WEBHOOK_DELETE = new Route(Method.DELETE, "webhooks/{webhook_id}/{token}/messages/{message_id}");
        public static final Route EXECUTE_WEBHOOK_SLACK = new Route(Method.POST, "webhooks/{webhook_id}/{token}/slack");
        public static final Route EXECUTE_WEBHOOK_GITHUB = new Route(Method.POST, "webhooks/{webhook_id}/{token}/github");
    }

    public static class Stickers {
        public static final Route GET_GUILD_STICKERS = new Route(Method.GET, "guilds/{guild_id}/stickers");
        public static final Route GET_GUILD_STICKER = new Route(Method.GET, "guilds/{guild_id}/stickers/{sticker_id}");
        public static final Route MODIFY_GUILD_STICKER = new Route(Method.PATCH, "guilds/{guild_id}/stickers/{sticker_id}");
        public static final Route DELETE_GUILD_STICKER = new Route(Method.DELETE, "guilds/{guild_id}/stickers/{sticker_id}");
        public static final Route CREATE_GUILD_STICKER = new Route(Method.POST, "guilds/{guild_id}/stickers");
        public static final Route GET_STICKER = new Route(Method.GET, "stickers/{sticker_id}");
        public static final Route LIST_PACKS = new Route(Method.GET, "sticker-packs");
    }

    public static class Emojis {
        public static final Route MODIFY_EMOJI = new Route(Method.PATCH, "guilds/{guild_id}/emojis/{emoji_id}");
        public static final Route DELETE_EMOJI = new Route(Method.DELETE, "guilds/{guild_id}/emojis/{emoji_id}");
        public static final Route CREATE_EMOJI = new Route(Method.POST, "guilds/{guild_id}/emojis");
        public static final Route GET_EMOJIS = new Route(Method.GET, "guilds/{guild_id}/emojis");
        public static final Route GET_EMOJI = new Route(Method.GET, "guilds/{guild_id}/emojis/{emoji_id}");
    }

    public static class Guilds {
        public static final Route GET_GUILD = new Route(Method.GET, "guilds/{guild_id}");
        public static final Route MODIFY_GUILD = new Route(Method.PATCH, "guilds/{guild_id}");
        public static final Route GET_VANITY_URL = new Route(Method.GET, "guilds/{guild_id}/vanity-url");
        public static final Route CREATE_CHANNEL = new Route(Method.POST, "guilds/{guild_id}/channels");
        public static final Route GET_CHANNELS = new Route(Method.GET, "guilds/{guild_id}/channels");
        public static final Route MODIFY_CHANNELS = new Route(Method.PATCH, "guilds/{guild_id}/channels");
        public static final Route MODIFY_ROLES = new Route(Method.PATCH, "guilds/{guild_id}/roles");
        public static final Route GET_BANS = new Route(Method.GET, "guilds/{guild_id}/bans");
        public static final Route GET_BAN = new Route(Method.GET, "guilds/{guild_id}/bans/{user_id}");
        public static final Route UNBAN = new Route(Method.DELETE, "guilds/{guild_id}/bans/{user_id}");
        public static final Route BAN = new Route(Method.PUT, "guilds/{guild_id}/bans/{user_id}");
        public static final Route BULK_BAN = new Route(Method.POST, "guilds/{guild_id}/bulk-ban");
        public static final Route KICK_MEMBER = new Route(Method.DELETE, "guilds/{guild_id}/members/{user_id}");
        public static final Route MODIFY_MEMBER = new Route(Method.PATCH, "guilds/{guild_id}/members/{user_id}");
        public static final Route ADD_MEMBER = new Route(Method.PUT, "guilds/{guild_id}/members/{user_id}");
        public static final Route GET_MEMBER = new Route(Method.GET, "guilds/{guild_id}/members/{user_id}");
        public static final Route MODIFY_SELF = new Route(Method.PATCH, "guilds/{guild_id}/members/@me");
        public static final Route PRUNABLE_COUNT = new Route(Method.GET, "guilds/{guild_id}/prune");
        public static final Route PRUNE_MEMBERS = new Route(Method.POST, "guilds/{guild_id}/prune");
        public static final Route GET_WEBHOOKS = new Route(Method.GET, "guilds/{guild_id}/webhooks");
        public static final Route GET_GUILD_EMBED = new Route(Method.GET, "guilds/{guild_id}/embed");
        public static final Route MODIFY_GUILD_EMBED = new Route(Method.PATCH, "guilds/{guild_id}/embed");
        public static final Route GET_GUILD_EMOJIS = new Route(Method.GET, "guilds/{guild_id}/emojis");
        public static final Route GET_AUDIT_LOGS = new Route(Method.GET, "guilds/{guild_id}/audit-logs");
        public static final Route GET_VOICE_REGIONS = new Route(Method.GET, "guilds/{guild_id}/regions");
        public static final Route UPDATE_VOICE_STATE = new Route(Method.PATCH, "guilds/{guild_id}/voice-states/{user_id}");
        public static final Route GET_INTEGRATIONS = new Route(Method.GET, "guilds/{guild_id}/integrations");
        public static final Route CREATE_INTEGRATION = new Route(Method.POST, "guilds/{guild_id}/integrations");
        public static final Route DELETE_INTEGRATION = new Route(Method.DELETE, "guilds/{guild_id}/integrations/{integration_id}");
        public static final Route MODIFY_INTEGRATION = new Route(Method.PATCH, "guilds/{guild_id}/integrations/{integration_id}");
        public static final Route SYNC_INTEGRATION = new Route(Method.POST, "guilds/{guild_id}/integrations/{integration_id}/sync");
        public static final Route ADD_MEMBER_ROLE = new Route(Method.PUT, "guilds/{guild_id}/members/{user_id}/roles/{role_id}");
        public static final Route REMOVE_MEMBER_ROLE = new Route(Method.DELETE, "guilds/{guild_id}/members/{user_id}/roles/{role_id}");
        public static final Route LIST_ACTIVE_THREADS = new Route(Method.GET, "guilds/{guild_id}/threads/active");
        public static final Route GET_SCHEDULED_EVENT = new Route(Method.GET, "guilds/{guild_id}/scheduled-events/{scheduled_event_id}");
        public static final Route GET_SCHEDULED_EVENTS = new Route(Method.GET, "guilds/{guild_id}/scheduled-events");
        public static final Route CREATE_SCHEDULED_EVENT = new Route(Method.POST, "guilds/{guild_id}/scheduled-events");
        public static final Route MODIFY_SCHEDULED_EVENT = new Route(Method.PATCH, "guilds/{guild_id}/scheduled-events/{scheduled_event_id}");
        public static final Route DELETE_SCHEDULED_EVENT = new Route(Method.DELETE, "guilds/{guild_id}/scheduled-events/{scheduled_event_id}");
        public static final Route GET_SCHEDULED_EVENT_USERS = new Route(Method.GET, "guilds/{guild_id}/scheduled-events/{scheduled_event_id}/users");
        public static final Route GET_WELCOME_SCREEN = new Route(Method.GET, "guilds/{guild_id}/welcome-screen");
        public static final Route MODIFY_WELCOME_SCREEN = new Route(Method.PATCH, "guilds/{guild_id}/welcome-screen");
        public static final Route CREATE_GUILD = new Route(Method.POST, "guilds");
        public static final Route DELETE_GUILD = new Route(Method.POST, "guilds/{guild_id}/delete");
    }

    public static class Users {
        public static final Route GET_USER = new Route(Method.GET, "users/{user_id}");
    }

    public static class Self {
        public static final Route GET_SELF = new Route(Method.GET, "users/@me");
        public static final Route MODIFY_SELF = new Route(Method.PATCH, "users/@me");
        public static final Route GET_GUILDS = new Route(Method.GET, "users/@me/guilds");
        public static final Route LEAVE_GUILD = new Route(Method.DELETE, "users/@me/guilds/{guild_id}");
        public static final Route GET_PRIVATE_CHANNELS = new Route(Method.GET, "users/@me/channels");
        public static final Route CREATE_PRIVATE_CHANNEL = new Route(Method.POST, "users/@me/channels");
    }

    public static class Interactions {
        public static final Route GET_COMMANDS = new Route(Method.GET, "applications/{application_id}/commands");
        public static final Route GET_COMMAND = new Route(Method.GET, "applications/{application_id}/commands/{command_id}");
        public static final Route CREATE_COMMAND = new Route(Method.POST, "applications/{application_id}/commands");
        public static final Route UPDATE_COMMANDS = new Route(Method.PUT, "applications/{application_id}/commands");
        public static final Route EDIT_COMMAND = new Route(Method.PATCH, "applications/{application_id}/commands/{command_id}");
        public static final Route DELETE_COMMAND = new Route(Method.DELETE, "applications/{application_id}/commands/{command_id}");
        public static final Route GET_GUILD_COMMANDS = new Route(Method.GET, "applications/{application_id}/guilds/{guild_id}/commands");
        public static final Route GET_GUILD_COMMAND = new Route(Method.GET, "applications/{application_id}/guilds/{guild_id}/commands/{command_id}");
        public static final Route CREATE_GUILD_COMMAND = new Route(Method.POST, "applications/{application_id}/guilds/{guild_id}/commands");
        public static final Route UPDATE_GUILD_COMMANDS = new Route(Method.PUT, "applications/{application_id}/guilds/{guild_id}/commands");
        public static final Route EDIT_GUILD_COMMAND = new Route(Method.PATCH, "applications/{application_id}/guilds/{guild_id}/commands/{command_id}");
        public static final Route DELETE_GUILD_COMMAND = new Route(Method.DELETE, "applications/{application_id}/guilds/{guild_id}/commands/{command_id}");
        public static final Route GET_ALL_COMMAND_PERMISSIONS = new Route(Method.GET, "applications/{application_id}/guilds/{guild_id}/commands/permissions");
        public static final Route EDIT_ALL_COMMAND_PERMISSIONS = new Route(Method.PUT, "applications/{application_id}/guilds/{guild_id}/commands/permissions");
        public static final Route GET_COMMAND_PERMISSIONS = new Route(Method.GET, "applications/{application_id}/guilds/{guild_id}/commands/{command_id}/permissions");
        public static final Route EDIT_COMMAND_PERMISSIONS = new Route(Method.PUT, "applications/{application_id}/guilds/{guild_id}/commands/{command_id}/permissions");
        public static final Route CALLBACK = new Route(Method.POST, "interactions/{interaction_id}/{interaction_token}/callback", true);
        public static final Route CREATE_FOLLOWUP = new Route(Method.POST, "webhooks/{application_id}/{interaction_token}", true);
        public static final Route EDIT_FOLLOWUP = new Route(Method.PATCH, "webhooks/{application_id}/{interaction_token}/messages/{message_id}", true);
        public static final Route DELETE_FOLLOWUP = new Route(Method.DELETE, "webhooks/{application_id}/{interaction_token}/messages/{message_id}", true);
        public static final Route GET_MESSAGE = new Route(Method.GET, "webhooks/{application_id}/{interaction_token}/messages/{message_id}", true);
    }

    public static class Applications {
        public static final Route GET_BOT_APPLICATION = new Route(Method.GET, "oauth2/applications/@me");
        public static final Route GET_ROLE_CONNECTION_METADATA = new Route(Method.GET, "applications/{application_id}/role-connections/metadata");
        public static final Route UPDATE_ROLE_CONNECTION_METADATA = new Route(Method.PUT, "applications/{application_id}/role-connections/metadata");
        public static final Route GET_ENTITLEMENTS = new Route(Method.GET, "applications/{application_id}/entitlements");
        public static final Route GET_ENTITLEMENT = new Route(Method.GET, "applications/{application_id}/entitlements/{entitlement_id}");
        public static final Route CONSUME_ENTITLEMENT = new Route(Method.POST, "applications/{application_id}/entitlements/{entitlement_id}/consume");
        public static final Route CREATE_TEST_ENTITLEMENT = new Route(Method.POST, "applications/{application_id}/entitlements");
        public static final Route DELETE_TEST_ENTITLEMENT = new Route(Method.DELETE, "applications/{application_id}/entitlements/{entitlement_id}");
        public static final Route GET_APPLICATION_EMOJIS = new Route(Method.GET, "applications/{application_id}/emojis");
        public static final Route GET_APPLICATION_EMOJI = new Route(Method.GET, "applications/{application_id}/emojis/{emoji_id}");
        public static final Route CREATE_APPLICATION_EMOJI = new Route(Method.POST, "applications/{application_id}/emojis");
        public static final Route MODIFY_APPLICATION_EMOJI = new Route(Method.PATCH, "applications/{application_id}/emojis/{emoji_id}");
        public static final Route DELETE_APPLICATION_EMOJI = new Route(Method.DELETE, "applications/{application_id}/emojis/{emoji_id}");
    }

    public static class Misc {
        public static final Route GET_VOICE_REGIONS = new Route(Method.GET, "voice/regions");
        public static final Route GATEWAY = new Route(Method.GET, "gateway");
        public static final Route GATEWAY_BOT = new Route(Method.GET, "gateway/bot");
    }
}

