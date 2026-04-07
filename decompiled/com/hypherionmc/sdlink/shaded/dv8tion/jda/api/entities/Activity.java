/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RichPresence;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public interface Activity {
    public static final Pattern STREAMING_URL = Pattern.compile("https?://(www\\.)?(twitch\\.tv/|youtube\\.com/watch\\?v=).+", 2);
    public static final int MAX_ACTIVITY_NAME_LENGTH = 128;
    public static final int MAX_ACTIVITY_STATE_LENGTH = 128;

    public boolean isRich();

    @Nullable
    public RichPresence asRichPresence();

    @Nonnull
    public String getName();

    @Nullable
    public String getState();

    @Nullable
    public String getUrl();

    @Nonnull
    public ActivityType getType();

    @Nullable
    public Timestamps getTimestamps();

    @Nullable
    public EmojiUnion getEmoji();

    @Nonnull
    @Contract(value="_->new")
    public Activity withState(@Nullable String var1);

    @Nonnull
    public static Activity playing(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 128, "Name");
        return EntityBuilder.createActivity(name, null, ActivityType.PLAYING);
    }

    @Nonnull
    public static Activity streaming(@Nonnull String name, @Nullable String url) {
        Checks.notEmpty(name, "Provided game name");
        name = Helpers.isBlank(name) ? name : name.trim();
        Checks.notLonger(name, 128, "Name");
        ActivityType type = Activity.isValidStreamingUrl(url) ? ActivityType.STREAMING : ActivityType.PLAYING;
        return EntityBuilder.createActivity(name, url, type);
    }

    @Nonnull
    public static Activity listening(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 128, "Name");
        return EntityBuilder.createActivity(name, null, ActivityType.LISTENING);
    }

    @Nonnull
    public static Activity watching(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 128, "Name");
        return EntityBuilder.createActivity(name, null, ActivityType.WATCHING);
    }

    @Nonnull
    public static Activity competing(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 128, "Name");
        return EntityBuilder.createActivity(name, null, ActivityType.COMPETING);
    }

    @Nonnull
    public static Activity customStatus(@Nonnull String name) {
        Checks.notBlank(name, "Name");
        name = name.trim();
        Checks.notLonger(name, 128, "Name");
        return EntityBuilder.createActivity(name, null, ActivityType.CUSTOM_STATUS);
    }

    @Nonnull
    public static Activity of(@Nonnull ActivityType type, @Nonnull String name) {
        return Activity.of(type, name, null);
    }

    @Nonnull
    public static Activity of(@Nonnull ActivityType type, @Nonnull String name, @Nullable String url) {
        Checks.notNull((Object)type, "Type");
        switch (type) {
            case PLAYING: {
                return Activity.playing(name);
            }
            case STREAMING: {
                return Activity.streaming(name, url);
            }
            case LISTENING: {
                return Activity.listening(name);
            }
            case WATCHING: {
                return Activity.watching(name);
            }
            case COMPETING: {
                return Activity.competing(name);
            }
            case CUSTOM_STATUS: {
                return Activity.customStatus(name);
            }
        }
        throw new IllegalArgumentException("ActivityType " + (Object)((Object)type) + " is not supported!");
    }

    public static boolean isValidStreamingUrl(@Nullable String url) {
        return url != null && STREAMING_URL.matcher(url).matches();
    }

    public static enum ActivityType {
        PLAYING(0),
        STREAMING(1),
        LISTENING(2),
        WATCHING(3),
        CUSTOM_STATUS(4),
        COMPETING(5);

        private final int key;

        private ActivityType(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static ActivityType fromKey(int key) {
            switch (key) {
                default: {
                    return PLAYING;
                }
                case 1: {
                    return STREAMING;
                }
                case 2: {
                    return LISTENING;
                }
                case 3: {
                    return WATCHING;
                }
                case 4: {
                    return CUSTOM_STATUS;
                }
                case 5: 
            }
            return COMPETING;
        }
    }

    public static class Timestamps {
        protected final long start;
        protected final long end;

        public Timestamps(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long getStart() {
            return this.start;
        }

        @Nullable
        public Instant getStartTime() {
            return this.start <= 0L ? null : Instant.ofEpochMilli(this.start);
        }

        public long getEnd() {
            return this.end;
        }

        @Nullable
        public Instant getEndTime() {
            return this.end <= 0L ? null : Instant.ofEpochMilli(this.end);
        }

        public long getRemainingTime(TemporalUnit unit) {
            Checks.notNull(unit, "TemporalUnit");
            Instant end = this.getEndTime();
            return end != null ? Instant.now().until(end, unit) : -1L;
        }

        public long getElapsedTime(TemporalUnit unit) {
            Checks.notNull(unit, "TemporalUnit");
            Instant start = this.getStartTime();
            return start != null ? start.until(Instant.now(), unit) : -1L;
        }

        public String toString() {
            return new EntityString("RichPresenceTimestamp").addMetadata("start", this.start).addMetadata("end", this.end).toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Timestamps)) {
                return false;
            }
            Timestamps t = (Timestamps)obj;
            return this.start == t.start && this.end == t.end;
        }

        public int hashCode() {
            return Objects.hash(this.start, this.end);
        }
    }
}

