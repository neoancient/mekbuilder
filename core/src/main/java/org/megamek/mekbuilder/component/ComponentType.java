/**
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2019 The MegaMek Team
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
 * Various component categories useful for filtering.
 *
 * @author Neoancient
 */

public enum ComponentType {
    MISC("Miscellaneous"),		//Does not fit any other category
    ARMOR("Armor"),
    COCKPIT("Cockpit/Control"),	//Also includes control systems
    ENGINE("Engine"),
    SECONDARY_MOTIVE_SYSTEM("Secondary Motive System"), //Provides additional motive type to ground units, such as jump or underwater
    MOVE_ENHANCEMENT("Enhancement, Movement"),	//Supplements existing movement
    HEAT_SINK("Heat Sink"),
    LOCATION_ENHANCEMENT("Enhancement, Location"), //Provides benefit or capability to location, including CASE and turrets
    MEK_STRUCTURE("Internal Structure"),
    GYRO("Gyro"),
    MYOMER("Myomer"),
    HEAVY_WEAPON("Weapon, Heavy"),	//Includes BattleArmor weapons
    CAPITAL_WEAPON("Weapon, Capital"),
    AMMUNITION("Ammunition"),
    ELECTRONICS("Electronics"),
    PHYSICAL_WEAPON("Weapon, Physical"),
    ENHANCEMENT("Enhancement, Equipment"),	//Always paired with another component, such as Artemis or PPC Capacitor
    INF_WEAPON("Weapon, Infantry"),
    INF_ARMOR("Armor, Infantry");

    private final String displayName;

    ComponentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
