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

import com.fasterxml.jackson.annotation.JsonCreator;
import megamek.common.AmmoType;

/**
 * Component for weapon ammunition
 */
public class Ammunition extends Component {

    private AmmunitionType ammoType = AmmunitionType.NONE;
    private String munitionType = "Standard";
    private int damagePerShot = 0;
    private int rackSize = 0;
    private int shots = 0;
    private double kgPerShot = 0.0;
    private WeaponRange range = new WeaponRange(0, 0, 0, 0, 0);

    public AmmunitionType getAmmoType() {
        return ammoType;
    }

    public String getMunitionType() {
        return munitionType;
    }

    public int getDamagePerShot() {
        return damagePerShot;
    }

    public int getRackSize() {
        return rackSize;
    }

    public int getShots() {
        return shots;
    }

    public double getKgPerShot() {
        return kgPerShot;
    }

    public WeaponRange getRange() {
        return range;
    }

    public double tonsPerShot() {
        return kgPerShot / 1000.0;
    }
}
