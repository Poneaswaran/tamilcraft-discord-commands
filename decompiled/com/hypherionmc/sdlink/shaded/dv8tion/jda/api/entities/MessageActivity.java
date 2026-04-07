/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class MessageActivity {
    private final ActivityType type;
    private final String partyId;
    private final Application application;

    public MessageActivity(ActivityType type, String partyId, Application application) {
        this.type = type;
        this.partyId = partyId;
        this.application = application;
    }

    @Nonnull
    public ActivityType getType() {
        return this.type;
    }

    @Nullable
    public String getPartyId() {
        return this.partyId;
    }

    @Nullable
    public Application getApplication() {
        return this.application;
    }

    public static enum ActivityType {
        JOIN(1),
        SPECTATE(2),
        LISTENING(3),
        JOIN_REQUEST(5),
        UNKNOWN(-1);

        private final int id;

        private ActivityType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        @Nonnull
        public static ActivityType fromId(int id) {
            for (ActivityType activityType : ActivityType.values()) {
                if (activityType.id != id) continue;
                return activityType;
            }
            return UNKNOWN;
        }
    }

    public static class Application
    implements ISnowflake {
        private final String name;
        private final String description;
        private final String iconId;
        private final String coverId;
        private final long id;

        public Application(String name, String description, String iconId, String coverId, long id) {
            this.name = name;
            this.description = description;
            this.iconId = iconId;
            this.coverId = coverId;
            this.id = id;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        @Nonnull
        public String getDescription() {
            return this.description;
        }

        @Nullable
        public String getIconId() {
            return this.iconId;
        }

        @Nullable
        public String getIconUrl() {
            return this.iconId == null ? null : "https://cdn.discordapp.com/application/" + this.getId() + "/" + this.iconId + ".png";
        }

        @Nullable
        public ImageProxy getIcon() {
            String iconUrl = this.getIconUrl();
            return iconUrl == null ? null : new ImageProxy(iconUrl);
        }

        @Nullable
        public String getCoverId() {
            return this.coverId;
        }

        @Nullable
        public String getCoverUrl() {
            return this.coverId == null ? null : "https://cdn.discordapp.com/application/" + this.getId() + "/" + this.coverId + ".png";
        }

        @Nullable
        public ImageProxy getCover() {
            String coverUrl = this.getCoverUrl();
            return coverUrl == null ? null : new ImageProxy(coverUrl);
        }

        @Override
        public long getIdLong() {
            return this.id;
        }
    }
}

