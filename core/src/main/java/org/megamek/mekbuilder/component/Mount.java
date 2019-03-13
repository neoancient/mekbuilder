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
package org.megamek.mekbuilder.component;

import org.megamek.mekbuilder.component.Component;
import org.megamek.mekbuilder.component.ComponentLibrary;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitLocation;

/**
 * An adapter that tracks the details of how a {@link Component} is mounted on a {@link UnitBuild};
 * includes location(s), turret status, front/rear facing, armored, omni/modular, and link to other equipment.
 */
public class Mount {

    public enum ModuleType {
        FIXED, OMNI, BA_MANIPULATOR, BA_MWM, BA_APM
    }

    private final UnitBuild unit;
    private String componentKey;
    private transient Component component;
    private UnitLocation location = UnitLocation.NO_LOCATION;
    private ModuleType moduleType = ModuleType.FIXED;
    private boolean rearFacing;
    private boolean armored;
    private double size;

    /**
     * Creates a new mounting for a component with a variable size
     *
     * @param unit     The unit the component is mounted on
     * @param component The component to mount
     * @param size      The size of the component
     */
    public Mount(UnitBuild unit, Component component, double size) {
        this.unit = unit;
        this.component = component;
        this.size = size;
    }

    /**
     * Creates a new mounting for a component
     *
     * @param unit     The unit the component is mounted on
     * @param component The component to mount
     */
    public Mount(UnitBuild unit, Component component) {
        this(unit, component, 1);
    }

    public UnitBuild getUnit() {
        return unit;
    }

    public UnitLocation getLocation() {
        return location;
    }

    public void setLocation(UnitLocation location) {
        this.location = location;
    }

    public Component getComponent() {
        if (null == component) {
            component = ComponentLibrary.getInstance().getComponent(componentKey);
        }
        return component;
    }

    public void setComponent(Component component) {
        this.componentKey = component.getInternalName();
        this.component = component;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public boolean isRearFacing() {
        return rearFacing;
    }

    public void setRearFacing(boolean rearFacing) {
        this.rearFacing = rearFacing;
    }

    public boolean isArmored() {
        return armored;
    }

    public void setArmored(boolean armored) {
        this.armored = armored;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (component.variableSize()) {
            this.size = size;
        }
    }

    public double getComponentWeight() {
        return component.calcWeight(getUnit(), size);
    }

    public double getComponentCost() {
        return component.calcCost(getUnit(), size);
    }

    public int getComponentSlots() {
        return component.calcSlots(unit, size);
    }

    /**
     * Move all slots from one location to a new location. If there are no slots present in the
     * old location, nothing is changed.
     *
     * @param oldLoc The current location
     * @param newLoc The new location
     */
    public void changeLocation(UnitLocation oldLoc, UnitLocation newLoc) {
        if (location.equals(oldLoc)) {
            location = newLoc;
        }
    }

    /**
     * Move a certain number of slots from an old location to a new one, if it is possible to split
     * the component.
     *
     * @param oldLoc The old location
     * @param newLoc The new location
     * @param slots The number of slots to move. If the equipment cannot be split, or has fewer slots
     *              than indicated, all slots are moved.
     */
    public void changeLocation(UnitLocation oldLoc, UnitLocation newLoc, int slots) {
        changeLocation(oldLoc, newLoc);
    }

    public boolean isInLocation(UnitLocation loc) {
        return location.equals(loc);
    }
}
