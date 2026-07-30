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

import net.william278.annotaml.YamlComment;
import net.william278.annotaml.YamlFile;
import net.william278.annotaml.YamlKey;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@YamlFile(header = """
        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
        ┃     HuskHomesMenu Config      ┃
        ┃    Developed by William278   ┃
        ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
        ┗╸ Information: https://william278.net/project/huskhomesgui""")
public class Settings {

    @YamlKey("language")
    private String language = "en-gb";

    @YamlComment("Options for the home/warp list menu")
    @YamlKey("menu.rows")
    private int menuSize = 4;
    @YamlKey("menu.show_controls")
    private boolean showMenuControls = true;
    @YamlKey("menu.display_controls_help_in_lore")
    private boolean displayControlsHelpInCore = false;
    @YamlKey("menu.text_wrap_length")
    private int textWrapLength = 17;

    @YamlKey("menu.items.homes_filler")
    private String homesFillerItem = "minecraft:orange_stained_glass_pane";
    @YamlKey("menu.items.public_homes_filler")
    private String publicHomesFillerItem = "minecraft:lime_stained_glass_pane";
    @YamlKey("menu.items.warps_filler")
    private String warpsFillerItem = "minecraft:cyan_stained_glass_pane";
    @YamlKey("menu.items.default_icon")
    private String defaultIcon = "minecraft:stone";
    @YamlKey("menu.icons.paginate_first_page")
    private String paginateFirstPage = "minecraft:spectral_arrow";
    @YamlKey("menu.icons.paginate_previous_page")
    private String paginatePreviousPage = "minecraft:arrow";
    @YamlKey("menu.icons.paginate_next_page")
    private String paginateNextPage = "minecraft:arrow";
    @YamlKey("menu.icons.paginate_last_page")
    private String paginateLastPage = "minecraft:spectral_arrow";
    @YamlKey("menu.icons.controls_icon")
    private String controlsIcon = "minecraft:oak_sign";

    @YamlComment("Options for the home/warp editor GUI")
    @YamlKey("editor.icons.home_editor_filler")
    private String homeEditorFillerIcon = "minecraft:lime_stained_glass_pane";
    @YamlKey("editor.icons.warp_editor_filler")
    private String warpEditorFillerIcon = "minecraft:lime_stained_glass_pane";
    @YamlKey("editor.icons.back_button")
    private String editorBackButtonIcon = "minecraft:orange_stained_glass_pane";
    @YamlKey("editor.icons.edit_location_button")
    private String editorEditLocationButtonIcon = "minecraft:oak_boat";
    @YamlKey("editor.icons.edit_name_button")
    private String editorEditNameButtonIcon = "minecraft:name_tag";
    @YamlKey("editor.icons.edit_description_button")
    private String editorEditDescriptionButtonIcon = "minecraft:writable_book";
    @YamlKey("editor.icons.edit_privacy_button")
    private String editorEditPrivacyButtonIcon = "minecraft:nether_star";
    @YamlKey("editor.icons.delete_button")
    private String editorDeleteButtonIcon = "minecraft:barrier";

    @SuppressWarnings("unused")
    private Settings() {
    }

    @NotNull
    public String getLanguage() {
        return language;
    }

    public int getMenuSize() {
        return Math.max(2, Math.min(menuSize, 6));
    }

    public boolean doShowMenuControls() {
        return showMenuControls;
    }

    public boolean camelCase() {
        return displayControlsHelpInCore;
    }

    public int getTextWrapLength() {
        return textWrapLength;
    }

    @NotNull
    public Material getHomesFillerItem() {
        return getMaterial(homesFillerItem);
    }

    @NotNull
    public Material getPublicHomesFillerItem() {
        return getMaterial(publicHomesFillerItem);
    }

    @NotNull
    public Material getWarpsFillerItem() {
        return getMaterial(warpsFillerItem);
    }

    @NotNull
    public Material getDefaultIcon() {
        return getMaterial(defaultIcon);
    }

    @NotNull
    public Material getPaginateFirstPage() {
        return getMaterial(paginateFirstPage);
    }

    @NotNull
    public Material getPaginatePreviousPage() {
        return getMaterial(paginatePreviousPage);
    }

    @NotNull
    public Material getPaginateNextPage() {
        return getMaterial(paginateNextPage);
    }

    @NotNull
    public Material getPaginateLastPage() {
        return getMaterial(paginateLastPage);
    }

    @NotNull
    public Material getControlsIcon() {
        return getMaterial(controlsIcon);
    }

    @NotNull
    public Material getHomeEditorFillerIcon() {
        return getMaterial(homeEditorFillerIcon);
    }

    @NotNull
    public Material getWarpEditorFillerIcon() {
        return getMaterial(warpEditorFillerIcon);
    }

    @NotNull
    public Material getEditorBackButtonIcon() {
        return getMaterial(editorBackButtonIcon);
    }

    @NotNull
    public Material getEditorEditLocationButtonIcon() {
        return getMaterial(editorEditLocationButtonIcon);
    }

    @NotNull
    public Material getEditorEditNameButtonIcon() {
        return getMaterial(editorEditNameButtonIcon);
    }

    @NotNull
    public Material getEditorEditDescriptionButtonIcon() {
        return getMaterial(editorEditDescriptionButtonIcon);
    }

    @NotNull
    public Material getEditorEditPrivacyButtonIcon() {
        return getMaterial(editorEditPrivacyButtonIcon);
    }

    @NotNull
    public Material getEditorDeleteButtonIcon() {
        return getMaterial(editorDeleteButtonIcon);
    }

    @NotNull
    private Material getMaterial(@NotNull String id) {
        id = id.replace("minecraft:", "");
        return Optional.ofNullable(Material.matchMaterial(id)).orElse(Material.STONE);
    }
}
