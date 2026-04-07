/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.intellij.lang.annotations.PrintFormat
 *  org.jetbrains.annotations.Contract
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.MissingAccessException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.intellij.lang.annotations.PrintFormat;
import org.jetbrains.annotations.Contract;

public class Checks {
    public static final Pattern ALPHANUMERIC_WITH_DASH = Pattern.compile("[\\w-]+", 256);
    public static final Pattern ALPHANUMERIC = Pattern.compile("\\w+", 256);
    public static final Pattern LOWERCASE_ASCII_ALPHANUMERIC = Pattern.compile("[a-z0-9_]+");

    @Contract(value="null -> fail")
    public static void isSnowflake(String snowflake) {
        Checks.isSnowflake(snowflake, snowflake);
    }

    @Contract(value="null, _ -> fail")
    public static void isSnowflake(String snowflake, String message) {
        Checks.notNull(snowflake, message);
        if (snowflake.length() > 20 || !Helpers.isNumeric(snowflake)) {
            throw new IllegalArgumentException(message + " is not a valid snowflake value! Provided: \"" + snowflake + "\"");
        }
    }

    @Contract(value="false, _ -> fail")
    public static void check(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    @Contract(value="false, _, _ -> fail")
    public static void check(boolean expression, @PrintFormat String message, Object ... args2) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, args2));
        }
    }

    @Contract(value="false, _, _ -> fail")
    public static void check(boolean expression, @PrintFormat String message, Object arg) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, arg));
        }
    }

    @Contract(value="null, _ -> fail")
    public static void notNull(Object argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        }
    }

    @Contract(value="null, _ -> fail")
    public static void notEmpty(CharSequence argument, String name) {
        Checks.notNull(argument, name);
        if (Helpers.isEmpty(argument)) {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    @Contract(value="null, _ -> fail")
    public static void notBlank(CharSequence argument, String name) {
        Checks.notNull(argument, name);
        if (Helpers.isBlank(argument)) {
            throw new IllegalArgumentException(name + " may not be blank");
        }
    }

    @Contract(value="null, _ -> fail")
    public static void noWhitespace(CharSequence argument, String name) {
        Checks.notNull(argument, name);
        if (Helpers.containsWhitespace(argument)) {
            throw new IllegalArgumentException(name + " may not contain blanks. Provided: \"" + argument + "\"");
        }
    }

    @Contract(value="null, _ -> fail")
    public static void notEmpty(Collection<?> argument, String name) {
        Checks.notNull(argument, name);
        if (argument.isEmpty()) {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    @Contract(value="null, _ -> fail")
    public static void notEmpty(Object[] argument, String name) {
        Checks.notNull(argument, name);
        if (argument.length == 0) {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    @Contract(value="null, _ -> fail")
    public static void noneNull(Collection<?> argument, String name) {
        Checks.notNull(argument, name);
        argument.forEach(it -> Checks.notNull(it, name));
    }

    @Contract(value="null, _ -> fail")
    public static void noneNull(Object[] argument, String name) {
        Checks.notNull(argument, name);
        for (Object it : argument) {
            Checks.notNull(it, name);
        }
    }

    @Contract(value="null, _ -> fail")
    public static <T extends CharSequence> void noneEmpty(Collection<T> argument, String name) {
        Checks.notNull(argument, name);
        argument.forEach(it -> Checks.notEmpty(it, name));
    }

    @Contract(value="null, _ -> fail")
    public static <T extends CharSequence> void noneBlank(Collection<T> argument, String name) {
        Checks.notNull(argument, name);
        argument.forEach(it -> Checks.notBlank(it, name));
    }

    @Contract(value="null, _ -> fail")
    public static <T extends CharSequence> void noneContainBlanks(Collection<T> argument, String name) {
        Checks.notNull(argument, name);
        argument.forEach(it -> Checks.noWhitespace(it, name));
    }

    public static void inRange(String input, int min, int max, String name) {
        Checks.notNull(input, name);
        int length = Helpers.codePointLength(input);
        Checks.check(min <= length && length <= max, "%s must be between %d and %d characters long! Provided: \"%s\"", name, min, max, input);
    }

    public static void notLonger(String input, int length, String name) {
        Checks.notNull(input, name);
        Checks.check(Helpers.codePointLength(input) <= length, "%s may not be longer than %d characters! Provided: \"%s\"", name, length, input);
    }

    public static void matches(String input, Pattern pattern, String name) {
        Checks.notNull(input, name);
        Checks.check(pattern.matcher(input).matches(), "%s must match regex ^%s$. Provided: \"%s\"", name, pattern.pattern(), input);
    }

    public static void isLowercase(String input, String name) {
        Checks.notNull(input, name);
        Checks.check(input.toLowerCase(Locale.ROOT).equals(input), "%s must be lowercase only! Provided: \"%s\"", name, input);
    }

    public static void positive(int n, String name) {
        if (n <= 0) {
            throw new IllegalArgumentException(name + " may not be negative or zero");
        }
    }

    public static void positive(long n, String name) {
        if (n <= 0L) {
            throw new IllegalArgumentException(name + " may not be negative or zero");
        }
    }

    public static void notNegative(int n, String name) {
        if (n < 0) {
            throw new IllegalArgumentException(name + " may not be negative");
        }
    }

    public static void notNegative(long n, String name) {
        if (n < 0L) {
            throw new IllegalArgumentException(name + " may not be negative");
        }
    }

    public static void notLonger(Duration duration, Duration maxDuration, TimeUnit resolutionUnit, String name) {
        Checks.notNull(duration, name);
        Checks.check(duration.compareTo(maxDuration) <= 0, "%s may not be longer than %s. Provided: %s", name, JDALogger.getLazyString(() -> Helpers.durationToString(maxDuration, resolutionUnit)), JDALogger.getLazyString(() -> Helpers.durationToString(duration, resolutionUnit)));
    }

    public static <T> void checkUnique(Stream<T> stream, String format, BiFunction<Long, T, Object[]> getArgs) {
        Map counts = stream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Map.Entry entry : counts.entrySet()) {
            if (entry.getValue() <= 1L) continue;
            Object[] args2 = getArgs.apply(entry.getValue(), (Long)entry.getKey());
            throw new IllegalArgumentException(Helpers.format(format, args2));
        }
    }

    public static void checkDuplicateIds(Stream<? extends LayoutComponent> layouts) {
        Stream<String> stream = layouts.flatMap(row -> row.getComponents().stream()).filter(ActionComponent.class::isInstance).map(ActionComponent.class::cast).map(ActionComponent::getId).filter(Objects::nonNull);
        Checks.checkUnique(stream, "Cannot have components with duplicate custom IDs. Id: \"%s\" appeared %d times!", (count, value) -> new Object[]{value, count});
    }

    public static void checkComponents(String errorMessage, Collection<? extends Component> components, Predicate<Component> predicate) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (Component component : components) {
            Checks.handleComponent(component, predicate, sb, "root.components[" + idx + "]");
            ++idx;
        }
        if (sb.length() > 0) {
            throw new IllegalArgumentException(errorMessage + "\n" + sb.toString().trim());
        }
    }

    public static void checkComponents(String errorMessage, Component[] components, Predicate<Component> predicate) {
        Checks.checkComponents(errorMessage, Arrays.asList(components), predicate);
    }

    private static void handleComponent(Component component, Predicate<Component> predicate, StringBuilder sb, String path) {
        if (!predicate.test(component)) {
            sb.append(" - ").append(path).append(" - <").append((Object)component.getType()).append(">\n");
        }
        if (component instanceof LayoutComponent) {
            int idx = 0;
            for (Component child : (LayoutComponent)component) {
                Checks.handleComponent(child, predicate, sb, path + ".components[" + idx + "]");
                ++idx;
            }
        }
    }

    public static void checkAccess(IPermissionHolder issuer, GuildChannel channel) {
        if (issuer.hasAccess(channel)) {
            return;
        }
        EnumSet<Permission> perms = issuer.getPermissionsExplicit(channel);
        if (channel instanceof AudioChannel && !perms.contains((Object)Permission.VOICE_CONNECT)) {
            throw new MissingAccessException(channel, Permission.VOICE_CONNECT);
        }
        throw new MissingAccessException(channel, Permission.VIEW_CHANNEL);
    }

    public static void checkSupportedChannelTypes(EnumSet<ChannelType> supported, ChannelType type, String what) {
        Checks.check(supported.contains((Object)type), "Can only configure %s for channels of types %s", what, JDALogger.getLazyString(() -> supported.stream().map(Enum::name).collect(Collectors.joining(", "))));
    }
}

