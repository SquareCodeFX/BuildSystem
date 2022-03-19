/*
 * Copyright (c) 2022, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.eintosti.buildsystem.expansion.luckperms;

import com.eintosti.buildsystem.BuildSystem;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author einTosti
 */
public class LuckPermsExpansion {

    private final BuildSystem plugin;
    private final ContextManager contextManager;
    private final List<ContextCalculator<Player>> registeredCalculators;

    public LuckPermsExpansion(BuildSystem plugin) {
        LuckPerms luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
        if (luckPerms == null) {
            throw new IllegalStateException("LuckPerms API not loaded.");
        }

        this.plugin = plugin;
        this.contextManager = luckPerms.getContextManager();
        this.registeredCalculators = new ArrayList<>();
    }

    public void registerAll() {
        register("role", () -> new RoleCalculator(plugin));
    }

    private void register(String option, Supplier<ContextCalculator<Player>> calculatorSupplier) {
        plugin.getLogger().info("Registering '" + option + "' calculator");
        ContextCalculator<Player> calculator = calculatorSupplier.get();
        this.contextManager.registerCalculator(calculator);
        this.registeredCalculators.add(calculator);
    }

    public void unregisterAll() {
        this.registeredCalculators.forEach(this.contextManager::unregisterCalculator);
        this.registeredCalculators.clear();
    }
}
