/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package com.hypherionmc.sdlink.core.jsondb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hypherionmc.sdlink.core.jsondb.annotations.Document;
import com.hypherionmc.sdlink.core.jsondb.annotations.Id;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonDatabase {
    private final File dbFolder;
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private final Map<String, ReentrantReadWriteLock> locks = new HashMap<String, ReentrantReadWriteLock>();

    public JsonDatabase(String folderPath) {
        this.dbFolder = new File(folderPath);
        if (!this.dbFolder.exists()) {
            this.dbFolder.mkdirs();
        }
    }

    public void setupDB(Set<Class<?>> tables) {
        tables.forEach(this::initializeCollection);
    }

    private void initializeCollection(Class<?> clazz) {
        String collectionName = this.getCollectionName(clazz);
        File collectionFile = new File(this.dbFolder, collectionName + ".json");
        this.locks.put(collectionName, new ReentrantReadWriteLock());
        if (!collectionFile.exists()) {
            try (FileWriter writer = new FileWriter(collectionFile);){
                writer.write("{\"schemaVersion\":\"1.0\"}\n");
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to initialize collection: " + collectionName, e);
            }
        }
    }

    public <T> List<T> reloadCollection(String collectionName, Class<T> clazz) {
        if (!new File(this.dbFolder, collectionName + ".json").exists()) {
            this.initializeCollection(clazz);
        }
        this.lockRead(collectionName);
        try {
            ArrayList<Object> arrayList;
            BufferedReader reader = new BufferedReader(new FileReader(new File(this.dbFolder, collectionName + ".json")));
            try {
                String line;
                ArrayList<Object> entries = new ArrayList<Object>();
                boolean skipHeader = true;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    if (skipHeader) {
                        skipHeader = false;
                        continue;
                    }
                    entries.add(this.gson.fromJson(line, clazz));
                }
                arrayList = entries;
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
                    throw new RuntimeException("Failed to reload collection: " + collectionName, e);
                }
            }
            reader.close();
            return arrayList;
        }
        finally {
            this.unlockRead(collectionName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void upsert(Object entry) {
        String collectionName = this.getCollectionName(entry.getClass());
        this.lockWrite(collectionName);
        try {
            List<?> entries = this.reloadCollection(collectionName, entry.getClass());
            entries.removeIf(e -> this.getId(e).equals(this.getId(entry)));
            entries.add(entry);
            this.saveCollection(collectionName, entries);
        }
        finally {
            this.unlockWrite(collectionName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void remove(Object entry) {
        String collectionName = this.getCollectionName(entry.getClass());
        this.lockWrite(collectionName);
        try {
            List<?> entries = this.reloadCollection(collectionName, entry.getClass());
            entries.removeIf(e -> this.getId(e).equals(this.getId(entry)));
            this.saveCollection(collectionName, entries);
        }
        finally {
            this.unlockWrite(collectionName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T findById(Object id, Class<T> clazz) {
        String collectionName = this.getCollectionName(clazz);
        this.lockRead(collectionName);
        try {
            T t = this.reloadCollection(collectionName, clazz).stream().filter(entry -> id.equals(this.getId(entry))).findFirst().orElse(null);
            return t;
        }
        finally {
            this.unlockRead(collectionName);
        }
    }

    public <T> List<T> getCollection(Class<T> clazz) {
        return this.reloadCollection(this.getCollectionName(clazz), clazz);
    }

    private void saveCollection(String collectionName, List<?> entries) {
        try (FileWriter writer = new FileWriter(new File(this.dbFolder, collectionName + ".json"));){
            writer.write("{\"schemaVersion\":\"1.0\"}\n");
            for (Object entry : entries) {
                writer.write(this.gson.toJson(entry) + "\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to save collection: " + collectionName, e);
        }
    }

    private String getCollectionName(Class<?> clazz) {
        Document annotation = clazz.getAnnotation(Document.class);
        return annotation != null ? annotation.collection() : clazz.getSimpleName();
    }

    private Object getId(Object entry) {
        Class<?> clazz = entry.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Id.class)) continue;
                field.setAccessible(true);
                return field.get(entry);
            }
            throw new RuntimeException("No field annotated with @Id found in: " + clazz.getSimpleName());
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access @Id field in: " + clazz.getSimpleName(), e);
        }
    }

    private void lockRead(String collectionName) {
        this.locks.get(collectionName).readLock().lock();
    }

    private void unlockRead(String collectionName) {
        this.locks.get(collectionName).readLock().unlock();
    }

    private void lockWrite(String collectionName) {
        this.locks.get(collectionName).writeLock().lock();
    }

    private void unlockWrite(String collectionName) {
        this.locks.get(collectionName).writeLock().unlock();
    }
}

