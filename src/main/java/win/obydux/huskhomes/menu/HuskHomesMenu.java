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
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.william278.desertwell.util.Version;
import net.william278.huskhomes.BukkitHuskHomes;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import win.obydux.huskhomes.menu.command.HuskHomesMenuCommand;
import win.obydux.huskhomes.menu.config.Locales;
import win.obydux.huskhomes.menu.config.Settings;
import win.obydux.huskhomes.menu.listener.ListListener;

import java.util.Objects;
import java.util.logging.Level;

public class HuskHomesMenu extends JavaPlugin implements HuskHomesMenuPlugin {
    private BukkitAudiences adventure;
    private BukkitHuskHomes huskHomes;
    private Settings settings;
    private Locales locales;

    @Override
    public void onEnable() {
        // Load audiences
        this.adventure = BukkitAudiences.create(this);
        this.huskHomes = Objects.requireNonNull((BukkitHuskHomes) getServer().getPluginManager()
                .getPlugin("HuskHomes"), "HuskHomes plugin is required");

        // Load settings and locales
        this.reloadConfigFiles();

        // Register event listener
        getServer().getPluginManager().registerEvents(new ListListener(this), this);

        // Register command using Brigadier
        HuskHomesMenuCommand command = new HuskHomesMenuCommand(this);
        command.register();

        // Log to console
        getLogger().log(Level.INFO, "Successfully enabled HuskHomesMenu v" + getDescription().getVersion());
    }

    public void reloadConfigFiles() {
        this.settings = loadSettings();
        this.locales = loadLocales();
    }

    @Override
    @NotNull
    public AudienceProvider getAudiences() {
        return adventure;
    }

    @Override
    @NotNull
    public Version getPluginVersion() {
        return Version.fromString(getDescription().getVersion(), "-");
    }

    @Override
    @NotNull
    public Settings getSettings() {
        return settings;
    }

    @Override
    @NotNull
    public Locales getLocales() {
        return locales;
    }

    @NotNull
    public BukkitHuskHomes getHuskHomes() {
        return huskHomes;
    }
}