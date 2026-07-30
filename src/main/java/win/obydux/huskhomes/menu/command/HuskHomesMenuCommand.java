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

package win.obydux.huskhomes.menu.command;

import de.themoep.minedown.adventure.MineDown;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HuskHomesMenuCommand implements CommandExecutor, TabExecutor {
    private final win.obydux.huskhomes.menu.HuskHomesMenu plugin;
    private final AboutMenu aboutMenu;

    public HuskHomesMenuCommand(@NotNull win.obydux.huskhomes.menu.HuskHomesMenu plugin) {
        this.plugin = plugin;
        this.aboutMenu = AboutMenu.builder().title(Component.text("HuskHomesMenu"))
                .description(Component.text("Show HuskHomes homes and warps in a simple to use menu"))
                .version(plugin.getPluginVersion())
                .credits("Author",
                        AboutMenu.Credit.of("William278").description("Click to visit website").url("https://william278.net"))
                .credits("Contributors",
                        AboutMenu.Credit.of("ApliNi").description("Code"),
                        AboutMenu.Credit.of("Obydux").description("Code"))
                .credits("Translators",
                        AboutMenu.Credit.of("ApliNi").description("Simplified Chinese (zh-cn)"),
                        AboutMenu.Credit.of("Revoolt").description("Spanish (es-es)"),
                        AboutMenu.Credit.of("XeroLe1er").description("French (fr-fr)"))
                .buttons(
                        AboutMenu.Link.of("https://william278.net/docs/huskhomes/gui-add-on")
                                .text("About").icon("⛏"),
                        AboutMenu.Link.of("https://github.com/Obydux/HuskHomesMenu/issues")
                                .text("Issues").icon("❌").color(TextColor.color(0xff0000)),
                        AboutMenu.Link.of("https://discord.gg/sQ6VmWDzN3")
                                .text("Discord").icon("⭐").color(TextColor.color(0x6773f5)))
                .build();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        final Audience audience;
        if (sender instanceof Player player) {
            audience = plugin.getAudiences().player(player.getUniqueId());
        } else {
            audience = plugin.getAudiences().console();
        }

        final String subCommand = args.length >= 1 ? args[0] : "";
        if (subCommand.equals("reload")) {
            plugin.reloadConfigFiles();
            audience.sendMessage(new MineDown("[[HuskHomesMenu]](#00fb9a bold) [Reloaded config files!](#00fb9a)")
                    .toComponent());
        } else {
            audience.sendMessage(aboutMenu.toComponent());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        return this.filter(List.of("reload", "about"), args);
    }

    @NotNull
    private List<String> filter(@NotNull List<String> list, @NotNull String[] args) {
        final List<String> filtered = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith(args[args.length - 1])) {
                filtered.add(s);
            }
        }
        return filtered;
    }
}
