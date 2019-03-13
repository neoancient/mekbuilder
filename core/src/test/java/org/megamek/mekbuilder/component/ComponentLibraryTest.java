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
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Clan Ferro-Fibrous") instanceof Armor),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Standard Cockpit") instanceof Cockpit),
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("Heat Sink") instanceof HeatSink),
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