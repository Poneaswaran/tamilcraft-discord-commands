/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.text.Text
 *  net.kyori.adventure.text.event.ClickEvent
 *  net.kyori.adventure.text.event.ClickEvent$Action
 *  net.kyori.adventure.text.event.HoverEvent
 *  net.kyori.adventure.text.event.HoverEvent$Action
 *  net.kyori.adventure.text.event.HoverEventSource
 *  net.kyori.adventure.text.format.NamedTextColor
 *  net.kyori.adventure.text.format.Style
 *  net.kyori.adventure.text.format.TextColor
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.sdlink.SDLinkConstants;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageIgnoreConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

public final class SDLinkChatUtils {
    private static final Pattern CHANNEL_PATTERN = Pattern.compile("\\[#(.*?)\\]", 2);
    private static final Pattern USER_ROLE_PATTERN = Pattern.compile("\\[@(.*?)\\]", 2);

    public static String parse(String message) {
        String finalMessage;
        block5: {
            finalMessage = message;
            try {
                Matcher m = CHANNEL_PATTERN.matcher(message);
                while (m.find()) {
                    String channelKey = m.group().replace("[", "").replace("]", "");
                    if (CacheManager.getServerChannels().isEmpty() || !CacheManager.getServerChannels().containsKey(channelKey)) continue;
                    finalMessage = finalMessage.replace("[" + channelKey + "]", CacheManager.getServerChannels().get(channelKey));
                }
                Matcher c = USER_ROLE_PATTERN.matcher(message);
                while (c.find()) {
                    String key = c.group().replace("[", "").replace("]", "");
                    if (!CacheManager.getServerRoles().isEmpty() && CacheManager.getServerRoles().containsKey(key)) {
                        finalMessage = finalMessage.replace("[" + key + "]", CacheManager.getServerRoles().get(key));
                    }
                    if (CacheManager.getUserCache().isEmpty() || !CacheManager.getUserCache().containsKey(key)) continue;
                    finalMessage = finalMessage.replace("[" + key + "]", CacheManager.getUserCache().get(key));
                }
            }
            catch (Exception e) {
                if (!SDLinkConfig.INSTANCE.generalConfig.debugging) break block5;
                SDLinkConstants.LOGGER.error("Failed to parse mention", (Throwable)e);
            }
        }
        return finalMessage;
    }

    public static Text parseChatLinks(String input) {
        Pattern pattern = Pattern.compile("\\b(?:https?)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]*[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(input);
        Text component = Text.empty();
        int lastEnd = 0;
        while (matcher.find()) {
            String url = matcher.group();
            String msg = input.substring(lastEnd, matcher.start());
            component.append(Text.fromString((String)msg, (boolean)SDLinkConfig.INSTANCE.chatConfig.formatting));
            Style emptyStyle = Style.empty().color((TextColor)NamedTextColor.BLUE).clickEvent(ClickEvent.clickEvent((ClickEvent.Action)ClickEvent.Action.OPEN_URL, (String)url)).hoverEvent((HoverEventSource)HoverEvent.hoverEvent((HoverEvent.Action)HoverEvent.Action.SHOW_TEXT, (Object)Text.literal((String)"Click to Open").getComponent()));
            Text urlComponent = Text.literal((String)url).style(emptyStyle);
            component.append(urlComponent);
            lastEnd = matcher.end();
        }
        String remaining = input.substring(lastEnd);
        component.append(Text.fromString((String)remaining, (boolean)SDLinkConfig.INSTANCE.chatConfig.formatting));
        return component;
    }

    public static String applyFiltering(String input, Predicate<MessageIgnoreConfig.Ignore> ignoreCheck) {
        if (!SDLinkConfig.INSTANCE.ignoreConfig.enabled) {
            return input;
        }
        for (MessageIgnoreConfig.Ignore i : SDLinkConfig.INSTANCE.ignoreConfig.entries) {
            if (!ignoreCheck.test(i)) continue;
            boolean isMatch = false;
            switch (i.searchMode) {
                case MATCHES: {
                    isMatch = input.equalsIgnoreCase(i.search);
                    break;
                }
                case CONTAINS: {
                    isMatch = input.contains(i.search);
                    break;
                }
                case STARTS_WITH: {
                    isMatch = input.startsWith(i.search);
                    break;
                }
                case REGEX: {
                    try {
                        Pattern pattern = Pattern.compile(i.search);
                        Matcher matcher = pattern.matcher(input);
                        isMatch = matcher.find();
                        break;
                    }
                    catch (Exception e) {
                        BotController.INSTANCE.getLogger().error("Invalid regex pattern: {}", (Object)i.search);
                    }
                }
            }
            if (!isMatch) continue;
            if (i.action == MessageIgnoreConfig.ActionMode.REPLACE) {
                input = i.searchMode == MessageIgnoreConfig.FilterMode.REGEX ? input.replaceAll(i.search, i.replace) : input.replace(i.search, i.replace);
                continue;
            }
            input = "";
        }
        return input;
    }
}

