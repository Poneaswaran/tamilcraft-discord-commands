/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.text;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.internal.InlineOnly;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.text.Regex;
import com.hypherionmc.sdlink.shaded.kotlin.text.RegexOption;
import com.hypherionmc.sdlink.shaded.kotlin.text.StringsKt__RegexExtensionsJVMKt;
import java.util.Set;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\b\u00a8\u0006\u0007"}, d2={"toRegex", "Lcom/hypherionmc/sdlink/shaded/kotlin/text/Regex;", "", "options", "", "Lcom/hypherionmc/sdlink/shaded/kotlin/text/RegexOption;", "option", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"}, xs="com/hypherionmc/sdlink/shaded/kotlin/text/StringsKt")
class StringsKt__RegexExtensionsKt
extends StringsKt__RegexExtensionsJVMKt {
    @InlineOnly
    private static final Regex toRegex(String $this$toRegex) {
        Intrinsics.checkNotNullParameter($this$toRegex, "<this>");
        return new Regex($this$toRegex);
    }

    @InlineOnly
    private static final Regex toRegex(String $this$toRegex, RegexOption option) {
        Intrinsics.checkNotNullParameter($this$toRegex, "<this>");
        Intrinsics.checkNotNullParameter(option, "option");
        return new Regex($this$toRegex, option);
    }

    @InlineOnly
    private static final Regex toRegex(String $this$toRegex, Set<? extends RegexOption> options) {
        Intrinsics.checkNotNullParameter($this$toRegex, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return new Regex($this$toRegex, options);
    }
}

