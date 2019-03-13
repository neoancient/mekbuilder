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

/**
 *
 */
public class PhysicalWeapon extends Component {
    private double damageFactor = 0.0;
    private double damageAddend = 0.0;
    private CalcMethod damageCalc = CalcMethod.BASIC;

    public int damage(UnitBuild unit) {
        return (int) Math.ceil(damageCalc.calcValue(this, unit, damageFactor) + damageAddend);
    }
}
