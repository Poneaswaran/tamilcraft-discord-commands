/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.kotlin.text;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.text.MatchGroup;
import com.hypherionmc.sdlink.shaded.kotlin.text.MatchGroupCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bg\u0018\u00002\u00020\u0001J\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6\u0002\u00a8\u0006\u0006"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/text/MatchNamedGroupCollection;", "Lcom/hypherionmc/sdlink/shaded/kotlin/text/MatchGroupCollection;", "get", "Lcom/hypherionmc/sdlink/shaded/kotlin/text/MatchGroup;", "name", "", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@SinceKotlin(version="1.1")
public interface MatchNamedGroupCollection
extends MatchGroupCollection {
    @Nullable
    public MatchGroup get(@NotNull String var1);
}

