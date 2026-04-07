/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public class PKUtil {
    public static final String PK_APP_ID = "466378653216014359";
    public static final Set<String> PK_USERS = Collections.synchronizedSet(new HashSet());

    public static boolean isPK(MessageReceivedEvent event) {
        if (event.isWebhookMessage()) {
            if (Objects.equals(event.getMessage().getApplicationId(), PK_APP_ID)) {
                new Thread(() -> {
                    String sender = PKUtil.getSender(event.getMessageId());
                    if (sender != null) {
                        PK_USERS.add(sender);
                    }
                }).start();
                return true;
            }
            return false;
        }
        if (PK_USERS.contains(event.getAuthor().getId())) {
            return PKUtil.getSender(event.getMessageId()) != null;
        }
        return false;
    }

    @Nullable
    public static String getSender(String id) {
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(1000L)).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.pluralkit.me/v2/messages/" + id)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return null;
            }
            JSONObject json = new JSONObject(response.body());
            return json.getString("sender");
        }
        catch (IOException | InterruptedException e) {
            BotController.INSTANCE.getLogger().error("An error occurred while checking message in PluralKit api", (Throwable)e);
            return null;
        }
    }
}

