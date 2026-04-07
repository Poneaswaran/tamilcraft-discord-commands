/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.okhttp3.internal.authenticator;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.DefaultConstructorMarker;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.text.StringsKt;
import com.hypherionmc.sdlink.shaded.okhttp3.Address;
import com.hypherionmc.sdlink.shaded.okhttp3.Authenticator;
import com.hypherionmc.sdlink.shaded.okhttp3.Challenge;
import com.hypherionmc.sdlink.shaded.okhttp3.Credentials;
import com.hypherionmc.sdlink.shaded.okhttp3.Dns;
import com.hypherionmc.sdlink.shaded.okhttp3.HttpUrl;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okhttp3.Route;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/internal/authenticator/JavaNetAuthenticator;", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Authenticator;", "defaultDns", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Dns;", "(Lokhttp3/Dns;)V", "authenticate", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Request;", "route", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Route;", "response", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Response;", "connectToInetAddress", "Ljava/net/InetAddress;", "Ljava/net/Proxy;", "url", "Lcom/hypherionmc/sdlink/shaded/okhttp3/HttpUrl;", "dns", "okhttp"})
public final class JavaNetAuthenticator
implements Authenticator {
    @NotNull
    private final Dns defaultDns;

    public JavaNetAuthenticator(@NotNull Dns defaultDns) {
        Intrinsics.checkNotNullParameter(defaultDns, "defaultDns");
        this.defaultDns = defaultDns;
    }

    public /* synthetic */ JavaNetAuthenticator(Dns dns, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            dns = Dns.SYSTEM;
        }
        this(dns);
    }

    @Override
    @Nullable
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
        Intrinsics.checkNotNullParameter(response, "response");
        List<Challenge> challenges = response.challenges();
        Request request = response.request();
        HttpUrl url = request.url();
        boolean proxyAuthorization = response.code() == 407;
        Object object = route;
        if (object == null || (object = ((Route)object).proxy()) == null) {
            object = Proxy.NO_PROXY;
        }
        Object proxy = object;
        for (Challenge challenge : challenges) {
            PasswordAuthentication auth;
            PasswordAuthentication passwordAuthentication;
            Object dns;
            if (!StringsKt.equals("Basic", challenge.scheme(), true)) continue;
            Object object2 = route;
            if (object2 == null || (object2 = ((Route)object2).address()) == null || (object2 = ((Address)object2).dns()) == null) {
                object2 = dns = this.defaultDns;
            }
            if (proxyAuthorization) {
                SocketAddress socketAddress = ((Proxy)proxy).address();
                Intrinsics.checkNotNull(socketAddress, "null cannot be cast to non-null type java.net.InetSocketAddress");
                InetSocketAddress proxyAddress = (InetSocketAddress)socketAddress;
                String string = proxyAddress.getHostName();
                Intrinsics.checkNotNullExpressionValue(proxy, "proxy");
                passwordAuthentication = java.net.Authenticator.requestPasswordAuthentication(string, this.connectToInetAddress((Proxy)proxy, url, (Dns)dns), proxyAddress.getPort(), url.scheme(), challenge.realm(), challenge.scheme(), url.url(), Authenticator.RequestorType.PROXY);
            } else {
                String string = url.host();
                Intrinsics.checkNotNullExpressionValue(proxy, "proxy");
                passwordAuthentication = java.net.Authenticator.requestPasswordAuthentication(string, this.connectToInetAddress((Proxy)proxy, url, (Dns)dns), url.port(), url.scheme(), challenge.realm(), challenge.scheme(), url.url(), Authenticator.RequestorType.SERVER);
            }
            if ((auth = passwordAuthentication) == null) continue;
            String credentialHeader = proxyAuthorization ? "Proxy-Authorization" : "Authorization";
            String string = auth.getUserName();
            Intrinsics.checkNotNullExpressionValue(string, "auth.userName");
            char[] cArray = auth.getPassword();
            Intrinsics.checkNotNullExpressionValue(cArray, "auth.password");
            char[] cArray2 = cArray;
            String credential = Credentials.basic(string, new String(cArray2), challenge.charset());
            return request.newBuilder().header(credentialHeader, credential).build();
        }
        return null;
    }

    private final InetAddress connectToInetAddress(Proxy $this$connectToInetAddress, HttpUrl url, Dns dns) throws IOException {
        InetAddress inetAddress;
        Proxy.Type type = $this$connectToInetAddress.type();
        if ((type == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) == 1) {
            inetAddress = CollectionsKt.first(dns.lookup(url.host()));
        } else {
            SocketAddress socketAddress = $this$connectToInetAddress.address();
            Intrinsics.checkNotNull(socketAddress, "null cannot be cast to non-null type java.net.InetSocketAddress");
            InetAddress inetAddress2 = ((InetSocketAddress)socketAddress).getAddress();
            inetAddress = inetAddress2;
            Intrinsics.checkNotNullExpressionValue(inetAddress2, "address() as InetSocketAddress).address");
        }
        return inetAddress;
    }

    public JavaNetAuthenticator() {
        this(null, 1, null);
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Proxy.Type.values().length];
            try {
                nArray[Proxy.Type.DIRECT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

