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

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import win.obydux.huskhomes.menu.HuskHomesMenu;

public class HuskHomesMenuCommand {
    private final HuskHomesMenu plugin;
    private final AboutMenu aboutMenu;

    public HuskHomesMenuCommand(@NotNull HuskHomesMenu plugin) {
        this.plugin = plugin;
        this.aboutMenu = AboutMenu.builder().title(Component.text("HuskHomesMenu"))
                .description(Component.text("Show HuskHomes homes and warps in a simple to use menu."))
                .version(plugin.getPluginVersion())
                .credits("Authors",
                        AboutMenu.Credit.of("William278").description("Click to visit website").url("https://william278.net"),
                        AboutMenu.Credit.of("Obydux").description("Click to visit website").url("https://github.com/Obydux"))
                .credits("Contributors",
                        AboutMenu.Credit.of("ApliNi").description("Code"))
                .credits("Translators",
                        AboutMenu.Credit.of("ApliNi").description("Simplified Chinese (zh-cn)"),
                        AboutMenu.Credit.of("Revoolt").description("Spanish (es-es)"),
                        AboutMenu.Credit.of("XeroLe1er").description("French (fr-fr)"))
                .buttons(
                        AboutMenu.Link.of("https://github.com/Obydux/HuskHomesMenu/blob/master/README.md")
                                .text("About").icon("⛏"),
                        AboutMenu.Link.of("https://github.com/Obydux/HuskHomesMenu/issues")
                                .text("Issues").icon("❌").color(TextColor.color(0xff0000)),
                        AboutMenu.Link.of("https://discord.gg/sQ6VmWDzN3")
                                .text("Discord").icon("⭐").color(TextColor.color(0x6773f5)))
                .build();
    }

    public void register() {
        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                event -> {
                    final Commands commands = event.registrar();

                    commands.register(
                            LiteralArgumentBuilder.<CommandSourceStack>literal("huskhomesmenu")
                                    .requires(source -> source.getSender().isOp() || source.getSender().hasPermission("huskhomesmenu.command"))
                                    .executes(this::executeAbout)
                                    .then(LiteralArgumentBuilder.<CommandSourceStack>literal("reload")
                                            .executes(this::executeReload))
                                    .then(LiteralArgumentBuilder.<CommandSourceStack>literal("about")
                                            .executes(this::executeAbout))
                                    .build(),
                            "Main HuskHomesMenu command."
                    );
                }
        );
    }

    private int executeReload(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        plugin.reloadConfigFiles();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            try {
                Component comp = Component.text("[HuskHomesMenu] Reloaded config files!");
                if (sender instanceof Player player) {
                    player.sendMessage(comp);
                } else {
                    plugin.getAudiences().console().sendMessage(comp);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to send component message in executeReload: " + e.getMessage());
            }
        });
        return 1;
    }

    private int executeAbout(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            try {
                Component comp = aboutMenu.toComponent();
                if (sender instanceof Player player) {
                    player.sendMessage(comp);
                } else {
                    plugin.getAudiences().console().sendMessage(comp);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to send aboutMenu component: " + e.getMessage());
            }
        });
        return 1;
    }
}