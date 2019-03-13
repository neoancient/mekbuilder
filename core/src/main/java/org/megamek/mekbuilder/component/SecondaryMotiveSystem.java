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

/**
 * Components that provide a secondary movement mode (jump, underwater, VTOL). This does not include mechanical
 * jump jets, which do not provide a true jump mode and cannot be enhanced by partial wing or BA jump boosters.
 */
public class SecondaryMotiveSystem extends Component {

    private MotiveType mode = MotiveType.GROUND;

    private static final double BA_JUMP_WEIGHTS[] = { 25, 25, 50, 125, 250 };
    private static final double BA_UMU_WEIGHTS[] = { 45, 45, 85, 160, 250 };
    private static final double BA_VTOL_WEIGHTS[] = { 30, 40, 60 };

    public MotiveType getMode() {
        return mode;
    }

    @Override
    public double calcWeight(UnitBuild unit, double size) {
        if (unit.getUnitType().equals(UnitType.PROTOMEK)) {
            return unit.getTonnage() > 5 ? getWeightFactor() : getWeightFactor() * 0.5;
        } else if (unit.getUnitType().equals(UnitType.BATTLE_ARMOR)) {
            switch (mode) {
                case JUMP:
                    return BA_JUMP_WEIGHTS[unit.getWeightClass().value];
                case SUBMERSIBLE:
                    return BA_UMU_WEIGHTS[unit.getWeightClass().value];
                case VTOL:
                    return BA_VTOL_WEIGHTS[unit.getWeightClass().value];
                default:
                    return 0.0;
            }
        }
        if (unit.getTonnage() > 85) {
            return getWeightFactor() * 2;
        } else if (unit.getTonnage() > 55) {
            return getWeightFactor();
        } else {
            return getWeightFactor() * 0.5;
        }
    }

    @Override
    public double calcCost(UnitBuild unit, double size) {
        if (unit.getUnitType().equals(UnitType.BATTLE_ARMOR)) {
            if (mode.equals(MotiveType.JUMP)) {
                switch (unit.getWeightClass()) {
                    case ASSAULT:
                        return 300000 * size;
                    case HEAVY:
                        return 150000 * size;
                    case MEDIUM:
                        return 75000 * size;
                    default:
                        return 50000 * size;
                }
            } else {
                switch (unit.getWeightClass()) {
                    case ASSAULT:
                        return 150000 * size;
                    case HEAVY:
                        return 100000 * size;
                    case MEDIUM:
                        return 75000 * size;
                    default:
                        return 50000 * size;
                }
            }
        } else {
            return getCostFactor() * size * size * unit.getTonnage();
        }
    }

    @Override
    public int calcSlots(UnitBuild unit, double size) {
        // Vehicular jump jets only take one slot regardless of how many
        if (unit.getUnitType().isVehicle() && size > 0) {
            return 1;
        } else {
            return super.calcSlots(unit, size);
        }
    }
}
