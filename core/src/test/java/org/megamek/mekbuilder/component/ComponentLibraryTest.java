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
                () -> assertTrue(ComponentLibrary.getInstance().getComponent("ISCASE") instanceof Component)
        );
    }
}