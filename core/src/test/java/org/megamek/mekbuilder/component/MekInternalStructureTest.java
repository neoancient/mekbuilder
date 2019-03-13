package org.megamek.mekbuilder.component;

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.MekBuild;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class MekInternalStructureTest {

    @Test
    void testStructureWeight() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(50);

        assertEquals(mek.getStructureTonnage(), 5.0);
    }

    @Test
    void setComponent() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(50);
        mek.setStructureType(ComponentKeys.MEK_STRUCTURE_IS_ENDO_STEEL);

        assertEquals(mek.getStructureTonnage(), 2.5);
    }
}