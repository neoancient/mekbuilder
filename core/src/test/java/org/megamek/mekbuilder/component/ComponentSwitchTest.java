package org.megamek.mekbuilder.component;

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.UnitWeightClass;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Neoancient
 */
class ComponentSwitchTest {
    @Test
    void testIntParser() {
        assertAll(
                () -> assertEquals(DataParsers.intParser.apply("1", ","), 1),
                () -> assertEquals(DataParsers.intParser.apply("-2", ","), -2)
        );
    }

    @Test
    void testDoubleArrayParser() {
        final List<Double> list = (List<Double>) DataParsers.doubleArrayParser.apply("1.1,2.3,-0.4", ",");

        assertAll(
                () -> assertEquals(list.size(), 3),
                () -> assertTrue(list.contains(Double.valueOf(1.1))),
                () -> assertTrue(list.contains(Double.valueOf(2.3))),
                () -> assertTrue(list.contains(Double.valueOf(-0.4)))
        );
    }

    @Test
    void testWeightClassSetParser() {
        final StringJoiner sj = new StringJoiner(",");
        sj.add(UnitWeightClass.LIGHT.name());
        sj.add(UnitWeightClass.HEAVY.name());
        final Set<UnitWeightClass> set = (Set<UnitWeightClass>) ComponentSwitch.WEIGHT_CLASS.deserializeValue(sj.toString());

        assertAll(
                () -> assertEquals(set.size(), 2),
                () -> assertTrue(set.contains(UnitWeightClass.LIGHT)),
                () -> assertTrue(set.contains(UnitWeightClass.HEAVY))
        );
    }

}