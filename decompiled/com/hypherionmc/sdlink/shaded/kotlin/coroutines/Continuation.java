/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin.coroutines;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.coroutines.CoroutineContext;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020\u0002J\u001e\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH&\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/coroutines/Continuation;", "T", "", "context", "Lcom/hypherionmc/sdlink/shaded/kotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "result", "Lcom/hypherionmc/sdlink/shaded/kotlin/Result;", "(Ljava/lang/Object;)V", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@SinceKotlin(version="1.3")
public interface Continuation<T> {
    @NotNull
    public CoroutineContext getContext();

    public void resumeWith(@NotNull Object var1);
}

