/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.managers;

import com.hypherionmc.sdlink.core.database.HiddenPlayers;
import com.hypherionmc.sdlink.core.database.SDLinkAccount;
import com.hypherionmc.sdlink.core.jsondb.JsonDatabase;
import com.hypherionmc.sdlink.core.jsondb.annotations.Document;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class DatabaseManager {
    public static final DatabaseManager INSTANCE = new DatabaseManager();
    private final JsonDatabase sdlinkDatabase = new JsonDatabase("sdlinkstorage");
    private final Set<Class<?>> tables = new LinkedHashSet<Class<?>>(){
        {
            this.add(SDLinkAccount.class);
            this.add(HiddenPlayers.class);
        }
    };

    DatabaseManager() {
        this.sdlinkDatabase.setupDB(this.tables);
    }

    public void initialize() {
        this.tables.forEach(t -> this.sdlinkDatabase.reloadCollection(t.getAnnotation(Document.class).collection(), t));
    }

    public void updateEntry(Object t) {
        this.sdlinkDatabase.upsert(t);
        this.reload(t.getClass());
    }

    public void deleteEntry(Object t) {
        this.sdlinkDatabase.remove(t);
        this.reload(t.getClass());
    }

    public void deleteEntry(Object t, Class<?> clazz) {
        this.sdlinkDatabase.remove(t);
        this.reload(t.getClass());
    }

    private void reload(Class<?> clazz) {
        this.sdlinkDatabase.reloadCollection(clazz.getAnnotation(Document.class).collection(), clazz);
    }

    public <T> T findById(Object id, Class<T> entityClass) {
        this.reload(entityClass);
        return this.sdlinkDatabase.findById(id, entityClass);
    }

    public <T> List<T> getCollection(Class<T> entityClass) {
        this.reload(entityClass);
        return this.sdlinkDatabase.getCollection(entityClass);
    }

    public <T> List<T> findAll(Class<T> tClass) {
        this.reload(tClass);
        return this.sdlinkDatabase.getCollection(tClass);
    }
}

