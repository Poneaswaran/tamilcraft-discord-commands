/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IModalCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IPremiumRequiredReplyCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;

public interface CommandInteraction
extends IReplyCallback,
CommandInteractionPayload,
IModalCallback,
IPremiumRequiredReplyCallback {
}

