/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.menu;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.EmbedBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.menu.Menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.slf4j.LoggerFactory;

public class ButtonEmbedPaginator
extends Menu {
    private static final List<String> paginators = new ArrayList<String>();
    private final BiFunction<Integer, Integer, String> text;
    private final Consumer<Message> finalAction;
    private final boolean waitOnSinglePage;
    private final List<MessageEmbed> embeds;
    private final int bulkSkipNumber;
    private final boolean wrapPageEnds;
    private final ButtonStyle style;
    public static final Emoji BIG_LEFT = Emoji.fromUnicode("\u23ea");
    public static final Emoji LEFT = Emoji.fromUnicode("\u25c0");
    public static final Emoji STOP = Emoji.fromUnicode("\u23f9");
    public static final Emoji RIGHT = Emoji.fromUnicode("\u25b6");
    public static final Emoji BIG_RIGHT = Emoji.fromUnicode("\u23e9");

    protected ButtonEmbedPaginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout2, TimeUnit unit, BiFunction<Integer, Integer, String> text, Consumer<Message> finalAction, boolean waitOnSinglePage, List<MessageEmbed> embeds, int bulkSkipNumber, boolean wrapPageEnds, ButtonStyle style) {
        super(waiter, users, roles, timeout2, unit);
        this.text = text;
        this.finalAction = finalAction;
        this.waitOnSinglePage = waitOnSinglePage;
        this.embeds = embeds;
        this.bulkSkipNumber = bulkSkipNumber;
        this.wrapPageEnds = wrapPageEnds;
        this.style = style;
    }

    @Override
    public void display(MessageChannel channel) {
        this.paginate(channel, 1);
    }

    @Override
    public void display(Message message) {
        this.paginate(message, 1);
    }

    public void display(InteractionHook hook) {
        this.paginate(hook, 1);
    }

    public void paginate(MessageChannel channel, int pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > this.embeds.size()) {
            pageNum = this.embeds.size();
        }
        MessageCreateData msg = MessageCreateData.fromEditData(this.renderPage(pageNum));
        this.initialize(channel.sendMessage(msg), pageNum);
    }

    public void paginate(Message message, int pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > this.embeds.size()) {
            pageNum = this.embeds.size();
        }
        MessageEditData msg = this.renderPage(pageNum);
        this.initialize(message.editMessage(msg), pageNum);
    }

    public void paginate(InteractionHook hook, int pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > this.embeds.size()) {
            pageNum = this.embeds.size();
        }
        MessageEditData msg = this.renderPage(pageNum);
        this.initialize(hook.editOriginal(msg), pageNum);
    }

    private void initialize(RestAction<Message> action, int pageNum) {
        action.queue(m -> {
            if (this.embeds.size() > 1) {
                List<Button> actions = this.buildButtons();
                ((MessageEditAction)m.editMessage(this.renderPage(pageNum)).setActionRow(actions)).queue(v -> this.pagination((Message)m, pageNum));
            } else if (this.waitOnSinglePage) {
                String id = System.currentTimeMillis() + ":STOP";
                ((MessageEditAction)m.editMessage(this.renderPage(pageNum)).setActionRow(Button.of(this.style, id, STOP))).queue(v -> this.pagination((Message)m, pageNum));
            } else {
                this.finalAction.accept((Message)m);
            }
        });
    }

    private List<Button> buildButtons() {
        String id = "bep:" + System.currentTimeMillis();
        ArrayList<Button> actions = new ArrayList<Button>();
        actions.add(Button.of(this.style, id + ":LEFT", LEFT));
        actions.add(Button.of(this.style, id + ":STOP", STOP));
        actions.add(Button.of(this.style, id + ":RIGHT", RIGHT));
        if (this.bulkSkipNumber > 1) {
            actions.add(0, Button.primary(id + ":BIG_LEFT", BIG_LEFT));
            actions.add(Button.of(this.style, id + ":BIG_RIGHT", BIG_RIGHT));
        }
        return actions;
    }

    private void pagination(Message message, int pageNum) {
        paginators.add(message.getId());
        this.waiter.waitForEvent(ButtonInteractionEvent.class, event -> this.checkButton((ButtonInteractionEvent)event, message.getIdLong()), event -> this.handleButtonInteraction((ButtonInteractionEvent)event, message, pageNum), this.timeout, this.unit, () -> this.finalAction.accept(message));
    }

    private boolean checkButton(ButtonInteractionEvent event, long messageId) {
        if (event.getComponentId().startsWith("bep:") && !paginators.contains(event.getMessageId())) {
            event.reply("This paginator is no longer active. The buttons will be removed. Please make a new one!").setEphemeral(true).queue();
            event.getMessage().editMessageComponents(new LayoutComponent[0]).queue();
            return false;
        }
        if (event.getMessageIdLong() != messageId) {
            return false;
        }
        if (Arrays.asList(STOP, LEFT, RIGHT).contains(event.getButton().getEmoji())) {
            return this.isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);
        }
        if (Arrays.asList(BIG_LEFT, BIG_RIGHT).contains(event.getButton().getEmoji())) {
            return this.bulkSkipNumber > 1 && this.isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);
        }
        return false;
    }

    private void handleButtonInteraction(ButtonInteractionEvent event, Message message, int pageNum) {
        int i;
        int newPageNum = pageNum;
        int pages = this.embeds.size();
        EmojiUnion emoji = event.getButton().getEmoji();
        if (emoji == null) {
            LoggerFactory.getLogger(this.getClass()).warn("Received null emoji in ButtonInteractionEvent!");
            return;
        }
        if (emoji.equals(LEFT)) {
            if (newPageNum == 1 && this.wrapPageEnds) {
                newPageNum = pages + 1;
            }
            if (newPageNum > 1) {
                --newPageNum;
            }
        } else if (emoji.equals(RIGHT)) {
            if (newPageNum == pages && this.wrapPageEnds) {
                newPageNum = 0;
            }
            if (newPageNum < pages) {
                ++newPageNum;
            }
        } else if (emoji.equals(BIG_LEFT)) {
            if (newPageNum > 1 || this.wrapPageEnds) {
                for (i = 1; (newPageNum > 1 || this.wrapPageEnds) && i < this.bulkSkipNumber; --newPageNum, ++i) {
                    if (newPageNum != 1) continue;
                    newPageNum = pages + 1;
                }
            }
        } else if (emoji.equals(BIG_RIGHT)) {
            if (newPageNum < pages || this.wrapPageEnds) {
                for (i = 1; (newPageNum < pages || this.wrapPageEnds) && i < this.bulkSkipNumber; ++newPageNum, ++i) {
                    if (newPageNum != pages) continue;
                    newPageNum = 0;
                }
            }
        } else if (emoji.equals(STOP)) {
            event.deferEdit().queue(interactionHook -> this.finalAction.accept(message));
            return;
        }
        int n = newPageNum;
        event.deferEdit().queue(interactionHook -> ((MessageEditAction)message.editMessage(this.renderPage(n)).setActionRow(this.buildButtons())).queue(m -> this.pagination((Message)m, n)));
    }

    private MessageEditData renderPage(int pageNum) {
        MessageEditBuilder mbuilder = new MessageEditBuilder();
        MessageEmbed membed = this.embeds.get(pageNum - 1);
        mbuilder.setEmbeds(membed);
        if (this.text != null) {
            mbuilder.setContent(this.text.apply(pageNum, this.embeds.size()));
        }
        return mbuilder.build();
    }

    public static class Builder
    extends Menu.Builder<Builder, ButtonEmbedPaginator> {
        private BiFunction<Integer, Integer, String> text = (page, pages) -> null;
        private Consumer<Message> finalAction = m -> m.delete().queue();
        private boolean waitOnSinglePage = false;
        private int bulkSkipNumber = 1;
        private boolean wrapPageEnds = false;
        private ButtonStyle style = ButtonStyle.SECONDARY;
        private final List<MessageEmbed> embeds = new LinkedList<MessageEmbed>();

        @Override
        public ButtonEmbedPaginator build() {
            Checks.check(this.waiter != null, "Must set an EventWaiter");
            Checks.check(!this.embeds.isEmpty(), "Must include at least one item to paginate");
            return new ButtonEmbedPaginator(this.waiter, this.users, this.roles, this.timeout, this.unit, this.text, this.finalAction, this.waitOnSinglePage, this.embeds, this.bulkSkipNumber, this.wrapPageEnds, this.style);
        }

        public Builder setText(String text) {
            this.text = (i0, i1) -> text;
            return this;
        }

        public Builder setText(BiFunction<Integer, Integer, String> textBiFunction) {
            this.text = textBiFunction;
            return this;
        }

        public Builder setFinalAction(Consumer<Message> finalAction) {
            this.finalAction = finalAction;
            return this;
        }

        public Builder waitOnSinglePage(boolean waitOnSinglePage) {
            this.waitOnSinglePage = waitOnSinglePage;
            return this;
        }

        public Builder clearItems() {
            this.embeds.clear();
            return this;
        }

        public Builder addItems(MessageEmbed ... embeds) {
            this.embeds.addAll(Arrays.asList(embeds));
            return this;
        }

        public Builder addItems(Collection<MessageEmbed> embeds) {
            this.embeds.addAll(embeds);
            return this;
        }

        public Builder addItems(String ... items) {
            for (String item : items) {
                Checks.check(item.length() <= 2048, "Text may not be longer than 2048 characters.");
                this.embeds.add(new EmbedBuilder().setDescription(item).build());
            }
            return this;
        }

        public Builder setItems(MessageEmbed ... embeds) {
            this.embeds.clear();
            this.embeds.addAll(Arrays.asList(embeds));
            return this;
        }

        public Builder setItems(Collection<MessageEmbed> embeds) {
            this.embeds.clear();
            this.addItems(embeds);
            return this;
        }

        public Builder setItems(String ... items) {
            this.embeds.clear();
            this.addItems(items);
            return this;
        }

        public Builder setBulkSkipNumber(int bulkSkipNumber) {
            this.bulkSkipNumber = Math.max(bulkSkipNumber, 1);
            return this;
        }

        public Builder wrapPageEnds(boolean wrapPageEnds) {
            this.wrapPageEnds = wrapPageEnds;
            return this;
        }

        public Builder setButtonStyle(ButtonStyle style) {
            this.style = style;
            return this;
        }
    }
}

