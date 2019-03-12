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

/**
 * Qualities that apply specifically to weapons and weapon-like equipment
 */
enum WeaponFlag {
    DIRECT_FIRE_BALLISTIC("DB"),
    DIRECT_FIRE_ENERGY("DE"),
    PULSE("P"),
    MISSILE("M"),

    AREA_EFFECT("AE"),
    CLUSTER("C"),
    HEAT_CAUSING("H"),
    ANTI_INFANTRY("AI"),
    RAPID_FIRE("R"),
    VARIABLE_DAMAGE("V"),
    ONE_SHOT("OS"),
    POINT_BLANK("PD"),
    ELECTRONICS("E"),
    COUNTER_ELECTRONICS("CE"),
    TARGETING_SYSTEM("T"),
    SWITCHABLE_AMMO("S"),
    PERFORMANCE_ENHANCEMENT("PE"),
    FLAK("F"),
    EXPLOSIVE_WEAPON("X");

    public final String code;

    WeaponFlag(String code) {
        this.code = code;
    }
}
