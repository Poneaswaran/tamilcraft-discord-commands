/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageActivity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.messages.MessagePoll;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.messages.MessageSnapshot;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerItem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PollVotersPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachmentProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.MessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.MessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PollVotersPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.MultipartBody;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Formattable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Unmodifiable;

public interface Message
extends ISnowflake,
Formattable {
    public static final String JUMP_URL = "https://discord.com/channels/%s/%s/%s";
    public static final int MAX_FILE_SIZE = 0x1900000;
    public static final int MAX_FILE_AMOUNT = 10;
    public static final int MAX_CONTENT_LENGTH = 2000;
    public static final int MAX_REACTIONS = 20;
    public static final int MAX_EMBED_COUNT = 10;
    public static final int MAX_STICKER_COUNT = 3;
    public static final int MAX_COMPONENT_COUNT = 5;
    public static final int MAX_NONCE_LENGTH = 25;
    public static final Pattern INVITE_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?discord(?:(?:app)?\\.com/invite|\\.gg)/(?<code>[a-z0-9-]+)(?:\\?\\S*)?(?:#\\S*)?", 2);
    public static final Pattern JUMP_URL_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?discord(?:app)?\\.com/channels/(?<guild>\\d+)/(?<channel>\\d+)/(?<message>\\d+)(?:\\?\\S*)?(?:#\\S*)?", 2);

    public static void suppressContentIntentWarning() {
        ReceivedMessage.didContentIntentWarning = true;
    }

    @Nullable
    public MessageReference getMessageReference();

    @Nullable
    default public Message getReferencedMessage() {
        return this.getMessageReference() != null ? this.getMessageReference().getMessage() : null;
    }

    @Nonnull
    public Mentions getMentions();

    public boolean isEdited();

    @Nullable
    public OffsetDateTime getTimeEdited();

    @Nonnull
    public User getAuthor();

    @Nullable
    public Member getMember();

    public int getApproximatePosition();

    @Nonnull
    public String getJumpUrl();

    @Nonnull
    public String getContentDisplay();

    @Nonnull
    public String getContentRaw();

    @Nonnull
    public String getContentStripped();

    @Nonnull
    public @Unmodifiable List<String> getInvites();

    @Nullable
    public String getNonce();

    public boolean isFromType(@Nonnull ChannelType var1);

    public boolean isFromGuild();

    @Nonnull
    public ChannelType getChannelType();

    public boolean isWebhookMessage();

    @Nullable
    default public String getApplicationId() {
        return this.getApplicationIdLong() == 0L ? null : Long.toUnsignedString(this.getApplicationIdLong());
    }

    public long getApplicationIdLong();

    public boolean hasChannel();

    public long getChannelIdLong();

    @Nonnull
    default public String getChannelId() {
        return Long.toUnsignedString(this.getChannelIdLong());
    }

    @Nonnull
    public MessageChannelUnion getChannel();

    @Nonnull
    public GuildMessageChannelUnion getGuildChannel();

    @Nullable
    public Category getCategory();

    public boolean hasGuild();

    public long getGuildIdLong();

    @Nullable
    default public String getGuildId() {
        return this.isFromGuild() ? Long.toUnsignedString(this.getGuildIdLong()) : null;
    }

    @Nonnull
    public Guild getGuild();

    @Nonnull
    public @Unmodifiable List<Attachment> getAttachments();

    @Nonnull
    public @Unmodifiable List<MessageEmbed> getEmbeds();

    @Nonnull
    public @Unmodifiable List<LayoutComponent> getComponents();

    @Nullable
    public MessagePoll getPoll();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Message> endPoll();

    @Nonnull
    @CheckReturnValue
    default public PollVotersPaginationAction retrievePollVoters(long answerId) {
        return new PollVotersPaginationActionImpl(this.getJDA(), this.getChannelId(), this.getId(), answerId);
    }

    @Nonnull
    default public @Unmodifiable List<ActionRow> getActionRows() {
        return this.getComponents().stream().filter(ActionRow.class::isInstance).map(ActionRow.class::cast).collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    default public @Unmodifiable List<Button> getButtons() {
        return this.getComponents().stream().map(LayoutComponent::getButtons).flatMap(Collection::stream).collect(Helpers.toUnmodifiableList());
    }

    @Nullable
    default public Button getButtonById(@Nonnull String id) {
        Checks.notNull(id, "Button ID");
        return this.getButtons().stream().filter(it -> id.equals(it.getId())).findFirst().orElse(null);
    }

    @Nonnull
    default public @Unmodifiable List<Button> getButtonsByLabel(@Nonnull String label, boolean ignoreCase) {
        Checks.notNull(label, "Label");
        Predicate<Button> filter = ignoreCase ? b -> label.equalsIgnoreCase(b.getLabel()) : b -> label.equals(b.getLabel());
        return this.getButtons().stream().filter(filter).collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    public @Unmodifiable List<MessageReaction> getReactions();

    @Nonnull
    public @Unmodifiable List<StickerItem> getStickers();

    @Nonnull
    public @Unmodifiable List<MessageSnapshot> getMessageSnapshots();

    public boolean isTTS();

    @Nullable
    public MessageActivity getActivity();

    @Nonnull
    @CheckReturnValue
    public MessageEditAction editMessage(@Nonnull CharSequence var1);

    @Nonnull
    @CheckReturnValue
    public MessageEditAction editMessage(@Nonnull MessageEditData var1);

    @Nonnull
    @CheckReturnValue
    public MessageEditAction editMessageEmbeds(@Nonnull Collection<? extends MessageEmbed> var1);

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageEmbeds(MessageEmbed ... embeds) {
        Checks.noneNull(embeds, "MessageEmbeds");
        return this.editMessageEmbeds(Arrays.asList(embeds));
    }

    @Nonnull
    @CheckReturnValue
    public MessageEditAction editMessageComponents(@Nonnull Collection<? extends LayoutComponent> var1);

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageComponents(LayoutComponent ... components) {
        Checks.noneNull(components, "Components");
        return this.editMessageComponents(Arrays.asList(components));
    }

    @Nonnull
    @CheckReturnValue
    public MessageEditAction editMessageFormat(@Nonnull String var1, Object ... var2);

    @Nonnull
    @CheckReturnValue
    public MessageEditAction editMessageAttachments(@Nonnull Collection<? extends AttachedFile> var1);

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageAttachments(AttachedFile ... attachments) {
        Checks.noneNull(attachments, "Attachments");
        return this.editMessageAttachments(Arrays.asList(attachments));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyStickers(@Nonnull Collection<? extends StickerSnowflake> stickers) {
        return this.getGuildChannel().sendStickers(stickers).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyStickers(StickerSnowflake ... stickers) {
        return this.getGuildChannel().sendStickers(stickers).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction reply(@Nonnull CharSequence content) {
        return this.getChannel().sendMessage(content).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction reply(@Nonnull MessageCreateData msg) {
        return this.getChannel().sendMessage(msg).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyPoll(@Nonnull MessagePollData poll) {
        return this.getChannel().sendMessagePoll(poll).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyEmbeds(@Nonnull MessageEmbed embed, MessageEmbed ... other) {
        Checks.notNull(embed, "MessageEmbeds");
        Checks.noneNull(other, "MessageEmbeds");
        ArrayList<MessageEmbed> embeds = new ArrayList<MessageEmbed>(1 + other.length);
        embeds.add(embed);
        Collections.addAll(embeds, other);
        return this.replyEmbeds(embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        return this.getChannel().sendMessageEmbeds(embeds).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyComponents(@Nonnull LayoutComponent component, LayoutComponent ... other) {
        Checks.notNull(component, "LayoutComponents");
        Checks.noneNull(other, "LayoutComponents");
        ArrayList<LayoutComponent> components = new ArrayList<LayoutComponent>(1 + other.length);
        components.add(component);
        Collections.addAll(components, other);
        return this.replyComponents(components);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        return this.getChannel().sendMessageComponents(components).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyFormat(@Nonnull String format, Object ... args2) {
        return this.getChannel().sendMessageFormat(format, args2).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyFiles(FileUpload ... files) {
        return this.getChannel().sendFiles(files).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction replyFiles(@Nonnull Collection<? extends FileUpload> files) {
        return this.getChannel().sendFiles(files).setMessageReference(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction forwardTo(@Nonnull MessageChannel channel) {
        Checks.notNull(channel, "Target channel");
        if (channel instanceof MessageChannelMixin) {
            ((MessageChannelMixin)channel).checkCanSendMessage();
        }
        return new MessageCreateActionImpl(channel).setMessageReference(MessageReference.MessageReferenceType.FORWARD, this);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    public JDA getJDA();

    public boolean isPinned();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> pin();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> unpin();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> addReaction(@Nonnull Emoji var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> clearReactions();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> clearReactions(@Nonnull Emoji var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> removeReaction(@Nonnull Emoji var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> removeReaction(@Nonnull Emoji var1, @Nonnull User var2);

    @Nonnull
    @CheckReturnValue
    public ReactionPaginationAction retrieveReactionUsers(@Nonnull Emoji var1);

    @Nullable
    @CheckReturnValue
    public MessageReaction getReaction(@Nonnull Emoji var1);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> suppressEmbeds(boolean var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> crosspost();

    public boolean isSuppressedEmbeds();

    @Nonnull
    public EnumSet<MessageFlag> getFlags();

    public long getFlagsRaw();

    public boolean isEphemeral();

    public boolean isSuppressedNotifications();

    public boolean isVoiceMessage();

    @Nullable
    public ThreadChannel getStartedThread();

    @Nonnull
    public MessageType getType();

    @Nullable
    public Interaction getInteraction();

    @Nonnull
    @CheckReturnValue
    public ThreadChannelAction createThreadChannel(@Nonnull String var1);

    public static class Interaction
    implements ISnowflake {
        private final long id;
        private final int type;
        private final String name;
        private final User user;
        private final Member member;

        public Interaction(long id, int type, String name, User user, Member member) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.user = user;
            this.member = member;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        public int getTypeRaw() {
            return this.type;
        }

        @Nonnull
        public InteractionType getType() {
            return InteractionType.fromKey(this.getTypeRaw());
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        @Nonnull
        public User getUser() {
            return this.user;
        }

        @Nullable
        public Member getMember() {
            return this.member;
        }
    }

    public static class Attachment
    implements ISnowflake,
    AttachedFile {
        private static final Set<String> IMAGE_EXTENSIONS = new HashSet<String>(Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "tiff", "svg", "apng"));
        private static final Set<String> VIDEO_EXTENSIONS = new HashSet<String>(Arrays.asList("webm", "flv", "vob", "avi", "mov", "wmv", "amv", "mp4", "mpg", "mpeg", "gifv"));
        private final long id;
        private final String url;
        private final String proxyUrl;
        private final String fileName;
        private final String contentType;
        private final String description;
        private final int size;
        private final int height;
        private final int width;
        private final boolean ephemeral;
        private final String waveform;
        private final double duration;
        private final JDAImpl jda;

        public Attachment(long id, String url, String proxyUrl, String fileName, String contentType, String description, int size, int height, int width, boolean ephemeral, String waveform, double duration, JDAImpl jda) {
            this.id = id;
            this.url = url;
            this.proxyUrl = proxyUrl;
            this.fileName = fileName;
            this.contentType = contentType;
            this.description = description;
            this.size = size;
            this.height = height;
            this.width = width;
            this.ephemeral = ephemeral;
            this.waveform = waveform;
            this.duration = duration;
            this.jda = jda;
        }

        @Nonnull
        public JDA getJDA() {
            return this.jda;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Nonnull
        public String getUrl() {
            return this.url;
        }

        @Nonnull
        public String getProxyUrl() {
            return this.proxyUrl;
        }

        @Nonnull
        public AttachmentProxy getProxy() {
            return new AttachmentProxy(this.width > 0 && this.height > 0 ? this.proxyUrl : this.url);
        }

        @Nonnull
        public String getFileName() {
            return this.fileName;
        }

        @Nullable
        public String getFileExtension() {
            int index = this.fileName.lastIndexOf(46) + 1;
            return index == 0 || index == this.fileName.length() ? null : this.fileName.substring(index);
        }

        @Nullable
        public String getContentType() {
            return this.contentType;
        }

        @Nullable
        public String getDescription() {
            return this.description;
        }

        public int getSize() {
            return this.size;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        public boolean isEphemeral() {
            return this.ephemeral;
        }

        @Nullable
        public byte[] getWaveform() {
            if (this.waveform == null) {
                return null;
            }
            return Base64.getDecoder().decode(this.waveform);
        }

        public double getDuration() {
            return this.duration;
        }

        public boolean isImage() {
            if (this.width < 0) {
                return false;
            }
            String extension = this.getFileExtension();
            return extension != null && IMAGE_EXTENSIONS.contains(extension.toLowerCase());
        }

        public boolean isVideo() {
            if (this.width < 0) {
                return false;
            }
            String extension = this.getFileExtension();
            return extension != null && VIDEO_EXTENSIONS.contains(extension.toLowerCase());
        }

        public boolean isSpoiler() {
            return this.getFileName().startsWith("SPOILER_");
        }

        @Override
        public void close() {
        }

        @Override
        public void forceClose() {
        }

        @Override
        public void addPart(@Nonnull MultipartBody.Builder builder, int index) {
        }

        @Override
        @Nonnull
        public DataObject toAttachmentData(int index) {
            return DataObject.empty().put("id", this.id);
        }
    }

    public static enum MessageFlag {
        CROSSPOSTED(0),
        IS_CROSSPOST(1),
        EMBEDS_SUPPRESSED(2),
        SOURCE_MESSAGE_DELETED(3),
        URGENT(4),
        EPHEMERAL(6),
        LOADING(7),
        NOTIFICATIONS_SUPPRESSED(12),
        IS_VOICE_MESSAGE(13);

        private final int value;

        private MessageFlag(int offset) {
            this.value = 1 << offset;
        }

        public int getValue() {
            return this.value;
        }

        @Nonnull
        public static EnumSet<MessageFlag> fromBitField(int bitfield) {
            Set set = Arrays.stream(MessageFlag.values()).filter(e -> (e.value & bitfield) > 0).collect(Collectors.toSet());
            return set.isEmpty() ? EnumSet.noneOf(MessageFlag.class) : EnumSet.copyOf(set);
        }

        public static int toBitField(@Nonnull Collection<MessageFlag> coll) {
            Checks.notNull(coll, "Collection");
            int flags = 0;
            for (MessageFlag messageFlag : coll) {
                flags |= messageFlag.value;
            }
            return flags;
        }
    }

    public static enum MentionType {
        USER("<@!?(\\d+)>", "users"),
        ROLE("<@&(\\d+)>", "roles"),
        CHANNEL("<#(\\d+)>", null),
        EMOJI("<a?:([a-zA-Z0-9_]+):([0-9]+)>", null),
        HERE("@here", "everyone"),
        EVERYONE("@everyone", "everyone"),
        SLASH_COMMAND("</([\\w-]+)(?> ([\\w-]+))??(?> ([\\w-]+))?:(\\d+)>", null);

        private final Pattern pattern;
        private final String parseKey;

        private MentionType(String regex, String parseKey) {
            this.pattern = Pattern.compile(regex);
            this.parseKey = parseKey;
        }

        @Nonnull
        public Pattern getPattern() {
            return this.pattern;
        }

        @Nullable
        public String getParseKey() {
            return this.parseKey;
        }
    }
}

