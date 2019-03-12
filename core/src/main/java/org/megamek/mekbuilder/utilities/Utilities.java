/**
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2019 The MegaMek Team
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
package org.megamek.mekbuilder.utilities;

import org.megamek.mekbuilder.unit.UnitBuild;

/**
 * A collection of general purpose utilities
 */

public class Utilities {

    /**
     * Used for round a weight up to the nearest ton
     *
     * @param val A weight value in tons
     * @return    The whole tonnage value for the fractional weight
     */
    public static double roundToTon(double val) {
        return Math.ceil(val);
    }

    /**
     * Used for rounding a weight up to the nearest half ton
     *
     * @param val A weight value in tons
     * @return    The weight rounded to the half ton
     */
    public static double roundToHalfTon(double val) {
        return Math.ceil(val * 2.0) * 0.5;
    }

    /**
     * Used for rounding a weight to the nearest kilogram.
     *
     * @param val A weight value in tons
     * @return    The weight rounded to the nearest kilogram
     */
    public static double roundToKilo(double val) {
        return Math.round(val * 1000.0) / 1000.0;
    }

    /**
     * Used for rounding a weight using the appropriate standard for the type of unit.
     *
     * @param val  A weight value in tons
     * @param unit The unit used to determine the rounding standard
     * @return     The weight rounded appropriately for the unit
     */
    public static double round(double val, UnitBuild unit) {
        return unit.usesKilogramStandard() ? roundToKilo(val) : roundToHalfTon(val);
    }
}
