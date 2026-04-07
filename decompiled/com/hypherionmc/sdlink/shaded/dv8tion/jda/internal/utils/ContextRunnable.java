/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ThreadLocalReason;
import java.util.concurrent.Callable;

public class ContextRunnable<E>
implements Runnable,
Callable<E> {
    private final String localReason = ThreadLocalReason.getCurrent();
    private final Runnable runnable;
    private final Callable<E> callable;

    public ContextRunnable(Runnable runnable2) {
        this.runnable = runnable2;
        this.callable = null;
    }

    public ContextRunnable(Callable<E> callable) {
        this.runnable = null;
        this.callable = callable;
    }

    @Override
    public void run() {
        try (ThreadLocalReason.Closable __ = ThreadLocalReason.closable(this.localReason);){
            this.runnable.run();
        }
    }

    @Override
    public E call() throws Exception {
        try (ThreadLocalReason.Closable __ = ThreadLocalReason.closable(this.localReason);){
            E e = this.callable.call();
            return e;
        }
    }
}

