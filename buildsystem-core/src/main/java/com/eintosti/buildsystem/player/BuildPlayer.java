/*
 * Copyright (c) 2023, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.eintosti.buildsystem.player;

import com.eintosti.buildsystem.navigator.NavigatorInventoryType;
import com.eintosti.buildsystem.settings.Settings;
import com.eintosti.buildsystem.world.BuildWorld;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author einTosti
 * @since 2.21.0
 */
public class BuildPlayer implements ConfigurationSerializable {

    private final UUID uuid;
    private final Settings settings;
    private final CachedValues cachedValues;

    private BuildWorld cachedWorld;
    private LogoutLocation logoutLocation;
    private Location previousLocation;
    private NavigatorInventoryType lastLookedAt;

    public BuildPlayer(UUID uuid, Settings settings) {
        this.uuid = uuid;
        this.settings = settings;
        this.cachedValues = new CachedValues();
    }

    /**
     * Gets the unique-id of the player.
     *
     * @return The player's UUID
     */
    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Gets the player's per-player settings.
     *
     * @return The player's settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Gets values that are supposed to be cached for a short amount of time.
     *
     * @return The player's cached values
     */
    public CachedValues getCachedValues() {
        return cachedValues;
    }

    /**
     * Gets the world the player has selected for an action.
     *
     * @return The cached world, if any
     */
    @Nullable
    public BuildWorld getCachedWorld() {
        return cachedWorld;
    }

    /**
     * Marks a world as selected to later be used.
     *
     * @param buildWorld The world to select.
     */
    public void setCachedWorld(BuildWorld buildWorld) {
        this.cachedWorld = buildWorld;
    }

    /**
     * Gets the location where the player was last before logging off.
     *
     * @return The location
     */
    @Nullable
    public LogoutLocation getLogoutLocation() {
        return logoutLocation;
    }

    /**
     * Sets the location where the player was last before logging off.
     *
     * @param logoutLocation The logout location
     */
    public void setLogoutLocation(LogoutLocation logoutLocation) {
        this.logoutLocation = logoutLocation;
    }

    /**
     * Gets the location the player was last at.
     * <p>
     * Usually this is the last location before teleportation.
     *
     * @return The player's previous location
     */
    @Nullable
    public Location getPreviousLocation() {
        return previousLocation;
    }

    /**
     * Sets the location the player was last at.
     * <p>
     * Usually this is the last location before teleportation.
     *
     * @param location The location
     */
    public void setPreviousLocation(Location location) {
        this.previousLocation = location;
    }

    /**
     * Gets the {@link NavigatorInventoryType} the player last looked at.
     *
     * @return The last looked navigator inventory type
     */
    @Nullable
    public NavigatorInventoryType getLastLookedAt() {
        return lastLookedAt;
    }

    /**
     * Sets the {@link NavigatorInventoryType} the player last looked at.
     *
     * @param type The last looked navigator inventory type
     */
    public void setLastLookedAt(NavigatorInventoryType type) {
        this.lastLookedAt = type;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> player = new HashMap<>();

        player.put("settings", settings.serialize());
        if (logoutLocation != null) {
            player.put("logout-location", logoutLocation.toString());
        }

        return player;
    }
}