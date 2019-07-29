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

import megamek.common.annotations.Nullable;
import org.megamek.mekbuilder.component.ComponentSwitch;
import org.megamek.mekbuilder.component.ComponentType;
import org.megamek.mekbuilder.component.HeavyWeapon;
import org.megamek.mekbuilder.component.WeaponFlag;
import org.megamek.mekbuilder.component.IEngineMount;
import org.megamek.mekbuilder.component.Mount;
import org.megamek.mekbuilder.tech.Faction;
import org.megamek.mekbuilder.tech.TechBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Base class for unit types
 */

public abstract class UnitBuild {

    private final UnitType unitType;
    private final List<Mount> components = new ArrayList<>();

    private String chassis = "";
    private String model = "";
    private String source = "";
    private int year = 3067;
    private TechBase techBase = TechBase.IS;
    private Faction faction = null;

    protected UnitBuild(UnitType unitType) {
        this.unitType = unitType;
    }

    protected List<Mount> getComponents() {
        return components;
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

    /**
     * @return The chassis name. Variants typically keep the same chassis name and differ in model name.
     */
    public String getChassis() {
        return chassis;
    }

    /**
     * Sets the chassis name.
     *
     * @param chassis The chassis name
     */
    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    /**
     * @return The unit's model name. Variants typically have the same chassis name but differ in model name.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model name.
     *
     * @param model The unit's model name
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return The unit's source publication
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source The name of the source publication
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The unit's initial production year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the production year.
     *
     * @param year The year the unit was first produced.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return Whether the unit has an IS or Clan tech base, or {@link TechBase#ALL} for mixed tech.
     */
    public TechBase getTechBase() {
        return techBase;
    }

    /**
     * Sets whether the unit has an IS or Clan base.
     *
     * @param techBase The tech base.
     */
    public void setTechBase(TechBase techBase) {
        this.techBase = techBase;
    }

    /**
     * The faction most closely associated with the unit. If non-null, this is used for faction-specific tech intro
     * dates.
     *
     * @return The producing faction
     */
    public @Nullable Faction getFaction() {
        return faction;
    }

    /**
     * Sets the faction to use for faction-specific intro dates.
     *
     * @param faction The production faction, or {@code null} to ignore faction-specific intro dates.
     */
    public void setFaction(@Nullable Faction faction) {
        this.faction = faction;
    }

    /**
     * @return Whether this unit should round weights to the kilogram instead of the half ton.
     */
    abstract public boolean usesKilogramStandard();

    /**
     * @return The total tonnage of all installed weapons
     */
    public double getWeaponTonnage() {
        return getComponents().stream()
                .filter(m -> m.getComponent().getType().equals(ComponentType.HEAVY_WEAPON)
                    || m.getComponent().getType().equals(ComponentType.INF_WEAPON))
                .mapToDouble(Mount::getComponentWeight).sum();
    }

    /**
     * @param loc A location on the unit
     * @return    The total tonnage of weapons installed in the location
     */
    public double getWeaponTonnage(UnitLocation loc) {
        return getComponents().stream()
                .filter(m -> m.isInLocation(loc)
                        && m.getComponent().getType().equals(ComponentType.HEAVY_WEAPON))
                .mapToDouble(Mount::getComponentWeight).sum();
    }

    /**
     * @return The total tonnage of all installed energy weapons
     */
    public double getEnergyWeaponTonnage() {
        return getComponents().stream()
                .filter(m -> m.getComponent().getType().equals(ComponentType.HEAVY_WEAPON)
                        && ((HeavyWeapon) m.getComponent()).hasFlag(WeaponFlag.DIRECT_FIRE_ENERGY))
                .mapToDouble(Mount::getComponentWeight).sum();
    }

    /**
     * @return The total tonnage of weapons that can be linked to a targeting computer.
     */
    public double getTCLinkedTonnage() {
        return getComponents().stream()
                .filter(m -> m.getComponent().hasFlag(ComponentSwitch.TC_LINKABLE))
                .mapToDouble(Mount::getComponentWeight).sum();
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
