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

import com.sun.istack.NotNull;
import megamek.common.annotations.Nullable;
import org.megamek.mekbuilder.component.*;
import org.megamek.mekbuilder.tech.Faction;
import org.megamek.mekbuilder.tech.ITechFilter;
import org.megamek.mekbuilder.tech.TechBase;
import org.megamek.mekbuilder.tech.UnitConstructionOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Base class for unit types
 */

public abstract class UnitBuild {

    private final List<Mount> components = new ArrayList<>();
    private UnitConstructionOption baseConstructionOption;

    private String chassis = "";
    private String model = "";
    private String source = "";
    private int year = 3067;
    private TechBase techBase = TechBase.IS;
    private Faction faction = null;
    private double tonnage = 0.0;

    protected UnitBuild(UnitConstructionOption option) {
        this.baseConstructionOption = option;
    }

    public List<Mount> getComponents() {
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

    /**
     * The unit construction option sets the variation on the base unit type and can affect
     * available intro years and weight limits.
     *
     * @return The current base construction option
     */
    public UnitConstructionOption getBaseConstructionOption() {
        return baseConstructionOption;
    }

    /**
     * Allows changing the unit subtype, but the new one must have the same base type as the previous one.
     *
     * @param option The construction option
     */
    public void setBaseConstructionOption(UnitConstructionOption option) {
        if (option.getUnitType() != baseConstructionOption.getUnitType()) {
            throw new IllegalArgumentException("Illegal change of unit type.");
        }
        baseConstructionOption = option;
    }

    public UnitType getUnitType() {
        return baseConstructionOption.getUnitType();
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
     * To get the current weight of installed components, use {@link #buildWeight()}
     *
     * @return The declared final tonnage of the unit.
     */
    public double getTonnage() {
        return tonnage;
    }

    /**
     * Sets the declared weight of the unit
     *
     * @param tonnage The weight of the unit in tons
     */
    public void setTonnage(double tonnage) {
        this.tonnage = tonnage;
    }

    /**
     * @return Whether the unit is built using modular omni technology
     */
    public boolean isOmni() {
        return false;
    }

    /**
     * Sets whether the unit uses omni modular technology. Note that units which cannot
     * be built using omni tech will ignore this.
     *
     * @param omni Whether the unit is build with omni tech
     */
    public void setOmni(boolean omni) {}

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
     * Sets the base movement for the unit. Setting this will adjust engine size as necessary.
     *
     * @param walk The base walk/cruise/safe thrust value
     */
    public abstract void setBaseWalkMP(int walk);

    /**
     * @return The walk/cruise/safe thrust MP without modifications for movement enhancements or restrictions
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

    /**
     * @return A String representation of the running/flanking MP that shows the effect of movement enhancements
     *         (e.g. MASC, supercharger, TSM)
     */
    public String formattedRunMP() {
        return Integer.toString(getRunMP());
    }

    /**
     * @return The minimum base walking/cruising/safe thrust value for the unit
     */
    public abstract int minWalkMP();

    /**
     * Calculates the maximum speed of the unit based on factors such as tonnage and engine type.
     *
     * @return           The maximum base walking/cruising/safe thrust value for the unit.
     */
    public int maxWalkMP() {
        return maxWalkMP(null);
    }

    /**
     * Calculates the maximum speed of the unit based on factors such as tonnage and engine type.
     *
     * @param techFilter Used by some units to determine whether to consider a large engine. If {@code null},
     *                   the large engine (if any) is considered legal.
     * @return           The maximum base walking/cruising/safe thrust value for the unit.
     */
    public abstract int maxWalkMP(@Nullable ITechFilter techFilter);

    /**
     * A secondary motive system provides an additional movement mode (jump, UMU, VTOL, etc).
     *
     * @return The secondary motive system component.
     */
    public abstract SecondaryMotiveSystem getSecondaryMotiveType();

    /**
     * Sets the component that provides a secondary movement mode (jump, UMU, VTOL, etc).
     * For unit types that cannot have a secondary system this is a noop.
     *
     * @param secondary The secondary motive system.
     */
    public abstract void setSecondaryMotiveType(SecondaryMotiveSystem secondary);

    /**
     * The base jump/UMU/VTOL MP without adjustments for restrictions
     */
    public abstract int getBaseSecondaryMP();

    /**
     * Sets the base value for calculating jump/UMU/VTOL MP
     * @param mp The base movement points
     */
    public abstract void setSecondaryMP(int mp);

    /**
     * @return The actual jump/UMU/VTOL MP after adjusting for restrictions
     */
    public abstract int getSecondaryMP();

    /**
     * Sets the number of heat sinks beyond those provided weight-free by the engine.
     *
     * @param count The number of additional heat sinks
     */
    public abstract void setAdditionalHeatSinkCount(int count);

    /**
     * @return The type of heat sink installed on the unit
     */
    public abstract HeatSink getHeatSinkType();

    /**
     * Sets the type of heat sink installed on the unit
     *
     * @param heatSink the type of heat sink
     */
    public abstract void setHeatSinkType(HeatSink heatSink);

    /**
     * Does not include those provided weight-fre by the engine.
     *
     * @return The number of additional heat sinks
     */
    public abstract int getAdditionalHeatSinkCount();

    public SecondaryMotiveSystem getDefaultSecondaryMotiveType() {
        return (SecondaryMotiveSystem) ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.SECONDARY_MOTIVE_NONE);
    }

    public HeatSink getDefaultHeatSinkType() {
        return (HeatSink) ComponentLibrary.getInstance()
                .getComponent(ComponentKeys.HEAT_SINK_SINGLE);
    }

    /**
     * Determines whether the component is allow on the unit. By default this checks
     * unit type, but subclasses will apply additional restrictions.
     *
     * @param component The component to mount
     * @return          Whether the component is legal for the unit
     */
    public boolean allowed(Component component) {
        return component.allowed(this);
    }

    /**
     * Checks whether this component is compatible with all components already installed.
     * @param component The componet to install
     * @return          Whether the component is compatible
     */
    public boolean compatibleWithInstalled(Component component) {
        for (Mount m : getComponents()) {
            if (!m.getComponent().equals(component)
                    && m.getComponent().incompatibleWith(component)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates the correct {@link Mount} type for the component. The mount is not added to the unit.
     *
     * @param c The Component to mount
     * @return  The component mount
     */
    public Mount createMount(@NotNull Component c) {
        switch (c.getType()) {
            case ARMOR:
                return new ArmorMount(this, (Armor) c);
            case COCKPIT:
                return new CockpitMount(this, (Cockpit) c);
            case HEAT_SINK:
                return new HeatSinkMount(this, (HeatSink) c);
            case HEAVY_WEAPON:
                return new WeaponMount(this, (HeavyWeapon) c, null);
            case SECONDARY_MOTIVE_SYSTEM:
                return new CompoundMount(this, c);
            default:
                return new Mount(this, c);
        }
    }

    /**
     * Retrieves a {@link Component} using the lookup key and creates the correct {@link Mount}
     * type for the component. The new mount is not added to the unit.
     *
     * @param componentKey a component lookup key
     * @return             a mount instance, or {@code null} if the lookup key does not match
     *                     any component
     */
    @Nullable
    public Mount createMount(String componentKey) {
        Component c = ComponentLibrary.getInstance().getComponent(componentKey);
        if (null != c) {
            return createMount(c);
        } else {
            return null;
        }
    }

    /**
     * Adds the equipment mount to the unit.
     *
     * @param m The equipment mount to add
     */
    public void addMount(Mount m) {
        components.add(m);
    }

    /**
     * Removes the mount from the unit.
     *
     * @param m The mount to remove
     * @return  Whether the mount was found and removed
     */
    public boolean removeMount(Mount m) {
        return components.remove(m);
    }

    /**
     * The movement heat is the amount of heat the unit generates simply by moving. This includes
     * mech running/jumping heat as well as misc equipment such as stealth armor.
     *
     * @return The unit's movement heat
     */
    public int movementHeat() {
        return getComponents().stream()
                .mapToInt(m -> m.getComponent().movementHeat(getUnitType()))
                .sum();
    }

    /**
     * Calculates the heat from all weapons fired at the maximum rate.
     *
     * @return The maximum weapon heat
     */
    public int maxWeaponHeat() {
        return getComponents().stream()
                .mapToInt(Mount::maxWeaponHeat)
                .sum();
    }

    /**
     * Calculates the heat from all weapons adjusted for BV purposes.
     *
     * @return The total weapon heat used for calculating BV.
     */
    public double adjustedWeaponHeat() {
        return getComponents().stream()
                .mapToDouble(Mount::modifiedWeaponHeat)
                .sum();
    }

    /**
     * @return The amount of heat that can be dissipated by the unit per round.
     */
    public int heatDissipation() {
        return getComponents().stream()
                .mapToInt(Mount::heatDissipation).sum();
    }
}
