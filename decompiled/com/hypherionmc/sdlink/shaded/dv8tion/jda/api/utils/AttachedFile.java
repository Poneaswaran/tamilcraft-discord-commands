/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachmentUpdate;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.Requester;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.MultipartBody;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;

public interface AttachedFile
extends Closeable {
    public static final int MAX_DESCRIPTION_LENGTH = 1024;

    @Nonnull
    public static FileUpload fromData(@Nonnull InputStream data, @Nonnull String name) {
        return FileUpload.fromData(data, name);
    }

    @Nonnull
    public static FileUpload fromData(@Nonnull byte[] data, @Nonnull String name) {
        return FileUpload.fromData(data, name);
    }

    @Nonnull
    public static FileUpload fromData(@Nonnull File file, @Nonnull String name) {
        return FileUpload.fromData(file, name);
    }

    @Nonnull
    public static FileUpload fromData(@Nonnull File file) {
        return FileUpload.fromData(file);
    }

    @Nonnull
    public static FileUpload fromData(@Nonnull Path path, @Nonnull String name, OpenOption ... options) {
        return FileUpload.fromData(path, name, options);
    }

    @Nonnull
    public static FileUpload fromData(@Nonnull Path path, OpenOption ... options) {
        return FileUpload.fromData(path, options);
    }

    @Nonnull
    public static AttachmentUpdate fromAttachment(long id) {
        return AttachmentUpdate.fromAttachment(id);
    }

    @Nonnull
    public static AttachmentUpdate fromAttachment(@Nonnull String id) {
        return AttachmentUpdate.fromAttachment(id);
    }

    @Nonnull
    public static AttachmentUpdate fromAttachment(@Nonnull Message.Attachment attachment) {
        return AttachmentUpdate.fromAttachment(attachment);
    }

    public void addPart(@Nonnull MultipartBody.Builder var1, int var2);

    @Nonnull
    public DataObject toAttachmentData(int var1);

    @Nonnull
    public static MultipartBody.Builder createMultipartBody(@Nonnull List<? extends AttachedFile> files) {
        return AttachedFile.createMultipartBody(files, (RequestBody)null);
    }

    @Nonnull
    public static MultipartBody.Builder createMultipartBody(@Nonnull List<? extends AttachedFile> files, @Nullable DataObject payloadJson) {
        RequestBody body = payloadJson != null ? RequestBody.create(payloadJson.toJson(), Requester.MEDIA_TYPE_JSON) : null;
        return AttachedFile.createMultipartBody(files, body);
    }

    @Nonnull
    public static MultipartBody.Builder createMultipartBody(@Nonnull List<? extends AttachedFile> files, @Nullable RequestBody payloadJson) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); ++i) {
            AttachedFile file = files.get(i);
            file.addPart(builder, i);
        }
        if (payloadJson != null) {
            builder.addFormDataPart("payload_json", null, payloadJson);
        }
        return builder;
    }

    public void forceClose() throws IOException;
}

