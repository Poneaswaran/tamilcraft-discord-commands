/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.text;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.internal.InlineOnly;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.text.Regex;
import com.hypherionmc.sdlink.shaded.kotlin.text.StringsKt__IndentKt;
import java.util.regex.Pattern;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00a8\u0006\u0003"}, d2={"toRegex", "Lcom/hypherionmc/sdlink/shaded/kotlin/text/Regex;", "Ljava/util/regex/Pattern;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"}, xs="com/hypherionmc/sdlink/shaded/kotlin/text/StringsKt")
class StringsKt__RegexExtensionsJVMKt
extends StringsKt__IndentKt {
    @InlineOnly
    private static final Regex toRegex(Pattern $this$toRegex) {
        Intrinsics.checkNotNullParameter($this$toRegex, "<this>");
        return new Regex($this$toRegex);
    }
}

