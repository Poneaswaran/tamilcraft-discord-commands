/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TIntObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TIntObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.regex.Pattern;

public class MarkdownSanitizer {
    public static final int NORMAL = 0;
    public static final int BOLD = 1;
    public static final int ITALICS_U = 2;
    public static final int ITALICS_A = 4;
    public static final int MONO = 8;
    public static final int MONO_TWO = 16;
    public static final int BLOCK = 32;
    public static final int SPOILER = 64;
    public static final int UNDERLINE = 128;
    public static final int STRIKE = 256;
    public static final int QUOTE = 512;
    public static final int QUOTE_BLOCK = 1024;
    private static final int ESCAPED_BOLD = -2147483647;
    private static final int ESCAPED_ITALICS_U = -2147483646;
    private static final int ESCAPED_ITALICS_A = -2147483644;
    private static final int ESCAPED_MONO = -2147483640;
    private static final int ESCAPED_MONO_TWO = -2147483632;
    private static final int ESCAPED_BLOCK = -2147483616;
    private static final int ESCAPED_SPOILER = -2147483584;
    private static final int ESCAPED_UNDERLINE = -2147483520;
    private static final int ESCAPED_STRIKE = -2147483392;
    private static final int ESCAPED_QUOTE = -2147483136;
    private static final int ESCAPED_QUOTE_BLOCK = -2147482624;
    private static final Pattern codeLanguage = Pattern.compile("^\\w+\n.*", 40);
    private static final Pattern quote = Pattern.compile("> +.*", 40);
    private static final Pattern quoteBlock = Pattern.compile(">>>\\s+\\S.*", 40);
    private static final TIntObjectMap<String> tokens = new TIntObjectHashMap<String>();
    private int ignored;
    private SanitizationStrategy strategy;

    public MarkdownSanitizer() {
        this.ignored = 0;
        this.strategy = SanitizationStrategy.REMOVE;
    }

    public MarkdownSanitizer(int ignored, @Nullable SanitizationStrategy strategy) {
        this.ignored = ignored;
        this.strategy = strategy == null ? SanitizationStrategy.REMOVE : strategy;
    }

    @Nonnull
    public static String sanitize(@Nonnull String sequence2) {
        return MarkdownSanitizer.sanitize(sequence2, SanitizationStrategy.REMOVE);
    }

    @Nonnull
    public static String sanitize(@Nonnull String sequence2, @Nonnull SanitizationStrategy strategy) {
        Checks.notNull(sequence2, "String");
        Checks.notNull((Object)strategy, "Strategy");
        return new MarkdownSanitizer().withStrategy(strategy).compute(sequence2);
    }

    @Nonnull
    public static String escape(@Nonnull String sequence2) {
        return MarkdownSanitizer.escape(sequence2, 0);
    }

    @Nonnull
    public static String escape(@Nonnull String sequence2, int ignored) {
        return new MarkdownSanitizer().withIgnored(ignored).withStrategy(SanitizationStrategy.ESCAPE).compute(sequence2);
    }

    @Nonnull
    public static String escape(@Nonnull String sequence2, boolean single) {
        Checks.notNull(sequence2, "Input");
        if (!single) {
            return MarkdownSanitizer.escape(sequence2);
        }
        StringBuilder builder = new StringBuilder();
        boolean escaped = false;
        boolean newline = true;
        block6: for (int i = 0; i < sequence2.length(); ++i) {
            char current = sequence2.charAt(i);
            if (newline) {
                newline = Character.isWhitespace(current);
                if (current == '>') {
                    if (i + 1 < sequence2.length() && Character.isWhitespace(sequence2.charAt(i + 1))) {
                        builder.append("\\>");
                        continue;
                    }
                    if (i + 3 < sequence2.length() && sequence2.startsWith(">>>", i) && Character.isWhitespace(sequence2.charAt(i + 3))) {
                        builder.append("\\>\\>\\>").append(sequence2.charAt(i + 3));
                        i += 3;
                        continue;
                    }
                    builder.append(current);
                    continue;
                }
            }
            if (escaped) {
                builder.append(current);
                escaped = false;
                continue;
            }
            switch (current) {
                case '*': 
                case '_': 
                case '`': {
                    builder.append('\\').append(current);
                    continue block6;
                }
                case '|': 
                case '~': {
                    if (i + 1 < sequence2.length() && sequence2.charAt(i + 1) == current) {
                        builder.append('\\').append(current).append('\\').append(current);
                        ++i;
                        continue block6;
                    }
                    builder.append(current);
                    continue block6;
                }
                case '\\': {
                    builder.append(current);
                    escaped = true;
                    continue block6;
                }
                case '\n': {
                    builder.append(current);
                    newline = true;
                    continue block6;
                }
                default: {
                    builder.append(current);
                }
            }
        }
        return builder.toString();
    }

    @Nonnull
    public MarkdownSanitizer withStrategy(@Nonnull SanitizationStrategy strategy) {
        Checks.notNull((Object)strategy, "Strategy");
        this.strategy = strategy;
        return this;
    }

    @Nonnull
    public MarkdownSanitizer withIgnored(int ignored) {
        this.ignored |= ignored;
        return this;
    }

    private int getRegion(int index, @Nonnull String sequence2) {
        if (sequence2.length() - index >= 3) {
            String threeChars;
            switch (threeChars = sequence2.substring(index, index + 3)) {
                case "```": {
                    return this.doesEscape(index, sequence2) ? -2147483616 : 32;
                }
                case "***": {
                    return this.doesEscape(index, sequence2) ? -2147483643 : 5;
                }
            }
        }
        if (sequence2.length() - index >= 2) {
            String twoChars;
            switch (twoChars = sequence2.substring(index, index + 2)) {
                case "**": {
                    return this.doesEscape(index, sequence2) ? -2147483647 : 1;
                }
                case "__": {
                    return this.doesEscape(index, sequence2) ? -2147483520 : 128;
                }
                case "~~": {
                    return this.doesEscape(index, sequence2) ? -2147483392 : 256;
                }
                case "``": {
                    return this.doesEscape(index, sequence2) ? -2147483632 : 16;
                }
                case "||": {
                    return this.doesEscape(index, sequence2) ? -2147483584 : 64;
                }
            }
        }
        char current = sequence2.charAt(index);
        switch (current) {
            case '*': {
                return this.doesEscape(index, sequence2) ? -2147483644 : 4;
            }
            case '_': {
                return this.doesEscape(index, sequence2) ? -2147483646 : 2;
            }
            case '`': {
                return this.doesEscape(index, sequence2) ? -2147483640 : 8;
            }
        }
        return 0;
    }

    private boolean hasCollision(int index, @Nonnull String sequence2, char c) {
        if (index < 0) {
            return false;
        }
        return index < sequence2.length() - 1 && sequence2.charAt(index + 1) == c;
    }

    private int findEndIndex(int afterIndex, int region, @Nonnull String sequence2) {
        if (this.isEscape(region)) {
            return -1;
        }
        int lastMatch = afterIndex + this.getDelta(region) + 1;
        block12: while (lastMatch != -1) {
            switch (region) {
                case 5: {
                    lastMatch = sequence2.indexOf("***", lastMatch);
                    break;
                }
                case 1: {
                    lastMatch = sequence2.indexOf("**", lastMatch);
                    if (lastMatch == -1 || !this.hasCollision(lastMatch + 1, sequence2, '*')) break;
                    lastMatch += 3;
                    continue block12;
                }
                case 4: {
                    lastMatch = sequence2.indexOf(42, lastMatch);
                    if (lastMatch == -1 || !this.hasCollision(lastMatch, sequence2, '*')) break;
                    if (this.hasCollision(lastMatch + 1, sequence2, '*')) {
                        lastMatch += 3;
                        continue block12;
                    }
                    lastMatch += 2;
                    continue block12;
                }
                case 128: {
                    lastMatch = sequence2.indexOf("__", lastMatch);
                    break;
                }
                case 2: {
                    lastMatch = sequence2.indexOf(95, lastMatch);
                    if (lastMatch == -1 || !this.hasCollision(lastMatch, sequence2, '_')) break;
                    lastMatch += 2;
                    continue block12;
                }
                case 64: {
                    lastMatch = sequence2.indexOf("||", lastMatch);
                    break;
                }
                case 32: {
                    lastMatch = sequence2.indexOf("```", lastMatch);
                    break;
                }
                case 16: {
                    lastMatch = sequence2.indexOf("``", lastMatch);
                    if (lastMatch == -1 || !this.hasCollision(lastMatch + 1, sequence2, '`')) break;
                    lastMatch += 3;
                    continue block12;
                }
                case 8: {
                    lastMatch = sequence2.indexOf(96, lastMatch);
                    if (lastMatch == -1 || !this.hasCollision(lastMatch, sequence2, '`')) break;
                    if (this.hasCollision(lastMatch + 1, sequence2, '`')) {
                        lastMatch += 3;
                        continue block12;
                    }
                    lastMatch += 2;
                    continue block12;
                }
                case 256: {
                    lastMatch = sequence2.indexOf("~~", lastMatch);
                    break;
                }
                default: {
                    return -1;
                }
            }
            if (lastMatch == -1 || !this.doesEscape(lastMatch, sequence2)) {
                return lastMatch;
            }
            ++lastMatch;
        }
        return -1;
    }

    @Nonnull
    private String handleRegion(int start, int end, @Nonnull String sequence2, int region) {
        String resolved = sequence2.substring(start, end);
        switch (region) {
            case 8: 
            case 16: 
            case 32: {
                return resolved;
            }
        }
        return new MarkdownSanitizer(this.ignored, this.strategy).compute(resolved);
    }

    private int getDelta(int region) {
        switch (region) {
            case -2147483643: 
            case -2147483616: 
            case 5: 
            case 32: {
                return 3;
            }
            case -2147483647: 
            case -2147483632: 
            case -2147483584: 
            case -2147483520: 
            case -2147483392: 
            case 1: 
            case 16: 
            case 64: 
            case 128: 
            case 256: {
                return 2;
            }
            case -2147483646: 
            case -2147483644: 
            case -2147483640: 
            case -2147483136: 
            case 2: 
            case 4: 
            case 8: {
                return 1;
            }
        }
        return 0;
    }

    private void applyStrategy(int region, @Nonnull String seq, @Nonnull StringBuilder builder) {
        if (this.strategy == SanitizationStrategy.REMOVE) {
            if (codeLanguage.matcher(seq).matches()) {
                builder.append(seq.substring(seq.indexOf("\n") + 1));
            } else {
                builder.append(seq);
            }
            return;
        }
        String token = tokens.get(region);
        if (token == null) {
            throw new IllegalStateException("Found illegal region for strategy ESCAPE '" + region + "' with no known format token!");
        }
        if (region == 128) {
            token = "_\\_";
        } else if (region == 1) {
            token = "*\\*";
        } else if (region == 5) {
            token = "*\\*\\*";
        }
        builder.append("\\").append(token).append(seq).append("\\").append(token);
    }

    private boolean doesEscape(int index, @Nonnull String seq) {
        int backslashes = 0;
        for (int i = index - 1; i > -1 && seq.charAt(i) == '\\'; --i) {
            ++backslashes;
        }
        return backslashes % 2 != 0;
    }

    private boolean isEscape(int region) {
        return (Integer.MIN_VALUE & region) != 0;
    }

    private boolean isIgnored(int nextRegion) {
        return (nextRegion & this.ignored) == nextRegion;
    }

    @Nonnull
    public String compute(@Nonnull String sequence2) {
        Checks.notNull(sequence2, "Input");
        StringBuilder builder = new StringBuilder();
        String end = this.handleQuote(sequence2);
        if (end != null) {
            return end;
        }
        boolean onlySpacesSinceNewLine = true;
        int i = 0;
        while (i < sequence2.length()) {
            int delta;
            int nextRegion = this.getRegion(i, sequence2);
            char c = sequence2.charAt(i);
            boolean isNewLine = c == '\n';
            boolean isSpace = c == ' ';
            boolean bl = onlySpacesSinceNewLine = isNewLine || onlySpacesSinceNewLine && isSpace;
            if (nextRegion == 0) {
                String result;
                builder.append(sequence2.charAt(i++));
                if (!isNewLine && (!isSpace || !onlySpacesSinceNewLine) || i >= sequence2.length() || (result = this.handleQuote(sequence2.substring(i))) == null) continue;
                return builder.append(result).toString();
            }
            int endRegion = this.findEndIndex(i, nextRegion, sequence2);
            if (this.isIgnored(nextRegion) || endRegion == -1) {
                delta = this.getDelta(nextRegion);
                for (int j = 0; j < delta; ++j) {
                    builder.append(sequence2.charAt(i++));
                }
                continue;
            }
            delta = this.getDelta(nextRegion);
            this.applyStrategy(nextRegion, this.handleRegion(i + delta, endRegion, sequence2, nextRegion), builder);
            i = endRegion + delta;
        }
        return builder.toString();
    }

    private String handleQuote(@Nonnull String sequence2) {
        if (!this.isIgnored(512) && quote.matcher(sequence2).matches()) {
            int start = sequence2.indexOf(62);
            if (start < 0) {
                start = 0;
            }
            StringBuilder builder = new StringBuilder(this.compute(sequence2.substring(start + 2)));
            if (this.strategy == SanitizationStrategy.ESCAPE) {
                builder.insert(0, "\\> ");
            }
            return builder.toString();
        }
        if (!this.isIgnored(1024) && quoteBlock.matcher(sequence2).matches()) {
            if (this.strategy == SanitizationStrategy.ESCAPE) {
                return this.compute("\\".concat(sequence2));
            }
            return this.compute(sequence2.substring(4));
        }
        return null;
    }

    static {
        tokens.put(0, "");
        tokens.put(1, "**");
        tokens.put(2, "_");
        tokens.put(4, "*");
        tokens.put(5, "***");
        tokens.put(8, "`");
        tokens.put(16, "``");
        tokens.put(32, "```");
        tokens.put(64, "||");
        tokens.put(128, "__");
        tokens.put(256, "~~");
    }

    public static enum SanitizationStrategy {
        REMOVE,
        ESCAPE;

    }
}

