/*
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class ComponentLibraryTest {

    @Test
    void testLoadLibrary() {
        assertAll(
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Ammo AC/5") instanceof Ammunition),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent(ComponentKeys.ARMOR_STANDARD) instanceof Armor),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent(ComponentKeys.COCKPIT_STANDARD_MEK) instanceof Cockpit),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent(ComponentKeys.HEAT_SINK_SINGLE) instanceof HeatSink),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Small Laser") instanceof HeavyWeapon),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("MechWarrior Combat Suit") instanceof InfantryArmorKit),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Auto-Rifle") instanceof InfantryWeapon),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("ISMASC") instanceof MoveEnhancement),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("FusionEngine") instanceof MVFEngine),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Sword") instanceof PhysicalWeapon),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Jump Jet") instanceof SecondaryMotiveSystem)
        );
    }
}