/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface MessagePoll {
    public static final int MAX_QUESTION_TEXT_LENGTH = 300;
    public static final int MAX_ANSWER_TEXT_LENGTH = 55;
    public static final int MAX_ANSWERS = 10;
    public static final long MAX_DURATION_HOURS = 168L;

    @Nonnull
    public LayoutType getLayout();

    @Nonnull
    public Question getQuestion();

    @Nonnull
    public @Unmodifiable List<Answer> getAnswers();

    @Nullable
    public OffsetDateTime getTimeExpiresAt();

    public boolean isMultiAnswer();

    public boolean isFinalizedVotes();

    default public boolean isExpired() {
        return this.getTimeExpiresAt().isBefore(OffsetDateTime.now());
    }

    public static enum LayoutType {
        DEFAULT(1),
        UNKNOWN(-1);

        private final int key;

        private LayoutType(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        @Nonnull
        public static LayoutType fromKey(int key) {
            for (LayoutType type : LayoutType.values()) {
                if (type.key != key) continue;
                return type;
            }
            return UNKNOWN;
        }
    }

    public static class Answer {
        private final long id;
        private final String text;
        private final EmojiUnion emoji;
        private final int votes;
        private final boolean selfVoted;

        public Answer(long id, String text, EmojiUnion emoji, int votes, boolean selfVoted) {
            this.id = id;
            this.text = text;
            this.emoji = emoji;
            this.votes = votes;
            this.selfVoted = selfVoted;
        }

        public long getId() {
            return this.id;
        }

        @Nonnull
        public String getText() {
            return this.text;
        }

        @Nullable
        public EmojiUnion getEmoji() {
            return this.emoji;
        }

        public int getVotes() {
            return this.votes;
        }

        public boolean isSelfVoted() {
            return this.selfVoted;
        }
    }

    public static class Question {
        private final String text;
        private final EmojiUnion emoji;

        public Question(String text, Emoji emoji) {
            this.text = text;
            this.emoji = (EmojiUnion)emoji;
        }

        @Nonnull
        public String getText() {
            return this.text;
        }

        @Nullable
        public EmojiUnion getEmoji() {
            return this.emoji;
        }
    }
}

