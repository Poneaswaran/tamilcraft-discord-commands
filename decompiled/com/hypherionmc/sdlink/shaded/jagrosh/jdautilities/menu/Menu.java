/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.menu;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class Menu {
    protected final EventWaiter waiter;
    protected Set<User> users;
    protected Set<Role> roles;
    protected final long timeout;
    protected final TimeUnit unit;

    protected Menu(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout2, TimeUnit unit) {
        this.waiter = waiter;
        this.users = users;
        this.roles = roles;
        this.timeout = timeout2;
        this.unit = unit;
    }

    public abstract void display(MessageChannel var1);

    public abstract void display(Message var1);

    protected boolean isValidUser(User user) {
        return this.isValidUser(user, null);
    }

    protected boolean isValidUser(User user, @Nullable Guild guild) {
        if (user.isBot()) {
            return false;
        }
        if (this.users.isEmpty() && this.roles.isEmpty()) {
            return true;
        }
        if (this.users.contains(user)) {
            return true;
        }
        if (guild == null || !guild.isMember(user)) {
            return false;
        }
        return guild.getMember(user).getRoles().stream().anyMatch(this.roles::contains);
    }

    public static abstract class Builder<T extends Builder<T, V>, V extends Menu> {
        protected EventWaiter waiter;
        protected Set<User> users = new HashSet<User>();
        protected Set<Role> roles = new HashSet<Role>();
        protected long timeout = 1L;
        protected TimeUnit unit = TimeUnit.MINUTES;

        public abstract V build();

        public final T setEventWaiter(EventWaiter waiter) {
            this.waiter = waiter;
            return (T)this;
        }

        public final T addUsers(User ... users) {
            this.users.addAll(Arrays.asList(users));
            return (T)this;
        }

        public final T setUsers(User ... users) {
            this.users.clear();
            this.users.addAll(Arrays.asList(users));
            return (T)this;
        }

        public final T addRoles(Role ... roles) {
            this.roles.addAll(Arrays.asList(roles));
            return (T)this;
        }

        public final T setRoles(Role ... roles) {
            this.roles.clear();
            this.roles.addAll(Arrays.asList(roles));
            return (T)this;
        }

        public final T setTimeout(long timeout2, TimeUnit unit) {
            this.timeout = timeout2;
            this.unit = unit;
            return (T)this;
        }
    }
}

