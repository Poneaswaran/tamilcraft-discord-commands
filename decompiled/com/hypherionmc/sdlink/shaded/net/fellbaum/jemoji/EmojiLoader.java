/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jspecify.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiLanguage;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiManager;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Emojis;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.internal.ResourceFilesProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import org.jspecify.annotations.Nullable;

public final class EmojiLoader {
    private static final @Nullable ResourceFilesProvider RESOURCE_FILES_PROVIDER_LANGUAGE_MODULE;

    private EmojiLoader() {
    }

    private static List<ResourceFilesProvider> providers() {
        ArrayList<ResourceFilesProvider> services = new ArrayList<ResourceFilesProvider>();
        ServiceLoader<ResourceFilesProvider> loader = ServiceLoader.load(ResourceFilesProvider.class);
        loader.forEach(services::add);
        return services;
    }

    static Object readFromAllLanguageResourceFiles(String fileName, EmojiLanguage language) {
        if (RESOURCE_FILES_PROVIDER_LANGUAGE_MODULE == null) {
            throw new IllegalStateException("Trying to access a property for language \"" + language.getValue() + "\" but the jemoji-language module is missing. To add multi language support, see here https://github.com/felldo/JEmoji?tab=readme-ov-file#-jemoji-language-module");
        }
        return RESOURCE_FILES_PROVIDER_LANGUAGE_MODULE.readFileAsObject(fileName + language.getValue());
    }

    /*
     * Exception decompiling
     */
    private static String readFileAsString(String filePathName) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 5 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static void loadAllEmojiDescriptions() {
        for (EmojiLanguage value : EmojiLanguage.values()) {
            EmojiManager.getEmojiDescriptionForLanguageAndEmoji(value, Emojis.THUMBS_UP.getEmoji());
        }
    }

    public static void loadAllEmojiKeywords() {
        for (EmojiLanguage value : EmojiLanguage.values()) {
            EmojiManager.getEmojiKeywordsForLanguageAndEmoji(value, Emojis.THUMBS_UP.getEmoji());
        }
    }

    static {
        List<ResourceFilesProvider> providers = EmojiLoader.providers();
        switch (providers.size()) {
            case 1: {
                RESOURCE_FILES_PROVIDER_LANGUAGE_MODULE = providers.get(0);
                break;
            }
            case 0: {
                RESOURCE_FILES_PROVIDER_LANGUAGE_MODULE = null;
                break;
            }
            default: {
                throw new IllegalStateException("Found too many ResourceFilesProviders");
            }
        }
    }
}

