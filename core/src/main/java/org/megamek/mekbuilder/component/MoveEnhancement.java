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

import org.megamek.mekbuilder.unit.MotiveType;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitType;
import org.megamek.mekbuilder.unit.UnitWeightClass;

import java.util.EnumSet;
import java.util.Set;

/**
 * Components that enhance a unit's movement but does not provide a secondary movement mode. This includes equipment
 * such as MASC or a partial wing, but not jump jets. It does include mechanical jump jets, which provides a very
 * restricted type of jump and is more like enhancements than the secondary motive systems.
 */
public class MoveEnhancement extends Component {

    private MotiveType mode = MotiveType.GROUND;
    private boolean baseModeRequired = false;
    /** Tracks incompatibility based on another movement enhancement having a particular quality */
    private Set<ComponentSwitch> incompatible = EnumSet.noneOf(ComponentSwitch.class);

    /**
     * @return The movement mode that is enhanced by this component
     */
    public MotiveType getMode() {
        return mode;
    }

    /**
     * Some movement enhancements require the unit to have a secondary motive system to grant the
     * base movement mode. For example, a partial wing requires jump jets, but MASC
     * does not require any additional motive system.
     *
     * @return Whether the unit requires the corresponding secondary motive system.
     */
    public boolean isBaseModeRequired() {
        return baseModeRequired;
    }

    /**
     * @param other Another movement enhancement component
     * @return Whether this component can be used on the same unit as the other component.
     */
    public boolean compatible(MoveEnhancement other) {
        for (ComponentSwitch flag : incompatible) {
            if (other.hasFlag(flag)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double calcCost(UnitBuild unit, double size) {
        // Mechanical jump jet costs are non-linear
        if (hasFlag(ComponentSwitch.MECH_JUMP)) {
            if (unit.getUnitType().isMech()) {
                return calcCost(unit, 1.0) * size * size;
            } else if (unit.getUnitType().equals(UnitType.BATTLE_ARMOR)) {
                if (unit.getWeightClass().equals(UnitWeightClass.ASSAULT)) {
                    return 600000.0;
                } else if (unit.getWeightClass().equals(UnitWeightClass.ASSAULT.HEAVY)) {
                    return 300000.0;
                } else if (unit.getWeightClass().equals(UnitWeightClass.MEDIUM)) {
                    return 150000.0;
                } else {
                    return 100000.0;
                }
            }
        }
        return super.calcCost(unit, size);
    }

    @Override
    public boolean allowed(UnitBuild unit) {
        if (isBaseModeRequired() && !getMode().equals(unit.getSecondaryMotiveType().getMode())) {
            return false;
        }
        return super.allowed(unit);
    }
}
