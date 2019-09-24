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

import java.util.Arrays;

/**
 * The possible formulas for calculating a component's weight, slot requirement, cost, or BV.
 */

@FunctionalInterface
interface Calculation {
    double calcValue(Component component, UnitBuild unit, double factor);
}

public enum CalcMethod {

    BASIC ("-", (c, u, f) -> f), // factor itself
    TOTAL_TONNAGE ("TT", (c, u, f) -> f * u.getTonnage()), // factor x total unit tonnage
    INVERSE_TT ("TT/#", (c, u, f) -> (f != 0.0) ? 0.0 : u.getTonnage() / f), // total tonnage / factor
    COMPONENT_TONNAGE ("CT", (c, u, f) -> f * c.calcWeight(u)), // factor * tonnage of component
    ENGINE_TONNAGE ("ET", (c, u, f) -> f * u.getEngine().getEngineTonnage()), // factor * engine tonnage
    ENGINE_RATING ("ER", (c, u, f) -> f * u.getEngine().getEngineRating()), // factor * engine rating
    CRITICAL_SPACE ("CS", (c, u, f) -> f * c.calcSlots(u)), // factor * number of critical slots
    STRUCTURE_TONNAGE ("IT", (c, u, f) -> f * u.getStructureTonnage()), // factor * internal structure tonnage
    ARMOR_TONNAGE ("AT", (c, u, f) -> f * u.getArmorTonnage()), // factor * total armor tonnage
    ARMOR_VALUE ("AV", (c, u, f) -> f * u.getTotalArmorPoints()), // factor * total armor points
    POINTS_PER_TON ("PPT", (c, u, f) -> f > 0 ? 1.0 / f : 0.0), // 1 / factor
    MASC ("ETCT", (c, u, f) -> f * u.getTonnage() * c.calcWeight(u)), // factor * engine tonnage * component tonnage
    MOVEMENT_POINTS ("MP", (c, u, f) -> f * u.getBaseWalkMP()), // factor * ground MP
    NUM_LEGS ("LEG", (c, u, f) -> {
        if (u.isQuad()) {
            return f * 4;
        } else if (u.isTripod()) {
            return f * 3;
        } else if (u.isBiped()) {
            return f * 2;
        } else {
            return f;
        }
    }), // factor * number of legs (Mek)
    WEAPON_TONNAGE ("WT", (c, u, f) -> f * u.getWeaponTonnage()), // factor * weapon tonnage
    TARGETING_COMPUTER_TONNAGE ("TCT", (c, u, f) -> f * u.getTCLinkedTonnage()), // factor * tonnage of weapons linked to TC
    LINKED_TONNAGE ("LT", (c, u, f) -> 0.0), //factor * tonnage of linked equipment; this must be handled by the mount.
    LINKED_COST ("LC", (c, u, f) -> 0.0), //factor * cost of linked equipment; this must be handled by the mount.
    ENERGY_WEAPON_TONNAGE ("EWT", (c, u, f) -> f * u.getEnergyWeaponTonnage()); //factor * tonnage of energy weapons

    private final String abbrev;
    private final Calculation calculation;

    CalcMethod(String abbrev, Calculation calculation) {
        this.abbrev = abbrev;
        this.calculation = calculation;
    }

    /**
     * Calculates a component's weight, cost, slot requirement, or BV
     *
     * @param component The component
     * @param unit      The unit it's mounted on
     * @param factor    The component's factor for the type of value being calculated
     * @return          The result of the calculation
     */
    public double calcValue(Component component, UnitBuild unit, double factor) {
        return calculation.calcValue(component, unit, factor);
    }

    public static CalcMethod fromAbbrev(String abbrev) {
        return Arrays.stream(CalcMethod.values()).filter(v -> v.abbrev.equals(abbrev)).findFirst().orElse(null);
    }
}
