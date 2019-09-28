/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2017 The MegaMek Team
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.megamek.mekbuilder.component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.Nullable;
import org.megamek.mekbuilder.tech.*;
import org.megamek.mekbuilder.unit.HeatStrategy;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitLocation;
import org.megamek.mekbuilder.unit.UnitType;

import java.util.*;

/**
 * Individual components that make up units. This class is immutable, and there is only
 * one instance of each component. Any situational details such as location
 * or variable size are handled through {@link Mount Mount}.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Component implements ITechDelegator {
    private ComponentType componentType = ComponentType.MISC;
    private boolean isDefault = false;
    private String internalName = "?";
    private String mmName = "?";
    private String fullName = "?";
    private String shortName = "?";
    private String nameFormat = null;
    private double weightFactor = 0.0;
    private double weightAddend = 0.0;
    private CalcMethod weightCalc = CalcMethod.BASIC;
    private RoundWeight roundWeight = RoundWeight.STANDARD;
    private double costFactor = 0.0;
    private double costAddend = 0.0;
    private CalcMethod costCalc = CalcMethod.BASIC;
    private double slots = 0.0;
    private double slotAddend = 0.0;
    private CalcMethod slotCalc = CalcMethod.BASIC;
    private double bvFactor = 0.0;
    private double bvFactor2 = 0.0;
    private BVCalcType bvCalcType = BVCalcType.EQUIPMENT;
    private String rulesRef = "?";
    @JsonSerialize(using=ComponentSwitch.SwitchMapSerializer.class)
    @JsonDeserialize(using=ComponentSwitch.SwitchMapDeserializer.class)
    private Map<ComponentSwitch, Object> switches = new EnumMap<>(ComponentSwitch.class);
    @JsonAlias("techAdvancement")
    private TechProgression techProgression = new TechProgression();
    private Set<UnitLocation> permittedLocations = EnumSet.noneOf(UnitLocation.class);
    @JsonSerialize(using=ComponentSwitch.SwitchMapSerializer.class)
    @JsonDeserialize(using=ComponentSwitch.SwitchMapDeserializer.class)
    private Map<ComponentSwitch, Object> requiredComponents = new EnumMap<>(ComponentSwitch.class);
    @JsonSerialize(using=ComponentSwitch.SwitchMapSerializer.class)
    @JsonDeserialize(using=ComponentSwitch.SwitchMapDeserializer.class)
    private Map<ComponentSwitch, Object> incompatibleComponents = new EnumMap<>(ComponentSwitch.class);
    private Map<UnitLocation, Integer> fixedLocations = new EnumMap<>(UnitLocation.class);
    private Set<UnitType> allowedUnitTypes = EnumSet.noneOf(UnitType.class);

    @JsonCreator
    Component() {}

    double getWeightFactor() {
        return weightFactor;
    }

    double getCostFactor() {
        return costFactor;
    }

    public ComponentType getType() {
        return componentType;
    }

    /**
     * @return Whether this is the default component in its category (e.g. standard structure, armor, fusion engine)
     */
    public boolean isDefault() {
        return isDefault;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getMMName() {
        return mmName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    /**
     * Formats a display name that incorporates the size of variable-sized components.
     * If no name format is provided, returns fullName.
     *
     * @param size The size of the component mount.
     * @return     The display name
     */
    String displayName(double size) {
        if (nameFormat == null) {
            return fullName;
        } else {
            return String.format(nameFormat, size);
        }
    }

    /**
     * The way this Component affect BV is determined by {@link #getBVCalcType()} using this factor.
     *
     * @return The BV factor
     */
    public double getBVFactor() {
        return bvFactor;
    }

    /**
     * For components that are included in both offensive and defensive BV, {@link #getBVFactor()} is used
     * for offensive and this value is used for defensive.
     *
     * @return The value to add to defensive bonus if required.
     */
    public double getBVFactor2() {
        return bvFactor2;
    }

    /**
     * @return A value indicating how this component affects BV calculations.
     */
    public BVCalcType getBVCalcType() {
        return bvCalcType;
    }

    /**
     * Components with fixed locations should override this to return the number required in each location.
     *
     * @param unit The unit mounting the component
     * @param location The location to check
     * @return The number of slots that must be allocated in the given location for this component.
     */
    public int fixedSlots(UnitBuild unit, UnitLocation location) {
        return fixedLocations.getOrDefault(location, 0);
    }

    /**
     * Calculates the weight of the component
     *
     * @param unit The unit mounting the component
     * @return The combined weight of the components
     */
    double calcWeight(UnitBuild unit) {
        return calcWeight(unit, 1.0);
    }

    /**
     * For non-linear weight calculations. This should be access through the {@link Mount}
     *
     * @param unit The unit mounting the component
     * @param size The size or number of the component present
     * @return The combined weight of the components
     */
    double calcWeight(UnitBuild unit, double size) {
       return roundWeight.round((weightCalc.calcValue(this, unit, weightFactor) + weightAddend) * size,
               unit);
    }

    /**
     * Calculates the cost of the component. This should be access through the {@link Mount}
     *
     * @param unit The unit mounting the component
     * @return The combined cost of the components
     */
    double calcCost(UnitBuild unit) {
        return calcCost(unit, 1.0);
    }

    /**
     * For non-linear cost calculations (e.g. jump jets).  This should be access through the {@link Mount}
     *
     * @param unit The unit mounting the component
     * @param size The size of number of the component present
     * @return The combined cost of the components
     */
    double calcCost(UnitBuild unit, double size) {
        return (costCalc.calcValue(this, unit, costFactor) + costAddend) * size;
    }

    /**
     * Calculates the number of slots the component requires. This should be access through the {@link Mount}
     *
     * @param unit The unit mounting the component
     * @return The number of slots required by the components
     */
    int calcSlots(UnitBuild unit) {
        return calcSlots(unit, 1.0);
    }

    /**
     * For non-linear slot calculations (e.g. vehicular jump jets take one slot regardless of number).
     * This should be access through the {@link Mount}
     *
     * @param unit The unit mounting the component
     * @param size The number or size of the component present
     * @return The number of slots required by the components
     */
    int calcSlots(UnitBuild unit, double size) {
        double s = slotCalc.calcValue(this, unit, slots) + slotAddend;
        if (hasFlag(ComponentSwitch.VARIABLE_SLOTS)) {
            s *= size;
        }
        return (int) Math.ceil(s);
    }

    /**
     * This mostly applies to heat sinks, but there is other equipment (such as partial wings)
     * that provide heat dissipation benefits as well.
     *
     * @return The amount of heat that can be dissipated by this Component.
     */
    int heatDissipation() {
        if (bvCalcType.equals(BVCalcType.HEAT_SINK)) {
            return (int) bvFactor;
        } else {
            return 0;
        }
    }

    /**
     * @return Whether only one of this type of equipment can be mounted in a location
     */
    public boolean locationExclusive() {
        return switches.containsKey(ComponentSwitch.LOCATION_EXCLUSIVE);
    }

    /**
     * @return Whether the component can be distributed to various locations around the unit.
     */
    public boolean spreadable() {
        return switches.containsKey(ComponentSwitch.SPREADABLE);
    }

    /**
     * @return If the equipment can explode and the location is eligible for CASE.
     */
    public boolean explosive() {
        return switches.containsKey(ComponentSwitch.EXPLOSIVE);
    }

    /**
     * @return If the component can be hit; if not, show greyed out on the record sheet crit table.
     */
    public boolean hittable() {
        return switches.containsKey(ComponentSwitch.HITTABLE);
    }

    /**
     * @return Whether the component has a particular location (or locations) where it always installed.
     */
    public boolean locationFixed() {
        return !fixedLocations.isEmpty();
    }

    /**
     * @return Whether this component can be placed in a modular mount (e.g. omni pod or BA modular mount).
     */
    public boolean moduleEligible() {
        return switches.containsKey(ComponentSwitch.MODULE);
    }

    /**
     * @return Whether this component has a variable size
     */
    public boolean variableSize() {
        return switches.containsKey(ComponentSwitch.VARIABLE);
    }

    /**
     * For variable-sized equipment, the step size is the minimal size increments. If not specified, it is
     * assumed to be one kilogram. This value has no meaning for components without variable size.
     *
     * @return The minimum size increment
     */
    public double variableStepSize() {
        if (variableSize()) {
            List<?> vals = (List<?>) switches.get(ComponentSwitch.VARIABLE);
            if (vals.size() > 0) {
                return (Double) vals.get(0);
            }
        }
        return 0.001;
    }

    /**
     * Some variable-sized equipment has a minimum size. If not specified, the minimum is assumed to be
     * the same as the step size. This value has no meaning for components without variable size.
     *
     * @return The minimum size
     */
    public double variableSizeMin() {
        if (variableSize()) {
            List<?> vals = (List<?>) switches.get(ComponentSwitch.VARIABLE);
            if (vals.size() > 1) {
                return (Double) vals.get(1);
            }
        }
        return variableStepSize();
    }

    /**
     * Some variable-sized equipment has a minimum size. If not specified, the maximum size returned is
     * {@link Double#MAX_VALUE}. This value has no meaning for components without variable size.
     *
     * @return The maximum size
     */
    public double variableSizeMax() {
        if (variableSize()) {
            List<?> vals = (List<?>) switches.get(ComponentSwitch.VARIABLE);
            if (vals.size() > 2) {
                return (Double) vals.get(2);
            }
        }
        return Double.MAX_VALUE;
    }

    /**
     * @param flag
     * @return Whether this component has the flag set.
     */
    public boolean hasFlag(ComponentSwitch flag) {
        return switches.containsKey(flag);
    }

    /**
     * Lookup for the value associated with a component flag. A {@code null} can indicate
     * that the flag is associated with an explicit null value or that the component
     * does not have the flag in question.
     *
     * @param flag The flag to test
     * @return     The value associated with the flag.
     */
    public @Nullable
    Object flagValue(ComponentSwitch flag) {
        return switches.get(flag);
    }

    /**
     * Compares the flag associated with a given value and returns whether they are equal.
     * This versioon of the method is used for filtering components and assumes that those
     * who do not have an associated value are to be included.
     * @see #flagMatches(ComponentSwitch, Object, boolean)
     *
     * @param flag        The flag to check
     * @param match       The value to match
     * @return            Whether the value associated with the flag matches the provided value
     */
    public boolean flagMatches(ComponentSwitch flag, Object match) {
        return flagMatches(flag, match, true);
    }

    /**
     * Compares the flag associated with a given value and returns whether they are equal.
     * If the Component does not have a value associated with the flag, the default value is returned.
     *
     * @param flag        The flag to check
     * @param match       The value to match
     * @param defaultVal  The value to return if the flag is not present
     * @return            Whether the value associated with the flag matches the provided value
     */
    public boolean flagMatches(ComponentSwitch flag, Object match, boolean defaultVal) {
        final Object val = switches.get(flag);
        if (val != null) {
            return val.equals(match);
        } else {
            return defaultVal;
        }
    }

    /**
     * @param loc A location on the unit
     * @param unit The unit
     * @return Whether the component can be mounted in the given location.
     */
    public boolean locationIsPermitted(UnitLocation loc, UnitBuild unit) {
        if (permittedLocations.isEmpty() || permittedLocations.contains(loc)) {
            return true;
        }
        if (loc == UnitLocation.VIRTUAL_COCKPIT && unit.isCockpitLocation(loc)) {
            return true;
        }
        if (loc == UnitLocation.VIRTUAL_NON_COCKPIT && unit.isCockpitLocation(loc)) {
            return false;
        }
        if (loc == UnitLocation.VIRTUAL_ENGINE && !unit.getEngine().isEngineLocation(loc)) {
            return false;
        }
        if (permittedLocations.contains(UnitLocation.VEE_TURRET) && (loc == UnitLocation.VEE_TURRET2)) {
            return true;
        }
        if (permittedLocations.contains(UnitLocation.VEE_LEFT)
                && (loc == UnitLocation.SHV_LFRONT || loc == UnitLocation.SHV_LREAR)) {
            return true;
        }
        if (permittedLocations.contains(UnitLocation.VEE_RIGHT)
                && (loc == UnitLocation.SHV_RFRONT || loc == UnitLocation.SHV_RREAR)) {
            return true;
        }
        if (permittedLocations.contains(UnitLocation.VIRTUAL_MEK_LATERAL)
                && unit.isQuad()
                && (loc == UnitLocation.MEK_LTORSO || loc == UnitLocation.MEK_RTORSO)) {
            return true;
        }
        if (permittedLocations.contains(UnitLocation.VIRTUAL_MEK_LATERAL)
                && !unit.isQuad()
                && (loc == UnitLocation.MEK_LARM || loc == UnitLocation.MEK_RARM)) {
            return true;
        }
        //Ignore the permitted locations if they do not apply to this build.
        return permittedLocations.stream().noneMatch(l -> unit.getLocationSet().contains(l));
    }

    /**
     * @param unit The unit a component might be installed on
     * @return Whether the component can be installed on the unit based on build type
     */
    public boolean allowed(UnitBuild unit) {
        return allowedUnitTypes.isEmpty() || allowedUnitTypes.contains(unit.getUnitType());
    }

    /**
     * @return A set of flags that must be met by other installed components.
     */
    public Set<ComponentSwitch> requirements() {
        return Collections.unmodifiableSet(requiredComponents.keySet());
    }

    /**
     * Checks list of incompatible components against another's flags and vice versa.
     *
     * @param other Another Component to compare
     * @return      Whether the two components are incompatible on the same unit.
     */
    public boolean incompatibleWith(Component other) {
        return incompatibleComponents.keySet().stream().anyMatch(other::hasFlag)
                || other.incompatibleComponents.keySet().stream().anyMatch(this::hasFlag);
    }

    /**
     * Returns the amount of heat generated for a specific unit type.
     *
     * @param unitType The type of unit using the component.
     * @return         The heat generated by the component.
     */
    public int movementHeat(UnitType unitType) {
        if (unitType.heatStrategy.equals(HeatStrategy.NOT_TRACKED)) {
            return 0;
        }
        return (Integer) switches.getOrDefault(ComponentSwitch.MOVEMENT_HEAT, 0);
    }

    /**
     * Computes the amount of heat generated by firing a weapon at its full firing rate.
     * Heat generated by non-weapon Components is treated as movement heat, so non-weapons
     * should return 0 for this.
     *
     * @param unitType The type of unit the component is mounted on.
     * @return         The maximum single-turn heat that can be generated by firing as a weapon.
     */
    public double maxWeaponHeat(UnitType unitType) {
        return 0;
    }

    @Override
    public ITechProgression techDelegate() {
        return techProgression;
    }

}
