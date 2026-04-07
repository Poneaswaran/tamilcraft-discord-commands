/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.operator;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ContextException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public abstract class RestActionOperator<I, O>
implements RestAction<O> {
    protected BooleanSupplier check;
    protected long deadline = -1L;
    protected final RestAction<I> action;

    public RestActionOperator(RestAction<I> action) {
        this.action = action;
    }

    protected static <E> void doSuccess(Consumer<? super E> callback, E value) {
        if (callback == null) {
            RestAction.getDefaultSuccess().accept(value);
        } else {
            callback.accept(value);
        }
    }

    protected static void doFailure(Consumer<? super Throwable> callback, Throwable throwable) {
        if (callback == null) {
            RestAction.getDefaultFailure().accept(throwable);
        } else {
            callback.accept(throwable);
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
    }

    protected void handle(RestAction<I> action, Consumer<? super Throwable> failure, Consumer<? super I> success) {
        Consumer<Throwable> catcher = this.contextWrap(failure);
        action.queue(result -> {
            try {
                if (success != null) {
                    success.accept((Object)result);
                }
            }
            catch (Throwable ex) {
                RestActionOperator.doFailure(catcher, ex);
            }
        }, catcher);
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.action.getJDA();
    }

    @Override
    @Nonnull
    public RestAction<O> setCheck(@Nullable BooleanSupplier checks) {
        this.check = checks;
        this.action.setCheck(checks);
        return this;
    }

    @Override
    @Nullable
    public BooleanSupplier getCheck() {
        return this.action.getCheck();
    }

    @Override
    @Nonnull
    public RestAction<O> deadline(long timestamp) {
        this.deadline = timestamp;
        this.action.deadline(timestamp);
        return this;
    }

    @Nullable
    protected <T> RestAction<T> applyContext(RestAction<T> action) {
        if (action == null) {
            return null;
        }
        if (this.check != null) {
            action.setCheck(this.check);
        }
        if (this.deadline >= 0L) {
            action.deadline(this.deadline);
        }
        return action;
    }

    @Nullable
    protected Consumer<? super Throwable> contextWrap(@Nullable Consumer<? super Throwable> callback) {
        if (callback instanceof ContextException.ContextConsumer) {
            return callback;
        }
        if (RestAction.isPassContext()) {
            return ContextException.here(callback == null ? RestAction.getDefaultFailure() : callback);
        }
        return callback;
    }
}

