/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.kotlin.sequences;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.collections.CollectionsKt;
import com.hypherionmc.sdlink.shaded.kotlin.internal.InlineOnly;
import com.hypherionmc.sdlink.shaded.kotlin.jvm.internal.Intrinsics;
import com.hypherionmc.sdlink.shaded.kotlin.sequences.Sequence;
import com.hypherionmc.sdlink.shaded.kotlin.sequences.SequencesKt;
import com.hypherionmc.sdlink.shaded.kotlin.sequences.SequencesKt__SequenceBuilderKt;
import java.util.Enumeration;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u00a8\u0006\u0004"}, d2={"asSequence", "Lcom/hypherionmc/sdlink/shaded/kotlin/sequences/Sequence;", "T", "Ljava/util/Enumeration;", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"}, xs="com/hypherionmc/sdlink/shaded/kotlin/sequences/SequencesKt")
class SequencesKt__SequencesJVMKt
extends SequencesKt__SequenceBuilderKt {
    @InlineOnly
    private static final <T> Sequence<T> asSequence(Enumeration<T> $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
        return SequencesKt.asSequence(CollectionsKt.iterator($this$asSequence));
    }
}

