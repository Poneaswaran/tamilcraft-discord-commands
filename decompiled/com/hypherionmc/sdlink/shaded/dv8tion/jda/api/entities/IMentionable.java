/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Formattable;
import java.util.Formatter;

public interface IMentionable
extends Formattable,
ISnowflake {
    @Nonnull
    public String getAsMention();

    @Override
    default public void formatTo(Formatter formatter, int flags, int width, int precision) {
        boolean leftJustified = (flags & 1) == 1;
        boolean upper = (flags & 2) == 2;
        String out = upper ? this.getAsMention().toUpperCase(formatter.locale()) : this.getAsMention();
        MiscUtil.appendTo(formatter, width, precision, leftJustified, out);
    }
}

