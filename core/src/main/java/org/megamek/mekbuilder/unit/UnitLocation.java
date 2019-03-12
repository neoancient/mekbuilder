/**
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
package org.megamek.mekbuilder.unit;

/**
 * @author: Neoancient
 */
public enum UnitLocation {
    NO_LOCATION(false, false, false),

    //Virtual locations that match various locations depending on external circumstances
    VIRTUAL_ENGINE(false, false, false), //Any location containing an engine
    VIRTUAL_COCKPIT(false, false, false), //The cockpit/control system location
    VIRTUAL_NON_COCKPIT(false, false, false), //Any location except the cockpit/control system location
    VIRTUAL_MEK_LATERAL(false, false, false), //Matches arm of biped Mek or side torso of quad. Used by
    //some industrial equipment.

    MEK_HEAD(true, true, false),
    MEK_CTORSO(true, true, true),
    MEK_LTORSO(true, true, true),
    MEK_RTORSO(true, true, true),
    MEK_LARM(true, true, false),
    MEK_RARM(true, true, false),
    MEK_LLEG(true, true, false),
    MEK_RLEG(true, true, false),
    MEK_LFLEG(true, true, false), //replaces arm locations in quads; MEK_LLEG and MEK_RLEG are used for rear legs
    MEK_RFLEG(true, true, false), //replaces arm locations in quads; MEK_LLEG and MEK_RLEG are used for rear legs
    MEK_CLEG(true, true, false), //center leg for tripods

    PMEK_TORSO(true, true, false), PMEK_MAIN_GUN(true, false, false),
    PMEK_LARM(true, false, false), PMEK_RARM(true, false, false),

    //Standard combat and support vehicles
    VEE_BODY(false, false, false),
    VEE_FRONT(true, false, false), VEE_LEFT(true, false, false),
    VEE_RIGHT(true, false, false), VEE_REAR(true, false, false),
    VEE_TURRET(true, false, false), VEE_TURRET2(true, false, false),
    VTOL_ROTOR(true, false, false),

    //Superheavy vehicles use the same locations as standard vees except for the sides
    SHV_LFRONT(true, false, false),
    SHV_RFRONT(true, false, false),
    SHV_LREAR(true, false, false), SHV_RREAR(true, false, false),

    //Conventional infantry
    INFANTRY_SOLDIER(false, false, false),
    INFANTRY_SQUAD(false, false, false),

    //BattleArmor
    BA_LARM(true, false, false),
    BA_RARM(true, false, false),
    BA_BODY(true, false, false), BA_TURRET(true, false, false),
    BA_SQUAD_SUPPORT(false, false, false),

    //Aerospace and conventional fighters, aerodyne small craft and dropships
    AERODYNE_NOSE(true, false, false),
    AERODYNE_LWING(true, true, false),
    AERODYNE_RWING(true, true, false),
    AERODYNE_AFT(true, false, false),
    AERODYNE_FUSELAGE(false, false, false),

    //Spheroid small craft and dropships
    SPHEROID_NOSE(true, false, false),
    SPHEROID_LF(true, false, false), SPHEROID_RF(true, false, false),
    SPHEROID_LR(true, false, false), SPHEROID_RR(true, false, false),
    SPHEROID_AFT(true, false, false), SPHEROID_FUSELAGE(false, false, false);

    private final boolean armored;
    private final boolean rearMounting;
    private final boolean rearArmor;

    UnitLocation(boolean armored, boolean rearMounting, boolean rearArmor) {
        this.armored = armored;
        this.rearMounting = rearMounting;
        this.rearArmor = rearArmor;
    }

    /**
     * @return Whether this location can have armor mounted
     */
    public boolean isArmored() {
        return armored;
    }

    /**
     * @return Whether weapons can be mounted with a rear facing in this location
     */
    public boolean hasRearMounting() {
        return rearMounting;
    }

    /**
     * @return Whether the location has separate rear armor (e.g. Mech torso
     */
    public boolean hasRearArmor() {
        return rearArmor;
    }
}
