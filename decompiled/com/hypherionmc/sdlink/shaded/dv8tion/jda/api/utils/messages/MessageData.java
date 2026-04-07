/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface MessageData {
    @Nonnull
    public String getContent();

    @Nonnull
    public List<MessageEmbed> getEmbeds();

    @Nonnull
    public List<LayoutComponent> getComponents();

    @Nonnull
    public List<? extends AttachedFile> getAttachments();

    public boolean isSuppressEmbeds();

    @Nonnull
    public Set<String> getMentionedUsers();

    @Nonnull
    public Set<String> getMentionedRoles();

    @Nonnull
    public EnumSet<Message.MentionType> getAllowedMentions();

    public boolean isMentionRepliedUser();
}

