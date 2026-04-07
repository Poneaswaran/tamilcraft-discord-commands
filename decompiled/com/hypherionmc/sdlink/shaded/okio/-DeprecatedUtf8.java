/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.okio;

import com.hypherionmc.sdlink.shaded.kotlin.Deprecated;
import com.hypherionmc.sdlink.shaded.kotlin.DeprecationLevel;
import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.ReplaceWith;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.okio.Utf8;
import org.jetbrains.annotations.NotNull;

@Deprecated(message="changed in Okio 2.x")
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0007\u00a8\u0006\n"}, d2={"Lcom/hypherionmc/sdlink/shaded/okio/-DeprecatedUtf8;", "", "()V", "size", "", "string", "", "beginIndex", "", "endIndex", "com.hypherionmc.sdlink.shaded.okio"})
public final class -DeprecatedUtf8 {
    @NotNull
    public static final -DeprecatedUtf8 INSTANCE = new -DeprecatedUtf8();

    private -DeprecatedUtf8() {
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="string.utf8Size()", imports={"com.hypherionmc.sdlink.shaded.okio.utf8Size"}), level=DeprecationLevel.ERROR)
    public final long size(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return Utf8.size$default(string, 0, 0, 3, null);
    }

    @Deprecated(message="moved to extension function", replaceWith=@ReplaceWith(expression="string.utf8Size(beginIndex, endIndex)", imports={"com.hypherionmc.sdlink.shaded.okio.utf8Size"}), level=DeprecationLevel.ERROR)
    public final long size(@NotNull String string, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(string, "string");
        return Utf8.size(string, beginIndex, endIndex);
    }
}

