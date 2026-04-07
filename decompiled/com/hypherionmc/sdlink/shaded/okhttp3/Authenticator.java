/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.okhttp3;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmField;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okhttp3.Route;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.authenticator.JavaNetAuthenticator;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00e6\u0080\u0001\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\t"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/Authenticator;", "", "authenticate", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Request;", "route", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Route;", "response", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Response;", "Companion", "okhttp"})
public interface Authenticator {
    @NotNull
    public static final Companion Companion = com.hypherionmc.sdlink.shaded.okhttp3.Authenticator$Companion.$$INSTANCE;
    @JvmField
    @NotNull
    public static final Authenticator NONE = new Companion.AuthenticatorNone();
    @JvmField
    @NotNull
    public static final Authenticator JAVA_NET_AUTHENTICATOR = new JavaNetAuthenticator(null, 1, null);

    @Nullable
    public Request authenticate(@Nullable Route var1, @NotNull Response var2) throws IOException;

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0006B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0001R\u0013\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0001\u00a8\u0006\u0007"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/Authenticator$Companion;", "", "()V", "JAVA_NET_AUTHENTICATOR", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Authenticator;", "NONE", "AuthenticatorNone", "okhttp"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        static {
            $$INSTANCE = new Companion();
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/Authenticator$Companion$AuthenticatorNone;", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Authenticator;", "()V", "authenticate", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Request;", "route", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Route;", "response", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Response;", "okhttp"})
        private static final class AuthenticatorNone
        implements Authenticator {
            @Override
            @Nullable
            public Request authenticate(@Nullable Route route, @NotNull Response response) {
                Intrinsics.checkNotNullParameter(response, "response");
                return null;
            }
        }
    }
}

