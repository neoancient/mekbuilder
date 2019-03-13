/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2017 The MegaMek Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.megamek.mekbuilder.unit;

import org.megamek.mekbuilder.component.ComponentSwitch;
import org.megamek.mekbuilder.component.ComponentType;
import org.megamek.mekbuilder.component.HeavyWeapon;
import org.megamek.mekbuilder.component.WeaponFlag;
import org.megamek.mekbuilder.component.IEngineMount;
import org.megamek.mekbuilder.component.Mount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Base class for unit types
 */

public abstract class UnitBuild {

    private final UnitType unitType;
    private final List<Mount> components = new ArrayList<>();

    protected UnitBuild(UnitType unitType) {
        this.unitType = unitType;
    }

    /**
     * @return An unmodifiable {@link List} of the installed components.
     */
    public List<Mount> getComponents() {
        return Collections.unmodifiableList(components);
    }

    /**
     * Calculates the weight of all installed components.
     *
     * @return The current build weight
     */
    public double buildWeight() {
        return components.stream().mapToDouble(Mount::getComponentWeight).sum();
    }

    public boolean isBiped() {
        return false;
    }
    public boolean isTripod() {
        return false;
    }
    public boolean isQuad() {
        return false;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    abstract public boolean usesKilogramStandard();

    /**
     * @return The total tonnage of all installed weapons
     */
    public double getWeaponTonnage() {
        return getComponents().stream()
                .filter(m -> m.getComponent().getType().equals(ComponentType.HEAVY_WEAPON)
                    || m.getComponent().getType().equals(ComponentType.INF_WEAPON))
                .mapToDouble(m -> m.getComponentWeight()).sum();
    }

    /**
     * @param loc A location on the unit
     * @return    The total tonnage of weapons installed in the location
     */
    public double getWeaponTonnage(UnitLocation loc) {
        return getComponents().stream()
                .filter(m -> m.isInLocation(loc)
                        && m.getComponent().getType().equals(ComponentType.HEAVY_WEAPON))
                .mapToDouble(m -> m.getComponentWeight()).sum();
    }

    /**
     * @return The total tonnage of all installed energy weapons
     */
    public double getEnergyWeaponTonnage() {
        return getComponents().stream()
                .filter(m -> m.getComponent().getType().equals(ComponentType.HEAVY_WEAPON)
                        && ((HeavyWeapon) m.getComponent()).hasFlag(WeaponFlag.DIRECT_FIRE_ENERGY))
                .mapToDouble(m -> m.getComponentWeight()).sum();
    }

    /**
     * @return The total tonnage of weapons that can be linked to a targeting computer.
     */
    public double getTCLinkedTonnage() {
        return getComponents().stream()
                .filter(m -> m.getComponent().hasFlag(ComponentSwitch.TC_LINKABLE))
                .mapToDouble(m -> m.getComponentWeight()).sum();
    }

    /**
     * To get the current weight of installed components, use {@link #buildWeight()}
     *
     * @return The declared final tonnage of the unit.
     */
    abstract public double getTonnage();

    /**
     * @return The weight of the structure, in tons.
     */
    abstract public double getStructureTonnage();

    /**
     * @return The weight of the armor, in tons
     */
    abstract public double getArmorTonnage();

    /**
     * @return The total number of armor points allocated on the unit
     */
    abstract public double getTotalArmorPoints();

    abstract public String getDefaultArmorName();

    /**
     * @param loc A location on the unit
     * @return    The maximum number of armor points that can be allocated to the location
     */
    abstract public int getMaxArmorPoints(UnitLocation loc);

    /**
     * @return The unit's weight class, based on declared weight.
     */
    abstract public UnitWeightClass getWeightClass();

    /**
     * @return The unit's engine
     */
    abstract public IEngineMount getEngine();

    /**
     * @return A {@link Set} of locations available on the unit
     */
    abstract public Set<UnitLocation> getLocationSet();

    /**
     * @param loc A location on the unit
     * @return    Whether the location contains the cockpit or command center.
     */
    abstract public boolean isCockpitLocation(UnitLocation loc);
    /**
     * @return The walk/cruise/safe thrust MP with modifications for movement enhancements or restrictions
     */
    abstract public int getBaseWalkMP();

    /**
     * @return The run/flank/max thrust MP with modifications for movement enhancements or restrictions
     */
    public int getBaseRunMP() {
        return (int) Math.ceil(getBaseWalkMP() * 1.5);
    }

    /**
     * @return The actual walk/cruise/safe thrust MP after adjusting for restrictions
     */
    public int getWalkMP() {
        return getBaseWalkMP();
    }

    /**
     * @return The actual run/flank/max thrust MP after adjusting for restrictions
     */
    public int getRunMP() {
        return getBaseRunMP();
    }

}
