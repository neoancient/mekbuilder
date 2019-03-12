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

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.EnumSet;
import java.util.Set;

/**
 * Conventional infantry weapons. These can also be used by BA and small support vehicles.
 */
public class InfantryWeapon extends Component {

    private double damage = 0.0;
    private int range = 0;
    private Set<InfantryWeaponFlag> weaponFlags = EnumSet.noneOf(InfantryWeaponFlag.class);
    @JsonAlias("crew")
    private int crewRequirement = 1;

    public double getDamage() {
        return damage;
    }

    /**
     * @return The base range of the weapon
     */
    public int getRange() {
        return range;
    }

    public boolean hasFlag(InfantryWeaponFlag flag) {
        return weaponFlags.contains(flag);
    }

    /**
     * Weapons that are encumbering (e.g. "1E") will also have the {@link InfantryWeaponFlag#ENCUMBERING ENCUMBERING flag}
     * @return The numeric portion of the crew requirement
     */
    public int crewRequirement() {
        return crewRequirement;
    }
}
