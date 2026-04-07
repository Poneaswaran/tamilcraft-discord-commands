/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.localization;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Map;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

public class LocalizationMapper {
    private final LocalizationFunction localizationFunction;

    private LocalizationMapper(LocalizationFunction localizationFunction) {
        this.localizationFunction = localizationFunction;
    }

    @Nonnull
    public static LocalizationMapper fromFunction(@Nonnull LocalizationFunction localizationFunction) {
        return new LocalizationMapper(localizationFunction);
    }

    public void localizeCommand(CommandData commandData, DataArray optionArray) {
        TranslationContext ctx = new TranslationContext();
        ctx.withKey(commandData.getName(), () -> {
            ctx.trySetTranslation(commandData.getNameLocalizations(), "name");
            if (commandData.getType() == Command.Type.SLASH) {
                SlashCommandData slashCommandData = (SlashCommandData)commandData;
                ctx.trySetTranslation(slashCommandData.getDescriptionLocalizations(), "description");
                this.localizeOptionArray(optionArray, ctx);
            }
        });
    }

    private void localizeOptionArray(DataArray optionArray, TranslationContext ctx) {
        ctx.forObjects(optionArray, o -> o.getString("name"), obj -> {
            if (obj.hasKey("name_localizations")) {
                ctx.trySetTranslation(obj.getObject("name_localizations"), "name");
            }
            if (obj.hasKey("description_localizations")) {
                ctx.trySetTranslation(obj.getObject("description_localizations"), "description");
            }
            if (obj.hasKey("options")) {
                this.localizeOptionArray(obj.getArray("options"), ctx);
            }
            if (obj.hasKey("choices")) {
                ctx.withKey("choices", () -> this.localizeOptionArray(obj.getArray("choices"), ctx));
            }
        });
    }

    private class TranslationContext {
        private final Stack<String> keyComponents = new Stack();

        private TranslationContext() {
        }

        private void forObjects(DataArray source2, Function<DataObject, String> keyExtractor, Consumer<DataObject> consumer) {
            for (int i = 0; i < source2.length(); ++i) {
                boolean isOption;
                DataObject item = source2.getObject(i);
                Runnable runnable2 = () -> {
                    String key = (String)keyExtractor.apply(item);
                    this.keyComponents.push(key);
                    consumer.accept(item);
                    this.keyComponents.pop();
                };
                OptionType type = OptionType.fromKey(item.getInt("type", -1));
                boolean bl = isOption = type != OptionType.SUB_COMMAND && type != OptionType.SUB_COMMAND_GROUP && type != OptionType.UNKNOWN;
                if (isOption) {
                    this.withKey("options", runnable2);
                    continue;
                }
                runnable2.run();
            }
        }

        private void withKey(String key, Runnable runnable2) {
            this.keyComponents.push(key);
            runnable2.run();
            this.keyComponents.pop();
        }

        private String getKey(String finalComponent) {
            StringJoiner joiner = new StringJoiner(".");
            for (String keyComponent : this.keyComponents) {
                joiner.add(keyComponent.replace(" ", "_"));
            }
            joiner.add(finalComponent.replace(" ", "_"));
            return joiner.toString().toLowerCase();
        }

        private void trySetTranslation(LocalizationMap localizationMap, String finalComponent) {
            String key = this.getKey(finalComponent);
            try {
                Map<DiscordLocale, String> data = LocalizationMapper.this.localizationFunction.apply(key);
                localizationMap.setTranslations(data);
            }
            catch (Exception e) {
                throw new RuntimeException("An uncaught exception occurred while using a LocalizationFunction, localization key: '" + key + "'", e);
            }
        }

        private void trySetTranslation(DataObject localizationMap, String finalComponent) {
            String key = this.getKey(finalComponent);
            try {
                Map<DiscordLocale, String> data = LocalizationMapper.this.localizationFunction.apply(key);
                data.forEach((locale, localizedValue) -> {
                    Checks.check(locale != DiscordLocale.UNKNOWN, "Localization function returned a map with an 'UNKNOWN' DiscordLocale");
                    localizationMap.put(locale.getLocale(), localizedValue);
                });
            }
            catch (Exception e) {
                throw new RuntimeException("An uncaught exception occurred while using a LocalizationFunction, localization key: '" + key + "'", e);
            }
        }
    }
}

