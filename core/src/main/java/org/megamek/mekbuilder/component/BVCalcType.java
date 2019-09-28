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
 * Indicates how the BV factor is used in calculating the battle value.
 */
public enum BVCalcType {
    /** Has no effect on BV */
    NONE,
    /** For armor, this is the value to use for the protected location. For other equipment,
     *  this is added to the armor's ATM for the location in which it is mounted. */
    ARMOR,
    INTERNAL_STRUCTURE,
    ENGINE_TYPE, //Requires adjustment for superheavy meks
    MEK_GYRO,
    /** Based on vehicle motive system */
    VEHICLE_TYPE,
    /** May prevent defensive battle rating penalty for explosive equipment */
    CASE,
    /** As CASE, but more protection */
    CASE_II,
    /** Center torso armor factor is multiplied by BV factor (which doubles it for torso-mounted) */
    TORSO_COCKPIT,
    /** Amount of heat that can be dissipated in a round */
    HEAT_SINK,
    /** Added to base movement (TSM bonus) */
    MOVE_FACTOR,
    /** Use as movement heat if no jump/UMU (super-cooled myomer) */
    MOVEMENT_HEAT,
    /** Multiplier for heat sink capacity (coolant pod) */
    HEAT_CAPACITY,

    /** Added to the defensive battle rating. */
    DEFENSIVE,
    /** Added to the offensive battle rating, possibly adjusted for heat. */
    WEAPON,
    /** Added to the offensive battle rating, possibly adjusted for excess. */
    AMMUNITION,
    /** BV factor is multiplied by damage (physical weapons). */
    DAMAGE,
    /** Added to the offensive battle rating, but not adjusted for heat. */
    EQUIPMENT,
    /** BV of linked equipment is multiplied by this amount. */
    LINKED,
    /** First value is offensive BV and second is defensive. */
    BOTH,
    /** Multiply BV of qualifying machine guns in same location by factor */
    MGA,
    /** Requires lookup table */
    PM_MISSILE_TUBE,
    /** Second value is with capacitor */
    PPC,
    /** First value is multiplied by armor type modifier for location; second is defensive bv mod per location */
    HARJEL,

    /** Multiplier for final BV */
    FINAL;
}
