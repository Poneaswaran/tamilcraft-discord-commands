/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.AbstractMessageBuilderMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;

public interface MessageEditBuilderMixin<R extends MessageEditRequest<R>>
extends AbstractMessageBuilderMixin<R, MessageEditBuilder>,
MessageEditRequest<R> {
    @Override
    @Nonnull
    default public R setAttachments(@Nullable Collection<? extends AttachedFile> attachments) {
        ((MessageEditBuilder)this.getBuilder()).setAttachments((Collection)attachments);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R setReplace(boolean isReplace) {
        ((MessageEditBuilder)this.getBuilder()).setReplace(isReplace);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R setFiles(@Nullable Collection<? extends FileUpload> files) {
        ((MessageEditBuilder)this.getBuilder()).setFiles(files);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R applyData(@Nonnull MessageEditData data) {
        ((MessageEditBuilder)this.getBuilder()).applyData(data);
        return (R)this;
    }

    @Override
    default public boolean isReplace() {
        return ((MessageEditBuilder)this.getBuilder()).isReplace();
    }
}

