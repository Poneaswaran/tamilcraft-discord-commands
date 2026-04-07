/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.JvmField;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okhttp3.Cookie;
import com.hypherionmc.sdlink.shaded.okhttp3.HttpUrl;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nJ\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&\u00a8\u0006\u000b"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/CookieJar;", "", "loadForRequest", "", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Cookie;", "url", "Lcom/hypherionmc/sdlink/shaded/okhttp3/HttpUrl;", "saveFromResponse", "", "cookies", "Companion", "okhttp"})
public interface CookieJar {
    @NotNull
    public static final Companion Companion = com.hypherionmc.sdlink.shaded.okhttp3.CookieJar$Companion.$$INSTANCE;
    @JvmField
    @NotNull
    public static final CookieJar NO_COOKIES = new Companion.NoCookies();

    public void saveFromResponse(@NotNull HttpUrl var1, @NotNull List<Cookie> var2);

    @NotNull
    public List<Cookie> loadForRequest(@NotNull HttpUrl var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0005B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0001\u00a8\u0006\u0006"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/CookieJar$Companion;", "", "()V", "NO_COOKIES", "Lcom/hypherionmc/sdlink/shaded/okhttp3/CookieJar;", "NoCookies", "okhttp"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        static {
            $$INSTANCE = new Companion();
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a8\u0006\u000b"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/CookieJar$Companion$NoCookies;", "Lcom/hypherionmc/sdlink/shaded/okhttp3/CookieJar;", "()V", "loadForRequest", "", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Cookie;", "url", "Lcom/hypherionmc/sdlink/shaded/okhttp3/HttpUrl;", "saveFromResponse", "", "cookies", "okhttp"})
        private static final class NoCookies
        implements CookieJar {
            @Override
            public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
                Intrinsics.checkNotNullParameter(url, "url");
                Intrinsics.checkNotNullParameter(cookies, "cookies");
            }

            @Override
            @NotNull
            public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
                Intrinsics.checkNotNullParameter(url, "url");
                return CollectionsKt.emptyList();
            }
        }
    }
}

