/*
 * This file is part of HuskHomesGUI, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package win.obydux.huskhomes.menu.config;

import de.themoep.minedown.adventure.MineDown;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.william278.annotaml.YamlFile;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@YamlFile(header = """
        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
        ┃     HuskHomesMenu Locales     ┃
        ┃    Developed by William278   ┃
        ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
        ┗╸ Formatted in MineDown: https://github.com/Phoenix616/MineDown""",
        rootedMap = true)
public class Locales {

    /**
     * The raw set of locales loaded from yaml
     */
    public Map<String, String> rawLocales = new HashMap<>();

    @SuppressWarnings("unused")
    private Locales() {
    }

    /**
     * Returns a raw, un-formatted locale loaded from the locales file
     *
     * @param localeId String identifier of the locale, corresponding to a key in the file
     * @return An {@link Optional} containing the locale corresponding to the id, if it exists
     */
    public Optional<String> getRawLocale(@NotNull String localeId) {
        return Optional.ofNullable(rawLocales.get(localeId)).map(StringEscapeUtils::unescapeJava);
    }

    /**
     * Returns a raw, un-formatted locale loaded from the locales file, with replacements applied
     * <p>
     * Note that replacements will not be MineDown-escaped; use {@link #escapeText(String)} to escape replacements
     *
     * @param localeId     String identifier of the locale, corresponding to a key in the file
     * @param replacements Ordered array of replacement strings to fill in placeholders with
     * @return An {@link Optional} containing the replacement-applied locale corresponding to the id, if it exists
     */
    public Optional<String> getRawLocale(@NotNull String localeId, @NotNull String... replacements) {
        return getRawLocale(localeId).map(locale -> applyReplacements(locale, replacements));
    }

    /**
     * Returns a MineDown-formatted locale from the locales file
     *
     * @param localeId String identifier of the locale, corresponding to a key in the file
     * @return An {@link Optional} containing the formatted locale corresponding to the id, if it exists
     */
    public String getLocale(@NotNull String localeId) {
        return getLocale(localeId, new String[0]);
    }

    /**
     * Returns a MineDown-formatted locale from the locales file, with replacements applied
     * <p>
     * Note that replacements will be MineDown-escaped before application
     *
     * @param localeId     String identifier of the locale, corresponding to a key in the file
     * @param replacements Ordered array of replacement strings to fill in placeholders with
     * @return An {@link Optional} containing the replacement-applied, formatted locale corresponding to the id, if it exists
     */
    @NotNull
    public String getLocale(@NotNull String localeId, @NotNull String... replacements) {
        return getRawLocale(localeId, Arrays.stream(replacements)
                .map(Locales::escapeText).toArray(String[]::new))
                .map(MineDown::new).map(MineDown::toComponent)
                .map(LegacyComponentSerializer.builder().build()::serialize)
                .orElse("");
    }

    /**
     * Apply placeholder replacements to a raw locale
     *
     * @param rawLocale    The raw, unparsed locale
     * @param replacements Ordered array of replacement strings to fill in placeholders with
     * @return the raw locale, with inserted placeholders
     */
    @NotNull
    private String applyReplacements(@NotNull String rawLocale, @NotNull String... replacements) {
        int replacementIndexer = 1;
        for (String replacement : replacements) {
            String replacementString = "%" + replacementIndexer + "%";
            rawLocale = rawLocale.replace(replacementString, replacement);
            replacementIndexer += 1;
        }
        return rawLocale;
    }

    /**
     * Escape a string from {@link MineDown} formatting for use in a MineDown-formatted locale
     *
     * @param string The string to escape
     * @return The escaped string
     */
    @NotNull
    public static String escapeText(@NotNull String string) {
        final StringBuilder value = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            boolean isEscape = c == '\\';
            boolean isColorCode = i + 1 < string.length() && (c == 167 || c == '&');
            boolean isEvent = c == '[' || c == ']' || c == '(' || c == ')';
            if (isEscape || isColorCode || isEvent) {
                value.append('\\');
            }

            value.append(c);
        }
        return value.toString();
    }


    /**
     * Wraps the given string to a new line after every (int) characters.
     *
     * @param string the string to be wrapped, cannot be null
     * @return the wrapped string
     * @throws NullPointerException if the string is null
     */
    public static String textWrap(@NotNull win.obydux.huskhomes.menu.HuskHomesMenu plugin, @NotNull String string) {
        // ([\x00-\xFF]{1,2}|.?){27}
        Matcher matcher = Pattern.compile("([\\x00-\\xFF]{1,2}|.?){"+ plugin.getSettings().getTextWrapLength() +"}").matcher(string);
        StringBuilder out = new StringBuilder();

        while (matcher.find()) {
            if (!matcher.group().trim().isEmpty()) {
                out.append(plugin.getLocales().getLocale("item_description_line_style", matcher.group().trim()));
            }
        }
        return String.valueOf(out);
    }

}