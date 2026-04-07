/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.kotlin.coroutines.jvm.internal;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.coroutines.Continuation;
import com.hypherionmc.sdlink.shaded.kotlin.coroutines.CoroutineContext;
import com.hypherionmc.sdlink.shaded.kotlin.coroutines.EmptyCoroutineContext;
import com.hypherionmc.sdlink.shaded.kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b!\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/coroutines/jvm/internal/RestrictedContinuationImpl;", "Lcom/hypherionmc/sdlink/shaded/kotlin/coroutines/jvm/internal/BaseContinuationImpl;", "completion", "Lcom/hypherionmc/sdlink/shaded/kotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "context", "Lcom/hypherionmc/sdlink/shaded/kotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@SinceKotlin(version="1.3")
public abstract class RestrictedContinuationImpl
extends BaseContinuationImpl {
    public RestrictedContinuationImpl(@Nullable Continuation<Object> completion) {
        super(completion);
        Continuation<Object> continuation = completion;
        if (continuation != null) {
            boolean bl;
            Continuation<Object> continuation2;
            Continuation<Object> it = continuation2 = continuation;
            boolean bl2 = false;
            boolean bl3 = bl = it.getContext() == EmptyCoroutineContext.INSTANCE;
            if (!bl) {
                boolean bl4 = false;
                String string = "Coroutines with restricted suspension must have EmptyCoroutineContext";
                throw new IllegalArgumentException(string.toString());
            }
        }
    }

    @Override
    @NotNull
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }
}

