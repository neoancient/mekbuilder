package org.megamek.mekbuilder.tech;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure that all built-in construction options are available
 */
class ConstructionOptionKeyTest {
    @Test
    void testAllOptionsLoad() {
        StringJoiner sb = new StringJoiner(",");

        for (ConstructionOptionKey key : ConstructionOptionKey.values()) {
            if (key.get() == null) {
                sb.add(key.name());
            }
        }

        assertEquals("", sb.toString());
    }

    @Test
    void testPreviousWeightClassOptionsMatch() {
        StringJoiner sb = new StringJoiner(",");

        for (ConstructionOptionKey key : ConstructionOptionKey.values()) {
            final ConstructionOption val = key.get();
            if (val instanceof UnitConstructionOption) {
                final ConstructionOptionKey prev = ((UnitConstructionOption) val).getPrevWeightKey();
                final ConstructionOptionKey next = ((UnitConstructionOption) val).getNextWeightKey();
                if ((prev != null)
                        && (!(prev.get() instanceof UnitConstructionOption)
                        || ((UnitConstructionOption) prev.get()).getNextWeightKey() != key)) {
                    sb.add(key.name());
                } else if ((next != null)
                        && (!(next.get() instanceof UnitConstructionOption)
                        || ((UnitConstructionOption) next.get()).getPrevWeightKey() != key)) {
                    sb.add(key.name());
                }
            }
        }

        assertEquals("", sb.toString());
    }
}