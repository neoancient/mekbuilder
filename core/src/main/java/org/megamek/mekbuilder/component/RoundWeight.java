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

import org.megamek.mekbuilder.unit.UnitBuild;

import java.util.function.BiFunction;

/**
 * A series of rounding methods.
 */
public enum RoundWeight {
    /** Perform no rounding */
    NONE ((w, u) -> w),
    /** Round to nearest kg */
    NEAREST_KG ((w, u) -> Math.round(w * 1000.0) / 1000.0),
    /** Round up to next half ton */
    NEXT_HALF_TON ((w, u) -> Math.ceil(truncate(w) * 2.0) / 2.0),
    /** Round kg standard to nearest kg, ton-standard to next half ton */
    STANDARD ((w, u) -> {
        if (null != u && u.usesKilogramStandard()) {
            return RoundWeight.NEAREST_KG.round(w, u);
        } else {
            return RoundWeight.NEXT_HALF_TON.round(w, u);
        }
    }),
    /** Round up to nearest kg (used for small SV engine and structure) */
    NEXT_KG ((w, u) -> Math.ceil(truncate(w) * 1000.0) / 1000.0),
    /** Round up to nearest ton */
    NEXT_TON ((w, u) -> Math.ceil(truncate(w)));

    private final BiFunction<Double, UnitBuild, Double> calc;

    RoundWeight(BiFunction<Double, UnitBuild, Double> apply) {
        this.calc = apply;
    }

    /**
     * Applies the rounding operation to a weight
     * @param weight The weight to be rounded, in metric tons.
     * @param unit   The unit the equipment is mounted on. This is needed for operations that
     *               depend on whether the unit uses the ton or kg standard. If {@code null},
     *               the unit is assumed to use the ton standard.
     * @return       The result of the rounding operation.
     */
    public double round(double weight, UnitBuild unit) {
        return calc.apply(weight, unit);
    }

    /**
     * Chops off trailing float irregularities by rounding to the gram. Used as the first step
     * in rounding operations that round up.
     *
     * @param weight The weight to round.
     * @return       The weight rounded to the gram.
     */
    public static double truncate(double weight) {
        return Math.round(weight * 1000000.0) / 1000000.0;
    }
}
