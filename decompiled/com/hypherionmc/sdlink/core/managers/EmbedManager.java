/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  org.apache.commons.io.FileUtils
 */
package com.hypherionmc.sdlink.core.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.messaging.embeds.DiscordEmbed;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.io.FileUtils;

public final class EmbedManager {
    private static final File embedDir = new File("./config/simple-discord-link/embeds");
    public static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private static final ConcurrentHashMap<String, String> embeds = new ConcurrentHashMap();

    public static void init() {
        embeds.clear();
        embedDir.mkdirs();
        EmbedManager.defaultEmbeds();
        if (embedDir.listFiles() == null) {
            return;
        }
        for (File file : embedDir.listFiles()) {
            if (!file.isFile() || !file.getName().endsWith(".json")) continue;
            try {
                String json = FileUtils.readFileToString((File)file, (Charset)StandardCharsets.UTF_8);
                embeds.put(file.getName().replace(".json", ""), json);
            }
            catch (Exception e) {
                BotController.INSTANCE.getLogger().error("Failed to load custom embed {}", (Object)file.getName(), (Object)e);
            }
        }
    }

    public static String getEmbed(String key) {
        return embeds.get(key);
    }

    private static void defaultEmbeds() {
        File defaultEmbed = new File(embedDir.getAbsolutePath() + File.separator + "default.json");
        if (!defaultEmbed.exists()) {
            DiscordEmbed embed = new DiscordEmbed();
            DiscordEmbed.Author author = new DiscordEmbed.Author();
            author.name = "%author%";
            author.url = null;
            author.icon_url = "%avatar%";
            embed.author = author;
            embed.description = "%message_contents%";
            embed.color = "#000000";
            EmbedManager.writeToFile(defaultEmbed, embed);
        }
    }

    private static void writeToFile(File file, Object data) {
        try (FileWriter writer = new FileWriter(file);){
            gson.toJson(data, (Appendable)writer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

