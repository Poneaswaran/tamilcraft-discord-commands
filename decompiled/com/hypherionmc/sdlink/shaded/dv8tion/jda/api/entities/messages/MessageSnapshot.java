/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerItem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import org.jetbrains.annotations.Unmodifiable;

public class MessageSnapshot {
    private final Object mutex = new Object();
    private final MessageType type;
    private final Mentions mentions;
    private final OffsetDateTime editTime;
    private final String content;
    private final List<Message.Attachment> attachments;
    private final List<MessageEmbed> embeds;
    private final List<LayoutComponent> components;
    private final List<StickerItem> stickers;
    private final long flags;
    private List<String> invites;

    public MessageSnapshot(MessageType type, Mentions mentions, OffsetDateTime editTime, String content, List<Message.Attachment> attachments, List<MessageEmbed> embeds, List<LayoutComponent> components, List<StickerItem> stickers, long flags) {
        this.type = type;
        this.mentions = mentions;
        this.editTime = editTime;
        this.content = content;
        this.attachments = Collections.unmodifiableList(attachments);
        this.embeds = Collections.unmodifiableList(embeds);
        this.components = Collections.unmodifiableList(components);
        this.stickers = Collections.unmodifiableList(stickers);
        this.flags = flags;
    }

    @Nonnull
    public MessageType getType() {
        return this.type;
    }

    @Nonnull
    public Mentions getMentions() {
        return this.mentions;
    }

    public boolean isEdited() {
        return this.editTime != null;
    }

    @Nullable
    public OffsetDateTime getTimeEdited() {
        return this.editTime;
    }

    @Nonnull
    public String getContentRaw() {
        return this.content;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nonnull
    public @Unmodifiable List<String> getInvites() {
        if (this.invites != null) {
            return this.invites;
        }
        Object object = this.mutex;
        synchronized (object) {
            if (this.invites != null) {
                return this.invites;
            }
            this.invites = new ArrayList<String>();
            Matcher m = Message.INVITE_PATTERN.matcher(this.getContentRaw());
            while (m.find()) {
                this.invites.add(m.group(1));
            }
            this.invites = Collections.unmodifiableList(this.invites);
            return this.invites;
        }
    }

    @Nonnull
    public @Unmodifiable List<Message.Attachment> getAttachments() {
        return this.attachments;
    }

    @Nonnull
    public @Unmodifiable List<MessageEmbed> getEmbeds() {
        return this.embeds;
    }

    @Nonnull
    public @Unmodifiable List<LayoutComponent> getComponents() {
        return this.components;
    }

    @Nonnull
    public @Unmodifiable List<StickerItem> getStickers() {
        return this.stickers;
    }

    public long getFlagsRaw() {
        return this.flags;
    }

    @Nonnull
    public EnumSet<Message.MessageFlag> getFlags() {
        return Message.MessageFlag.fromBitField((int)this.getFlagsRaw());
    }
}

