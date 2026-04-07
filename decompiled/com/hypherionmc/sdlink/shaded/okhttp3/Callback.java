/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okhttp3;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.okhttp3.Call;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2={"Lcom/hypherionmc/sdlink/shaded/okhttp3/Callback;", "", "onFailure", "", "call", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lcom/hypherionmc/sdlink/shaded/okhttp3/Response;", "okhttp"})
public interface Callback {
    public void onFailure(@NotNull Call var1, @NotNull IOException var2);

    public void onResponse(@NotNull Call var1, @NotNull Response var2) throws IOException;
}

