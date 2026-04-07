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
import com.hypherionmc.sdlink.core.config.AppliesTo;
import java.util.ArrayList;
import java.util.List;

public final class MessageIgnoreConfig {
    @Path(value="enabled")
    @SpecComment(value="Enable the filter system")
    public boolean enabled = true;
    @Path(value="entries")
    @SpecComment(value="List of entries to process")
    public List<Ignore> entries = new ArrayList<Ignore>();
    @Path(value="ignoredThreads")
    @SpecComment(value="Ignore messages sent from certain threads. Enable debug logging to see what thread the message is from")
    public List<String> ignoredThread = new ArrayList<String>();

    public static class Ignore {
        @Path(value="target")
        @SpecComment(value="Should this filter target CHAT, USERNAME, BOTH, or CONSOLE.")
        public FilterTarget target = FilterTarget.CHAT;
        @Path(value="ignoreConsole")
        @SpecComment(value="Ignore this filter in Console Messages")
        public boolean ignoreConsole = false;
        @Path(value="appliesTo")
        @SpecComment(value="What way of relay does this filter apply to. DISCORD or MINECRAFT")
        public AppliesTo appliesTo = AppliesTo.DISCORD;
        @Path(value="search")
        @SpecComment(value="The text to search for in the message")
        public String search;
        @Path(value="replace")
        @SpecComment(value="Text to replace `search` with, if it's found. Leave empty to ignore")
        public String replace;
        @Path(value="searchMode")
        @SpecComment(value="How should `search` be found in the text. Valid entries are STARTS_WITH, MATCHES and CONTAINS, REGEX")
        public FilterMode searchMode = FilterMode.CONTAINS;
        @Path(value="action")
        @SpecComment(value="How should `replace` be treated, when `search` is found using `searchMode`")
        public ActionMode action = ActionMode.REPLACE;
    }

    public static enum FilterTarget {
        CHAT,
        USERNAME,
        BOTH,
        CONSOLE;


        public boolean isChat() {
            return this == CHAT || this == BOTH;
        }

        public boolean isUsername() {
            return this == USERNAME || this == BOTH;
        }

        public boolean isConsole() {
            return this == CONSOLE || this == BOTH;
        }
    }

    public static enum ActionMode {
        REPLACE,
        IGNORE;

    }

    public static enum FilterMode {
        STARTS_WITH,
        MATCHES,
        CONTAINS,
        REGEX;

    }
}

