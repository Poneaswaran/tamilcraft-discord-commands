/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.connection;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okhttp3.Interceptor;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.connection.Exchange;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.http.RealInterceptorChain;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/connection/ConnectInterceptor;", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Interceptor;", "()V", "intercept", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Response;", "chain", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Interceptor$Chain;", "okhttp"})
public final class ConnectInterceptor
implements Interceptor {
    @NotNull
    public static final ConnectInterceptor INSTANCE = new ConnectInterceptor();

    private ConnectInterceptor() {
    }

    @Override
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        RealInterceptorChain realChain = (RealInterceptorChain)chain;
        Exchange exchange = realChain.getCall$okhttp().initExchange$okhttp((RealInterceptorChain)chain);
        RealInterceptorChain connectedChain = RealInterceptorChain.copy$okhttp$default(realChain, 0, exchange, null, 0, 0, 0, 61, null);
        return connectedChain.proceed(realChain.getRequest$okhttp());
    }
}

