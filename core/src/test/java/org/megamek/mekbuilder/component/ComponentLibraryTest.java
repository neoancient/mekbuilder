package org.megamek.mekbuilder.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class ComponentLibraryTest {

    @Test
    void testLoadLibrary() {
        assertTrue(ComponentLibrary.getInstance().getComponent("Small Cockpit") instanceof Component);
    }
}