/*
 * Copyright (c) 2023, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package de.eintosti.buildsystem.navigator.inventory;

import com.google.common.collect.Sets;
import de.eintosti.buildsystem.BuildSystemPlugin;
import de.eintosti.buildsystem.api.world.Visibility;
import de.eintosti.buildsystem.api.world.data.WorldStatus;
import de.eintosti.buildsystem.util.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ArchiveInventory extends FilteredWorldsInventory {

    private final BuildSystemPlugin plugin;
    private final InventoryUtils inventoryUtils;

    public ArchiveInventory(BuildSystemPlugin plugin) {
        super(plugin, "archive_title", "archive_no_worlds", Visibility.IGNORE,
                Sets.newHashSet(WorldStatus.ARCHIVE)
        );

        this.plugin = plugin;
        this.inventoryUtils = plugin.getInventoryUtil();
    }

    @Override
    protected Inventory createInventory(Player player) {
        Inventory inventory = super.createInventory(player);
        inventoryUtils.addGlassPane(plugin, player, inventory, 49);
        return inventory;
    }
}