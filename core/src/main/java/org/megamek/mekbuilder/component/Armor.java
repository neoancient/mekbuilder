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

import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitLocation;

/**
 * Component for all types of unit armor. Provides additional methods for calculating slots.
 */
public class Armor extends Component {

    @Override
    public int calcSlots(UnitBuild unit, double size) {
        if (unit.getUnitType().isVehicle()) {
            return fixedSlots(unit, UnitLocation.VEE_BODY);
        } else if (unit.getUnitType().isFighter()) {
            return unit.getLocationSet().stream().mapToInt(l -> fixedSlots(unit, l)).sum();
        } else {
            return super.calcSlots(unit, 1.0);
        }
    }

    /**
     * @param unit
     * @return The number of slots taken up in a location covered in patchwork armor.
     */
    public int patchworkSlots(UnitBuild unit) {
        int totalSlots = calcSlots(unit);
        if (totalSlots == 0) {
            return 0;
        } else if (unit.getUnitType().isVehicle()) {
            return totalSlots > 2 ? 2 : 1;
        } else if (unit.getUnitType().isFighter()) {
            return totalSlots > 3 ? 2 : 1;
        }
        if (totalSlots > 14) {
            return 3;
        } else if (totalSlots > 7) {
            return 2;
        } else {
            return 1;
        }
    }
}
