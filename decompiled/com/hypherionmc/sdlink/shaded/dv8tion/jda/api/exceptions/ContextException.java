/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ContextException
extends Exception {
    @Nonnull
    public static Consumer<Throwable> herePrintingTrace() {
        return ContextException.here(Throwable::printStackTrace);
    }

    @Nonnull
    public static Consumer<Throwable> here(@Nonnull Consumer<? super Throwable> acceptor) {
        return new ContextConsumer(new ContextException(), acceptor);
    }

    public static class ContextConsumer
    implements Consumer<Throwable> {
        private final ContextException context;
        private final Consumer<? super Throwable> callback;

        private ContextConsumer(ContextException context, Consumer<? super Throwable> callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void accept(Throwable throwable) {
            if (this.callback != null) {
                this.callback.accept(Helpers.appendCause(throwable, this.context));
            }
        }
    }
}

