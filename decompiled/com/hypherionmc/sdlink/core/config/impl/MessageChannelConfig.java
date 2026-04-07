/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 */
package com.hypherionmc.sdlink.core.config.impl;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import com.hypherionmc.sdlink.api.messaging.MessageDestination;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.util.DestinationHolder;

public final class MessageChannelConfig {
    @Path(value="chat")
    @SpecComment(value="Control where CHAT messages are delivered")
    public DestinationObject chat = DestinationObject.of(MessageDestination.CHAT, false, "default");
    @Path(value="start")
    @SpecComment(value="Control where START messages are delivered")
    public DestinationObject start = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="stop")
    @SpecComment(value="Control where STOP messages are delivered")
    public DestinationObject stop = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="join")
    @SpecComment(value="Control where JOIN messages are delivered")
    public DestinationObject join = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="leave")
    @SpecComment(value="Control where LEAVE messages are delivered")
    public DestinationObject leave = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="advancements")
    @SpecComment(value="Control where ADVANCEMENT messages are delivered")
    public DestinationObject advancements = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="death")
    @SpecComment(value="Control where DEATH messages are delivered")
    public DestinationObject death = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="commands")
    @SpecComment(value="Control where COMMAND messages are delivered")
    public DestinationObject commands = DestinationObject.of(MessageDestination.EVENT, false, "default");
    @Path(value="whitelist")
    @SpecComment(value="Control where WHITELIST change messages are delivered")
    public DestinationObject whitelist = DestinationObject.of(MessageDestination.CONSOLE, false, "default");
    @Path(value="custom")
    @SpecComment(value="Control where messages that match none of the above are delivered")
    public DestinationObject custom = DestinationObject.of(MessageDestination.EVENT, false, "default");

    public static class DestinationObject {
        @Path(value="channel")
        @SpecComment(value="The Channel the message will be delivered to. Valid entries are CHAT, EVENT, CONSOLE, OVERRIDE")
        public MessageDestination channel;
        @Path(value="useEmbed")
        @SpecComment(value="Should the message be sent using EMBED style messages")
        public boolean useEmbed;
        @Path(value="embedLayout")
        @SpecComment(value="Embed Layout to use")
        public String embedLayout;
        @Path(value="override")
        @SpecComment(value="Override the destination with a custom channel/webhook url. Make sure to change `channel` above to OVERRIDE")
        public String override;

        DestinationObject(MessageDestination destination, boolean useEmbed, String embedLayout, String override) {
            this.channel = destination;
            this.useEmbed = useEmbed;
            this.embedLayout = embedLayout;
            this.override = override;
        }

        public static DestinationObject of(MessageDestination destination, boolean useEmbed, String embedLayout) {
            return new DestinationObject(destination, useEmbed, embedLayout, "");
        }

        public DestinationHolder toHolder(MessageType type) {
            return DestinationHolder.of(this, type);
        }
    }
}

