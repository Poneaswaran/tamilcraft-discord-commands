/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ErrorResponseException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.ErrorResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ErrorHandler
implements Consumer<Throwable> {
    private static final Consumer<? super Throwable> empty = e -> {};
    private final Consumer<? super Throwable> base;
    private final Map<Predicate<? super Throwable>, Consumer<? super Throwable>> cases = new LinkedHashMap<Predicate<? super Throwable>, Consumer<? super Throwable>>();

    public ErrorHandler() {
        this(RestAction.getDefaultFailure());
    }

    public ErrorHandler(@Nonnull Consumer<? super Throwable> base) {
        Checks.notNull(base, "Consumer");
        this.base = base;
    }

    @Nonnull
    public ErrorHandler ignore(@Nonnull ErrorResponse ignored, ErrorResponse ... errorResponses) {
        Checks.notNull((Object)ignored, "ErrorResponse");
        Checks.noneNull((Object[])errorResponses, "ErrorResponse");
        return this.ignore(EnumSet.of(ignored, errorResponses));
    }

    @Nonnull
    public ErrorHandler ignore(@Nonnull Collection<ErrorResponse> errorResponses) {
        return this.handle(errorResponses, empty);
    }

    @Nonnull
    public ErrorHandler ignore(@Nonnull Class<?> clazz, Class<?> ... classes) {
        Checks.notNull(clazz, "Classes");
        Checks.noneNull(classes, "Classes");
        return this.ignore((? super Throwable it) -> {
            if (clazz.isInstance(it)) {
                return true;
            }
            for (Class e : classes) {
                if (!e.isInstance(it)) continue;
                return true;
            }
            return false;
        });
    }

    @Nonnull
    public ErrorHandler ignore(@Nonnull Predicate<? super Throwable> condition) {
        return this.handle(condition, empty);
    }

    @Nonnull
    public ErrorHandler handle(@Nonnull ErrorResponse response, @Nonnull Consumer<? super ErrorResponseException> handler) {
        Checks.notNull((Object)response, "ErrorResponse");
        return this.handle(EnumSet.of(response), handler);
    }

    @Nonnull
    public ErrorHandler handle(@Nonnull Collection<ErrorResponse> errorResponses, @Nonnull Consumer<? super ErrorResponseException> handler) {
        Checks.notNull(handler, "Handler");
        Checks.noneNull(errorResponses, "ErrorResponse");
        return this.handle(ErrorResponseException.class, (? super T it) -> errorResponses.contains((Object)it.getErrorResponse()), handler);
    }

    @Nonnull
    public <T> ErrorHandler handle(@Nonnull Class<T> clazz, @Nonnull Consumer<? super T> handler) {
        Checks.notNull(clazz, "Class");
        Checks.notNull(handler, "Handler");
        return this.handle(clazz::isInstance, (? super Throwable ex) -> handler.accept((Object)clazz.cast(ex)));
    }

    @Nonnull
    public <T> ErrorHandler handle(@Nonnull Class<T> clazz, @Nonnull Predicate<? super T> condition, @Nonnull Consumer<? super T> handler) {
        Checks.notNull(clazz, "Class");
        Checks.notNull(handler, "Handler");
        return this.handle((? super Throwable it) -> clazz.isInstance(it) && condition.test((Object)clazz.cast(it)), (? super Throwable ex) -> handler.accept((Object)clazz.cast(ex)));
    }

    @Nonnull
    public ErrorHandler handle(@Nonnull Collection<Class<?>> clazz, @Nullable Predicate<? super Throwable> condition, @Nonnull Consumer<? super Throwable> handler) {
        Checks.noneNull(clazz, "Class");
        Checks.notNull(handler, "Handler");
        ArrayList classes = new ArrayList(clazz);
        Predicate<Throwable> check2 = it -> classes.stream().anyMatch(c -> c.isInstance(it)) && (condition == null || condition.test((Throwable)it));
        return this.handle(check2, handler);
    }

    @Nonnull
    public ErrorHandler handle(@Nonnull Predicate<? super Throwable> condition, @Nonnull Consumer<? super Throwable> handler) {
        Checks.notNull(condition, "Condition");
        Checks.notNull(handler, "Handler");
        this.cases.put(condition, handler);
        return this;
    }

    @Override
    public void accept(Throwable t) {
        for (Map.Entry<Predicate<? super Throwable>, Consumer<? super Throwable>> entry : this.cases.entrySet()) {
            Predicate<? super Throwable> condition = entry.getKey();
            Consumer<? super Throwable> callback = entry.getValue();
            if (!condition.test(t)) continue;
            callback.accept(t);
            return;
        }
        this.base.accept(t);
    }
}

