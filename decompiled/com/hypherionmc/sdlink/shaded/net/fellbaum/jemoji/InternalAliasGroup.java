/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Emoji;
import java.util.Collection;
import java.util.function.Function;

enum InternalAliasGroup {
    DISCORD(Emoji::getDiscordAliases),
    GITHUB(Emoji::getGithubAliases),
    SLACK(Emoji::getSlackAliases);

    private final Function<Emoji, Collection<String>> aliasCollectionSupplier;

    private InternalAliasGroup(Function<Emoji, Collection<String>> aliasCollectionSupplier) {
        this.aliasCollectionSupplier = aliasCollectionSupplier;
    }

    public Function<Emoji, Collection<String>> getAliasCollectionSupplier() {
        return this.aliasCollectionSupplier;
    }
}

