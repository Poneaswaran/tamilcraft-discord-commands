/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Emoji;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiSubGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Qualification;
import java.util.Arrays;
import java.util.Collections;

interface EmojiPhone {
    public static final Emoji MOBILE_PHONE = new Emoji("\ud83d\udcf1", "\\uD83D\\uDCF1", Collections.unmodifiableList(Arrays.asList(":mobile_phone:", ":iphone:")), Collections.singletonList(":iphone:"), Collections.singletonList(":iphone:"), Collections.unmodifiableList(Arrays.asList("cell", "communication", "mobile", "phone", "telephone")), false, false, 0.6, Qualification.fromString("fully-qualified"), "mobile phone", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, false);
    public static final Emoji MOBILE_PHONE_WITH_ARROW = new Emoji("\ud83d\udcf2", "\\uD83D\\uDCF2", Collections.singletonList(":calling:"), Collections.singletonList(":calling:"), Collections.singletonList(":calling:"), Collections.unmodifiableList(Arrays.asList("arrow", "build", "call", "cell", "communication", "mobile", "phone", "receive", "telephone")), false, false, 0.6, Qualification.fromString("fully-qualified"), "mobile phone with arrow", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, false);
    public static final Emoji TELEPHONE = new Emoji("\u260e\ufe0f", "\\u260E\\uFE0F", Collections.singletonList(":telephone:"), Collections.unmodifiableList(Arrays.asList(":phone:", ":telephone:")), Collections.unmodifiableList(Arrays.asList(":phone:", ":telephone:")), Collections.unmodifiableList(Arrays.asList("phone", "telephone")), false, false, 0.6, Qualification.fromString("fully-qualified"), "telephone", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, false);
    public static final Emoji TELEPHONE_UNQUALIFIED = new Emoji("\u260e", "\\u260E", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("phone", "telephone")), false, false, 0.6, Qualification.fromString("unqualified"), "telephone", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, true);
    public static final Emoji TELEPHONE_RECEIVER = new Emoji("\ud83d\udcde", "\\uD83D\\uDCDE", Collections.singletonList(":telephone_receiver:"), Collections.singletonList(":telephone_receiver:"), Collections.singletonList(":telephone_receiver:"), Collections.unmodifiableList(Arrays.asList("communication", "phone", "receiver", "telephone", "voip")), false, false, 0.6, Qualification.fromString("fully-qualified"), "telephone receiver", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, false);
    public static final Emoji PAGER = new Emoji("\ud83d\udcdf", "\\uD83D\\uDCDF", Collections.singletonList(":pager:"), Collections.singletonList(":pager:"), Collections.singletonList(":pager:"), Collections.unmodifiableList(Arrays.asList("communication", "pager")), false, false, 0.6, Qualification.fromString("fully-qualified"), "pager", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, true);
    public static final Emoji FAX_MACHINE = new Emoji("\ud83d\udce0", "\\uD83D\\uDCE0", Collections.unmodifiableList(Arrays.asList(":fax:", ":fax_machine:")), Collections.singletonList(":fax:"), Collections.singletonList(":fax:"), Collections.unmodifiableList(Arrays.asList("communication", "fax", "machine")), false, false, 0.6, Qualification.fromString("fully-qualified"), "fax machine", EmojiGroup.OBJECTS, EmojiSubGroup.PHONE, false);
}

