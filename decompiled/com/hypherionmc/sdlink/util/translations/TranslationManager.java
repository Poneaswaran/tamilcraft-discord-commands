/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.util.translations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hypherionmc.sdlink.core.discord.BotController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import lombok.Generated;

public final class TranslationManager {
    public static final TranslationManager INSTANCE = new TranslationManager();
    private Map<String, String> translations = Collections.emptyMap();
    private final Type type = new TypeToken<Map<String, String>>(){}.getType();
    private final Gson gson = new Gson();

    public void loadTranslations(String lang) {
        this.translations.clear();
        this.loadTranslatedFile(lang);
    }

    private void loadTranslatedFile(String lang) {
        File f = new File("config/simple-discord-link/language/" + lang + ".json");
        f.getParentFile().mkdirs();
        if (f.exists()) {
            Map<String, String> diskMap = this.loadMapFromFile(f);
            Map<String, String> resourceMap = this.loadMapFromFile(f);
            if (diskMap != null && resourceMap != null && !diskMap.keySet().equals(resourceMap.keySet())) {
                BotController.INSTANCE.getLogger().warn("Translation keys mismatch in {}. Regenerating from resource.", (Object)f.getName());
                if (this.createFileFromResource(f, "assets/sdlink/lang/" + lang + ".json")) {
                    this.loadFromFile(f);
                    return;
                }
            }
            if (diskMap != null) {
                this.translations = diskMap;
                return;
            }
        } else if (lang.equals("en_us") && this.createFileFromResource(f, "assets/sdlink/lang/en_us.json")) {
            this.loadFromFile(f);
            return;
        }
        if (this.loadFromResource("assets/sdlink/lang/" + lang + ".json")) {
            return;
        }
        BotController.INSTANCE.getLogger().warn("Failed to load translation for {}. Falling back to en_us.", (Object)lang);
        this.loadFromResource("assets/sdlink/lang/en_us.json");
    }

    private boolean loadFromFile(File file) {
        boolean bl;
        FileReader reader = new FileReader(file);
        try {
            this.translations = (Map)this.gson.fromJson((Reader)reader, this.type);
            bl = true;
        }
        catch (Throwable throwable) {
            try {
                try {
                    reader.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                BotController.INSTANCE.getLogger().error("Failed to load translation file: {}", (Object)file.getAbsolutePath(), (Object)e);
                return false;
            }
        }
        reader.close();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean loadFromResource(String resourcePath) {
        try (InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(resourcePath);){
            if (inputStream2 == null) {
                boolean bl2 = false;
                return bl2;
            }
            InputStreamReader reader = new InputStreamReader(inputStream2);
            this.translations = (Map)this.gson.fromJson((Reader)reader, this.type);
            boolean bl = true;
            return bl;
        }
        catch (IOException e) {
            BotController.INSTANCE.getLogger().error("Failed to load resource translation file: {}", (Object)resourcePath, (Object)e);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean createFileFromResource(File targetFile, String resourcePath) {
        try (InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(resourcePath);){
            if (inputStream2 == null) {
                BotController.INSTANCE.getLogger().error("Default translation file {} not found in resources!", (Object)resourcePath);
                boolean bl2 = false;
                return bl2;
            }
            try (FileOutputStream outputStream2 = new FileOutputStream(targetFile);){
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream2.read(buffer)) != -1) {
                    outputStream2.write(buffer, 0, bytesRead);
                }
            }
            BotController.INSTANCE.getLogger().info("Default translation file created at {}", (Object)targetFile.getAbsolutePath());
            boolean bl = true;
            return bl;
        }
        catch (IOException e) {
            BotController.INSTANCE.getLogger().error("Failed to create default translation file {}", (Object)targetFile.getAbsolutePath(), (Object)e);
            return false;
        }
    }

    public String translate(String key) {
        return this.translations.getOrDefault(key, key);
    }

    private Map<String, String> loadMapFromFile(File file) {
        Map map;
        FileReader reader = new FileReader(file);
        try {
            map = (Map)this.gson.fromJson((Reader)reader, this.type);
        }
        catch (Throwable throwable) {
            try {
                try {
                    reader.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                BotController.INSTANCE.getLogger().error("Failed to load translation file: {}", (Object)file.getAbsolutePath(), (Object)e);
                return null;
            }
        }
        reader.close();
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Map<String, String> loadMapFromResource(String resourcePath) {
        try (InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(resourcePath);){
            if (inputStream2 == null) {
                Map<String, String> map2 = null;
                return map2;
            }
            InputStreamReader reader = new InputStreamReader(inputStream2);
            Map map = (Map)this.gson.fromJson((Reader)reader, this.type);
            return map;
        }
        catch (IOException e) {
            BotController.INSTANCE.getLogger().error("Failed to load resource translation file: {}", (Object)resourcePath, (Object)e);
            return null;
        }
    }

    @Generated
    protected TranslationManager() {
    }
}

