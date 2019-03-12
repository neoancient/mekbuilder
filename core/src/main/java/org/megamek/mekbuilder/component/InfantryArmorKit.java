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

import java.util.EnumSet;
import java.util.Set;

/**
 * Armor wearable by conventional infantry
 */
public class InfantryArmorKit extends Component {

    private double damageDivisor = 1.0;
    private Set<InfantryArmorFlag> armorFlags = EnumSet.noneOf(InfantryArmorFlag.class);

    public double getDamageDivisor() {
        return damageDivisor;
    }

    public boolean hasFlag(InfantryArmorFlag flag) {
        return armorFlags.contains(flag);
    }
}
