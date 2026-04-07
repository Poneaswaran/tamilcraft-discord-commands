/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.http;

import com.hypherionmc.sdlink.shaded.kotlin.ExceptionsKt;
import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.text.StringsKt;
import com.hypherionmc.sdlink.shaded.okhttp3.Interceptor;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okhttp3.ResponseBody;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.Util;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.connection.Exchange;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.http.HttpMethod;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.http.RealInterceptorChain;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.http2.ConnectionShutdownException;
import com.hypherionmc.sdlink.shaded.okio.BufferedSink;
import com.hypherionmc.sdlink.shaded.okio.Okio;
import java.io.IOException;
import java.net.ProtocolException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/http/CallServerInterceptor;", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Response;", "chain", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Interceptor$Chain;", "shouldIgnoreAndWaitForRealResponse", "code", "", "okhttp"})
public final class CallServerInterceptor
implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean forWebSocket) {
        this.forWebSocket = forWebSocket;
    }

    @Override
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        RealInterceptorChain realChain = (RealInterceptorChain)chain;
        Exchange exchange = realChain.getExchange$okhttp();
        Intrinsics.checkNotNull(exchange);
        Exchange exchange2 = exchange;
        Request request = realChain.getRequest$okhttp();
        RequestBody requestBody = request.body();
        long sentRequestMillis = System.currentTimeMillis();
        boolean invokeStartEvent = true;
        Response.Builder responseBuilder = null;
        IOException sendRequestException = null;
        try {
            exchange2.writeRequestHeaders(request);
            if (HttpMethod.permitsRequestBody(request.method()) && requestBody != null) {
                if (StringsKt.equals("100-continue", request.header("Expect"), true)) {
                    exchange2.flushRequest();
                    responseBuilder = exchange2.readResponseHeaders(true);
                    exchange2.responseHeadersStart();
                    invokeStartEvent = false;
                }
                if (responseBuilder == null) {
                    BufferedSink bufferedRequestBody;
                    if (requestBody.isDuplex()) {
                        exchange2.flushRequest();
                        bufferedRequestBody = Okio.buffer(exchange2.createRequestBody(request, true));
                        requestBody.writeTo(bufferedRequestBody);
                    } else {
                        bufferedRequestBody = Okio.buffer(exchange2.createRequestBody(request, false));
                        requestBody.writeTo(bufferedRequestBody);
                        bufferedRequestBody.close();
                    }
                } else {
                    exchange2.noRequestBody();
                    if (!exchange2.getConnection$okhttp().isMultiplexed$okhttp()) {
                        exchange2.noNewExchangesOnConnection();
                    }
                }
            } else {
                exchange2.noRequestBody();
            }
            if (requestBody == null || !requestBody.isDuplex()) {
                exchange2.finishRequest();
            }
        }
        catch (IOException e) {
            if (e instanceof ConnectionShutdownException) {
                throw e;
            }
            if (!exchange2.getHasFailure$okhttp()) {
                throw e;
            }
            sendRequestException = e;
        }
        try {
            Response response;
            int code;
            if (responseBuilder == null) {
                Response.Builder builder = exchange2.readResponseHeaders(false);
                Intrinsics.checkNotNull(builder);
                responseBuilder = builder;
                if (invokeStartEvent) {
                    exchange2.responseHeadersStart();
                    invokeStartEvent = false;
                }
            }
            if (this.shouldIgnoreAndWaitForRealResponse(code = (response = responseBuilder.request(request).handshake(exchange2.getConnection$okhttp().handshake()).sentRequestAtMillis(sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build()).code())) {
                Response.Builder builder = exchange2.readResponseHeaders(false);
                Intrinsics.checkNotNull(builder);
                responseBuilder = builder;
                if (invokeStartEvent) {
                    exchange2.responseHeadersStart();
                }
                response = responseBuilder.request(request).handshake(exchange2.getConnection$okhttp().handshake()).sentRequestAtMillis(sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
                code = response.code();
            }
            exchange2.responseHeadersEnd(response);
            Response response2 = response = this.forWebSocket && code == 101 ? response.newBuilder().body(Util.EMPTY_RESPONSE).build() : response.newBuilder().body(exchange2.openResponseBody(response)).build();
            if (StringsKt.equals("close", response.request().header("Connection"), true) || StringsKt.equals("close", Response.header$default(response, "Connection", null, 2, null), true)) {
                exchange2.noNewExchangesOnConnection();
            }
            if (code == 204 || code == 205) {
                ResponseBody responseBody = response.body();
                if ((responseBody != null ? responseBody.contentLength() : -1L) > 0L) {
                    ResponseBody responseBody2 = response.body();
                    throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + (responseBody2 != null ? Long.valueOf(responseBody2.contentLength()) : null));
                }
            }
            return response;
        }
        catch (IOException e) {
            if (sendRequestException != null) {
                ExceptionsKt.addSuppressed(sendRequestException, e);
                throw sendRequestException;
            }
            throw e;
        }
    }

    private final boolean shouldIgnoreAndWaitForRealResponse(int code) {
        return code == 100 ? true : (102 <= code ? code < 200 : false);
    }
}

