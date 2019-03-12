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
 * Weapon categories for use by aerospace units. This determines which weapons can be grouped together
 * in the same bay.
 */
public enum AeroWeaponClass {
    UNKNOWN(false),
    LASER(false),
    POINT_DEFENSE(false),
    PPC(false),
    PULSE_LASER(false),
    ARTILLERY(false),
    AMS(false),
    AC(false),
    LBX(false),
    LRM(false),
    SRM(false),
    MRM(false),
    MML(false),
    ATM(false),
    ROCKET_LAUNCHER(false),
    PLASMA(false),
    CAPITAL_LASER(true),
    CAPITAL_PPC(true),
    CAPITAL_GAUSS(true),
    CAPITAL_MISSILE(true),
    AR10(true),
    SCREEN(true),
    SUB_CAPITAL_CANNON(true),
    CAPITAL_MD(true);

    public final boolean capital;

    AeroWeaponClass(boolean capital) {
        this.capital = capital;
    }
}
