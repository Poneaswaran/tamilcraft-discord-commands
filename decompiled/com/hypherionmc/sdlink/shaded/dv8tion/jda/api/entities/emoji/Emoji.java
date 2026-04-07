/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.CustomEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji.UnicodeEmojiImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EncodingUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Formattable;
import java.util.Formatter;
import java.util.regex.Matcher;

public interface Emoji
extends SerializableData,
Formattable {
    @Nonnull
    public static UnicodeEmoji fromUnicode(@Nonnull String code) {
        Checks.notEmpty(code, "Unicode");
        if (code.startsWith("U+") || code.startsWith("u+")) {
            String[] codepoints;
            StringBuilder emoji = new StringBuilder();
            for (String codepoint : codepoints = code.trim().split("\\s*[uU]\\+")) {
                emoji.append(codepoint.isEmpty() ? "" : EncodingUtil.decodeCodepoint("U+" + codepoint));
            }
            code = emoji.toString();
        }
        return new UnicodeEmojiImpl(code);
    }

    @Nonnull
    public static CustomEmoji fromCustom(@Nonnull String name, long id, boolean animated) {
        Checks.notEmpty(name, "Name");
        return new CustomEmojiImpl(name, id, animated);
    }

    @Nonnull
    public static CustomEmoji fromCustom(@Nonnull CustomEmoji emoji) {
        Checks.notNull(emoji, "Emoji");
        return Emoji.fromCustom(emoji.getName(), emoji.getIdLong(), emoji.isAnimated());
    }

    @Nonnull
    public static EmojiUnion fromFormatted(@Nonnull String code) {
        Checks.notEmpty(code, "Formatting Code");
        Matcher matcher = Message.MentionType.EMOJI.getPattern().matcher(code);
        if (matcher.matches()) {
            return (EmojiUnion)((Object)Emoji.fromCustom(matcher.group(1), Long.parseUnsignedLong(matcher.group(2)), code.startsWith("<a")));
        }
        return (EmojiUnion)((Object)Emoji.fromUnicode(code));
    }

    @Nonnull
    public static EmojiUnion fromData(@Nonnull DataObject emoji) {
        Checks.notNull(emoji, "Emoji Data");
        if (emoji.isNull("id")) {
            return (EmojiUnion)((Object)Emoji.fromUnicode(emoji.getString("name")));
        }
        return (EmojiUnion)((Object)Emoji.fromCustom(emoji.getString("name"), emoji.getUnsignedLong("id"), emoji.getBoolean("animated")));
    }

    @Nonnull
    public Type getType();

    @Nonnull
    public String getName();

    @Nonnull
    public String getAsReactionCode();

    @Nonnull
    public String getFormatted();

    @Override
    default public void formatTo(Formatter formatter, int flags, int width, int precision) {
        boolean leftJustified = (flags & 1) == 1;
        String out = this.getFormatted();
        MiscUtil.appendTo(formatter, width, precision, leftJustified, out);
    }

    public static enum Type {
        UNICODE,
        CUSTOM;

    }
}

