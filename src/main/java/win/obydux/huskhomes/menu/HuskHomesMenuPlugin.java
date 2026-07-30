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

package win.obydux.huskhomes.menu;

import net.kyori.adventure.platform.AudienceProvider;
import net.william278.annotaml.Annotaml;
import net.william278.desertwell.util.Version;
import win.obydux.huskhomes.menu.config.Locales;
import win.obydux.huskhomes.menu.config.Settings;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public interface HuskHomesMenuPlugin {

    @NotNull
    default Locales loadLocales() {
        try {
            return Annotaml.create(new File(getDataFolder(), "messages-" + loadSettings().getLanguage() + ".yml"),
                    Annotaml.create(Locales.class, getResource("locales/" + loadSettings().getLanguage() + ".yml")).get()).get();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | IOException e) {
            throw new IllegalStateException("Failed to load locales file", e);
        }
    }

    @NotNull
    default Settings loadSettings() {
        try {
            return Annotaml.create(new File(getDataFolder(), "config.yml"), Settings.class).get();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | IOException e) {
            throw new IllegalStateException("Failed to load config file", e);
        }
    }

    @NotNull
    AudienceProvider getAudiences();

    @NotNull
    Version getPluginVersion();

    @NotNull
    Settings getSettings();

    @NotNull
    Locales getLocales();

    File getDataFolder();

    InputStream getResource(@NotNull String fileName);

}
