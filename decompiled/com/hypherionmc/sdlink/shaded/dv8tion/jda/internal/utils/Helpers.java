/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Helpers {
    private static final ZoneOffset OFFSET = ZoneOffset.of("+00:00");
    private static final Consumer EMPTY_CONSUMER = v -> {};

    public static <T> Consumer<T> emptyConsumer() {
        return EMPTY_CONSUMER;
    }

    public static OffsetDateTime toOffset(long instant) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(instant), OFFSET);
    }

    public static long toTimestamp(String iso8601String) {
        TemporalAccessor joinedAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(iso8601String);
        return Instant.from(joinedAt).toEpochMilli();
    }

    public static OffsetDateTime toOffsetDateTime(@Nullable TemporalAccessor temporal) {
        ZoneOffset offset;
        if (temporal == null) {
            return null;
        }
        if (temporal instanceof OffsetDateTime) {
            return (OffsetDateTime)temporal;
        }
        try {
            offset = ZoneOffset.from(temporal);
        }
        catch (DateTimeException ignore) {
            offset = ZoneOffset.UTC;
        }
        try {
            LocalDateTime ldt = LocalDateTime.from(temporal);
            return OffsetDateTime.of(ldt, offset);
        }
        catch (DateTimeException ignore) {
            try {
                Instant instant = Instant.from(temporal);
                return OffsetDateTime.ofInstant(instant, offset);
            }
            catch (DateTimeException ex) {
                throw new DateTimeException("Unable to obtain OffsetDateTime from TemporalAccessor: " + temporal + " of type " + temporal.getClass().getName(), ex);
            }
        }
    }

    public static String format(String format, Object ... args2) {
        return String.format(Locale.ROOT, format, args2);
    }

    public static boolean isEmpty(CharSequence seq) {
        return seq == null || seq.length() == 0;
    }

    public static boolean containsWhitespace(CharSequence seq) {
        if (Helpers.isEmpty(seq)) {
            return false;
        }
        for (int i = 0; i < seq.length(); ++i) {
            if (!Character.isWhitespace(seq.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isBlank(CharSequence seq) {
        if (Helpers.isEmpty(seq)) {
            return true;
        }
        for (int i = 0; i < seq.length(); ++i) {
            if (Character.isWhitespace(seq.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static int countMatches(CharSequence seq, char c) {
        if (Helpers.isEmpty(seq)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < seq.length(); ++i) {
            if (seq.charAt(i) != c) continue;
            ++count;
        }
        return count;
    }

    public static String truncate(String input, int maxWidth) {
        if (input == null) {
            return null;
        }
        Checks.notNegative(maxWidth, "maxWidth");
        if (input.length() <= maxWidth) {
            return input;
        }
        if (maxWidth == 0) {
            return "";
        }
        return input.substring(0, maxWidth);
    }

    public static String rightPad(String input, int size) {
        int pads = size - input.length();
        if (pads <= 0) {
            return input;
        }
        StringBuilder out = new StringBuilder(input);
        for (int i = pads; i > 0; --i) {
            out.append(' ');
        }
        return out.toString();
    }

    public static String leftPad(String input, int size) {
        int pads = size - input.length();
        if (pads <= 0) {
            return input;
        }
        StringBuilder out = new StringBuilder();
        for (int i = pads; i > 0; --i) {
            out.append(' ');
        }
        return out.append(input).toString();
    }

    public static boolean isNumeric(String input) {
        if (Helpers.isEmpty(input)) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return true;
    }

    public static int codePointLength(CharSequence string) {
        return (int)string.codePoints().count();
    }

    public static String[] split(String input, String match) {
        ArrayList<String> out = new ArrayList<String>();
        int i = 0;
        while (i < input.length()) {
            int j = input.indexOf(match, i);
            if (j == -1) {
                out.add(input.substring(i));
                break;
            }
            out.add(input.substring(i, j));
            i = j + match.length();
        }
        return out.toArray(new String[0]);
    }

    public static boolean equals(String a, String b, boolean ignoreCase) {
        return ignoreCase ? a == b || a != null && b != null && a.equalsIgnoreCase(b) : Objects.equals(a, b);
    }

    public static boolean deepEquals(Collection<?> first, Collection<?> second) {
        if (first == second) {
            return true;
        }
        if (first == null || second == null || first.size() != second.size()) {
            return false;
        }
        Iterator<?> itFirst = first.iterator();
        Iterator<?> itSecond = second.iterator();
        while (itFirst.hasNext()) {
            Object elementSecond;
            Object elementFirst = itFirst.next();
            if (Objects.equals(elementFirst, elementSecond = itSecond.next())) continue;
            return false;
        }
        return true;
    }

    public static boolean deepEqualsUnordered(Collection<?> first, Collection<?> second) {
        if (first == second) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }
        return first.size() == second.size() && second.containsAll(first);
    }

    public static <E extends Enum<E>> EnumSet<E> copyEnumSet(Class<E> clazz, Collection<E> col) {
        return col == null || col.isEmpty() ? EnumSet.noneOf(clazz) : EnumSet.copyOf(col);
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T ... elements) {
        HashSet set = new HashSet(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    @SafeVarargs
    public static <T> List<T> listOf(T ... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }

    public static TLongObjectMap<DataObject> convertToMap(ToLongFunction<DataObject> getId, DataArray array) {
        TLongObjectHashMap<DataObject> map = new TLongObjectHashMap<DataObject>();
        for (int i = 0; i < array.length(); ++i) {
            DataObject obj = array.getObject(i);
            long objId = getId.applyAsLong(obj);
            map.put(objId, obj);
        }
        return map;
    }

    public static <T extends Throwable> T appendCause(T throwable, Throwable cause) {
        Object t = throwable;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        t.initCause(cause);
        return throwable;
    }

    public static boolean hasCause(Throwable throwable, Class<? extends Throwable> cause) {
        for (Throwable cursor = throwable; cursor != null; cursor = cursor.getCause()) {
            if (!cause.isInstance(cursor)) continue;
            return true;
        }
        return false;
    }

    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList);
    }

    public static <T> Collector<T, ?, DataArray> toDataArray() {
        return Collector.of(DataArray::empty, DataArray::add, DataArray::addAll, new Collector.Characteristics[0]);
    }

    public static String durationToString(Duration duration, TimeUnit resolutionUnit) {
        long actual = resolutionUnit.convert(duration.getSeconds(), TimeUnit.SECONDS);
        String raw = actual + " " + resolutionUnit.toString().toLowerCase(Locale.ROOT);
        long days = duration.toDays();
        long hours = duration.toHours() % 24L;
        long minutes = duration.toMinutes() % 60L;
        long seconds = duration.getSeconds() - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);
        StringJoiner joiner = new StringJoiner(" ");
        if (days > 0L) {
            joiner.add(days + " days");
        }
        if (hours > 0L) {
            joiner.add(hours + " hours");
        }
        if (minutes > 0L) {
            joiner.add(minutes + " minutes");
        }
        if (seconds > 0L) {
            joiner.add(seconds + " seconds");
        }
        return raw + " (" + joiner + ")";
    }
}

